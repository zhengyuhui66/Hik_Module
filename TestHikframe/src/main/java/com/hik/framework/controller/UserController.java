package com.hik.framework.controller;

import java.util.List;
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
import com.hik.app.entity.SYS_USER;
import com.hik.framework.service.IMenuService;
import com.hik.framework.service.IUserService;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.MD5Util;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/userController")
public class UserController extends BaseController{
    private final Log log = LogFactory.getLog(UserController.class); 
	
	@Autowired(required=true)
	private IUserService iUserService;
	
	
	@RequestMapping("/userManger")
    public String userManger(HttpServletRequest request,HttpServletResponse response){
    	return "systemManger/userManger/userManger";
    }
	
	@RequestMapping("/getUser")
	@HikLog(content="查询用户",curd=LogCommon.QUERY)
	public String getUser(HttpServletRequest request,HttpServletResponse response){
		try{
		int start=getStartOrLimit(request, "start");
		int limit=getStartOrLimit(request, "limit");
		String searchStr = request.getParameter("searchStr");
		String userId= request.getSession().getAttribute("userId").toString();
		Page page = iUserService.getUser(start, limit,searchStr,userId);
		this.returnResponse(response, JSONObject.fromObject(page));
		
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return null;
	}
	
	@RequestMapping("/editUser")
	@HikLog(content="编辑用户",curd=LogCommon.UPDATE)
	public String editUser(HttpServletRequest request,HttpServletResponse response){
		try{
//			 int USID = getIntRequestParam(request,"usid");
			 int USERID= getIntRequestParam(request,"userid");
			 String PWORD=request.getParameter("pword");
//			 EncrypDES de1 = new EncrypDES();  
//			 byte[] encontent = de1.Encrytor(PWORD); 
			 String USER_NAME=request.getParameter("user_name");
			 String TELPHONE=request.getParameter("telphone");
			 String EMAIL=request.getParameter("email");
			 String DESCRIPTION=request.getParameter("description");
			 SYS_USER sysUser = new SYS_USER();
			 sysUser.setUSERID(USERID);
			 sysUser.setPWORD(MD5Util.MD5(PWORD));
			 sysUser.setUSER_NAME(USER_NAME);
			 sysUser.setTELPHONE(TELPHONE);
			 sysUser.setEMAIL(EMAIL);
			 sysUser.setDESCRIPTION(DESCRIPTION);
			int result= iUserService.editUser(sysUser);
				this.setResultInfo(UPDATE_SUCCESS_INFO, result);
				this.returnResponse(response, this.getResultInfo());
			
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(UPDATE_FAILURE_INFO, null);
				this.returnResponse(response, this.getResultInfo());
			}
		return null;
	}

	@RequestMapping("/addUser")
	@HikLog(content="新增用户",curd=LogCommon.NEWADD)
	public String addUser(HttpServletRequest request,HttpServletResponse response){
		try{
			//用户基本信息
			 int USERID= getIntRequestParam(request,"userid");
			 String PWORD=request.getParameter("pword");
//			 EncrypDES de1 = new EncrypDES();  
//			 byte[] encontent = de1.Encrytor(PWORD); 
//			 
			 String USER_NAME=request.getParameter("user_name");
			 String TELPHONE=request.getParameter("telphone");
			 String EMAIL=request.getParameter("email");
			 String DESCRIPTION=request.getParameter("description");
//			 角色授权信息
			 HttpSession session = request.getSession();
			 String puerid = (String) session.getAttribute("userId");
			 int _puserId = Integer.parseInt(puerid);

			 SYS_USER sysUser = new SYS_USER();
			 sysUser.setUSERID(USERID);
			 sysUser.setPWORD(MD5Util.MD5(PWORD));
			 sysUser.setUSER_NAME(USER_NAME);
			 sysUser.setTELPHONE(TELPHONE);
			 sysUser.setEMAIL(EMAIL);
			 sysUser.setDESCRIPTION(DESCRIPTION);
			 sysUser.setISLOGOUT("1");
			 int result= iUserService.addUser(sysUser,_puserId);
			 this.setResultInfo(ADD_SUCCESS_INFO, result);
			 this.returnResponse(response, this.getResultInfo());
			
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(ADD_FAILURE_INFO, null);
				this.returnResponse(response, this.getResultInfo());
			}
		return null;
	}
	@RequestMapping("/deleteUser")
	@HikLog(content="删除用户",curd=LogCommon.DELETE)
	public String deleteUser(HttpServletRequest request,HttpServletResponse response){
		String userid=request.getParameter("userid");
		try{
			int result=0;
			if(userid.contains(",")){
				String[] _useridstr= userid.split(",");
				int[] _userids= new int[_useridstr.length];
				for(int i=0;i<_useridstr.length;i++){
					_userids[i]=Integer.parseInt(_useridstr[i]);
				}
				result=iUserService.deleteUser(_userids);
				this.setResultInfo(DELETE_SUCCESS_INFO, "多");
			}else{
				int _userid=Integer.parseInt(userid);
				result=iUserService.deleteUser(_userid);
				this.setResultInfo(DELETE_SUCCESS_INFO, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/getRoleById")
	public void getRoleById(HttpServletRequest request,HttpServletResponse response){
		try{
			HttpSession session = request.getSession();
			String _trid = session.getAttribute("trid").toString();
			String userid=request.getParameter("userid");
			List result = iUserService.getRoleById(_trid,userid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	
	@RequestMapping("/addUserRole")
	public void addUserRole(HttpServletRequest request,HttpServletResponse response){
		int userid=getIntRequestParam(request, "userid");
		String[] trids = request.getParameterValues("trid");
		try{
		int result = iUserService.addUserRole(userid, trids);
			this.setResultInfo(INSERT_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(INSERT_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	
	@RequestMapping("/getParentUser")
	public void getParentUser(HttpServletRequest request,HttpServletResponse response){
		try{
			int userid=getIntRequestParam(request, "userid");
		List result = iUserService.getParentUser(userid);
			this.setResultInfo(INSERT_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(INSERT_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	@RequestMapping("/valiateUpdate")
	public void valiateUpdate(HttpServletRequest request,HttpServletResponse response){
		try{
			String userid=request.getParameter("userid");
			String username=request.getParameter("username");
			boolean result = iUserService.valiateUpdate(userid,username);
			this.setResultInfo(INSERT_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(INSERT_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	@RequestMapping("/valiateAdd")
	public void valiateAdd(HttpServletRequest request,HttpServletResponse response){
		try{
			String username=request.getParameter("username");
			boolean result = iUserService.valiateAdd(username);
			this.setResultInfo(INSERT_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(INSERT_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	
	
}
