package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IHomeDao {
	/**
	 * 广告为单位的展示统计top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByAdv();
	/**
	 * 线路为单位的展示统计top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByRoute();
	/**
	 * 广告为单位的点击统计次数
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByAdv();
	/**
	 *线路为单位的点击统计次数
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByRoute();
	/**
	 * 统计上网用户数
	 * @return
	 */
	public List<JSONObject> getTotalUser();
	/**
	 * 统计历史广告总数
	 * @return
	 */
	public List<JSONObject> getTotalAdv();
	/**
	 * 统计当前线路
	 * @return
	 */
	public List<JSONObject> getTotalRoute();
	/**
	 * 统计当前公交车数
	 * @return
	 */
	public List<JSONObject> getTotalBus();
}
