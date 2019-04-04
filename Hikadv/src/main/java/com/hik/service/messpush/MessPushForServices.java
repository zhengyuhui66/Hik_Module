package com.hik.service.messpush;

import java.util.List;

import com.hik.framework.utils.Page;

public interface MessPushForServices {
	public Page queryForPage(String UserId,int start,int limit,String searchStr);
	
	public int savePushMessage(String[] userid,String content,String username,String descr);
	
	public String pushByone(String clientid,String content);
//	public List getcidByuserId(String userid);
	public String pushByGroup(String content);
	
	/**
	 * 20170122 added by cookies
	 *  兼容ios 推送消息
	 * @param userid
	 * @param content
	 * @param username
	 * @param descr
	 * @param apnsTitle
	 * @param apnsBody
	 * @return
	 */
	public int savePushMessage(String[] userid,String content,String username,String descr,String apnsTitle,String apnsBody);
	
	/**
	 * 20170122 added by cookies
	 * 兼容ios 推送消息
	 * @param clientid
	 * @param content    
	 * @param apnsTitle  IOS离线推送通知标题
	 * @param apnsBody	 IOS离线推送通知消息体
	 * @return
	 */
	public String pushByoneCompatWithIOS(String clientid,String content,String apnsTitle,String apnsBody);
	
}
