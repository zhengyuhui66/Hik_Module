package com.hik.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IPutScheduleLogService;
import com.hik.service.IPutScheduleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/psl")
public class PutScheduleLogController  extends BaseController{
	
	@Autowired
	private IPutScheduleLogService iputScheduleLogService;
	@RequestMapping("/putScheduleLog")
    public String getputSchedule(HttpServletRequest request,HttpServletResponse response){
    	return "putManger/putScheduleLog";
    }
	
	@RequestMapping("/queryPutLog")
	@HikLog(content="²éÑ¯ÅÅÆÚ",curd=LogCommon.QUERY)
	public void queryPutLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iputScheduleLogService.queryPutLog(start,limit,searchStr);
			this.returnResponse(response,JSONObject.fromObject(page));
			
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
}
