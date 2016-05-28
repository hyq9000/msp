package com.mmm.clouds.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.common.log.ExceptionLogger;
import com.common.web.WebUtils;
import com.mmm.clouds.model.SpreadPath;
import com.mmm.clouds.service.CustomerService;
import com.mmm.clouds.service.SpereadPathService;
@Service
public class SpreadPathServiceImpl extends DaoMybatisImpl<SpreadPath> implements SpereadPathService{

	@Autowired CustomerService customerService;
	
	@Override
	/**
	 * 当传播路径记录不存在时，加新一条新的传播路径；
	 * 当传播者，阅读者，用户内容三者与要加的对应数据相同时，则视为已存在；
	 */
	public void add(SpreadPath spreadPath) throws Exception{
		Map<String,Object> rs=(Map<String,Object>)super.executeQueryOne("hasSame", WebUtils.generateMapData(
				new String[]{"spreadCid","readerCid","userContentId","spreadDeep"},
				new Object[]{spreadPath.getSpreadCid(),spreadPath.getReaderCid(),spreadPath.getUserContentId()
						,spreadPath.getSpreadDeep()}));
		//如果已经此人的读类型记录，但本次要写提传播类型，则修改为传播类型
		if(rs!=null){
			ExceptionLogger.writeLog("丝丝丝丝丝丝丝丝丝丝丝丝丝丝rs:"+rs.get("SPREAD_TYPE")+"::::this"+spreadPath.getSpreadType()+":::"+rs.get("SPREAD_TYPE").equals(spreadPath.getSpreadType()));
			if(spreadPath.getSpreadType() > (Integer)rs.get("SPREAD_TYPE"))
				super.executeUpdate("updateSpreadType", rs.get("PATH_ID"));				
			//如果已有此人的转发记录，或本次要写的读类型记录已经存在，则退出
			else 	
				return ;
		//如果没有记录，则加一条记录
		}else if(rs==null)
			super.add(spreadPath);
	}
	
	
	@Override
	public List<Map<String, Object>> getContentRelation(long userContentId) throws Exception {
		return super.executeQuery("getContentRelation", userContentId);
	}
}
