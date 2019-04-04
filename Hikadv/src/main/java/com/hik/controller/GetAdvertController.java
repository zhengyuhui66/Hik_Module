package com.hik.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IGetAdvertService;
import net.sf.json.JSONObject;

@Controller   
@RequestMapping("/getAdvertController")
public class GetAdvertController  extends BaseController{
	
	@Autowired
	private IGetAdvertService igetAdvertService;
    
	@RequestMapping("/advertManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "advertManger/advertManger";
    }
	
	@RequestMapping("/simulateAdvertView")
    public String simulateAdvertView(HttpServletRequest request,HttpServletResponse response){
    	return "advertManger/simulateAdvertView";
    }
	
	
//    @RequestMapping("/queryBrandById")
//	public ModelAndView queryBrandById(HttpServletRequest request,HttpServletResponse response){
//    	
//		Map<String,Object> map = new HashMap<String,Object>();
//		String busId = request.getParameter("busId");
//		List<JSONObject> list = igetAdvertService.getAdvert(busId);
//		String modelUrl="";
//		for(JSONObject obj:list){
//			modelUrl=obj.getString("modelurl");
//			String modelmelid=obj.getString("modelmodeid");
//			map.put("materurl"+modelmelid, obj.getString("materpath")+obj.getString("matername"));
//			map.put("adverturl"+modelmelid, obj.getString("adverturl"));
//		}
//		return new ModelAndView("redirect:"+modelUrl,map);
//	}
	/**
	 * ���ݳ����鿴���Ч��
	 * @param request
	 * @param response
	 */
    @RequestMapping("/queryByBusId")
	public void queryByBusId(HttpServletRequest request,HttpServletResponse response){
    	try{
			String busId = request.getParameter("busId");
			List<JSONObject> list = igetAdvertService.getAdvert(busId);
			String modelUrl="";
			Map<String,Object> map = new HashMap<String,Object>();
			for(JSONObject obj:list){
				modelUrl=obj.getString("modelurl");
				String modelmelid=obj.getString("modelmodeid");
				map.put("materurl"+modelmelid, obj.getString("materpath"));
				map.put("adverturl"+modelmelid, obj.getString("adverturl"));
			}
			List result = new ArrayList();
			result.add(modelUrl);
			result.add(map);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
    	 } catch (Exception e) {
 			e.printStackTrace();
 			this.setResultInfo(QUERY_FAILURE_INFO, null);
 		}
    	this.returnResponse(response, this.getResultInfo());
    	return;
	}
    
    /**
     * Ԥ�����
     * @param request
     * @param response
     */
    @RequestMapping("/getViewAdvert")
	public void getViewAdvert(HttpServletRequest request,HttpServletResponse response){
    	try{
			String modeid=request.getParameter("id");
			String params=request.getParameter("params");
			JSONObject json = JSONObject.fromObject(params);
			List result=igetAdvertService.getAdvertView(modeid, json);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
    	 } catch (Exception e) {
 			e.printStackTrace();
 			this.setResultInfo(QUERY_FAILURE_INFO, null);
 		}
    	this.returnResponse(response, this.getResultInfo());
    	return;
	}
    
    
    /**
     * �жϹ�������Ƿ��ظ�
     * @param request
     * @param response
     */
    @RequestMapping("/getrepeatAdvertName")
	public void getrepeatAdvertName(HttpServletRequest request,HttpServletResponse response){
    	try{
			String name=request.getParameter("name");
			boolean result=igetAdvertService.getrepeatAdvertName(name);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
    	 } catch (Exception e) {
 			e.printStackTrace();
 			this.setResultInfo(QUERY_FAILURE_INFO, null);
 		}
    	this.returnResponse(response, this.getResultInfo());
    	return;
	}
    
