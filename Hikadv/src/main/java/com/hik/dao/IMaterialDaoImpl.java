package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IMaterialDaoImpl extends BaseHIKDao implements IMaterialDao{
	
	public Page queryMateriel(String UserId,int start,int limit,String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"mater_id","custom_id","username","mater_name","mater_size","mater_type","create_time","update_time","mater_path","mater_state","mater_width","mater_height","trid"};
		String sql="SELECT m.MATER_ID,"+
			       "m.CUSTOMER_ID,"+
			       "s.USER_NAME,"+
			       "m.MATER_NAME,"+
			       "m.MATER_SIZE,"+
			       "m.MATER_TYPE,"+
			       "m.CREATE_TIME,"+
			       "m.UPDATE_TIME,"+
			       "m.MATER_PATH,"+
			       "m.MATER_STATE, "+
			       "m.MATER_WIDTH,"+
			       "m.MATER_HEIGHT, "+
			       "sur.trid "+
			  "FROM MATERIEL_INFO m "+
			  "LEFT JOIN SYS_USER s "+
			    "ON (m.CUSTOMER_ID = s.USERID) "+
		     "left join SYS_USER_ROLE sur "+
		     "on(sur.userid=s.USERID)  ";
		if(UserId!=null){
			sql+=" where s.userid=? ";
			param.add(UserId);
		}
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			if(UserId!=null){
				sql+=" and ";
			}else{
				sql+=" where ";
			}
			  sql+="  ("+
				   "m.MATER_ID like ? or "+
			       "m.CUSTOMER_ID like ? or "+
			       "s.USER_NAME like ? or "+
			       "m.MATER_NAME like ? or "+
			       "m.MATER_SIZE like ? or "+
			       "m.MATER_TYPE like ? or "+
			       "m.CREATE_TIME like ? or "+
			       "m.UPDATE_TIME like ? or "+
			       "m.MATER_PATH like ? or "+
			       "m.MATER_STATE like ? or "+
			       "m.MATER_WIDTH like ? or "+
			       "m.MATER_HEIGHT like ?)";
			for(int i=0;i<12;i++){
				param.add(temparam);
			}
		}
			Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询物料信息");
		return page;
	}

	public Page queryMaterhyperAudit(int start, int limit,String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] str = new String[]{"audit_id","customer_id","mater_name","mater_size","mater_type","create_time","mater_path","mater_state","mater_width","mater_height","audit_state","audit_result","audit_stime","audit_name","audit_proce","mater_id"};

		String sql="SELECT M.AUDIT_ID,"+
			       "MA.CUSTOMER_ID,"+
			       "MA.MATER_NAME,"+
			       "MA.MATER_SIZE,"+
			       "MA.MATER_TYPE,"+
			       "MA.CREATE_TIME,"+
			       "MA.MATER_PATH,"+
			       "MA.MATER_STATE,"+
			       "MA.MATER_WIDTH,"+
			       "MA.MATER_HEIGHT,"+
			       "M.AUDIT_STATE,"+
			       "M.AUDIT_RESULT,"+
			       "M.AUDIT_STIME,"+
			       "P.NAME,"+
			       "M.AUDIT_PROCE,"+ 
			       "MA.MATER_ID "+
			  "FROM  AUDIT_INFO M "+
			  "LEFT JOIN MATERIEL_INFO MA "+
			    "ON (MA.MATER_ID = M.MATER_ID) "+
			  "LEFT JOIN PROCE_INFO P "+
			    "ON (P.PROCE_ID = M.AUDIT_PROCE) "+
			 "WHERE  MA.MATER_STATE != '已删除'";
	      if(StringUtils.isNotEmpty(searchStr)){
		    sql+= " and (M.AUDIT_ID LIKE ? or "+
		          "MA.CUSTOMER_ID LIKE ? or "+
		          "MA.MATER_NAME LIKE ? or "+
		          "MA.MATER_SIZE LIKE ? or "+
		          "MA.MATER_TYPE LIKE ? or "+
		          "MA.CREATE_TIME LIKE ? or "+
		          "MA.MATER_PATH LIKE ? or "+
		          "MA.MATER_STATE LIKE ? or "+
		          "M.AUDIT_STATE LIKE ? or "+
		          "M.AUDIT_RESULT LIKE ? or "+
			      "MA.MATER_WIDTH LIKE ? or "+
			      "MA.MATER_HEIGHT LIKE ?) ";
				String temparam = "%"+searchStr+"%";
				for(int k=0;k<12;k++){
					param.add(temparam);
			      }
	      }
	      sql+=" order by M.AUDIT_ID";
