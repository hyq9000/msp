package com.mmm.clouds.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

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
 * The persistent class for the t_content_outline database table.
 * 
 */
@Entity
@Table(name="t_content_outline")
@MyBatisEntity(namespace = "ContentOutline")
public class ContentOutline extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long contentOutlineId;
	private Long contentId;
	private Integer contentOutlineAuthor;
	private String contentLink;
	private int contentOutlineRcount;
	private int contentOutlineScount;
	private String contentOutlineTitle;
	private byte contentOutlineType;
	private String contentOutlineHead;
	private Timestamp contentOutlineTime;
	
	public ContentOutline() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONTENT_OUTLINE_ID")
	public Long getContentOutlineId() {
		return this.contentOutlineId;
	}

	public void setContentOutlineId(Long contentOutlineId) {
		this.contentOutlineId = contentOutlineId;
	}


	@Column(name="CONTENT_ID")
	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}


	@Column(name="CONTENT_LINK")
	public String getContentLink() {
		return this.contentLink;
	}

	public void setContentLink(String contentLink) {
		this.contentLink = contentLink;
	}


	@Column(name="CONTENT_OUTLINE_RCOUNT")
	public int getContentOutlineRcount() {
		return this.contentOutlineRcount;
	}

	public void setContentOutlineRcount(int contentOutlineRcount) {
		this.contentOutlineRcount = contentOutlineRcount;
	}


	@Column(name="CONTENT_OUTLINE_SCOUNT")
	public int getContentOutlineScount() {
		return this.contentOutlineScount;
	}

	public void setContentOutlineScount(int contentOutlineScount) {
		this.contentOutlineScount = contentOutlineScount;
	}


	@Column(name="CONTENT_OUTLINE_TITLE")
	public String getContentOutlineTitle() {
		return this.contentOutlineTitle;
	}

	public void setContentOutlineTitle(String contentOutlineTitle) {
		this.contentOutlineTitle = contentOutlineTitle;
	}


	@Column(name="CONTENT_OUTLINE_TYPE")
	public byte getContentOutlineType() {
		return this.contentOutlineType;
	}

	public void setContentOutlineType(byte contentOutlineType) {
		this.contentOutlineType = contentOutlineType;
	}

	@Column(name="CONTENT_OUTLINE_HEAD")
	public String getContentOutlineHead() {
		return contentOutlineHead;
	}


	public void setContentOutlineHead(String contentOutlineHead) {
		this.contentOutlineHead = contentOutlineHead;
	}

	@Column(name="CONTENT_OUTLINE_AUTHOR")
	public Integer getContentOutlineAuthor() {
		return contentOutlineAuthor;
	}


	public void setContentOutlineAuthor(Integer contentOutlineAuthor) {
		this.contentOutlineAuthor = contentOutlineAuthor;
	}

	@Column(name="CONTENT_OUTLINE_TIME")
	public Timestamp getContentOutlineTime() {
		return contentOutlineTime;
	}


	public void setContentOutlineTime(Timestamp contentOutlineTime) {
		this.contentOutlineTime = contentOutlineTime;
	}
	
	

}