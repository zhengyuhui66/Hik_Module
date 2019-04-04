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
	 * ��ҳ��ѯ
	 * @param start  ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ����ģ����ѯ
	 * @return
	 */
	Page queryForPage(int start,int limit,String searchStr);
	/**
	 * ����
	 * @param pricondition
	 * @return
	 */
	int addAdvputSet(PRICONDITION pricondition);
	/**
	 * ����
	 * @param pricondition
	 * @return
	 */
	int updateAdvputSet(PRICONDITION pricondition);
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	int deleteAdvputSet(String[] id);
	
	
	List queryputSet();
}
