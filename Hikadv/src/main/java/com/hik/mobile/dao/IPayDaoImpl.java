package com.hik.mobile.dao;

import java.util.ArrayList;
import java.util.List;

import com.hik.dao.BaseHIKDao;
import com.hik.mobile.entity.OrderInfo;
import com.jcraft.jsch.UserInfo;

import net.sf.json.JSONObject;

public class IPayDaoImpl extends BaseHIKDao implements IPayDao {

	/**
	 * �����û�����ĳ�ֵ����ȡ�ۿ���
	 */
	@Override
	public List<JSONObject> getDisCountRate(String str) {
		List prams = new ArrayList();
		String[] strShow = new String[] { "CLICKCOUNT" };
		prams.add(str);

		String sql = "";
		List<JSONObject> list = getNoPageObject(sql, prams, strShow, "�õ��ۿ���");
		if (list != null && list.size() > 0) {
			System.out.println(list);
		}
		return list;
	}

	/**
	 * �����û�id��ȡ��ֵ��¼
	 */
	@Override
	public List<JSONObject> getChargeList(String userid) {
		// TODO �Զ����ɵķ������
		return null;
	}

	/**
	 * �����û�id��ȡ�û����
	 */
	@Override
	public List<JSONObject> getBalance(String userid) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	public List<JSONObject> saveOrderInfo(String userid,OrderInfo orderInfo) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	public List<JSONObject> getOrderInfo() {
		// TODO �Զ����ɵķ������
		return null;
	}
	

}
