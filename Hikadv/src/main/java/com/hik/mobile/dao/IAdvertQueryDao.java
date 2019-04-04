package com.hik.mobile.dao;

import java.util.List;

import net.sf.json.JSONObject;

public interface IAdvertQueryDao {

	/**
	 * ��ѯ�����е�ģ��ҳ
	 * @return
	 */
	public List<JSONObject> getAllTemplate();
	/**
	 * ����ģ��id��ѯ���λ
	 * @param userid
	 * @param tempid
	 * @return
	 */
	public List<JSONObject> getSpaceByTemplate(String userid,String tempid);
	/**
	 * ��ѯ���еĹ���ز�
	 * @return
	 */
	public List<JSONObject> getAllAd();

	/**
	 * ���ݹ���ѯ���еĹ���ز�
	 * @return
	 */
	public List<JSONObject> getAllAd(String userid);
}
