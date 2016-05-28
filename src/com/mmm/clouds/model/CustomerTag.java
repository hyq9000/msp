package com.mmm.clouds.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

import javax.persistence.Table;


import com.common.dbutil.MyBatisEntity;


/**
 * The persistent class for the t_customer_tag database table.
 * 
 */
@Entity
@Table(name="t_customer_tag")
@MyBatisEntity(namespace = "CustomerTag")
public class CustomerTag extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long customerTagId;
	private Long customerId;
	private int tagId;
	private String tagName;

	public CustomerTag() {
	}


	@Id
	@Column(name="CUSTOMER_TAG_ID")
	public Long getCustomerTagId() {
		return customerTagId;
	}




	public void setCustomerTagId(Long customerTagId) {
		this.customerTagId = customerTagId;
	}




	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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