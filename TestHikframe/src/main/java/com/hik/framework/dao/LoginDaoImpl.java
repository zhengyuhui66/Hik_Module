package com.hik.framework.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hik.dao.BaseDao;
import com.hik.dao.BaseHIKDao;

import net.sf.json.JSONObject;


@Repository
public class LoginDaoImpl extends BaseHIKDao implements ILoginDao{
	public List getIfLoginInfo(String userName, String password,Log log) {
		// TODO Auto-generated method stub
		List tlist = new ArrayList();
		String[] str =new String[]{"userId","pword","userName","telphone","email","createTime","loginTime","lastLoginTime","loginTimes","trid"};
		tlist.add(userName);
		tlist.add(password);
		
//		
//		String sql="SELECT S.USERID,"+
//			       "S.PWORD,"+
//			       "S.USER_NAME,"+
//			       "S.TELPHONE,"+
//			       "S.EMAIL,"+
//			       "S.CREATE_TIME,"+
//			       "S.LOGIN_TIME,"+
//			       "S.LAST_LOGIN_TIME,"+
//			       "S.LOGIN_TIMES,"+
//			       "SR.TRID "+
//			  "FROM SYS_USER S "+
//			  "LEFT JOIN SYS_USER_ROLE SU "+
//			    "ON (S.USERID = SU.USERID) "+
//			  "LEFT JOIN SYS_ROLE SR "+
//			    "ON (SR.TRID = SU.TRID) "+
//			 "WHERE S.USERID = ? "+
//			   "AND PWORD =? ";

	   
		String sql="SELECT S.USERID,S.PWORD,S.USER_NAME,S.TELPHONE,S.EMAIL,S.CREATE_TIME,S.LOGIN_TIME,S.LAST_LOGIN_TIME,S.LOGIN_TIMES,sr.trid "+
		  "FROM SYS_USER S "+
		  "LEFT JOIN SYS_USER_ROLE SU "+
		    "ON (S.USERID = SU.USERID) "+
		  "LEFT JOIN SYS_ROLE SR "+
		    "ON (SR.TRID =SU.TRID) where s.user_name=? and pword=?";
		log.info("======="+sql);
		System.out.println(">>>>>>>>>>>"+sql);
		List<JSONObject> result = getNoPageObject(sql, tlist, str, "得到登陆信息:addUser->sql");
		log.info("::::::::"+result.toString());
		System.out.println("<<<<<<<<<<<<<<"+result);
		return result;
	}

	public boolean getUserInfo(String user_name) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(user_name);
		String sql="select * from SYS_USER where user_name=?";
		List list = getNoPageObject(sql, param, "判断用户的SQL是");
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}

}
