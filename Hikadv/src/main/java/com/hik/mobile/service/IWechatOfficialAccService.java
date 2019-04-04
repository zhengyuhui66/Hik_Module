package com.hik.mobile.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IWechatOfficialAccService {

	/**
	 * ��ҳ��ȡ���Ͷ�Ź��ں�
	 * @param start
	 * @param limit
	 * @param searchStr
	 * @return
	 */
	public Page getOfficialAccList(int start,int limit,String searchStr);
	/**
	 * �������߱��湫�ں�
	 * @param officalAcc
	 * @return
	 */
	public int  saveOrUpdateOfficialAccList(int officalAcc);
	
	/**
	 * ɾ�����ں�
	 * @param ids
	 * @return
	 */
	public int deleteOfficialAcc(String[] ids);
	
	/**
	 * �����ȡһ����湫�ں�
	 * @return
	 */
	public List<JSONObject> getRandomOfficial();
	/**
	 * ������֤�û�����
	 * @param openid
	 * @param tid
	 * @return
	 */
	@Deprecated
	public int saveUserInfo(String openid,String tid);

	/**
	 * ������֤�û�����
	 * @param openid
	 * @param tid
	 * @return
	 */
	public int saveUserInfo(String openid,String tid,String phoneMac,String phoneNum);
	
	/**
	 * ���ݲ�ѯ��mac��ѯ�û�openid
	 * @param openid
	 * @return
	 */
	public List<JSONObject> getUserInfoByMac(String usermac);
	/**
	 * �����û��ֻ������ѯ�û���Ϣ
	 * @param phonenum
	 * @return
	 */
	public List<JSONObject> getUserInfoByphoneNum(String phonenum);
	/**
	 * �����û�id��ѯ��Ӧ���ںŵ�Accesstoken
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAccessTokenByUserId(String userid);
}
