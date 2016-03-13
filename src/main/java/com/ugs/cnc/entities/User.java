/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * User.java
 * 
 * Author: Capt Bilal
 * 
 * Version 1.0
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
@Table (name = "User")
public class User implements Serializable {

	private static final long serialVersionUID = 20L;
	
	@Id
    @Column (name = "User_Id", nullable=false)
	private String userId;
    @Column (name = "First_Name", nullable=false)
	private String firstName;
    @Column (name = "Last_Name", nullable=false)
	private String lastName;
    @Column (name = "Password", nullable=false)
	private String password;
    @Column (name = "User_Status", nullable=false)
	private String userStatus;
    @Column (name = "User_Role", nullable=false)
	private String userRole;

    public User() {
    }

    public User(String userId, String firstName, String lastName, String password, String userRole, String userStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRole = userRole;
        this.userStatus = userStatus;  
    }
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		return fullName.append(firstName).append(" ").append(lastName).toString();
	}
	
	public String toString() {

		return new ToStringBuilder(this).append("userId", userId)
				.append("firstName", firstName)
				.append("lastName", lastName)
				.append("password", password)
				.append("userStatus", userStatus)
				.append("userRole", userRole).toString();
	}

	public int hashCode() {

		return new HashCodeBuilder(31, 7).append(userId)
				.append(firstName).append(lastName)
				.append(password).append(userStatus)
				.append(userRole).toHashCode();
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
		User user = (User) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(userId, user.userId)
				.append(firstName, user.firstName)
				.append(lastName, user.lastName)
				.append(password, user.password)
				.append(userStatus, user.userStatus)
				.append(userRole, user.userRole)
				.isEquals();
	}

}
