/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * LocationDao.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.dao;

import com.ugs.cnc.dao.ILocationDao;
import com.ugs.cnc.entities.Location;

import org.springframework.stereotype.Repository;

/**
 * Location DAO Implementation class
 * 
 * @author Capt Bilal
 */
@Repository
public class LocationDao extends AbstractDao<Location, String> implements
		ILocationDao {

	/**
	 * Constructor for Location DAO
	 * 
	 */
	protected LocationDao() {
		super(Location.class);
	}

	/**
	 * Method to save device location to the database
	 * 
	 * @param location
	 *            Location of the device that is to be saved in the database
	 */
	@Override
	public void saveLocation(Location location) {
		save(location);
	}

	/**
	 * Method for searching a location by device Id from the database
	 * 
	 * @param deviceId
	 *            Device Id for the location to be searched
	 * @return Location with the specified device Id
	 * 
	 */
	@Override
	public Location findDeviceLocation(String deviceId) {
		Location location = findById(deviceId);
		return location;
	}

	/**
	 * Method to update device location stored in the database
	 * 
	 * @param location
	 *            Device location that is to be updated in the database
	 */
	@Override
	public void updateLocation(Location location) {
		saveOrUpdate(location);
	}

}
