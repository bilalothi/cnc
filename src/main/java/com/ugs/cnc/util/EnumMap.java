/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * EnumMap.java
 * 
 * Author: Capt Bilal
 * 
 * version 1.2
 */

package com.ugs.cnc.util;

import java.util.*;

/**
 * A generic map for use with Enums to do reverse lookup on Enums defined so that
 * values assgined to each Enum Type can be retrieved using the codes specified for 
 * those types
 * 
 * @author Capt Bilal
 */
public class EnumMap<V extends Enum<V> & IEnumValueSupplier> {
  private Map<String, V> enumMap = new HashMap<String, V>();
  public EnumMap(Class<V> valueType) {
    for (V v : valueType.getEnumConstants()) {
      enumMap.put(v.getCode(), v);
    }
  }

  public V get(String enumCode) {
    return enumMap.get(enumCode);
  }
}