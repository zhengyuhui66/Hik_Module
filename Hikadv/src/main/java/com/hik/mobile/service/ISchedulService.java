package com.hik.mobile.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ISchedulService {
	/**
	 * 查询指定时间段内的广告排期 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<JSONObject> getSchedul(String startDate, String endDate);
	/**
	 * 查询指定日期内投放的广告排期
	 * @param Date
	 * @return
	 */
	public List<JSONObject> getSchedulByDate(String Date);
	
	/**
	 * 获取所有线路
	 * @param lineId
	 * @return
	 */
	public List<JSONObject> getAllLine();
	/**
	 * 根据线路Id获取车辆 
	 * @param lineId
	 * @return
	 */
	public List<JSONObject> getCarByLine(String lineId);
	/**
	 * 获取所有线路下的所有车辆
	 * @return
	 */
	public List<JSONObject> getAllCar();
	
	/**
	 * 根据筛选的条件获取投放排期
	 * @return
	 */
	public Page getSchedulPut4Mobile(int start, int limit, String stime, String etime, String[] timeslice, String[] periodArr, String routeid, String busid, String userid,String orderby);
	
}
