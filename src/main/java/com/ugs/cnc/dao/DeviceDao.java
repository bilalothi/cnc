/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * DeviceDao.java
 * 
 * Author: Capt Bilal
 */
package com.ugs.cnc.dao;

import com.ugs.cnc.dao.IDeviceDao;
import com.ugs.cnc.entities.Device;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Device DAO Implementation class
 *
 * @author Capt Bilal
 */
@Repository
public class DeviceDao extends AbstractDao<Device, String> implements
        IDeviceDao {

    /**
     * Constructor for Device DAO
     *
     */
    protected DeviceDao() {
        super(Device.class);
    }

    /**
     * Method to save a device to the database
     *
     * @param device Device that is to be saved in the database
     */
    @Override
    public void saveDevice(Device device) {
        save(device);
    }

    /**
     * Method for searching a device by device Id from the database
     *
     * @param deviceId Device Id for the device to be searched
     * @return Device with the specified device Id
     *
     */
    @Override
    public Device findDeviceById(String deviceId) {
        Device device = findById(deviceId);
        return device;
    }

    /**
     * Method for searching devices by network id from the database
     *
     * @param deviceNetworkId Network Id for devices to be searched
     * @return List of devices with the specified network Id
     *
     */
    @Override
    public List<Device> findDevicesByNetworkId(String deviceNetworkId) {
        List<Device> deviceList = findByCriteria(Restrictions.eq("networkId",
                deviceNetworkId));
        return deviceList;
    }

    /**
     * Method for searching devices by type from the database
     *
     * @param deviceType Type for devices to be searched
     * @return List of devices with the specified type
     *
     */
    @Override
    public List<Device> findDevicesByType(String deviceType) {
        List<Device> deviceList = findByCriteria(Restrictions.eq("deviceType", deviceType));
        return deviceList;
    }

    /**
     * Method for searching devices by status from the database
     *
     * @param deviceStatus Status for devices to be searched
     * @return List of devices with the specified status
     *
     */
    @Override
    public List<Device> findDevicesByStatus(String deviceStatus) {
        List<Device> deviceList = findByCriteria(Restrictions.eq(
                "deviceStatus", deviceStatus));
        return deviceList;
    }

    /**
     * Method for retrieving all the devices stored in the DB
     *
     * @return List of all devices
     *
     */
    @Override
    public List<Device> getAllDevices() {
        List<Device> deviceList = findAll();
        return deviceList;
    }

    /**
     * Method to delete a device from the database
     *
     * @param device Device that is to be deleted from the database
     */
    @Override
    public void deleteDevice(Device device) {
        delete(device);
    }

    /**
     * Method to update device information stored in the database
     *
     * @param device Device that is to be updated in the database
     */
    @Override
    public void updateDevice(Device device) {
        saveOrUpdate(device);
    }

}
