/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * AbstractDao.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.2
 */
package com.ugs.cnc.dao;

import com.ugs.cnc.dao.IAbstractDao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Hibernate Abstract DAO Implementation class
 *
 * @author Capt Bilal
 */
public abstract class AbstractDao<E, I extends Serializable> implements IAbstractDao<E, I> {

    private Class<E> entityClass;
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Constructor for Hibernate Abstract DAO
     *
     * @param entityClass The object on which a hibernate database operation is
     * to be performed
     */
    protected AbstractDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Method to get a hibernate connection session
     *
     * @return Session hibernate session
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Method to search an object by id from the database
     *
     * @param I Id for the object searched
     *
     * @return E Object that is searched
     */
    @SuppressWarnings("unchecked")
    @Override
    public E findById(I Id) {
        return (E) getCurrentSession().get(entityClass, Id);
    }

    /**
     * Method to save an object to the database
     *
     * @param E Object that is to be saved in the database
     */
    @Override
    public void save(E e) {
        getCurrentSession().save(e);
    }

    /**
     * Method to save an object or update it if it already exists in the
     * database
     *
     * @param E Object that is to be saved or updated in the database
     */
    @Override
    public void saveOrUpdate(E e) {
        getCurrentSession().saveOrUpdate(e);
    }

    /**
     * Method to update an object in the database
     *
     * @param E Object that is to be updated in the database
     */
    @Override
    public void update(E e) {
        getCurrentSession().update(e);
    }

    /**
     * Method to merge an object in the database
     *
     * @param E Object that is to be updated in the database
     */
    @Override
    public void merge(E e) {
        getCurrentSession().merge(e);
    }

    /**
     * Method to persist an object in the database
     *
     * @param E Object that is to be updated in the database
     */
    @Override
    public void persist(E e) {
        getCurrentSession().persist(e);
    }

    /**
     * Method to delete an object from the database
     *
     * @param E Object that is to be deleted from the database
     */
    @Override
    public void delete(E e) {
        getCurrentSession().delete(e);
    }

    /**
     * Method to search an object by a given criteria
     *
     * @param criterion criteria for the search
     * @return List of objects that were searched
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<E> findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }

    /**
     * Method to retrieve all the objects of a given type
     *
     * @return List of all the objects of a given type
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll() {
        return getCurrentSession().createQuery("from " + entityClass.getName()).list();
    }

}
