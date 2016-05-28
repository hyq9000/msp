package com.mmm.clouds.service;

import java.util.List;

import com.common.utils.EntityService;
import com.mmm.clouds.model.ContentTag;

public interface ContentTagService extends EntityService<ContentTag> {
	/**
	 * 查取指定内容纲要ID的所有关联标签对象
	 * @param contentOutlineId
	 * @return
	 * @throws Exception
	 */
	List<ContentTag> getContentTags(long contentOutlineId) throws Exception;
}
