package com.mmm.clouds.service;

import com.common.utils.EntityService;
import com.mmm.clouds.model.Clue;

public interface ClueService extends EntityService<Clue> {
	byte TYPE_READ = 0, // 阅读
			TYPE_SPREAD = 1, // 转发
			TYPE_FIRVORITE = 2;// 收藏

}
