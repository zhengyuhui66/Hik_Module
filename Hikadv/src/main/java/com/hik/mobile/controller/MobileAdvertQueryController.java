package com.hik.mobile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.interceptor.HikLog;
import com.hik.mobile.service.IAdvertQueryService;
import com.hik.service.IGetAdvertService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mobile/adPreview")
public class MobileAdvertQueryController extends BaseController{
	@Autowired
	IAdvertQueryService iQueryService;
	
	@Autowired
	IGetAdvertService igetAdvertService;
	
	@RequestMapping("/template")
	@HikLog(content = "��ѯ����ģ��", curd = LogCommon.QUERY)
	public void getAllTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String userid = request.getParameter("userid");
//		String startDate = request.getParameter("start");
//		String endDate = request.getParameter("end");

			try {
				List<JSONObject> rst = iQueryService.getAllTemplate();
				this.setResultInfo(QUERY_SUCCESS_INFO, rst);
			} catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, "���ݻ�ȡ�쳣" + e.toString());
			}
		
		this.returnResponse(response, this.getResultInfo());
		return;

	}
	@RequestMapping("/adspace")
	@HikLog(content = "����ģ��ID��ѯģ����λ", curd = LogCommon.QUERY)
	public void getAdSpaceByTempId(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String tempid = request.getParameter("templateid");
		try {
			List<JSONObject> rst = iQueryService.getSpaceByTemplate("", tempid);
			this.setResultInfo(QUERY_SUCCESS_INFO, rst);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "���ݻ�ȡ�쳣" + e.toString());
		}
	
	this.returnResponse(response, this.getResultInfo());
	}
	@RequestMapping("/adlist")
	@HikLog(content = "��ѯ���й���ز�", curd = LogCommon.QUERY)
	public void getAllAdList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		try {
			String userid=request.getParameter("userid");
			List<JSONObject> rst=null;
			if (StringUtils.isEmpty(userid)) {
				rst = iQueryService.getAllAd();	
			}else{
				rst=iQueryService.getAllAd(userid);
			}
			this.setResultInfo(QUERY_SUCCESS_INFO, rst);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "���ݻ�ȡ�쳣" + e.toString());
		}
	
	this.returnResponse(response, this.getResultInfo());
	}
	
	@RequestMapping("/adView")
	@HikLog(content = "��ѯѡ���زĺ��ģ����ʾ·��", curd = LogCommon.QUERY)
	public void getAdvertView(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String modeid=request.getParameter("id");
			String params=request.getParameter("params");
			if(StringUtils.isBlank(modeid)){
				this.setResultInfo(QUERY_FAILURE_INFO, "��ѡ��ģ��");
			}else if (StringUtils.isBlank(params)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "������ѡ��һ������ز�");
			}else {
			JSONObject json = JSONObject.fromObject(params);
			List result=igetAdvertService.getAdvertView(modeid, json);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
			}
    	 } catch (Exception e) {
 			e.printStackTrace();
 			this.setResultInfo(QUERY_FAILURE_INFO, null);
 		}
    	this.returnResponse(response, this.getResultInfo());
    	return;
	}
	
	
}
