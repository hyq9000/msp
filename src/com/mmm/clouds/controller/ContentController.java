package com.mmm.clouds.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.utils.AESOperator;
import com.common.web.WebUtils;
import com.mmm.clouds.model.Content;
import com.mmm.clouds.model.ContentOutline;
import com.mmm.clouds.model.UserContent;
import com.mmm.clouds.service.ContentOutlineService;
import com.mmm.clouds.service.UserContentService;
import com.mmm.clouds.service.impl.WxServiceImpl;
import com.mmm.clouds.utils.StaticResourceGenerator;

/**
 * 内容的维护及发布处理
 * </br>date 2016-5-1
 * @author hyq
 */
@Controller
@RequestMapping(path="/content")
public class ContentController extends SuperController {

	@Autowired private WxServiceImpl wxService;
	@Autowired private ContentOutlineService contentOutlineService;
	@Autowired private UserContentService userContentService;
	@Autowired private StaticResourceGenerator sg;
	/**
	 * 保存内容,生成内容及内容纲要表数据
	 * @param titl 内容标题
	 * @param type 内容类型：0:文章            1:应用            2:外链            3:H5页',
	 * @param head 内容首图
	 * @param link 如是外链，则为完整的外键地址，包括http://部分
	 * @return 返回保存成功的内容纲要id号,格式如下：
	 * {
	 * 	  data:内容纲要id
	 * }
	 */
	@RequestMapping(path="/save")
	@ResponseBody
	public String saveContent(String userIdStr,String title,byte type,String head,String link,String text) throws Exception{
		Content content=new Content();
		content.setContentText(text);
		ContentOutline outline=new ContentOutline();
		outline.setContentLink(link);
		int userId=AESOperator.decryptToInteger(userIdStr);	
		outline.setContentOutlineAuthor(userId);
		outline.setContentOutlineHead(head);
		outline.setContentOutlineTitle(title);
		outline.setContentOutlineType(type);
		contentOutlineService.saveContent(outline, content);
		
		return WebUtils.responseData(outline.getContentOutlineId());
	}
	
	/**
	 * 预发布内容,保存内容，生成静态页，绑定名片，表单等附属内容,生成用户内容表数据
	 * @return
	 */
	@RequestMapping(path="/prePublish")
	@ResponseBody
	public String prePublishContent(long contentOutlineId,String userIdStr,HttpServletRequest request)throws Exception{
		String templateFile=request.getServletContext().getRealPath("/resources/template/NewFile.html");
		int userId=AESOperator.decryptToInteger(userIdStr);	
		
		Map<String,Object> userInfo=(Map<String,Object>)super.cache.get(userIdStr);//获取用户相关信息；
	    
		WxServiceImpl.WxInfo wxInfo=wxService.getWxConfigInformation(userId);
		ContentOutline contentOutline=contentOutlineService.getById(contentOutlineId);
		
		//先获得预发布的文件名;
		
		String fname=sg.generateFileName();
		String urlName=sg.generateUrlPathName(fname);
		long timeStamp=new Date().getTime();
		
		//先写用户内容记录
		UserContent uc=new UserContent();
		uc.setContentOutlineId(contentOutlineId);
		uc.setContentOutlineTitle(contentOutline.getContentOutlineTitle());
		uc.setUserContentReadCount(0);
		uc.setUserContentSpreadCount(0);
		uc.setUserId(userId);
		uc.setContentOutlineAurl(urlName);
		userContentService.add(uc);			
		
		//组织微信JS接口页的相关数据；
		String appId=wxInfo.appId,
				contentOutlineTitle=contentOutline.getContentOutlineTitle(),
				nonceStr=wxInfo.token,				
				contentOutlineHead=contentOutline.getContentOutlineHead(),
				webHome="http://"+super.host+":"+super.port+super.context;		
		Map data=WebUtils.generateMapData(
				new String[]{"appId","contentOutlineTitle",
						"contentOutlineId","nonceStr",
						"contentOutlineHead","webHome"},
				new Object[]{appId,contentOutlineTitle,
						contentOutlineId,nonceStr,
						contentOutlineHead,webHome});
		//生成静态的内容页面，并返回URL
		String accessUrl= sg.article2html(templateFile, data,fname);
	
		
		//更新用户内容表中的“aurl"字段
		uc.setContentOutlineAurl(accessUrl);
		userContentService.updateAccessUrl(uc.getUserContentId(), accessUrl);
		
		
		//响应请求
		return WebUtils.responseData(accessUrl);
	}
	
	/**
	 * 发布内容,预发布内容，生成微信的访问URL;
	 * @return
	 */
	@RequestMapping(path="/publish")
	@ResponseBody
	public String publishContent(){
		return "test";
	}
}
