/*
 * Copyright 2016, MCS Rwp
 *
 * Author: Capt Bilal
 */
package com.ugs.cnc.controller;

import com.ugs.cnc.entities.ApplMode;
import com.ugs.cnc.entities.Device;
import com.ugs.cnc.service.IApplModeService;
import com.ugs.cnc.service.IDeviceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Spring MVC annotated controller for appl mode related CRUD operations
 *
 * @author Capt Bilal
 */
@RequestMapping("/applMode")
@Controller
public class ApplModeController {

    private static final Logger logger = LoggerFactory.getLogger(ApplModeController.class);

    @Autowired
    private IApplModeService applModeService;

    @Autowired
    private IDeviceService deviceService;

    /**
     * Save Particular Mode for Application
     *
     * @param modeName
     * @return
     */
    @RequestMapping(value = "/saveApplMode", method = RequestMethod.POST)
    @ResponseBody
    public String saveApplMode(@RequestParam("modeName") String modeName) {
        System.out.println("Mode Name : " + modeName);
        if (modeName != null) {
            ApplMode applMode = applModeService.findByMode(modeName);
            if (applMode != null) {
                Device device = deviceService.findByDeviceId("0");
                device.setApplMode(applMode.getId());
                deviceService.updateDevice(device);
            }

            return "success";
        }
        else {
            return "failure";
        }
    }

    /**
     * Get All Modes
     *
     * @return
     */
    @RequestMapping(value = "/getAllModes", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllModes() {

        List<ApplMode> applModeList = applModeService.getAll();
        Map<String, Object> responseMap = new HashMap<String, Object>();

        if (applModeList.isEmpty()) {
            responseMap.put("found", false);
            return responseMap;
        }

        responseMap.put("found", true);
        responseMap.put("applModeList", applModeList);

        return responseMap;
    }

}
