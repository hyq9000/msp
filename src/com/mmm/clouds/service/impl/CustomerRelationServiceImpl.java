package com.mmm.clouds.service.impl;

import org.springframework.stereotype.Service;
import com.common.dbutil.DaoMybatisImpl;
import com.common.web.WebUtils;
import com.mmm.clouds.model.CustomerRelation;
import com.mmm.clouds.service.CustomerRelationService;
@Service
public class CustomerRelationServiceImpl extends DaoMybatisImpl<CustomerRelation> implements CustomerRelationService {
	
	@Override
	public boolean hasOne(long customer1, long customer2) throws Exception {
		Object rs=super.executeQueryOne("hasOne", WebUtils.generateMapData(
				new String[]{"c1","c2"},
				new Object[]{customer1,customer2}
				));
		return rs!=null;
	}
}
