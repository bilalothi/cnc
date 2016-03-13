/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * AlertController.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.2
 */

package com.ugs.cnc.controller;

import com.ugs.cnc.entities.Alert;
import com.ugs.cnc.entities.Message;
import com.ugs.cnc.entities.User;
import com.ugs.cnc.enums.AlertState;
import com.ugs.cnc.enums.DeviceType;
import com.ugs.cnc.enums.MessageType;
import com.ugs.cnc.service.IAlertService;
import com.ugs.cnc.service.IMessageProducer;
import com.ugs.cnc.service.IMessagingService;
import com.ugs.cnc.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring MVC annotated controller for alert CRUD operations
 * 
 * @author Capt Bilal
 */

@RequestMapping("/alert")
@Controller
public class AlertController {

	private static final Logger logger = LoggerFactory
			.getLogger(AlertController.class);

	@Autowired
	private IAlertService alertService;
	@Autowired
	private IMessagingService messagingService;
	@Autowired
	private IMessageProducer messageProducerService;
	@Autowired
	private IUserService userService;

	private static final String ALERT_DEVICE_ID_MESSAGE = "Alerts from Device Id: ";
	private static final String ALERT_ACCEPTED_MESSAGE = " have been accepted by Remote User: ";
	private static final String ALERT_ASSIGNED_BY_MESSAGE = ", by the Remote User: ";
	private static final String ALERT_ASSIGNED_TO_MESSAGE = " have been assigned to Field User: ";
	private static final String ALERT_RESOLVED_MESSAGE = " have been resolved by Field User: ";

