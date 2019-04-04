package com.hik.service;

import java.util.List;
import java.util.Set;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IGetAdvertService {
	/**
	 * 得到当前的广告
	 * @return
	 */
	public List<JSONObject> getAdvert(String busId);
	/**
	 * 
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
	 * 
	 * @param modelid
	 * @param json
	 * @return
	 */
	public List getAdvertView(String modelid,JSONObject json);
	/**
	 * 查看判断广告名称是否重复
	 * @param name
	 * @return
	 */
	public boolean getrepeatAdvertName(String name);
	/**
	 * 得到所有的物料
	 * @return
	 */
	public List getMater(String materTypeid,String materSizeid,String materCreatorid);
	/**
	 * 保存新增广告信息
	 * @param materId 物料ID
	 * @param adverturl  广告地址
	 * @param description  广告描述
	 * @param advertname 广告名称
	 * @return
	 */
	public int saveAdvert(String materId,String adverturl,String description,String advertname,String userid,Set<String> putconIdSet,String advertpropertys,String advertproperty);
	
	/**
	 * 更新广告信息
	 * @param adverid 更新ID
	 * @param materId 物料ID
	 * @param adverturl  广告地址
	 * @param description  广告描述
	 * @param advertname 广告名称
	 * @return
	 */
	public int  updateAdvert(String adverid,String materId,String adverturl,String description,String advertname,Set<String> putconIdSet,String advertpropertys,String advertproperty);
	/**
	 * 删除广告
	 * @param advertids 需要删除的广告
	 * @return
	 */
	public int deleteAdvert(String[] advertids);
	public List getAdvGrounpById(String[] advertids);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List getConditionByAdvid(String id);
	
	
	public List getAdvBymaterId(String[] materid);

}
