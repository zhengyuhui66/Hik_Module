package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MMPPPRODUCT;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ImmppProductDao {

	/**
	 * 分页得到所有的广告集
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getmmppgroup(int start,int limit,String searchStr);

	public int savemmppgroup(MMPPPRODUCT mmppproduct);
	
	public int updatemmppgroup(MMPPPRODUCT mmppproduct);
	
	public int deletemmppgroup(String[] ids);

	List queryifputProduct(String[] ids);
	
//	public List queryGroupPutproduct(String[] ids);
	
}
