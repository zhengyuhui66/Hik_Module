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
	 * ��ѯ�����û���������
	 * @param name ��������
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @return
	 */
	public Page queryForPage(String UserId, int start, int limit, String searchStr);
	
	public int savePushMess(Map<String,String> username,String content,String name,String descr);
	
	
}
