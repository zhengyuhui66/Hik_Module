package com.hik.framework.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GetContent implements Filter {
	protected Log log = LogFactory.getLog(this.getClass()); 
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		 HttpServletRequest request = (HttpServletRequest) arg0;
		 
		 String url=request.getRequestURI();
		 if(url.contains("/materialController/uploadMateriel")||url.contains("uploadMateriel")){
			 log.info("请求的URL为:"+url);
		 }
		 
		 if(url.contains("/materialController/updateMateriel")||url.contains("updateMateriel")){
			 log.info("请求的URL为:"+url);
		 }
		 
		 if(url.contains("/advertMmController/saveadvertModel")||url.contains("saveadvertModel")){
			 log.info("请求的URL为:"+url);
		 }
		 
		 if(url.contains("/client/upload")||url.contains("upload")){
			 log.info("请求的URL为:"+url);
		 }
		 SysContent.setRequest((HttpServletRequest) arg0);  
	        SysContent.setResponse((HttpServletResponse) arg1);  
	        arg2.doFilter(arg0,arg1);
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
