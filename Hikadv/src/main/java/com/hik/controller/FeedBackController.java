package com.hik.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hik.framework.controller.BaseController;
import com.hik.framework.service.ClientService;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.FeedBackService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/feedbackController")
public class FeedBackController extends BaseController{
    private final Log log = LogFactory.getLog(FeedBackController.class); 
    
//    @Value("${clientPath}")
//	private String clientPath;
    
	@Autowired(required=true)
	private FeedBackService feedBackService;
	
    @RequestMapping("/index")
    public String client(HttpServletRequest request,HttpServletResponse response){
    	return "feedback/feedback";
    }
	/**
	 * 自定义界面权限
	 * @param request
	 * @param response
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request,HttpServletResponse response){
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			Page bol = feedBackService.queryClientInfo(start,limit);
//			this.setResultInfo(QUERY_SUCCESS_INFO, bol);
			this.returnResponse(response,JSONObject.fromObject(bol));
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return null;
	}
	
	
//	@RequestMapping("/upload")
//	@HikLog(content="上传新的客户端",curd=LogCommon.NEWADD)
//	public void contest(@RequestParam(value = "file", required = false) MultipartFile file,
//			HttpServletRequest request,
//			HttpServletResponse response) throws Exception{
//		String versionid = request.getParameter("version");
//		String versionName = request.getParameter("versionname");
//		String versiondesc = request.getParameter("versiondesc");
//		String userid = CommonUtil.getUserId(request);
//	    try{
//	    	 String path = request.getSession().getServletContext().getRealPath("client/android");
//	    	 int result=feedBackService.saveClient(path,file,request,versionid,versionName,versiondesc,userid);
//			this.setResultInfo(QUERY_SUCCESS_INFO, result);
//		}catch (Exception e){
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, "上传异常");
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	 }
}
