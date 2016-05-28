package com.mmm.clouds.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import com.common.dbutil.MyBatisEntity;

import java.util.Date;


/**
 * The persistent class for the t_clue database table.
 * 
 */
@Entity
@Table(name="t_clue")
@MyBatisEntity(namespace = "Clue")
public class Clue extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clueAppId;
	private String clueContentTitle;
	private Long clueId;
	private String clueOpenid;
	private Date clueTime;
	private byte clueType;
	private Long userContentId;
	private String cluePosition;
	private String cluePositionName;

	public Clue() {
	}


	@Column(name="CLUE_APP_ID")
	public String getClueAppId() {
		return this.clueAppId;
	}

	public void setClueAppId(String clueAppId) {
		this.clueAppId = clueAppId;
	}


	@Column(name="CLUE_CONTENT_TITLE")
	public String getClueContentTitle() {
		return this.clueContentTitle;
	}

	public void setClueContentTitle(String clueContentTitle) {
		this.clueContentTitle = clueContentTitle;
	}


	@Column(name="CLUE_ID")
	public Long getClueId() {
		return this.clueId;
	}

	public void setClueId(Long clueId) {
		this.clueId = clueId;
	}


	@Column(name="CLUE_OPENID")
	public String getClueOpenid() {
		return this.clueOpenid;
	}

	public void setClueOpenid(String clueOpenid) {
		this.clueOpenid = clueOpenid;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CLUE_TIME")
	public Date getClueTime() {
		return this.clueTime;
	}

	public void setClueTime(Date clueTime) {
		this.clueTime = clueTime;
	}


	@Column(name="CLUE_TYPE")
	public byte getClueType() {
		return this.clueType;
	}

	public void setClueType(byte clueType) {
		this.clueType = clueType;
	}


	@Column(name="USER_CONTENT_ID")
	public Long getUserContentId() {
		return this.userContentId;
	}

	public void setUserContentId(Long userContentId) {
		this.userContentId = userContentId;
	}

	@Column(name="CLUE_POSITION")
	public String getCluePosition() {
		return cluePosition;
	}


	public void setCluePosition(String cluePosition) {
		this.cluePosition = cluePosition;
	}

	@Column(name="CLUE_POSITION_NAME")
	public String getCluePositionName() {
		return cluePositionName;
	}


	public void setCluePositionName(String cluePositionName) {
		this.cluePositionName = cluePositionName;
	}
	
	

}