package com.hik.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IInternetUserService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/internetusercontroller")
public class InternetUserController extends ExportBaseController {
	
	@Autowired
	private IInternetUserService iInternetUserService;
	
    @RequestMapping("/internetuser")
    public String internetuser(HttpServletRequest request,HttpServletResponse response){
    	return "busManger/internetUser";
    }
    
	/**
	 * 分页查询设备上下线信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryinternetuser")
	@HikLog(content="分页查询上网用户",curd=LogCommon.QUERY)
	public void queryinternetuser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iInternetUserService.queryInternetUser(start, limit, searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	
	@RequestMapping("/exportinternetuser")
	@HikLog(content="导出上网用户信息",curd=LogCommon.QUERY)
	public void exportinternetuser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String _start=request.getParameter("start");
			String _limit=request.getParameter("limit");
			int start=Integer.parseInt(_start);
			int limit=Integer.parseInt(_limit);
			String searchStr=request.getParameter("searchStr");
			String title=request.getParameter("title");
			Object obj = request.getParameter("rowtitles");
			JSONObject rowtitles=JSONObject.fromObject(obj);
			Page page = iInternetUserService.queryInternetUser(start, limit, searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }
}
