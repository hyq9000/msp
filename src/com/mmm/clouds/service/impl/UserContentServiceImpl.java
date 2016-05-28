package com.mmm.clouds.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.common.log.ExceptionLogger;
import com.common.web.WebUtils;
import com.mmm.clouds.model.UserContent;
import com.mmm.clouds.service.UserContentService;

@Service
public class UserContentServiceImpl extends DaoMybatisImpl<UserContent> implements UserContentService {

	@Override
	public void updateStatisticValue(long userContentId, byte type) throws Exception {
		super.executeUpdate("updateStatisticValue", WebUtils.generateMapData(
				new String[]{"type","userContentId"},new Object[]{type,userContentId}));		
		
	}
	
	@Override
	/**
	 * 如果用户ID及内容纲要ID不存在，则新加；否则不操作
	 */
	public void add(UserContent userContent) throws Exception{
		List<Map<String,Object>> rs=this.executeQuery("hasSame", WebUtils.generateMapData(
				new String[]{"userId","contentOutlineId"},
				new Object[]{userContent.getUserId(),userContent.getContentOutlineId()}
				));
		
		if(rs!=null && rs.size()>0){
			ExceptionLogger.writeLog("用户内容已经存在！"+rs.get(0).get("USER_CONTENT_ID"));
			userContent.setUserContentId((Long)rs.get(0).get("USER_CONTENT_ID"));
			return ;
		}
		
		super.add(userContent);
	}
	
	
	@Override
	public void updateAccessUrl(long userContentId, String accessUrl) throws Exception {
		super.executeQuery("updateAccessUrl", WebUtils.generateMapData(
				new String[]{"userContentId","accessUrl"},
				new Object[]{userContentId,accessUrl}));
	}
}
