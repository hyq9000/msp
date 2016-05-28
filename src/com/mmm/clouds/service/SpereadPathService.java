package com.mmm.clouds.service;
import java.util.List;
import java.util.Map;

import com.common.utils.EntityService;
import com.mmm.clouds.model.SpreadPath;

public interface SpereadPathService extends EntityService<SpreadPath> {

	byte SPREAD_TYPE_READ=0,//阅读类型
	SPREAD_TYPE_SPREAD=1;//传播（转发）类型
	
	List<Map<String,Object>> getContentRelation(long userContentId) throws Exception;
	
}
