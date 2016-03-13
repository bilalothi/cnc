/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * AlertDao.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.1
 */

package com.ugs.cnc.dao;

import com.ugs.cnc.dao.IAlertDao;
import com.ugs.cnc.entities.Alert;
import com.ugs.cnc.enums.AlertState;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Alert DAO Implementation class
 * 
 * @author Capt Bilal
 */
@Repository
public class AlertDao extends AbstractDao<Alert, String> implements IAlertDao {

	/**
	 * Constructor for Alert DAO
	 * 
	 */
	protected AlertDao() {
		super(Alert.class);
	}

	/**
	 * Method to save different kinds of alerts from a device to the database
	 * 
	 * @param alert
	 *            Alert that is to be saved in the database
	 */
	@Override
	public void saveAlert(Alert alert) {
		save(alert);
	}

	/**
	 * Method to update the state an alert
	 * 
	 * @param alert
	 *            Alert that is to be updated in the database
	 */
	@Override
	public void updateAlertState(Alert alert) {
		update(alert);
	}

	/**
	 * Method for searching alerts by date
	 * 
	 * @param startDate
	 *            starting date limit of alerts to be searched
	 * @param endDate
	 *            ending date limit of alerts to be searched
	 * @return List of alerts with the specified date and time limits
	 * 
	 * */
	@Override
	public List<Alert> getAlertsByDate(Date startDate, Date endDate) {
		List<Alert> alertList = findByCriteria(Restrictions.between(
				"alertDate", startDate, endDate));
		return alertList;
	}

	/**
	 * Method for searching alerts by type from the database
	 * 
	 * @param alertType
	 *            type of alerts to be searched
	 * @return List of alerts with the specified type of alert
	 */
	@Override
	public List<Alert> getAlertsByType(String alertType) {
		List<Alert> alertList = findByCriteria(Restrictions.eq("alertType",
				alertType.toString()));
		return alertList;
	}

	/**
	 * Method for searching alerts by state from the database
	 * 
	 * @param alertState
	 *            state of alerts to be searched
	 * @return List of alerts with the specified state of alert
	 */
	@Override
	public List<Alert> getAlertsByState(String alertState) {
		List<Alert> alertList = findByCriteria(Restrictions.eq("alertType",
				alertState.toString()));
		return alertList;
	}

	/**
	 * Method for searching alerts by deviceId from the database
	 * 
	 * @param deviceId
	 *            Device Id for the alerts to be searched
	 * @return List of alerts searched
	 */
	@Override
	public List<Alert> getAlertsByDeviceId(String deviceId) {
		List<Alert> alertList = findByCriteria(Restrictions.eq("alertDeviceId",
				deviceId));
		return alertList;
	}
	
	/**
	 * Method for searching the alerts that are to be resolved, from the database
	 * 
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @param deviceId
	 *            Device Id for the active alerts to be searched
	 * @param assignedBy
	 *            user who was assigned the alerts and is now responsible for resolving the alerts
	 * @return List of active alerts searched
	 */
	@Override
	public List<Alert> getAlertsToResolve(AlertState alertState, String deviceId, String assignedTo) {
		Criterion deviceIdCriteria = Restrictions.eq("alertDeviceId", deviceId);
		Criterion alertStateCriteria = Restrictions.eq("alertState", alertState.toString());
		Criterion userCriteria = Restrictions.eq("assignedTo", assignedTo);
		List<Alert> alertList = findByCriteria(Restrictions.conjunction()
				.add(deviceIdCriteria).add(alertStateCriteria).add(userCriteria));
		return alertList;
	}
	
	/**
	 * Method for searching active alerts that are to be assigned to a user, from the database
	 * 
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @param deviceId
	 *            Device Id for the active alerts to be searched
	 * @param assignedBy
	 *            user who had accepted the alerts and is now responsible for assigning the alerts
	 * @return List of active alerts searched
	 */
	@Override
	public List<Alert> getAlertsToAssign(AlertState alertState, String deviceId, String assignedBy) {
		Criterion deviceIdCriteria = Restrictions.eq("alertDeviceId", deviceId);
		Criterion alertStateCriteria = Restrictions.eq("alertState", alertState.toString());
		Criterion userCriteria = Restrictions.eq("assignedBy", assignedBy);
		List<Alert> alertList = findByCriteria(Restrictions.conjunction()
				.add(deviceIdCriteria).add(alertStateCriteria).add(userCriteria));
		return alertList;
	}
	
	/**
	 * Method for searching active alerts by deviceId from the database
	 * 
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @param deviceId
	 *            Device Id for the active alerts to be searched
	 * @return List of active alerts searched
	 */
	@Override
	public List<Alert> getAlertsByStateAndDeviceId(AlertState alertState, String deviceId) {
		Criterion deviceIdCriteria = Restrictions.eq("alertDeviceId", deviceId);
		Criterion alertStateCriteria = Restrictions.eq("alertState", alertState.toString());
		List<Alert> alertList = findByCriteria(Restrictions.conjunction()
				.add(deviceIdCriteria).add(alertStateCriteria));
		return alertList;
	}

	/**
	 * Method for searching alerts by state and username from the database
	 * 
	 * @param username
	 *            user for which the assigned alerts are to be searched
	 * @param alertState
	 *            state of the alerts that are to be searched
	 * @return List of assigned alerts searched
	 */
	@Override
	public List<Alert> getAlertsByStateAndUser(AlertState alertState, String username) {
		Criterion userCriteria = Restrictions.eq("assignedTo", username);
		Criterion alertStateCriteria = Restrictions.eq("alertState", alertState.toString());
		List<Alert> alertList = findByCriteria(Restrictions.conjunction()
				.add(userCriteria).add(alertStateCriteria));
		return alertList;
	}
	
	/**
	 * Method for searching alerts by type of alert and date, from the database
	 * 
	 * @param startDate
	 *            starting date limit of alerts to be searched
	 * @param endDate
	 *            ending date limit of alerts to be searched
	 * @param alertState
	 *            state of alerts to be searched
	 * @return List of alerts with the specified alert state and date limits
	 */
	@Override
	public List<Alert> getAlertsByStateAndDate(String alertState,
			Date startDate, Date endDate) {
		Criterion dateCriteria = Restrictions.between("alertDate", startDate,
				endDate);
		Criterion alertStateCriteria = Restrictions.eq("alertState", alertState);
		List<Alert> alertList = findByCriteria(Restrictions.conjunction()
				.add(dateCriteria).add(alertStateCriteria));
		return alertList;
	}

}
