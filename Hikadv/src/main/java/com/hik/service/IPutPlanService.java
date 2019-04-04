package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutPlanService {

	/**
	 * 得到所有的产品，进行筛选投放
	 * @return
	 */
	public List getProduct(String authid);
	
	/**
	 * 查询车辆信息
	 * @return
	 */
	public List<JSONObject> getBus(String roid);
	
//	/**
//	 * 投放动作
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param timeslices 时间片段集合
//	 * @param busids 车辆集合
//	 * @param jsonObject 周期以及 周期对应的产品
//	 * @return
//	 */
//	public Object putPlan(String stime,String etime,String[] timeslices,String[] apids,JSONObject jsonObject,String userId);
//	/**
//	 * 投放动作
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param timeslices 时间片段集合
//	 * @param busids 车辆集合
//	 * @param jsonObject 周期以及 周期对应的产品
//	 * @return
//	 */
//	public Object putPlan(String stime,String timeslices,String[] apids,JSONObject jsonObject,String userId);
	
	/**
	 * 投放动作
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param timeslices 时间片段集合
	 * @param busids 车辆集合
	 * @param jsonObject 周期以及 周期对应的产品
	 * @return
	 */
	public Object putPan(String[] stime,String[] timeslices,String[] apids,JSONObject jsonObject,String userId,long st);

	/**
	 * 投放动作
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param timeslices 时间片段集合
	 * @param busids 车辆集合
	 * @param jsonObject 周期以及 周期对应的产品
	 * @return
	 */
	public List putPlantest(String stime,String etime,String[] timeslices,String[] busids,JSONObject jsonObject,String userId);
	
}