//	      Page page = getPageObject(sql, start, limit, str, "分页查询物料审核信息");
		Page page = getPageObject(sql, start, limit,param, str, "分页查询物料审核信息");
		return page;
	}

	
	public int auditUpdate(String auditId, String audit_state, String audit_result,String audit_desc,String userId,String stime) {
		// TODO Auto-generated method stub
		String sql="update AUDIT_INFO set audit_state=?,audit_result=?,audit_desc=?,audit_userid=?,audit_stime=? where audit_id=?";
		Query query =entityManager.createNativeQuery(sql);
		query.setParameter(1, audit_state);
		query.setParameter(2, audit_result);
		query.setParameter(3, audit_desc);
		query.setParameter(4, userId);
		query.setParameter(5, stime);
		query.setParameter(6, auditId);
		int result=query.executeUpdate();
		return result;
	}

	public List<JSONObject> getAuditInfo(String materId) {
		// TODO Auto-generated method stub   
		List param = new ArrayList();
		param.add(materId);
		String[] str=new String[]{"audit_proce","name","audit_state","audit_result","audit_etime","audit_desc","audit_stime","audit_user","audit_telphone"};		
		String sql="SELECT T.AUDIT_PROCE, "+
			       "P.NAME, "+
			       "T.AUDIT_STATE, "+
			       "T.AUDIT_RESULT, "+
			       "T.AUDIT_ETIME, "+
			       "T.AUDIT_DESC, "+
			       "T.AUDIT_STIME, "+
			       "S.USER_NAME, "+
			       "S.TELPHONE "+
			  "FROM AUDIT_INFO T "+
			  "LEFT JOIN PROCE_INFO P "+
			    "ON (T.AUDIT_PROCE = P.PROCE_ID) "+
			   "LEFT JOIN SYS_USER S ON(T.AUDIT_USERID=S.USERID) "+
			 "WHERE MATER_ID = ? "+
			 "ORDER BY AUDIT_PROCE ";
	 
		List<JSONObject> result=getNoPageObject(sql, param, str, "查询物料的审核信息");
		return result;
	}

	public int saveAuditInfo(AUDIT_INFO audit_INFO) {
		// TODO Auto-generated method stub
		
		return 0;
	}
	
	public List<JSONObject> getProce_desc(){
		String[] str = new String[]{"proce_id","name","ab_name","proce_time","proce_desc","proce_role"};
		String sql="select * from PROCE_INFO";
		List<JSONObject> result = getNoPageObject(sql, str, "查询所有的审核流程的信息");
		return result;
	}

	public List<JSONObject> getAuditUser(String UserId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(UserId);
		String[] str=new String[]{"audit_id","audit_name"};
		String sql="select proce_id,name from proce_info";
		List<JSONObject> result=getNoPageObject(sql, str, "查询物料的审核信息");
		return result;
	}

	public List<JSONObject> getSubUser(String UserId){
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(UserId);
		String[] str=new String[]{"userId","userName"};
		String sql="SELECT s.userid,s.user_name FROM sys_user s START WITH s.userid=? CONNECT BY PRIOR s.userid =s.puserid";
//	    String sql="SELECT s.userid,su.user_name FROM SYS_USER_ROLE s LEFT JOIN sys_user su ON (s.userid=su.userid)START WITH s.userid=? CONNECT BY PRIOR s.userid=s.puserid";
	   List<JSONObject> result=getNoPageObject(sql, param, str, "查询当前用户的子用户");
		return result;
	}

	public int saveOrUpdateAudituser(AUDIT_USERINFO au) {
		List list = new ArrayList();
		list.add(au.getPUSERID());
		list.add(au.getAUDIT_ID());
		list.add(au.getUSERID());
		String sql="insert into AUDIT_USERINFO values(?,?,?)";
		return executeUpdateSQL(sql, list, "插入AUDIT_USERINFO新的数据");

	}

	public int deleteAuditUser(String puserId) {
		// TODO Auto-generated method stub
		String sql="delete from AUDIT_USERINFO where puserId=?";
		Query query=entityManager.createNativeQuery(sql);
		query.setParameter(1,puserId);
		int result=query.executeUpdate();
		return result;
	}


	public List<JSONObject> getAuditChecked(String UserId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(UserId);
		String[] str=new String[]{"puserId","auditId","userId"};
		String sql="select * from AUDIT_USERINFO where puserId=?";
		List<JSONObject> result=getNoPageObject(sql, param, str, "查询选中的信息");
		return result;
	}

	public int deleteMaterInfo(List<String> params) {
		String sql="delete from MATERIEL_INFO  where mater_id in ( ";
//		// TODO Auto-generated method stub
//		String sql="update MATERIEL_INFO set mater_state='已删除' where mater_id in (";
		for(int i=0;i<params.size();i++){
			sql+="?,";
		}
		sql=sql.substring(0,sql.length()-1);
		sql+=")";
		return executeUpdateSQL(sql, params, "删除物料");
	}
	public int deleteMaterInfo(String params) {
		// TODO Auto-generated method stub
		List _params = new ArrayList();
		_params.add(params);
		String sql="delete from MATERIEL_INFO where mater_id=?";
		return executeUpdateSQL(sql, _params, "删除物料");
	}
	
	public List getPUserFromAudit_userinfo(String userId){
		List params = new ArrayList();
		params.add(userId);
		String[] str = new String[]{"puserid","audit_id"};
		String sql="select puserid,audit_id from Audit_userinfo where userId=?";
		return getNoPageObject(sql, params,str,"查询审核权限的父类ID");
	}

	public List checkiPassAudit(String audit_proce,String mater_id) {
		// TODO Auto-generated method stub
		int audit_proce_ = Integer.parseInt(audit_proce);
		audit_proce_--;
		List param = new ArrayList();
		param.add(audit_proce_);
		param.add(mater_id);
		String[] str = new String[]{"audit_state","audit_result","name"};
		String sql="select a.audit_state,a.audit_result,p.name from  AUDIT_INFO a left join PROCE_INFO p on(a.audit_proce=p.proce_id) where a.audit_proce=? and a.mater_id=?";
		return getNoPageObject(sql, param,str);
	}

	public List queryMaxProce_desc() {
		// TODO Auto-generated method stub
		String sql="select MAX(proce_id) from PROCE_INFO t";
		return getNoPageObject(sql);
	}
	
	public List queryMaterById(String materId){
		List param = new ArrayList();
		param.add(materId);
		String sql="select mater_name from MATERIEL_INFO where mater_id=?";
		return getNoPageObject(sql, param);
	}

	public int uploadMaterTempPath(String materid, String tempURL) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(tempURL);
		param.add(materid);
		String sql="update MATERIEL_INFO set MATER_PATH=? , mater_state='"+CommonSence.AUDITED_ALL_PASS+"' where mater_id=?";
		
		return executeUpdateSQL(sql, param, "审核通过,更新为永久性地址");
	}

	public String getSEQToMater() {
		// TODO Auto-generated method stub
		String sql="select MATERIELINFO_SEQ.nextval from dual";
		List result = getNoPageObject(sql); 
		return result.get(0).toString();
	}

	public String getSEQToAudit() {
		// TODO Auto-generated method stub
		String sql="select AUDITINFO_SEQ.nextval from dual";
		List result =getNoPageObject(sql);
		return result.get(0).toString();
	}

	public int uploadMaterState(String result,String proceid, String materid) {
		// TODO Auto-generated method stub
		List param =new ArrayList();
		param.add(result);
		param.add(proceid);
		param.add(materid);
		String sql="update MATERIEL_INFO set mater_state=(SELECT NAME||? FROM  PROCE_INFO WHERE proce_id=?) where mater_id=?";
		return executeUpdateSQL(sql, param);
	}
	
