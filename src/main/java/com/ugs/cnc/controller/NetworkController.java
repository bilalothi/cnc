/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * NetworkController.java
 * 
 * Author: Capt Bilal
 */
package com.ugs.cnc.controller;

import com.ugs.cnc.entities.Network;
import com.ugs.cnc.service.INetworkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring MVC annotated controller for network related operations
 *
 * @author Capt Bilal
 */
@RequestMapping("/network")
@Controller
public class NetworkController {

    @Autowired
    private INetworkService networkService;

    /**
     * Method to search networks by Name
     *
     * @param networkName Type for networks to be searched
     * @return Map containing the networks found
     */
    @RequestMapping(value = "/searchByName", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchNetworksByName(
            @RequestParam("networkNameSearchParam") String networkName) {
        List<Network> searchedNetworks = networkService
                .findByNetworkName(networkName);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (searchedNetworks == null) {
            responseMap.put("found", false);
            return responseMap;
        }
        responseMap.put("found", true);
        responseMap.put("networks", searchedNetworks);

        return responseMap;
    }

    /**
     * Method to save a new network (Only one network can be saved) 
     *
     * @param network Network that is to be saved
     * @return success for successfully saving the network
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String saveNetwork(Network network) {
        
        Network existing = networkService.findByNetworkId(network.getNetworkId());
        if (existing != null) {
            return "failure";
        }
        
        List<Network> lstNetwork = networkService.getAllNetworks(); 
        if( lstNetwork.isEmpty() ) {
            networkService.saveNetwork(network);
            return "success";
        }
        else {
            return "failure";
        }
    }

    /**
     * Method to get network information for all the existing networks
     *
     * @return Map containing all the networks
     */
    @RequestMapping(value = "/getAllNetworks", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Network>> searchNetwork() {

        List<Network> networkList = networkService.getAllNetworks();
        Map<String, List<Network>> responseMap = new HashMap<String, List<Network>>();

        responseMap.put("networks", networkList);

        return responseMap;
    }

    /**
     * Method to search a network by Id
     *
     * @param networkId Network Id for the network to be searched
     * @return Map containing the networks found
     */
    @RequestMapping(value = "/searchNetworkById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchNetworkById(
            @RequestParam("networkIdSearchParam") String networkId) {
        Network searchedNetwork = networkService.findByNetworkId(networkId);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (searchedNetwork == null) {
            responseMap.put("found", false);
            return responseMap;
        }
        responseMap.put("found", true);
        responseMap.put("network", searchedNetwork);

        return responseMap;
    }

}
