package com.hik.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.hik.service.VehicleWifiService;
import com.hik.service.WechatVehicleWifiService;
import com.hik.util.SMSUtils;
import com.hik.util.GetAccessUtil;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

/**
 * 微信认证登陆
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/VehicleWifi_ningbo_Controller")
public class WechatsController extends BaseController{
	@Autowired
	private WechatVehicleWifiService vehicleWifiService;
    
    @Value("${serverhttp}")
	private String serverhttp;
    //认证一键登录
    @Value("${defaultwifiwechat_url}")
	private String defaultwifiwechat_url;
    
    @Value("${wechatlogined}")
    private String wechatlogined;
    
    @Value("${defaultwechatauthflag}")
    private String defaultwechatauthflag;
    
    
	/**
	 * 记录认证
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/Wifi",method= RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		log.info("开始。。。。。。");
		//AP地址
		HttpSession session = request.getSession();
		String gwIp=request.getParameter("gwIp");
		//AP端口
		String gwPort=request.getParameter("gwPort");
		//AP  MAC地址
		String gwMac=request.getParameter("gwMac");
		//客户端MAC
		String clientMac=request.getParameter("clientMac");
		//用户请求地址	
		String url=request.getParameter("url");
		
		String authType = request.getParameter("authType");
		
		String appId=request.getParameter("appId");//: 微信公众号平台账号ID
		
		String extend=request.getParameter("extend");
		
		String shopId=request.getParameter("shopId");
		
		String ssid=request.getParameter("ssid");
		
		String secretKey=null;
		//：扩展参数（目前传递回调URL和token）
		//：AP设备所在的门店ID
		//：AP无线信号名称
		if(gwIp==null||gwPort==null||gwMac==null||clientMac==null){
			return null;
		}
//		String URL=defaultwechatauthflag+"?gw_address="+gw_address+"&gw_port="+gw_port+"&gw_id="+gw_id+"&mac="+mac+"&url="+URLEncoder.encode(url, "UTF-8");
		String URL=serverhttp+defaultwechatauthflag+"?gwIp="+gwIp+"&gwPort="+gwPort+"&gwMac="+gwMac+"&clientMac="+clientMac+"&url="+URLEncoder.encode(url, "UTF-8")
		+"&authType="+authType+"&appId="+appId+"&extend="+extend+"&shopId="+shopId+"&ssid="+ssid;
		log.info("=========>"+URL);
		//二层过虑
		return "redirect:"+URL;

	}
	/**
	 * 记录认证
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/Wifi/logining",method= RequestMethod.GET)
	public String logining(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		//AP地址
		long startime = System.currentTimeMillis();
		HttpSession session = request.getSession();
		String gwIp=request.getParameter("gwIp");
		//AP端口
		String gwPort=request.getParameter("gwPort");
		//AP  MAC地址Hikadv.war
		String gwMac=request.getParameter("gwMac");
		//客户端MAC
		String clientMac=request.getParameter("clientMac");
		//用户请求地址	
		String url=request.getParameter("url");
		
		String authType = request.getParameter("authType");
		
		String appId=request.getParameter("appId");//: 微信公众号平台账号ID
		
		String extend=request.getParameter("extend");
		
		String shopId=request.getParameter("shopId");
		
		String ssid=request.getParameter("ssid");
		
//		String secretKey=null;
		if(gwIp==null||gwPort==null||gwMac==null||clientMac==null){
			return null;
		}
		log.error("===========login_GET!  gwIp"+gwIp+" gwPort:"+gwPort+" gwMac:"+gwMac+" clientMac:"+clientMac+" url:"+url+" authType:"+authType);
		session.setAttribute("gwIp",gwIp);
		session.setAttribute("gwPort",gwPort);
		session.setAttribute("gwMac",getMACStr(gwMac));
		session.setAttribute("clientMac",clientMac);
		String _url=null;
		try {
			if(url!=null){
				_url = URLEncoder.encode(url, "UTF-8");
				session.setAttribute("url",_url);
			}else{
				session.setAttribute("url","www.baidu.com");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Happen happen = new Happen(null,DateUtil.getCurrentDate(),null,null);
		JSONObject tempResult=vehicleWifiService.login(getMACStr(gwMac),clientMac,session,happen);
		String URL=JSONUtils.getString(tempResult, "url");
		String appid=JSONUtils.getString(tempResult, "appid");
		String appsecret=JSONUtils.getString(tempResult, "appsecret");
		String secretKey=JSONUtils.getString(tempResult, "secretKey");
		url="https://www.baidu.com";
		String ecodeurl = URLEncoder.encode(serverhttp+wechatlogined,"UTF-8");
		String ecodextend = URLEncoder.encode(extend,"UTF-8");
		String param = "mac="+clientMac+"&appId="+appid+"&extend="+ecodextend+"&shop_id="+shopId+"&authUrl="+ecodeurl+"&ssid="+ssid+"&bssid="+gwMac+"&secretkey="+secretKey+"&gwIp="+gwIp+"&gwPort="+gwPort+"&userUrl="+_url+"&flag="+authType;
		String resultUrl=serverhttp+URL;
		if(resultUrl.contains(defaultwifiwechat_url)){
			log.info("为默认界面");
			resultUrl+="?"+param;
		}else{
			log.info("不是默认界面");
			resultUrl+="&"+param;
		}
		log.info("URL=2************"+resultUrl);
		return "redirect:"+resultUrl;
	}

	private String getMACStr(String mac){
		String result=mac.replace(":", "-");
		return result;
	}
	@RequestMapping(value="/Wifi/clickCount",method= RequestMethod.GET)
	public void clickCount(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String advertid=request.getParameter("advertid");
		String modelid=request.getParameter("modelid");
		String modelmodelid=request.getParameter("modelmodelid");
		String clickTime=DateUtil.getCurrentDate();
		String gwMac=session.getAttribute("gwMac").toString();
		String clientMac=session.getAttribute("clientMac").toString();
		String phone=session.getAttribute("phone").toString();
		CLICK_COUNT_LOG logg = new CLICK_COUNT_LOG(null, clickTime, null, gwMac, phone, clientMac, modelid, modelmodelid, null,null,advertid,null);
		String URL=vehicleWifiService.saveClickCount(logg);
		this.returnResponse(response, URL);
	}
	@RequestMapping(value="/Wifi/clickCount",method= RequestMethod.POST)
	public void clickCount2(HttpServletRequest request,HttpServletResponse response){
		return;
	}
	@RequestMapping(value="/Wifi/showCount",method= RequestMethod.GET)
	public void showCount(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String advertid=request.getParameter("advertid"); 
		String modelid=request.getParameter("modelid");
		String advposid=request.getParameter("modelmodelid");
		String clickTime=DateUtil.getCurrentDate();
		//AP地址
		Object gwMac=session.getAttribute("gwMac");
		//客户端地址
		Object mac=session.getAttribute("clientMac");
		if(gwMac==null||mac==null){
			return;
		}
		log.error("=====>>>>:统计展示次数advertid:"+advertid+" modelid:"+modelid+" advposid:"+advposid+" apmac:"+gwMac+" mac:"+mac);
		SHOW_COUNT_LOG count_LOG = new SHOW_COUNT_LOG(null, null, null, gwMac.toString(), null, mac.toString(), modelid, advposid, null, null, advertid, null);
		int  result=vehicleWifiService.saveShowCount(count_LOG);
		this.returnResponse(response, result);
	}
	@RequestMapping(value="/Wifi/showCount",method= RequestMethod.POST)
	public void showCount2(HttpServletRequest request,HttpServletResponse response){
		return;
	}
	
	@RequestMapping(value="/Wifi/logined",method= RequestMethod.GET)
	public String logined2(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		log.info("===========================");
		HttpSession session = request.getSession();
		//extend:APip|APport|Apmac|终端mac|用户请求URL|用户认证AP的URL
		String extend=request.getParameter("extend");
		String openId=request.getParameter("openId");
		String tid=request.getParameter("tid");
		String[] _extends=extend.split("\\|");
		String gw_address=_extends[0];
		String gw_port=_extends[1];
		String gw_id =_extends[2];
		String clientMac=_extends[3];
		String authUrl=_extends[4];
		String userUrl=_extends[5];
		String flag=_extends[6];
		log.info("extends:"+extend+"  openId:"+openId+" tid:"+tid);
		log.info(" gw_address:"+gw_address+"gw_port:"+gw_port+" gw_id:"+gw_id+" clientMac:"+clientMac);
		
		JSONObject json = new JSONObject();
		json.accumulate("openId", openId);
		json.accumulate("tid", tid);
		
		String pubip = request.getRemoteAddr();
		Map<String,Object> map = new HashMap<String,Object>();
		String token=GetToken();
//		List<JSONObject> speedJson = vehicleWifiService.getSpeedAndTime(gw_id);
//		String speed="128";
//		String timeout="15000";
//		if(speedJson!=null&&speedJson.size()>0){
//			JSONObject jsons = speedJson.get(0);
//			speed=JSONUtils.getString(jsons, "speed");
//			timeout=JSONUtils.getString(jsons, "timeout");			
//		}
//		String flag="0";//0 短信,1 微信
		String speed="1024";
		String timeout="15000";
		Object speeds=session.getAttribute("speed");
		Object timeouts=session.getAttribute("timeout");
		if(speeds!=null){
			speed=speeds.toString();
		}
		if(timeouts!=null){
			timeout=timeouts.toString();
		}
		String upspeed="8192";   //单位kbps 
		
		Happen happen = new Happen(null,DateUtil.getCurrentDate(),null,null);
		
		log.error("==start Wifi/logined  GW_ID:"+gw_id+" clickMac:"+clientMac+" url"+userUrl+"authUrl"+authUrl+" happen:"+happen.toString());
		String URL=vehicleWifiService.portal(gw_id,null,clientMac, session,userUrl,happen);
		
		String ecodeurl = URLEncoder.encode(URL,"UTF-8");
		String httpUrl="redirect:"+authUrl+"?token=" +token+ "&Phone=13486353632&Pubip=" + pubip + "&Speed=" +speed + "&UpSpeed=" +upspeed + "&Flag=" +flag + "&Timeout=" +timeout+"&Url="+ecodeurl;
		log.error("httpUrl:"+httpUrl);
		return httpUrl;
	}
	
	@RequestMapping(value="/Wifi/test",method= RequestMethod.GET)
	public ModelAndView test(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String httpUrl="redirect:http://www.baidu.com";
		ModelAndView result=new ModelAndView(httpUrl);
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
	 
	 @RequestMapping(value="/Wifi/SendSMSCode",method= RequestMethod.GET)
	 public void SendSMSCode(HttpServletRequest request,HttpServletResponse response){
		 HttpSession session = request.getSession();
		 String phone = request.getParameter("phone");
			session.setAttribute("phone",phone);
		 try{
			 String code=SMSUtils.getSMSCode();
			 String key="smscode"+phone;
			 log.info("key======>sendSMSCode:"+key+"   code:"+code);
			 session.setAttribute(key, code);
		    	vehicleWifiService.sendSMSCode(phone,code);
				this.setResultInfo(QUERY_SUCCESS_INFO,code);
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, "发送短信失败");
			}
		   this.returnResponse(response, this.getResultInfo());
	 }
	 
	 
	 @RequestMapping(value="/Wifi/toVaild")
	 public void toVaild(HttpServletRequest request,HttpServletResponse response){
		 HttpSession session = request.getSession();
		 String code=request.getParameter("code");
		 String phone=request.getParameter("phone");
		 JSONObject jsons=new JSONObject();
		 try{
			 String key="smscode"+phone;
			 Object vailCodetemp=session.getAttribute(key);
			 String vaildCode=null;
			 if(vailCodetemp!=null){
				 vaildCode=session.getAttribute(key).toString();
			 }
			 jsons.accumulate("smscod", vaildCode);
			 if(code!=null&&code.equals(vaildCode)){
				 log.info("key======>toVaild:"+key+"   vaildcode:"+vaildCode+"  验证成功");
				 jsons.accumulate("result", true);
			 }else{
				 log.info("key======>toVaild:"+key+"   vaildcode:"+vaildCode+" 验证失败");
				 jsons.accumulate("result", false);
			 }
			this.setResultInfo(QUERY_SUCCESS_INFO,jsons);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	 }
	 /**
	  * 确定是否要放行，auth:1放行  auth:0不放行
	  * @param request
	  * @param response
	  */
	@RequestMapping(value="/Wifi/auth",method= RequestMethod.GET)
	public void Auth(HttpServletRequest request,HttpServletResponse response){
		String token=request.getParameter("token");
//		String stage=request.getParameter("stage");
		String ip=request.getParameter("ip");
		String mac=request.getParameter("mac");
		String incoming=request.getParameter("incoming");
		String outgoing=request.getParameter("outgoing");
		String gw_id=request.getParameter("gw_id");
		String result = CheckToken(token);
		log.error("===WIFI/AUTH:token:"+token+" ip:"+ip+" mac:"+mac+" incoming:"+incoming+"outgoing:"+outgoing+" gw_id"+gw_id+" result:"+result);
		this.returnResponse(response, result);
	}
	
	@RequestMapping(value="/Wifi/ping/",method= RequestMethod.GET)
    public void Ping(HttpServletRequest request,HttpServletResponse response){
		String gw_id=request.getParameter("gwMac");//=00037F8274C8&"
		String sys_uptime=request.getParameter("sys_uptime");//=76477&
		String sys_memfree=request.getParameter("sys_memfree");//=14448&"
	    String sys_load=request.getParameter("sys_load");//=0.41&
     	String wifidog_uptime=request.getParameter("wifidog_uptime");//=76446 
//     	log.info("ping ... ...");
     	 this.returnResponse(response, "Pong");
     	 return;
//        return "Pong";
    }

//	 @RequestMapping(value="/Wifi/getAccessToken")
//	 public void getAccessToken(HttpServletRequest request,HttpServletResponse response){
//		try{
//			 String accress=GetAccessUtil.getAccessToken();
//			this.setResultInfo(QUERY_SUCCESS_INFO,accress);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//		this.returnResponse(response, this.getResultInfo());
//	 }
//	 
	 
}
