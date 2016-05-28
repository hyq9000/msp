package com.mmm.clouds.service.impl;

import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.common.dbutil.DaoMybatisImpl;
import com.common.log.ExceptionLogger;
import com.common.web.WebUtils;
import com.mmm.clouds.model.Customer;
import com.mmm.clouds.model.CustomerRelation;
import com.mmm.clouds.model.UserCustomer;
import com.mmm.clouds.service.CustomerRelationService;
import com.mmm.clouds.service.CustomerService;
import com.mmm.clouds.service.UserCustomerService;

@Service
public class CustomerServiceImpl extends DaoMybatisImpl<Customer> implements CustomerService {

	@Autowired CustomerRelationService customerRelationService;
	@Autowired UserCustomerService userCustomerService;
	
	@Override	
	public void addCustomer(Customer customer,long relationCustomerId,int userId) throws Exception {
		Map<String,Object> rs=(Map<String,Object>)this.executeQueryOne("hasOne", WebUtils.generateMapData(
				new String[]{"appId", "openid"},
				new Object[]{customer.getWxAppId(),customer.getWxOpenid()}));
		
		if(rs!=null){
			ExceptionLogger.writeLog(customer.getWxAppId()+":"+customer.getWxOpenid()+"该客户信息已经存在!"+rs.get("customerId"));
			customer.setCustomerId((Long)rs.get("customerId"));
		}else{
			ExceptionLogger.writeLog(customer.getWxAppId()+":"+customer.getWxOpenid()+"该客户信息不存在!"+rs);
			//1，新加一行客户信息入库
			super.add(customer);
		}
		
		/*
		 * 2，如果满足以下三个条件：则写客户关系表,
		 *   a,不是用户转发的内容，
		 *   b,不是客户自己转发的内容，
		 *   c，转发者及阅读者关系不存在 
		 */
		
		if(relationCustomerId>0 && relationCustomerId!=customer.getCustomerId() ){
			CustomerRelation cr=new CustomerRelation();
			cr.setCrRelation("朋友圈");			
			cr.setCustomerId1(relationCustomerId);
			cr.setCustomerId2(customer.getCustomerId());
			//如果保存时，报1062错误，说明转发者及阅读者关系已存在 ，忽略即可
			try {
				customerRelationService.add(cr);
			} catch (DuplicateKeyException e) {
				ExceptionLogger.writeLog("----------------------------客户关系已经存在:"+e);
			}
		}
		
		//3，否写用户与客户关系表
		UserCustomer uc=new UserCustomer();
		uc.setCustomerId(customer.getCustomerId());
		uc.setUserId(userId);
		//如果保存时，报1062错误，说明转发者及阅读者关系已存在 ，忽略即可
		try {
			userCustomerService.add(uc);	
		} catch (DuplicateKeyException e) {
			ExceptionLogger.writeLog("----------------------------用户客户关系已经存在:"+e);;
		}		
	}

	@Override
	public String getNickById(long customerId) throws Exception {
		Map<String,Object> rs=(Map<String,Object>)super.executeQueryOne("getNickById", customerId);
		return rs==null?null:(String)rs.get("customerNick");
	}		
}
