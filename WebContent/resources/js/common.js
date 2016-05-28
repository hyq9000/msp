
/*
 * 将URL中带的参数解析成一个json对象返回
 */
function parseUrlParameter(url){
	var paramStrs=location.href.split("?");
	if(paramStrs.length<2)
		return null;
	else{
		var params=paramStrs[1].split('&');
		var rs={};
		for(var i=0;i<params.length;i++){
			var kvs=params[i].split("=");
			rs[kvs[0]]=kvs[1];
		}
		return rs;
	}
}