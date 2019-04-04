package com.hik.mobile.service;

import java.util.List;
import net.sf.json.JSONObject;

public interface IAdvertQueryService {
	/**
	 * ��ѯ�����е�ģ��ҳ
	 * @return
	 */
	public List<JSONObject> getAllTemplate() ;
	/**
	 * ����ģ��id��ѯ���λ
	 * @param userid
	 * @param tempid
	 * @return
	 */
	public List<JSONObject> getSpaceByTemplate(String userid, String tempid) ;
	/**
	 * ��ѯ���еĹ���ز�
	 * @return
	 */
	public List<JSONObject> getAllAd();
	public List<JSONObject> getAllAd(String userid);
}
