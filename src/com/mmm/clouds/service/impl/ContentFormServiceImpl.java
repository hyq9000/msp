package com.mmm.clouds.service.impl;



import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;

import com.mmm.clouds.model.ContentForm;
import com.mmm.clouds.service.ContentFormService;
@Service
public class ContentFormServiceImpl extends DaoMybatisImpl<ContentForm> implements ContentFormService {

}
