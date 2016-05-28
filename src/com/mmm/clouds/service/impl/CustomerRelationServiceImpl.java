package com.mmm.clouds.service.impl;

import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.mmm.clouds.model.CustomerRelation;
import com.mmm.clouds.service.CustomerRelationService;
@Service
public class CustomerRelationServiceImpl extends DaoMybatisImpl<CustomerRelation> implements CustomerRelationService {

}
