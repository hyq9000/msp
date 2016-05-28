package com.mmm.clouds.service.impl;

import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.mmm.clouds.model.UserCustomer;
import com.mmm.clouds.service.UserCustomerService;
@Service
public class UserCustomerServiceImpl extends DaoMybatisImpl<UserCustomer> implements UserCustomerService {

}
