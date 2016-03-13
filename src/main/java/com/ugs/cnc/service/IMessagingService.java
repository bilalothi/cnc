/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IMessageService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import com.ugs.cnc.entities.Message;

import java.util.Date;
import java.util.List;

/**
 * Interface to provide message CRUD operations and message processing
 *
 * @author Capt Bilal
 */
public interface IMessagingService {

    //AlertMessage findByMessageId(String messageId);
    void saveMessage(Message message);
    List<Message> getAllMessages();
    Message findById(String messageId);
    List<Message> findByDeviceId(String deviceId);
    List<Message> findByType(String messageType);
    List<Message> findByDate(Date startDate, Date endDate);
    List<Message> findByTypeAndDate(String messageType, Date startDate, Date endDate);
}
