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
 * The persistent class for the t_customer_group database table.
 * 
 */
@Entity
@Table(name="t_customer_group")
@MyBatisEntity(namespace = "CustomerGroup")
public class CustomerGroup extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private short groupId;
	private String groupName;
	private int userId;

	public CustomerGroup() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="GROUP_ID")
	public short getGroupId() {
		return this.groupId;
	}

	public void setGroupId(short groupId) {
		this.groupId = groupId;
	}


	@Column(name="GROUP_NAME")
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	@Column(name="USER_ID")
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}