/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * Message.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.1
 */

package com.ugs.cnc.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "Message_Log")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Message.class);
	@Id
	@GeneratedValue
	@Column(name = "Message_Id")
	private Integer messageId;
	@Column(name = "Message_Content")
	private String messageContent;
	@Column(name = "Message_Type")
	private String messageType;
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Message_Timestamp")
	private Date messageDate;
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn (name = "Device_Id", nullable = false)
	// private Device messageDevice;
	@Column(name = "Device_Id")
	private String messageDeviceId;
	@Column(name = "Network_Id")
	private String messageNetworkId;
	@Column(name = "Device_Type")
	private String messageDeviceType;
	@Column(name = "Message_Raw_Data")
	private String messageRawData;
	@Column(name = "Device_Mac")
	private String deviceMac;
	@Column(name = "Battery_Status")
	private String batteryStatus;
	@Column(name="Inactive_Devices")
	private String inactiveDevices;
	@Column(name="Explode")
	private Boolean explode;
	 

	public Message() {

		Date currentDate = new Date();
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
		String currentDateString = formatter.format(currentDate);

		try {
			Date formattedDate = formatter.parse(currentDateString);
			setMessageDate(formattedDate);
		} catch (ParseException e) {
			logger.info("Problem with formatting date: {}", e);
		}

	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageDeviceId() {
		return messageDeviceId;
	}

	public void setMessageDeviceId(String messageDeviceId) {
		this.messageDeviceId = messageDeviceId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getMessageNetworkId() {
		return messageNetworkId;
	}

	public void setMessageNetworkId(String messageNetworkId) {
		this.messageNetworkId = messageNetworkId;
	}

	public String getMessageRawData() {
		return messageRawData;
	}

	public void setMessageRawData(String messageRawData) {
		this.messageRawData = messageRawData;
	}

	public String getMessageDeviceType() {
		return messageDeviceType;
	}

	public void setMessageDeviceType(String messageDeviceType) {
		this.messageDeviceType = messageDeviceType;
	}
	
	

	

	public Boolean getExplode() {
		return explode;
	}

	public void setExplode(Boolean explode) {
		this.explode = explode;
	}

	public String getInactiveDevices() {
		return inactiveDevices;
	}

	public void setInactiveDevices(String inactiveDevices) {
		this.inactiveDevices = inactiveDevices;
	}

	public String getBatteryStatus() {
		return batteryStatus;
	}

	public void setBatteryStatus(String batteryStatus) {
		this.batteryStatus = batteryStatus;
	}

	

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String toString() {

		return new ToStringBuilder(this).append("messageId", messageId)
				.append("messageContent", messageContent)
				.append("messageType", messageType)
				.append("messageDeviceId", messageDeviceId)
				.append("messageNetworkId", messageNetworkId)
				.append("messageDate", messageDate)
				.append("messageDeviceType", messageDeviceType)
				.append("messageRawData", messageRawData).toString();
	}

	public int hashCode() {

		return new HashCodeBuilder(31, 7).append(messageId)
				.append(messageContent).append(messageType)
				.append(messageNetworkId).append(messageDeviceId)
				.append(messageDate).append(messageDeviceType)
				.append(messageRawData).toHashCode();
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
		Message message = (Message) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(messageId, message.messageId)
				.append(messageContent, message.messageContent)
				.append(messageType, message.messageType)
				.append(messageNetworkId, message.messageNetworkId)
				.append(messageDeviceId, message.messageDeviceId)
				.append(messageDeviceType, message.messageDeviceType)
				.append(messageRawData, message.messageRawData)
				.append(messageDate, message.messageDate).isEquals();
	}
}