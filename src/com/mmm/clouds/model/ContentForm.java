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

import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the t_content_form database table.
 * 
 */
@Entity
@Table(name="t_content_form")
@MyBatisEntity(namespace = "ContentForm")
public class ContentForm extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String contentFormId;
	private BigInteger contentOutlineId;
	private BigInteger customerId;
	private String customerNick;
	private String customerTitle;
	private String formContent;
	private Date formSubmitTime;
	private int userContentId;

	public ContentForm() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONTENT_FORM_ID")
	public String getContentFormId() {
		return this.contentFormId;
	}

	public void setContentFormId(String contentFormId) {
		this.contentFormId = contentFormId;
	}


	@Column(name="CONTENT_OUTLINE_ID")
	public BigInteger getContentOutlineId() {
		return this.contentOutlineId;
	}

	public void setContentOutlineId(BigInteger contentOutlineId) {
		this.contentOutlineId = contentOutlineId;
	}


	@Column(name="CUSTOMER_ID")
	public BigInteger getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}


	@Column(name="CUSTOMER_NICK")
	public String getCustomerNick() {
		return this.customerNick;
	}

	public void setCustomerNick(String customerNick) {
		this.customerNick = customerNick;
	}


	@Column(name="CUSTOMER_TITLE")
	public String getCustomerTitle() {
		return this.customerTitle;
	}

	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}


	@Column(name="FORM_CONTENT")
	public String getFormContent() {
		return this.formContent;
	}

	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FORM_SUBMIT_TIME")
	public Date getFormSubmitTime() {
		return this.formSubmitTime;
	}

	public void setFormSubmitTime(Date formSubmitTime) {
		this.formSubmitTime = formSubmitTime;
	}


	@Column(name="USER_CONTENT_ID")
	public int getUserContentId() {
		return this.userContentId;
	}

	public void setUserContentId(int userContentId) {
		this.userContentId = userContentId;
	}

}