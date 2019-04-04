package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface TimeMangerService {
	/**
	 * ��ѯ�ֻ�������δ������ֻ��ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListtime(String id);
	/**
	 * ��ѯ�ֻ��������ֻ��ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListgtime(String id);
	/**
	 * ��ѯ���е��ֻ���
	 * @param id
	 * @return
	 */
	public Page querytime(int start,int limit,String search);
/**
 * ����� �߸������е��ֻ���
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
	public int saveOrupdatetime(String id,String name,String stime,String etime,String sdtime,String edtime, String userid,String descr);
/**
 * ɾ���ֻ���
 * @param ids
 * @return
 */
	public int deletetime(String[] ids);
	/**
	 * ��ҳ��ѯ�ֻ�����
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygtime(int start,int limit,String search);
	/**
	 * ����������ֻ�����
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @param timeids
	 * @return
	 */
	public int saveOrupdategtime(String id,String name, String userid,String descr,String timeids);
	/**
	 * ɾ���ֻ�����
	 * @param ids
	 * @return
	 */
	public int deletegtime(String[] ids);
	

}
