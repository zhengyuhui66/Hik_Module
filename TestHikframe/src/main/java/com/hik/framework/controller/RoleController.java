package com.hik.framework.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.hik.app.entity.SYS_ROLE;
import com.hik.framework.service.IMenuService;
import com.hik.framework.service.IRoleService;
import com.hik.framework.utils.LogCommon;
import com.hik.interceptor.HikLog;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController{
    private final Log log = LogFactory.getLog(RoleController.class); 
	
	@Autowired(required=true)
	private IRoleService iRoleService;
	
	@RequestMapping("/roleManger")
    public String roleManger(HttpServletRequest request,HttpServletResponse response){
    	return "systemManger/roleManger/roleManger";
    }
	@RequestMapping("/getRoleInfo")
	@HikLog(content="查询角色",curd=LogCommon.QUERY)
	public void getRoleInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iRoleService.getRoleInfo();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	@RequestMapping("/addOrUpdateRoleInfo")
	@HikLog(content="新增或更新角色",curd=LogCommon.NEWADD)
	public void addOrUpdateRoleInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			String trid=request.getParameter("trid");
			String pid=request.getParameter("pid");
			String name=request.getParameter("name");
			String description=request.getParameter("description");
//			String create_time=request.getParameter("create_time");
//			if(StringUtils.isEmpty(trid)){
//				
////				trid=getUUID();
//				Date currentTime = new Date();
//				   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				   String dateString = formatter.format(currentTime);
//				   create_time=dateString;
//			}
			SYS_ROLE role = new SYS_ROLE(trid, pid, name, description, null);
			int result = iRoleService.addOrUpdateRoleInfo(role);
			boolean success=false;
			if(result!=0){
				success=true;
			}
			this.setResultInfo(QUERY_SUCCESS_INFO, success);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	@RequestMapping("/deleteRoleById")
	@HikLog(content="删除角色",curd=LogCommon.DELETE)
	public void deleteRoleById(HttpServletRequest request,HttpServletResponse response){
		try{
		String _trid=request.getParameter("trid");
		String[] trid=_trid.split(",");
		 int result=iRoleService.deleteRoleById(trid);
			this.setResultInfo(DELETE_SUCCESS_INFO,result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	@RequestMapping("/getPRoleRight")
	public void getPRoleRight(HttpServletRequest request,HttpServletResponse response){
		try{
			String trid=request.getParameter("trid");
			String ptrid=request.getParameter("ptrid");
			if(trid==null||ptrid==null){
				return;
			}
//			String trid,String ptrid
			 List result=iRoleService.getPRoleRight(trid,ptrid);
			 this.returnResponse(response,result);
			} catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}
			return;
	}
	@RequestMapping("/saveRoleRight")
	@HikLog(content="保存当前权限设置",curd=LogCommon.UPDATE)
	public void saveRoleRight(HttpServletRequest request,HttpServletResponse response){
		try{
		String _trid=request.getParameter("trid");
		String _rrid=request.getParameter("rrid");
//		String trid=_trid.split(",");
		String[] rrid=_rrid.split(",");
		 int result=iRoleService.saveRoleRight(_trid,rrid);
			this.setResultInfo(DELETE_SUCCESS_INFO,result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	
}
