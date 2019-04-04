package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutPlanService {

	/**
	 * �õ����еĲ�Ʒ������ɸѡͶ��
	 * @return
	 */
	public List getProduct(String authid);
	
	/**
	 * ��ѯ������Ϣ
	 * @return
	 */
	public List<JSONObject> getBus(String roid);
	
//	/**
//	 * Ͷ�Ŷ���
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param timeslices ʱ��Ƭ�μ���
//	 * @param busids ��������
//	 * @param jsonObject �����Լ� ���ڶ�Ӧ�Ĳ�Ʒ
//	 * @return
//	 */
//	public Object putPlan(String stime,String etime,String[] timeslices,String[] apids,JSONObject jsonObject,String userId);
//	/**
//	 * Ͷ�Ŷ���
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param timeslices ʱ��Ƭ�μ���
//	 * @param busids ��������
//	 * @param jsonObject �����Լ� ���ڶ�Ӧ�Ĳ�Ʒ
//	 * @return
//	 */
//	public Object putPlan(String stime,String timeslices,String[] apids,JSONObject jsonObject,String userId);
	
	/**
	 * Ͷ�Ŷ���
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @param timeslices ʱ��Ƭ�μ���
	 * @param busids ��������
	 * @param jsonObject �����Լ� ���ڶ�Ӧ�Ĳ�Ʒ
	 * @return
	 */
	public Object putPan(String[] stime,String[] timeslices,String[] apids,JSONObject jsonObject,String userId,long st);

	/**
	 * Ͷ�Ŷ���
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @param timeslices ʱ��Ƭ�μ���
	 * @param busids ��������
	 * @param jsonObject �����Լ� ���ڶ�Ӧ�Ĳ�Ʒ
	 * @return
	 */
	public List putPlantest(String stime,String etime,String[] timeslices,String[] busids,JSONObject jsonObject,String userId);
	
}
