package com.hik.controller;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.http.IGtPush;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.messpush.MessPushForServices;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/mpc")
public class MessPushController  extends BaseController{
    @Value("${getappId}")
	private String appId;
  
    @Value("${getappsecret}")
	private String appSecret;
  
    @Value("${getappkey}")
	private String appKey;
  
    @Value("${getmastersecret}")
	private String masterSecret;
    
    @Value("${gethost}")
	private String host;
    
	@Autowired
	private MessPushForServices messPushForServices;
	
	@RequestMapping("/messPush")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "messpush/messpush";
    }
	
	@RequestMapping("/pushMessage")
	public void pushMessage(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String content=request.getParameter("content");
	    	String descr=request.getParameter("descr");
	    	String alisa=request.getParameter("alisa");//ceshi,admin,root,zhu,yibai
	    	String username = CommonUtil.getUserName(request);
	    	
	    	String title=request.getParameter("apnsTitle");
	    	String body=request.getParameter("apnsBody");
	    	int result;
	    	String[] alisas =alisa.split(",");
	    	log.info("内容:"+content+" 别名:"+alisa+" 描述:"+descr);
	    	if ((!StringUtils.isEmpty(title))&&(!StringUtils.isEmpty(body))) {
	    		//兼容IOS离线推送方式
	    		result=messPushForServices.savePushMessage(alisas,content,username,descr,title,body);
			}else{
				result=messPushForServices.savePushMessage(alisas, content,username,descr);
			}
	    	this.setResultInfo(QUERY_SUCCESS_INFO, result);
//	    	this.returnResponse(response,JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "");
		}
	    this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	

	@RequestMapping("/queryForpage")
	public void queryMessPush(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String UserId=null;
	    	String RoleId = CommonUtil.getTrid(request);
	    	int limit=getStartOrLimit(request, "limit");
	    	int start=getStartOrLimit(request, "start");
	    	String searchStr=request.getParameter("searchStr");
	    	if(CommonSence.ADERT_USER.equals(RoleId)){
	    		UserId=CommonUtil.getUserId(request);
	    	}
	    	Page page = messPushForServices.queryForPage(UserId, start, limit, searchStr);
	    	this.returnResponse(response,JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());   
		}
	    return;
	 }
}
