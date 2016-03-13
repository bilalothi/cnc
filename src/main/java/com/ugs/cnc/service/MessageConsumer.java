/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * MessageConsumer.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

//import org.codehaus.jackson.map.JsonMappingException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//import org.springframework.amqp.support.converter.JsonMessageConverter;
//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.ObjectMapper;

/**
 * Message consumption service for acquiring messages in the form of raw data
 * from the message exchange gateway queue
 * 
 * @author Capt Bilal
 */
public class MessageConsumer implements MessageListener {

	private static final Logger logger = LoggerFactory
			.getLogger(MessageConsumer.class);

	private MessageProcessingService messageProcessingService;

	public void setMessageProcessingService(MessageProcessingService messageProcessingService) {
        this.messageProcessingService = messageProcessingService;
    }
	
	@Override
	public void onMessage(Message message) {

		byte[] body = message.getBody();
		String consumedMessage = new String(body);
		System.out.println("Consumed Message ------------------------------------------------------------------------------------   : " + consumedMessage);
		// JsonMessageConverter jmc = new JsonMessageConverter();
		// RawMessage rawMessage = (RawMessage)jmc.fromMessage(message);
		try {
			messageProcessingService.processAndSendMessage(consumedMessage);
			
			// Push the Message to Server 
			//pushMessageToServer(consumedMessage);

		} catch (Exception e) {
			logger.info("Receiver exception: " + e);
			e.printStackTrace();
		}
		
		
		
	}
	
	public void pushMessageToServer(String message) {
		Connection connectionRabbit = null; 
		Channel channel = null; 
		try {
			System.out.println("11111111111111111111-------------------------------------------------------------------------------");
		    ConnectionFactory factory = new ConnectionFactory();
		    
		    factory.setHost("192.168.1.6");
		    
		    System.out.println("2222222222222222222222 -------------------------------------------------------------------------------");
		    
		    connectionRabbit = factory.newConnection();
		    if( connectionRabbit != null ){
		    	System.out.println("333333333333333333333333 -------------------------------------------------------------------------------");
			    channel = connectionRabbit.createChannel();
			    channel.queueBind("ugsInboundQueue", "amq.direct", "ugsInboundQueue");

			    //channel.queueDeclare("ugsInboundQueueServer", false, false, false, "ugsInboundQueue");
			    channel.basicPublish("amq.direct", "ugsInboundQueue", null, message.getBytes());
			    System.out.println(" [x] Sent '" + message + "'");
		    }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		finally {
			try {
				connectionRabbit.close();
				channel.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		
	}

}