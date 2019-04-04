package com.hik.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ShowClickService {
	/**
	 * 得到所有的广告
	 * @return
	 */
	public List<JSONObject> getAllAdvert();
	/**
	 * 根据广告得到物料
	 * @return
	 */
	public List<JSONObject> getMaterByAdvertId(String advertId);
	/**
	 * 得到所有的线路
	 */
	public List<JSONObject> getAllRold();
	/**
	 * 根据线路ID得到当前线路所有的公交车
	 * @param RoldId
	 * @return
	 */
	public List<JSONObject> getBusIdByRoldId(String RoldId);
	/**
	 * 查询以车辆路线为粒度的的统计信息
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param roldId 路线id
	 * @param busId 车辆ID
	 * @return
	 */
	public List<JSONObject> queryShowInfo(String stime,String etime,String roldId,String busId,String advertid,String materid);
	/**
	 * 查询以广告物料为粒度的统计信息
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param advertid 广告ID
	 * @param materid 物料ID
	 * @return
	 */
	public List<JSONObject> queryClickInfo(String stime,String etime,String roldId,String busId,String advertid,String materid);
	
	
	/**
	 * 查询路线用户上网人数
	 * @param routeid 线路ID
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @return
	 */
	public Page queryRouteSufCount(String name,String stime,String etime,int start,int limit);
	/**
	 * 查询车辆用户上网人数
	 * @param busid 车辆ID
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @return
	 */
	public Page queryBusSufCount(String name,String stime,String etime,int start,int limit);
	
	public List queryRouteSufCountTotal(String name, String stime, String etime);
	
	public List queryBusSufCountTotal(String name, String stime, String etime);
//	
//	/**
//	 * 查询路线用户上网人数
//	 * @param name 过虑名称
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param start 开始条数
//	 * @param limit 每页几条
//	 * @return
//	 */
//	public Page queryRouteSufCount(String name,String stime,String etime,int start,int limit);
//	/**
//	 * 查询车辆用户上网人数
//	 * @param name 过虑名称
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param start 开始条数
//	 * @param limit 每页几条
//	 * @return
//	 */
//	public Page queryBusSufCount(String name,String stime,String etime,int start,int limit);
}
