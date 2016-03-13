/*
 * MessageController.java
 * 
 * Author: Capt Bilal 
 */

package com.ugs.cnc.controller;

import com.ugs.cnc.entities.Message;
import com.ugs.cnc.service.IMessagingService;

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
 * Spring MVC annotated controller for message CRUD operations
 * 
 * @author Capt Bilal
 */

@RequestMapping("/message")
@Controller
public class MessageController {

	private static final Logger logger = LoggerFactory
			.getLogger(MessageController.class);

	@Autowired
	private IMessagingService messageService;

	/**
	 * Method to search messages by device Id
	 * 
	 * @param deviceId
	 *            device Id for the messages to be searched
	 * @return Map containing the messages searched
	 */
	@RequestMapping(value = "/searchByDeviceId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByDeviceId(
			@RequestParam("messageDeviceIdSearchParam") String deviceId) {

		List<Message> messageList = messageService.findByDeviceId(deviceId);
		Map<String, Object> responseMap = new HashMap<String, Object>();

		if (messageList.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("messages", messageList);
		return responseMap;
	}

	/**
	 * Method to search messages by date and type
	 * 
	 * @param messageType
	 *            type of messages to be searched
	 * @param startDate
	 *            start date as lower limit of search
	 * @param endDate
	 *            end date as upper limit of search
	 * @return Map containing the messages searched
	 */
	@RequestMapping(value = "/searchByTypeAndDate", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByMessageTypeAndDate(
			@RequestParam("messageType") String messageType,
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
			logger.info("Problem with formatting date in message controller: ",
					e);
		}
		List<Message> messageList = messageService.findByTypeAndDate(
				messageType, startingDate, endingDate);
		if (messageList.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;

		}

		responseMap.put("found", true);
		responseMap.put("messages", messageList);
		return responseMap;
	}

}
