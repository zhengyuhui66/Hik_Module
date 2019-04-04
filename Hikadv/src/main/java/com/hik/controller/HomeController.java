package com.hik.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.service.IHomeService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/homeController")
public class HomeController  extends BaseController{
	@Autowired
	private IHomeService iHomeService;
	
	@RequestMapping("/showInternetBusUsers")
    public String showInternetBusUsers(HttpServletRequest request,HttpServletResponse response){
    	return "showEcharts/showInternetBusUsers";
    }

	@RequestMapping("/showInternetLineUsers")
    public String showInternetLineUsers(HttpServletRequest request,HttpServletResponse response){
    	return "showEcharts/showInternetLineUsers";
    }

	/**
	 * 广告为单位的展示统计top10
	 * @return
	 */
	@RequestMapping("/getTop10ShowCountByAdv")
	public void getTop10ShowCountByAdv(HttpServletRequest request,HttpServletResponse response){
		 try{
			 List<JSONObject> result=iHomeService.getTop10ShowCountByAdv();
				this.setResultInfo(QUERY_SUCCESS_INFO, result);
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}
		   this.returnResponse(response, this.getResultInfo());   
		    return;
	 }
	/**
	 * 线路为单位的展示统计top10
	 * @return
	 */
	@RequestMapping("/getTop10ShowCountByRoute")
	public void getTop10ShowCountByRoute(HttpServletRequest request,HttpServletResponse response){
		try{
			 List<JSONObject> result=iHomeService.getTop10ShowCountByRoute();
				this.setResultInfo(QUERY_SUCCESS_INFO, result);
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}
		   this.returnResponse(response, this.getResultInfo());   
		    return;
	}
	/**
	 * 广告为单位的点击统计次数
	 * @return
	 */
	@RequestMapping("/getTop10ClickCountByAdv")
	public void getTop10ClickCountByAdv(HttpServletRequest request,HttpServletResponse response){
		try{
			 List<JSONObject> result=iHomeService.getTop10ClickCountByAdv();
				this.setResultInfo(QUERY_SUCCESS_INFO, result);
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}
		   this.returnResponse(response, this.getResultInfo());   
		    return;
	}
	/**
	 *线路为单位的点击统计次数
	 * @return
	 */
	@RequestMapping("/getTop10ClickCountByRoute")
	public void getTop10ClickCountByRoute(HttpServletRequest request,HttpServletResponse response){
		try{
			 List<JSONObject> result=iHomeService.getTop10ClickCountByRoute();
				this.setResultInfo(QUERY_SUCCESS_INFO, result);
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}
		   this.returnResponse(response, this.getResultInfo());   
		    return;
	}
	/**
	 * 全局统计
	 * @return
	 */
	@RequestMapping("/getTotalInfo")
	public void getTotalInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			 List<JSONObject> result=iHomeService.getTotalInfo();
				this.setResultInfo(QUERY_SUCCESS_INFO, result);
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}
		   this.returnResponse(response, this.getResultInfo());   
		    return;
	}
	
//	@RequestMapping("/checkAllPassAudit")
//	public void checkAllPassAudit(HttpServletRequest request,HttpServletResponse response){
//		try{
//			String mater_id = request.getParameter("mater_id");
//			boolean result=iMaterialService.checkAllPassAudit(mater_id);
//			this.setResultInfo(QUERY_SUCCESS_INFO, result);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//	   this.returnResponse(response, this.getResultInfo());   
//	    return;
//	}
}
