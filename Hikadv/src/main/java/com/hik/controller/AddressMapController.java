package com.hik.controller;

import java.util.List;

import javax.persistence.Column;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.AddressMapService;
import com.hik.service.IPutMangerSetService;
import com.hik.service.IPutProductService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/amapc")
public class AddressMapController extends BaseController {
	
	@Autowired
	private AddressMapService addressMapService;
	
    @RequestMapping("/priorityPutManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/addressMap";
    }
    
    @RequestMapping("/addressgroup")
    public String addressgroup(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/addressgroup";
    }
    
	/**
	 * 分页查询广告投放条件
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryAddress")
	@HikLog(content="页查询所有的地址",curd=LogCommon.QUERY)
	public void queryAddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result = addressMapService.getAllAddress();
			this.returnResponse(response, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }

	   /**
     * 数据分组编辑时未选择终端系统列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListaddress")
	@HikLog(content="页查询所有的终端系统号",curd=LogCommon.QUERY)
	public void queryListclienttype(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			List result = addressMapService.queryListaddress(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
    /**
     * 数据分组编辑时终端系统已选择列表    
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListgaddress")
	@HikLog(content="页查询所有的终端系统号",curd=LogCommon.QUERY)
	public void queryListgAddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	
		try{
			String id = request.getParameter("id");
			if(id==null||StringUtils.isEmpty(id)){
				this.setResultInfo(QUERY_SUCCESS_INFO, null);
			}else{
				List result = addressMapService.queryListgaddress(id);
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
	 * 分页查询广告投放条件
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveaddress")
	@HikLog(content="保存地址",curd=LogCommon.NEWADD)
	public void saveaddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String nameid = request.getParameter("name");  
			String descrid = request.getParameter("descr");  
			String latlongid = request.getParameter("latlong");
			String latlongids[]=null;
			if(latlongid==null){
				throw new Exception();
			}else{
				latlongids=latlongid.split(",");
				if(latlongids.length!=0&&latlongids.length%2!=0){
					throw new Exception();
				}else{
					HttpSession session = request.getSession();
					String userid=session.getAttribute("userId").toString();
					int result=addressMapService.saveaddress(nameid, descrid,userid,latlongids);
					this.setResultInfo(QUERY_SUCCESS_INFO, result);
				}
			}
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 分页查询广告投放条件
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteAddress")
	@HikLog(content="页查询所有的地址",curd=LogCommon.QUERY)
	public void deleteAddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = addressMapService.deleteAddress(ids);
//			this.returnResponse(response, result);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
//	====================================分组===================================
	
	/**
	 * 分页查询终端系统分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querygaddress")
	@HikLog(content="分页查询终端系统分组",curd=LogCommon.QUERY)
	public void querygclientsys(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = addressMapService.querygaddress(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 保存或更新终端系统分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdategaddress")
	@HikLog(content="保存或更新终端系统分组",curd=LogCommon.NEWADD)
	public void saveOrupdategaddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			  String gaddressname=request.getParameter("gaddressname");
			  String gaddressid=request.getParameter("gaddressid");
			  String addressids=request.getParameter("addressids");
			  String gaddressdesc=request.getParameter("gaddressdesc");
			  HttpSession session =request.getSession();
				String userid=session.getAttribute("userId").toString();
				
			int result = addressMapService.saveOrupdategaddress(gaddressid,gaddressname,userid,gaddressdesc,addressids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除终端系统分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletegaddress")
	@HikLog(content="删除终端系统分组",curd=LogCommon.QUERY)
	public void deletegaddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = addressMapService.deletegaddress(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
