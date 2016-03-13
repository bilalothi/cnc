/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * MessagingService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugs.cnc.dao.IMessageDao;
import com.ugs.cnc.entities.Message;

/**
 * Messaging service for message CRUD operations
 * 
 * @author Capt Bilal
 */
@Service("messagingService")
@Transactional(readOnly = true)
public class MessagingService implements IMessagingService {

	private static final Logger logger = LoggerFactory
			.getLogger(MessagingService.class);

	@Autowired
	private IMessageDao messageDao;

	/**
	 * Service method for saving a message from a device
	 * 
	 * @param message
	 *            the message received from the device after conversion into
	 *            readable text
	 */
	@Override
	@Transactional(readOnly = false)
	public void saveMessage(Message message) {
		try {
			messageDao.saveMessage(message);
		} catch (DataAccessException e) {
			logger.error("Message could not be saved to the database: " + e);

		}
	}

	/**
	 * Service method for retrieving all messages
	 * 
	 * @return list of all the messages
	 */
	@Override
	public List<Message> getAllMessages() {
		return messageDao.findAll();
	}

	/**
	 * Service method for searching messages by date
	 * 
	 * @param startDate
	 *            starting date limit of messages to be searched
	 * @param endDate
	 *            ending date limit of messages to be searched
	 * @return List of messages with the specified date and time limits
	 * 
	 * */
	@Override
	public List<Message> findByDate(Date startDate, Date endDate) {
		return messageDao.getMessagesByDate(startDate, endDate);
	}

	/**
	 * Service method for searching messages by type
	 * 
	 * @param messageType
	 *            type of messages to be searched
	 * @return List of messages with the specified type of message
	 */
	@Override
	public List<Message> findByType(String messageType) {
		return messageDao.getMessagesByType(messageType);
	}

	/**
	 * Service method for searching messages by type of message and date
	 * 
	 * @param startDate
	 *            starting date of messages to be searched
	 * @param endDate
	 *            ending date of messages to be searched
	 * @param messageType
	 *            type of messages to be searched
	 * @return List of messages with the specified message type and date limits
	 */
	@Override
	public List<Message> findByTypeAndDate(String messageType, Date startDate,
			Date endDate) {
		return messageDao.getMessagesByTypeAndDate(messageType, startDate, endDate);
	}

	/**
	 * Service method for searching messages by deviceId
	 * 
	 * @param deviceId
	 *            Device Id for the messages to be searched
	 * @return List of messages with the specified device Id
	 */
	@Override
	public List<Message> findByDeviceId(String deviceId) {
		return messageDao.getMessagesByDeviceId(deviceId);
	}
	
	/**
	 * Service method for searching a message by messageId
	 * 
	 * @param messageId
	 *            Id for the message to be searched
	 * @return message with the specified message Id
	 */
	@Override
	public Message findById(String messageId) {
		return messageDao.findById(messageId);
	}

}
