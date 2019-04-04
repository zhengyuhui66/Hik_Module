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
	 * ��ҳ��ѯͶ�Ų�Ʒ
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ɸѡ����
	 * @return
	 */
		Page queryCorManger(int start,int limit,String searchStr);
		/**
		 * ����Ͷ�Ų�Ʒ
		 * @param putproduct
		 * @return
		 */
		int addCorManger(CORP corp);
		/**
		 * ����Ͷ�Ų�Ʒ
		 * @param putproduct
		 * @return
		 */
		int updateCorManger(CORP corp);
		/**
		 * ɾ��Ͷ�Ų�Ʒ
		 * @param ids
		 * @return
		 */
		String deleteCorManger(String[] ids);
		
		
		List queryifCorManger(String[] ids);
		
	

}
