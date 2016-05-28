package com.mmm.clouds.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.web.WebUtils;
import com.mmm.clouds.service.SpereadPathService;

import net.sf.json.JSONObject;

/**
 * 人脉关系数据分析控制层
 * Date:2016-5-25
 * @author hyq
 *
 */
@Controller
@RequestMapping(path="/relation")
public class PersonRelationControl extends SuperController {
	private @Autowired SpereadPathService spreadPathService;
	
	/**
	 * 
	 * @param userContentId
	 * @return json格式如下：
	 * {
	 * 	code:
	 *  error:
	 *  data:[{
				name : '客户昵称',
				symbol :'image://http://客户头像URL',	
				value : 2,
				children:[{
					name : '客户昵称',
					symbol :'image://http://客户头像URL',	
					value : 2,
					children:[...]//无限层次
				}]
			}]
	 */
	@RequestMapping(path="/content/{userContentId}")
	@ResponseBody
	public String getContentRelation(@PathVariable("userContentId") long userContentId) throws Exception{
		List<Map<String,Object>> list= spreadPathService.getContentRelation(userContentId);
		List<Map<String,Object>> rs=getEchartsTreeJson(list);
		return WebUtils.responseData(rs);
	}
	
	/**
	 * 适配百度之Echarts图形数据组件
	 * @param list
	 * @return
	 * [{
				name : '客户昵称',
				symbol :'image://http://客户头像URL',	
				value : 2,
				children:[{
					name : '客户昵称',
					symbol :'image://http://客户头像URL',	
					value : 2,
					children:[...]//无限层次
				}]
			}]
	 */
	private List<Map<String,Object>> getEchartsTreeJson(List<Map<String,Object>> list){		
		List<Map<String,Object>> rs=new ArrayList<Map<String,Object>>();
		Map<String, Object> contextPre=null, 
				contextCurrent=list.get(0),
				root=list.get(0);
		Map<String,Map> contextMap=new HashMap<>();
		contextMap.put("cur", contextCurrent);
		contextMap.put("pre", contextPre);
		
		root.put("name", list.get(0).get("READER_NICK"));
		root.put("symbol","image://"+list.get(0).get("READER_HEAD"));
		root.put("value",list.get(0).get("READER_CID"));
		root.put("symbolSize", new int[]{50,50});
		Map itemStyle=new HashMap(){{
			put("normal",new HashMap(){{
				put("label",new HashMap(){{
					put("show",false);
				}});
			}});
		}};
		
		root.put("itemStyle",itemStyle);
		root.remove("READER_HEAD");
		/*root.remove("READER_NICK");
		
		root.remove("READER_CID");
		root.remove("SPREAD_CID");*/
		rs.add(root);
		for(int i=1;i<list.size();i++){
			Map<String,Object> map=list.get(i);			
			setContext(contextMap,map);	
			addToContext(contextMap.get("cur"),map);
		}
		return rs;
	}

	/**
	 * 根据传播数据行，找到当前上下文节点及上一个上下文节点；
	 * @param context 存储两个上下文对象
	 * @param data 待处理的数据
	 * @return
	 */
	private void  setContext( Map<String,Map> contextMap,Map<String, Object> data) {		
		
		Map cur=contextMap.get("cur");//当前上下文节点
		Map pre=contextMap.get("pre");//上一个上下文节点
		if(cur==null){
			contextMap.put("pre", cur);
			contextMap.put("cur",data);
		}else{	
			//如果数据行中的传播者id与当前上下文中的阅读者ID一致，说明找到了；
			if(data.get("SPREAD_CID").equals(cur.get("READER_CID")))
				return ;
			else{
				
				/*
				 * 否则找当前上下文的子级节点，如果与某子级节点的相等，则找到该子级节点为当前节点；
				 * 如果子级节点也找不到，则修改上一个上下文节点为当前节点，递归调用该方法
				 */
				List<Map<String,Object>> contextChildren=(List<Map<String,Object>>)cur.get("children");
				boolean find=false;
				for(Map<String,Object> tmp : contextChildren){
					if(data.get("SPREAD_CID").equals(tmp.get("READER_CID"))){					
						contextMap.put("pre", cur);
						contextMap.put("cur",tmp);
						find=true;
						break;
					}
				}
				if(find==false){
					contextMap.put("cur",pre);					
					setContext(contextMap,data);
				}					
			}
			
		}

	}

	/**
	 * 将数据加入到当前操作的上下文节点中
	 * @param currentContext 当前操作的节点；（当前上下文）
	 * @param data 数据
	 */
	private void addToContext(Map<String,Object> currentContext,Map<String,Object> data){		
		List<Map<String,Object>> children=(List<Map<String,Object>> )currentContext.get("children");
		if(children==null){
			children=new ArrayList<Map<String,Object>>();
			currentContext.put("children", children);			
		}		
		data.put("name", data.get("READER_NICK"));
		data.put("symbol","image://"+data.get("READER_HEAD"));
		data.put("value",data.get("READER_CID"));
		data.remove("READER_HEAD");
	/*	data.remove("READER_NICK");
		
		data.remove("READER_CID");
		data.remove("SPREAD_CID");*/
		data.put("symbolSize", new int[]{50,50});
		Map itemStyle=new HashMap(){{
			put("normal",new HashMap(){{
				put("label",new HashMap(){{
					put("show",false);
				}});
			}});
		}};
		
		data.put("itemStyle",itemStyle);
		children.add(data);
	}

}
