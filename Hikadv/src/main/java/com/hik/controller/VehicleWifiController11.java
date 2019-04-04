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
import com.hik.util.SMSUtils;
import com.hik.util.GetAccessUtil;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

/**
 * wifi控制2.0说明
 * 路由器升级2.0版本
 * @author Administrator
 * 
 */
@Deprecated
@Controller
@RequestMapping("/vnw")
public class VehicleWifiController11 extends BaseController{
	
	@Autowired
	private VehicleWifiService vehicleWifiService;

    @Value("${serverhttp}")
	private String serverhttp;
    
    @Value("${defaultwechatauthflag}")
    private String defaultwechatauthflag;	
    /**
	 * 记录认证
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/Wifi/",method= RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		log.error("===========login_GET!");
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
		if(gwIp==null||gwPort==null||gwMac==null||clientMac==null){
			return null;
		}
//		String URL=defaultwechatauthflag+"?gw_address="+gw_address+"&gw_port="+gw_port+"&gw_id="+gw_id+"&mac="+mac+"&url="+URLEncoder.encode(url, "UTF-8");
		String URL=serverhttp+defaultwechatauthflag+"?gwIp="+gwIp+"&gwPort="+gwPort+"&gwMac="+gwMac+"&clientMac="+clientMac+"&url="+URLEncoder.encode(url, "UTF-8")
		+"&authType="+authType+"&appId=1&extend=1&shopId=1&ssid=1";
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
		log.error("===========login_GET!");
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
		if(gwIp==null||gwPort==null||gwMac==null||clientMac==null){
			return null;
		}
//		log.error("===========login_GET!  gwIp"+gwIp+" gwPort:"+gwPort+" gwMac:"+gwMac+" clientMac:"+clientMac+" url:"+url+" authType:"+authType);
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
		String URL=vehicleWifiService.login(getMACStr(gwMac),clientMac,session,happen);
		log.info("请求认证界面==>:"+URL);
		return "redirect:"+URL;
	}
	private String getMACStr(String mac){
		String result=mac.replace(":", "-");
		return result;
	}
	public static void main(String[] args) {
		Object js ="dddd";
		System.out.println(js);
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
	@RequestMapping(value="/Wifi/logined",method= RequestMethod.POST)
	public ModelAndView logined(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		HttpSession session = request.getSession();
		log.error("==start Wifi/logined");
		Object gw_address=session.getAttribute("gwIp");
		Object gw_port=session.getAttribute("gwPort");
		Object phone=session.getAttribute("phone");
		Object gw_id = session.getAttribute("gwMac");
		//客户端MAC
		Object clientMac=session.getAttribute("clientMac");
		//用户请求地址	
		Object url=session.getAttribute("url");
		log.error("==start Wifi/logined  gw_address:"+gw_address);
		if(gw_address==null||gw_port==null||phone==null||gw_id==null){
			return null;
		}
		String pubip = request.getRemoteAddr();
		Map<String,Object> map = new HashMap<String,Object>();
		String token=GetToken();
//		List<JSONObject> speedJson = vehicleWifiService.getSpeedAndTime(gw_id.toString());
//		String speed="128";
//		String timeout="15000";
//		if(speedJson!=null&&speedJson.size()>0){
//			JSONObject json = speedJson.get(0);
//			speed=JSONUtils.getString(json, "speed");
//			timeout=JSONUtils.getString(json, "timeout");			
//		}
		String speed="512";
		String timeout="15000";
		Object speeds=session.getAttribute("speed");
		Object timeouts=session.getAttribute("timeout");
		if(speeds!=null){
			speed=speeds.toString();
		}
		if(timeouts!=null){
			timeout=timeouts.toString();
		}
		String flag="0";//0 短信,1 微信
		String upspeed="8192";   //单位kbps 
		
		log.error("==start Wifi/logined  upspeed:"+upspeed);
		
		Happen happen = new Happen(null,DateUtil.getCurrentDate(),phone.toString(),null);
		log.error("==start Wifi/logined  GW_ID:"+gw_id+" phone:"+phone+" clickMac:"+clientMac+" url"+url+" happen:"+happen.toString());
		String URL=vehicleWifiService.portal(gw_id.toString(), phone.toString(), clientMac.toString(), session,url.toString(),happen);
		log.error("==start Wifi/logined  URL:"+URL);
//		URL="http://www.qq.com";
		String ecodeurl = URLEncoder.encode(URL,"UTF-8");
		String httpUrl="redirect:http://"+gw_address.toString()+":"+gw_port.toString()+"/wifidog/auth?token=" +token+ "&Phone=" +phone + "&Pubip=" + pubip + "&Speed=" +speed + "&UpSpeed=" +upspeed + "&Flag=" +flag + "&Timeout=" +timeout+"&Url="+ecodeurl;
		ModelAndView result=new ModelAndView(httpUrl);
		log.error("===httpUrl:"+httpUrl);
		return result;
	}
	
	@RequestMapping(value="/Wifi/logined",method= RequestMethod.GET)
	public void logined2(HttpServletRequest request,HttpServletResponse response){
		log.info("===========================");
		String extend=request.getParameter("extend");
		String openId=request.getParameter("openId");
		String tid=request.getParameter("tid");
		log.info("extends:"+extend+"  openId:"+openId+" tid:"+tid);
		return;
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
     	log.info("ping ... ...");
     	 this.returnResponse(response, "Pong");
     	 return;
//        return "Pong";
    }
	
//	@RequestMapping(value="/Wifi/portal",method= RequestMethod.GET)
//	public String Portal(HttpServletRequest request,HttpServletResponse response){
//		HttpSession session=request.getSession();
//		Object url=session.getAttribute("url");
//		Object gwMac=session.getAttribute("gwMac");
//		Object phonemac=session.getAttribute("clientMac");
//		Object phone = session.getAttribute("phone");
//		log.error("===WIFI/PORTAL:URL:"+url+"  gwMac:"+gwMac+" phonemac:"+phonemac+" phone:"+phone);
//		if(url==null||gwMac==null||phonemac==null||phone==null){
//			return null;
//		}
//		Happen happen = new Happen(null,DateUtil.getCurrentDate(),null,null,phone.toString());
//		String URL=vehicleWifiService.portal(gwMac.toString(), phone.toString(), phonemac.toString(), session,url.toString(),happen);
//		log.error("===WIFI/PORTAL:URL:"+URL);
////		log.info("认证成功的URL==>:"+URL);
//		return "redirect:"+URL;
//    }
	
	@RequestMapping(value="/Wifi/portal",method= RequestMethod.POST)
	public void portal2(HttpServletRequest request,HttpServletResponse response){
		return;
	}
	
//	 @RequestMapping(value="/Wifi/getAccessToken")
//	 public void getAccessToken(HttpServletRequest request,HttpServletResponse response){
//		 try{
//			 String accress=GetAccessUtil.getAccessToken();
//			this.setResultInfo(QUERY_SUCCESS_INFO,accress);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//		this.returnResponse(response, this.getResultInfo());
//	 }
	 
//	 @RequestMapping(value="/Wifi/getOpenWeChat")
//	 public void getOpenWeChat(HttpServletRequest request,HttpServletResponse response){
//		 try{
//			 
////			 String accress=GetAccessUtil.getAccessToken();
////		        var appId='wxc96cf77dc69519d4';
////		        var extend='zhengyuhuiTest';
////		        var timestamp=date.getTime();
////		        var shop_id='2544548';
////		        var authUrl='http://218.108.10.25:8080/Wifi/logined';
////		        var mac='aa:aa:aa:aa:aa:aa';
////		        var ssid='SX-BUS';
////		        var bssid='00:03:7F:7F:89:D1';
////		        var secretkey='fe609581f6a9bea7498fc4b6449411d0';
//			 HttpSession session = request.getSession();
//			 Object appId=session.getAttribute("appId");
//			 Object extend=session.getAttribute("extend");
//			 Object shop_id=session.getAttribute("shopId");
//			 Object ssid=session.getAttribute("ssid");
//			 
//			 JSONObject json = new JSONObject();
//			 json.accumulate("appId",PROCEDURCES.appid);
//			 json.accumulate("extend",extend);
//			 json.accumulate("shop_id",shop_id);
//			 json.accumulate("authUrl", wechatlogined);
//			 json.accumulate("ssid", ssid);
//			 json.accumulate("bssid", "");
//			 json.accumulate("secretkey", PROCEDURCES.secretKey);
//			this.setResultInfo(QUERY_SUCCESS_INFO,json);
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//		this.returnResponse(response, this.getResultInfo());
//	 }
	 
}
