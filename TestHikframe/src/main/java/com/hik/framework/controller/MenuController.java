package com.hik.framework.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.app.entity.SYS_RIGHT;
import com.hik.framework.service.IMenuService;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.MD5Util;
import com.hik.interceptor.HikLog;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/menuController")
public class MenuController extends BaseController{
    private final Log log = LogFactory.getLog(MenuController.class); 
	
	@Autowired(required=true)
	private IMenuService iMenuService;
	
	
	@RequestMapping("/menuManger")
    public String menuManger(HttpServletRequest request,HttpServletResponse response){
    	return "systemManger/menuManger/menuManger";
    }
	
	@RequestMapping("/menu")
	@HikLog(content="查询餐单",curd=LogCommon.QUERY)
	public String getMenu(HttpServletRequest request,HttpServletResponse response){
		try{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		if(StringUtils.isNoneBlank(userId)){
			List list = iMenuService.getIMenuInfo(userId);
			this.returnResponse(response,list);
		}else{
			this.setResultInfo(QUERY_FAILURE_INFO, "用户不存在");
			this.returnResponse(response, this.getResultInfo());
		}
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return null;
	}
	
	@RequestMapping("/updatepword")
	@HikLog(content="更改当前的密码",curd=LogCommon.UPDATE)
	public String updatepword(HttpServletRequest request,HttpServletResponse response){
		try{
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userId");
		String newpword = request.getParameter("newpword");
		boolean result = iMenuService.updatepword(userid,MD5Util.MD5(newpword));
		this.returnResponse(response,result);
		
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return null;
	}
	
	
	@RequestMapping("/editmenu")
	@HikLog(content="编辑餐单",curd=LogCommon.UPDATE)
	public String getEditMenu(HttpServletRequest request,HttpServletResponse response){
		try{
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			if(StringUtils.isNoneBlank(userId)){
				List list = iMenuService.getIEditMenuInfo(userId);
				this.returnResponse(response,list);
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "用户不存在");
				this.returnResponse(response, this.getResultInfo());
			}
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
				this.returnResponse(response, this.getResultInfo());
			}
		return null;
	}
public static void main(String[] args) {
	String role="fd";
	String[] roles=role.split(",");
	UUID uuid = UUID.randomUUID();
	System.out.println(uuid);
}
	@RequestMapping("/addMenu")
	public String addMenu(HttpServletRequest request,HttpServletResponse response){
		String text=request.getParameter("text");
		String role = request.getParameter("role");
		JSONObject json = JSONObject.fromObject(role);
		List rolesList = new ArrayList();
		 Iterator it = json.keys();  
		 while(it.hasNext()){  
			 String key=it.next().toString();
			 if("true".equals(json.get(key).toString())){
				 rolesList.add(key);
			 }
		 }
		String[] roles=(String[])rolesList.toArray(new String[rolesList.size()]);
		String description=request.getParameter("description");
		String leve=request.getParameter("leve");
		String parent_id = request.getParameter("parent_id");
		SYS_RIGHT sys_RIGHT = new SYS_RIGHT();
		try{
		sys_RIGHT.setDESCRIPTION(description);
		sys_RIGHT.setRIGHT_NAME(text);
		sys_RIGHT.setLEVE(Integer.parseInt(leve));
		sys_RIGHT.setPARENT_TR_ID(parent_id);
		sys_RIGHT.setRRID(null);
			int result=iMenuService.addMenu(sys_RIGHT,roles);
			this.setResultInfo(UPDATE_SUCCESS_INFO, null);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(UPDATE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	
	@RequestMapping("/updataOrAddMenu")
	public String updataOrAddMenu(HttpServletRequest request,HttpServletResponse response){
		String text=request.getParameter("text");
		String rrid=request.getParameter("rrid");
		String description=request.getParameter("description");
		String role=request.getParameter("role");
		JSONObject json = JSONObject.fromObject(role);
		String leve=request.getParameter("leve");
		String parent_id = request.getParameter("parent_id");
		SYS_RIGHT sys_RIGHT = new SYS_RIGHT();
		try{
		sys_RIGHT.setRRID(rrid);
		sys_RIGHT.setDESCRIPTION(description);
		sys_RIGHT.setRIGHT_NAME(text);
		sys_RIGHT.setLEVE(Integer.parseInt(leve));
		sys_RIGHT.setPARENT_TR_ID(parent_id);
		
			iMenuService.updataOrAddMenu(sys_RIGHT,json);
			this.setResultInfo(UPDATE_SUCCESS_INFO, null);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(UPDATE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	@RequestMapping("/deleteMenu")
	@HikLog(content="删除餐单",curd=LogCommon.DELETE)
	public String deleteMenu(HttpServletRequest request,HttpServletResponse response){
		String rrid=request.getParameter("rrid");
		try{
			iMenuService.deleteMenu(rrid);
			this.setResultInfo(INSERT_SUCCESS_INFO, true);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(INSERT_SUCCESS_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/getRoleInfo")
	public void getRoleInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iMenuService.getRoleInfo();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	/**
	 * 某个餐单所拥有的权限所有者
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getRightRoles")
	public void getRightRoles(HttpServletRequest request,HttpServletResponse response){
		try{
			String rrid=request.getParameter("rrid");
			List result = iMenuService.getRightRoles(rrid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	
	
	/**
	 * 自定义界面权限
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getHaveRight")
	public void getHaveRight(HttpServletRequest request,HttpServletResponse response){
		try{
			String description=request.getParameter("description");
			
//			userId
			boolean bol = iMenuService.getHaveRight(description,request);
			this.setResultInfo(QUERY_SUCCESS_INFO, bol);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
}
