
	/**
	 * 微信前端通用功能封装
	 * 时间：2016-05-27
	 * 作者：hyq
	 */
	var WxUtils=(function(){
		/*
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
		function generateStateStr(userContentId,spreadCustomerId,spreadCustomerNick,spreadDeep){
			return userContentId+"P"+spreadCustomerId+"P"+spreadDeep+"P"+URLEncoder.encode(spreadCustomerNick);	
		}
		
		function getStateObj(stateStr){
			var obj={};
			var stateStr=decodeURI(stateStr);//汉字转码
			var state=stateStr.split("P");	
			
			obj.userContentId=parseInt(state[0]);
			obj.spreadCustomerId=parseInt(state[1]);
			obj.spreadDeep=parseInt(state[2]);
			obj.spreadCustomerNick=parseInt(state[3]);
			return obj;
		}
		
		return {
			generateStateStr:generateStateStr,
			getStateObj:getStateObj		
		}
	})();
	
	