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
import com.hik.service.EventMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/event")
public class EventMangerController extends BaseController {
	
	@Autowired
	private EventMangerService eventMangerService;
	
    @RequestMapping("/eventManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/eventManger";
    }
    /**
     * 数据分组编辑时未选择事件列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListevent")
	@HikLog(content="页查询所有的事件号",curd=LogCommon.QUERY)
	public void queryListevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			List result = eventMangerService.queryListevent(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
    /**
     * 数据分组编辑时事件已选择列表    
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListgevent")
	@HikLog(content="页查询所有的事件号",curd=LogCommon.QUERY)
	public void queryListgevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			if(id==null||StringUtils.isEmpty(id)){
				this.setResultInfo(QUERY_SUCCESS_INFO, null);
			}else{
				List result = eventMangerService.queryListgevent(id);
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
	 * 分页查询所有的事件号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryevent")
	@HikLog(content="分页查询所有的事件号",curd=LogCommon.QUERY)
	public void queryevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = eventMangerService.queryevent(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 保存所有的事件号
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdateevent")
	@HikLog(content="保存所有的事件号",curd=LogCommon.NEWADD)
	public void saveOrupdateevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");  
			String name = request.getParameter("name");  
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			String descr = request.getParameter("descr");
			HttpSession session =request.getSession();
			String userid=session.getAttribute("userId").toString();
			int result = eventMangerService.saveOrupdateevent(id,name,stime,etime,userid,descr);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除事件号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteevent")
	@HikLog(content="删除事件",curd=LogCommon.QUERY)
	public void deleteevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = eventMangerService.deleteevent(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	/**
	 * 分页查询事件分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querygevent")
	@HikLog(content="分页查询事件分组",curd=LogCommon.QUERY)
	public void querygevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = eventMangerService.querygevent(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 保存或更新事件分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdategevent")
	@HikLog(content="保存或更新事件分组",curd=LogCommon.NEWADD)
	public void saveOrupdategevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			  String geventname=request.getParameter("geventname");
			  String geventid=request.getParameter("geventid");
			  String eventids=request.getParameter("eventids");
			  String geventdesc=request.getParameter("geventdesc");
			  HttpSession session =request.getSession();
				String userid=session.getAttribute("userId").toString();
				
			int result = eventMangerService.saveOrupdategevent(geventid,geventname,userid,geventdesc,eventids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除事件分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletegevent")
	@HikLog(content="删除事件分组",curd=LogCommon.QUERY)
	public void deletegevent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = eventMangerService.deletegevent(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	
	
}
