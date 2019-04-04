package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutMangerSetDao {
	/**
	 * 分页查询
	 * @param start  开始条数
	 * @param limit 每页几条
	 * @param searchStr 条件模糊查询
	 * @return
	 */
	Page queryForPage(int start,int limit,String searchStr);
	/**
	 * 新增
	 * @param pricondition
	 * @return
	 */
	int addAdvputSet(PRICONDITION pricondition);
	/**
	 * 更新
	 * @param pricondition
	 * @return
	 */
	int updateAdvputSet(PRICONDITION pricondition);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int deleteAdvputSet(String[] id);
	
	
	List queryputSet();
}
