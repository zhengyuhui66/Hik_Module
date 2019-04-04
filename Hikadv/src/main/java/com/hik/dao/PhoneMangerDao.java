package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface PhoneMangerDao {
/**
 * 查询手机分组中未被选 中的手机列表
 * @param id
 * @return
 */
public List queryListphone(String id);	
/**
 * 查询手机分组中被选 中的手机列表
 * @param id
 * @return
 */
public List queryListgphone(String id);
/**
 * 查询所有的手机列表
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page queryphone(int start,int limit,String search);
/**
 * 新增保存手机信息
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savephone(String name, String userid,String descr);
/**
 * 更新手机信息
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updatephone(String id,String name, String userid,String descr);
/**
 * 删除手机信息
 */
public int deletephone(String[] ids);
/**
 * 分页查询手机分组信息
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page querygphone(int start,int limit,String search);
/**
 * 保存新增的手机分组信息
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savegphone(String id,String name, String userid,String descr);
/**
 * 更新的手机分组信息
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updategphone(String id,String name, String userid,String descr);
/**
 * 删除手机分组
 * @param ids
 * @return
 */
public int deletegphone(String[] ids);
/**
 * 保存新增分组中的手机号
 * @param gpid
 * @param ids
 * @return
 */
public int insertgp(String gpid,String[] ids);
/**
 *删除分组中的手机号
 * @param gpid
 * @param ids
 * @return
 */
public int deletegp(String id);
/**
 * 得到手机列表
 * @return
 */
public List getgphoneid();
}
