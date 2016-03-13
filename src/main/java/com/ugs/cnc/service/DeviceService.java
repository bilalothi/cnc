/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * DeviceService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ugs.cnc.entities.Device;
import com.ugs.cnc.dao.IDeviceDao;
import com.ugs.cnc.service.IDeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Device service for device related operations
 * 
 * @author Capt Bilal
 */
@Service("deviceService")
@Transactional(readOnly = true)
public class DeviceService implements IDeviceService {

	private static final Logger logger = LoggerFactory
			.getLogger(DeviceService.class);
	@Autowired
	private IDeviceDao deviceDao;

	/**
	 * Service method for searching a device by device Id
	 * 
	 * @param deviceId
	 *            Device Id for the device to be searched
	 * @return Device with the specified device Id
	 * 
	 */
	@Override
	public Device findByDeviceId(String deviceId) {
		return deviceDao.findDeviceById(deviceId);
	}

	/**
	 * Service method for searching devices by type
	 * 
	 * @param deviceType
	 *            Type for devices to be searched
	 * @return List of devices with the specified type
	 * 
	 */
	@Override
	public List<Device> findByDeviceType(String deviceType) {

		logger.info("Retrieving devices with type: {}, from the database",
				deviceType);
		return deviceDao.findDevicesByType(deviceType);
	}

	/**
	 * Service method for searching devices by network
	 * 
	 * @param deviceNetwork
	 *            Network for devices to be searched
	 * @return List of devices with the specified network
	 * 
	 */
	public List<Device> findByDeviceNetwork(String deviceNetworkId) {

		logger.info(
				"Retrieving devices with network id: {}, from the database",
				deviceNetworkId);
		return deviceDao.findDevicesByNetworkId(deviceNetworkId);
	}

	/**
	 * Service method for searching devices by status
	 * 
	 * @param deviceStatus
	 *            Status for devices to be searched
	 * @return List of devices with the specified status
	 * 
	 */
	@Override
	public List<Device> findByDeviceStatus(String deviceStatus) {

		logger.info("Retrieving devices with status: {}, from the database",
				deviceStatus);
		return deviceDao.findDevicesByStatus(deviceStatus);
	}

	/**
	 * Service method for saving a device in the DB
	 * 
	 * @param device
	 *            Device to be saved
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void saveDevice(Device device) {

		try {
			deviceDao.saveDevice(device);
		} catch (DataAccessException e) {
			logger.warn("Device info could not be added to the database: " + e);

		}
	}

	/**
	 * Service method for deleting a device from the DB
	 * 
	 * @param deviceId
	 *            Device Id for the device to be deleted
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteDevice(String deviceId) {
		try {
			Device device = deviceDao.findById(deviceId);
			if (device != null) {
				deviceDao.deleteDevice(device);
			} else {
				logger.info("Device not found");
			}
		} catch (DataAccessException e) {
			logger.warn("Device could not deleted from the database: " + e);

		}
	}

	/**
	 * Service method for retrieving all the devices stored in the DB
	 * 
	 * @return List of all devices
	 * 
	 */
	@Override
	public List<Device> getAllDevices() {

		return deviceDao.findAll();
	}

	/**
	 * Service method for updating device information
	 * 
	 * @param device
	 *            Device to be updated
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateDevice(Device device) {
		try {
			deviceDao.updateDevice(device);
		} catch (DataAccessException e) {
			logger.warn("Device could not be updated in the database: " + e);

		}
	}

}
