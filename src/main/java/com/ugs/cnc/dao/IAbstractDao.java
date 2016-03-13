/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IAbstractDao.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.1
 */

package com.ugs.cnc.dao;

import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

/**
 * Interface to provide database operations for objects
 *
 * @author Capt Bilal
 */
public interface IAbstractDao<E, I extends Serializable> {

    E findById(I id);
    void saveOrUpdate(E e);
    void save(E e);
    void persist(E e);
    void merge(E e);
    public List<E> findAll();
    void delete(E e);
    List<E> findByCriteria(Criterion criterion);
	void update(E e);
}
