package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.BLACKLIST;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IBlackListMangerDao {
	/**
	 * 分页查询黑名单
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @param searchStr 筛选条件
	 * @return
	 */
		Page queryBlackListManger(int start,int limit,String searchStr);
		/**
		 * 新增黑名单
		 * @param putproduct
		 * @return
		 */
		int addBlackListManger(BLACKLIST blacklist);
		/**
		 * 更新黑名单
		 * @param putproduct
		 * @return
		 */
		int updateBlackListManger(BLACKLIST blacklist);
		/**
		 * 删除黑名单
		 * @param ids
		 * @return
		 */
		String deleteBlackListManger(String[] ids);
		
		
//		List queryifRouteManger(String[] ids);
		
	

}