    /**
     * ���ݳ����鿴��֤�ɹ���Ͷ��Ч��
     * @param request
     * @param response
     */
    @RequestMapping("/queryByLoginedBusId")
	public void queryByLoginedBusId(HttpServletRequest request,HttpServletResponse response){
    	try{
			String busId = request.getParameter("busId");
			List<JSONObject> list = igetAdvertService.getLoginedAdvert(busId);
			String modelUrl="";
			Map<String,Object> map = new HashMap<String,Object>();
			for(JSONObject obj:list){
				modelUrl=obj.getString("modelurl");
				String modelmelid=obj.getString("modelmodeid");
				map.put("materurl"+modelmelid, obj.getString("materpath"));
				map.put("adverturl"+modelmelid, obj.getString("adverturl"));
			}
			List result = new ArrayList();
			result.add(modelUrl);
			result.add(map);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
    	 } catch (Exception e) {
 			e.printStackTrace();
 			this.setResultInfo(QUERY_FAILURE_INFO, null);
 		}
    	this.returnResponse(response, this.getResultInfo());
    	return;
	}
	/**
	 * ��ҳ�õ����еĹ��
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getAdvert")
	public void getAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr= request.getParameter("searchStr");
			Page page = igetAdvertService.getAdvert(start, limit,searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	
	
	/**
	 * ȡ�õ��е������ṩ�������ѡ��
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getMater")
	public void getMater(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String materTypeid=request.getParameter("materTypeid");
			String materSizeid=request.getParameter("materSizeid");
			String materCreatorid=request.getParameter("materCreatorid");
			List result = igetAdvertService.getMater(materTypeid,materSizeid,materCreatorid);
			this.returnResponse(response, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * ���������Ĺ����Ϣ
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveAdvert")
	@HikLog(content="��������Ϣ",curd=LogCommon.NEWADD)
	public void saveAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String materId = request.getParameter("materid");
			String adverturl = request.getParameter("adverturl");  
			String description=request.getParameter("description");
			String advertname=request.getParameter("advertname");
			String propertysid=request.getParameter("propertysid");
			String propertyid=request.getParameter("propertyid");
			
			String[] putcondIds= request.getParameterValues("putcondId");
			Set<String> putconIdSet = null;
			if(putcondIds!=null){
				putconIdSet=new HashSet<String>(Arrays.asList(putcondIds));				
			}
			String userid=CommonUtil.getUserId(request);
			int result = igetAdvertService.saveAdvert(materId,adverturl,description,advertname,userid,putconIdSet,propertysid,propertyid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * ���µĹ����Ϣ
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateAdvert")
	@HikLog(content="���¹����Ϣ",curd=LogCommon.UPDATE)
	public void updateAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String materId = request.getParameter("materid");
			String adverturl = request.getParameter("adverturl");  
			String description=request.getParameter("description");
			String advertname=request.getParameter("advertname");
			String adverid = request.getParameter("adverid");
			String propertysid=request.getParameter("propertysid");
			String propertyid=request.getParameter("propertyid");
			String[] putcondIds= request.getParameterValues("putcondId");
			Set<String> putconIdSet = null;
			if(putcondIds!=null){
				putconIdSet=new HashSet<String>(Arrays.asList(putcondIds));				
			}
			int result = igetAdvertService.updateAdvert(adverid,materId,adverturl,description,advertname,putconIdSet,propertysid,propertyid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * ɾ�����
	 */
	
	@RequestMapping("/deleteAdvert")
	@HikLog(content="ɾ�������Ϣ",curd=LogCommon.DELETE)
	public void deleteAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String advertid = request.getParameter("advertid");
			String[] advertids = advertid.split(",");
			List list = igetAdvertService.getAdvGrounpById(advertids);
			if(list!=null&&list.size()>0){
				this.setResultInfo(QUERY_FAILURE_INFO, list.toString()+"�Ѵ��ڹ�漯��");
			}else{
				int result = igetAdvertService.deleteAdvert(advertids);
				this.setResultInfo(QUERY_SUCCESS_INFO, result);
			}

	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	

	/**
	 * �õ����С�����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getConditionByAdvid")
	public void getConditionByAdvid(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			
			String id=request.getParameter("id");
			List result = igetAdvertService.getConditionByAdvid(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	/**
	 * �鿴�����Ƿ������ɹ��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getAdvBymaterId")
	public void getAdvBymaterId(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			
//			String[] id=request.getParameterValues("materid");
			String materId = request.getParameter("materId");
			String[] materIds = materId.split(",");
			List result = igetAdvertService.getAdvBymaterId(materIds);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
}
