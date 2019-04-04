package com.hik.framework.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_ROLE;
import com.hik.dao.BaseHIKDao;
import com.hik.framework.utils.CommonSence;

import net.sf.json.JSONObject;


@Repository
public class RoleDaoImpl extends BaseHIKDao implements IRoleDao{
	
	
	public List<JSONObject> getRoleInfo(){
		String[] jsonstr=new String[]{"name","create_time","description","trid","pid"};
		String sql="select * from SYS_ROLE t ";
		List<JSONObject> result = getNoPageObject(sql, jsonstr, "得到角色信息");
		return result;
	}

	public int deleteRoleRight(String[] trid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		Collections.addAll(param, trid);
		String sql=null;
		if(param.size()!=0&&param !=null){
			sql="delete from SYS_ROLE_RIGHT where trid in(";
			for(int i=0;i<param.size();i++){
				sql+="?,";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		int result=executeUpdateSQL(sql, param);
		return result;
	}

	public int deleteRole(String[] trid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		Collections.addAll(param, trid);
		String sql=null;
		if(param.size()!=0&&param !=null){
			sql="delete from SYS_ROLE where trid in(";
			for(int i=0;i<param.size();i++){
				sql+="?,";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		int result=executeUpdateSQL(sql, param);
		return result;
	}

	public List getPRoleRight(String trid){
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(trid);
		String[] str = new String[]{"text","description","leve","rrid","parent_tr_id"};
		String sql="WITH M AS "+
				 "(SELECT S.* "+
				    "FROM SYS_ROLE_RIGHT T "+
				    "LEFT JOIN SYS_RIGHT S "+
				      "ON (T.RRID = S.RRID) "+
				   "WHERE TRID = ?) "+
				"SELECT M.* "+
				  "FROM M "+
				 "START WITH M.PARENT_TR_ID = '"+CommonSence.HYPE_RIGHT+"' "+
				"CONNECT BY PRIOR M.RRID = M.PARENT_TR_ID ";
		List result = getNoPageObject(sql, param, str, "查询当前角色所拥有的权限"); 
		return result;
	}

	public int saveRoleRight(String _trid, String rrid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(_trid);
		param.add(rrid);
//		String[] str = new String[]{"text","description","leve","rrid","parent_tr_id"};
		String sql="insert into SYS_ROLE_RIGHT(trid,rrid) values(?,?)";
		return executeUpdateSQL(sql, param, "插入的数据SQL");
	}

	public List getRoleRightBytrid(String trid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(trid);
//		String[] jsonstr=new String[]{"rrid","trid"};
		String sql="select rrid from SYS_ROLE_RIGHT t where trid=?";
		List result = getNoPageObject(sql, param, "查询选中角色的所有权限");
		return result;
	}

	@Override
	public List getSeqSysrole() {
		// TODO Auto-generated method stub
		String sql ="select sys_role_seq.nextval from dual";
		return getNoPageObject(sql);
	}

	@Override
	public int addRoleInfo(SYS_ROLE sysrole) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(sysrole.getPid());
		param.add(sysrole.getName());
		param.add(sysrole.getDescription());
		String sql="insert into SYS_ROLE(trid,pid,name,create_time,description) values(sys_role_seq.nextval,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		return executeUpdateSQL(sql, param);
	}

	@Override
	public int UpdateRoleInfo(SYS_ROLE sysrole) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(sysrole.getPid());
		param.add(sysrole.getName());
		param.add(sysrole.getDescription());
		param.add(sysrole.getTrid());
		String sql="update SYS_ROLE set pid=?,name=?,description=?,create_time=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') where trid=?";
		return executeUpdateSQL(sql, param);
	}
	
	
}
