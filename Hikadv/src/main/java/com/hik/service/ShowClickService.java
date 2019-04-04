package com.hik.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ShowClickService {
	/**
	 * �õ����еĹ��
	 * @return
	 */
	public List<JSONObject> getAllAdvert();
	/**
	 * ���ݹ��õ�����
	 * @return
	 */
	public List<JSONObject> getMaterByAdvertId(String advertId);
	/**
	 * �õ����е���·
	 */
	public List<JSONObject> getAllRold();
	/**
	 * ������·ID�õ���ǰ��·���еĹ�����
	 * @param RoldId
	 * @return
	 */
	public List<JSONObject> getBusIdByRoldId(String RoldId);
	/**
	 * ��ѯ�Գ���·��Ϊ���ȵĵ�ͳ����Ϣ
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @param roldId ·��id
	 * @param busId ����ID
	 * @return
	 */
	public List<JSONObject> queryShowInfo(String stime,String etime,String roldId,String busId,String advertid,String materid);
	/**
	 * ��ѯ�Թ������Ϊ���ȵ�ͳ����Ϣ
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @param advertid ���ID
	 * @param materid ����ID
	 * @return
	 */
	public List<JSONObject> queryClickInfo(String stime,String etime,String roldId,String busId,String advertid,String materid);
	
	
	/**
	 * ��ѯ·���û���������
	 * @param routeid ��·ID
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @return
	 */
	public Page queryRouteSufCount(String name,String stime,String etime,int start,int limit);
	/**
	 * ��ѯ�����û���������
	 * @param busid ����ID
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @return
	 */
	public Page queryBusSufCount(String name,String stime,String etime,int start,int limit);
	
	public List queryRouteSufCountTotal(String name, String stime, String etime);
	
	public List queryBusSufCountTotal(String name, String stime, String etime);
//	
//	/**
//	 * ��ѯ·���û���������
//	 * @param name ��������
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param start ��ʼ����
//	 * @param limit ÿҳ����
//	 * @return
//	 */
//	public Page queryRouteSufCount(String name,String stime,String etime,int start,int limit);
//	/**
//	 * ��ѯ�����û���������
//	 * @param name ��������
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param start ��ʼ����
//	 * @param limit ÿҳ����
//	 * @return
//	 */
//	public Page queryBusSufCount(String name,String stime,String etime,int start,int limit);
}
