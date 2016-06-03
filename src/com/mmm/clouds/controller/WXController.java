package com.mmm.clouds.controller;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.common.log.ExceptionLogger;
import com.common.utils.AESOperator;
import com.common.web.WebUtils;
import com.mmm.clouds.model.Clue;
import com.mmm.clouds.model.Customer;
import com.mmm.clouds.model.SpreadPath;
import com.mmm.clouds.model.UserContent;
import com.mmm.clouds.service.ClueService;
import com.mmm.clouds.service.ContentOutlineService;
import com.mmm.clouds.service.CustomerService;
import com.mmm.clouds.service.CustomerTagService;
import com.mmm.clouds.service.SpereadPathService;
import com.mmm.clouds.service.UserContentService;
import com.mmm.clouds.service.impl.SpreadPathServiceImpl;
import com.mmm.clouds.service.impl.WxServiceImpl;
import com.mmm.clouds.service.impl.WxServiceImpl.WxInfo;
import com.mmm.clouds.utils.UserUtils;
import com.mmm.clouds.utils.WxUtils;

import net.sf.json.JSONObject;

/**
 * 微信功能入口
 * @date 2016-4-22
 * @author hyq
 */
@Controller
@RequestMapping("/wx")
public class WXController extends SuperController {
	@Autowired UserContentService userContentService;
	@Autowired ClueService clueService;
	@Autowired CustomerService customerService;
	@Autowired ContentOutlineService contentOutlineService;
	@Autowired SpereadPathService spreadPathService;
	@Autowired CustomerTagService customerTagService;
	@Autowired WxServiceImpl wxService;
	
	/**
	 * 微信公众号配置回调入口
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @throws Exception
	 */
	@RequestMapping(path ="")
	@ResponseBody
	public String weixin(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request)
			throws Exception {
		ServletInputStream is = request.getInputStream();
		byte[] buf = new byte[1024];
		while (is.read(buf) != -1);
		ExceptionLogger.writeLog(ExceptionLogger.DEBUG, "***微信***" + new String(buf, "UTF-8").trim());
		return echostr;
	}
	
	

	/**
	 * 将内容ID打包成符合微信页面授权要求的格式
	 * @param userContentId
	 * @param spreadCid 分享（传播者id);,如果是-
	 * @param userId 加密了的用户id;
	 * @return 
	 */
	@RequestMapping(path="/packUrl")
	@ResponseBody
	public String packToWxUrl(long userContentId,long spreadCid,String spreadNick,short spreadDeep)throws Exception{
		//TODO:根据用户内容ID，获得用户id;
		Map<String,Object> userInfo=null;
		if(spreadCid==-1){
			UserContent uc=userContentService.getById(userContentId);
			int userId=uc.getUserId();
			String userStr=AESOperator.encrypt(userId+"");
			userInfo=UserUtils.getCurrentUserFromCache(userStr, cache);
			spreadNick=(String)userInfo.get("name");
		}
		
		String url=wxService.packUrl(spreadCid,spreadNick,userContentId,spreadDeep, super.host,super.port,super.context);
		return WebUtils.responseData(url);
	}
	
