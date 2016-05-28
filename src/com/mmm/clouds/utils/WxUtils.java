package com.mmm.clouds.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;

import com.common.log.BusinessException;
import com.common.log.ExceptionLogger;

/**
 * 与微信相关的公共逻辑
 * @author hyq
 *
 */
public class WxUtils {
	/**
	 * 根据规则生成 State字段的值串，规则 为 <ul>记录着内容的上下文信息，如转发者数据，深度等；特别约定如下：
	 * <li>其中将客户ID(传播者，也 就是转发者ID),用户内容ID,传播深度,用户名称 附在state字段中，格式为： 用户内容ID+'P'+客户ID+'P'+传播深度+'P'+客户名称（昵称）+'P'+用户ID  如： 245P32P3P张大大P1
	 * <li>如果是该内容是发源者，则客户ID为“用户ID"的负值；
	 * </ul>
	 * @param userContentId
	 * @param spreadCustomerId
	 * @param spreadCustomerNick
	 * @param userId
	 * @return
	 */
	public static String generateStateStr(long userContentId,long spreadCustomerId,String spreadCustomerNick,short spreaDeep){
		return URLEncoder.encode(userContentId+"P"+spreadCustomerId+"P"+spreaDeep+"P"+spreadCustomerNick);	
	}
	
	/**
	 * 根据state字符串，返回一个 WxState对象
	 * @param stateStr
	 * @return
	 */
	public static WxState getWxState(String stateStr) throws Exception{
		try {
			String[] parameters=URLDecoder.decode(stateStr).split("P");
			//获取当前页面传播用户的信息
			String userContentId=parameters[0];
			String spreadCid=parameters[1];
			String spreadDeep=parameters[2];
			String spreadNick=parameters[3];
			//封装数据到对象
			WxState wxState=new WxState();
			wxState.spreadCustomerId=Long.parseLong(spreadCid);
			wxState.spreadCustomerNick=spreadNick;
			wxState.userContentId=Long.parseLong(userContentId);
			wxState.deep=Short.parseShort(spreadDeep);
			
			return wxState;
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, WxUtils.class);
			throw new BusinessException("state状态值传参不正确！",-4);
		}
		
	}
	
	/**
	 * 微信授权URL中的state值，封装的一个类;
	 * @author hyq
	 */
	public static class WxState{
		public long userContentId,//用户内容id
		spreadCustomerId;//分享者客户ID
		public Short deep;//内容传播深度
		public String spreadCustomerNick;//分享者客户昵称
	}
}
