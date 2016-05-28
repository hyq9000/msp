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
 * The persistent class for the t_user database table.
 * 
 */
@Entity
@Table(name="t_user")
@MyBatisEntity(namespace = "User")
public class User extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userId;
	private int enterpriseId;
	private String userLoginName;
	private String userPassword;
	private String userName;
	private Byte userStatus;

	public User() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	@Column(name="ENTERPRISE_ID")
	public int getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}


	@Column(name="USER_LOGIN_NAME")
	public String getUserLoginName() {
		return this.userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}


	@Column(name="USER_NAME")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Column(name="USER_PASSWORD")
	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name="USER_STATUS")
	public Byte getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(Byte userStatus) {
		this.userStatus = userStatus;
	}
	
	

}