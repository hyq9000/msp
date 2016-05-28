package com.mmm.clouds.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import com.common.dbutil.MyBatisEntity;



/**
 * The persistent class for the t_user_customer database table.
 * 
 */
@Entity
@Table(name="t_user_customer")
@MyBatisEntity(namespace = "UserCustomer")
public class UserCustomer extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long userCustomerId;
	private Long customerId;
	private int userId;

	public UserCustomer() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_CUSTOMER_ID")
	public Long getUserCustomerId() {
		return this.userCustomerId;
	}

	public void setUserCustomerId(Long userCustomerId) {
		this.userCustomerId = userCustomerId;
	}


	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	@Column(name="USER_ID")
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}