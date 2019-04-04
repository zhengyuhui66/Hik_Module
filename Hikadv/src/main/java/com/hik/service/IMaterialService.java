package com.hik.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IMaterialService {
	/**
	 * 
	 * @param UserId��ǰ�û�ID
	 * @param start ��ʼ�ڼ���
	 * @param limit ÿҳ����
	 * @return ��ǰ��������Ϣ
	 */
	public Page queryMateriel(String UserId,int start,int limit,String searchStr);
	/**
	 * 
	 * @param info ����������Ϣ
	 * @param file �ϴ��ļ� 
	 * @param targetFile ����Ŀ���ļ� 
	 */
	public void saveMateriel(MATERIEL_INFO info,MultipartFile file,File targetFile,String userid) throws Exception;
	
	public int updateMateriel(MATERIEL_INFO info,MultipartFile file,File targetFile,String userid)throws Exception;
	
	/**
	 * 
	 * @param UserId ��ǰ�û�ID
	 * @param start ��ʼ�ڼ���
	 * @param limit ÿҳ����
	 * @param searchStr ��������
	 * @return ��ǰ��Ҫ��˵���Ϣ
	 */
	public Page queryMaterAudit(String UserId,int start,int limit,String searchStr);
	/**
	 * 
	 * @param auditId ���ID
	 * @param audit_state ���״̬
	 * @param audit_result ��˽��
	 * @return ��˸�������
	 */
//	public int auditUpdate(String auditId,String audit_state,String audit_result,String audit_desc,String mater_id,String audit_proce,String path,String userid) throws Exception;
	public int auditUpdate2(String auditId,String audit_state,String audit_result,String audit_desc,String mater_id,String audit_proce,String path,String userid) throws Exception;

	/**
	 * 
	 * @param materId ����ID
	 * @return ����������Ϣ��ѯ�����Ϣ
	 */
	public List<JSONObject> getAuditInfo(String materId);
	/**
	 * 
	 * @param UserId �û�ID
	 * @return ��ǰ�û��£���ӵ�е����Ȩ��
	 */
	public List<JSONObject> getAuditUser(String UserId);
	
	 /** 
	 * @param UserId �û�ID
	 * @return ��ǰ�û��£���ӵ�е����Ȩ��
	 */
	public List<JSONObject> getAuditChecked(String UserId);
	
	
	/**
	 * �õ���ǰ�û������е����û�
	 * @return
	 */
	public List<JSONObject> getSubUser(String UserId);
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public int saveOrUpdateAudituser(List<AUDIT_USERINFO> list,String userid);
	
	/**
	 * ����ɾ������
	 * @param params
	 * @return
	 */
	public int deleteMaterInfo(List<String> params,String path,List<String> files);
	/**
	 * 
	 * @param audit_proce
	 * @return �鿴ǰһ����������Ƿ�ͨ��
	 */
	public List checkiPassAudit(String audit_proce,String audit_id);
	
	/**
	 * 
	 * @param mater_id ����ID
	 * @return
	 */
	public boolean checkAllPassAudit(String mater_id);
	
	
	
	public List queryMaterType();
	public List queryMaterCreator();
	public List queryMaterSize();
//	/**
//	 * �������������Ե�ַ
//	 * @param materid
//	 * @param tempURL
//	 * @return
//	 */
//	public int uploadMaterTempPath(String materid,String tempURL);
	
}
