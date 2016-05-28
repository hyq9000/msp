package com.mmm.clouds.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Id;
import javax.persistence.Table;

import com.common.dbutil.MyBatisEntity;


/**
 * The persistent class for the t_content_tag database table.
 * 
 */
@Entity
@Table(name="t_content_tag")
@MyBatisEntity(namespace = "ContentTag")
public class ContentTag extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long contentTagId;
	private Long contentOutlineId;
	
	private int tagId;
	private String tagName;

	public ContentTag() {
	}

	@Id
	@Column(name="CONTENT_TAG_ID")
	public Long getContentTagId() {
		return contentTagId;
	}



	public void setContentTagId(Long contentTagId) {
		this.contentTagId = contentTagId;
	}



	@Column(name="CONTENT_OUTLINE_ID")
	public Long getContentOutlineId() {
		return this.contentOutlineId;
	}

	public void setContentOutlineId(Long contentOutlineId) {
		this.contentOutlineId = contentOutlineId;
	}


	@Column(name="TAG_ID")
	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}


	@Column(name="TAG_NAME")
	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}