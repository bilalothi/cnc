package com.ugs.cnc.enums;

import com.ugs.cnc.util.EnumMap;
import com.ugs.cnc.util.IEnumValueSupplier;

/**
 * Enum for Status Type 
 *
 * @author Bilal
 */
public enum StatusType implements IEnumValueSupplier{
    ON("1"), 
    SLEEP("2"), 
    OFF("0"), 
    DEBUG("3");
    
    private String statusTypeCode;
	
    private static EnumMap<StatusType> map =
		      new EnumMap<StatusType>(StatusType.class);
    
	private StatusType(String statusTypeCode) {
		this.statusTypeCode = statusTypeCode;
	}
	
	public String getCode() {
		return statusTypeCode;
	}
	
	public static StatusType getValue(String code) {
		return map.get(code);
	}
	
}