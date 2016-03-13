/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IUserService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import com.ugs.cnc.entities.User;

import java.util.List;

/**
 * Interface to provide CRUD operations for users
 *
 * @author Capt Bilal
 */
public interface IUserService {

	User findByUserId(String userId);
	List<User> findByUserName(String name);
	List<User> findByUserRole(String userRole);
	List<User> findByUserStatus(String userStatus);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String userId);
    List<User> getAllUsers();
}
