package com.hik.mobile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.http.IGtPush;
import com.hik.framework.controller.BaseController;
import com.hik.framework.service.ILoginService;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.MD5Util;
import com.hik.interceptor.HikLog;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/mobile")
public class MobileLoginController extends BaseController {
	@Autowired(required=false)
	private ILoginService iLoginService;
	
	@Value("${getappId}")
	private String appId;
  
    @Value("${getappsecret}")
	private String appSecret;
  
    @Value("${getappkey}")
	private String appKey;
  
    @Value("${getmastersecret}")
	private String masterSecret;
    
    @Value("${gethost}")
	private String host;
	
	@RequestMapping("/userlogin")
	@HikLog(content="登陆认证",curd=LogCommon.QUERY)
	public void userlogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String passWord=request.getParameter("passWord");
//		String userId=request.getParameter("userId");
		String username=request.getParameter("username");
		String cid=request.getParameter("cid");
		
		log.info("登陆的用户名:"+username+" CID:"+cid);
		if(StringUtils.isBlank(username)||StringUtils.isBlank(passWord)){
			this.setResultInfo(QUERY_FAILURE_INFO, "用户名或者密码为空");
		}else{
			JSONObject result = new JSONObject();
			try{
				List iflogin = iLoginService.getIfLoginInfo(username, MD5Util.MD5(passWord),log);
				if(iflogin!=null&&iflogin.size()>0){
					 result = (JSONObject) iflogin.get(0);
					result.accumulate("flag", true);
//					String userAlias=result.optString("userId");
					//绑定个推Alias
					IGtPush push = new IGtPush(host, appKey, masterSecret);
					//检查是否有绑定
					checkAliasAndUnbind(push,appId,username,cid);
					
					IAliasResult bindSCid = push.bindAlias(appId, username, cid);
					log.info("绑定结果：" + bindSCid.getResult() + "错误码:" + bindSCid.getErrorMsg());
					log.info("请求输出cid列表信息：" + bindSCid.getClientIdList() + "别名信息:" + bindSCid.getAlias());
					if (bindSCid.getResult()) {
						result.accumulate("bindflag", true);
						
					}else{
						result.accumulate("bindflag", false);
						result.accumulate("bindmsg", bindSCid.getErrorMsg());
					}
					this.setResultInfo(QUERY_SUCCESS_INFO, result);
				}else{
					result.accumulate("flag", false);
					this.setResultInfo(QUERY_FAILURE_INFO, result);
				}
				
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}  
		}
		 this.returnResponse(response, this.getResultInfo());   
		return;
	 }
	
	/**
	 * Alias绑定之前先检查是否已经被绑定，是 则解绑
	 * @param push
	 * @param appId2
	 * @param username
	 * @param cid
	 */
	private void checkAliasAndUnbind(IGtPush push, String appId2, String username, String cid) {
		// TODO 自动生成的方法存根
		
    		List clientList = getClientId(username);
    		if (clientList!=null&&clientList.size()>0) {
				for(int i=0;i<clientList.size();i++){
					//先解除Alias绑定 
					IAliasResult unbindAliasResult=push.unBindAlias(appId, username, clientList.get(i).toString());
					if (unbindAliasResult.getResult()) {
						log.info("Alias "+username+" 有绑定，已经解除绑定，解除绑定结果："+unbindAliasResult.getResult() );
					}else {
						log.info("Alias "+username+" 有绑定，但是解绑失败。请注意！ cid is"+cid);
					}
				}
			}
    		
    		
		
		
//		//查询是否已经绑定
//		IAliasResult bindInfo=push.queryAlias(appId, cid);
//		if(bindInfo.getResult()){
//			//先解除Alias绑定 
//			IAliasResult unbindAliasResult=push.unBindAlias(appId, username, cid);
//			if (unbindAliasResult.getResult()) {
//				log.info("Alias "+username+" 有绑定，已经解除绑定，解除绑定结果："+unbindAliasResult.getResult() );
//			}else {
//				log.info("Alias "+username+" 有绑定，但是解绑失败。请注意！ cid is"+cid);
//			}
//		}else{
//			log.info("Alias "+username+" 是第一次绑定");
//		}
		
	}
	
	 public List getClientId(String alias){
	        IGtPush push = new IGtPush(host, appKey, masterSecret);
	        IAliasResult queryClient = push.queryClientId(appId, alias);
	        log.info("别名："+alias+"  根据别名获取的CID：" + queryClient.getClientIdList());
	        return queryClient.getClientIdList();
	    }


	@RequestMapping("/mobile/checkpword")
	public void checkpword(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String passWord=request.getParameter("passWord");
//		String userId=request.getParameter("userId");
		String username=request.getParameter("username");
		try{
			List iflogin = iLoginService.getIfLoginInfo(username, passWord,log);
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
	

	
	
	@RequestMapping("/regists")
	public String getRegist(){
		return "regist";	
	}
	@RequestMapping("/index")
	public String getIndex(){
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

