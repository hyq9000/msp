/**
 * 
 */
package com.mmm.clouds.service;

import com.common.utils.EntityService;
import com.mmm.clouds.model.CustomerTag;

/**
 * @author hyq
 *
 */
public interface CustomerTagService extends EntityService<CustomerTag> {
	/**
	 * 根据内容纲要的标签，为客户贴标签;
	 * @param contentOutlineId 文章纲要ID
	 * @param customerId 客户ID
	 * @throws Exception
	 */
	void addCustomerTagByContentTag(long contentOutlineId,long customerId) throws Exception;
}
