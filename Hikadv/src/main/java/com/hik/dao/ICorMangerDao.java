package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ICorMangerDao {
	/**
	 * 分页查询投放产品
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @param searchStr 筛选条件
	 * @return
	 */
		Page queryCorManger(int start,int limit,String searchStr);
		/**
		 * 新增投放产品
		 * @param putproduct
		 * @return
		 */
		int addCorManger(CORP corp);
		/**
		 * 更新投放产品
		 * @param putproduct
		 * @return
		 */
		int updateCorManger(CORP corp);
		/**
		 * 删除投放产品
		 * @param ids
		 * @return
		 */
		String deleteCorManger(String[] ids);
		
		
		List queryifCorManger(String[] ids);
		
	

}
