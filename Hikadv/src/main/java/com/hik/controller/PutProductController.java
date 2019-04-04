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

import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.controller.BaseController;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IPutMangerSetService;
import com.hik.service.IPutProductService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/putproductcontroller")
public class PutProductController extends ExportBaseController {
	
	@Autowired
	private IPutProductService iPutProductService;
	
    @RequestMapping("/putproduct")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "putManger/putProduct";
    }
    
	/**
	 * 分页查询广告投放条件
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryputProduct")
	@HikLog(content="页查询广告投放条件",curd=LogCommon.QUERY)
	public void queryputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iPutProductService.queryputProduct(start, limit,searchStr);
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
	@RequestMapping("/addorUpdateputProduct")
	@HikLog(content="新增更或新投放产品",curd=LogCommon.NEWADD)
	public void addorUpdateputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		int _id=0;
		String id=request.getParameter("id");
		String proname=request.getParameter("proname");
		String modelid=request.getParameter("modelid");
		String advposid1=request.getParameter("advposid1");
		String advposid2=request.getParameter("advposid2");
		String advposid3=request.getParameter("advposid3");
		String advposid4=request.getParameter("advposid4");
		String advposid5=request.getParameter("advposid5");
		String advgroup1=request.getParameter("advgroup1");
		String advgroup2=request.getParameter("advgroup2");
		String advgroup3=request.getParameter("advgroup3");
		String advgroup4=request.getParameter("advgroup4");
		String advgroup5=request.getParameter("advgroup5");
		String isdelete=request.getParameter("isdelete"); 
		String descr=request.getParameter("descr");
		try{	
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			
			PUTPRODUCT pricondition = new PUTPRODUCT(id, proname,modelid, advposid1, advposid2, advposid3, advposid4, advposid5, advgroup1, advgroup2, advgroup3, advgroup4, advgroup5, null, userId, userId, null, isdelete, descr);
			int result=iPutProductService.addorUpdateputProduct(pricondition);
			String TIP=id==null?UPDATE_SUCCESS_INFO:QUERY_SUCCESS_INFO;
			this.setResultInfo(TIP, result);
//			this.returnResponse(response, this.getResultInfo());
	    } catch (Exception e) {
			e.printStackTrace();
			String TIP2=id==null?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO;
			this.setResultInfo(id==null?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO, null);
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
	@RequestMapping("/deleteputProduct")
	@HikLog(content="删除投放产品",curd=LogCommon.DELETE)
	public void deleteputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String[] ids=request.getParameterValues("ids");
			String result=iPutProductService.deleteputProduct(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	@RequestMapping("/exportputProduct")
	@HikLog(content="导出投放产品",curd=LogCommon.QUERY)
	public void exportputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
			Page page = iPutProductService.queryputProduct(start, limit,searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }
}
