/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * Network.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.1
 */
package com.ugs.cnc.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Field_Network")
public class Network implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @Column(name = "Field_Network_Id", unique = true, nullable = false)
    private String networkId;
    @Column(name = "Field_Network_Name")
    private String networkName;
    @Column(name = "Field_Network_Status", nullable = false)
    private String networkStatus;
    @Column(name = "Field_Network_Type")
    private String networkType;

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String toString() {

        return new ToStringBuilder(this).append("networkId", networkId)
                .append("networkName", networkName)
                .append("networkStatus", networkStatus)
                .append("networkType", networkType).toString();
    }

    public int hashCode() {

        return new HashCodeBuilder(31, 7).append(networkId).append(networkName)
                .append(networkStatus).append(networkType).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Network network = (Network) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj))
                .append(networkId, network.networkId)
                .append(networkName, network.networkName)
                .append(networkStatus, network.networkStatus)
                .append(networkType, network.networkType)
                .isEquals();
    }

}
