/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * ILocationDao.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.dao;

import com.ugs.cnc.entities.Location;

/**
 * Interface to provide database operations for device location
 *
 * @author Capt Bilal
 */
public interface ILocationDao extends IAbstractDao<Location, String> {
    void saveLocation(Location location);
    void updateLocation(Location location);
    Location findDeviceLocation(String deviceId);
}
