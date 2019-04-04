package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface EventMangerService {
	/**
	 * ��ѯ�ֻ�������δ������ֻ��ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListevent(String id);
	/**
	 * ��ѯ�ֻ��������ֻ��ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListgevent(String id);
	/**
	 * ��ѯ���е��ֻ���
	 * @param id
	 * @return
	 */
	public Page queryevent(int start,int limit,String search);
/**
 * ����� �߸������е��ֻ���
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
	public int saveOrupdateevent(String id,String name,String stime,String etime, String userid,String descr);
/**
 * ɾ���ֻ���
 * @param ids
 * @return
 */
	public int deleteevent(String[] ids);
	/**
	 * ��ҳ��ѯ�ֻ�����
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygevent(int start,int limit,String search);
	/**
	 * ����������ֻ�����
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @param eventids
	 * @return
	 */
	public int saveOrupdategevent(String id,String name, String userid,String descr,String eventids);
	/**
	 * ɾ���ֻ�����
	 * @param ids
	 * @return
	 */
	public int deletegevent(String[] ids);
	
	
	public List getEventHappen();
}
