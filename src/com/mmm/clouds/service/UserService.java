package com.mmm.clouds.service;

import com.common.utils.EntityService;
import com.mmm.clouds.model.User;

public interface UserService extends EntityService<User> {
	
	byte STATUS_NO_ACTIVED=0,//未激活
			STATUS_COMMON=1,//正常
			STATUS_STOP=2;//已停用
	/**
	 * 用户登陆	
	 * @param userName 用户名
	 * @param password 密码原文
	 * @return 登陆成功 则返回User对象，否则返回null;
	 * @throws Exception
	 */
	User login(String userName,String password) throws Exception;
}
