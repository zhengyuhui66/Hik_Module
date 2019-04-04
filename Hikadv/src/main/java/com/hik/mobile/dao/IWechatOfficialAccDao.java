package com.hik.mobile.dao;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public interface IWechatOfficialAccDao {
	
	public List<JSONObject> getRandomOfficial();
	@Deprecated
	public int saveUserInfo(String openId,String tId);
	
	public int saveUserInfo(String openId,String tId,String phoneMac,String phoneNum);
	
	public List<JSONObject> getUserInfoByMac(String usermac); 
	
	public List<JSONObject> getUserInfoByphoneNum(String phonenum);
	
	
	public List<JSONObject> getAccessTokenByUserId(String userid);
}
