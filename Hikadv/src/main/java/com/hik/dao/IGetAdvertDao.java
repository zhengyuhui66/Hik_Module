package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IGetAdvertDao {
	/**
	 * 得到当前的广告
	 * @return
	 */
	public List<JSONObject> getAdvert(String busId);
	/**
	 * 得到登陆后当前的
	 * @param busId
	 * @return
	 */
	public List<JSONObject> getLoginedAdvert(String busId);

	/**
	 * 分页得到所有的广告
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getAdvert(int start,int limit,String searchStr);
	/**
	 * 模版ID
	 * @param modelid
	 * @return
	 */
	public List<JSONObject> getModelUrl(String modelid);
	/**
	 * 广告名称
	 * @param name
	 * @return
	 */
	public List<JSONObject> getAdvertUrl(String name);
	
	/**
	 * 得到所有的物料
	 * @return
	 */
	public List getMater(String materTypeid,String materSizeid,String materCreatorid);
//	/**
//	 * 得到具体的物料信息供浏览
//	 * @param materId 物料ID
//	 * @return
//	 */
//	public List getMaterById(String materId);
	/**
	 * 保存新增广告信息
	 * @param materId 物料ID
	 * @param adverturl  广告地址
	 * @param description  广告描述
	 * @param advertname 广告名称
	 * @return
	 */
	public int saveAdvert(String id,String materId,String adverturl,String description,String advertname,String userid,String propertysid,String advertproperty);
	/**
	 * 更新广告信息
	 * @param adverid 更新ID
	 * @param materId 物料ID
	 * @param adverturl  广告地址
	 * @param description  广告描述
	 * @param advertname 广告名称
	 * @return
	 */
	public int  updateAdvert(String adverid,String materId,String adverturl,String description,String advertname,String propertysid,String advertproperty);
	/**
	 * 删除广告
	 * @param advertids 需要删除的广告
	 * @return
	 */
	public int deleteAdvert(String[] advertids);
	/**
	 * 删除广告投放设置属性
	 * @param id 需要删除的广告投放设置属性的ID
	 * @return
	 */
	public int deleteAdvputSet(String advertid);
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List getrepeatAdvertName(String name);

	/**
	 * 
	 * @return
	 */
	public List AdvertId();
	/**
	 * 
	 * @param id
	 * @param conditionid
	 * @return
	 */
	public int saveAdvPutset(String id,String conditionid);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List getConditionByAdvid(String id);
	
	public List getadvingroup(String[] id);
	
	/**
	 * 查询物料是否被制作成广告，以便判断物料是否可以删除
	 * @param materid
	 * @return
	 */
	public List getAdvBymaterId(String[] materid);
	
}
