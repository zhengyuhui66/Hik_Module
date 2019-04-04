package com.hik.mobile.dao;

import java.util.List;

import net.sf.json.JSONObject;

public interface IAdvertQueryDao {

	/**
	 * 查询出所有的模板页
	 * @return
	 */
	public List<JSONObject> getAllTemplate();
	/**
	 * 根据模板id查询广告位
	 * @param userid
	 * @param tempid
	 * @return
	 */
	public List<JSONObject> getSpaceByTemplate(String userid,String tempid);
	/**
	 * 查询所有的广告素材
	 * @return
	 */
	public List<JSONObject> getAllAd();

	/**
	 * 根据广告查询所有的广告素材
	 * @return
	 */
	public List<JSONObject> getAllAd(String userid);
}
