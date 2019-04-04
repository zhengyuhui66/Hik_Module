package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface AddressMapService {
	
	public int saveaddress(String nameid,String descrid,String userid,String[] latlongid);
	public List getAllAddress();
	
	public int deleteAddress(String[] ids);
	
	
	
	/**
	 * ��ѯ�ֻ�������δ������ֻ��ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListaddress(String id);
	/**
	 * ��ѯ�ֻ��������ֻ��ŵ��б�
	 * @param id
	 * @return
	 */
	public List queryListgaddress(String id);
	/**
	 * ��ҳ��ѯ�ֻ�����
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygaddress(int start,int limit,String search);
	/**
	 * ����������ֻ�����
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @param addressids
	 * @return
	 */
	public int saveOrupdategaddress(String id,String name, String userid,String descr,String addressids);
	/**
	 * ɾ���ֻ�����
	 * @param ids
	 * @return
	 */
	public int deletegaddress(String[] ids);
}
