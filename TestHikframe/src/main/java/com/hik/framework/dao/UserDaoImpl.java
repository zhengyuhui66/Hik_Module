package com.hik.framework.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.dao.BaseDao;
import com.hik.dao.BaseHIKDao;
import com.hik.framework.utils.Page;
import net.sf.json.JSONObject;


@Repository
public class UserDaoImpl extends BaseHIKDao implements IUserDao{
	private final Log log = LogFactory.getLog(UserDaoImpl.class);
	public Page getUser(int start, int limit,String searchStr,String userid) {
		// TODO Auto-generated method stub
		String log="查询用户:getUser->sql:";
//		String[] str=new String[]{"userid","pword","user_name","telphone","email","create_time","login_time","last_login_time","login_times","description","role","trid","puserid"};
//		String sql="SELECT S.*,decode(SR.NAME,null,'未分配角色',SR.NAME),sr.trid,SU.PUSERID "+
//				  "FROM SYS_USER S "+
//				  "LEFT JOIN SYS_USER_ROLE SU "+
//				    "ON (S.USERID = SU.USERID) "+
//				  "LEFT JOIN SYS_ROLE SR "+
//				    "ON (SR.TRID =SU.TRID) WHERE s.islogout='1' ";
//		
		String[] str=new String[]{"userid","pword","user_name","telphone","email","create_time","login_time","last_login_time","login_times","description","puserid"};
		String sql="SELECT userid,pword,user_name,telphone,email,create_time,login_time,last_login_time,login_times,description,puserid "+
				  "FROM SYS_USER "+
				  "WHERE islogout='1' ";
		Page page = null;
		List param = new ArrayList();
		if(StringUtils.isNotEmpty(searchStr)){
			sql+=" and (telphone  LIKE ? OR email LIKE ? OR description LIKE ? OR user_name LIKE ? )";
			sql+=" START WITH PUSERID=? CONNECT BY PRIOR USERID=PUSERID ";
			String temparam = "%"+searchStr+"%";
			param.add(temparam);
			param.add(temparam);
			param.add(temparam);
			param.add(temparam);
			param.add(userid);
			page=getPageObject(sql, start, limit, param,str,log);
		}else{
			sql+=" START WITH PUSERID=? CONNECT BY PRIOR USERID=PUSERID ";
			param.add(userid);
			page=getPageObject(sql,start,limit,param,str,log);
		}
		return page;
	}

