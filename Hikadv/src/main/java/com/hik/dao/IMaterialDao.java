package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IMaterialDao {
	public Page queryMateriel(String UserId,int start,int limit,String searchStr);
	/**
	 * Ӫ���ͻ���Ա �������
	 * @param UserId
	 * @param audit_state
	 * @param start
	 * @param limit
	 * @return
	 */
//	public Page queryMaterAudit(String UserId,List audit_state,int start,int limit,String searchStr);
//	/**
//	 * ��������Ա �������
//	 * @param state
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
	public Page queryMaterhyperAudit(int start, int limit,String searchStr);
//	/**
//	 * �߼�����Ա �������
//	 * @param UserId
//	 * @param state
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
//	public Page queryMaterSeniorAudit(String UserId,int start, int limit,String searchStr);
	
	public int auditUpdate(String auditId, String audit_state, String audit_result,String audit_desc,String UserId,String stime);
	
	public List<JSONObject> getAuditInfo(String materId);
	
	public int saveAuditInfo(AUDIT_INFO audit_INFO);
	
	public List<JSONObject> getProce_desc();
	
	public List<JSONObject> getAuditUser(String UserId);
	/**
	 * �õ���ǰ�û������е����û�
	 * @return
	 */
	public List<JSONObject> getSubUser(String UserId);
	
	public List<JSONObject> getAuditChecked(String UserId);
	
	
//	public List<JSONObject> getAudit(String UserId,String audit);
	
	
	public int saveOrUpdateAudituser(AUDIT_USERINFO  au);
	/**
	 * ɾ��AUDIT_USERINFO �е�����
	 * @param puserId
	 * @return
	 */
	public int deleteAuditUser(String puserId);
	
//	public int deleteAuditSubUser(String userid,String audit_id);
//	/**
//	 * ɾ������Ȩ�û�
//	 * @param userId
//	 * @return
//	 */
	
//	public int deleteAuditsubUser(String userId);
//	/**
//	 * ��ѯ�û�����
//	 * @param UserId
//	 * @return
//	 */
//	public int getUserLevel(String UserId);
	/**
	 * ɾ������
	 * @param params
	 * @return
	 */
	public int deleteMaterInfo(List<String> params);
	/**
	 * ɾ������
	 */
	public int deleteMaterInfo(String params);
	/**
	 * 
	 * @param userId
	 * @return ��ѯ����ID
	 */
	public List getPUserFromAudit_userinfo(String userId);
	/**
	 * 
	 * @param audit_proce
	 * @return �鿴ǰһ�������Ƿ�ͨ��
	 */
	public List checkiPassAudit(String audit_proce,String mater_id);
	/**
	 * ��ѯ������̺�
	 * @return
	 */
	public List queryMaxProce_desc();
	/**
	 * ��ѯ������Ϣ
	 * @param materId ����ID
	 * @return
	 */
	public List queryMaterById(String materId);
	/**
	 * ���ϸ��������Ե�ַ
	 * @param materid ����ID
	 * @param tempURL �����Ե�ַ;
	 * @return
	 */
	public int uploadMaterTempPath(String materid, String tempURL);
	/**
	 * ȡ������
	 * @return
	 */
	public String getSEQToMater();
	/**
	 * ȡ������
	 * @return
	 */
	public String getSEQToAudit();
	/**
	 * ��������״̬
	 * @param result  ���
	 * @param proceid �������
	 * @param materid ����ID
	 * @return
	 */
	public int uploadMaterState(String result,String proceid, String materid);	
//	/**
//	 * 
//	 * @param materid
//	 * @return
//	 */
//	public int uploadMaterState(String materid);
	/**
	 * �ָ�����еĳ�ʼֵ
	 * @param materid ����ID
	 * @return
	 */
	public int updateAuditInfo(String materid);
	
	
	/**
	 * 
	 * @param mater_id ����ID
	 * @return
	 */
	public List checkAllPassAudit(String mater_id);
	
	
	public List queryMaterType();
	public List queryMaterCreator();
	public List queryMaterSize();
}
