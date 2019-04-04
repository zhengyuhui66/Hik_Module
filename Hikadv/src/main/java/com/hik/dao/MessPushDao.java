package com.hik.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface MessPushDao {
	/**
	 * 查询车辆用户上网人数
	 * @param name 过虑名称
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @return
	 */
	public Page queryForPage(String UserId, int start, int limit, String searchStr);
	
	public int savePushMess(Map<String,String> username,String content,String name,String descr);
	
	
}
