package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface PhoneMangerService {
	/**
	 * ��ѯ�ն�ϵͳ������δ������ն�ϵͳ�ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListphone(String id);
	/**
	 * ��ѯ�ն�ϵͳ�������ն�ϵͳ�ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListgphone(String id);
	/**
	 * ��ѯ���е��ն�ϵͳ��
	 * @param id
	 * @return
	 */
	public Page queryphone(int start,int limit,String search);
/**
 * ����� �߸������е��ն�ϵͳ��
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
	public int saveOrupdatephone(String id,String name, String userid,String descr);
/**
 * ɾ���ն�ϵͳ��
 * @param ids
 * @return
 */
	public int deletephone(String[] ids);
	/**
	 * ��ҳ��ѯ�ն�ϵͳ����
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygphone(int start,int limit,String search);
	/**
	 * ����������ն�ϵͳ����
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @param phoneids
	 * @return
	 */
	public int saveOrupdategphone(String id,String name, String userid,String descr,String phoneids);
	/**
	 * ɾ���ն�ϵͳ����
	 * @param ids
	 * @return
	 */
	public int deletegphone(String[] ids);
}
