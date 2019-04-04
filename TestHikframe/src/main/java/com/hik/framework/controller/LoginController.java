package com.hik.framework.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.service.ILoginService;
import com.hik.framework.utils.CreateImageCode;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.MD5Util;
import com.hik.interceptor.HikLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class LoginController extends BaseController{
	@Autowired(required=false)
	private ILoginService iLoginService;

	@RequestMapping("/userlogin")
	@HikLog(content="登陆认证",curd=LogCommon.QUERY)
	public void userlogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String passWord=request.getParameter("passWord");
//		String userId=request.getParameter("userId");
		String username=request.getParameter("username");
		String flag = request.getParameter("flag");
		String verificode = request.getParameter("verificode");
		log.info("用户登陆：密码:"+passWord+" username:"+username+" verificode:"+verificode+"SESSIONID:"+request.getSession().getId());
		System.out.println("登陆认证。。。。。。。。。");
		if("1".equals(flag)){
			HttpSession session = request.getSession();
			String verificode2=session.getAttribute("verificode").toString();
			if(verificode.equals(verificode2)){
				if(StringUtils.isBlank(username)||StringUtils.isBlank(passWord)){
					this.setResultInfo(QUERY_FAILURE_INFO, "用户名或者密码为空");
				}else{
					try{
//						 EncrypDES de1 = new EncrypDES();
//						 byte[] decontent = de1.Encrytor(passWord); 
						
						List iflogin = iLoginService.getIfLoginInfo(username, MD5Util.MD5(passWord),log);
						if(iflogin!=null&&iflogin.size()>0){
							JSONObject result = (JSONObject) iflogin.get(0);
							HttpSession httpSession = request.getSession();
							httpSession.setAttribute("userId",result.get("userId").toString());
//							httpSession.setAttribute("passWord",result.get("pword").toString());
							httpSession.setAttribute("userName",result.get("userName"));
							httpSession.setAttribute("telphone",result.get("telphone"));
							httpSession.setAttribute("email",result.get("email"));
							httpSession.setAttribute("createTime",result.get("createTime"));
							httpSession.setAttribute("loginTime",result.get("loginTime"));
							httpSession.setAttribute("lastLoginTime",result.get("lastLoginTime"));
							httpSession.setAttribute("loginTimes",result.get("loginTimes"));
							httpSession.setAttribute("trid",result.get("trid"));
							this.setResultInfo(QUERY_SUCCESS_INFO, "登陆成功");
						}else{
							this.setResultInfo(QUERY_FAILURE_INFO, "用户名或密码错误");
						}
			    } catch (Exception e) {
					e.printStackTrace();
					this.setResultInfo(QUERY_FAILURE_INFO, null);
				}  
				}
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "验证码不正确");
			}
		}else{
			
			if(StringUtils.isBlank(username)||StringUtils.isBlank(passWord)){
				this.setResultInfo(QUERY_FAILURE_INFO, "用户名或者密码为空");
			}else{
				try{
//					 EncrypDES de1 = new EncrypDES();
//					 byte[] decontent = de1.Encrytor(passWord); 
					log.info("==============================");
					List iflogin = iLoginService.getIfLoginInfo(username, MD5Util.MD5(passWord),log);

					log.info("=============================="+iflogin.toString());
					if(iflogin!=null&&iflogin.size()>0){
						JSONObject result = (JSONObject) iflogin.get(0);
						HttpSession httpSession = request.getSession();
						httpSession.setAttribute("userId",result.get("userId").toString());
						httpSession.setAttribute("passWord",result.get("pword").toString());
						httpSession.setAttribute("userName",result.get("userName"));
						httpSession.setAttribute("telphone",result.get("telphone"));
						httpSession.setAttribute("email",result.get("email"));
						httpSession.setAttribute("createTime",result.get("createTime"));
						httpSession.setAttribute("loginTime",result.get("loginTime"));
						httpSession.setAttribute("lastLoginTime",result.get("lastLoginTime"));
						httpSession.setAttribute("loginTimes",result.get("loginTimes"));
						httpSession.setAttribute("trid",result.get("trid"));
						this.setResultInfo(QUERY_SUCCESS_INFO, "登陆成功");
					}else{
						this.setResultInfo(QUERY_FAILURE_INFO, "用户名或密码错误");
					}
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}  
			}
		}
		HttpSession httpSession = request.getSession();
		log.info("登陆密码:"+httpSession.getAttribute("passWord")+"登陆用户名:"+
		httpSession.getAttribute("userId")+"SESSIONID:"+httpSession.getId());
		 this.returnResponse(response, this.getResultInfo());   
		return;
	 }
	
	
	@RequestMapping("/checkpword")
	public void checkpword(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String passWord=request.getParameter("passWord");
//		String userId=request.getParameter("userId");
		String username=request.getParameter("username");
//		 EncrypDES de1 = new EncrypDES();
//		 byte[] decontent = de1.Encrytor(passWord); 
		
		try{
			List iflogin = iLoginService.getIfLoginInfo(username,MD5Util.MD5(passWord),log);
			boolean result=true;
			if(iflogin==null||iflogin.size()==0){
				result=false;
			}
			this.setResultInfo(QUERY_SUCCESS_INFO,result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}  
		 this.returnResponse(response, this.getResultInfo());   
		return;
	 }
	
	@RequestMapping("/Wifi/getCode")
	public void getCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 response.setContentType("image/jpeg");
         //禁止图像缓存。
         response.setHeader("Pragma", "no-cache");
         response.setHeader("Cache-Control", "no-cache");
         response.setDateHeader("Expires", 0);
         
         HttpSession session = request.getSession();
         CreateImageCode vCode = new CreateImageCode(100,30,5,10);
         session.setAttribute("verificode", vCode.getCode());
         vCode.write(response.getOutputStream());
	 }	
	@RequestMapping("/regists")
	public String getRegist(){
		return "regist";	
	}
	@RequestMapping("/index")
	public String getIndex(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		log.info("UserId Index:"+session.getAttribute("userId"));
		return "index";	
	}
	@RequestMapping("/login")
	public String getLogin(){
		return "login";
	}
	@RequestMapping("/menus")
	public String getMenu(){
		return null;
	}
	
}
