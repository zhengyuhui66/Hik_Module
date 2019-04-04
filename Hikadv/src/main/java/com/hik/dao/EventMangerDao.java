package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface EventMangerDao {
/**
 * ��ѯ�ն�ϵͳ������δ��ѡ �е��ն�ϵͳ�б�
 * @param id
 * @return
 */
public List queryListevent(String id);	
/**
 * ��ѯ�ն�ϵͳ�����б�ѡ �е��ն�ϵͳ�б�
 * @param id
 * @return
 */
public List queryListgevent(String id);
/**
 * ��ѯ���е��ն�ϵͳ�б�
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page queryevent(int start,int limit,String search);
/**
 * ���������ն�ϵͳ��Ϣ
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int saveevent(String name, String userid,String descr,String stime,String etime);
/**
 * �����ն�ϵͳ��Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updateevent(String id,String name, String userid,String descr,String stime,String etime);
/**
 * ɾ���ն�ϵͳ��Ϣ
 */
public int deleteevent(String[] ids);
/**
 * ��ҳ��ѯ�ն�ϵͳ������Ϣ
 * @param start
 * @param limit
 * @param search
 * @return
 */
public Page querygevent(int start,int limit,String search);
/**
 * �����������ն�ϵͳ������Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int savegevent(String id,String name, String userid,String descr);
/**
 * ���µ��ն�ϵͳ������Ϣ
 * @param id
 * @param name
 * @param userid
 * @param descr
 * @return
 */
public int updategevent(String id,String name, String userid,String descr);
/**
 * ɾ���ն�ϵͳ����
 * @param ids
 * @return
 */
public int deletegevent(String[] ids);
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
public List getgeventid();


public List getEventHappen();
}
