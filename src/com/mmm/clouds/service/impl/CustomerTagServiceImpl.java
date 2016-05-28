package com.mmm.clouds.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.common.dbutil.Paging;
import com.common.log.ExceptionLogger;
import com.common.web.WebUtils;
import com.mmm.clouds.model.ContentTag;
import com.mmm.clouds.model.CustomerTag;
import com.mmm.clouds.service.ContentTagService;
import com.mmm.clouds.service.CustomerTagService;

@Service
public class CustomerTagServiceImpl extends DaoMybatisImpl<CustomerTag> implements CustomerTagService {

	@Autowired ContentTagService contentTagService;
	
	@Override
	public void addCustomerTagByContentTag(long contentOutlineId, long customerId) throws Exception {
		List<ContentTag> tags=contentTagService.getContentTags(contentOutlineId);
		//如果内容标签不为空，则将这些标签打给客户；
		if(tags!=null && tags.size()>0){
			String tagIdStr="";
			
			for(ContentTag tag : tags){
				tagIdStr+=tag.getTagId()+",";
			}
			//去掉最后那个","
			tagIdStr=tagIdStr.equals("")?tagIdStr:tagIdStr.substring(0,tagIdStr.length()-1);		
			ExceptionLogger.writeLog("--------------文章的标签："+tagIdStr);
			//查出文章已标的哪些标签是已经被打上客户身上了
	
			List<Map<String,Integer>> hasTagIds=super.executeQuery("hasSome",WebUtils.generateMapData(
					new String[]{"customerId","tagIdStr"},
					new Object[]{customerId,tagIdStr}
					));
			
			ExceptionLogger.writeLog("--------------客户的已有标签数："+hasTagIds.size());
			//删除掉已经打了的标签,并将没有删除的加入新LIST中；
			List<ContentTag> tagsTmp=new ArrayList<ContentTag>();
			Iterator<ContentTag> tmp=tags.iterator();		
			while(tmp.hasNext()){
				ContentTag tag=tmp.next();
				boolean flag=false;
				for(Map<String,Integer> tagId:hasTagIds){				
					if(tagId.get("tagId").equals(tag.getTagId())){
						tmp.remove();
						flag=true;
						break;
					}
				}	
				if(flag==false)
					tagsTmp.add(tag);			
			}
			
			ExceptionLogger.writeLog("--------------客户的要贴标签数："+tagsTmp.size());
			if(tagsTmp.size()>0){
				//批量打上新标签
				super.executeUpdate("addCustomerTag",WebUtils.generateMapData(
						new String[]{"customerId","list"},
						new Object[]{customerId,tagsTmp}
				));		
			}
		}
	}

}
