package com.mmm.clouds.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.TemporalType;

import com.common.dbutil.MyBatisEntity;


/**
 * The persistent class for the t_content database table.
 * 
 */
@Entity
@Table(name="t_content")
@MyBatisEntity(namespace = "Content")
public class Content extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long contentId;
	private String contentText;

	public Content() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONTENT_ID")
	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}


	@Lob
	@Column(name="CONTENT_TEXT")
	public String getContentText() {
		return this.contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

}