	/**
	 * 访问微信封装页后，微信用户阅时，微信回调API,这是所有页面的访问路由入口；
	 * 如：https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8ee0e90954e70b96&redirect_uri=http%3a%2f%2f175.10.38.37%2fmrm%2fact%2fuser%2fwxp&response_type=code&scope=snsapi_base&state=http%3a%2f%2f175.10.38.37%2fmrm%2fact%2fuser%2fwx#wechat_redirect
	 * @param code 
	 * @param state 目标url的唯一标识号;用户内容关联ID
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/wxRead")
	public String wxRead(String code,String state)throws Exception{
		ExceptionLogger.writeLog("方法:wxRead(),微信传来的state:"+state);
		//取得state中的值对象
		WxUtils.WxState stateObj=WxUtils.getWxState(state);
		
		UserContent uc=userContentService.getById(stateObj.userContentId);
		WxServiceImpl.WxInfo wxInfo=wxService.getWxConfigInformation(uc.getUserId());
		
				
		//获取当前页面传播用户的信息

		//获得当前页面阅读用户的信息		
		String tokenAndOpenId=wxService.getAccessToken(wxInfo.appId,wxInfo.rescret, code);
		String[] tmp=tokenAndOpenId.split(":");
		String token=tmp[0],openid=tmp[1];
		
		
		//下面wxService.getCustomerInfo（） 里边如果执行过一次，则会缓存一个用户的json对象；
		String key=code+openid;
		Customer customer=(Customer)cache.get(key);
		if(customer==null){
			//获得当前页面阅读用户的信息	
			JSONObject customerInfo=wxService.getCustomerInfo(token,openid);
			//---------------------------------------------------
			
			//如果是新客户，则写客户, 写客户关系表,写用户与客户关系表
			customer=new Customer();
			customer.setCustomerCity(customerInfo.getString("city"));
			customer.setCustomerCountry(customerInfo.getString("country"));
			customer.setCustomerHead(customerInfo.getString("headimgurl"));
			customer.setCustomerNick(customerInfo.getString("nickname"));
			customer.setCustomerProvincy(customerInfo.getString("province"));
			customer.setCustomerPrivilege(customerInfo.getString("privilege"));
			customer.setCustomerSex(Byte.parseByte(customerInfo.getString("sex")));
			customer.setWxAppId(wxInfo.appId);
			customer.setWxOpenId(customerInfo.getString("openid"));
			customerService.addCustomer(customer,stateObj.spreadCustomerId,uc.getUserId());
			//缓存客户对象
			cache.put(key, customer,3600*60);
						
			//写线索
			Clue clue=new Clue();
			clue.setClueAppId(wxInfo.appId);
			clue.setClueContentTitle(uc.getContentOutlineTitle());
			clue.setClueOpenid(customerInfo.getString("openid"));
			clue.setCluePosition("");
			clue.setCluePositionName("");
			clue.setClueType(ClueService.TYPE_READ);
			clue.setUserContentId(uc.getUserContentId());
			clueService.add(clue);
					
			
			//更新内容提纲状态值，用户内容状态值
			contentOutlineService.updateStatisticValue(uc.getContentOutlineId(), ContentOutlineService.STATISTIC_READ);
			userContentService.updateStatisticValue(stateObj.userContentId, UserContentService.STATISTIC_READ);
			
			//写传播路径
			SpreadPath path=new SpreadPath();
			path.setClueId(clue.getClueId());
			path.setContentTitle(uc.getContentOutlineTitle());
			path.setReaderCid(customer.getCustomerId());
			path.setReaderNick(customer.getCustomerNick());
			path.setSpreadCid(stateObj.spreadCustomerId);
			path.setSpreadDeep(stateObj.deep);
			path.setSpreadNick(stateObj.spreadCustomerNick);
			path.setUserContentId(stateObj.userContentId);
			path.setSpreadType(SpereadPathService.SPREAD_TYPE_READ);
			spreadPathService.add(path);
	
			//根据内容纲要的标签，给客户贴标签
			customerTagService.addCustomerTagByContentTag(uc.getContentOutlineId(), customer.getCustomerId());
			//---------------------------------------------------
		}
		
		/* 
		 * 将（客户）的相关信息通过URL带到目标页面上去；以便转发之用;
		 * 1,阅读者客户ID
		 * 2,阅读者客户昵称；
		 * 3,页面标识号(State) 
		 */
		//根据state获得目标URL,并重定向到该目标页
		String target=uc.getContentOutlineAurl()+"?openid="+customer.getWxOpenid()+"&cid="+customer.getCustomerId()+"&cnick="+URLEncoder.encode(customer.getCustomerNick())+"&state="+URLEncoder.encode(state);
		ExceptionLogger.writeLog("方法::wxRead():要转发的URL:"+target);
		return "redirect:"+target;
	}
	
	/**
	 * 微信转发时的回调处理API
	 * @param state 上下文信息串，记录者传播者的信息
	 * @param openid 阅读者id
	 * @param customerId 阅读者的客户ID;
	 * @param customerNick 阅读者的客户昵称;
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/share")
	@ResponseBody
	public String wxShare(String state,String openid,long customerId,String customerNick,HttpServletResponse response)throws Exception{		
		ExceptionLogger.writeLog("-------------------转发（分享）处理开始:"+state);
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		WxUtils.WxState stateObj=WxUtils.getWxState(state);	
		Long userContentId=stateObj.userContentId;
		long spreadId=stateObj.spreadCustomerId;
		short spreadDeep=stateObj.deep;
		String spreadNick=URLDecoder.decode(stateObj.spreadCustomerNick);
		ExceptionLogger.writeLog(spreadNick+":"+stateObj.spreadCustomerNick);
		UserContent uc=userContentService.getById(userContentId);
		WxServiceImpl.WxInfo wxInfo=wxService.getWxConfigInformation(uc.getUserId());
		//写线索
		Clue clue=new Clue();
		clue.setClueAppId(wxInfo.appId);
		clue.setClueContentTitle(uc.getContentOutlineTitle());
		clue.setClueOpenid(openid);
		clue.setCluePosition("");
		clue.setCluePositionName("");
		clue.setClueType(ClueService.TYPE_SPREAD);
		clue.setUserContentId(uc.getUserContentId());
		clueService.add(clue);
		
		//写传播路径
		SpreadPath path=new SpreadPath();
		path.setClueId(clue.getClueId());
		path.setContentTitle(uc.getContentOutlineTitle());		
		path.setReaderCid(customerId);
		path.setReaderNick(URLDecoder.decode(customerNick));
		path.setSpreadCid(spreadId);
		path.setSpreadDeep(spreadDeep);
		path.setSpreadNick(spreadNick);
		path.setUserContentId(userContentId);
		path.setSpreadType(spreadPathService.SPREAD_TYPE_SPREAD);
		spreadPathService.add(path);
		
		//更新内容提纲状态,更新用户内容状态
		contentOutlineService.updateStatisticValue(uc.getContentOutlineId(), ContentOutlineService.STATISTIC_SPREAD);
		userContentService.updateStatisticValue(userContentId, UserContentService.STATISTIC_SPREAD);
		ExceptionLogger.writeLog("-------------------转发（分享）处理结束");
		return WebUtils.responseCode(1);		
	}
	
	/**
	 * 给微信jsapi调用页面的签名方法
	 * @param uid 用户id(一般是负值)
	 * @param timestamp 
	 * @param urlName
	 * @return
	 */
	@RequestMapping(path="/signature")
	@ResponseBody
	public String getSignatureString(long timestamp,String urlName,HttpServletResponse response)throws Exception{
		ExceptionLogger.writeLog("签名参数:"+timestamp+":"+urlName);
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods","GET,POST");
		WxInfo wxInfo=wxService.getWxConfigInformation(1);
		String signature=wxService.getJsapiSignature(wxInfo.token,timestamp, urlName);
		return WebUtils.responseData(signature);
	}
	
	/**
	 * 给微信jsapi调用页面的签名方法
	 * @param uid 用户id(一般是负值)
	 * @param timestamp 
	 * @param urlName
	 * @return
	 */
	@RequestMapping(path="/test")
	public String test()throws Exception{
		return "redirect:"+"http://220.181.57.217";
	}
	

}
