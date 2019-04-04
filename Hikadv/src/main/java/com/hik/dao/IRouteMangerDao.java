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
	 * ��ҳ��ѯ��·
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ɸѡ����
	 * @return
	 */
		Page queryRouteManger(int start,int limit,String searchStr);
		/**
		 * ������·
		 * @param putproduct
		 * @return
		 */
		int addRouteManger(ROUTE route);
		/**
		 * ������·
		 * @param putproduct
		 * @return
		 */
		int updateRouteManger(ROUTE route);
		/**
		 * ɾ����·
		 * @param ids
		 * @return
		 */
		String deleteRouteManger(String[] ids);
		
		
		int setting(String[] ids,String speed,String timeout);
		
		List<JSONObject> queryDeviceList(String id);
//		List queryifRouteManger(String[] ids);
		
	

}
