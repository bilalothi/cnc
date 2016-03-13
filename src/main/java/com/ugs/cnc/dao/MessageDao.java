/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * MessageDao.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.dao;

import com.ugs.cnc.dao.IMessageDao;
import com.ugs.cnc.entities.Message;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Message DAO Implementation class
 * 
 * @author Capt Bilal
 */
@Repository
public class MessageDao extends AbstractDao<Message, String> implements
		IMessageDao {

	/**
	 * Constructor for Message DAO
	 * 
	 */
	protected MessageDao() {
		super(Message.class);
	}

	/**
	 * Method to save different kinds of messages from a device to the database
	 * 
	 * @param message
	 *            Message that is to be saved in the database
	 */
	@Override
	public void saveMessage(Message message) {
		saveOrUpdate(message);
	}

	/**
	 * Method for retrieving all messages from the database
	 * 
	 * @return List of all the messages
	 */
	@Override
	public List<Message> getAllMessages() {
		List<Message> alertMessageList = findAll();
		return alertMessageList;
	}

	/**
	 * Method for searching messages by date
	 * 
	 * @param startDate
	 *            starting date limit of messages to be searched
	 * @param endDate
	 *            ending date limit of messages to be searched
	 * @return List of messages with the specified date and time limits
	 * 
	 * */
	@Override
	public List<Message> getMessagesByDate(Date startDate, Date endDate) {
		List<Message> messageList = findByCriteria(Restrictions.between(
				"messageDate", startDate, endDate));
		return messageList;
	}

	/**
	 * Method for searching messages by type from the database
	 * 
	 * @param messageType
	 *            type of messages to be searched
	 * @return List of messages with the specified type of message
	 */
	@Override
	public List<Message> getMessagesByType(String messageType) {
		List<Message> messageList = findByCriteria(Restrictions.eq(
				"messageType", messageType));
		return messageList;
	}

	/**
	 * Method for searching messages by deviceId from the database
	 * 
	 * @param deviceId
	 *            Device Id for the messages to be searched
	 * @return List of messages searched
	 */
	@Override
	public List<Message> getMessagesByDeviceId(String deviceId) {
		List<Message> messageList = findByCriteria(Restrictions.eq(
				"messageDeviceId", deviceId));
		return messageList;
	}

	/**
	 * Method for searching messages by type of message and date, from the
	 * database
	 * 
	 * @param startDate
	 *            starting date limit of messages to be searched
	 * @param endDate
	 *            ending date limit of messages to be searched
	 * @param messageType
	 *            type of messages to be searched
	 * @return List of messages with the specified message type and date limits
	 */
	@Override
	public List<Message> getMessagesByTypeAndDate(String messageType,
			Date startDate, Date endDate) {
		Criterion dateCriteria = Restrictions.between("messageDate", startDate,
				endDate);
		Criterion messageTypeCriteria = Restrictions.eq("messageType",
				messageType);
		List<Message> messageList = findByCriteria(Restrictions.conjunction()
				.add(dateCriteria).add(messageTypeCriteria));
		return messageList;
	}

}
