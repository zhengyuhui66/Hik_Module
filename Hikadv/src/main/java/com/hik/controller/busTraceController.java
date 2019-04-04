package com.hik.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.app.entity.DEVICE;
import com.hik.dao.IQueryTraceDao;
import com.hik.framework.controller.BaseController;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IDeviceMangerService;
import com.hik.service.IQueryTraceService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/bustracecontroller")
public class busTraceController extends BaseController {
	
	@Autowired
	private IQueryTraceService iQueryTraceDao;
	
    @RequestMapping("/busTrace")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "busManger/busTrace";
    }
    
    
    
    
    @RequestMapping("/querybustrace")
    public void querybustrace(HttpServletRequest request,HttpServletResponse response){
    	String equipmentid=request.getParameter("equipmentid");
    	String starttime = request.getParameter("starttime");
    	String endtime = request.getParameter("endtime");
		try{
			List result =iQueryTraceDao.QueryTrace(starttime, endtime, equipmentid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
    }
}
