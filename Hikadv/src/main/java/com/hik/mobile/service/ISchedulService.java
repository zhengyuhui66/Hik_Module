package com.hik.mobile.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ISchedulService {
	/**
	 * ��ѯָ��ʱ����ڵĹ������ 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<JSONObject> getSchedul(String startDate, String endDate);
	/**
	 * ��ѯָ��������Ͷ�ŵĹ������
	 * @param Date
	 * @return
	 */
	public List<JSONObject> getSchedulByDate(String Date);
	
	/**
	 * ��ȡ������·
	 * @param lineId
	 * @return
	 */
	public List<JSONObject> getAllLine();
	/**
	 * ������·Id��ȡ���� 
	 * @param lineId
	 * @return
	 */
	public List<JSONObject> getCarByLine(String lineId);
	/**
	 * ��ȡ������·�µ����г���
	 * @return
	 */
	public List<JSONObject> getAllCar();
	
	/**
	 * ����ɸѡ��������ȡͶ������
	 * @return
	 */
	public Page getSchedulPut4Mobile(int start, int limit, String stime, String etime, String[] timeslice, String[] periodArr, String routeid, String busid, String userid,String orderby);
	
}
