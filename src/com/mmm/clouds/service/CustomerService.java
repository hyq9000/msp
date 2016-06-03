package com.mmm.clouds.service;

import com.common.utils.EntityService;
import com.mmm.clouds.model.Customer;

public interface CustomerService extends EntityService<Customer>{
	/**
	 * 如果存在与给定参数中相同微信公众号及openid的客户，则不新加，否则
	 * 1，新加一行客户信息入库
	 * 2，写客户关系表,
	 * 3，写用户与客户关系表
	 * @param customer 当前阅读者客户对象，
	 * @param spreadCustomerId 传播者客户ID
	 * @param userId 用户ID 
	 */
	void addCustomer(Customer customer,long spreadCustomerId,int userId) throws Exception;
	
	/**
	 * 查取指定ID的客户昵称
	 * @param customerId 客户id
	 * @return 
	 * @throws Exception
	 */
	String getNickById(long customerId) throws Exception;
}
