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
	 *  ����ios ������Ϣ
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
	 * ����ios ������Ϣ
	 * @param clientid
	 * @param content    
	 * @param apnsTitle  IOS��������֪ͨ����
	 * @param apnsBody	 IOS��������֪ͨ��Ϣ��
	 * @return
	 */
	public String pushByoneCompatWithIOS(String clientid,String content,String apnsTitle,String apnsBody);
	
}
