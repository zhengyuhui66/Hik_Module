package com.hik.mobile.dao;

import java.util.List;

import com.hik.mobile.entity.CONVERT_RATE;

import net.sf.json.JSONObject;

public interface IMainSumDao {
	
	/**
	 * ��ȡ��·���û���
	 * @return
	 */
	public List<JSONObject> getTolUser(String userid);
	/**
	 * ���ݹ������ȡ�õ������Ͷ�Ź���ع�������
	 * @param adver
	 * @return
	 */
	public List<JSONObject> getTotalAdServing(String userid);
	
	/**
	 * ���ݼƷѷ�ʽ��ȡ�û�ת����
	 * @param couType
	 * @return
	 */
	public CONVERT_RATE getConverRate(String userid,int couType);
	
	/**
	 * ���ݹ����id�õ������������ܴ���
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdvCount(String userid);
	
	/**
	 * Chart ���� �ӿ�1
	 * ��ȡ����������
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
	 * @param type �ͻ������� android || ios
	 * @return
	 */
	public List<JSONObject> getUpdateInfo(String type);
	
	/**
	 * ��ȡ���û�������ʷ�仯����
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdUsersHisTrend(String sDate,String eDate,String userid);
	
	/**
	 * ��ȡ����ع���ʷ����
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdExpoHisTrend(String sDate,String eDate,String userid);
	
	/**
	 * ��ȡ����ܵ����ʷ����
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdClickHisTrend(String sDate,String eDate,String userid);
	
	
	
}
