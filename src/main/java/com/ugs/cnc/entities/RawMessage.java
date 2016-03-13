/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * RawMessage.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.2
 */

package com.ugs.cnc.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class RawMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String deviceId;
	private String deviceType;
	private String messageType;
	private String macAdd; 
	private String batteryStatus; 
	private String payload;
	private String networkId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
	public String getMacAdd() {
		return macAdd;
	}

	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}
	
	

	public String getBatteryStatus() {
		return batteryStatus;
	}

	public void setBatteryStatus(String batteryStatus) {
		this.batteryStatus = batteryStatus;
	}

	public String toString() {

		return new ToStringBuilder(this).append("deviceId", deviceId)
				.append("networkId", networkId)
				.append("messageType", messageType)
				.append("deviceType", deviceType)
				.append("payload", payload)
				.toString();
	}

	public int hashCode() {

		return new HashCodeBuilder(31, 7).append(deviceId).append(deviceType)
				.append(networkId).append(messageType).append(payload)
				.toHashCode();
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
		RawMessage rawMessage = (RawMessage) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(deviceId, rawMessage.deviceId)
				.append(networkId, rawMessage.networkId)
				.append(messageType, rawMessage.messageType)
				.append(deviceType, rawMessage.deviceType)
				.append(payload, rawMessage.payload).isEquals();
	}

}
