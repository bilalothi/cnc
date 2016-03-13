/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * MessageProducer.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.ugs.cnc.entities.Message;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Message producing service for sending processed messages to a queue from
 * which the clients/browsers retrieve messages
 * 
 * @author Capt Bilal
 */
@Service("messageProducerService")
public class MessageProducer implements IMessageProducer {

	private static final Logger logger = LoggerFactory
			.getLogger(MessageProducer.class);

	private static final String EXCHANGE_NAME = "amq.topic";

	/**
	 * Method for converting messages into Json and publishing them to all the
	 * topic type queues with a routing key so that browser clients subscribed
	 * to those queues can retrieve these messages and display them on the C2I
	 * console.
	 * 
	 * @param message
	 *            Processed message which is to be published to the browser
	 *            client queues
	 */
	public void broadcastMessageToClients(Message message) {

		logger.debug("Producing message");

		ObjectMapper mapper = new ObjectMapper();
		String jsonMessage = null;
		try {
			jsonMessage = mapper.writeValueAsString(message);
			logger.debug("JSON message about to be  published to the queue: {}", jsonMessage);
		} 
		catch (JsonGenerationException e) {
			logger.warn("JSON Generation issue: JSON message could not be  published to the queue: {}", e);
		} 
		catch (JsonMappingException e) {
			logger.warn("JSON Mapping issue: JSON message could not be  published to the queue be: {}",e);
		} 
		catch (IOException e) {
			logger.warn("IO issue: JSON message could not be  published to the queue: {}", e);
		}

		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			// factory.setHost("queue-c2i.ugs.sensorflock.com");
			// factory.setHost("192.168.66.150");
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();

			// channel.exchangeDeclare(EXCHANGE_NAME, "topic");

			// RawMessage rawMessage = (RawMessage)jmc.fromMessage(message);
			String routingKey = "browser-clients";

			channel.basicPublish(EXCHANGE_NAME, routingKey, null, jsonMessage.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	/** 
	 * Main method to send message to RabbitMQ... 
	 * @param message
	 * @return
	 */
	public static boolean sendMessage(String message) {
		String QUEUE_NAME = "ugsOutboundQueue";
		Connection connection = null; 
		Channel channel = null; 

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return true; 

	}
	
	public static void main(String args[]) {
		String msg = "11:25:P:0099:1:A:04";
		sendMessage(msg);
		
	}

}
