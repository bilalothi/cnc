/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IAlertService.java
 * 
 * Author: Capt Bilal
 * 
 * version 1.1
 */

package com.ugs.cnc.service;

import com.ugs.cnc.entities.Alert;
import com.ugs.cnc.enums.AlertState;

import java.util.Date;
import java.util.List;

/**
 * Interface to provide alert CRUD operations
 * @author Capt Bilal
 */
public interface IAlertService {

    void saveAlert(Alert alert);
    void updateAlertState(Alert alert);
    void updateAlertStatus(Alert alert);
    List<Alert> findByState(String alertState);
    List<Alert> findByDeviceId(String deviceId);
    List<Alert> findAlertsByStateAndDeviceId(AlertState alertState, String deviceId);
    List<Alert> findAlertsToResolve(AlertState alertState, String deviceId, String username);
    List<Alert> findAlertsToAssign(AlertState alertState, String deviceId, String username);
    List<Alert> findAlertsByStateAndUser(AlertState alertState, String username);
    Alert findByAlertId(String alertId);
    List<Alert> findByType(String alertType);
    List<Alert> findByDate(Date startDate, Date endDate);
    List<Alert> findByStateAndDate(String alertState, Date startDate, Date endDate);
}
