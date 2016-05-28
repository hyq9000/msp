package com.mmm.clouds.service.impl;

import org.springframework.stereotype.Service;
import com.common.dbutil.DaoMybatisImpl;
import com.mmm.clouds.model.Clue;
import com.mmm.clouds.service.ClueService;

@Service
public class ClueServiceImpl extends DaoMybatisImpl<Clue> implements ClueService{

}
