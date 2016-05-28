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

import java.math.BigInteger;


/**
 * The persistent class for the t_customer_relation database table.
 * 
 */
@Entity
@Table(name="t_customer_relation")
@MyBatisEntity(namespace = "CustomerRelation")
public class CustomerRelation extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String crId;
	private String crRelation;
	private Long customerId1;
	private Long customerId2;

	public CustomerRelation() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CR_ID")
	public String getCrId() {
		return this.crId;
	}

	public void setCrId(String crId) {
		this.crId = crId;
	}


	@Column(name="CR_RELATION")
	public String getCrRelation() {
		return this.crRelation;
	}

	public void setCrRelation(String crRelation) {
		this.crRelation = crRelation;
	}


	@Column(name="CUSTOMER_ID1")
	public Long getCustomerId1() {
		return this.customerId1;
	}

	public void setCustomerId1(Long customerId1) {
		this.customerId1 = customerId1;
	}


	@Column(name="CUSTOMER_ID2")
	public Long getCustomerId2() {
		return this.customerId2;
	}

	public void setCustomerId2(Long customerId2) {
		this.customerId2 = customerId2;
	}

}