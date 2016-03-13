/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IMessageDao.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.dao;

import java.util.Date;
import java.util.List;

import com.ugs.cnc.entities.Message;

/**
 * Interface to provide database operations for messages
 * 
 * @author Capt Bilal
 */
public interface IMessageDao extends IAbstractDao<Message, String> {
	void saveMessage(Message message);

	List<Message> getAllMessages();

	List<Message> getMessagesByDate(Date startDate, Date endDate);

	List<Message> getMessagesByType(String messageType);

	List<Message> getMessagesByDeviceId(String deviceId);

	List<Message> getMessagesByTypeAndDate(String messageType, Date startDate,
			Date endDate);

}
