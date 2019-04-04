package com.hik.mobile.service;

import java.util.List;

import com.hik.mobile.entity.CONVERT_RATE;

import net.sf.json.JSONObject;

public interface IMainSumService  {

	/**
	 * ��ȡ����ʷ�����û���
	 * @return
	 */
	public List<JSONObject> getTolUser(String userid);
	/**
	 * ��õ�ǰ�����Ͷ�ŵĹ������
	 * @param user ���ֽ
	 * @return Ͷ����
	 */
	public List<JSONObject> getTotalAdServing(String user);
	/**
	 * �û�ת����
	 * @param coutType �Ʒѷ�ʽ<br/>
	 * ��cpc�Ʒѣ�ת���ʾ��ǵ�����ˣ������=�����/չʾ��
		��cpm�Ʒѣ���ô�Ϳ���Чչʾ�ʣ�����չʾ��/������
		��cpa�Ʒѣ���ô��Ҫ�������Ч����һ����ת����Ч����/�����
	 * @return ת����
	 */
	public CONVERT_RATE getConverRate(String userid,int coutType);
	
	/**
	 * �����û�id ��ȡͶ�ŵĹ������
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdvCount(String userid);
	
	
	/**
	 * Chart ���� �ӿ�1
	 * ���ݹ����ID��ȡ��������
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<JSONObject> getChartOfSurfNetSum(String userid,String startDate,String endDate);
	/**
	 * Chart���ݽӿ�2
	 * ���ݹ����Id ��ȡ�ض�ʱ���ڼ��ڵĹ��Ͷ��
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<JSONObject> getChartOfAdDelv(String userid,String startDate,String endDate);
	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	public List<JSONObject> getUpdateInfo(String type);
	
	/**
	 * ��ѯ�ܹ���û�����ʷ����
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdUsersHisTrend(String sDate, String eDate, String userid);
	/**
	 * ��ѯ��ǰ�û�����ع���ʷ����
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdExpoHisTrend(String sDate, String eDate, String userid);
	/**
	 * ��ѯ��ǰ�û�����Ͷ�Ź������ʷ����
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdClickHisTrend(String sDate, String eDate, String userid);
}
