package com.hik.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.ADVGROUP;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.Page;
import com.hik.service.IAdvertGroupService;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/advertGroupController")
public class AdvertGroupController  extends BaseController{
	
	@Autowired
	private IAdvertGroupService iAdvertGroupService;
    
	@RequestMapping("/advertGroup")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "advertManger/advertGroup";
    }
	/**
	 * 分页得到所有的广告
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getAdvertgroup")
	public void getAdvertgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr= request.getParameter("searchStr");
			Page page = iAdvertGroupService.getAdvertgroup(start, limit,searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 新增或者修改广告集
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdateAdvertgroup")
	public void saveOrupdateAdvertgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("id");
			String advid1=request.getParameter("advid1");
			String advid2=request.getParameter("advid2");
			String advid3=request.getParameter("advid3");
			String advid4=request.getParameter("advid4");
			String advid5=request.getParameter("advid5");
			String advid6=request.getParameter("advid6");
			String advid7=request.getParameter("advid7");
			String advid8=request.getParameter("advid8");
			String advid9=request.getParameter("advid9");
//			String creator=request.getParameter("creator");
			String creatime=request.getParameter("creatime");
//			String modifier=request.getParameter("modifier");
			String modifytime=request.getParameter("modifytime");
			String playstrager=request.getParameter("playstrager");
//			String playpristrager=request.getParameter("playpristrager");
			String descr=request.getParameter("descr");
			String name=request.getParameter("name");
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userId");
			ADVGROUP advgroup = new ADVGROUP(id, advid1, advid2, advid3, advid4, advid5, advid6, advid7, advid8, advid9, userid, creatime, userid, modifytime, playstrager/*, playpristrager*/, descr, name);
			
			int result = iAdvertGroupService.saveOrupdateAdvertgroup(advgroup);
//			this.returnResponse(response, JSONObject.fromObject(page));
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 删除广告集
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteAdvertgroup")
	public void deleteAdvertgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			List ifid = iAdvertGroupService.queryGroupPutproduct(ids);
			if(ifid!=null&&ifid.size()>0){
				String _result=ifid.get(0).toString();
				if(Integer.parseInt(_result)>0){
					this.setResultInfo(QUERY_FAILURE_INFO, _result);					
				}else{
					int result = iAdvertGroupService.deleteAdvertgroup(ids);
					this.setResultInfo(QUERY_SUCCESS_INFO, result);
				}
			}
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }


}
