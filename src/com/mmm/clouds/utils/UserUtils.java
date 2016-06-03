package com.mmm.clouds.utils;

import java.util.Map;

import com.common.cache.ICache;
import com.common.utils.AESOperator;
import com.common.web.WebUtils;
import com.mmm.clouds.model.User;

/**
 * 封装一些与当前用户及上下文关系的操作
 * @author hyq
 *
 */
public class UserUtils {

	/**
	 * 将登陆成功的用户对象相关数据写到缓存中去；有效时间为7天；
	 * @param currentUser
	 * @param cache
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> saveUserToCache(User currentUser,ICache cache) throws Exception{
		String userIdStr=AESOperator.encrypt(currentUser.getUserId()+"");
		Map<String,Object> userInfo=WebUtils.generateMapData(
				new String[]{"userName","userId"}, new Object[]{currentUser.getUserName(),userIdStr});
		cache.put(userIdStr,userInfo,3600*24*7);
		return userInfo;
	}
	
	public static Map<String,Object> getCurrentUserFromCache(String userStr,ICache cache) throws Exception{
		return (Map<String,Object>)cache.get(userStr);
	}

}
