/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * MainDisplayController.java
 * 
 * Author: Capt Bilal
 */
package com.ugs.cnc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ugs.cnc.entities.Device;
import com.ugs.cnc.service.IDeviceService;
import java.util.Map;

/**
 * Spring MVC annotated controller for C2I main display
 *
 * @author Capt Bilal
 */
@RequestMapping("/display")
@Controller
public class MainDisplayController {

    private static final Logger logger = LoggerFactory.getLogger(MainDisplayController.class);

    @Autowired
    private IDeviceService deviceService;

    /**
     * Method to initialize main display page for C2I
     *
     * @param model contains all the device information to be displayed on the
     * UI map after user is logged in. It also contain's the status of whether
     * any devices have been found or not and the role of the user that is
     * logged in.
     * @return display corresponding to display.jsp which processes and displays
     * the data that is sent
     */
    @RequestMapping(value = "/main/{username}/{userRole}", method = RequestMethod.GET)
    public String showMainDisplay(Model model, @PathVariable String username, @PathVariable String userRole) {

        logger.info("User logged in with role: " + userRole);
        List<Device> devices = deviceService.getAllDevices();
        if (devices.isEmpty()) {
            model.addAttribute("found", false);
            return "display";
        }
        model.addAttribute("deviceList", devices);
        model.addAttribute("found", true);
        model.addAttribute("userRole", userRole);
        model.addAttribute("username", username);

        return "display";
    }

    @RequestMapping(value = "/main/devices", method = RequestMethod.GET)
    public String showDeviceDisplay(Map<String, Object> map) {
        Device device = new Device();
        map.put("device", device);
        List<Device> lstDevices = deviceService.getAllDevices(); 
        if( lstDevices.isEmpty() ) {
            System.out.println("Device List is empty..........................................");
        }
        else {
            System.out.println("Device List is not empty..........................................");
        }
        map.put("deviceList", deviceService.getAllDevices());
        return "devices";
    }
}
