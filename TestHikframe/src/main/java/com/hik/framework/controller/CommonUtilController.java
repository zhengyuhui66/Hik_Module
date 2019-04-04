package com.hik.framework.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.framework.utils.CommonUtil;

@Controller
@RequestMapping("/getCommon")
public class CommonUtilController  extends BaseController{
	/**
	 * 获取用户ID
	 * @return
	 */
	@RequestMapping("/getUserId")
	public void getUserId(HttpServletRequest request,HttpServletResponse response){
			try{
				this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getUserId(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户密码
	 * @return
	 */
	@RequestMapping("/getPassWord")
	public void getPassWord(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getPassWord(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户名称
	 * @return
	 */
	@RequestMapping("/getUserName")
	public void getUserName(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getUserName(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户手机
	 * @return
	 */
	@RequestMapping("/getTelphone")
	public void getTelphone(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getTelphone(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户Email
	 * @return
	 */
	@RequestMapping("/getEmail")
	public void getEmail(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getEmail(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户创建时间
	 * @return
	 */
	@RequestMapping("/getCreateTime")
	public void getCreateTime(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getCreateTime(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户第一次登陆时间
	 * @return
	 */
	@RequestMapping("/getLoginTime")
	public void getLoginTime(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getLoginTime(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户最后一次登陆时间
	 * @return
	 */
	@RequestMapping("/getLastLoginTime")
	public void getLastLoginTime(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getLastLoginTime(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户登陆次数
	 * @return
	 */
	@RequestMapping("/getLoginTimes")
	public void getLoginTimes(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getLoginTimes(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 获取用户角色ID
	 * @return
	 */
	@RequestMapping("/getTrid")
	public void getTrid(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getTrid(request));
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}
	/**
	 * 返回UUID
	 * @return
	 */
	@RequestMapping("/getUUID")
	public void getUUID(HttpServletRequest request,HttpServletResponse response){
		try{
			this.setResultInfo(QUERY_FAILURE_INFO, CommonUtil.getUUID());
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, null);
			}finally{
				this.returnResponse(response, this.getResultInfo());				
			}
	}

}
