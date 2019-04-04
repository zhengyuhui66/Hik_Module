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
	 * ��ȡ�û�ID
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
	 * ��ȡ�û�����
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
	 * ��ȡ�û�����
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
	 * ��ȡ�û��ֻ�
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
	 * ��ȡ�û�Email
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
	 * ��ȡ�û�����ʱ��
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
	 * ��ȡ�û���һ�ε�½ʱ��
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
	 * ��ȡ�û����һ�ε�½ʱ��
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
	 * ��ȡ�û���½����
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
	 * ��ȡ�û���ɫID
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
	 * ����UUID
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
