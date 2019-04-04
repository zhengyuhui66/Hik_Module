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
	 * 营销客户人员 审核内容
	 * @param UserId
	 * @param audit_state
	 * @param start
	 * @param limit
	 * @return
	 */
//	public Page queryMaterAudit(String UserId,List audit_state,int start,int limit,String searchStr);
//	/**
//	 * 超级管理员 审核内容
//	 * @param state
//	 * @param start
//	 * @param limit
//	 * @return
//	 */
	public Page queryMaterhyperAudit(int start, int limit,String searchStr);
//	/**
//	 * 高级管理员 审核内容
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
	 * 得到当前用户下所有的子用户
	 * @return
	 */
	public List<JSONObject> getSubUser(String UserId);
	
	public List<JSONObject> getAuditChecked(String UserId);
	
	
//	public List<JSONObject> getAudit(String UserId,String audit);
	
	
	public int saveOrUpdateAudituser(AUDIT_USERINFO  au);
	/**
	 * 删除AUDIT_USERINFO 中的数据
	 * @param puserId
	 * @return
	 */
	public int deleteAuditUser(String puserId);
	
//	public int deleteAuditSubUser(String userid,String audit_id);
//	/**
//	 * 删除被授权用户
//	 * @param userId
//	 * @return
//	 */
	
//	public int deleteAuditsubUser(String userId);
//	/**
//	 * 查询用户级别
//	 * @param UserId
//	 * @return
//	 */
//	public int getUserLevel(String UserId);
	/**
	 * 删除参数
	 * @param params
	 * @return
	 */
	public int deleteMaterInfo(List<String> params);
	/**
	 * 删除单个
	 */
	public int deleteMaterInfo(String params);
	/**
	 * 
	 * @param userId
	 * @return 查询父类ID
	 */
	public List getPUserFromAudit_userinfo(String userId);
	/**
	 * 
	 * @param audit_proce
	 * @return 查看前一个流程是否通过
	 */
	public List checkiPassAudit(String audit_proce,String mater_id);
	/**
	 * 查询最大流程号
	 * @return
	 */
	public List queryMaxProce_desc();
	/**
	 * 查询物料信息
	 * @param materId 物料ID
	 * @return
	 */
	public List queryMaterById(String materId);
	/**
	 * 物料更新永久性地址
	 * @param materid 物料ID
	 * @param tempURL 永久性地址;
	 * @return
	 */
	public int uploadMaterTempPath(String materid, String tempURL);
	/**
	 * 取得索引
	 * @return
	 */
	public String getSEQToMater();
	/**
	 * 取得索引
	 * @return
	 */
	public String getSEQToAudit();
	/**
	 * 更新物料状态
	 * @param result  结果
	 * @param proceid 审核流程
	 * @param materid 物料ID
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
	 * 恢复审核中的初始值
	 * @param materid 物料ID
	 * @return
	 */
	public int updateAuditInfo(String materid);
	
	
	/**
	 * 
	 * @param mater_id 物料ID
	 * @return
	 */
	public List checkAllPassAudit(String mater_id);
	
	
	public List queryMaterType();
	public List queryMaterCreator();
	public List queryMaterSize();
}
