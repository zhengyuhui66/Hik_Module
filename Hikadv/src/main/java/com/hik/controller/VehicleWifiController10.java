package com.hik.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.Happen;
import com.hik.app.entity.SHOW_COUNT_LOG;
import com.hik.dao.RedisBaseDao;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.service.SendSMSCodeService;
import com.hik.service.VehicleWifiService;
import com.hik.service.VehicleWifiService2;
import com.hik.util.Area;
import com.hik.util.SMSUtils;
import net.sf.json.JSONObject;

/**
 * 最原始的一版
 * wifi控制2.0说明
 * 第一次 正常短信认证
 * 超时一键登陆
 * @author Administrator
 *
 */

@Deprecated
@Controller
@RequestMapping("/vnsw")
public class VehicleWifiController10 extends BaseController{
	
	@Autowired
	private VehicleWifiService2 vehicleWifiService;
    @Value("${serverhttp}")
	private String serverhttp;

    @Value("${defaultwifiauthflag}")
	private String defaultwifiauthflag;
	

	/**
	 * 记录认证
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/Wifi/login",method= RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		//AP地址
		String gw_address=request.getParameter("gw_address");
		//AP端口
		String gw_port=request.getParameter("gw_port");
		//AP  MAC地址
		String gw_id=request.getParameter("gw_id");
		//客户端MAC
		String mac=request.getParameter("mac");
		//用户请求地址	
		String url=request.getParameter("url");
		if(gw_address==null||gw_port==null||gw_id==null||mac==null){
			//一层过虑
			return null;
		}
		if(url!=null){
			url=URLEncoder.encode(url, "UTF-8");
		}
		String URL=serverhttp+defaultwifiauthflag+"?gw_address="+gw_address+"&gw_port="+gw_port+"&gw_id="+gw_id+"&mac="+mac+"&url="+url;
		return "redirect:"+URL;
	}
	@RequestMapping(value="/Wifi/login",method= RequestMethod.POST)
	public void loginPost(HttpServletRequest request,HttpServletResponse response){
	}
	/**
	 * 记录认证
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/Wifi/logining",method= RequestMethod.GET)
	public String logining(HttpServletRequest request,HttpServletResponse response){
		//AP地址
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
		if(gw_address==null||gw_port==null||gw_id==null||mac==null){
			return null;
		}
//		redis.setToHashPutkey(redisBean);
//		session.setAttribute("gw_address",gw_address);
//		session.setAttribute("gw_port",gw_port);
//		session.setAttribute("gw_id",getMACStr(gw_id));
//		session.setAttribute("mac",mac);
		
		JSONObject gwMap = new JSONObject();
		gwMap.put("gw_address",gw_address);
		gwMap.put("gw_port",gw_port);
		gwMap.put("gw_id",getMACStr(gw_id));
		gwMap.put("mac",mac);
		
		String _url=null;
		try {
			if(url!=null){
				_url = URLEncoder.encode(url, "UTF-8");
//				session.setAttribute("url",_url);
				gwMap.put("url",_url);
			}else{
//				session.setAttribute("url","www.baidu.com");
				gwMap.put("url","www.baidu.com");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String redisKey=com.hik.util.Utils.getSessionId(session);
		
		String URL=null;
			//三层过滤
			Happen happen = new Happen(null,DateUtil.getCurrentDate(),null,null);
//			URL=vehicleWifiService.login(getMACStr(gw_id),mac,session,happen);
			URL=vehicleWifiService.login(gwMap,session,happen);
			//log.info("请求认证界面==>:"+URL+"   所花费的时间是："+(System.currentTimeMillis()-startime));
		return "redirect:"+URL;
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
	
	@RequestMapping(value="/Wifi/clickCount",method= RequestMethod.GET)
	public void clickCount(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String advertid=request.getParameter("advertid");
		String modelid=request.getParameter("modelid");
		String modelmodelid=request.getParameter("modelmodelid");

		String advertUrl=vehicleWifiService.saveClickCount(advertid,modelid,modelmodelid,session);
//		log.info( "用户"+phone+"  路由器ID"+tgw_id+"  URL:"+adverturl);
		this.returnResponse(response, advertUrl);
	}
	@RequestMapping(value="/Wifi/clickCount",method= RequestMethod.POST)
	public void clickCount2(HttpServletRequest request,HttpServletResponse response){
		return;
	}
	@RequestMapping(value="/Wifi/showCount",method= RequestMethod.GET)
	public void showCount(HttpServletRequest request,HttpServletResponse response){
		log.info("show count");
		HttpSession session = request.getSession();
		String advertid=request.getParameter("advertid");
		String modelid=request.getParameter("modelid");
		String advposid=request.getParameter("modelmodelid");

		int result =vehicleWifiService.saveShowCount(advertid,modelid,advposid,session);
		this.returnResponse(response, result);
	}

	@RequestMapping(value="/Wifi/logined",method= RequestMethod.POST)
	public ModelAndView logined(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String pubip = request.getRemoteAddr();
		String token=GetToken();
		String result=vehicleWifiService.logined(pubip, token, session);
		ModelAndView httpUrl = new ModelAndView(result);
		return httpUrl;
	}
	@RequestMapping(value="/Wifi/logined",method= RequestMethod.GET)
	public void loginedget(HttpServletRequest request,HttpServletResponse response){}
	
	 private String GetToken()
     {
		 String dt=System.currentTimeMillis()+"";
         return dt;
     }

	 
	 @RequestMapping(value="/Wifi/SendSMSCode",method= RequestMethod.GET)
	 public void SendSMSCode(HttpServletRequest request,HttpServletResponse response){
		 HttpSession session = request.getSession();
		 String phone = request.getParameter("phone");
//			session.setAttribute("phone",phone);
		 try{
			 String code=SMSUtils.getSMSCode();
			 String key="smscode"+phone;
			 log.info("key======>sendSMSCode:"+key+"   code:"+code);
			 session.setAttribute(key, code);
		    	vehicleWifiService.sendSMSCode(phone,code,session);
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
		String stage=request.getParameter("stage");
		String ip=request.getParameter("ip");
		String mac=request.getParameter("mac");
		String incoming=request.getParameter("incoming");
		String outgoing=request.getParameter("outgoing");
		String gw_id=request.getParameter("gw_id");
	 String result = CheckToken(token);
	 this.returnResponse(response, result);
	}
	 private String CheckToken(String token){
		 log.info("token======>"+token);
		 Pattern pattern = Pattern.compile("^[0-9]*$");
		  Matcher matcher = pattern.matcher(token);
		  boolean b= matcher.matches();
		  if(!b){
			  log.info("判断token为乱码");
			  return "Auth:0";
		  }else{
			  log.info("判断token时间戳");			  
		  }
		 long log = Long.parseLong(token);
		 long cu=System.currentTimeMillis();
		 if(cu-log>3600000l){
			 return "Auth:0";
		 }else{
			 return "Auth:1"; 
		 }
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
	public String Portal(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		String URL=vehicleWifiService.portal(session);
		return "redirect:"+URL;
    }
	
	@RequestMapping(value="/Wifi/portal",method= RequestMethod.POST)
	public void portal2(HttpServletRequest request,HttpServletResponse response){
		return;
	}
	

	
}
