package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IHomeDao {
	/**
	 * ���Ϊ��λ��չʾͳ��top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByAdv();
	/**
	 * ��·Ϊ��λ��չʾͳ��top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByRoute();
	/**
	 * ���Ϊ��λ�ĵ��ͳ�ƴ���
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByAdv();
	/**
	 *��·Ϊ��λ�ĵ��ͳ�ƴ���
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByRoute();
	/**
	 * ͳ�������û���
	 * @return
	 */
	public List<JSONObject> getTotalUser();
	/**
	 * ͳ����ʷ�������
	 * @return
	 */
	public List<JSONObject> getTotalAdv();
	/**
	 * ͳ�Ƶ�ǰ��·
	 * @return
	 */
	public List<JSONObject> getTotalRoute();
	/**
	 * ͳ�Ƶ�ǰ��������
	 * @return
	 */
	public List<JSONObject> getTotalBus();
}
