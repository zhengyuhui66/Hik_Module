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
	 * @param UserId当前用户ID
	 * @param start 开始第几条
	 * @param limit 每页几条
	 * @return 当前的物料信息
	 */
	public Page queryMateriel(String UserId,int start,int limit,String searchStr);
	/**
	 * 
	 * @param info 保存物料信息
	 * @param file 上传文件 
	 * @param targetFile 保存目标文件 
	 */
	public void saveMateriel(MATERIEL_INFO info,MultipartFile file,File targetFile,String userid) throws Exception;
	
	public int updateMateriel(MATERIEL_INFO info,MultipartFile file,File targetFile,String userid)throws Exception;
	
	/**
	 * 
	 * @param UserId 当前用户ID
	 * @param start 开始第几条
	 * @param limit 每页几条
	 * @param searchStr 搜索条件
	 * @return 当前需要审核的信息
	 */
	public Page queryMaterAudit(String UserId,int start,int limit,String searchStr);
	/**
	 * 
	 * @param auditId 审核ID
	 * @param audit_state 审核状态
	 * @param audit_result 审核结果
	 * @return 审核更新数量
	 */
//	public int auditUpdate(String auditId,String audit_state,String audit_result,String audit_desc,String mater_id,String audit_proce,String path,String userid) throws Exception;
	public int auditUpdate2(String auditId,String audit_state,String audit_result,String audit_desc,String mater_id,String audit_proce,String path,String userid) throws Exception;

	/**
	 * 
	 * @param materId 物料ID
	 * @return 根据物料信息查询审核信息
	 */
	public List<JSONObject> getAuditInfo(String materId);
	/**
	 * 
	 * @param UserId 用户ID
	 * @return 当前用户下，所拥有的审核权限
	 */
	public List<JSONObject> getAuditUser(String UserId);
	
	 /** 
	 * @param UserId 用户ID
	 * @return 当前用户下，所拥有的审核权限
	 */
	public List<JSONObject> getAuditChecked(String UserId);
	
	
	/**
	 * 得到当前用户下所有的子用户
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
	 * 批量删除物料
	 * @param params
	 * @return
	 */
	public int deleteMaterInfo(List<String> params,String path,List<String> files);
	/**
	 * 
	 * @param audit_proce
	 * @return 查看前一个审核流程是否通过
	 */
	public List checkiPassAudit(String audit_proce,String audit_id);
	
	/**
	 * 
	 * @param mater_id 物料ID
	 * @return
	 */
	public boolean checkAllPassAudit(String mater_id);
	
	
	
	public List queryMaterType();
	public List queryMaterCreator();
	public List queryMaterSize();
//	/**
//	 * 更新物料永久性地址
//	 * @param materid
//	 * @param tempURL
//	 * @return
//	 */
//	public int uploadMaterTempPath(String materid,String tempURL);
	
}
