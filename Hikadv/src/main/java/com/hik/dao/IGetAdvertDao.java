package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IGetAdvertDao {
	/**
	 * �õ���ǰ�Ĺ��
	 * @return
	 */
	public List<JSONObject> getAdvert(String busId);
	/**
	 * �õ���½��ǰ��
	 * @param busId
	 * @return
	 */
	public List<JSONObject> getLoginedAdvert(String busId);

	/**
	 * ��ҳ�õ����еĹ��
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getAdvert(int start,int limit,String searchStr);
	/**
	 * ģ��ID
	 * @param modelid
	 * @return
	 */
	public List<JSONObject> getModelUrl(String modelid);
	/**
	 * �������
	 * @param name
	 * @return
	 */
	public List<JSONObject> getAdvertUrl(String name);
	
	/**
	 * �õ����е�����
	 * @return
	 */
	public List getMater(String materTypeid,String materSizeid,String materCreatorid);
//	/**
//	 * �õ������������Ϣ�����
//	 * @param materId ����ID
//	 * @return
//	 */
//	public List getMaterById(String materId);
	/**
	 * �������������Ϣ
	 * @param materId ����ID
	 * @param adverturl  ����ַ
	 * @param description  �������
	 * @param advertname �������
	 * @return
	 */
	public int saveAdvert(String id,String materId,String adverturl,String description,String advertname,String userid,String propertysid,String advertproperty);
	/**
	 * ���¹����Ϣ
	 * @param adverid ����ID
	 * @param materId ����ID
	 * @param adverturl  ����ַ
	 * @param description  �������
	 * @param advertname �������
	 * @return
	 */
	public int  updateAdvert(String adverid,String materId,String adverturl,String description,String advertname,String propertysid,String advertproperty);
	/**
	 * ɾ�����
	 * @param advertids ��Ҫɾ���Ĺ��
	 * @return
	 */
	public int deleteAdvert(String[] advertids);
	/**
	 * ɾ�����Ͷ����������
	 * @param id ��Ҫɾ���Ĺ��Ͷ���������Ե�ID
	 * @return
	 */
	public int deleteAdvputSet(String advertid);
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List getrepeatAdvertName(String name);

	/**
	 * 
	 * @return
	 */
	public List AdvertId();
	/**
	 * 
	 * @param id
	 * @param conditionid
	 * @return
	 */
	public int saveAdvPutset(String id,String conditionid);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List getConditionByAdvid(String id);
	
	public List getadvingroup(String[] id);
	
	/**
	 * ��ѯ�����Ƿ������ɹ�棬�Ա��ж������Ƿ����ɾ��
	 * @param materid
	 * @return
	 */
	public List getAdvBymaterId(String[] materid);
	
}
