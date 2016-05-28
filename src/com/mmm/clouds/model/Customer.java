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


/**
 * The persistent class for the t_customer database table.
 * 
 */
@Entity
@Table(name="t_customer")
@MyBatisEntity(namespace = "Customer")
public class Customer extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long customerId;
	private String customerCountry;
	private Date customerBirthday;
	private String customerCity;
	private String customerHead;
	private String customerName;
	private String customerNick;
	private String customerPhone;
	private String customerProvincy;
	private String customerQq;
	private Byte customerSex;
	private String customerWxId;
	private Integer groupId;
	private String customerPrivilege;
	private String wxAppId;
	private String wxOpenid;

	


	public Customer() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="CUSTOMER_BIRTHDAY")
	public Date getCustomerBirthday() {
		return this.customerBirthday;
	}

	public void setCustomerBirthday(Date customerBirthday) {
		this.customerBirthday = customerBirthday;
	}


	@Column(name="CUSTOMER_CITY")
	public String getCustomerCity() {
		return this.customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}


	@Column(name="CUSTOMER_HEAD")
	public String getCustomerHead() {
		return this.customerHead;
	}

	public void setCustomerHead(String customerHead) {
		this.customerHead = customerHead;
	}


	@Column(name="CUSTOMER_NAME")
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	@Column(name="CUSTOMER_NICK")
	public String getCustomerNick() {
		return this.customerNick;
	}

	public void setCustomerNick(String customerNick) {
		this.customerNick = customerNick;
	}


	@Column(name="CUSTOMER_PHONE")
	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}


	@Column(name="CUSTOMER_PROVINCY")
	public String getCustomerProvincy() {
		return this.customerProvincy;
	}

	public void setCustomerProvincy(String customerProvincy) {
		this.customerProvincy = customerProvincy;
	}


	@Column(name="CUSTOMER_QQ")
	public String getCustomerQq() {
		return this.customerQq;
	}

	public void setCustomerQq(String customerQq) {
		this.customerQq = customerQq;
	}


	@Column(name="CUSTOMER_SEX")
	public byte getCustomerSex() {
		return this.customerSex;
	}

	public void setCustomerSex(byte customerSex) {
		this.customerSex = customerSex;
	}


	@Column(name="CUSTOMER_WX_ID")
	public String getCustomerWxId() {
		return this.customerWxId;
	}

	public void setCustomerWxId(String customerWxId) {
		this.customerWxId = customerWxId;
	}


	@Column(name="GROUP_ID")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	@Column(name="CUSTOMER_RPIVILEGE")
	public String getCustomerPrivilege() {
		return customerPrivilege;
	}


	public void setCustomerPrivilege(String customerPrivilege) {
		this.customerPrivilege = customerPrivilege;
	}


	public String getCustomerCountry() {
		return customerCountry;
	}


	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}


	@Column(name="WX_APP_ID")
	public String getWxAppId() {
		return wxAppId;
	}


	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	@Column(name="WX_OPENID")
	public String getWxOpenid() {
		return wxOpenid;
	}


	public void setWxOpenId(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	
}