package com.hik.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.PRICONDITION;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IPutMangerSetService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/putmangersetcontroller")
public class PutMangerSetController extends BaseController {
	
	
	@Autowired
	private IPutMangerSetService iPutMangerSetService;
	
    @RequestMapping("/putMangerSet")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/putMangerSet";
    }
    
	/**
	 * 分页查询广告投放条件
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryAdvputSet")
	@HikLog(content="页查询广告投放条件",curd=LogCommon.QUERY)
	public void queryAdvputSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iPutMangerSetService.queryForPage(start, limit,searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 新增或更新广告投放设置数据
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addorUpdateAdvputSet")
	@HikLog(content="查询广告模版",curd=LogCommon.NEWADD)
	public void addorUpdateAdvputSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		int _id=0;
		try{
			String id=request.getParameter("id");
			if(!StringUtils.isEmpty(id)){
//				_id=Double.parseDouble(id);
				_id=Integer.parseInt(id);
			}
			String timeid = request.getParameter("timeid");
//			String stime=request.getParameter("stime");
//			String etime=request.getParameter("etime");   
			String addredssid=request.getParameter("addredssid"); 
			String eventid=request.getParameter("eventid");    
//			String phonetype=request.getParameter("phonetype");  
//			String phonesystem=request.getParameter("phonesystem");
			String phone=request.getParameter("phone");      
//			String clicktype=request.getParameter("clicktype");  
//			String viewtype=request.getParameter("viewtype"); 
			String creatime=request.getParameter("creatime");
			String modifytime=request.getParameter("modifytime");
			String descr=request.getParameter("descr");
			String name=request.getParameter("name");		
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			
			PRICONDITION pricondition = new PRICONDITION(_id,timeid, addredssid, eventid,/* phonetype, phonesystem,*/ phone, /*clicktype, viewtype,*/ userId, creatime, userId, modifytime, descr, name);
			int result=iPutMangerSetService.addorUpdateAdvputSet(pricondition);
			String TIP=_id==0?UPDATE_SUCCESS_INFO:QUERY_SUCCESS_INFO;
			this.setResultInfo(TIP, result);
//			this.returnResponse(response, this.getResultInfo());
	    } catch (Exception e) {
			e.printStackTrace();
			String TIP2=_id==0?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO;
			this.setResultInfo(_id==0?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 删除广告投放设置数据
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteAdvputSet")
	@HikLog(content="删除广告模版",curd=LogCommon.NEWADD)
	public void deleteAdvputSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			
			String[] ids=request.getParameterValues("ids");
			int result=iPutMangerSetService.deleteAdvputSet(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除广告投放设置数据
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryputSet")
	@HikLog(content="查询广告模版",curd=LogCommon.NEWADD)
	public void queryputSet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result=iPutMangerSetService.queryputSet();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
