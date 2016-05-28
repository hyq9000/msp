package com.mmm.clouds.controller;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.utils.AESOperator;
import com.common.web.WebUtils;
import com.mmm.clouds.model.User;
import com.mmm.clouds.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends SuperController {
	@Autowired private UserService userService;
	/**
	 * 用户登录 ,登陆成功的用户，会将用户的常用信息封装在map对象中，并以用户ID的加密串为KEY,
	 * 放到系统全局缓存中;
	 * @param name 用户名
	 * @param pwd 密码
	 * @param keepLogin 保持登陆 0:不保持，1：保持
	 * @return
	 */
	@RequestMapping(path="/login")
	@ResponseBody
	public String login(String name,String pwd) throws Exception{
		User user=userService.login(name, pwd);
		if(user!=null){
			String userIdStr=AESOperator.encrypt(user.getUserId()+"");
			Map<String,Object> userInfo=WebUtils.generateMapData(
					new String[]{"userName","userId"}, new Object[]{user.getUserName(),userIdStr});
			cache.put(userIdStr,userInfo,3600*24*7);
			return WebUtils.responseData(userInfo);
		}else
			return WebUtils.responseError("用户名或密码不正确!",-1);
	}
	
	/**
	 * 注册新用户
	 * @param name 真实姓名
	 * @param loginName 登陆名
	 * @param pwd 密码
	 * @param enterpriseId 企业ID 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/regist")
	@ResponseBody
	public String regist(String name,String loginName,String pwd,int eid) throws Exception{
		User user=new User();
		user.setEnterpriseId(1);
		user.setUserLoginName(loginName);
		user.setUserName(name);
		user.setUserLoginName(loginName);
		user.setUserPassword(DigestUtils.md5Hex(pwd));
		user.setUserStatus(UserService.STATUS_COMMON);
		userService.add(user);
		return WebUtils.responseCode(1);
	}
}
