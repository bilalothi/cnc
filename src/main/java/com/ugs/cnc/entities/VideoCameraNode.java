/*
 * Copyright 2013, SensorFlock Ltd.
 *
 * VideoCameraNode.java
 * 
 * Author: Capt Bilal
 */

package com.ugs.cnc.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table (name = "Video_Camera_Node")
public class VideoCameraNode implements Serializable {

	private static final long serialVersionUID = 9L;
	
	@Id
    @Column (name = "Video_Camera_Node_Id")
	private String videoCameraNodeId;
	@Column (name = "Video_Resolution")
	private String videoResolution;
	@Column (name = "Video_Stream_Link")
	private String videoStreamLink;
	@JoinColumn (name = "Video_Camera_Node_Location_Id")
	private Integer videoCameraNodeLocationId;
	@JoinColumn (name = "Video_Camera_Network_Id")
	private String videoCameraNodeNetworkId;

	public String getVideoCameraNodeId() {
		return videoCameraNodeId;
	}

	public void setVideoCameraNodeId(String videoCameraNodeId) {
		this.videoCameraNodeId = videoCameraNodeId;
	}

	public String getVideoResolution() {
		return videoResolution;
	}

	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}

	public String getVideoStreamLink() {
		return videoStreamLink;
	}

	public void setVideoStreamLink(String videoStreamLink) {
		this.videoStreamLink = videoStreamLink;
	}

	public Integer getVideoCameraNodeLocationId() {
		return videoCameraNodeLocationId;
	}

	public void setVideoCameraNodeLocationId(Integer videoCameraNodeLocationId) {
		this.videoCameraNodeLocationId = videoCameraNodeLocationId;
	}

	public String getVideoCameraNodeNetworkId() {
		return videoCameraNodeNetworkId;
	}

	public void setVideoCameraNodeNetworkId(String videoCameraNodeNetworkId) {
		this.videoCameraNodeNetworkId = videoCameraNodeNetworkId;
	}

	public String toString() {

		return new ToStringBuilder(this)
				.append("videoCameraNodeID", videoCameraNodeId)
				.append("videoResolution", videoResolution)
				.append("videoStreamLink", videoStreamLink)
				.append("videoCameraNodeNetworkId", videoCameraNodeNetworkId)
				.append("videoCameraNodeLocationId", videoCameraNodeLocationId)
				.toString();
	}

	public int hashCode() {

		return new HashCodeBuilder(31, 7).append(videoCameraNodeId)
				.append(videoResolution).append(videoStreamLink)
				.append(videoCameraNodeNetworkId)
				.append(videoCameraNodeLocationId).toHashCode();
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
		VideoCameraNode videoCameraNode = (VideoCameraNode) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(videoCameraNodeId, videoCameraNode.videoCameraNodeId)
				.append(videoResolution, videoCameraNode.videoResolution)
				.append(videoStreamLink, videoCameraNode.videoStreamLink)
				.append(videoCameraNodeNetworkId,
						videoCameraNode.videoCameraNodeNetworkId)
				.append(videoCameraNodeLocationId,
						videoCameraNode.videoCameraNodeLocationId).isEquals();
	}

}
