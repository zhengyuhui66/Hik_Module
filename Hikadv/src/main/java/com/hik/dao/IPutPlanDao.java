package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutPlanDao {
	/**
	 * 得到所有的产品，进行筛选投放
	 * @return
	 */
	public List getProduct(String authid);
	/**
	 * 查询车辆信息
	 * @return
	 */
	public List getBus();
//	/**
//	 * 投放动作
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param timeslices 时间片段集合
//	 * @param busids 车辆集合
//	 * @param jsonObject 周期以及 周期对应的产品
//	 * @return
//	 */
//	public Object putPlan(List<Object[]> obj);
	
//	/**
//	 * 投放动作
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param timeslices 时间片段集合
//	 * @param busids 车辆集合
//	 * @param jsonObject 周期以及 周期对应的产品
//	 * @return
//	 */
//	public Object putPan(List<Object[]> obj,long st);
	
	
	public int putPanSave(List<Object[]> obj);
	
	public int putPanSaveLog(List<Object[]> obj);	
//	/**
//	 * 投放动作
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param timeslices 时间片段集合
//	 * @param busids 车辆集合
//	 * @param jsonObject 周期以及 周期对应的产品
//	 * @return
//	 */
//	public Object putPlantest(List<Object[]> obj);
	
	public List putPlanTest(List timeList,String[] timeslices, List apids,JSONObject jsonObject);

	
}