	/**
	 * Method to search alerts by device Id
	 * 
	 * @param deviceID
	 *            device Id for the alerts to be searched
	 * @return Map containing the alerts searched
	 */
	@RequestMapping(value = "/searchByDeviceId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByDeviceId(
			@RequestParam("alertDeviceIdSearchParam") String deviceId) {

		List<Alert> alertList = alertService.findByDeviceId(deviceId);
		Map<String, Object> responseMap = new HashMap<String, Object>();

		if (alertList.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("alerts", alertList);
		return responseMap;
	}

	/**
	 * Method to search alerts by alert Id
	 * 
	 * @param alertId
	 *            alert Id for the alerts to be searched
	 * @return Map containing the alerts searched
	 */
	@RequestMapping(value = "/searchById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByAlertId(
			@RequestParam("alertIdSearchParam") String alertId) {

		Alert alert = alertService.findByAlertId(alertId);
		Map<String, Object> responseMap = new HashMap<String, Object>();

		if (alert == null) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("alert", alert);
		return responseMap;
	}

	/**
	 * Method to search alerts by date and state
	 * 
	 * @param alertState
	 *            state of alerts to be searched
	 * @param startDate
	 *            start date as lower limit of search
	 * @param endDate
	 *            end date as upper limit of search
	 * @return Map containing the alerts searched
	 */
	@RequestMapping(value = "/searchByStateAndDate", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByAlertStateAndDate(
			@RequestParam("alertState") String alertState,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) throws ParseException {

		Map<String, Object> responseMap = new HashMap<String, Object>();

		Date startingDate = new Date();
		Date endingDate = new Date();
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");

		try {
			startingDate = formatter.parse(startDate);
			endingDate = formatter.parse(endDate);
		} catch (ParseException e) {
			logger.info("Problem with formatting date in alert controller: ", e);
		}

		List<Alert> alertList = alertService.findByStateAndDate(alertState,
				startingDate, endingDate);
		if (alertList.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;

		}

		responseMap.put("found", true);
		responseMap.put("alerts", alertList);
		return responseMap;
	}

	/**
	 * Method to search alerts by State
	 * 
	 * @param alertState
	 *            state for alerts to be searched
	 * @return Map containing the alerts searched
	 */
	@RequestMapping(value = "/searchByState", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchAlertsByStatus(
			@RequestParam("alertStateSearchParam") String alertState) {
		List<Alert> alertList = alertService.findByState(alertState);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (alertList.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("alerts", alertList);

		return responseMap;
	}

	/**
	 * Method to search alerts assigned to a user
	 * 
	 * @param username
	 *            user associated with assigned alerts
	 * @return Map containing the alerts searched
	 */
	@RequestMapping(value = "/searchAssignedAlerts", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchAlertsAssignedToUser(
			@RequestParam("usernameParam") String username) {
		List<Alert> alertList = alertService.findAlertsByStateAndUser(
				AlertState.ASSIGNED, username);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (alertList.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("alerts", alertList);

		return responseMap;
	}

	/**
	 * Method to update alert state
	 * 
	 * @param alert
	 *            alert whose information is to be updated
	 * @return string denoting a success or failure for the update operation
	 */

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(@RequestParam("alertId") String alertId,
			@RequestParam("updateAlertState") String alertState) {

		Alert alertToUpdate = alertService.findByAlertId(alertId);

		if (alertToUpdate == null) {
			return "failure";
		}

		alertToUpdate.setAlertState(alertState);

		alertService.updateAlertState(alertToUpdate);
		return "success";

	}

	/**
	 * Method for a C2I ONSITE USER or COMMANDER to accept an alert. The alert
	 * accepted by a user then becomes his responsibility and he is required to
	 * assign it to a field user so that the field user can take some action
	 * against the threat that generated the alert.
	 * 
	 * @param deviceId
	 *            deviceId from which the alert has been generated
	 * @param assignedBy
	 *            user that has accepted the alert and is now responsible for
	 *            assigning it to a Field User
	 * 
	 * @return string denoting a success or failure for the accept alert
	 *         operation
	 */

	@RequestMapping(value = "/acceptAlert", method = RequestMethod.POST)
	@ResponseBody
	public String acceptAlert(
			@RequestParam("receivedAlertDeviceId") String deviceId,
			@RequestParam("assignedBy") String assignedBy) {

		List<Alert> alertList = alertService.findAlertsByStateAndDeviceId(
				AlertState.ACTIVE, deviceId);

		if (alertList.isEmpty()) {
			return "failure";
		}

		User acceptUser = userService.findByUserId(assignedBy);
		StringBuilder alertAcceptedMessage = new StringBuilder();
		Date alertAcceptedDate = generateCurrentDateAndTime();

		for (Alert alertToUpdate : alertList) {
			alertToUpdate.setAlertAcceptedDate(alertAcceptedDate);
			alertToUpdate.setAssignedBy(assignedBy);
			alertToUpdate.setAlertState(AlertState.ACCEPTED.toString());
			alertService.updateAlertStatus(alertToUpdate);
		}
		Message message = new Message();
		message.setMessageDeviceType(DeviceType.PIR_SENSOR_NODE.toString()); // Have to do with comparison of specific Sensor Type
		message.setMessageContent(alertAcceptedMessage
				.append(ALERT_DEVICE_ID_MESSAGE).append(deviceId)
				.append(ALERT_ACCEPTED_MESSAGE)
				.append(acceptUser.getFullName()).toString());
		message.setMessageDeviceId(deviceId);
		message.setMessageType(MessageType.ALERT_ACCEPTED.name());
		message.setMessageDate(alertAcceptedDate);
		// This data is set so some actions such as blinking tabs and populating
		// data
		// can be done for the remote users
		message.setMessageRawData(assignedBy);
		messageProducerService.broadcastMessageToClients(message);
		return "success";

	}

	/**
	 * Method for a C2I ONSITE USER or COMMANDER to assign an alert. The user
	 * who is assigned the alert, becomes responsible for taking some action
	 * against the threat that generated the alert.
	 * 
	 * @param deviceId
	 *            deviceId from which the alert has been generated
	 * @param assignedTo
	 *            user that the alert has been assigned to
	 * @param assignedBy
	 *            user that has assigned the alert
	 * @param assignComments
	 *            comments pertaining to alert assignment
	 * @return string denoting a success or failure for the assign alert
	 *         operation
	 */

	@RequestMapping(value = "/assignAlert", method = RequestMethod.POST)
	@ResponseBody
	public String assignAlert(
			@RequestParam("receivedAlertDeviceId") String deviceId,
			@RequestParam("assignedTo") String assignedTo,
			@RequestParam("assignedBy") String assignedBy,
			@RequestParam("assignComments") String assignComments) {

		List<Alert> alertList = alertService.findAlertsToAssign(
				AlertState.ACCEPTED, deviceId, assignedBy);

		if (alertList.isEmpty()) {
			return "failure";
		}

		StringBuilder alertAssignedMessage = new StringBuilder();
		User assignedByUser = userService.findByUserId(assignedBy);
		User assignedToUser = userService.findByUserId(assignedTo);

		Date alertAssignedDate = generateCurrentDateAndTime();

		for (Alert alertToUpdate : alertList) {
			alertToUpdate.setAlertAssignedDate(alertAssignedDate);
			alertToUpdate.setAssignedTo(assignedTo);
			alertToUpdate.setAlertState(AlertState.ASSIGNED.toString());
			alertToUpdate.setAssignComments(assignComments);
			alertService.updateAlertStatus(alertToUpdate);
		}

		Message message = new Message();
		message.setMessageDeviceType(DeviceType.PIR_SENSOR_NODE.toString()); // Have to do with comparison of specific Sensor Type
		message.setMessageContent(alertAssignedMessage
				.append(ALERT_DEVICE_ID_MESSAGE).append(deviceId)
				.append(ALERT_ASSIGNED_TO_MESSAGE)
				.append(assignedToUser.getFullName())
				.append(ALERT_ASSIGNED_BY_MESSAGE)
				.append(assignedByUser.getFullName()).toString());
		message.setMessageDeviceId(deviceId);
		message.setMessageType(MessageType.ALERT_ASSIGNED.toString());
		message.setMessageDate(alertAssignedDate);
		/*
		 * This data is set so some actions such as blinking tabs and populating
		 * data can be done for the user that has been assigned the alert
		 */
		message.setMessageRawData(assignedTo);

		messageProducerService.broadcastMessageToClients(message);
		return "success";

	}

	/**
	 * Method for a C2I FIELD USER to mark an alert as resolved after he has
	 * taken some action against the threat that generated the alert
	 * 
	 * @param deviceId
	 *            deviceId from which the alert has been generated
	 * @param resolvedBy
	 *            user that has resolved the alert
	 * @param resolveComments
	 *            comments pertaining to alert resolution
	 * @return string denoting a success or failure for the resolve alert
	 *         operation
	 */

	@RequestMapping(value = "/resolveAlert", method = RequestMethod.POST)
	@ResponseBody
	public String resolveAlert(
			@RequestParam("assignedAlertDeviceId") String deviceId,
			@RequestParam("resolveComments") String resolveComments,
			@RequestParam("resolvedBy") String resolvedBy) {

		List<Alert> alertList = alertService.findAlertsToResolve(
				AlertState.ASSIGNED, deviceId, resolvedBy);

		if (alertList.isEmpty()) {
			return "failure";
		}

		User resolveUser = userService.findByUserId(resolvedBy);

		StringBuilder alertResolvedMessage = new StringBuilder();
		Date alertResolvedDate = generateCurrentDateAndTime();

		for (Alert alertToUpdate : alertList) {
			alertToUpdate.setAlertResolvedDate(alertResolvedDate);
			alertToUpdate.setAlertState(AlertState.RESOLVED.toString());
			alertToUpdate.setResolveComments(resolveComments);
			alertService.updateAlertStatus(alertToUpdate);
		}

		Message message = new Message();
		message.setMessageDeviceType(DeviceType.PIR_SENSOR_NODE.toString()); //// Have to do with comparison of specific Sensor Type
		message.setMessageContent(alertResolvedMessage
				.append(ALERT_DEVICE_ID_MESSAGE).append(deviceId)
				.append(ALERT_RESOLVED_MESSAGE)
				.append(resolveUser.getFullName()).toString());
		message.setMessageDeviceId(deviceId);
		message.setMessageType(MessageType.ALERT_RESOLVED.toString());
		message.setMessageDate(alertResolvedDate);

		messageProducerService.broadcastMessageToClients(message);
		return "success";

	}

	/**
	 * Method to generate the current formatted date
	 * 
	 * @return current date
	 */
	private Date generateCurrentDateAndTime() {
		Date currentDate = new Date();
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
		String currentDateString = formatter.format(currentDate);

		try {
			Date formattedDate = formatter.parse(currentDateString);
			return formattedDate;
		} catch (ParseException e) {
			logger.info("Problem with formatting date: {}", e);
		}

		return currentDate;

	}

}
