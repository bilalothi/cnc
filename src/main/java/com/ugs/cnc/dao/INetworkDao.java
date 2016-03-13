/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * IDeviceDao.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.dao;

import com.ugs.cnc.entities.Network;

import java.util.List;

/**
 * Interface to provide database operations for devices
 *
 * @author Capt Bilal
 */
public interface INetworkDao extends IAbstractDao<Network, String> {
    void saveNetwork(Network network);
    void deleteNetwork(Network network);
    void updateNetwork(Network network);
    Network findNetworkById(String networkId);
    List<Network> findNetworksByName(String networkId);
    List<Network> getAllNetworks();
}
