/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * Device.java
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
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Device_Info")
public class Device implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id
	@Column(name = "Device_Id", nullable = false)
	private String deviceId;
	@Column(name = "Device_Status", nullable = false)
	private String deviceStatus;
	@Column(name = "Device_Type", nullable = false)
	private String deviceType;
	@Column(name = "Device_Type_Char")
	private String deviceTypeChar;
	// MAC Address for device
	@Column(name = "Device_MAC_Address")
	private String devicePhysicalAddress;
	// @OneToOne(cascade = CascadeType.ALL)
	// @PrimaryKeyJoinColumn
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL)
	private Location deviceLocation;
	@Column(name = "Network_Id")
	private String networkId;
	@Column(name = "Battery")
	private Integer deviceBattery;
	@Column(name = "Sensitivity")
	private Integer deviceSensitivity;
	@Column(name = "Signal_Strength")
	private Integer deviceSignalStrength;
	@Column(name = "Link_Quality")
	private Integer deviceLinkQualityIndicator;
	@Column(name = "Primary_Router_Id")
	private String devicePrimaryRouterId;
	@Column(name = "Secondry_Router_Id")
	private String deviceSecondryRouterId;
	@Column(name = "Video_Stream_Link")
	private String videoStreamLink;
	@Column(name = "Last_Alert_Time")
	private Date lastAlertTime; 
	@Column(name = "Explodable")
	private Boolean explodable;

	public String getVideoStreamLink() {
		return videoStreamLink;
	}

	public void setVideoStreamLink(String videoStreamLink) {
		this.videoStreamLink = videoStreamLink;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	
	public Boolean getExplodable() {
		return explodable;
	}

	public void setExplodable(Boolean explodable) {
		this.explodable = explodable;
	}

	public String getDeviceTypeChar() {
		return deviceTypeChar;
	}

	public void setDeviceTypeChar(String deviceTypeChar) {
		this.deviceTypeChar = deviceTypeChar;
	}

	public String getDevicePhysicalAddress() {
		return devicePhysicalAddress;
	}

	public void setDevicePhysicalAddress(String devicePhysicalAddress) {
		this.devicePhysicalAddress = devicePhysicalAddress;
	}

	public Location getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(Location deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public Integer getDeviceSensitivity() {
		return deviceSensitivity;
	}

	public void setDeviceSensitivity(Integer deviceSensitivity) {
		this.deviceSensitivity = deviceSensitivity;
	}

	public Integer getDeviceSignalStrength() {
		return deviceSignalStrength;
	}

	public void setDeviceSignalStrength(Integer deviceSignalStrength) {
		this.deviceSignalStrength = deviceSignalStrength;
	}

	public Integer getDeviceLinkQualityIndicator() {
		return deviceLinkQualityIndicator;
	}

	public void setDeviceLinkQualityIndicator(Integer deviceLinkQualityIndicator) {
		this.deviceLinkQualityIndicator = deviceLinkQualityIndicator;
	}

	public String getDevicePrimaryRouterId() {
		return devicePrimaryRouterId;
	}

	public void setDevicePrimaryRouterId(String devicePrimaryRouterId) {
		this.devicePrimaryRouterId = devicePrimaryRouterId;
	}

	public String getDeviceSecondryRouterId() {
		return deviceSecondryRouterId;
	}

	public void setDeviceSecondryRouterId(String deviceSecondryRouterId) {
		this.deviceSecondryRouterId = deviceSecondryRouterId;
	}

	public Integer getDeviceBattery() {
		return deviceBattery;
	}

	public void setDeviceBattery(Integer deviceBattery) {
		this.deviceBattery = deviceBattery;
	}
	
	
	

	public Date getLastAlertTime() {
		return lastAlertTime;
	}

	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	public String toString() {

		return new ToStringBuilder(this)
				.append("deviceId", deviceId)
				.append("deviceType", deviceType)
				.append("deviceStatus", deviceStatus)
				.append("devicePhysicalAddress", devicePhysicalAddress)
				.append("deviceSensitivity", deviceSensitivity)
				.append("deviceLinkQualityIndicator",
						deviceLinkQualityIndicator)
				.append("deviceBattery", deviceBattery)
				.append("deviceSignalStrength", deviceSignalStrength)
				.append("networkId", networkId)
				.append("deviceLocation", deviceLocation).toString();
	}

	public int hashCode() {

		return new HashCodeBuilder(31, 7).append(deviceId).append(deviceType)
				.append(deviceStatus).append(devicePhysicalAddress)
				.append(deviceLocation).append(deviceSensitivity)
				.append(deviceLinkQualityIndicator).append(deviceBattery)
				.append(deviceSignalStrength).append(networkId).toHashCode();
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
		Device device = (Device) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(deviceId, device.deviceId)
				.append(deviceType, device.deviceType)
				.append(deviceStatus, device.deviceStatus)
				.append(devicePhysicalAddress, device.devicePhysicalAddress)
				.append(deviceSensitivity, device.deviceSensitivity)
				.append(deviceLinkQualityIndicator,
						device.deviceLinkQualityIndicator)
				.append(deviceBattery, device.deviceBattery)
				.append(deviceSignalStrength, device.deviceSignalStrength)
				.append(networkId, device.networkId)
				.append(deviceLocation, device.deviceLocation).isEquals();
	}

}
