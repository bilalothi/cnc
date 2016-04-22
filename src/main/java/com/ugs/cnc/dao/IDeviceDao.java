/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IDeviceDao.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.0
 */
package com.ugs.cnc.dao;

import com.ugs.cnc.entities.Device;

import java.util.List;

/**
 * Interface to provide database operations for devices
 *
 * @author Capt Bilal
 */
public interface IDeviceDao extends IAbstractDao<Device, String> {

    void saveDevice(Device device);

    void deleteDevice(Device device);

    void updateDevice(Device device);

    Device findDeviceById(String deviceId);

    List<Device> findDevicesByStatus(String deviceStatus);

    List<Device> findDevicesByType(String deviceType);

    List<Device> findDevicesByNetworkId(String deviceNetworkId);

    List<Device> getAllDevices();
	//List<VideoCameraNode> findAllVideoCameras();
}
