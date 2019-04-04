package com.hik.framework.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.dao.BaseHIKDao;
import com.hik.framework.utils.CommonSence;

import net.sf.json.JSONObject;


@Repository
public class MenuDaoImpl extends BaseHIKDao implements IMenuDao{
	public List getMenuInfo(String userId) {
		List params = new ArrayList();
		params.add(userId);
		String[] jsonstr = new String[]{"text","description","leve","rrid","parent_id"};
        String sql="WITH T AS "+
        "(SELECT SUR.TRID FROM SYS_USER_ROLE SUR WHERE SUR.USERID = ?) "+
       "SELECT SR.* "+
         "FROM SYS_RIGHT SR "+
         "LEFT JOIN SYS_ROLE_RIGHT SRR "+
           "ON (SR.RRID = SRR.RRID), T "+
        "WHERE SRR.TRID = T.TRID AND SR.LEVE!=3"
        + " START WITH sr.PARENT_TR_ID = '"+CommonSence.HYPE_RIGHT+"'  "+
       "CONNECT BY PRIOR sr.RRID = sr.PARENT_TR_ID";
        
		List result=getNoPageObject(sql, params, jsonstr, "得到当前用户餐单信息");
		return result;
	}
	public List getEditMenuInfo(String userName) {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		params.add(userName);
		String[] jsonstr = new String[]{"text","description","leve","rrid","parent_id"};
		 String sql="WITH T AS "+
			        "(SELECT SUR.TRID FROM SYS_USER_ROLE SUR WHERE SUR.USERID = ?) "+
			       "SELECT SR.* "+
			         "FROM SYS_RIGHT SR "+
			         "LEFT JOIN SYS_ROLE_RIGHT SRR "+
			           "ON (SR.RRID = SRR.RRID), T "+
			        "WHERE SRR.TRID = T.TRID START WITH sr.PARENT_TR_ID = '"+CommonSence.HYPE_RIGHT+"'  "+
			       "CONNECT BY PRIOR sr.RRID = sr.PARENT_TR_ID";

		List result=getNoPageObject(sql, params, jsonstr, "编辑菜单信息");
		return result;
	}
	public int addMenu(SYS_RIGHT right) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add(right.getRRID());
		list.add(right.getPARENT_TR_ID());
		list.add(right.getRIGHT_NAME());
		list.add(right.getDESCRIPTION());
//		list.add(right.getROLE());
		list.add(right.getLEVE());
		String sql="INSERT INTO sys_right(rrid,parent_tr_id,right_name,description,leve) VALUES(?,?,?,?,?)";
		int t=executeUpdateSQL(sql, list,"添加菜单:addMenu->sql");
		return t;
	}
	
	public List<JSONObject> getRoleInfo(){
		String[] jsonstr=new String[]{"name","create_time","description","trid","pid"};
		String sql="select * from SYS_ROLE t ";
		List<JSONObject> result = getNoPageObject(sql, jsonstr, "得到角色信息");
		return result;
	}
	public List<JSONObject> getRoleInfo(String[] str) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		Collections.addAll(list, str);
//		String[] jsonstr=new String[]{"rolename"};
		String sql="select t.NAME from SYS_ROLE t";
		if(list.size()>=0){
			sql+=" where trid in(";
			for(int i=0;i<list.size();i++){
				sql+=list.get(i)+",";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		return getNoPageObject(sql,"查询的结果是");
	}

	public int addRightRole(String rrid,String roles) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(rrid);
		param.add(roles);
		String sql="insert into SYS_ROLE_RIGHT(rrid,trid) values(?,?)";
		int result=executeUpdateSQL(sql,param);
		return result;
	}
	
		public List getRightRoles(String rrid) {
			// TODO Auto-generated method stub
			List list = new ArrayList();
			list.add(rrid);
			String sql="SELECT m.trid FROM SYS_ROLE_RIGHT m where m.rrid=?";
			return getNoPageObject(sql, list,"权限拥有者有");
		}
		
		public int saveAndFlush(SYS_RIGHT right) {
			// TODO Auto-generated method stub
			List param = new ArrayList();
			param.add(right.getPARENT_TR_ID());
			param.add(right.getRIGHT_NAME());			
			param.add(right.getDESCRIPTION());
			param.add(right.getLEVE());
			param.add(right.getRRID());
			String sql="update sys_right set parent_tr_id=?,right_name=?,description=?,leve=? where rrid=?";
			int result = executeUpdateSQL(sql, param, "更新操作");
			return result;
		}
		
		public int saveAndFlushRightRole(String rrid, String trid) {
			// TODO Auto-generated method stub
			List param = new ArrayList();
			param.add(rrid);
			param.add(trid);
			param.add(rrid);
			param.add(trid);
			String sql="MERGE INTO SYS_ROLE_RIGHT p "+
		     "USING (select ? AS rrid,? AS trid FROM dual) np  "+ 
		     "ON (p.rrid = np.rrid AND p.trid = np.trid)   "+
		     "WHEN NOT MATCHED THEN "+
		     "INSERT VALUES(?,?) ";
			int result = executeUpdateSQL(sql, param, "如果没有数据就新增，如果有就不操作");
			return result;
		}
		
		public int deleteRightRole(String rrid, String trid){
			List param = new ArrayList();
			param.add(rrid);
			param.add(trid);
			String sql="delete from SYS_ROLE_RIGHT where rrid=? and trid=?";
			int result = executeUpdateSQL(sql, param, "如果数据就删除");
			return result;
		}
		public boolean getHaveRight(String description,String UserId) {
			// TODO Auto-generated method stub
			List param = new ArrayList();
			param.add(description);
			param.add(UserId);
			String sql = "SELECT T.* "+
						  "FROM SYS_RIGHT T "+
						  "LEFT JOIN SYS_ROLE_RIGHT S "+
						    "ON (T.RRID = S.RRID) "+
						  "LEFT JOIN SYS_USER_ROLE SU "+
						    "ON (SU.TRID = S.TRID) "+
						  "LEFT JOIN SYS_USER SS "+
						    "ON (SS.USERID = SU.USERID) "+
						 "WHERE T.DESCRIPTION = ? "+
						   "AND SS.USERID =?";
			List result = getNoPageObject(sql, param);
			boolean res=false;
			if(result!=null&&result.size()>0){
				res=true;
			}
			return res;
		}
		public int updatepword(String userid, String newpword) {
			// TODO Auto-generated method stub
			List param = new ArrayList();
			param.add(newpword);
			param.add(userid);
			String sql="update SYS_USER set pword=? where userid=?";
			
			return executeUpdateSQL(sql, param, "更新密码");
		}
		@Override
		public List getSeq_sysrightseq() {
			// TODO Auto-generated method stub
			String sql="select sys_right_seq.nextval from dual";
			return getNoPageObject(sql);
		}
}
