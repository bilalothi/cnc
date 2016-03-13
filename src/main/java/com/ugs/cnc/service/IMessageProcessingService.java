/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IMessageProducer.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

/**
 * Interface for message processing functionality
 *
 * @author Capt Bilal
 */
public interface IMessageProcessingService {

	void processAndSendMessage(String consumedMessage);

}
