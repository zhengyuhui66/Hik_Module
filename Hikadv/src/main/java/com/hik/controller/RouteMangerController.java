package com.hik.controller;

import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.CORP;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.controller.BaseController;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.ICorMangerService;
import com.hik.service.IPutMangerSetService;
import com.hik.service.IPutProductService;
import com.hik.service.IRouteMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/routemangercontroller")
public class RouteMangerController extends ExportBaseController {
	
	@Autowired
	private IRouteMangerService iRouteMangerService;
	
    @RequestMapping("/routemanger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "busManger/routeManger";
    }
    
	/**
	 * 分页查询线路信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryroutemanger")
	@HikLog(content="分页查询线路信息",curd=LogCommon.QUERY)
	public void queryroutemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iRouteMangerService.queryRouteManger(start, limit, searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	
	
	@RequestMapping("/queryDeviceList")
	@HikLog(content="设备具体信息",curd=LogCommon.QUERY)
	public void queryDeviceList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("id");
			List<JSONObject> result = iRouteMangerService.queryDeviceList(id);
			this.returnResponse(response, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	
	/**
	 * 新增或更新线路信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addorUpdateroutemanger")
	@HikLog(content="新增更或新公司信息",curd=LogCommon.NEWADD)
	public void addorUpdaterouteManger(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		int _id=0;"id","name","address","createtime","creator","modifier","modifytime","mark"
		String id=request.getParameter("id");
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		String mark = request.getParameter("mark");
		try{	
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			String dateTime=DateUtil.getCurrentDate();
//			CORP corp = new CORP(id, name, address, userId, dateTime, userId, dateTime, mark);
			
			ROUTE route = new ROUTE(id, name, state, userId, dateTime, userId, dateTime, mark);
			int result=iRouteMangerService.addorUpdateRouteManger(route);
			String TIP=id==null?UPDATE_SUCCESS_INFO:QUERY_SUCCESS_INFO;
			this.setResultInfo(TIP, result);
	    } catch (Exception e) {
			e.printStackTrace();
			String TIP2=id==null?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO;
			this.setResultInfo(id==null?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 删除线路信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteroutemanger")
	@HikLog(content="删除线路信息",curd=LogCommon.DELETE)
	public void deleteroutemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String[] ids=request.getParameterValues("ids");
			String result=iRouteMangerService.deleteRouteManger(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 设置线路参数信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/setting")
	@HikLog(content="设置参数",curd=LogCommon.UPDATE)
	public void setting(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
//			String[] ids=request.getParameterValues("id");
			String ids=request.getParameter("id");
			String speed = request.getParameter("speed");
			String timeout=request.getParameter("timeout");
			String[] idstr = ids.split(",");
			String result=iRouteMangerService.setting(idstr,speed,timeout);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	@RequestMapping("/exportroutemanger")
	@HikLog(content="导出线路信息",curd=LogCommon.QUERY)
	public void exportroutemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
//			int start=getStartOrLimit(request, "start");
//			int limit=getStartOrLimit(request, "limit");
			String _start=request.getParameter("start");
			String _limit=request.getParameter("limit");
			int start=Integer.parseInt(_start);
			int limit=Integer.parseInt(_limit);
			String searchStr=request.getParameter("searchStr");
			String title=request.getParameter("title");
			Object obj = request.getParameter("rowtitles");
			JSONObject rowtitles=JSONObject.fromObject(obj);
			Page page = iRouteMangerService.queryRouteManger(start, limit, searchStr);
//			Page page = iPutProductService.queryputProduct(start, limit,searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }
}
