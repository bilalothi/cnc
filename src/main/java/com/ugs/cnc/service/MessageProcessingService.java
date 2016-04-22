/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * MessageProcessingService.java
 * 
 * Author: Capt Bilal
 * 
 * version 1.3
 */
package com.ugs.cnc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugs.cnc.dao.IMessageDao;
import com.ugs.cnc.entities.Alert;
import com.ugs.cnc.entities.ApplMode;
import com.ugs.cnc.entities.Device;
import com.ugs.cnc.entities.Location;
import com.ugs.cnc.entities.Message;
import com.ugs.cnc.entities.Network;
import com.ugs.cnc.entities.RawMessage;
import com.ugs.cnc.enums.AlertState;
import com.ugs.cnc.enums.AlertType;
import com.ugs.cnc.enums.DeviceType;
import com.ugs.cnc.enums.MessageType;
import com.ugs.cnc.enums.StatusType;

/**
 * Service for processing messages received by sensor nodes and other devices
 *
 *
 * @author Capt Bilal
 */
@Service("messageProcessingService")
@Transactional(readOnly = true)
public class MessageProcessingService {

    private static final Logger logger = LoggerFactory
            .getLogger(MessageProcessingService.class);

    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IAlertService alertService;
    @Autowired
    private ILocationService locationService;
    @Autowired
    private IMessageProducer messageProducerService;
    @Autowired
    private IMessagingService messagingService;
    @Autowired
    private INetworkService networkService;
    @Autowired
    private IApplModeService applModeService;

    private static final String ALERT_MESSAGE = "Alert message received";
    private static final String LOW_BATTERY_MESSAGE = " -- Warning!!! Battery is low -- ";
    private static final String MEDIUM_BATTERY_MESSAGE = " -- Info!!! Half Battery is used -- ";
    private static final String HIGH_BATTERY_MESSAGE = " -- Info!!! Battery is full -- ";
    private static final String DEAD_BATTERY_MESSAGE = " -- Warning!!! Battery is DEAD -- ";
    private static final String SENSITIVITY_MESSAGE = "Device sensitivity status received: Sensitivity is set to level ";
    private static final String BATTERY_MESSAGE = "Device battery status received: Battery level is ";
    private static final String LINK_QUALITY_MESSAGE = "Device link quality received: Link Quality Indicator is ";
    private static final String WEAK_LINK_QUALITY_MESSAGE = "-- Warning!!! Link Quality is weak -- ";
    private static final String POOR_SIGNAL_STRENGTH_MESSAGE = "-- Warning!!! Signal strength is poor -- ";
    private static final String SIGNAL_STRENGTH_MESSAGE = "Device Signal strength received: Signal Strength is ";
    private static final String DEVICE_ON_MESSAGE = "Device turned on";
    private static final String DEVICE_OFF_MESSAGE = "Device turned off";
    private static final String DEVICE_DEBUG_MESSAGE = "Device in debug mode";
    private static final String DEVICE_SLEEP_MESSAGE = "Device in sleep mode";
    private static final String DEVICE_LOCATION_MESSAGE = "Device location received";
    private static final String AT_DEVICE_MESSAGE = " at device Id: ";
    private static final String WITH_DEVICE_MESSAGE = " Device with Id: ";
    private static final String DOES_NOT_EXIST_MESSAGE = " does not exist";
    // private static final String HEARTBEAT_MESSAGE =
    // "Device is alive. Heartbeat message received";
    private static final String DISCARD_MESSAGE = "--- message discarded. ";
    private static final String AT_SENSOR_MESSAGE = " at sensor number: ";
    private static final String AT_LOCATION_MESSAGE = " with location co-ordinates: ";
    private static final String INVALID_MESSAGE = " Invalid message --- message type does not exist: ";
    private static final String INVALID_ALERT_MESSAGE = " Invalid alert message --- type of alert does not exist: ";

    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static final int HOURS_HEART_BEAT_INTERVAL = 6;

    private double maxVoltageLevel = 4.0;
    private double minVoltageLevel = 3.6;

