package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutProductDao {
	/**
	 * ��ҳ��ѯͶ�Ų�Ʒ
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ɸѡ����
	 * @return
	 */
		Page queryputProduct(int start,int limit,String searchStr);
		/**
		 * ����Ͷ�Ų�Ʒ
		 * @param putproduct
		 * @return
		 */
		int addputProduct(PUTPRODUCT putproduct);
		/**
		 * ����Ͷ�Ų�Ʒ
		 * @param putproduct
		 * @return
		 */
		int updateputProduct(PUTPRODUCT putproduct);
		/**
		 * ɾ��Ͷ�Ų�Ʒ
		 * @param ids
		 * @return
		 */
		int deleteputProduct(String[] ids);
		
		
		List queryifputProduct(String[] ids);
		
	

}
