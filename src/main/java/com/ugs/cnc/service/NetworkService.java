/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * NetworkService.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ugs.cnc.entities.Network;
import com.ugs.cnc.dao.INetworkDao;
import com.ugs.cnc.service.INetworkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Network service for network related operations
 *
 * @author Capt Bilal
 */
@Service("networkService")
@Transactional(readOnly = true)
public class NetworkService implements INetworkService {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkService.class);
	@Autowired
	private INetworkDao networkDao;

	/**
	 * Service method for searching a network by network Id
	 * 
	 * @param networkId
	 *            Network Id for the network to be searched
	 * @return Network with the specified network Id
	 * 
	 */
	@Override
	public Network findByNetworkId(String networkId) {
		return networkDao.findNetworkById(networkId);
	}

	/**
	 * Service method for searching networks by name
	 * 
	 * @param networkName
	 *            Name for networks to be searched
	 * @return List of networks with the specified name
	 * 
	 */
	@Override
	public List<Network> findByNetworkName(String networkName) {

		logger.info("Retrieving networks with Name: {}, from the database", networkName);
		return networkDao.findNetworksByName(networkName);
	}
	
	/**
	 * Service method for saving a network in the DB
	 * 
	 * @param network
	 *            Network to be saved
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void saveNetwork(Network network) {
		try {
			networkDao.saveNetwork(network);
		} catch (DataAccessException e) {
			logger.warn("Network info could not be added to the database: " + e);

		}
	}

	/**
	 * Service method for deleting a network from the DB
	 * 
	 * @param networkId
	 *            Network Id for the network to be deleted
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteNetwork(String networkId) {
		try {
			Network network = networkDao.findById(networkId);
			if (network != null) {
				networkDao.delete(network);
			} else {
				logger.info("Network not found");
			}
		} catch (DataAccessException e) {
			logger.warn("Network could not deleted from the database: " + e);

		}
	}

	/**
	 * Service method for retrieving all the networks stored in the DB
	 * 
	 * @return List of all networks
	 * 
	 */
	@Override
	public List<Network> getAllNetworks() {

		return networkDao.findAll();
	}

	/**
	 * Service method for updating network information
	 * 
	 * @param network
	 *            Network to be updated
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateNetwork(Network network) {
		try {
			networkDao.saveNetwork(network);
		} catch (DataAccessException e) {
			logger.warn("Network could not be updated in the database: " + e);

		}
	}

}
