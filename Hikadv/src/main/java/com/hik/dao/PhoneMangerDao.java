package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface PhoneMangerDao {
/**
 * ��ѯ�ֻ�������δ��ѡ �е��ֻ��б�
 * @param id
 * @return
 */
public List queryListphone(String id);	
/**
 * ��ѯ�ֻ������б�ѡ �е��ֻ��б�
 * @param id
 * @return
 */
public List queryListgphone(String id);
/**
 * ��ѯ���е��ֻ��б�
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page queryphone(int start,int limit,String search);
/**
 * ���������ֻ���Ϣ
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savephone(String name, String userid,String descr);
/**
 * �����ֻ���Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updatephone(String id,String name, String userid,String descr);
/**
 * ɾ���ֻ���Ϣ
 */
public int deletephone(String[] ids);
/**
 * ��ҳ��ѯ�ֻ�������Ϣ
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page querygphone(int start,int limit,String search);
/**
 * �����������ֻ�������Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savegphone(String id,String name, String userid,String descr);
/**
 * ���µ��ֻ�������Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updategphone(String id,String name, String userid,String descr);
/**
 * ɾ���ֻ�����
 * @param ids
 * @return
 */
public int deletegphone(String[] ids);
/**
 * �������������е��ֻ���
 * @param gpid
 * @param ids
 * @return
 */
public int insertgp(String gpid,String[] ids);
/**
 *ɾ�������е��ֻ���
 * @param gpid
 * @param ids
 * @return
 */
public int deletegp(String id);
/**
 * �õ��ֻ��б�
 * @return
 */
public List getgphoneid();
}
