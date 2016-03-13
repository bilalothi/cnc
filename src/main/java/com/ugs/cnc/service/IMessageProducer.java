/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IMessageProducer.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import com.ugs.cnc.entities.Message;

/**
 * Interface for message production functionality
 *
 * @author Capt Bilal
 */
public interface IMessageProducer {

	void broadcastMessageToClients(Message message);

}
