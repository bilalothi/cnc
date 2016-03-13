/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * UserDao.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.0
 */

package com.ugs.cnc.dao;

import com.ugs.cnc.entities.User;
import com.ugs.cnc.dao.IUserDao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User DAO Implementation class
 * 
 * @author Capt Bilal
 */
@Repository
public class UserDao extends AbstractDao<User, String> implements IUserDao {

	/**
	 * Constructor for User DAO
	 * 
	 */
	protected UserDao() {
		super(User.class);
	}

	/**
	 * Method to save a user to the database
	 * 
	 * @param user
	 *            User that is to be saved in the database
	 */
	@Override
	public void saveUser(User user) {
		saveOrUpdate(user);
	}

	/**
	 * Method for retrieving all the users stored in the DB
	 * 
	 * @return List of all users
	 * 
	 */
	@Override
	public List<User> getAllUsers() {
		List<User> userList = findAll();
		return userList;
	}

	/**
	 * Method for searching a user by user id from the database
	 * 
	 * @param userId
	 *            Id for user to be searched
	 * @return User that has been searched
	 * 
	 */
	@Override
	public User findUserById(String userId) {
		return findById(userId);
	}

	/**
	 * Method for searching users by their first name or last name from the
	 * database
	 * 
	 * @param name
	 *            First or Last Name for users to be searched
	 * @return List of users with the specified first or last name
	 * 
	 */
	@Override
	public List<User> findUsersByName(String name) {
		Criterion firstNameCriteria = Restrictions.like("firstName", name);
		Criterion lastNameCriteria = Restrictions.like("lastName", name);
		List<User> userList = findByCriteria(Restrictions.disjunction()
				.add(firstNameCriteria).add(lastNameCriteria));
		return userList;
	}

	/**
	 * Method for searching users by role from the database
	 * 
	 * @param userRole
	 *            Role for users to be searched
	 * @return List of users with the specified role
	 * 
	 */
	@Override
	public List<User> findUsersByRole(String userRole) {
		List<User> userList = findByCriteria(Restrictions.eq("userRole",
				userRole));
		return userList;
	}

	/**
	 * Service method for searching users by status from the database
	 * 
	 * @param userStatus
	 *            Status for users to be searched
	 * @return List of users with the specified status
	 * 
	 */
	@Override
	public List<User> findUsersByStatus(String userStatus) {
		List<User> userList = findByCriteria(Restrictions.eq("userStatus",
				userStatus));
		return userList;
	}

	/**
	 * Method to delete a user from the database
	 * 
	 * @param user
	 *            User that is to be deleted from the database
	 */
	@Override
	public void deleteUser(User user) {
		delete(user);
	}

	/**
	 * Method to update user information stored in the database
	 * 
	 * @param user
	 *            User that is to be updated in the database
	 */
	@Override
	public void updateUser(User user) {

		saveOrUpdate(user);
	}
}
