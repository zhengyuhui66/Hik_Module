package com.hik.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.app.entity.WechatManger;
import com.hik.dao.IGetAdvertDao;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IWechatMangerService;
import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/wechatmancontroller")
public class WechatMangerController extends BaseController {
	

	@Autowired
	private IWechatMangerService iWechatMangerService;
	
    @RequestMapping("/wechatmanger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "advertManger/wechatManger";
    }
    
	/**
	 * 页查询公众号
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryWechat")
	@HikLog(content="页查询公众号",curd=LogCommon.QUERY)
	public void queryWechat(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iWechatMangerService.queryForPage(start, limit,searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 新增或更新公众号数据
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addorUpdateWechat")
	@HikLog(content="新增或更新公众号数据",curd=LogCommon.NEWADD)
	public void addorUpdateWechat(HttpServletRequest request,HttpServletResponse response) throws Exception {
		int _id=0;
		try{
			String id=request.getParameter("id");
			if(!StringUtils.isEmpty(id)){
				_id=Integer.parseInt(id);
			}
		   String name=request.getParameter("name");
		   String wechatid=request.getParameter("wechatid");
		   String type=request.getParameter("type");
		   String introduce=request.getParameter("introduce");
		   String identify=request.getParameter("identify");
		   String phone=request.getParameter("phone");
		   String address=request.getParameter("address");
		   String maininfo=request.getParameter("maininfo");
		   String owner=request.getParameter("owner");
		   String email=request.getParameter("email");
		   String orgid=request.getParameter("orgid");
		   String appid=request.getParameter("appid");
		   String appsecret=request.getParameter("appsecret");
		   String accesstoken=request.getParameter("accesstoken");
		   String propertysid= request.getParameter("propertysid");
			String propertyid = request.getParameter("propertyid");
			String user_adv = request.getParameter("user_adv");
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			String[] putcondIds= request.getParameterValues("putcondId");
			Set<String> putconIdSet = null;
			if(putcondIds!=null){
				putconIdSet=new HashSet<String>(Arrays.asList(putcondIds));				
			}
			WechatManger wm = new WechatManger(id, name, wechatid, type, introduce, identify, phone, address, maininfo, owner, email, orgid, appid, appsecret, accesstoken, userId, null, userId, null,user_adv,propertysid,propertyid);
			int result=iWechatMangerService.addorUpdateWechat(wm,putconIdSet);
			String TIP=_id==0?UPDATE_SUCCESS_INFO:QUERY_SUCCESS_INFO;
			this.setResultInfo(TIP, result);
//			this.returnResponse(response, this.getResultInfo());
	    } catch (Exception e) {
			e.printStackTrace();
			String TIP2=_id==0?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO;
			this.setResultInfo(_id==0?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 删除公众号数据
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteWechat")
	@HikLog(content="删除公众号数据",curd=LogCommon.NEWADD)
	public void deleteWechat(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			
			String[] ids=request.getParameterValues("ids");
			String result=iWechatMangerService.deleteWechat(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
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
	@RequestMapping("/queryMMPP")
	@HikLog(content="查询广告模版",curd=LogCommon.NEWADD)
	public void queryMMPP(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result=iWechatMangerService.queryMMPP();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
