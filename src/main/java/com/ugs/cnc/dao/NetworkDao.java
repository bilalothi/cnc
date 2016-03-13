/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * NetworkDao.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.dao;

import com.ugs.cnc.dao.INetworkDao;
import com.ugs.cnc.entities.Network;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Network DAO Implementation class
 * 
 * @author Capt Bilal
 */
@Repository
public class NetworkDao extends AbstractDao<Network, String> implements
		INetworkDao {

	/**
	 * Constructor for Network DAO
	 * 
	 */
	protected NetworkDao() {
		super(Network.class);
	}

	/**
	 * Method to save a device network to the database
	 * 
	 * @param network
	 *            Device network that is to be saved in the database
	 */
	@Override
	public void saveNetwork(Network network) {
		save(network);
	}

	/**
	 * Method for searching a network by network Id from the database
	 * 
	 * @param networkId
	 *            Network Id for the network to be searched
	 * @return Network with the specified network Id
	 * 
	 */
	@Override
	public Network findNetworkById(String networkId) {
		Network network = findById(networkId);
		return network;
	}

	/**
	 * Method for searching networks by name
	 * 
	 * @param networkName
	 *            Name for networks to be searched
	 * @return List of networks with the specified name
	 * 
	 */
	@Override
	public List<Network> findNetworksByName(String networkName) {
		List<Network> networkList = findByCriteria(Restrictions.eq(
				"networkStatus", networkName));
		return networkList;
	}

	/**
	 * Method for retrieving all the networks stored in the DB
	 * 
	 * @return List of all networks
	 * 
	 */
	@Override
	public List<Network> getAllNetworks() {
		List<Network> networkList = findAll();
		return networkList;
	}

	/**
	 * Method to delete a device network from the database
	 * 
	 * @param network
	 *            Device network that is to be deleted from the database
	 */
	@Override
	public void deleteNetwork(Network network) {
		delete(network);
	}

	/**
	 * Method to update device network information stored in the database
	 * 
	 * @param network
	 *            Device network that is to be updated in the database
	 */
	@Override
	public void updateNetwork(Network network) {
		saveOrUpdate(network);
	}

}
