package com.mmm.clouds.service;

import java.util.Map;

import com.common.utils.EntityService;
import com.mmm.clouds.model.UserContent;

public interface UserContentService extends EntityService<UserContent> { 
	byte STATISTIC_READ=0,
			STATISTIC_SPREAD=1;
	/**
	 * 更新文章纲要的统计值 
	 * @param userContentId 用户内容id
	 * @param type 0:阅读数,1:转发数
	 * @throws Exception
	 */
	void updateStatisticValue(long userContentId,byte type) throws Exception;
	
	/**
	 * 在发布（预发布）内容时，修改CONTENT_OUTLINE_AURL字段的值
	 * @param userContentId 用户内容ID
	 * @param accessUrl 静态页的访问URL,包括带的相关参数
	 * @throws Exception
	 */
	void updateAccessUrl(long userContentId,String accessUrl)throws Exception;
}
