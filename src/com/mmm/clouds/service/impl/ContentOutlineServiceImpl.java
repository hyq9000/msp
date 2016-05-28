package com.mmm.clouds.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.common.dbutil.DaoMybatisImpl;
import com.common.web.WebUtils;
import com.mmm.clouds.model.Content;
import com.mmm.clouds.model.ContentOutline;
import com.mmm.clouds.service.ContentOutlineService;
import com.mmm.clouds.service.ContentService;

@Service
public class ContentOutlineServiceImpl extends DaoMybatisImpl<ContentOutline> implements ContentOutlineService {
	
	@Autowired private ContentService contentService;
	
	@Override
	public void saveContent(ContentOutline outline, Content content) throws Exception {
		contentService.add(content);
		outline.setContentId(content.getContentId());
		super.add(outline);
	}

	@Override
	public void updateStatisticValue(long contentOutlineId, byte type) throws Exception {
		super.executeUpdate("updateStatisticValue", WebUtils.generateMapData(
				new String[]{"type","contentOutlineId"},new Object[]{type,contentOutlineId}));		
	}

	
}
