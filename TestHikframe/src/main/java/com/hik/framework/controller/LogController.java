package com.hik.framework.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.framework.service.ILogService;
import com.hik.framework.service.ILoginService;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/logController")
public class LogController extends BaseController{
	@Autowired(required=false)
	private ILogService iLogService;
	
	
	
	@RequestMapping("/logManger")
    public String logManger(HttpServletRequest request,HttpServletResponse response){
    	return "systemManger/logManger/logManger";
    }

	@RequestMapping("/getLogInfo")
	@HikLog(content="≤È—Ø»’÷æ",curd=LogCommon.QUERY)
	public void getLogInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String condition=request.getParameter("condition");
		int start = getStartOrLimit(request, "start");
		int limit = getStartOrLimit(request, "limit");
		try{
			Page result = iLogService.getLogInfo(condition,start,limit);
			this.returnResponse(response, JSONObject.fromObject(result));
//			this.setResultInfo(QUERY_SUCCESS_INFO,JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}  
//		 this.returnResponse(response, this.getResultInfo());   
		return;
	 }
}
