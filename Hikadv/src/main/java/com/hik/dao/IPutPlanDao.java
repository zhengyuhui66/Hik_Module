package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutPlanDao {
	/**
	 * �õ����еĲ�Ʒ������ɸѡͶ��
	 * @return
	 */
	public List getProduct(String authid);
	/**
	 * ��ѯ������Ϣ
	 * @return
	 */
	public List getBus();
//	/**
//	 * Ͷ�Ŷ���
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param timeslices ʱ��Ƭ�μ���
//	 * @param busids ��������
//	 * @param jsonObject �����Լ� ���ڶ�Ӧ�Ĳ�Ʒ
//	 * @return
//	 */
//	public Object putPlan(List<Object[]> obj);
	
//	/**
//	 * Ͷ�Ŷ���
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param timeslices ʱ��Ƭ�μ���
//	 * @param busids ��������
//	 * @param jsonObject �����Լ� ���ڶ�Ӧ�Ĳ�Ʒ
//	 * @return
//	 */
//	public Object putPan(List<Object[]> obj,long st);
	
	
	public int putPanSave(List<Object[]> obj);
	
	public int putPanSaveLog(List<Object[]> obj);	
//	/**
//	 * Ͷ�Ŷ���
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param timeslices ʱ��Ƭ�μ���
//	 * @param busids ��������
//	 * @param jsonObject �����Լ� ���ڶ�Ӧ�Ĳ�Ʒ
//	 * @return
//	 */
//	public Object putPlantest(List<Object[]> obj);
	
	public List putPlanTest(List timeList,String[] timeslices, List apids,JSONObject jsonObject);

	
}
