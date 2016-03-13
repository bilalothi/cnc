/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * ILocationService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import com.ugs.cnc.entities.Location;

/**
 * Interface to provide CRUD operations for device location
 *
 * @author Capt Bilal
 */
public interface ILocationService {

    Location findByDeviceId(String deviceId);
    void saveLocation(Location location);
    void updateLocation(Location location);
}
