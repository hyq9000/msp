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
 * The persistent class for the t_tag database table.
 * 
 */
@Entity
@Table(name="t_tag")
@MyBatisEntity(namespace = "Tag")
public class Tag extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int tagId;
	private String tagDescript;
	private String tagName;
	private int tagParentId;
	private byte tagType;

	public Tag() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TAG_ID")
	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}


	@Column(name="TAG_DESCRIPT")
	public String getTagDescript() {
		return this.tagDescript;
	}

	public void setTagDescript(String tagDescript) {
		this.tagDescript = tagDescript;
	}


	@Column(name="TAG_NAME")
	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}


	@Column(name="TAG_PARENT_ID")
	public int getTagParentId() {
		return this.tagParentId;
	}

	public void setTagParentId(int tagParentId) {
		this.tagParentId = tagParentId;
	}


	@Column(name="TAG_TYPE")
	public byte getTagType() {
		return this.tagType;
	}

	public void setTagType(byte tagType) {
		this.tagType = tagType;
	}

}