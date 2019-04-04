package com.hik.controller;

import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.CORP;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.controller.BaseController;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.ICorMangerService;
import com.hik.service.IPutMangerSetService;
import com.hik.service.IPutProductService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/cormangercontroller")
public class CorMangerController extends ExportBaseController {
	
	@Autowired
	private ICorMangerService iCorMangerService;
	
    @RequestMapping("/cormanger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "busManger/corManger";
    }
    
	/**
	 * 分页查询广告投放条件
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querycormanger")
	@HikLog(content="分页查询公司信息",curd=LogCommon.QUERY)
	public void queryputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iCorMangerService.queryCorpManger(start, limit, searchStr);
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
	@RequestMapping("/addorUpdatecormanger")
	@HikLog(content="新增更或新公司信息",curd=LogCommon.NEWADD)
	public void addorUpdateputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		int _id=0;"id","name","address","createtime","creator","modifier","modifytime","mark"
		String id=request.getParameter("id");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String mark = request.getParameter("mark");
		try{
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			String dateTime=DateUtil.getCurrentDate();
			CORP corp = new CORP(id, name, address, userId, dateTime, userId, dateTime, mark);
			int result=iCorMangerService.addorUpdateCorpManger(corp);
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
	 * 删除广告投放设置数据
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletecormanger")
	@HikLog(content="删除公司信息",curd=LogCommon.DELETE)
	public void deleteputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
//			String[] ids=request.getParameterValues("ids");
			String _ids = request.getParameter("ids");
			
			String[] ids=_ids.split(",");

			String result=iCorMangerService.deleteCorpManger(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	@RequestMapping("/exportcormanger")
	@HikLog(content="导出公司信息",curd=LogCommon.QUERY)
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
			Page page = iCorMangerService.queryCorpManger(start, limit, searchStr);
//			Page page = iPutProductService.queryputProduct(start, limit,searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }
}
