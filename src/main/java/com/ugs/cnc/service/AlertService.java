/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * AlertService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugs.cnc.dao.IAlertDao;
import com.ugs.cnc.entities.Alert;
import com.ugs.cnc.enums.AlertState;
import com.ugs.ws.UgsWS;
import com.ugs.ws.UgsWS_Service;

/**
 * Alert service for alert CRUD operations
 * 
 * @author Bilal
 */
@Service("alertService")
@Transactional(readOnly = true)
public class AlertService implements IAlertService {

	private static final Logger logger = LoggerFactory
			.getLogger(AlertService.class);

	@Autowired
	private IAlertDao alertDao;

	/**
	 * Service method for saving an alert message
	 * 
	 * @param alert
	 *            the alert received from the device after conversion into
	 *            readable text
	 */
	@Override
	@Transactional(readOnly = false)
	public void saveAlert(Alert alert) {
		try {

//			try {
//				UgsWS_Service ugsWS_Service = new UgsWS_Service();
//				UgsWS ugsWS = ugsWS_Service.getUgsWSPort();
//
//				if (ugsWS != null) {
//					com.ugs.ws.Alert alertWS = new com.ugs.ws.Alert();
//					alertWS.setDeviceId(alert.getAlertDeviceId());
//					alertWS.setFlag("false");
//					alertWS.setAlertState(alert.getAlertState());
//					alertWS.setAlertType(alert.getAlertType());
//
//					ugsWS.addAlert(alertWS);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			alertDao.saveAlert(alert);
		} catch (DataAccessException e) {
			logger.error("Alert could not be saved to the database: " + e);

		}
	}

	/**
	 * Service method for retrieving all alerts with a specified state
	 * 
	 * @param alertState
	 *            state of the alert to be searched
	 * @return list of all the alerts with the specified state
	 */
	@Override
	public List<Alert> findByState(String alertState) {
		return alertDao.getAlertsByState(alertState);
	}

	/**
	 * Service method for retrieving all alerts with a specified type
	 * 
	 * @param alertType
	 *            type of the alert to be searched
	 * @return list of all the alerts with the specified type
	 */
	@Override
	public List<Alert> findByType(String alertType) {
		return alertDao.getAlertsByType(alertType);
	}

	/**
	 * Service method for searching alerts by date
	 * 
	 * @param startDate
	 *            starting date limit of alerts to be searched
	 * @param endDate
	 *            ending date limit of alerts to be searched
	 * @return List of alerts with the specified date and time limits
	 * 
	 * */
	@Override
	public List<Alert> findByDate(Date startDate, Date endDate) {
		return alertDao.getAlertsByDate(startDate, endDate);
	}

	/**
	 * Service method for searching alerts by type of alert and date
	 * 
	 * @param startDate
	 *            starting date of alerts to be searched
	 * @param endDate
	 *            ending date of alerts to be searched
	 * @param alertState
	 *            state of alerts to be searched
	 * @return List of alerts with the specified alert type and date limits
	 */
	@Override
	public List<Alert> findByStateAndDate(String alertState, Date startDate,
			Date endDate) {
		return alertDao.getAlertsByStateAndDate(alertState, startDate, endDate);
	}

	/**
	 * Service method for searching alerts by deviceId
	 * 
	 * @param deviceId
	 *            Device Id for the alerts to be searched
	 * @return List of alerts with the specified device Id
	 */
	@Override
	public List<Alert> findByDeviceId(String deviceId) {
		return alertDao.getAlertsByDeviceId(deviceId);
	}

	/**
	 * Service method for searching alerts by state and deviceId
	 * 
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @param deviceId
	 *            Device Id for the alerts to be searched
	 * @return List of alerts searched
	 */
	@Override
	public List<Alert> findAlertsByStateAndDeviceId(AlertState alertState,
			String deviceId) {
		return alertDao.getAlertsByStateAndDeviceId(alertState, deviceId);
	}

	/**
	 * Service method for searching the assigned results that are to be resolved
	 * by a field user
	 * 
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @param deviceId
	 *            Device Id for the alerts to be searched
	 * @param username
	 *            user who was assigned the alerts and is now responsible for
	 *            resolving the alerts
	 * @return List of alerts searched
	 */
	@Override
	public List<Alert> findAlertsToResolve(AlertState alertState,
			String deviceId, String username) {
		return alertDao.getAlertsToResolve(alertState, deviceId, username);
	}

	/**
	 * Service method for searching the accepted alerts that are to be assigned
	 * to a field user
	 * 
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @param deviceId
	 *            Device Id for the alerts to be searched
	 * @param username
	 *            user who has accepted the alerts and is now responsible for
	 *            resolving the alerts
	 * @return List of alerts searched
	 */
	@Override
	public List<Alert> findAlertsToAssign(AlertState alertState,
			String deviceId, String username) {
		return alertDao.getAlertsToAssign(alertState, deviceId, username);
	}

	/**
	 * Service method for searching alerts by state and user
	 * 
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @param username
	 *            user for which the alerts are to be searched
	 * @return List of alerts searched
	 */
	@Override
	public List<Alert> findAlertsByStateAndUser(AlertState alertState,
			String username) {
		return alertDao.getAlertsByStateAndUser(alertState, username);
	}

	/**
	 * Service method for searching alerts by deviceId
	 * 
	 * @param alertId
	 *            Alert Id for the alert to be searched
	 * @return Alert with the specified alert Id
	 */
	@Override
	public Alert findByAlertId(String alertId) {
		return alertDao.findById(alertId);
	}

	/**
	 * Service method for updating the state of alerts
	 * 
	 * @param alert
	 *            Alert whose state is to be updated
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateAlertState(Alert alert) {
		alertDao.updateAlertState(alert);

	}

	/**
	 * Service method for updating the status of alert to accepted, assigned or
	 * resolved
	 * 
	 * @param alert
	 *            Alert whose state is to be updated
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateAlertStatus(Alert alert) {

		alertDao.update(alert);
	}

	/*
	 * /** Service method for updating the state of alerts to accepted
	 * 
	 * @param alertId alert that has been accepted
	 * 
	 * public void acceptAlert(Alert alert) { alertDao.update(alert);
	 * 
	 * }
	 * 
	 * /** Service method for assigning an alert to a user
	 * 
	 * @param alert alert that has been assigned
	 * 
	 * public void assignAlert(Alert alert) { alertDao.update(alert);
	 * 
	 * }
	 * 
	 * /** Service method for updating the state of alert to resolved
	 * 
	 * @param alert alert that has been resolved
	 * 
	 * public void resolveAlert(Alert alert) { alertDao.update(alert);
	 * 
	 * }
	 */
}
