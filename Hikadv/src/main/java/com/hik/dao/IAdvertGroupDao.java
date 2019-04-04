package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IAdvertGroupDao {

	/**
	 * 分页得到所有的广告集
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getAdvertgroup(int start,int limit,String searchStr);

	public int saveAdvertgroup(ADVGROUP advgroup);
	
	public int updateAdvertgroup(ADVGROUP advgroup);
	
	public int deleteAdvertgroup(String[] ids);

	public List queryGroupPutproduct(String[] ids);
	
}