//	public int uploadMaterState(String materid){
//		List param =new ArrayList();
//		param.add(materid);
//		String sql="update MATERIEL_INFO set mater_state='"+CommonSence.AUDITED_ALL_PASS+"' where mater_id=?";
//		return executeUpdateSQL(sql, param);
//	}

	public int updateAuditInfo(String materid) {
		// TODO Auto-generated method stub
		List param =new ArrayList();
		param.add(materid);
		String sql="update AUDIT_INFO set audit_state='未审核',audit_desc='"+CommonSence.UNAUDIT+"',audit_result='"+CommonSence.UNAUDIT+"',audit_userid is null where mater_id=?";
		return executeUpdateSQL(sql, param);
	}

	public List checkAllPassAudit(String mater_id) {
		// TODO Auto-generated method stub
		List param =new ArrayList();
		param.add(mater_id);
		String[] str = new String[]{"audit_state","audit_result"};
		String sql="select audit_state,audit_result from AUDIT_INFO t  WHERE mater_id=?";
		return getNoPageObject(sql, param, str);
	}

	@Override
	public List queryMaterType() {
		// TODO Auto-generated method stub
		String sql="select DISTINCT mater_type as id,mater_type as name FROM MATERIEL_INFO   WHERE mater_state='审核已全部通过'";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,str);
	}

	@Override
	public List queryMaterCreator() {
		// TODO Auto-generated method stub
		String sql="select DISTINCT t.customer_id as id,decode(s.user_name,NULL,'未命名',s.user_name) AS NAME from MATERIEL_INFO t LEFT JOIN Sys_User s ON(t.customer_id=s.userid)   WHERE mater_state='审核已全部通过'";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,str);
	}

	@Override
	public List queryMaterSize() {
		// TODO Auto-generated method stub
		String sql="select DISTINCT MATER_WIDTH||'*'||MATER_HEIGHT as id,MATER_WIDTH||'*'||MATER_HEIGHT as name FROM MATERIEL_INFO  WHERE mater_state='审核已全部通过'";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,str); 
	}

	
}
