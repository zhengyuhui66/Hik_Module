package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface AddressMapDao {

	public int saveaddress(String nameid,String descrid,String userid,String[] latlongid);
	
	public List getAllAddress();
	
	public int deleteAddress(String[] ids);
	
	
	/**
	 * 查询终端系统分组中未被选 中的终端系统列表
	 * @param id
	 * @return
	 */
	public List queryListaddress(String id);	
	/**
	 * 查询终端系统分组中被选 中的终端系统列表
	 * @param id
	 * @return
	 */
	public List queryListgaddress(String id);
	
	
	/**
	 * 分页查询终端系统分组信息
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygaddress(int start,int limit,String search);
	/**
	 * 保存新增的终端系统分组信息
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @return
	 */
	public int savegaddress(String id,String name, String userid,String descr);
	/**
	 * 更新的终端系统分组信息
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @return
	 */
	public int updategaddress(String id,String name, String userid,String descr);
	/**
	 * 删除终端系统分组
	 * @param ids
	 * @return
	 */
	public int deletegaddress(String[] ids);
	/**
	 * 保存新增分组中的终端系统号
	 * @param gpid
	 * @param ids
	 * @return
	 */
	public int insertgcs(String gcsid,String[] ids);
	/**
	 *删除分组中的终端系统号
	 * @param gpid
	 * @param ids
	 * @return
	 */
	public int deletegcs(String id);
	/**
	 * 得到终端系统列表
	 * @return
	 */
	public List getgaddressid();
}