    /**
     * Method for processing and converting messages received from the sensors
     * into readable text
     *
     * @param consumedMessage Message received from the sensor in raw form
     */
    @Transactional(readOnly = false)
    public void processAndSendMessage(String consumedMessage) {

        // 11:26:P:0099:1:A:03
        // Network ID : Device ID : Device Type : Mac Add : Battery Msg :  Message
        // Type : PDU
        boolean deviceExists = true;

        RawMessage rawMessage = new RawMessage();

        // Create Message over here (Parse String)
        rawMessage = createRawMessage(consumedMessage);

        String deviceId = rawMessage.getDeviceId();
        String networkId = rawMessage.getNetworkId();
        String macAdd = rawMessage.getMacAdd();
        // String batteryStatus = rawMessage.getBatteryStatus();
        Message message = new Message();
        String rawMessageContent = rawMessage.getPayload();
        StringBuilder finalMessage = new StringBuilder();
        String messageType = rawMessage.getMessageType();
        String deviceType = rawMessage.getDeviceType();

        List<Network> listNetwork = networkService.getAllNetworks();
        Network network = null;

        // Get Network 
        if (!listNetwork.isEmpty()) {
            network = listNetwork.get(0);
        }
        if (network.getNetworkId() != null && network.getNetworkId().equals(networkId)) {

            String alertType;
            String statusType;
            String delims = "[,]";
            String[] processedPayload;
            Device device = deviceService.findByDeviceId(deviceId);

            List<Device> lstInvactiveDevice = null;
            if (device == null) {
                deviceExists = false;
                logger.info("Device with id: {} does not exist in the database", deviceId);
            }

            System.out.println("Message Type in Message Processing Class : " + messageType);

            message.setMessageType(MessageType.getValue(messageType).name());
            message.setMessageDeviceId(deviceId);
            message.setMessageNetworkId(networkId);
            message.setDeviceMac(macAdd);
            // message.setBatteryStatus(batteryStatus);

            message.setMessageDeviceType(DeviceType.getValue(deviceType).name());
            message.setMessageRawData(rawMessageContent);

            if (messageType == null) {
                // If message type does not exist then mark the message type as
                // INVALID
                message.setMessageType(MessageType.INVALID.toString());
                message.setMessageContent(finalMessage.append(INVALID_MESSAGE).append(messageType).toString());
            }

            /**
             * ************************** ALERT RECEIVED
             * ****************************************
             */
            else {
                if (messageType.equals(MessageType.ALERT.getCode())) {
                    // Check Alert Validity.. if Threshold and Pulse greater or equal to 
                    // Application mode then show alert
                    if (checkAlertValidityByThresholdAndPulse(rawMessage)) {

                        message.setMessageContent(finalMessage.append(ALERT_MESSAGE).append(AT_DEVICE_MESSAGE).append(deviceId).toString());
                        if (!deviceExists) {
                            message.setMessageContent(finalMessage.append(WITH_DEVICE_MESSAGE).append(deviceId).append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE).toString());
                            message.setMessageType(MessageType.INVALID.toString());
                        }

                        // Device Exists
                        else {
                            processedPayload = rawMessageContent.split(delims);
                            Alert alert = new Alert();
                            alert.setAlertDeviceId(deviceId);
                            alert.setFlag("True");
                            alert.setAlertState(AlertState.ACTIVE.name());

                            // Save Alert in Local Database 
                            alertService.saveAlert(alert);

                            // Save Device Alert Time and status if it is Off
                            device.setLastAlertTime(new Date());
                            device.setDeviceStatus("ON");
                            deviceService.updateDevice(device);

                            // Update Status of all other Devices
                            lstInvactiveDevice = findInactiveSensors();
                            for (Device device2 : lstInvactiveDevice) {
                                System.out.println("Inactive Devices : " + device2.getDeviceId());
                                device2.setDeviceStatus("OFF");
                                device2.setDeviceBattery(0);
                                deviceService.updateDevice(device2);
                            }

                            alertType = processedPayload[0];

                            String explode = "";
                            if (processedPayload.length > 1) {
                                explode = processedPayload[1];
                            }

                            if (explode != null && !explode.equals("") && explode.equals("E")) {
                                message.setExplode(true);
                            }
                            else {
                                message.setExplode(false);
                            }

                            System.out.println("-------------------------------- Alert Type : " + alertType);

                            if (alertType.equals(AlertType.MOVEMENT1.getCode()) || alertType.equals(AlertType.MOVEMENT2.getCode()) || alertType.equals(AlertType.MOVEMENT3.getCode())) {
                                message.setMessageContent(finalMessage.append(" -- Movement detected ").append(AT_SENSOR_MESSAGE).append(deviceId).toString());
                            }

                            else {
                                if (alertType.equals(AlertType.VIBRATION.getCode())
                                        || alertType.equals(AlertType.VIBRATION1.getCode())) {
                                    message.setMessageContent(finalMessage.append(" -- Vibration detected ").append(AT_SENSOR_MESSAGE).append(deviceId).toString());
                                }

                                else {
                                    if (alertType.equals(AlertType.VIDEO_DETECTION.getCode()) || alertType.equals(AlertType.VIDEO_DETECTION1.getCode())) {
                                        message.setMessageContent(finalMessage.append(" -- Video detection ").append(AT_SENSOR_MESSAGE).append(deviceId).toString());
                                    }

                                    else {
                                        if (alertType.equals(AlertType.MICROWAVE.getCode()) || alertType.equals(AlertType.MICROWAVE1.getCode())) {
                                            message.setMessageContent(finalMessage.append(" -- Microwave detection ").append(AT_SENSOR_MESSAGE).append(deviceId).toString());
                                        }
                                        // Mark any other type of detection as invalid
                                        else {
                                            System.out.println("Inside Else part of Message Conditions");
                                            message.setMessageType(MessageType.INVALID.toString());
                                            message.setMessageContent(finalMessage.append(INVALID_ALERT_MESSAGE).append(processedPayload[0]).toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                /**
                 * ************************** LOCATION MESSAGES
                 * ****************************************
                 */
                else {
                    if (messageType.equals(MessageType.LOCATION.getCode())) {
                        // Device location cannot be saved if the device, for which the
                        // location has been received, does not exists in the database. Mark
                        // such a location message as invalid.
                        if (!deviceExists) {
                            message.setMessageContent(finalMessage
                                    .append(DEVICE_LOCATION_MESSAGE)
                                    .append(AT_DEVICE_MESSAGE).append(deviceId)
                                    .append(AT_LOCATION_MESSAGE).append(rawMessageContent)
                                    .append(WITH_DEVICE_MESSAGE).append(deviceId)
                                    .append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE)
                                    .toString());
                            message.setMessageType(MessageType.INVALID.toString());

                        }
                        else {
                            Location deviceLocation = locationService.findByDeviceId(deviceId);

                            processedPayload = rawMessage.getPayload().split(delims);
                            // If location for a device already exists then update the new
                            // location
                            if (deviceLocation != null) {
                                deviceLocation.setlatitude(Double.parseDouble(processedPayload[0]));
                                deviceLocation.setLongitude(Double.parseDouble(processedPayload[1]));
                                if (processedPayload.length > 2) {
                                    deviceLocation.setAltitude(Double.parseDouble(processedPayload[2]));
                                }
                                else {
                                    deviceLocation.setAltitude(22.11); // Hard Coded if
                                }															// there is no
                                // Altitude in our
                                // String

                                try {
                                    locationService.updateLocation(deviceLocation);
                                }
                                catch (DataAccessException e) {
                                    logger.warn("Location could not be updated in the database: {}", e);
                                }
                            }
                            // Save the location for a device which previously had no
                            // location stored for it
                            else {
                                try {
                                    deviceLocation = new Location();
                                    deviceLocation.setDeviceId(deviceId);
                                    deviceLocation.setlatitude(Double.parseDouble(processedPayload[0]));
                                    deviceLocation.setLongitude(Double.parseDouble(processedPayload[1]));
                                    deviceLocation.setAltitude(Double.parseDouble(processedPayload[2]));
                                    locationService.saveLocation(deviceLocation);
                                }
                                catch (DataAccessException e) {
                                    logger.warn("Location could not be saved in the database: {}", e);
                                }
                            }

                            message.setMessageContent(finalMessage
                                    .append(DEVICE_LOCATION_MESSAGE)
                                    .append(AT_DEVICE_MESSAGE).append(deviceId)
                                    .append(AT_LOCATION_MESSAGE).append(rawMessageContent)
                                    .toString());
                        }

                    }

                    /**
                     * ************************** STATUS MESSAGES
                     * ****************************************
                     */
                    else {
                        if (messageType.equals(MessageType.STATUS.getCode())) {
                            statusType = rawMessageContent;

                            if (statusType.equals(StatusType.ON.getCode())) {
                                // If the device, for which the ON status has been received,
                                // does not
                                // exist in the database then it means a new device has been
                                // added to the network. Save the new device to the database by
                                // setting it's status as ON.
                                if (!deviceExists) {
                                    // isDeviceTurnedOn = true;
                                    device = new Device();
                                    device.setDeviceId(deviceId);
                                    device.setNetworkId(networkId);
                                    device.setDeviceStatus(StatusType.getValue(statusType).name());
                                    device.setDeviceType(DeviceType.getValue(deviceType).name());
                                    device.setLastAlertTime(new Date());

                                    finalMessage.append("Device does not exist: New device turned on. Device info is saved to db - ");
                                    try {
                                        deviceService.saveDevice(device);
                                        deviceExists = true;
                                        logger.info("New Device with id: {} is saved to DB!!!", deviceId);
                                    }
                                    catch (DataAccessException e) {
                                        logger.warn("New Device with id: {} could not be saved to DB: {}", deviceId, e);
                                    }
                                }

                                else {

                                    device.setDeviceStatus(StatusType.ON.toString());
                                    device.setLastAlertTime(new Date());

                                    // If the device, for which the ON status has been received,
                                    // exist in the database then update the device status to
                                    // ON.
                                    try {
                                        deviceService.updateDevice(device);
                                        logger.info("Device status with id: {}  is updated to ON!!", deviceId);
                                    }
                                    catch (DataAccessException e) {
                                        logger.warn("Status of Device with id: {} could not be updated to ON: {}", deviceId, e);
                                    }

                                }
                                message.setMessageContent(finalMessage.append(DEVICE_ON_MESSAGE).append(AT_DEVICE_MESSAGE).append(deviceId).toString());

                            }
                            else {
                                if (statusType.equals(StatusType.OFF.getCode())) {
                                    if (!deviceExists) {
                                        finalMessage.append(WITH_DEVICE_MESSAGE).append(deviceId).append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE);
                                    }
                                    else {
                                        device.setDeviceStatus(StatusType.OFF.toString());
                                        device.setLastAlertTime(new Date());
                                        try {
                                            deviceService.updateDevice(device);
                                            logger.info("Device status with id: {}  is updated to OFF!!!", deviceId);
                                        }
                                        catch (DataAccessException e) {
                                            logger.warn("Status of Device with id: {} could not be updated to OFF: {}", deviceId, e);
                                        }

                                    }
                                    message.setMessageContent(finalMessage.append(DEVICE_OFF_MESSAGE).append(AT_DEVICE_MESSAGE).append(deviceId).toString());

                                }
                                else {
                                    if (statusType.equals(StatusType.SLEEP.getCode())) {

                                        if (!deviceExists) {
                                            finalMessage.append(WITH_DEVICE_MESSAGE).append(deviceId)
                                                    .append(DOES_NOT_EXIST_MESSAGE)
                                                    .append(DISCARD_MESSAGE);
                                        }
                                        else {
                                            device.setDeviceStatus(StatusType.SLEEP.toString());
                                            device.setLastAlertTime(new Date());
                                            try {
                                                deviceService.updateDevice(device);
                                                logger.info("Device status with id: {} is updated to SLEEP!!!", deviceId);
                                            }
                                            catch (DataAccessException e) {
                                                logger.warn("Status of Device with id: {} could not be updated to SLEEP: {}", deviceId, e);
                                            }

                                        }
                                        message.setMessageContent(finalMessage.append(DEVICE_SLEEP_MESSAGE).append(AT_DEVICE_MESSAGE).append(deviceId).toString());

                                    }
                                    else {
                                        if (statusType.equals(StatusType.DEBUG.getCode())) {

                                            if (!deviceExists) {
                                                finalMessage.append(WITH_DEVICE_MESSAGE).append(deviceId).append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE);
                                            }
                                            else {
                                                device.setDeviceStatus(StatusType.DEBUG.toString());
                                                device.setLastAlertTime(new Date());
                                                try {
                                                    deviceService.updateDevice(device);
                                                    logger.info("Device status with id: {} is updated to DEBUG!!! ", deviceId);
                                                }
                                                catch (DataAccessException e) {
                                                    logger.warn("Status of Device with id: {} could not be updated to DEBUG: {}", deviceId, e);
                                                }

                                            }
                                            message.setMessageContent(finalMessage.append(DEVICE_DEBUG_MESSAGE).append(AT_DEVICE_MESSAGE).append(deviceId).toString());

                                        }
                                    }
                                }
                            }
                        }

                        /**
                         * ************************************** BATTERY
                         * MESSAGE
                         * *************************************************************
                         */
                        // Here is the Battery Code and we need to perform Calculation over
                        // here.
                        else {
                            if (messageType.equals(MessageType.BATTERY.getCode())) {
                                if (!deviceExists) {
                                    finalMessage.append(WITH_DEVICE_MESSAGE).append(deviceId).append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE);
                                }
                                else {
                                    // Split the raw message with Comma if exists
                                    System.out.println("Raw Message Content ................... " + rawMessageContent);
                                    if (rawMessageContent.contains(",")) {
                                        String[] rawMessageArray = rawMessageContent.split(",");
                                        rawMessageContent = rawMessageArray[0];

                                        if (rawMessageArray.length > 1) {
                                            String explodeChar = rawMessageArray[1];
                                            if (explodeChar.equals("E")) {
                                                device.setExplodable(true);
                                            }
                                        }
                                    }

                                    double rawMessageContentDouble = 0;
                                    try {
                                        rawMessageContentDouble = Double.parseDouble(rawMessageContent);
                                    }
                                    catch (Exception e) {
                                        //rawMessageContentDouble = 0;
                                    }

                                    if (rawMessageContentDouble > 0) {
                                        double voltage = (rawMessageContentDouble * 5) / 4095;
                                        // Max = 4
                                        // Min = 3.6
                                        // 11:64:D:0084:B:3202
                                        System.out.println("Calculated Voltage : " + voltage);

                                        if (voltage > 3.8) {
                                            device.setDeviceBattery(3);
                                            message.setBatteryStatus("High");
                                            finalMessage.append(HIGH_BATTERY_MESSAGE).toString();
                                        }
                                        else {
                                            if (voltage > 3.7) {
                                                device.setDeviceBattery(2);
                                                message.setBatteryStatus("Medium");
                                                finalMessage.append(MEDIUM_BATTERY_MESSAGE).toString();
                                            }
                                            else {
                                                device.setDeviceBattery(1);
                                                message.setBatteryStatus("Low");
                                                finalMessage.append(LOW_BATTERY_MESSAGE).toString();
                                            }
                                        }

                                        try {
                                            device.setLastAlertTime(new Date());
                                            deviceService.updateDevice(device);
                                        }
                                        catch (DataAccessException e) {
                                            logger.warn("Battery level for device Id: {} could not be saved to the database: {}", deviceId, e);
                                        }

                                        // Update Status of all other Devices
                                        lstInvactiveDevice = findInactiveSensors();
                                        for (Device device2 : lstInvactiveDevice) {
                                            System.out.println("Inactive Devices : " + device2.getDeviceId());
                                            device2.setDeviceStatus("OFF");
                                            device2.setDeviceBattery(0);
                                            deviceService.updateDevice(device2);
                                        }
                                    }

                                }
                                message.setMessageContent(finalMessage.append(BATTERY_MESSAGE).append(rawMessageContent).append(AT_DEVICE_MESSAGE).append(deviceId).toString());
                            }

                            else {
                                if (messageType.equals(MessageType.SENSITIVITY.getCode())) {
                                    if (!deviceExists) {
                                        finalMessage.append(WITH_DEVICE_MESSAGE).append(deviceId)
                                                .append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE);
                                    }
                                    else {
                                        device.setDeviceSensitivity(Integer.parseInt(rawMessageContent));
                                        try {
                                            deviceService.saveDevice(device);
                                        }
                                        catch (DataAccessException e) {
                                            logger.warn("Sensitivity for device Id: {} could not be saved to the database: {}", deviceId, e);
                                        }
                                    }
                                    message.setMessageContent(finalMessage.append(SENSITIVITY_MESSAGE)
                                            .append(rawMessage.getPayload()).append(AT_DEVICE_MESSAGE)
                                            .append(deviceId).toString());
                                }

                                else {
                                    if (messageType.equals(MessageType.LINK_QUALITY.getCode())) {
                                        if (!deviceExists) {
                                            finalMessage.append("Device with id: ").append(deviceId)
                                                    .append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE);
                                        }
                                        else {
                                            if (Integer.parseInt(rawMessageContent) < 50) {
                                                finalMessage.append(WEAK_LINK_QUALITY_MESSAGE).toString();
                                            }

                                            device.setDeviceLinkQualityIndicator(Integer.parseInt(rawMessage.getPayload()));
                                            try {
                                                deviceService.saveDevice(device);
                                            }
                                            catch (DataAccessException e) {
                                                logger.warn("Link Quality for device Id: {} could not be saved to the database: {}", deviceId, e);
                                            }
                                        }
                                        message.setMessageContent(finalMessage.append(LINK_QUALITY_MESSAGE)
                                                .append(rawMessage.getPayload()).append(AT_DEVICE_MESSAGE)
                                                .append(deviceId).toString());
                                    }

                                    else {
                                        if (messageType.equals(MessageType.SIGNAL_STRENGTH.getCode())) {
                                            if (!deviceExists) {
                                                finalMessage.append("Device with id: ").append(deviceId)
                                                        .append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE);
                                            }
                                            else {
                                                if (Integer.parseInt(rawMessageContent) > 70) {
                                                    finalMessage.append(POOR_SIGNAL_STRENGTH_MESSAGE)
                                                            .toString();
                                                }

                                                device.setDeviceSignalStrength(Integer
                                                        .parseInt(rawMessageContent));

                                                try {
                                                    deviceService.saveDevice(device);
                                                }
                                                catch (DataAccessException e) {
                                                    logger.warn("Signal strength for device Id: {} could not be saved to the database: {}", deviceId, e);
                                                }
                                            }
                                            message.setMessageContent(finalMessage.append(SIGNAL_STRENGTH_MESSAGE).append(rawMessageContent).append(AT_DEVICE_MESSAGE).append(deviceId).toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Heartbeat is not used at this point but may be used in future
		/*
             * else if
             * (messageType.equals(MessageType.HEARTBEAT.getMessageTypeCode())) { if
             * (!deviceExists) {
             * finalMessage.append(WITH_DEVICE_MESSAGE).append(deviceId)
             * .append(DOES_NOT_EXIST_MESSAGE).append(DISCARD_MESSAGE); }
             * message.setMessageContent(finalMessage.append(HEARTBEAT_MESSAGE)
             * .append(AT_DEVICE_MESSAGE).append(deviceId).toString()); }
             */
            if (deviceExists) {
                try {
                    // Save the message if the device exists in the DB from which
                    // the message has been received otherwise discard it
                    messagingService.saveMessage(message);
                }
                catch (DataAccessException e) {
                    logger.error("Message could not be saved to the database: " + e);
                }
            }

            // Add inactive Devices
            String invactiveDevicesMessage = "";
            if (lstInvactiveDevice != null && !lstInvactiveDevice.isEmpty()) {
                for (Device invactdevice : lstInvactiveDevice) {
                    invactiveDevicesMessage = invactiveDevicesMessage + invactdevice.getDeviceId() + ",";
                }
            }
            message.setInactiveDevices(invactiveDevicesMessage);

            // Send message to clients connected through browsers after saving the
            // message to the DB or discarding it if the device does not exist from
            // which the message has been received
            messageProducerService.broadcastMessageToClients(message);
        }

    }

    /** 
     * This function checks the Alert validity by comparing Threshold and Pulse from 
     * message and Application mode values. <br /> 
     * 
     * If Threshold and Pulse in Message Payload are greater or equal to the values in 
     * DB according to selected Application Mode then display Alert on C2I. 
     * @param rawMessage
     * @return 
     */
    public boolean checkAlertValidityByThresholdAndPulse(RawMessage rawMessage) {
        boolean returnValue = false;
        if (rawMessage != null) {
            // Get C2I Device (for Application Mode) 
            Device c2iDevice = deviceService.findByDeviceId("0");
            if (c2iDevice != null && c2iDevice.getApplMode() != null) {
                Integer applModeId = c2iDevice.getApplMode();
                ApplMode applMode = applModeService.findById(applModeId);

                if (applMode != null) {
                    Integer applModethreshold = applMode.getThreshold();
                    System.out.println("Application Mode Threshold : " + applModethreshold);
                    
                    Integer applModepulse = applMode.getPulse();
                    System.out.println("Application Mode Pulse : " + applModepulse);
                    
                    String payLoad = rawMessage.getPayload();
                    String[] payLoadArr = payLoad.split(" ");

                    if (payLoadArr.length >= 4) {
                        String messageThreshold = payLoadArr[1];
                        System.out.println("Message Threshold : " + messageThreshold);
                        
                        String messagePulse = payLoadArr[5];
                        System.out.println("Message Pulse : " + messagePulse);
                        
                        if( messageThreshold != null ) {
                            Integer messageThresholdInt = Integer.parseInt(messageThreshold);
                            Integer messagePulseInt = Integer.parseInt(messagePulse);
                            
                            // If Threshold and Pulse in Message is greater than Application Mode then 
                            // display Alert on C2I
                            if( messageThresholdInt >= applModethreshold && messagePulseInt >= applModepulse ) {
                                returnValue = true; 
                            }
                            else {
                                returnValue = false; 
                            }
                        }
                    }

                }

            }
        }

        return returnValue;
    }

    /**
     * Method for converting messages received from the devices as a comma
     * separated string into RawMessage
     *
     * @param consumedMessage Message received from the device in the form of a
     * comma separated string
     *
     */
    private RawMessage createRawMessage(String consumedMessage) {
        // 11:26:P:0099:A:03
        // Network ID : Device ID : Device Type : Mac Add : Message
        // Type : PDU
        String delims = "[:]";
        String[] rawMessageProperties;
        rawMessageProperties = consumedMessage.split(delims);
        RawMessage rawMessage = new RawMessage();
        rawMessage.setNetworkId(rawMessageProperties[0]);
        rawMessage.setDeviceId(rawMessageProperties[1]);
        rawMessage.setDeviceType(rawMessageProperties[2]);
        rawMessage.setMacAdd(rawMessageProperties[3]);
        // rawMessage.setBatteryStatus(rawMessageProperties[4]);
        rawMessage.setMessageType(rawMessageProperties[4]);
        rawMessage.setPayload(rawMessageProperties[5]);

        return rawMessage;
    }

    /**
     * This method generate a list of Devices which haven't received any
     * messages since predescribed hours limit. which means devices battery is
     * empty
     *
     * @return
     */
    private List<Device> findInactiveSensors() {

        List<Device> returnValue = new ArrayList<Device>();

        List<Device> lstDevice = deviceService.getAllDevices();
        Date currentTime = new Date();

        for (Device device2 : lstDevice) {

            if (device2.getDeviceTypeChar() != null && device2.getDeviceTypeChar().equals("S") && device2.getDeviceStatus() != null && device2.getDeviceStatus().equals("ON")) {
                Date deviceDate = device2.getLastAlertTime();
                if (deviceDate != null) {
                    long diff = currentTime.getTime() - deviceDate.getTime();

                    long diffHours = diff / (60 * 60 * 1000) % 24;

                    if (diffHours > HOURS_HEART_BEAT_INTERVAL) {
                        returnValue.add(device2);
                    }
                }
                else {
                    returnValue.add(device2);
                }
            }

        }

        return returnValue;

    }
}
