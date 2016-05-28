package com.mmm.clouds.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.TemporalType;

import com.common.dbutil.MyBatisEntity;


/**
 * The persistent class for the t_user_content database table.
 * 
 */
@Entity
@Table(name="t_user_content")
@MyBatisEntity(namespace = "UserContent")
public class UserContent extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long userContentId;
	private String contentOutlineAurl;
	private Long contentOutlineId;
	private String contentOutlineTitle;
	private int userId;
	private int userContentReadCount;
	private int userContentSpreadCount;

	public UserContent() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_CONTENT_ID")
	public Long getUserContentId() {
		return this.userContentId;
	}

	public void setUserContentId(Long userContentId) {
		this.userContentId = userContentId;
	}


	@Column(name="CONTENT_OUTLINE_AURL")
	public String getContentOutlineAurl() {
		return this.contentOutlineAurl;
	}

	public void setContentOutlineAurl(String contentOutlineAurl) {
		this.contentOutlineAurl = contentOutlineAurl;
	}


	@Column(name="CONTENT_OUTLINE_ID")
	public long getContentOutlineId() {
		return this.contentOutlineId;
	}

	public void setContentOutlineId(long contentOutlineId) {
		this.contentOutlineId = contentOutlineId;
	}
	
	

	@Column(name="CONTENT_OUTLINE_TITLE")
	public String getContentOutlineTitle() {
		return contentOutlineTitle;
	}


	public void setContentOutlineTitle(String contentOutlineTitle) {
		this.contentOutlineTitle = contentOutlineTitle;
	}


	@Column(name="USER_ID")
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name="USER_CONTENT_READ_COUNT")
	public int getUserContentReadCount() {
		return userContentReadCount;
	}


	public void setUserContentReadCount(int userContentReadCount) {
		this.userContentReadCount = userContentReadCount;
	}

	@Column(name="USER_CONTENT_SPREAD_COUNT")
	public int getUserContentSpreadCount() {
		return userContentSpreadCount;
	}


	public void setUserContentSpreadCount(int userContentSpreadCount) {
		this.userContentSpreadCount = userContentSpreadCount;
	}
	
	

}