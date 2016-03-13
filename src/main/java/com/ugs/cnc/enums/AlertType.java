/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * AlertType.java
 * 
 * Author: Capt Bilal
 * 
 * version 1.2
 */

package com.ugs.cnc.enums;

import com.ugs.cnc.util.EnumMap;
import com.ugs.cnc.util.IEnumValueSupplier;

/**
 * Enum for Alert Type
 *
 * @author Bilal
 */
public enum AlertType implements IEnumValueSupplier{
	MOVEMENT1("01"),
	MOVEMENT2("02"),
	MOVEMENT3("03"),
	VIBRATION("01"), 
	VIBRATION1("1"),
	MICROWAVE("02"), 
	MICROWAVE1("2"),
	VIDEO_DETECTION("03"),
	VIDEO_DETECTION1("3")
	
//	REGISTRATION("R"),
//	ALERT("A"), 
//	BATTERY("B"),
//	GPS("G"),
//	HEARTBEAT("H"), 
//	STATUS("S")
	
//	SEISMIC_HUMAN("HU"),
//	SEISMIC_LIGHT_VEHICLE("LV"),
//	SEISMIC_HEAVY_VEHICLE("HV"),
//	SEISMIC_FLYING_TARGET("FT")
	;
	
	
	private String alertTypeCode;
	
	private static EnumMap<AlertType> map =
		      new EnumMap<AlertType>(AlertType.class);
	
	private AlertType(String alertTypeCode) {
		this.alertTypeCode = alertTypeCode;
	}
	
	public String getCode() {
		return alertTypeCode;
	}
	
	public static AlertType getValue(String code) {
		return map.get(code);
	}
}