/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * DeviceController.java
 * 
 * Author: Capt Bilal
 */
package com.ugs.cnc.controller;

import com.ugs.cnc.entities.Device;
import com.ugs.cnc.entities.Location;
import com.ugs.cnc.entities.Network;
import com.ugs.cnc.service.IDeviceService;
import com.ugs.cnc.service.INetworkService;
import com.ugs.cnc.service.LocationService;
import com.ugs.cnc.service.MessageProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring MVC annotated controller for device related CRUD operations
 *
 * @author Capt Bilal
 */
@RequestMapping("/device")
@Controller
public class DeviceController {

    private static final Logger logger = LoggerFactory
            .getLogger(DeviceController.class);

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private INetworkService networkService;

    /**
     * Method to initialize main display page for C2I
     *
     * @param model contains device object for initialization
     * @return dislay corresponding to display.jsp which processes and displays
     * the data that is sent
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showDeviceForm(Model model) {
        model.addAttribute("device", new Device());
        return "display";
    }

    /**
     * Method to save device information
     *
     * @param device to be saved
     * @return string denoting a success or failure for the save operation
     */
    @RequestMapping(value = "/addDevice", method = RequestMethod.POST)
    @ResponseBody
    public String savedevice(Device device) {
        device.setDeviceStatus("OFF");
        String deviceIdStr = device.getDeviceId();
        // MAC 
        int deviceMACInt = 0;
        if (deviceIdStr != null) {
            try {
                deviceMACInt = Integer.parseInt(deviceIdStr);
            }
            catch (Exception e) {

            }
        }
        device.setDevicePhysicalAddress(String.valueOf(deviceMACInt + 20));

        List<Network> lstNetwork = networkService.getAllNetworks();
        if (!lstNetwork.isEmpty()) {
            Network network = lstNetwork.get(0);
            device.setNetworkId(network.getNetworkId());

        }

        // Location 
        Location location = new Location();
        location.setDeviceId(device.getDeviceId());
        location.setlatitude(33.57957);
        location.setLongitude(73.06767);
        location.setAltitude(100D);
        device.setDeviceLocation(location);

        device.setExplodable(Boolean.FALSE);

        if (device.getDeviceType() != null) {
            String type = device.getDeviceType();
            if (type.equals("C2I_SYSTEM")) {
                device.setDeviceTypeChar("C");
            }
            else {
                if (type.equals("PIR_SENSOR_NODE") || type.equals("DUAL_SENSOR_NODE") || type.equals("SEISMIC_SENSOR_NODE")) {
                    device.setDeviceTypeChar("S");
                }
                else {
                    if (type.equals("ROUTER_NODE")) {
                        device.setDeviceTypeChar("R");
                    }
                }
            }
        }

        logger.debug("Inside device save controller method");
        System.out.println("Inside device save controller method");

        Device alreadyExistingDevice = deviceService.findByDeviceId(device.getDeviceId());
        if (alreadyExistingDevice != null) {
            return "failure";
        }
        deviceService.saveDevice(device);
        return "success";
    }

