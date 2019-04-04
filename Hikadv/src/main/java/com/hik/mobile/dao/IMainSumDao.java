package com.hik.mobile.dao;

import java.util.List;

import com.hik.mobile.entity.CONVERT_RATE;

import net.sf.json.JSONObject;

public interface IMainSumDao {
	
	/**
	 * 获取线路总用户数
	 * @return
	 */
	public List<JSONObject> getTolUser(String userid);
	/**
	 * 根据广告主获取得到广告主投放广告曝光量总数
	 * @param adver
	 * @return
	 */
	public List<JSONObject> getTotalAdServing(String userid);
	
	/**
	 * 根据计费方式获取用户转化率
	 * @param couType
	 * @return
	 */
	public CONVERT_RATE getConverRate(String userid,int couType);
	
	/**
	 * 根据广告主id得到广告主广告点击总次数
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdvCount(String userid);
	
	/**
	 * Chart 数据 接口1
	 * 获取总上网人数
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<JSONObject> getChartOfSurfNetSum(String userid,String startDate,String endDate);
	/**
	 * Chart数据接口2
	 * 根据广告主Id 获取特定时间期间内的广告投放
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<JSONObject> getChartOfAdDelv(String userid,String startDate,String endDate);
	
	/**
	 * 获取更新信息
	 * @param type 客户端类型 android || ios
	 * @return
	 */
	public List<JSONObject> getUpdateInfo(String type);
	
	/**
	 * 获取总用户数的历史变化趋势
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdUsersHisTrend(String sDate,String eDate,String userid);
	
	/**
	 * 获取广告曝光历史趋势
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdExpoHisTrend(String sDate,String eDate,String userid);
	
	/**
	 * 获取广告总点击历史趋势
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdClickHisTrend(String sDate,String eDate,String userid);
	
	
	
}
