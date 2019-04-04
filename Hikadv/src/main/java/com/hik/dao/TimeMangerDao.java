package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface TimeMangerDao {
/**
 * ��ѯ�ն�ϵͳ������δ��ѡ �е��ն�ϵͳ�б�
 * @param id
 * @return
 */
public List queryListtime(String id);	
/**
 * ��ѯ�ն�ϵͳ�����б�ѡ �е��ն�ϵͳ�б�
 * @param id
 * @return
 */
public List queryListgtime(String id);
/**
 * ��ѯ���е��ն�ϵͳ�б�
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page querytime(int start,int limit,String search);
/**
 * ���������ն�ϵͳ��Ϣ
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savetime(String name, String userid,String descr,String stime,String etime,String sdtime,String edtime);
/**
 * �����ն�ϵͳ��Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updatetime(String id,String name, String userid,String descr,String stime,String etime,String sdtime,String edtime);
/**
 * ɾ���ն�ϵͳ��Ϣ
 */
public int deletetime(String[] ids);
/**
 * ��ҳ��ѯ�ն�ϵͳ������Ϣ
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page querygtime(int start,int limit,String search);
/**
 * �����������ն�ϵͳ������Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savegtime(String id,String name, String userid,String descr);
/**
 * ���µ��ն�ϵͳ������Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updategtime(String id,String name, String userid,String descr);
/**
 * ɾ���ն�ϵͳ����
 * @param ids
 * @return
 */
public int deletegtime(String[] ids);
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
public List getgtimeid();

}
