/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IUserDao.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.dao;

import java.util.List;

import com.ugs.cnc.entities.User;

/**
 * Interface to provide database operations for users
 * 
 * @author Capt Bilal
 */
public interface IUserDao extends IAbstractDao<User, String> {
	
	void saveUser(User user);
	void updateUser(User user);
	void deleteUser(User user);
	List<User> getAllUsers();
	List<User> findUsersByStatus(String userStatus);
	List<User> findUsersByRole(String userRole);
	List<User> findUsersByName(String name);
	User findUserById(String userId);
}
