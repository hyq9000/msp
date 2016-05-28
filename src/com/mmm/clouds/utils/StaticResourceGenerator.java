package com.mmm.clouds.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.SystemUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.common.log.ExceptionLogger;
import com.common.template.FreeMarkUtil;
import com.common.web.HttpUtils;
import com.common.web.WebUtils;

/**
 * 类型描述:静态资源生成器:将静态资源或需要处理成静态资源的，写到静态资源服务器上；
 * 要注意的是：需要在本程序运行的机器上挂载一个远程静态服务器的目录；在两台机器上安装nsf 、 portmap
 * </br>创建时期: 2016年1月27日
 * @author hyq
 */
@Component
public class StaticResourceGenerator {

    /**
     * 静态服务器的域名
     */
    @Value("#{config['static-resource-context']}") private String staticResourceContext;
    /**
     * 静态服务器上共享可写文件目录名
     */
    @Value("#{config['file-server-directory']}") private String fileSeverDirectory;
	/**
	 * 根据给定的”文章“ID的信息，取得文章的视图页HTML，并写入到静态服务器上的指定位置；最后返回该资源在资源服务器上的URL;
	 * @param templateURL 文章内容模板URL ：文章视图页模板的url; 
	 * @return 该HTML在静态资源服务器上的访问URL; 
	 * @throws Exception
	 */
	public  String article2html(String templateURL,String articleId,String fname) throws Exception{
	        
	    HttpResponse response=HttpUtils.get(templateURL,WebUtils.generateMapData("aid", articleId));
	    String html=EntityUtils.toString(response.getEntity());
	    
	   // String fname = generateFileName();		    
	       
        Writer writer=new FileWriter(this.fileSeverDirectory+fname);
        BufferedWriter bwriter= new BufferedWriter(writer);
        bwriter.write(html);
        bwriter.close();
        writer.close();
        
        return generateUrlPathName(fname);
	}
	
	
	/**
	 * 根据给定的”文章“ID的信息，取得文章的视图页HTML，并写入到静态服务器上的指定位置；最后返回该资源在资源服务器上的URL;
	 * @param templateURL 文章内容模板URL ：文章视图页模板的url; 
	 * @param data  freemark的模板值
	 * @return 该HTML在静态资源服务器上的访问URL; 
	 * @throws Exception
	 */
	public  String article2html(String templateURL,Map<String,Object> data,String fname) throws Exception{
		ExceptionLogger.writeLog("根据内容模板"+templateURL+"写数据"+data+"到"+fname);
		FreeMarkUtil.flushContent(templateURL, data, fileSeverDirectory+fname);
        return generateUrlPathName(fname);
	}

	/**
	 * 生成一个相对路径的文件名，包括路径名;规则如: "/年月/UUID.html"
	 * @return
	 */
	public String generateFileName() {
		Date date=new Date();
		String tt=SystemUtils.FILE_SEPARATOR;
	    String fname=date.getYear()+1900+""+(date.getMonth()+1);
	    new File(fileSeverDirectory+SystemUtils.FILE_SEPARATOR+fname).mkdirs();
	    fname+=SystemUtils.FILE_SEPARATOR+UUID.randomUUID()+".html";
		return SystemUtils.FILE_SEPARATOR+fname;
	}
	
	/**
	 * 据给定的文件名称生成可访问的url 路径名称
	 * @param fileName 相对文件名，由generateFileName()方法生成的
	 * @return
	 */
	public String generateUrlPathName(String fileName){
		String urlPath=fileName.replaceAll("\\\\", "/");
		return staticResourceContext+urlPath;
	}
	
	
	/**
	 * 将给定的静态资源数据(如上传的图片，音、视频等）写入到指定位置的静态服务器上；并返回该资源在资源服务器上的URL;
	 * @param resource 资源文件字节数组
	 * @param suffix 资源文件后后辍名(不包括".")
	 * @param path 静态资源服务器共享目录名称
	 * @return 该HTML在静态资源服务器上的HTTP访问URL; 该URL的格式规则为："/r/年月/16位随机码" 如 "/r/201602/afdzaafdzafaadffffffaadfffff"
	 * @throws Exception
	 */
	public String saveResource(byte[] resource,String suffix,String fname) throws Exception{
	    FileOutputStream out=new FileOutputStream(fileSeverDirectory+fname);
        OutputStream bout=new BufferedOutputStream(out);
        bout.write(resource);
        bout.close();
        out.close();
        return generateUrlPathName(fname);
	}
}
