package com.hik.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.PhoneMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/pmangc")
public class PhoneMangerController extends BaseController {
	
	@Autowired
	private PhoneMangerService phoneMangerService;
	
    @RequestMapping("/priorityPutManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/phoneManger";
    }
    
    
    /**
     * 数据分组编辑时未选择手机列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListphone")
	@HikLog(content="页查询所有的手机号",curd=LogCommon.QUERY)
	public void queryListphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			List result = phoneMangerService.queryListphone(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
    /**
     * 数据分组编辑时手机已选择列表    
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListgphone")
	@HikLog(content="页查询所有的手机号",curd=LogCommon.QUERY)
	public void queryListgphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			if(id==null||StringUtils.isEmpty(id)){
				this.setResultInfo(QUERY_SUCCESS_INFO, null);
			}else{
				List result = phoneMangerService.queryListgphone(id);
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
	 * 分页查询所有的手机号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryphone")
	@HikLog(content="分页查询所有的手机号",curd=LogCommon.QUERY)
	public void queryphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = phoneMangerService.queryphone(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 保存所有的手机号
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdatephone")
	@HikLog(content="保存所有的手机号",curd=LogCommon.NEWADD)
	public void saveOrupdatephone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");  
			String name = request.getParameter("name");  
			String descr = request.getParameter("descr");
			HttpSession session =request.getSession();
			String userid=session.getAttribute("userId").toString();
			int result = phoneMangerService.saveOrupdatephone(id,name,userid,descr);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除手机号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletephone")
	@HikLog(content="删除手机",curd=LogCommon.QUERY)
	public void deletephone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = phoneMangerService.deletephone(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	/**
	 * 分页查询手机分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querygphone")
	@HikLog(content="分页查询手机分组",curd=LogCommon.QUERY)
	public void querygphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = phoneMangerService.querygphone(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 保存或更新手机分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdategphone")
	@HikLog(content="保存或更新手机分组",curd=LogCommon.NEWADD)
	public void saveOrupdategphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			  String gphonename=request.getParameter("gphonename");
			  String gphoneid=request.getParameter("gphoneid");
			  String phoneids=request.getParameter("phoneids");
			  String gphonedesc=request.getParameter("gphonedesc");
			  HttpSession session =request.getSession();
				String userid=session.getAttribute("userId").toString();
				
			int result = phoneMangerService.saveOrupdategphone(gphoneid,gphonename,userid,gphonedesc,phoneids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除手机分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletegphone")
	@HikLog(content="删除手机分组",curd=LogCommon.QUERY)
	public void deletegphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = phoneMangerService.deletegphone(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
