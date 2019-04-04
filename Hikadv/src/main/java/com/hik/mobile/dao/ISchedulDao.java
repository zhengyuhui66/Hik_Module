package com.hik.mobile.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ISchedulDao {
	/**
	 * 查询指定时间段内的广告排期 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<JSONObject> getSchedul(String startDate,String endDate);

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
	 * 根据参数获取投放记录
	 * @param start
	 * @param limit
	 * @param stime
	 * @param etime
	 * @param timeslice
	 * @param periodArr
	 * @param routeid
	 * @param busid
	 * @param userid
	 * @return
	 */
	public Page getSchedulPut4Mobile(int start, int limit, String stime, String etime, String[] timeslice,
			String[] periodArr, String routeid, String busid, String userid,String orderby);
	
}
