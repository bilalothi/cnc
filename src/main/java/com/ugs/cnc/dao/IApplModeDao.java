/*
 * Copyright 2016, MCS.
 *
 * IApplModeDao.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.0
 */
package com.ugs.cnc.dao;

import com.ugs.cnc.entities.ApplMode;

import java.util.List;

/**
 * Interface to provide database operations for devices
 *
 * @author Capt Bilal
 */
public interface IApplModeDao extends IAbstractDao<ApplMode, Integer> {

    ApplMode findByMode(String name);
    
    ApplMode findApplModeById(Integer id);

    List<ApplMode> getAll();

}
