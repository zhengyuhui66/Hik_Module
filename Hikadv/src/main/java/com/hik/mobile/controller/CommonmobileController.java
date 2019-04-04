package com.hik.mobile.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.interceptor.HikLog;
import com.hik.service.ICommonService;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/mobile/common")
public class CommonmobileController  extends BaseController{
	
	@Autowired
	private ICommonService iCommonService;
	/**
	 * 查询所有的广告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryAllModel")
	public void queryAllModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String cycid=request.getParameter("cycid");
	    	List result = iCommonService.queryAllModel(cycid);
	    	this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	
	 
	
	/**
	 * 查询所有的广告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryModelNum")
	public void queryModelNum(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String modelid=request.getParameter("id");
	    	String result = iCommonService.queryModelNum(modelid);
	    	this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	/**
	 * 查询广告集列表
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryAdvgroup")
	public void queryAdvgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result=iCommonService.queryAdvgroup();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 查询所有的广告记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryAllAdvert")
	public void queryAllAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String userid=null;
	    	String RoleId = CommonUtil.getTrid(request);
	    	if(CommonSence.ADERT_USER.equals(RoleId)){
	    		userid=CommonUtil.getUserId(request);
	    	}
	    	String id=request.getParameter("id");
	    	String pid=request.getParameter("pid");
	    	List<?> result = iCommonService.queryAllAdvert(userid,id,pid);
	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	
	/**
	 * 查询广告集的播放策略
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryplayfun")
	public void queryplayfun(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result = iCommonService.queryplayfun();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 查询认证周期
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryAuthCyc")
	public void queryAuthCyc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{

			List result= iCommonService.queryAuthCyc();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	@RequestMapping("/querySKIN")
	public String querySKIN(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iCommonService.querySKIN();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	
	/**
	 * 得到广告大类分类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getPropertys")
	public void getPropertys(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result = iCommonService.getPropertys();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    }catch (Exception e){
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 得到广告小类分类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getProperty")
	public void getProperty(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			
			String id=request.getParameter("id");
			if(StringUtils.isEmpty(id)){
				throw new Exception();
			}
			List result = iCommonService.getProperty(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
//			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 查询时间设置
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querytimeSet")
	public void querytimeSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result = iCommonService.querytimeSet();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 查询路线信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryRoadInfo")
	@HikLog(content="查询路线信息",curd=LogCommon.QUERY)
	public void queryRoadInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	List result = iCommonService.getRoute(null);
			this.setResultInfo(QUERY_SUCCESS_INFO,result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	/**
	 * 查询车辆信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryBusInfo")
	@HikLog(content=" 查询车辆信息",curd=LogCommon.QUERY)
	public void queryBusInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String lineId=request.getParameter("lineId");
	    	String busName = request.getParameter("busName");
	    	List result = iCommonService.queryBusInfo(lineId,busName);
	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	
	/**
	 * 查询车辆信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryBus")
	@HikLog(content=" 查询车辆信息",curd=LogCommon.QUERY)
	public void queryBus(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	List result = iCommonService.queryBusInfo();
	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	
	/**
	 * 查询车辆信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryProduct")
	@HikLog(content=" 查询产品信息",curd=LogCommon.QUERY)
	public void queryProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String cycid=request.getParameter("cycid");
	    	String modelid=request.getParameter("modelid");
	    	List result = iCommonService.queryProduct(cycid,modelid);
	    	this.setResultInfo(QUERY_SUCCESS_INFO,result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	
	
}

