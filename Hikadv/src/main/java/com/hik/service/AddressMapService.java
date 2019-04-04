package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface AddressMapService {
	
	public int saveaddress(String nameid,String descrid,String userid,String[] latlongid);
	public List getAllAddress();
	
	public int deleteAddress(String[] ids);
	
	
	
	/**
	 * 查询手机分组中未分组的手机号的列表
	 * @param id
	 * @return
	 */
	public List queryListaddress(String id);
	/**
	 * 查询手机分组中手机号的列表
	 * @param id
	 * @return
	 */
	public List queryListgaddress(String id);
	/**
	 * 分页查询手机分组
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygaddress(int start,int limit,String search);
	/**
	 * 新增或更新手机分组
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @param addressids
	 * @return
	 */
	public int saveOrupdategaddress(String id,String name, String userid,String descr,String addressids);
	/**
	 * 删除手机分组
	 * @param ids
	 * @return
	 */
	public int deletegaddress(String[] ids);
}
