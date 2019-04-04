package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface TimeMangerService {
	/**
	 * 查询手机分组中未分组的手机号的列表
	 * @param id
	 * @return
	 */
	public List queryListtime(String id);
	/**
	 * 查询手机分组中手机号的列表
	 * @param id
	 * @return
	 */
	public List queryListgtime(String id);
	/**
	 * 查询所有的手机号
	 * @param id
	 * @return
	 */
	public Page querytime(int start,int limit,String search);
/**
 * 保存或 者更新所有的手机号
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
	public int saveOrupdatetime(String id,String name,String stime,String etime,String sdtime,String edtime, String userid,String descr);
/**
 * 删除手机号
 * @param ids
 * @return
 */
	public int deletetime(String[] ids);
	/**
	 * 分页查询手机分组
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygtime(int start,int limit,String search);
	/**
	 * 新增或更新手机分组
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @param timeids
	 * @return
	 */
	public int saveOrupdategtime(String id,String name, String userid,String descr,String timeids);
	/**
	 * 删除手机分组
	 * @param ids
	 * @return
	 */
	public int deletegtime(String[] ids);
	

}
