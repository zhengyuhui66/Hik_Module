package com.hik.mobile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.MD5Util;
import com.hik.interceptor.HikLog;
import com.hik.mobile.entity.AccessToken;
import com.hik.mobile.service.IWechatOfficialAccService;
import com.hik.mobile.util.HttpURLConnectionTest;
import com.hik.mobile.util.WXUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mobile")
public class WechatOfficialAccController extends BaseController {

	@Autowired
	IWechatOfficialAccService iwechatofficialservice;

	String appId = "";
	String appSecret = "";
	String phoneMac = "";
	String phoneNum = "";
	String accessToken="";
	@RequestMapping("/wechatOfficialAcc")
	@HikLog(content = "随机获取一个广告公众账号", curd = LogCommon.QUERY)
	public void getRandomOfficialAcc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> rst = iwechatofficialservice.getRandomOfficial();
			this.setResultInfo(QUERY_SUCCESS_INFO, rst);
			appId = rst.get(0).optString("APPID");
			appSecret = rst.get(0).optString("APPSECRET");
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	@RequestMapping("/saveUserInfo")
	@HikLog(content = "接收用户信息,需要在5秒内响应回传status 200 ，不然微信服务器报公众号异常", curd = LogCommon.UPDATE)
	public void recieveUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String openid = request.getParameter("openId");
			String tid = request.getParameter("tid");

