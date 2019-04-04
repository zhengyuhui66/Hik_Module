package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IInternetUserDao {
	/**
	 * 分页查询上网用户信息
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @param searchStr 筛选条件
	 * @return
	 */
		Page queryInternetUser(int start,int limit,String searchStr);
		
	

}
