package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IRouteMangerDao {
	/**
	 * 分页查询线路
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @param searchStr 筛选条件
	 * @return
	 */
		Page queryRouteManger(int start,int limit,String searchStr);
		/**
		 * 新增线路
		 * @param putproduct
		 * @return
		 */
		int addRouteManger(ROUTE route);
		/**
		 * 更新线路
		 * @param putproduct
		 * @return
		 */
		int updateRouteManger(ROUTE route);
		/**
		 * 删除线路
		 * @param ids
		 * @return
		 */
		String deleteRouteManger(String[] ids);
		
		
		int setting(String[] ids,String speed,String timeout);
		
		List<JSONObject> queryDeviceList(String id);
//		List queryifRouteManger(String[] ids);
		
	

}
