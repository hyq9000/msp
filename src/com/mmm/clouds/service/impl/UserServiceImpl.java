package com.mmm.clouds.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.common.log.BusinessException;
import com.common.web.WebUtils;
import com.mmm.clouds.model.User;
import com.mmm.clouds.service.UserService;
@Service
public class UserServiceImpl extends DaoMybatisImpl<User> implements UserService {

	@Override
	public User login(String userName, String password) throws Exception {
		String encryPwd=DigestUtils.md5Hex(password);
		User user=(User)super.executeQueryOne("login", WebUtils.generateMapData(
				new String[]{"userName","password"},
				new Object[]{userName,encryPwd}
				));
		if(user!=null)
			return user;
		else
			throw new BusinessException("用户名或密码不正确！",-1);
		
	}
	
}
