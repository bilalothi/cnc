/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IDeviceService.java
 * 
 * Author: Capt Bilal
 */
package com.ugs.cnc.service;

import com.ugs.cnc.entities.Device;

import java.util.List;

/**
 * Interface to provide CRUD operations for devices
 *
 * @author Capt Bilal
 */
public interface IDeviceService {

    Device findByDeviceId(String deviceId);

    List<Device> findByDeviceType(String deviceType);

    List<Device> findByDeviceNetwork(String deviceNetworkId);

    List<Device> findByDeviceStatus(String deviceStatus);

    void saveDevice(Device device);

    void updateDevice(Device device);

    void deleteDevice(String deviceId);

    List<Device> getAllDevices();
}
