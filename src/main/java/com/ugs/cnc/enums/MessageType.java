/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * MessageType.java
 * 
 * Author: Capt Bilal
 * 
 * version 1.0
 */

package com.ugs.cnc.enums;

import com.ugs.cnc.util.EnumMap;
import com.ugs.cnc.util.IEnumValueSupplier;

/**
 * Enum for Message Type
 * 
 * @author Bilal
 */
public enum MessageType implements IEnumValueSupplier{
	//STATUS("0"), 
	STATUS("S"),
	//LOCATION("1"), 
	LOCATION("G"),
	TEMPERATURE("2"), 
	//BATTERY("3"), 
	BATTERY("B"),
	SENSITIVITY("7"), 
	LINK_QUALITY("15"), 
	SIGNAL_STRENGTH("16"), 
	//ALERT("17"), 
	ALERT("A"),
	INVALID("19"), 
	ALERT_ASSIGNED("20"), 
	ALERT_ACCEPTED("21"), 
	ALERT_RESOLVED("22"),
	HEART_BEAT("H"), 
	REGISTERED("R");
	// FOR LATER USE
	/*
	 * WAKE_UP("4"), ROUTER_NOT_FOUND("5"), SECONDRY_JOIN_REQUEST("6"),
	 * PRIMARY_JOIN_REQUEST( "8"), AUTH_FAIL("9"), FAULT("11"),
	 * NETWORK_MISMATCH("12"), ROUTER_SWITCHED( "13"), DRIFT_DETECTED("14"),,
	 * HEARTBEAT("18"), OUTBOUND_REQUEST("23"), OUTBOUND_ACKKNOWLEDGE("24")
	 */
	private String messageTypeCode;
	 
	private static EnumMap<MessageType> map =
		      new EnumMap<MessageType>(MessageType.class);
	
	private MessageType(String messageTypeCode) {
		this.messageTypeCode = messageTypeCode;
	}
	
	public String getCode() {
		return messageTypeCode;
	}
	
	public static MessageType getValue(String code) {
		return map.get(code);
	}

}