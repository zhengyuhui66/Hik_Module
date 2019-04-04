package com.hik.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.interceptor.HikLog;
import com.hik.service.IAdvertPlanService;

import net.sf.json.JSONArray;
@Deprecated
@Controller
@RequestMapping("/advertPlanController")
public class AdvertPlanController extends BaseController{
//	@Autowired
//	private IAdvertPlanService iAdvertPlanService;
//    @RequestMapping("/advertPlan")
//    public String materiel(HttpServletRequest request,HttpServletResponse response){
//    	return "advertManger/advertPlan";
//    }
//	/**
//	 * ��ѯ·����Ϣ
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/queryRoadInfo")
//	@HikLog(content="��ѯ·����Ϣ",curd=LogCommon.QUERY)
//	public void queryRoadInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	List result = iAdvertPlanService.queryRoadInfo();
//			this.setResultInfo(QUERY_SUCCESS_INFO,result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	/**
//	 * ��ѯ������Ϣ
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/queryBusInfo")
//	@HikLog(content=" ��ѯ������Ϣ",curd=LogCommon.QUERY)
//	public void queryBusInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	String lineId=request.getParameter("lineId");
//	    	List result = iAdvertPlanService.queryBusInfo(lineId);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	
////	/**
////	 * ��ѯ���еĹ��
////	 * @param request
////	 * @param response
////	 * @throws Exception
////	 */
////	@RequestMapping("/queryAllModel")
////	public void queryAllModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
////	    try{
////	    	String cycid=request.getParameter("cycid");
////	    	List result = iAdvertPlanService.queryAllModel(cycid);
////	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
////	    } catch (Exception e) {
////			e.printStackTrace();
////			this.setResultInfo(QUERY_FAILURE_INFO, null);
////		}
////	   this.returnResponse(response, this.getResultInfo());   
////	    return;
////	 }
//	/**
//	 * Ͷ�żƻ�·�ߵ�ģ��
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/updateRouteAdvertPlan")
//	@HikLog(content="Ͷ����·����֤����Ĺ��",curd=LogCommon.NEWADD)
//	public void updateRouteAdvertPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{ 
//	    	String stime=request.getParameter("stime");
//	    	String etime=request.getParameter("etime");
//	    	String routeId=request.getParameter("routeId");
//	    	String modelId=request.getParameter("modelId");
//	    	String modeAdvertid=request.getParameter("modeAdvertid");
//	    	int result = iAdvertPlanService.updateRouteAdvertPlan(stime,etime,routeId,modelId,modeAdvertid);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	/**  
//	 * Ͷ�żƻ�·�ߵ�ģ��
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/updateLoginedRouteAdvertPlan")
//	@HikLog(content="Ͷ����·����֤�ɹ�����Ĺ��",curd=LogCommon.NEWADD)
//	public void updateLoginedRouteAdvertPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	String stime=request.getParameter("stime");
//	    	String etime=request.getParameter("etime");
//	    	String routeId=request.getParameter("routeId");
//	    	String modelId=request.getParameter("modelId");
//	    	String modeAdvertid=request.getParameter("modeAdvertid");
//	    	int result = iAdvertPlanService.updateLoginedRouteAdvertPlan(stime,etime,routeId,modelId,modeAdvertid);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	/**
//	 * Ͷ�żƻ�������ģ��
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/updateAdvertPlan")
//	@HikLog(content="Ͷ�ų�������֤����Ĺ��",curd=LogCommon.NEWADD)
//	public void updateAdvertPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	String stime=request.getParameter("stime");
//	    	String etime=request.getParameter("etime");
//	    	String busId=request.getParameter("busId");
//	    	String modelId=request.getParameter("modelId");
//	    	String modelmodeId=request.getParameter("modelmodeId");
//	    	String advertId=request.getParameter("advertId");
//	    	
//	    	int result = iAdvertPlanService.updateAdvertPlan(stime,etime,modelId,busId,modelmodeId,advertId);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	
//	/**
//	 * Ͷ�żƻ�������ģ��
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/updateLoginedAdvertPlan")
//	@HikLog(content="Ͷ�ų�������֤�ɹ�����Ĺ��",curd=LogCommon.NEWADD)
//	public void updateLoginedAdvertPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	
//	    	String stime=request.getParameter("stime");
//	    	String etime=request.getParameter("etime");
//	    	String busId=request.getParameter("busId");
//	    	String modelId=request.getParameter("modelId");
//	    	String modelmodeId=request.getParameter("modelmodeId");
//	    	String advertId=request.getParameter("advertId");
//	    	int result = iAdvertPlanService.updateLoginedAdvertPlan(stime, etime, modelId, busId, modelmodeId, advertId);
////	    	int result = iAdvertPlanService.updateAdvertPlan(stime,etime,modelId,busId,modelmodeId,advertId);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	/**
//	 * Ͷ�żƻ������Ĺ����ʷ��¼
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/getBusAdvertPlanHistory")
//	public void getBusAdvertPlanHistory(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	
//	    	String busid=request.getParameter("busid");
//	    	List<?> result = iAdvertPlanService.getBusAdvertPlanHistory(busid);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	/**
//	 * Ͷ�żƻ������ĵ�½�����ʷ��¼
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/getBusLoginedAdvertPlanHistory")
//	public void getBusLoginedAdvertPlanHistory(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	String busid=request.getParameter("busid");
//	    	List<?> result = iAdvertPlanService.getBusLoginedAdvertPlanHistory(busid);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	/**���Ԥ��
//	 * ����ģ��ID��ѯ���е�ģ����Ϣ
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/queryModelModeById")
//	public void queryModelModeById(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	String modelid=request.getParameter("id");
//	    	List<?> result = iAdvertPlanService.queryModelModeById(modelid);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
//	
//	
//
//	
//	/**
//	 * ȡ����֤�������еĹ���¼
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/cancelPutAdvert")
//	@HikLog(content=" ȡ������֤����Ĺ��",curd=LogCommon.DELETE)
//	public void cancelPutAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
//	    try{
//	    	String str = request.getParameter("busids");
//	    	String[] busid=str.split(",");
//	    	if(busid.length==0){
//	    		throw new Exception();
//	    	}
//	    	List busids = Arrays.asList(busid);  
//	    	int result=iAdvertPlanService.cancelPutAdvert(busids);
//	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, "���޳���");
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
////	 /**
////	  * ����ID
////	  * @return
////	  */
////	 public int cancelPutAdvert(List busids);
//	 
//	/**
//	 * ȡ����֤�ɹ��������еĹ���¼
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//		@RequestMapping("/cancelPutLoginedAdvert")
//		@HikLog(content=" ȡ������֤�ɹ�����Ĺ��",curd=LogCommon.DELETE)
//		public void cancelPutLoginedAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		    try{
//		    	String str = request.getParameter("busids");
//		    	String[] busid=str.split(",");
//		    	if(busid.length==0){
//		    		throw new Exception();
//		    	}
//		    	List busids = Arrays.asList(busid);  
//		    	int result=iAdvertPlanService.cancelPutLoginedAdvert(busids);
//		    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
//		    } catch (Exception e) {
//				e.printStackTrace();
//				this.setResultInfo(QUERY_FAILURE_INFO, "���޳���");
//			}
//		   this.returnResponse(response, this.getResultInfo());   
//		    return;
//		 }
//	 
//		/**
//		 * ��ͣ��֤�������еĹ���¼
//		 * @param request
//		 * @param response
//		 * @throws Exception
//		 */
//		@RequestMapping("/pausePutAdvert")
//		@HikLog(content="��ͣ����֤����Ĺ��",curd=LogCommon.UPDATE)
//		public void pausePutAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		    try{
//		    	String str = request.getParameter("busids");
//		    	String value=request.getParameter("value");
//		    	String[] busid=str.split(",");
//		    	if(busid.length==0){
//		    		throw new Exception();
//		    	}
//		    	List busids = Arrays.asList(busid);  
//		    	int result=iAdvertPlanService.pausePutAdvert(busids,value);
//		    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
//		    } catch (Exception e) {
//				e.printStackTrace();
//				this.setResultInfo(QUERY_FAILURE_INFO, "���޳���");
//			}
//		   this.returnResponse(response, this.getResultInfo());   
//		    return;
//		 }
//	 
//		/**
//		 * ��ͣ��֤�ɹ��������еĹ���¼
//		 * @param request
//		 * @param response
//		 * @throws Exception
//		 */
//		@RequestMapping("/pausePutLoginedAdvert")
//		@HikLog(content="��ͣ����֤�ɹ�����Ĺ��",curd=LogCommon.UPDATE)
//		public void pausePutLoginedAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		    try{
//		    	String str = request.getParameter("busids");
//		    	String value=request.getParameter("value");
//		    	String[] busid=str.split(",");
//		    	if(busid.length==0){
//		    		throw new Exception();
//		    	}
//		    	List busids = Arrays.asList(busid);  
//		    	int result=iAdvertPlanService.pausePutLoginedAdvert(busids,value);
//		    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
//		    } catch (Exception e) {
//				e.printStackTrace();
//				this.setResultInfo(QUERY_FAILURE_INFO, "���޳���");
//			}
//		   this.returnResponse(response, this.getResultInfo());   
//		    return;
//		 }
//		
//		/**
//		 * ����·�߲�ѯ���еĳ���
//		 * @param request
//		 * @param response
//		 * @throws Exception
//		 */
//		@RequestMapping("/queryBusidByRouteIds")
//		@HikLog(content="������·����ѯ����",curd=LogCommon.QUERY)
//		public void queryBusidByRouteIds(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		    try{
//		    	String str = request.getParameter("routeIds");
//		    	String[] routeIds=str.split(",");
//		    	if(routeIds.length==0){
//		    		throw new Exception();
//		    	}
//		    	List routeids = Arrays.asList(routeIds);  
//		    	List result=iAdvertPlanService.queryBusidByRouteIds(routeids);
//		    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
//		    } catch (Exception e) {
//				e.printStackTrace();
//				this.setResultInfo(QUERY_FAILURE_INFO, "���޳���");
//			}
//		   this.returnResponse(response, this.getResultInfo());   
//		    return;
//		 }
		
}