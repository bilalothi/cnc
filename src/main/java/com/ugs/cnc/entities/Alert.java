/*
 * Copyright 2013, C4I.
 *
 * Alert.java
 * 
 * Author: Bilal
 * 
 * version 1.2
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
@Table(name = "Alert")
public class Alert implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Alert.class);

	@Id
	@GeneratedValue
	@Column(name = "Alert_Id")
	private Integer alertId;
	@Column(name = "Alert_Type")
	private String alertType;
	@Column(name = "Alert_State")
	private String alertState;
	@Column(name = "Alert_Received_Timestamp")
	private Date alertReceivedDate;
	@Column(name = "Alert_Assigned_Timestamp")
	private Date alertAssignedDate;
	@Column(name = "Alert_Accepted_Timestamp")
	private Date alertAcceptedDate;
	@Column(name = "Alert_Resolved_Timestamp")
	private Date alertResolvedDate;
	@Column(name = "Device_Id")
	private String alertDeviceId;
	@Column(name = "Assigned_To")
	private String assignedTo;
	@Column(name = "Assigned_By")
	private String assignedBy;
	@Column(name = "Assign_Comments")
	private String assignComments;
	@Column(name = "Resolve_Comments")
	private String resolveComments;
	@Column(name = "Flag")
	private String flag;

	public Alert() {
		Date currentDate = new Date();
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
		String currentDateString = formatter.format(currentDate);

		try {
			Date formattedDate = formatter.parse(currentDateString);
			setAlertReceivedDate(formattedDate);
		} catch (ParseException e) {
			logger.info("Problem with formatting date: {}", e);
		}
	}

	public Integer getAlertId() {
		return alertId;
	}

	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}

	public String getAlertState() {
		return alertState;
	}

	public void setAlertState(String alertState) {
		this.alertState = alertState;
	}

	public Date getAlertReceivedDate() {
		return alertReceivedDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setAlertReceivedDate(Date alertReceivedDate) {
		this.alertReceivedDate = alertReceivedDate;
	}

	public Date getAlertAssignedDate() {
		return alertAssignedDate;
	}

	public void setAlertAssignedDate(Date alertAssignedDate) {
		this.alertAssignedDate = alertAssignedDate;
	}

	public Date getAlertAcceptedDate() {
		return alertAcceptedDate;
	}

	public void setAlertAcceptedDate(Date alertAcceptedDate) {
		this.alertAcceptedDate = alertAcceptedDate;
	}

	public Date getAlertResolvedDate() {
		return alertResolvedDate;
	}

	public void setAlertResolvedDate(Date alertResolvedDate) {
		this.alertResolvedDate = alertResolvedDate;
	}

	public String getAlertDeviceId() {
		return alertDeviceId;
	}

	public void setAlertDeviceId(String alertDeviceId) {
		this.alertDeviceId = alertDeviceId;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public String getAssignComments() {
		return assignComments;
	}

	public void setAssignComments(String assignComments) {
		this.assignComments = assignComments;
	}

	public String getResolveComments() {
		return resolveComments;
	}

	public void setResolveComments(String resolveComments) {
		this.resolveComments = resolveComments;
	}

	public String toString() {

		return new ToStringBuilder(this).append("alertId", alertId)
				.append("alertType", alertType)
				.append("alertState", alertState)
				.append("alertDeviceId", alertDeviceId)
				.append("assignedTo", assignedTo)
				.append("assignedBy", assignedBy)
				.append("assignComments", assignComments)
				.append("resolveComments", resolveComments)
				.append("alertReceivedDate", alertReceivedDate)
				.append("alertAcceptedDate", alertAcceptedDate)
				.append("alertAssignedDate", alertAssignedDate)
				.append("alertResolvedDate", alertResolvedDate).toString();
	}

	public int hashCode() {

		return new HashCodeBuilder(31, 7).append(alertId).append(alertType)
				.append(alertState).append(alertDeviceId).append(assignedTo)
				.append(assignedBy).append(assignComments)
				.append(resolveComments).append(alertReceivedDate)
				.append(alertAcceptedDate).append(alertAssignedDate)
				.append(alertResolvedDate).toHashCode();
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
		Alert alert = (Alert) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(alertId, alert.alertId)
				.append(alertType, alert.alertType)
				.append(alertState, alert.alertState)
				.append(alertDeviceId, alert.alertDeviceId)
				.append(assignedTo, alert.assignedTo)
				.append(assignedBy, alert.assignedBy)
				.append(assignComments, alert.assignComments)
				.append(resolveComments, alert.resolveComments)
				.append(alertReceivedDate, alert.alertReceivedDate)
				.append(alertAcceptedDate, alert.alertAcceptedDate)
				.append(alertAssignedDate, alert.alertAssignedDate)
				.append(alertResolvedDate, alert.alertResolvedDate).isEquals();
	}
}