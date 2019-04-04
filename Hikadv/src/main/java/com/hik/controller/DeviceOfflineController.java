package com.hik.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.app.entity.DEVICE;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IDeviceMangerService;
import com.hik.service.IDeviceOfflineService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/deviceofflinecontroller")
public class DeviceOfflineController extends ExportBaseController {
	
	@Autowired
	private IDeviceOfflineService iDeviceOfflineService;
	
    @RequestMapping("/deviceoffline")
    public String deviceoffline(HttpServletRequest request,HttpServletResponse response){
    	StringUtils.isNotBlank("dd");
    	return "busManger/deviceOffline";
    }
    
	/**
	 * 分页查询设备上下线信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryofflinemanger")
	@HikLog(content="分页查询设备信息",curd=LogCommon.QUERY)
	public void queryofflinemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iDeviceOfflineService.queryDeviceOffline(start, limit, searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	
	@RequestMapping("/exportdeviceoffline")
	@HikLog(content="导出设备上下线信息",curd=LogCommon.QUERY)
	public void exportdeviceoffline(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String _start=request.getParameter("start");
			String _limit=request.getParameter("limit");
			int start=Integer.parseInt(_start);
			int limit=Integer.parseInt(_limit);
			String searchStr=request.getParameter("searchStr");
			String title=request.getParameter("title");
			Object obj = request.getParameter("rowtitles");
			JSONObject rowtitles=JSONObject.fromObject(obj);
			Page page = iDeviceOfflineService.queryDeviceOffline(start, limit, searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }
}
