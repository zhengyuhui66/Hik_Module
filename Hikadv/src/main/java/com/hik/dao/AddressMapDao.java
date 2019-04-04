package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface AddressMapDao {

	public int saveaddress(String nameid,String descrid,String userid,String[] latlongid);
	
	public List getAllAddress();
	
	public int deleteAddress(String[] ids);
	
	
	/**
	 * ��ѯ�ն�ϵͳ������δ��ѡ �е��ն�ϵͳ�б�
	 * @param id
	 * @return
	 */
	public List queryListaddress(String id);	
	/**
	 * ��ѯ�ն�ϵͳ�����б�ѡ �е��ն�ϵͳ�б�
	 * @param id
	 * @return
	 */
	public List queryListgaddress(String id);
	
	
	/**
	 * ��ҳ��ѯ�ն�ϵͳ������Ϣ
	 * @param start
	 * @param limit
	 * @param search
	 * @return
	 */
	public Page querygaddress(int start,int limit,String search);
	/**
	 * �����������ն�ϵͳ������Ϣ
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @return
	 */
	public int savegaddress(String id,String name, String userid,String descr);
	/**
	 * ���µ��ն�ϵͳ������Ϣ
	 * @param id
	 * @param name
	 * @param userid
	 * @param descr
	 * @return
	 */
	public int updategaddress(String id,String name, String userid,String descr);
	/**
	 * ɾ���ն�ϵͳ����
	 * @param ids
	 * @return
	 */
	public int deletegaddress(String[] ids);
	/**
	 * �������������е��ն�ϵͳ��
	 * @param gpid
	 * @param ids
	 * @return
	 */
	public int insertgcs(String gcsid,String[] ids);
	/**
	 *ɾ�������е��ն�ϵͳ��
	 * @param gpid
	 * @param ids
	 * @return
	 */
	public int deletegcs(String id);
	/**
	 * �õ��ն�ϵͳ�б�
	 * @return
	 */
	public List getgaddressid();
}