			if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(tid)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "openid或者tid不存在，此接口为微信服务器在认证过程中调用");
			} else {
				int result = iwechatofficialservice.saveUserInfo(openid, tid, phoneMac, phoneNum);
				this.setResultInfo(INSERT_SUCCESS_INFO, result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	@RequestMapping("/getAccessInfo")
	@HikLog(content = "在服务端获取AccessToken等", curd = LogCommon.QUERY)
	public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// String hikAppId="wxc96cf77dc69519d4";
			// String hikAppSecret="955be1b3fa5eff774f49bc3c9da8f7cf";
			String hikAppId = request.getParameter("appid");
			String hikAppSecret = request.getParameter("appsecret");
			String mExtend = request.getParameter("extend");
			JSONObject extendObj = JSONObject.fromObject(mExtend);
			phoneMac = extendObj.optString("phonemac");
			phoneNum = extendObj.optString("phonenum");
			String userAgent = extendObj.optString("useragent");
			System.out.println(userAgent);
			if (StringUtils.isEmpty(phoneMac) || StringUtils.isEmpty(phoneNum)) {
				log.info("用户手机号码或者mac地址未获取到phonemac/phonenum 字段缺失,extend:" + mExtend);
			}

			if (StringUtils.isEmpty(hikAppId) || StringUtils.isEmpty(hikAppSecret)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "hikAppId或者hikAppSecret不存在，请配置至少一个公众号");
			} else {
				String urlToken = WXUtil.getAccessTokenUrl(hikAppId, hikAppSecret);
				String access_result = HttpURLConnectionTest.httpURLConectionGET(urlToken);
				JSONObject accessObject = JSONObject.fromObject(access_result);
				// String urlShopParam =
				// "https://api.weixin.qq.com/bizwifi/shop/list?access_token="
				// + accessObject.optString("access_token");
				accessToken=accessObject.optString("access_token");
				String urlShopParam = WXUtil.getShopListUrl(accessToken);

				JSONObject potalParamObj = new JSONObject();
				potalParamObj.element("pageindex", 1);
				potalParamObj.element("pagesize", 10);
				// String parm="{\"shop_id\": 2390820,\"ssid\": \"SX-BUS\"}";

				// 请求门店下设备参数
				String potalResult = HttpURLConnectionTest.httpURLConnectionPOST(urlShopParam,
						potalParamObj.toString());
				JSONObject potalResultObject = JSONObject.fromObject(potalResult);
				if (potalResultObject.optInt("errcode") > 0) {
					this.setResultInfo(QUERY_FAILURE_INFO, "门店信息请求失败," + potalResultObject.optString("errmsg"));
				} else if (potalResultObject.optJSONObject("data").optInt("totalcount") == 0) {
					this.setResultInfo(QUERY_FAILURE_INFO, "当前公众号下未查询到门店信息。");
				} else {

					JSONObject potalObject = potalResultObject.optJSONObject("data").optJSONArray("records")
							.optJSONObject(0);
					String shopid = potalObject.optString("shop_id"); // AP设备所在门店的ID
					String ssid = potalObject.optString("ssid"); // AP设备信号名称，非必须

					String bssid = ssid; // AP设备mac地址，非必须
					String extend = "extend"; // 开发者自定义参数集合
					long timestamp = new Date().getTime(); // 时间戳(毫秒)

					// String authUrl =
					// "http://218.108.10.25:8080/mobile/saveUserInfo";
					// //服务器回调地址
					String authUrl = "http://10.10.4.92:7080/Hikadv/mobile/saveUserInfo";

					String mac = "38:BC:1A:14:72:9B"; // 用户手机mac地址 安卓设备必需
					// String shopid="2544548";
					// String ssid="ZH-Test";
					// String bssid=mac;

					JSONObject shopObj = new JSONObject();
					shopObj.element("shop_id", shopid);
					shopObj.element("ssid", ssid);

					// String secretUrl =
					// "https://api.weixin.qq.com/bizwifi/apportal/register?access_token="
					// + accessObject.optString("access_token");
					String secretUrl = WXUtil.getShopSecret(accessToken);

					String secretObj = HttpURLConnectionTest.httpURLConnectionPOST(secretUrl, shopObj.toString());
					// org.json.JSONObject secObj = new
					// org.json.JSONObject(secretObj);
					JSONObject secObj = JSONObject.fromObject(secretObj);
					if (secObj.optInt("errcode") > 0) {
						this.setResultInfo(QUERY_FAILURE_INFO, "门店SecretKey 获取失败" + secObj.optString("errmsg"));
					} else {

						String secretkey = secObj.optJSONObject("data").optString("secretkey");

						// var toSign = appId + extend + timestamp + shopId +
						// authUrl + mac + ssid + bssid + secretKey;
						String toSign = appId + extend + timestamp + shopid + authUrl + mac + ssid + bssid + secretkey;
						String sign = MD5Util.MD5(toSign).toLowerCase();
						List<JSONObject> resultlist = new ArrayList<JSONObject>();
						JSONObject object = new JSONObject();
						object.element("sign", sign);
						object.element("appid", hikAppId);
						object.element("appsecret", hikAppSecret);
						object.element("timestamp", timestamp);
						object.element("shopid", shopid);
						object.element("mac", mac);
						object.element("ssid", ssid);
						object.element("authurl", authUrl);
						object.element("extend", extend);
						object.element("bssid", bssid);
						object.element("secretkey", secretkey);
						this.setResultInfo(QUERY_SUCCESS_INFO, object);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	@RequestMapping("/getshopIdByAccessToken")
	@HikLog(content = "在服务端获取AccessToken", curd = LogCommon.QUERY)
	public void getshopIdByAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// String hikAppId="wxc96cf77dc69519d4";
			// String hikAppSecret="955be1b3fa5eff774f49bc3c9da8f7cf";
			String hikAppId = request.getParameter("appid");
			String hikAppSecret = request.getParameter("appsecret");

			if (StringUtils.isEmpty(hikAppId) || StringUtils.isEmpty(hikAppSecret)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "hikAppId或者hikAppSecret不存在，请配置至少一个公众号");
			} else {
				String url = WXUtil.getAccessTokenUrl(hikAppId, hikAppSecret);
				String access_result = HttpURLConnectionTest.httpURLConectionGET(url);
				this.setResultInfo(QUERY_SUCCESS_INFO, access_result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	@RequestMapping("/checkOpenidAttentioned")
	@HikLog(content = "检查用户openid是否关注了当前的公众账号", curd = LogCommon.QUERY)
	public void checkOpenidAttentioned(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userMac = request.getParameter("mac");
			if (StringUtils.isEmpty(userMac)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "用户mac未获取到");
			} else {
				List<JSONObject> result = iwechatofficialservice.getUserInfoByMac(userMac);
				if (result.size() > 0) {
					String openId = result.get(0).optString("OPENID");
					String url = WXUtil.getUserInfo(accessToken, openId);

					String wxUserResult = HttpURLConnectionTest.httpURLConectionGET(url);
					JSONObject wxUserObj = JSONObject.fromObject(wxUserResult);
					if (wxUserObj.containsKey("errcode")) {
						// 返回错误 例如{"errcode":40013,"errmsg":"invalid appid"}
						// 用户未关注，
						this.setResultInfo(QUERY_SUCCESS_INFO, false);
					} else {
						// 用户已经关注
						this.setResultInfo(QUERY_SUCCESS_INFO, true);
					}
				} else {
					// 用户未关注
					this.setResultInfo(QUERY_SUCCESS_INFO, false);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	public class TokenThread implements Runnable {
		// public static final String appID = "wx3e4a89adbc62b1f9";
		// public static final String appScret =
		// "1cafcbae8abed2fb0ba31394509c12c9";
		public AccessToken access_token = null;

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while (true) {
				try {
//					String urlToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
//							+ appId + "&secret=" + appSecret;
					String urlToken=WXUtil.getAccessTokenUrl(appId, appSecret);
					String access_result = HttpURLConnectionTest.httpURLConectionGET(urlToken);
					if (null != access_result) {
						System.out.println("accessToken获取成功：" + access_result);
						JSONObject accessObject = JSONObject.fromObject(access_result);
						// 7000秒之后重新进行获取
						access_token.setAccess_token(accessObject.optString("access_token"));
						access_token.setExpires_in(accessObject.optInt("expires_in"));
						Thread.sleep((accessObject.optInt("expires_in") - 200) * 1000);
					} else {
						// 获取失败时，60秒之后尝试重新获取
						Thread.sleep(60 * 1000);
					}
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
		}
	}

	@RequestMapping("/getAccessTokenInLocal")
	@HikLog(content = "在服务端获取AccessToken", curd = LogCommon.QUERY)
	public void getAccessTokenByUserid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userid = request.getParameter("userid");

			if (StringUtils.isEmpty(userid)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "用户ID不能为空");
			} else {
				
				List<JSONObject> result=iwechatofficialservice.getAccessTokenByUserId(userid);
				if (result.size()>0) {
					this.setResultInfo(QUERY_SUCCESS_INFO, result);	
				}else{
					this.setResultInfo(QUERY_FAILURE_INFO, "您尚未在系统中投放推广的微信公众号");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

}
