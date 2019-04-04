package com.hik.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.BLACKLIST;
import com.hik.app.entity.DEVICE;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IBlackListMangerService;
import com.hik.service.IDeviceMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/blacklistmangercontroller")
public class BlackListMangerController extends ExportBaseController {
	
	@Autowired
	private IBlackListMangerService iBlackListMangerService;
	
    @RequestMapping("/blacklistmanger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "busManger/blackListManger";
    }
    
	/**
	 * 分页查询设备信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryblacklistmanger")
	@HikLog(content="分页查询黑名单信息",curd=LogCommon.QUERY)
	public void queryroutemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iBlackListMangerService.queryBlackListManger(start, limit, searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 新增或更新黑名单信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addorUpdateblacklistmanger")
	@HikLog(content="新增更或新黑名单信息",curd=LogCommon.NEWADD)
	public void addorUpdatedeviceManger(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String id=request.getParameter("id");
		String phone = request.getParameter("phone");
		String mark = request.getParameter("mark");
 
		try{	
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			String dateTime=DateUtil.getCurrentDate();
			BLACKLIST blacklist = new BLACKLIST(id, phone, userId, dateTime, dateTime, dateTime, mark);
			int result=iBlackListMangerService.addorUpdateBlackListManger(blacklist);
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
	 * 删除黑名单信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteblacklistmanger")
	@HikLog(content="删除黑名单信息",curd=LogCommon.DELETE)
	public void deletedevicemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String[] ids=request.getParameterValues("ids");
			String result=iBlackListMangerService.deleteBlackListManger(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	@RequestMapping("/exportblacklistmanger")
	@HikLog(content="导出黑名单信息",curd=LogCommon.QUERY)
	public void exportdevicemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String _start=request.getParameter("start");
			String _limit=request.getParameter("limit");
			int start=Integer.parseInt(_start);
			int limit=Integer.parseInt(_limit);
			String searchStr=request.getParameter("searchStr");
			String title=request.getParameter("title");
			Object obj = request.getParameter("rowtitles");
			JSONObject rowtitles=JSONObject.fromObject(obj);
			Page page = iBlackListMangerService.queryBlackListManger(start, limit, searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }
}
