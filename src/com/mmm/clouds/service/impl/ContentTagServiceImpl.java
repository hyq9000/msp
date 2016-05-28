/**
 * 
 */
package com.mmm.clouds.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.common.dbutil.Paging;
import com.mmm.clouds.model.ContentTag;
import com.mmm.clouds.service.ContentTagService;

/**
 * @author hyq
 *
 */
@Service
public class ContentTagServiceImpl extends DaoMybatisImpl<ContentTag> implements ContentTagService {

	@Override
	public List<ContentTag> getContentTags(long contentOutlineId) throws Exception {
		return (List<ContentTag>)super.executeQuery("getContentTags", contentOutlineId);
	}

	

}
