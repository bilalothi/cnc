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

import com.ugs.cnc.entities.ApplMode;
import java.util.List;

/**
 * Interface to provide ApplMode CRUD operations
 * @author Capt Bilal
 */
public interface IApplModeService {

    
    List<ApplMode> getAll();
    ApplMode findByMode(String modeName);
    ApplMode findById(Integer id);
}
