/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * UserController.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ugs.cnc.entities.User;
import com.ugs.cnc.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring MVC annotated controller for user administration
 * 
 * @author Capt Bilal
 */

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * Method to save a new user
	 * 
	 * @param user
	 *            User that is to be saved
	 * @return string denoting a success or failure for the save operation
	 */

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String saveUser(User user) {
		User alreadyExistingUser = userService.findByUserId(user.getUserId());

		if (alreadyExistingUser != null) {
			return "failure";
		}
		userService.saveUser(user);
		return "success";
	}

	/**
	 * Method to get user information for all the existing users
	 * 
	 * @return Map containing all the existing users
	 */
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<User>> getAllUsers() {

		List<User> userList = userService.getAllUsers();
		Map<String, List<User>> responseMap = new HashMap<String, List<User>>();

		responseMap.put("users", userList);

		return responseMap;
	}

	/**
	 * Method to search a user by Id
	 * 
	 * @param userId
	 *            User Id for the user to be searched
	 * @return Map containing the users searched
	 */
	@RequestMapping(value = "/searchById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchUserById(
			@RequestParam("userIdSearchParam") String userId) {
		User searchedUser = userService.findByUserId(userId);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (searchedUser == null) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("user", searchedUser);

		return responseMap;
	}

	/**
	 * Method to search users by name
	 * 
	 * @param userName
	 *            First or Last name of the users to be searched
	 * @return Map containing the users searched
	 */
	@RequestMapping(value = "/searchByName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchUsersByName(
			@RequestParam("userNameSearchParam") String name) {
		List<User> searchedUsers = userService.findByUserName(name);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (searchedUsers.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("users", searchedUsers);

		return responseMap;
	}

	/**
	 * Method to search users by Role
	 * 
	 * @param userRole
	 *            Role for users to be searched
	 * @return Map containing the users searched
	 */
	@RequestMapping(value = "/searchByRole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchUsersByRole(
			@RequestParam("userRoleSearchParam") String userRole) {
		List<User> searchedUsers = userService.findByUserRole(userRole);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (searchedUsers.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("users", searchedUsers);

		return responseMap;
	}

	/**
	 * Method to search users by Status
	 * 
	 * @param userStatus
	 *            Status for users to be searched
	 * @return Map containing the users searched
	 */
	@RequestMapping(value = "/searchByStatus", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchUsersByStatus(
			@RequestParam("userStatusSearchParam") String userStatus) {
		List<User> searchedUsers = userService.findByUserStatus(userStatus);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (searchedUsers.isEmpty()) {
			responseMap.put("found", false);
			return responseMap;
		}
		responseMap.put("found", true);
		responseMap.put("users", searchedUsers);

		return responseMap;
	}

	/**
	 * Method to update user information
	 * 
	 * @param user
	 *            user whose information is to be updated
	 * @return string denoting a success or failure for the update operation
	 */

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(@RequestParam("updateUserId") String userId,
			@RequestParam("updateFirstName") String firstName,
			@RequestParam("updateLastName") String lastName,
			@RequestParam("updatePassword") String password,
			@RequestParam("updateUserRole") String userRole,
			@RequestParam("updateUserStatus") String userStatus) {

		User userToUpdate = userService.findByUserId(userId);

		if (userToUpdate == null) {
			return "failure";
		}
		userToUpdate.setFirstName(firstName);
		userToUpdate.setLastName(lastName);
		userToUpdate.setPassword(password);
		userToUpdate.setUserRole(userRole);
		userToUpdate.setUserStatus(userStatus);
	
		userService.updateUser(userToUpdate);
		return "success";

	}

	/*
	 * @RequestMapping(value = "/update", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public String updateUser(User user) {
	 * 
	 * User userToUpdate = userService.findByUserId(user.getUserId());
	 * 
	 * if (userToUpdate == null) { return "failure"; }
	 * userService.updateUser(user); 
	 * return "success";
	 * 
	 * }
	 */
	/**
	 * Method to delete a user
	 * 
	 * @param userId
	 *            user id for the user that is to be deleted
	 * @return string denoting a success or failure for the delete operation
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public String deleteUser(@RequestParam("userIdDeleteParam") String userId) {

		User userToDelete = userService.findByUserId(userId);

		if (userToDelete == null) {
			return "failure";
		}

		userService.deleteUser(userId);
		return "success";
	}

}
