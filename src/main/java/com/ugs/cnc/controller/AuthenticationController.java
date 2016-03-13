/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * UserController.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ugs.cnc.entities.User;
import com.ugs.cnc.service.IUserService;

/**
 * Spring MVC annotated controller for user authentication
 * 
 * @author Capt Bilal
 */

@RequestMapping("/")
@Controller
public class AuthenticationController {

	@Autowired
	private IUserService userService;

	/**
	 * Method for initializing the login page
	 * 
	 * @param model
	 * @return login which corresponds to login.jsp to which the data is to be sent
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String showUserForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	/**
	 * Method for authenticating a user
	 * 
	 * @param model
	 * @param user to be authenticated
	 * @return login which corresponds to login.jsp to which the data is to be sent
	 */
	@RequestMapping(value = "login/authenticate", method = RequestMethod.POST)
	public String authenticateUser(Model model, User user) {
		User applicationUser = userService.findByUserId(user.getUserId());
		if (applicationUser != null) {
			if (user.getPassword().equals(applicationUser.getPassword())) {
				model.addAttribute("authentication", "success");
				model.addAttribute("user", applicationUser);
			} else {
				model.addAttribute("authentication", "failure");
			}
			return "login";
		}
		else {
			model.addAttribute("authentication", "not found");
            return "login";
		}
	}


}