	public int editUser(SYS_USER user) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add(user.getUSERID());
		list.add(user.getPWORD());
		list.add(user.getUSER_NAME());
		list.add(user.getTELPHONE());
		list.add(user.getEMAIL());
		list.add(user.getDESCRIPTION());
		list.add(user.getUSERID());
		String sql="update SYS_USER set userid=?,pword=?,user_name=?,telphone=?,email=?,description=? where userid=?";
		int result=executeUpdateSQL(sql, list,  "编辑用户:editUser");
		return result;
	}

	public int addUser(SYS_USER user,int puserid) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add(user.getUSERID());
		list.add(user.getPWORD());
		list.add(user.getUSER_NAME());
		list.add(user.getTELPHONE());
		list.add(user.getEMAIL());
		list.add(user.getDESCRIPTION());
		list.add(user.getISLOGOUT());
		list.add(puserid);
		String sql="insert into SYS_USER(create_time,userid,pword,user_name,telphone,email,description,islogout,puserid) values(to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),?,?,?,?,?,?,?,?)";
		int result=executeUpdateSQL(sql, list, "添加用户:");
		return result;
	}
	
	
	public List getUser(int userid){
		List param = new ArrayList();
		String sql="select usid from sys_user where userid=?"; 
		List list = getNoPageObject(sql, param, "得到用户信息");
		return list;
		
	}

	public int addRoleUser(int userid,String trid){
		List paramList = new ArrayList();
		paramList.add(userid);
		paramList.add(trid);
		String sql="insert into SYS_USER_ROLE(id,userid,trid) values(sys_user_role_req.nextVAL,?,?)";
//		String sql="MERGE INTO SYS_USER_ROLE p "+
//		 "USING (select ? AS USERID FROM dual) np "+ 
//		 "ON (p.USERID = np.USERID) "+ 
//		 "WHEN MATCHED THEN "+ 
//		   "UPDATE  SET TRID=? WHERE USERID=? "+ 
//		 "WHEN NOT MATCHED THEN "+ 
//		  "INSERT values(?,?,?) ";
		  int result=executeUpdateSQL(sql,paramList);
		return result;
	}

	public int deleteUser(int userid) {
		// TODO Auto-generated method stub
		List paramList = new ArrayList();
		paramList.add(userid);
//		String sql = "delete from SYS_USER where userid=?";
		String sql="update SYS_USER set islogout='0' where userid=?";
		int result=executeUpdateSQL(sql,paramList);
		return result;
	}
	
	public int deleteUser(int[] userid) {
		// TODO Auto-generated method stub
//		String sql = "delete from SYS_USER where userid in (";
		String sql="update SYS_USER set islogout='0' where userid in (";
		List params = new ArrayList();
		for(int i=0;i<userid.length;i++){
			params.add(userid[i]);
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		int temp=executeUpdateSQL(sql,params);
		return temp;
	}
	public List getRoleById(String trid) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add(trid);
		String[] str = new String[]{"name","create_time","desciption","trid","pid"};
		String sql="SELECT m.* FROM sys_role m START WITH pid=? CONNECT BY PRIOR m.trid=m.pid";
		return getNoPageObject(sql, list, str,"得到当前用户下的角色:getRoleById->sql");
	}


	public List getParentUser(int userId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(userId);
		String sql="SELECT S.USERID, SU.USER_NAME "+
				    "FROM SYS_USER_ROLE S  "+
				    "LEFT JOIN SYS_USER SU  "+
				    "ON (S.USERID = SU.USERID) "+ 
				    "START WITH s.USERID =?  "+
				    "CONNECT BY PRIOR S.PUSERID =S.USERID";
		String[] str = new String[]{"userid","user_name"};
		return getNoPageObject(sql, param, str, "查询用户的父类用户");
	}

	public List getPUserId(int userId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(userId+"");
		String sql="SELECT puserid FROM sys_user_role WHERE userid=?";
//		String[] str = new String[]{"userid"};
		return getNoPageObject(sql, param, "查询用户的父类用户");
	}

	public List getUserId() {
		// TODO Auto-generated method stub
		String sql="select usermanger_sqe.nextval from dual";
		return getNoPageObject(sql);
	}

	public List valiateAdd(String username) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(username);
		String sql="select COUNT(*) from SYS_USER t WHERE t.user_name=?";
		return getNoPageObject(sql, param);
	}

	public List valiateUpdate(String userid, String username) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(username);
		param.add(userid);
		param.add(username);
		String[] str = new String[]{"MCOUN","NCOUN"};
		String sql="WITH M AS "+
					 "(SELECT COUNT(*) AS MCOUN "+
					    "FROM SYS_USER T "+
					   "WHERE (T.USER_NAME =? AND USERID =?)), "+
					"N AS "+
					 "(SELECT COUNT(*) AS NCOUN FROM SYS_USER T WHERE T.USER_NAME =?) "+
					"SELECT M.MCOUN, N.NCOUN FROM M, N ";

		return getNoPageObject(sql,param,str);
	}

	@Override
	public List getRoleByUserId(String userId) {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		params.add(userId);
		String sql="select trid from SYS_USER_ROLE where userid=?";
		return getNoPageObject(sql, params);
	}

	@Override
	public int deleteUserRole(int userid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(userid);
		String sql="delete from SYS_USER_ROLE where userid=?";
		return executeUpdateSQL(sql, param);
	}


}
