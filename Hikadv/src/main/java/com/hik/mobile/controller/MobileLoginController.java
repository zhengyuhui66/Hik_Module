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
	@HikLog(content="��½��֤",curd=LogCommon.QUERY)
	public void userlogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String passWord=request.getParameter("passWord");
//		String userId=request.getParameter("userId");
		String username=request.getParameter("username");
		String cid=request.getParameter("cid");
		
		log.info("��½���û���:"+username+" CID:"+cid);
		if(StringUtils.isBlank(username)||StringUtils.isBlank(passWord)){
			this.setResultInfo(QUERY_FAILURE_INFO, "�û�����������Ϊ��");
		}else{
			JSONObject result = new JSONObject();
			try{
				List iflogin = iLoginService.getIfLoginInfo(username, MD5Util.MD5(passWord),log);
				if(iflogin!=null&&iflogin.size()>0){
					 result = (JSONObject) iflogin.get(0);
					result.accumulate("flag", true);
//					String userAlias=result.optString("userId");
					//�󶨸���Alias
					IGtPush push = new IGtPush(host, appKey, masterSecret);
					//����Ƿ��а�
					checkAliasAndUnbind(push,appId,username,cid);
					
					IAliasResult bindSCid = push.bindAlias(appId, username, cid);
					log.info("�󶨽����" + bindSCid.getResult() + "������:" + bindSCid.getErrorMsg());
					log.info("�������cid�б���Ϣ��" + bindSCid.getClientIdList() + "������Ϣ:" + bindSCid.getAlias());
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
	 * Alias��֮ǰ�ȼ���Ƿ��Ѿ����󶨣��� ����
	 * @param push
	 * @param appId2
	 * @param username
	 * @param cid
	 */
	private void checkAliasAndUnbind(IGtPush push, String appId2, String username, String cid) {
		// TODO �Զ����ɵķ������
		
    		List clientList = getClientId(username);
    		if (clientList!=null&&clientList.size()>0) {
				for(int i=0;i<clientList.size();i++){
					//�Ƚ��Alias�� 
					IAliasResult unbindAliasResult=push.unBindAlias(appId, username, clientList.get(i).toString());
					if (unbindAliasResult.getResult()) {
						log.info("Alias "+username+" �а󶨣��Ѿ�����󶨣�����󶨽����"+unbindAliasResult.getResult() );
					}else {
						log.info("Alias "+username+" �а󶨣����ǽ��ʧ�ܡ���ע�⣡ cid is"+cid);
					}
				}
			}
    		
    		
		
		
//		//��ѯ�Ƿ��Ѿ���
//		IAliasResult bindInfo=push.queryAlias(appId, cid);
//		if(bindInfo.getResult()){
//			//�Ƚ��Alias�� 
//			IAliasResult unbindAliasResult=push.unBindAlias(appId, username, cid);
//			if (unbindAliasResult.getResult()) {
//				log.info("Alias "+username+" �а󶨣��Ѿ�����󶨣�����󶨽����"+unbindAliasResult.getResult() );
//			}else {
//				log.info("Alias "+username+" �а󶨣����ǽ��ʧ�ܡ���ע�⣡ cid is"+cid);
//			}
//		}else{
//			log.info("Alias "+username+" �ǵ�һ�ΰ�");
//		}
		
	}
	
	 public List getClientId(String alias){
	        IGtPush push = new IGtPush(host, appKey, masterSecret);
	        IAliasResult queryClient = push.queryClientId(appId, alias);
	        log.info("������"+alias+"  ���ݱ�����ȡ��CID��" + queryClient.getClientIdList());
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

