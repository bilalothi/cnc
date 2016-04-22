/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * DeviceDao.java
 * 
 * Author: Capt Bilal
 */
package com.ugs.cnc.dao;


import com.ugs.cnc.entities.ApplMode;
import java.util.List;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Repository;

/**
 * Device DAO Implementation class
 *
 * @author Capt Bilal
 */
@Repository
public class ApplModeDao extends AbstractDao<ApplMode, Integer> implements IApplModeDao {

    /**
     * Constructor for Appl Mode
     *
     */
    protected ApplModeDao() {
        super(ApplMode.class);
    }

    @Override
    public com.ugs.cnc.entities.ApplMode findByMode(String name) {
        ApplMode applMode = null; 
        List<ApplMode> applModeList = findByCriteria(Restrictions.eq("name", name));
        if( applModeList != null && !applModeList.isEmpty()) {
            applMode = applModeList.get(0);
        }
        return applMode;
    }
    
    @Override
    public com.ugs.cnc.entities.ApplMode findApplModeById(Integer id) {
        ApplMode applMode = null; 
        applMode = findById(id);
        return applMode;
    }

    @Override
    public List<com.ugs.cnc.entities.ApplMode> getAll() {
        List<ApplMode> applModeList = findAll();
        return applModeList;
    }

    
}
