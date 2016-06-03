package com.mmm.clouds.service;

import com.common.utils.EntityService;
import com.mmm.clouds.model.CustomerRelation;

public interface CustomerRelationService extends EntityService<CustomerRelation>{
	/**
	 * 查看是否组给定这两个客户的关系；关系只要存在，无所谓方向；
	 * @param customer1 客户1id
	 * @param customer2 客户2id
	 * @return 有则返回true,无同返回false
	 * @throws Exception
	 */
	 boolean hasOne(long customer1,long customer2) throws Exception;
}
