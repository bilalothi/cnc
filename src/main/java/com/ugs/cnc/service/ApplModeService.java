/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * AlertService.java
 * 
 * Author: Capt Bilal
 */
package com.ugs.cnc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugs.cnc.dao.IApplModeDao;
import com.ugs.cnc.entities.ApplMode;

/**
 * Alert service for alert CRUD operations
 *
 * @author Bilal
 */
@Service("applModeService")
@Transactional(readOnly = true)
public class ApplModeService implements IApplModeService {

    private static final Logger logger = LoggerFactory.getLogger(ApplModeService.class);

    @Autowired
    private IApplModeDao applModeDao;

    /**
     *
     * @param modeName
     * @return
     */
    @Override
    public ApplMode findByMode(String modeName) {
        return applModeDao.findByMode(modeName);
    }

    /**
     *
     * @return
     */
    @Override
    public List<ApplMode> getAll() {
        return applModeDao.findAll();
    }

    /** 
     * 
     * @param id
     * @return 
     */
    @Override
    public ApplMode findById(Integer id) {
        return applModeDao.findById(id);
    }
}
