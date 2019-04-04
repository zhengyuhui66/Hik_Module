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
	/**********  ΢����ؽӿ�Url  **************/
	/***************************************/

	/**
	 * ��ȡAccessToken
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getAccessTokenUrl(String appId,String appSecret){
		
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appId + "&secret=" + appSecret;
	}
	/**
	 * ��ȡ�ŵ���Ϣ
	 * @param access_token
	 * @return
	 */
	public static String getShopListUrl(String access_token){
		
		return "https://api.weixin.qq.com/bizwifi/shop/list?access_token="
				+ access_token;
	}
	/**
	 * ��ȡ�ŵ�Secret
	 * @param access_token
	 * @return
	 */
	public static String getShopSecret(String access_token){
		return "https://api.weixin.qq.com/bizwifi/apportal/register?access_token="
				+access_token;
	}
	/**
	 * �����û�openid��ȡ�û���Ϣ
	 * ���ݷ��ؽ�� subscribe ֵΪ0ʱ��������û�û�й�ע�ù��ں�
	 * �ж��û��Ƿ��ע��ǰ���ں�
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static String getUserInfo(String access_token,String openid){
		return "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
	}
	
}
