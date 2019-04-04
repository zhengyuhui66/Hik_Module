package com.hik.mobile.dao;

import java.util.List;

import com.hik.mobile.entity.OrderInfo;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public interface IPayDao {
	/**
	 * 传入充值的金额，得到对应的折扣
	 * @param str
	 * @return
	 */
	public List<JSONObject> getDisCountRate(String str);
	/**
	 * 获取用户充值记录
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getChargeList(String userid);
	
	/**
	 * 获取用户余额
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getBalance(String userid);
	
	/**
	 * 保存用户订单
	 * @param userid
	 * @param orderInfo
	 * @return
	 */
	public List<JSONObject> saveOrderInfo(String userid,OrderInfo orderInfo);
	/**
	 * 获取订单信息
	 * @return
	 */
	public List<JSONObject> getOrderInfo();
	
}
