package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface TimeMangerDao {
/**
 * 查询终端系统分组中未被选 中的终端系统列表
 * @param id
 * @return
 */
public List queryListtime(String id);	
/**
 * 查询终端系统分组中被选 中的终端系统列表
 * @param id
 * @return
 */
public List queryListgtime(String id);
/**
 * 查询所有的终端系统列表
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page querytime(int start,int limit,String search);
/**
 * 新增保存终端系统信息
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savetime(String name, String userid,String descr,String stime,String etime,String sdtime,String edtime);
/**
 * 更新终端系统信息
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updatetime(String id,String name, String userid,String descr,String stime,String etime,String sdtime,String edtime);
/**
 * 删除终端系统信息
 */
public int deletetime(String[] ids);
/**
 * 分页查询终端系统分组信息
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page querygtime(int start,int limit,String search);
/**
 * 保存新增的终端系统分组信息
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savegtime(String id,String name, String userid,String descr);
/**
 * 更新的终端系统分组信息
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updategtime(String id,String name, String userid,String descr);
/**
 * 删除终端系统分组
 * @param ids
 * @return
 */
public int deletegtime(String[] ids);
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
public List getgtimeid();

}
