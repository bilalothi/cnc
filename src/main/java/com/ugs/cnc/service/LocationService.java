/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * LocationService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ugs.cnc.entities.Location;
import com.ugs.cnc.dao.ILocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for location related operations
 *
 * @author Capt Bilal
 */
@Service("locationService")
@Transactional(readOnly = true)
public class LocationService implements ILocationService {
    
	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    @Autowired
    private ILocationDao locationDao;

	/**
	 * Service method for searching a location by device Id
	 * 
	 * @param deviceId
	 *            Device Id for the location to be searched
	 * @return Location with the specified device Id
	 * 
	 */
    @Override
    public Location findByDeviceId(String deviceId) {
        return locationDao.findDeviceLocation(deviceId);
    }

    /**
	 * Service method for saving a device location
	 * 
	 * @param location
	 *            Device location to be saved
	 * 
	 */
    @Override
    @Transactional(readOnly = false)
    public void saveLocation(Location location) {

    	try {
        locationDao.saveLocation(location);
        } catch (DataAccessException e) {
            logger.warn("location info could not be added to the database: " + e );

        }
    }

    /**
	 * Service method for updating device location information
	 * 
	 * @param location
	 *            Device location to be updated
	 * 
	 */
    @Override
    @Transactional(readOnly = false)
    public void updateLocation(Location location) {
    	try {
        locationDao.updateLocation(location);
    	} catch (DataAccessException e) {
            logger.warn("location could not be updated in the database: " + e );

        }
    }
    
}
