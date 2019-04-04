package com.hik.mobile.util;

import java.util.Random;

public class WXUtil {
	public static String getNonceStr() {
		Random random = new Random();
		return AppMD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	
	

	/***************************************/
	/**********  微信相关接口Url  **************/
	/***************************************/

	/**
	 * 获取AccessToken
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getAccessTokenUrl(String appId,String appSecret){
		
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appId + "&secret=" + appSecret;
	}
	/**
	 * 获取门店信息
	 * @param access_token
	 * @return
	 */
	public static String getShopListUrl(String access_token){
		
		return "https://api.weixin.qq.com/bizwifi/shop/list?access_token="
				+ access_token;
	}
	/**
	 * 获取门店Secret
	 * @param access_token
	 * @return
	 */
	public static String getShopSecret(String access_token){
		return "https://api.weixin.qq.com/bizwifi/apportal/register?access_token="
				+access_token;
	}
	/**
	 * 根据用户openid获取用户信息
	 * 根据返回结果 subscribe 值为0时，代表此用户没有关注该公众号
	 * 判断用户是否关注当前公众号
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static String getUserInfo(String access_token,String openid){
		return "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
	}
	
}
