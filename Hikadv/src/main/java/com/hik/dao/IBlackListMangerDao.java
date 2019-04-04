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
	 * ��ҳ��ѯ������
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ɸѡ����
	 * @return
	 */
		Page queryBlackListManger(int start,int limit,String searchStr);
		/**
		 * ����������
		 * @param putproduct
		 * @return
		 */
		int addBlackListManger(BLACKLIST blacklist);
		/**
		 * ���º�����
		 * @param putproduct
		 * @return
		 */
		int updateBlackListManger(BLACKLIST blacklist);
		/**
		 * ɾ��������
		 * @param ids
		 * @return
		 */
		String deleteBlackListManger(String[] ids);
		
		
//		List queryifRouteManger(String[] ids);
		
	

}
