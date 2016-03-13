/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * UserService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import com.ugs.cnc.entities.User;
import com.ugs.cnc.dao.IUserDao;
import com.ugs.cnc.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User service for user related operations
 * 
 * @author Capt Bilal
 */
@Service("userService")
@Transactional(readOnly = true)
public class UserService implements IUserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	@Autowired
	private IUserDao userDao;

	/**
	 * Service method for searching a user by user id
	 * 
	 * @param userId
	 *            Id for user to be searched
	 * @return User that has been searched
	 * 
	 */
	@Override
	public User findByUserId(String userId) {

		logger.info("Retrieving user with user id {} from the database", userId);
		return userDao.findById(userId);
	}

	/**
	 * Service method for searching users by name
	 * 
	 * @param name
	 *            Name for users to be searched
	 * @return List of users with the specified name
	 * 
	 */
	@Override
	public List<User> findByUserName(String name) {

		logger.info("Retrieving user with name: {}, from the database", name);
		return userDao.findUsersByName(name);
	}
	
	/**
	 * Service method for searching users by role
	 * 
	 * @param userRole
	 *            Role for users to be searched
	 * @return List of users with the specified role
	 * 
	 */
	@Override
	public List<User> findByUserRole(String userRole) {

		logger.info("Retrieving users with role: {}, from the database", userRole);
		return userDao.findUsersByRole(userRole);
	}
	
	/**
	 * Service method for searching users by status
	 * 
	 * @param userStatus
	 *            Status for users to be searched
	 * @return List of users with the specified status
	 * 
	 */
	@Override
	public List<User> findByUserStatus(String userStatus) {

		logger.info("Retrieving users with status: {}, from the database", userStatus);
		return userDao.findUsersByStatus(userStatus);
	}
	/**
	 * Service method for saving a user in the DB
	 * 
	 * @param user
	 *            User to be saved
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		try {
			userDao.saveUser(user);
		} catch (DataAccessException e) {
			logger.warn("User could not be saved in the database: " + e);

		}
	}

	/**
	 * Service method for deleting a user from the DB
	 * 
	 * @param username
	 *            User Id for the user to be deleted
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteUser(String userId) {
		User user = userDao.findById(userId);
		try {
			if (user != null) {
				userDao.delete(user);
			} else {
				logger.info("User with userId {} could not be found", userId);

			}
		} catch (DataAccessException e) {
			logger.warn("User could not be deleted from the database: " + e);

		}
	}

	/**
	 * Service method for getting updating user information
	 * 
	 * @param user
	 *            User for which the information is to be updated
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateUser(User user) {
		
		try {
			userDao.updateUser(user);
		} catch (DataAccessException e) {
			logger.warn("User could not be updated in the database: " + e);

		}
	}

	/**
	 * Service method for retrieving all the users stored in the DB
	 * 
	 * @return List of all users
	 * 
	 */
	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
}
