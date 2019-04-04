package com.hik.mobile.service;

import java.util.List;

import com.hik.mobile.entity.CONVERT_RATE;

import net.sf.json.JSONObject;

public interface IMainSumService  {

	/**
	 * 获取总历史在线用户数
	 * @return
	 */
	public List<JSONObject> getTolUser(String userid);
	/**
	 * 获得当前广告主投放的广告总数
	 * @param user 广告纸
	 * @return 投放数
	 */
	public List<JSONObject> getTotalAdServing(String user);
	/**
	 * 用户转化率
	 * @param coutType 计费方式<br/>
	 * 按cpc计费，转化率就是点击率了，点击率=点击数/展示数
		按cpm计费，那么就看有效展示率，就是展示数/请求数
		按cpa计费，那么需要看点击到效果这一步的转化，效果数/点击数
	 * @return 转化率
	 */
	public CONVERT_RATE getConverRate(String userid,int coutType);
	
	/**
	 * 根据用户id 获取投放的广告总数
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdvCount(String userid);
	
	
	/**
	 * Chart 数据 接口1
	 * 根据广告主ID获取上网人数
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
	 * @return
	 */
	public List<JSONObject> getUpdateInfo(String type);
	
	/**
	 * 查询总广告用户数历史趋势
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdUsersHisTrend(String sDate, String eDate, String userid);
	/**
	 * 查询当前用户广告曝光历史趋势
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdExpoHisTrend(String sDate, String eDate, String userid);
	/**
	 * 查询当前用户所有投放广告点击历史趋势
	 * @param sDate
	 * @param eDate
	 * @param userid
	 * @return
	 */
	public List<JSONObject> getAdClickHisTrend(String sDate, String eDate, String userid);
}
