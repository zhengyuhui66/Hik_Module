package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface PhoneMangerService {
	/**
	 * 查询终端系统分组中未分组的终端系统号的列表
	 * @param id
	 * @return
	 */
	public List queryListphone(String id);
	/**
	 * 查询终端系统分组中终端系统号的列表
	 * @param id
	 * @return
	 */
	public List queryListgphone(String id);
	/**
	 * 查询所有的终端系统号
	 * @param id
	 * @return
	 */
	public Page queryphone(int start,int limit,String search);
/**
 * 保存或 者更新所有的终端系统号
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
	public int saveOrupdatephone(String id,String name, String userid,String descr);
/**
 * 删除终端系统号
 * @param ids
 * @return
 */
	public int deletephone(String[] ids);
	/**
	 * 分页查询终端系统分组
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygphone(int start,int limit,String search);
	/**
	 * 新增或更新终端系统分组
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @param phoneids
	 * @return
	 */
	public int saveOrupdategphone(String id,String name, String userid,String descr,String phoneids);
	/**
	 * 删除终端系统分组
	 * @param ids
	 * @return
	 */
	public int deletegphone(String[] ids);
}
