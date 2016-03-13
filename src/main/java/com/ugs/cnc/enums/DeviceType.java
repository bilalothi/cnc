package com.ugs.cnc.enums;

import com.ugs.cnc.util.EnumMap;
import com.ugs.cnc.util.IEnumValueSupplier;

/**
 * Enum for Device Type
 * 
 * @author Capt Bilal
 */
public enum DeviceType implements IEnumValueSupplier{
	// Changed by Capt Bilal 
	ROUTER_NODE("R"),
	PIR_SENSOR_NODE("P"),
	DUAL_SENSOR_NODE("D"),
	SEISMIC_SENSOR_NODE("S"),
	//SENSOR_NODE("S"), 
	GATEWAY_NODE("G"), 
	VIDEO_CAMERA_NODE("V"), 
	HUMAN_ATTACHED("H"),
	C2I_SYSTEM("C");

	private String deviceTypeCode;

	private static EnumMap<DeviceType> map =
		      new EnumMap<DeviceType>(DeviceType.class);
	
	private DeviceType(String deviceTypeCode) {
		this.deviceTypeCode = deviceTypeCode;
	}

	public String getCode() {
		return deviceTypeCode;
	}
	
	public static DeviceType getValue(String code) {
		return map.get(code);
	}
}