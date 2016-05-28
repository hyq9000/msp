package com.mmm.clouds.service.impl;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Formatter;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.common.log.ExceptionLogger;
import com.common.utils.BusinessException;
import com.common.web.HttpUtils;
import com.mmm.clouds.utils.WxUtils;

import net.sf.json.JSONObject;


@Service
public class WxServiceImpl extends DaoMybatisImpl<Object> {
	
	/**
	 * 微信配置信息对象
	 * @author hyq
	 */
	public class WxInfo{
		public String appId,rescret,token;
		public WxInfo(String appId,String rescret,String token){
			this.appId=appId;
			this.rescret=rescret;
			this.token=token;			
		}
	}
	
	/**
	 * 微信页面授权url格式；
	 */
	private final String OAUTH2_URL="https://open.weixin.qq.com/connect/oauth2/authorize";
	private final String OAUTH2_URL_FIXED_PARAMETER_1="&response_type=code&scope=snsapi_userinfo";
	private final String OAUTH2_URL_FIXED_PARAMETER_2="#wechat_redirect";
	
	/**
	 * 最终拼成的字符串格式为： https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 * @param spreadCustomerId 内容传播者的客户id;如果是操作员自己,则传操作员的ID负值;
	 * @param spreadCustomerNick 内容传播者的客户的昵称，如是操作员自己，则为操作员姓名
	 * @param userContentId 用户内容ID;
	 * @param spreadDeep 传播深度
	 * @param applicationContext 应用上下文名称
	 * @return 返回符合 https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 */
	public String packUrl(long spreadCustomerId,String spreadCustomerNick,
			long userContentId,short spreadDeep,String host,int port,String applicationContext)throws Exception{
		WxInfo wxInfo=this.getWxConfigInformation(1);
		String portStr=port==80?"":":"+port;
		//微信的redirectUrl字段的值;并进行url编码； 
		String redirectUrl="http://"+host+portStr+applicationContext+"/v1.0/wx/wxRead";
		redirectUrl=URLEncoder.encode(redirectUrl);
		
		//生成state字段值串
		String stateStr=WxUtils.generateStateStr(userContentId, spreadCustomerId, spreadCustomerNick,spreadDeep);
		String url=this.OAUTH2_URL+"?appid="+wxInfo.appId+"&redirect_uri="+redirectUrl+this.OAUTH2_URL_FIXED_PARAMETER_1
				+"&state="+stateStr+this.OAUTH2_URL_FIXED_PARAMETER_2;
		return url;
	}
	
	/**
	 * 取得当前用户所属微信公众号的APPID,Rescret的值；
	 * @param userId
	 */
	public  WxInfo getWxConfigInformation(int userId) throws Exception{
		//获取当前登陆用户的用户ID
		
		//根据用户获得所属微信公众号的配置信息
		
		//return new WxInfo("wx9fa0006b05a1ebb1","cea90c1cd3a4b50ef3eaa6e4e3e17d0f","83u8eie388i3");
		return new WxInfo("wx8ee0e90954e70b96","d4624c36b6795d1d99dcf0547af5443d","83u8eie388i3");		
	}
	
	
	private final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";
	private final String ACCESS_TOKEN_URL_FIXED_PARAMETER="&grant_type=authorization_code";

	/**
	 * 根据当前微信号及从属用户的openID
	 * @param appId 微信公众号
	 * @param secret 加密串
	 * @param code 微信带过来的code值
	 * @param state 微信传过来的目标URL的标识号;
	 * @return "token:openid"格式的字符串
	 */
	public String getAccessToken(String appId,String secret,String code)throws Exception{		
		String key=code;
		String rs=(String)cache.get(key);
		if(rs==null){
			ExceptionLogger.writeLog("token没有被缓存，重新请求获得取!");
			String url=ACCESS_TOKEN_URL+"?appid="+appId+"&secret="+secret+"&code="+code+ACCESS_TOKEN_URL_FIXED_PARAMETER;
			HttpResponse re=HttpUtils.get(url);		    
		    String openId="", token="";
			String json=EntityUtils.toString(re.getEntity());
		    JSONObject obj=JSONObject.fromObject(json);
		    if(obj.containsKey("openid"))
		    	openId = obj.getString("openid");
		    else
		    	throw new BusinessException("获取openid或access_token失败!");
		    if(obj.containsKey("access_token"))
		    	token = obj.getString("access_token");
		    else
		    	throw new BusinessException("获取openid或access_token失败!");
			rs= token+":"+openId;
			cache.put(key, rs,3600*60);	
		}
		return rs;
	}
	
