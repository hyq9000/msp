package com.mmm.clouds.service;

import com.common.utils.EntityService;
import com.mmm.clouds.model.Content;
import com.mmm.clouds.model.ContentOutline;

public interface ContentOutlineService extends EntityService<ContentOutline> {

	byte STATISTIC_READ=0,
			STATISTIC_SPREAD=1;
	
	/**
	 * 保存一篇内容：包括内容本身及内容的纲要
	 * @param outline 内容的纲要对象
	 * @param content 内容
	 * @throws Exception
	 */
	void saveContent(ContentOutline outline,Content content) throws Exception;
	
	/**
	 * 更新文章纲要的统计值 
	 * @param contentOutlineId
	 * @param type 0:阅读数,1:转发数
	 * @throws Exception
	 */
	void updateStatisticValue(long contentOutlineId,byte type) throws Exception;
}
