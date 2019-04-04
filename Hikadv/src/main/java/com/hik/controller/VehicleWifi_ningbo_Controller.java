package com.hik.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.Happen;
import com.hik.app.entity.SHOW_COUNT_LOG;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.service.SendSMSCodeService;
import com.hik.service.VehicleWifiService;
import com.hik.service.VehicleWifi_ningbo_Service;
import com.hik.util.Area;
import com.hik.util.SMSUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 宁波APP打开时一键登陆
 * @author Administrator
 *
 */
@Deprecated
@Controller
@RequestMapping("/vnwff")
public class VehicleWifi_ningbo_Controller extends BaseController{
	

    @Value("${serverhttp}")
	private String serverhttp;
	
    @Value("${ningbowifiurl}")
	private String ningbowifiurl;
	
	@Autowired
	private VehicleWifi_ningbo_Service vehicleWifiService;

	/**
	 * 记录认证
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/Wifi/login",method= RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response){
//		//AP地址
		HttpSession session = request.getSession();
		String gw_address=request.getParameter("gw_address");
		//AP端口
		String gw_port=request.getParameter("gw_port");
		//AP  MAC地址
		String gw_id=request.getParameter("gw_id");
		//客户端MAC
		String mac=request.getParameter("mac");
		//用户请求地址	
		String url=request.getParameter("url");

		if(url.contains("ewifi")){
			log.info("接收到的标识URL符合规范:"+url);
			String token=GetToken();
			String gwid=getMACStr(gw_id.toString());
			log.info("gw_address:"+gw_address+"  gw_port:"+gw_port+"  gw_id:"+gwid+"  mac:"+mac+"  url:"+url);
			List<JSONObject> speedJson = vehicleWifiService.getSpeedAndTime(gwid);
			log.info("speedJson:"+speedJson.toString());
			String speed="512000";
			String timeout="15000";
			if(speedJson!=null&&speedJson.size()>0){
				JSONObject json = speedJson.get(0);
				speed=JSONUtils.getString(json, "speed");
				timeout=JSONUtils.getString(json, "timeout");
			}
			log.info("speed:"+speed+"   timeout:"+timeout);
			String pubip = request.getRemoteAddr();
			String phone=url.substring(url.length()-16, url.length()-5);
			String httpUrl="redirect:http://"+gw_address.toString()+":"+gw_port.toString()+"/wifidog/auth?token=" +token+ "&Phone=" +phone + "&Pubip=" + pubip + "&Speed=" +speed + "&Timeout=" +timeout;
			return httpUrl;
		}else{
			log.info("接收到的标识URL不符合规范跳转到下载界面:"+url);
			return "redirect:"+serverhttp+ningbowifiurl;
		}
	}

	
	private String getMACStr(String mac){
		char[] byte0=mac.toCharArray();
		String result="";
		for(int i=0;i<byte0.length;i++){
			result+=byte0[i];
			if(i%2==1&&i!=byte0.length-1){
				result+="-";
			}
		}
		return result;
	}
	

	 private String GetToken()
     {
		 String dt=System.currentTimeMillis()+"";
         return dt;
     }

	 private String CheckToken(String token){
		 long log = Long.parseLong(token);
		 long cu=System.currentTimeMillis();
		 if(cu-log>3600000l){
			 return "Auth:0";
		 }else{
			 return "Auth:1"; 
		 }
	 }
	 /**
	  * 确定是否要放行，auth:1放行  auth:0不放行
	  * @param request
	  * @param response
	  */
	@RequestMapping(value="/Wifi/auth",method= RequestMethod.GET)
	public void Auth(HttpServletRequest request,HttpServletResponse response){
		String token=request.getParameter("token");
		String stage=request.getParameter("stage");
		String ip=request.getParameter("ip");
		String mac=request.getParameter("mac");
		String incoming=request.getParameter("incoming");
		String outgoing=request.getParameter("outgoing");
		String gw_id=request.getParameter("gw_id");
		log.info("进入认证");
		if(token==null||gw_id==null||ip==null||mac==null){
			return;
		}
		 String result = CheckToken(token);
		 HttpSession http = request.getSession();
		 http.setAttribute("authResult",result);
		 
		 http.getAttribute("authResult!");
		 log.info("进入认证token,获取到的result:"+result);
		 this.returnResponse(response, result);
	}
	
	@RequestMapping(value="/Wifi/ping/",method= RequestMethod.GET)
    public void Ping(HttpServletRequest request,HttpServletResponse response){
		String gw_id=request.getParameter("gw_id");//=00037F8274C8&"
		String sys_uptime=request.getParameter("sys_uptime");//=76477&
		String sys_memfree=request.getParameter("sys_memfree");//=14448&"
	    String sys_load=request.getParameter("sys_load");//=0.41&
     	String wifidog_uptime=request.getParameter("wifidog_uptime");//=76446 
     	 this.returnResponse(response, "Pong");
     	 return;
    }
	
	@RequestMapping(value="/Wifi/portal",method= RequestMethod.GET)
	public void Portal(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		json.accumulate("result","0");
		json.accumulate("errorMsg","无");
		log.info("认证成功 返回："+json.toString());
		this.returnResponse(response,json);
    }
}