	private final String USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo";
	private final String USER_INFO_URL_FIXED_PARAMETER="&lang=zh_CN";
	/**
	 * 从微信端获到客户的基本信息
	 * @param token
	 * @param openId
	 * @return  格式如：
	 * {
		   "openid":" OPENID",
		   "nickname": NICKNAME,
		   "sex":"1",
		   "province":"PROVINCE"
		   "city":"CITY",
		   "country":"COUNTRY",
		    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46", 
			"privilege":[
			"PRIVILEGE1"
			"PRIVILEGE2"
		    ],
		    "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
		}
	 */
	public JSONObject getCustomerInfo(String token,String openId)throws Exception{
		
		String url=USER_INFO_URL+"?access_token="+token+"&openid="+openId+USER_INFO_URL_FIXED_PARAMETER;
		HttpResponse re=HttpUtils.get(url);
		String tmp=EntityUtils.toString(re.getEntity(),"UTF-8");
		ExceptionLogger.writeLog("从微信获取客户基本信息");
		return JSONObject.fromObject(tmp);		
	}
	
	
	private final String JSAPI_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	private final String GET_ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	
	/**
	 * 获得JS-JDK的签名串
	 * @param noncestr 随机串，也就是appId,，与页面文件的config里的保持一致
	 * @param timestamp 时间戳，与页面文件的config里的保持一致
	 * @param pageUrl 页面的完整
	 * @return
	 */
	public String getJsapiSignature(String noncestr,long timestamp,String pageUrl) throws Exception{
		WxInfo wxInfo=this.getWxConfigInformation(1);
		final String CACH_KEY_TOKEN=wxInfo.appId+"ACCESS_TOKEN",
				CACHE_KEY_TICKET=wxInfo.appId+"JSAPI_TICKET";
		//看缓存中有否，如没有，则请求
		String accessToken=(String)cache.get(CACH_KEY_TOKEN);
		ExceptionLogger.writeLog("缓存中的accessToken :"+accessToken);
		if(accessToken==null){
			//取得access_token;请求格式：https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
			String tokenUrl=GET_ACCESS_TOKEN_URL+"&appid="+wxInfo.appId+"&secret="+wxInfo.rescret;
			
			HttpResponse re=HttpUtils.get(tokenUrl);				
			String json=EntityUtils.toString(re.getEntity());
		    JSONObject obj=JSONObject.fromObject(json);
		    try{
		    	accessToken=obj.getString("access_token");
		    	cache.put(CACH_KEY_TOKEN,accessToken,7100*1000);		
		    }catch(Exception e){
		    	throw new BusinessException(json);				
		    }		
		}	
		//看缓存中有否，如没有，则请求
		String jsapiTicket=(String)cache.get(CACHE_KEY_TICKET);
		ExceptionLogger.writeLog("缓存中的jsapiTicket :"+jsapiTicket);
		if(jsapiTicket==null){
			//取得ticket
			String ticketUrl=JSAPI_TICKET_URL+"?access_token="+accessToken+"&type=jsapi";		
			HttpResponse re=HttpUtils.get(ticketUrl);				
			String json=EntityUtils.toString(re.getEntity());
		    JSONObject obj=JSONObject.fromObject(json);
		    try{
		    	jsapiTicket=obj.getString("ticket");
		    	cache.put(CACHE_KEY_TICKET,jsapiTicket,7100*1000);	
		    }catch(Exception e){
		    	throw new BusinessException(json);
		    }
		}
		String string1="jsapi_ticket="+jsapiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+pageUrl;
		ExceptionLogger.writeLog("要签名的字符串："+string1);
		String s1= DigestUtils.sha1Hex(string1);
		ExceptionLogger.writeLog("签名的字符串1："+s1);
		 MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(string1.getBytes("UTF-8"));
	        String s2 = byteToHex(crypt.digest());
	        ExceptionLogger.writeLog("签名的字符串2："+s2);
	        
	        return s2;
	        
	}
	
	 /**
     * 将字节码转为十六进制数
     * 
     * @param hash 待转字节码数组
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	/**
	 * 获得JS-JDK的签名串
	 * @param noncestr 随机串，也就是appId,，与页面文件的config里的保持一致
	 * @param timestamp 时间戳，与页面文件的config里的保持一致
	 * @param pageUrl 页面的完整
	 * @return
	 */
	public String getJsapiSignature(String appId,String rescret,String noncestr,long timestamp,String pageUrl) throws Exception{
		final String CACH_KEY_TOKEN=appId+"ACCESS_TOKEN",
				CACHE_KEY_TICKET=appId+"JSAPI_TICKET";
		//看缓存中有否，如没有，则请求
		String accessToken=(String)cache.get(CACH_KEY_TOKEN);
		try {
			if(accessToken==null){
				//取得access_token;请求格式：https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
				String tokenUrl=GET_ACCESS_TOKEN_URL+"&appid="+appId+"&secret="+rescret;
				
				HttpResponse re=HttpUtils.get(tokenUrl);				
				String json=EntityUtils.toString(re.getEntity());
			    JSONObject obj=JSONObject.fromObject(json);
				accessToken=obj.getString("access_token");
				cache.put(CACH_KEY_TOKEN,accessToken,7100);				
			}	
			//看缓存中有否，如没有，则请求
			String jsapiTicket=(String)cache.get(CACHE_KEY_TICKET);
			if(jsapiTicket==null){
				//取得ticket
				String ticketUrl=JSAPI_TICKET_URL+"?access_token="+accessToken+"&type=jsapi";		
				HttpResponse re=HttpUtils.get(ticketUrl);				
				String json=EntityUtils.toString(re.getEntity());
			    JSONObject obj=JSONObject.fromObject(json);
			    jsapiTicket=obj.getString("ticket");
			    cache.put(CACHE_KEY_TICKET,jsapiTicket,7100);	
			}
			String string1="jsapi_ticket="+jsapiTicket+"&noncestr="+noncestr+"&url="+pageUrl;
			return DigestUtils.sha1Hex(string1);
		}catch(Exception e){
			ExceptionLogger.writeLog(e, this.getClass());
			return "";
		}
	}
	
}
