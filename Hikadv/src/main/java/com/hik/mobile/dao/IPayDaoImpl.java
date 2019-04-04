package com.hik.mobile.dao;

import java.util.ArrayList;
import java.util.List;

import com.hik.dao.BaseHIKDao;
import com.hik.mobile.entity.OrderInfo;
import com.jcraft.jsch.UserInfo;

import net.sf.json.JSONObject;

public class IPayDaoImpl extends BaseHIKDao implements IPayDao {

	/**
	 * 根据用户输入的充值金额获取折扣率
	 */
	@Override
	public List<JSONObject> getDisCountRate(String str) {
		List prams = new ArrayList();
		String[] strShow = new String[] { "CLICKCOUNT" };
		prams.add(str);

		String sql = "";
		List<JSONObject> list = getNoPageObject(sql, prams, strShow, "得到折扣率");
		if (list != null && list.size() > 0) {
			System.out.println(list);
		}
		return list;
	}

	/**
	 * 根据用户id获取充值记录
	 */
	@Override
	public List<JSONObject> getChargeList(String userid) {
		// TODO 自动生成的方法存根
		return null;
	}

	/**
	 * 根据用户id获取用户余额
	 */
	@Override
	public List<JSONObject> getBalance(String userid) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<JSONObject> saveOrderInfo(String userid,OrderInfo orderInfo) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<JSONObject> getOrderInfo() {
		// TODO 自动生成的方法存根
		return null;
	}
	

}
