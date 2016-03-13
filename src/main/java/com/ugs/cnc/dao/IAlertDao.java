/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IAlertDao.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.2
 */

package com.ugs.cnc.dao;

import java.util.Date;
import java.util.List;

import com.ugs.cnc.entities.Alert;
import com.ugs.cnc.enums.AlertState;

/**
 * Interface to provide database operations for alerts
 * 
 * @author Bilal
 */
public interface IAlertDao extends IAbstractDao<Alert, String> {
	void saveAlert(Alert alert);

	void updateAlertState(Alert alert);

	List<Alert> getAlertsByDate(Date startDate, Date endDate);

	List<Alert> getAlertsByType(String alertType);

	List<Alert> getAlertsByState(String alertState);

	List<Alert> getAlertsByDeviceId(String deviceId);

	List<Alert> getAlertsByStateAndDate(String alertState, Date startDate,
			Date endDate);
	
	List<Alert> getAlertsByStateAndDeviceId(AlertState alertState, String deviceId);
	
	List<Alert> getAlertsByStateAndUser(AlertState alertState, String username);
	
	List<Alert> getAlertsToResolve(AlertState alertState, String deviceId, String assignedTo);

	List<Alert> getAlertsToAssign(AlertState alertState, String deviceId, String assignedBy);


}
