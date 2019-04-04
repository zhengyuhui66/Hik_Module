package com.hik.mobile.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IWechatOfficialAccService {

	/**
	 * 分页获取广告投放公众号
	 * @param start
	 * @param limit
	 * @param searchStr
	 * @return
	 */
	public Page getOfficialAccList(int start,int limit,String searchStr);
	/**
	 * 新增或者保存公众号
	 * @param officalAcc
	 * @return
	 */
	public int  saveOrUpdateOfficialAccList(int officalAcc);
	
	/**
	 * 删除公众号
	 * @param ids
	 * @return
	 */
	public int deleteOfficialAcc(String[] ids);
	
	/**
	 * 随机获取一个广告公众号
	 * @return
	 */
	public List<JSONObject> getRandomOfficial();
	/**
	 * 保存认证用户数据
	 * @param openid
	 * @param tid
	 * @return
	 */
	@Deprecated
	public int saveUserInfo(String openid,String tid);

	/**
	 * 保存认证用户数据
	 * @param openid
	 * @param tid
	 * @return
	 */
	public int saveUserInfo(String openid,String tid,String phoneMac,String phoneNum);
	
	/**
	 * 根据查询用mac查询用户openid
	 * @param openid
	 * @return
	 */
	public List<JSONObject> getUserInfoByMac(String usermac);
	/**
	 * 根据用户手机号码查询用户信息
	 * @param phonenum
	 * @return
	 */
	public List<JSONObject> getUserInfoByphoneNum(String phonenum);
	/**
	 * 根据用户id查询对应公众号的Accesstoken
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAccessTokenByUserId(String userid);
}
