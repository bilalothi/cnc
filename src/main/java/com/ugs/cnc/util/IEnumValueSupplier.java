/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IEnumValueSupplier.java
 * 
 * Author: Capt Bilal
 * 
 * version 1.2
 */

package com.ugs.cnc.util;

/**
 * Interface for Enums to implement so that the code defined for a particular
 * Enum Type can be retrieved 
 * 
 * @author Capt Bilal
 */
public interface IEnumValueSupplier {
	public String getCode();
}