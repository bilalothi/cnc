/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * INetworkService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import com.ugs.cnc.entities.Network;

import java.util.List;

/**
 * Interface to provide CRUD operations for networks
 *
 * @author Capt Bilal
 */
public interface INetworkService {

    Network findByNetworkId(String networkId);
	List<Network> findByNetworkName(String networkName);
    void saveNetwork(Network network);
    void updateNetwork(Network network);
    void deleteNetwork(String networkId);
    List<Network> getAllNetworks();
}
