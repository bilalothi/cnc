/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * Location.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Device_Location")
public class Location implements Serializable {

	private static final long serialVersionUID = 4L;

	@Id
	@Column(name = "Device_Id", nullable=false)
	private String deviceId;
	@Column(name = "latitude", nullable=false)
	private Double latitude;
	@Column(name = "Longitude", nullable=false)
	private Double longitude;
	@Column(name = "Altitude", nullable=false)
	private Double altitude;
	//@OneToOne(mappedBy="deviceLocation", cascade=CascadeType.ALL)
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	/*
	 * @JsonIgnore - To avoid the Json response getting stuck in an infinite loop - it happens
	 * because of hibernate bi-directional annotation association. There may be
	 * a better way to resolve this issue as opposed to using the JsonIgnore
	 * annotation
	 */
	@JsonIgnore
    private Device device;
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Double getlatitude() {
		return latitude;
	}

	public void setlatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String toString() {

		return new ToStringBuilder(this).append("deviceId", deviceId)
				.append("latitude", latitude).append("longitude", longitude)
				.append("altitude", altitude).append("deviceId", deviceId)
				.toString();
	}

	public int hashCode() {

		return new HashCodeBuilder(31, 7).append(deviceId).append(latitude)
				.append(longitude).append(deviceId).toHashCode();
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
		Location location = (Location) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(deviceId, location.deviceId)
				.append(latitude, location.latitude)
				.append(longitude, location.longitude)
				.append(altitude, location.altitude)
				.append(deviceId, location.deviceId).isEquals();
	}

}