    /**
     * Method to get device information for all the existing devices
     *
     * @return Map containing all the existing devices
     */
    @RequestMapping(value = "/getAllDevices", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllDevices() {

        List<Device> deviceList = deviceService.getAllDevices();
        Map<String, Object> responseMap = new HashMap<String, Object>();

        if (deviceList.isEmpty()) {
            responseMap.put("found", false);
            return responseMap;
        }

        responseMap.put("found", true);
        responseMap.put("devices", deviceList);

        return responseMap;
    }

    /**
     * Method to get device information for all the video camera devices
     *
     * @return Map containing all the video camera devices
     */
    @RequestMapping(value = "/getVideoCameras", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getVideoCameras() {

        List<Device> videoCameraList = deviceService
                .findByDeviceType("VIDEO_CAMERA_NODE");
        Map<String, Object> responseMap = new HashMap<String, Object>();

        if (videoCameraList.isEmpty()) {
            responseMap.put("found", false);
            return responseMap;
        }

        responseMap.put("found", true);
        responseMap.put("devices", videoCameraList);

        return responseMap;
    }

    /**
     * Method to search a device by device id
     *
     * @param deviceId Device Id for the device to be searched
     * @return Map containing the device searched
     */
    @RequestMapping(value = "/searchById", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> searchDeviceById(
            @RequestParam("deviceIdSearchParam") String deviceId) {

        Device searchedDevice = deviceService.findByDeviceId(deviceId);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (searchedDevice == null) {
            responseMap.put("found", false);
            return responseMap;
        }
        responseMap.put("found", true);
        responseMap.put("device", searchedDevice);

        return responseMap;
    }

    /**
     * Method to search devices by Type
     *
     * @param deviceType Type for devices to be searched
     * @return Map containing the devices searched
     */
    @RequestMapping(value = "/searchByType", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchDevicesByType(
            @RequestParam("deviceTypeSearchParam") String deviceType) {
        List<Device> searchedDevices = deviceService.findByDeviceType(deviceType);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (searchedDevices.isEmpty()) {
            responseMap.put("found", false);
            return responseMap;
        }
        responseMap.put("found", true);
        responseMap.put("devices", searchedDevices);

        return responseMap;
    }

    /**
     * Method to search devices by Status
     *
     * @param deviceStatus Status for devices to be searched
     * @return Map containing the devices searched
     */
    @RequestMapping(value = "/searchByStatus", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchDevicesByStatus(@RequestParam("deviceStatusSearchParam") String deviceStatus) {

        List<Device> searchedDevices = deviceService
                .findByDeviceStatus(deviceStatus);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (searchedDevices.isEmpty()) {
            responseMap.put("found", false);
            return responseMap;
        }

        responseMap.put("found", true);
        responseMap.put("devices", searchedDevices);

        return responseMap;
    }

    /**
     * Method to search devices by NetworkId
     *
     * @param deviceNetworkId NetworkId for devices to be searched
     * @return Map containing the devices searched
     */
    @RequestMapping(value = "/searchByNetworkId", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchDevicesByNetworkId(@RequestParam("networkIdSearchParam") String networkId) {
        List<Device> searchedDevices = deviceService
                .findByDeviceNetwork(networkId);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (searchedDevices.isEmpty()) {
            responseMap.put("found", false);
            return responseMap;
        }
        responseMap.put("found", true);
        responseMap.put("devices", searchedDevices);

        return responseMap;
    }

    /**
     * Method to delete a device
     *
     * @param model
     * @param deviceId Device Id for the device to be deleted
     * @return string denoting a success or failure for the delete operation
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteDevice(
            @RequestParam("deviceIdDeleteParam") String deviceId) {

        Device deviceToDelete = deviceService.findByDeviceId(deviceId);

        if (deviceToDelete == null) {
            return "failure";
        }
        deviceService.deleteDevice(deviceId);
        return "success";
    }

    /**
     * Method to update device information
     *
     * @param device Device to be updated
     * @return string denoting a success or failure for the update operation
     */
    @RequestMapping(value = "/updateDevice", method = RequestMethod.POST)
    @ResponseBody
    public String updatedevice(Device device) {

        Device deviceToUpdate = deviceService.findByDeviceId(device
                .getDeviceId());

        if (deviceToUpdate == null) {
            return "failure";
        }
        deviceService.updateDevice(device);
        return "success";
    }

    /**
     * Method to Update a device
     *
     * @param model
     * @param deviceId Device Id for the device to be deleted
     * @return string denoting a success or failure for the delete operation
     */
    @RequestMapping(value = "/updateDeviceStatus", method = RequestMethod.GET)
    @ResponseBody
    public String updateDeviceStatus(
            @RequestParam("deviceIdUpdateParam") String deviceId) {

        Device deviceToUpdate = deviceService.findByDeviceId(deviceId);

        if (deviceToUpdate == null) {
            return "failure";
        }
        deviceToUpdate.setDeviceStatus("OFF");
        deviceService.updateDevice(deviceToUpdate);
        return "success";
    }

    /**
     * Method to Update a device
     *
     * @param model
     * @param deviceId Device Id for the device to be deleted
     * @return string denoting a success or failure for the delete operation
     */
    @RequestMapping(value = "/updateDeviceLocation", method = RequestMethod.GET)
    @ResponseBody
    public String updateDeviceLocation(
            @RequestParam("deviceIdUpdateParam") String deviceId, @RequestParam("deviceLatitudeUpdateParam") String latitude, @RequestParam("deviceLongitudeUpdateParam") String longitude) {

        Device deviceToUpdate = deviceService.findByDeviceId(deviceId);
        if (deviceToUpdate == null) {
            return "failure";
        }

        Double latitudeDouble = 0d;
        Double longiDouble = 0d;

        try {
            latitudeDouble = Double.parseDouble(latitude);
            longiDouble = Double.parseDouble(longitude);
        }
        catch (Exception e) {
            return "failure";
        }

        Location l = deviceToUpdate.getDeviceLocation();
        if (l == null) {
            return "failure";
        }

        l.setLongitude(longiDouble);
        l.setlatitude(latitudeDouble);

        deviceToUpdate.setDeviceLocation(l);

        deviceService.updateDevice(deviceToUpdate);
        return "success";
    }

    /**
     *
     *
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/explode", method = RequestMethod.GET)
    @ResponseBody
    public String explodeMine(
            @RequestParam("deviceIdParam") String deviceId,
            @RequestParam("devicePhysicalAddressParam") String devicePhysicalAddress,
            @RequestParam("networkIdParam") String networkId
    ) {

        System.out.println("Device ID received : " + deviceId);

        MessageProducer.sendMessage(devicePhysicalAddress + ":E:" + networkId + ":" + deviceId);

        // Device deviceToDelete = deviceService.findByDeviceId(deviceId);
        //
        // if (deviceToDelete == null) {
        // return "failure";
        // }
        // deviceService.deleteDevice(deviceId);
        return "success";
    }

}
