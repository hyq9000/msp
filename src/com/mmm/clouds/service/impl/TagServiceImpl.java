package com.mmm.clouds.service.impl;

import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.mmm.clouds.model.Tag;
import com.mmm.clouds.service.TagService;
@Service
public class TagServiceImpl extends DaoMybatisImpl<Tag> implements TagService {

}
