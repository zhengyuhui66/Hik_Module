package com.hik.mobile.dao;

import java.util.List;

import com.hik.mobile.entity.OrderInfo;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public interface IPayDao {
	/**
	 * �����ֵ�Ľ��õ���Ӧ���ۿ�
	 * @param str
	 * @return
	 */
	public List<JSONObject> getDisCountRate(String str);
	/**
	 * ��ȡ�û���ֵ��¼
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getChargeList(String userid);
	
	/**
	 * ��ȡ�û����
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getBalance(String userid);
	
	/**
	 * �����û�����
	 * @param userid
	 * @param orderInfo
	 * @return
	 */
	public List<JSONObject> saveOrderInfo(String userid,OrderInfo orderInfo);
	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	public List<JSONObject> getOrderInfo();
	
}
