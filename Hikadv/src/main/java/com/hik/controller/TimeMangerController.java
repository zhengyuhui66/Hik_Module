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
import com.hik.service.TimeMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/time")
public class TimeMangerController extends BaseController {
	
	@Autowired
	private TimeMangerService timeMangerService;
	
    @RequestMapping("/timeManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/timeManger";
    }
    
    
    /**
     * 数据分组编辑时时间设置列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListtime")
	@HikLog(content="页查询时间设置",curd=LogCommon.QUERY)
	public void queryListtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			List result = timeMangerService.queryListtime(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
    /**
     * 数据分组编辑时时间设置已选择列表    
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListgtime")
	@HikLog(content="页查询时间设置",curd=LogCommon.QUERY)
	public void queryListgtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			if(id==null||StringUtils.isEmpty(id)){
				this.setResultInfo(QUERY_SUCCESS_INFO, null);
			}else{
				List result = timeMangerService.queryListgtime(id);
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
	 * 分页查询时间设置
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querytime")
	@HikLog(content="分页时间设置",curd=LogCommon.QUERY)
	public void querytime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = timeMangerService.querytime(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 保存所有的终端系统号
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdatetime")
	@HikLog(content="保存时间设置",curd=LogCommon.NEWADD)
	public void saveOrupdatetime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");  
			String name = request.getParameter("name");  
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			String sdtime = request.getParameter("sdtime");
			String edtime = request.getParameter("edtime");
			String descr = request.getParameter("descr");
			HttpSession session =request.getSession();
			String userid=session.getAttribute("userId").toString();
			int result = timeMangerService.saveOrupdatetime(id,name,stime,etime,sdtime,edtime,userid,descr);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除终端系统号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletetime")
	@HikLog(content="删除时间设置",curd=LogCommon.QUERY)
	public void deletetime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = timeMangerService.deletetime(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	/**
	 * 分页查询终端系统分组
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querygtime")
	@HikLog(content="分页查询终端系统分组",curd=LogCommon.QUERY)
	public void querygtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = timeMangerService.querygtime(start,limit,search);
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
	@RequestMapping("/saveOrupdategtime")
	@HikLog(content="保存或更新终端系统分组",curd=LogCommon.NEWADD)
	public void saveOrupdategtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			  String gtimename=request.getParameter("gtimename");
			  String gtimeid=request.getParameter("gtimeid");
			  String timeids=request.getParameter("timeids");
			  String gtimedesc=request.getParameter("gtimedesc");
			  HttpSession session =request.getSession();
				String userid=session.getAttribute("userId").toString();
				
			int result = timeMangerService.saveOrupdategtime(gtimeid,gtimename,userid,gtimedesc,timeids);
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
	@RequestMapping("/deletegtime")
	@HikLog(content="删除终端系统分组",curd=LogCommon.QUERY)
	public void deletegtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = timeMangerService.deletegtime(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	

	
}
