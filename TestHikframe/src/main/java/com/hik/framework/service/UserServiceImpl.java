package com.hik.framework.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.dao.BaseService;
import com.hik.dao.SYS_RIGHT_jpa;
import com.hik.dao.SYS_USER_jpa;
import com.hik.framework.dao.ILoginDao;
import com.hik.framework.dao.IMenuDao;
import com.hik.framework.dao.IUserDao;
import com.hik.framework.dao.LoginDaoImpl;
import com.hik.framework.utils.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false,isolation=Isolation.DEFAULT)
public class UserServiceImpl extends BaseService implements IUserService{
    private final Log log = LogFactory.getLog(UserServiceImpl.class);
	@Autowired
	private IUserDao iUserDao;
//	@Autowired
//	private SYS_USER_jpa sys_user_jpa;
	
	public Page getUser(int start, int limit,String searchStr,String usid) {
		// TODO Auto-generated method stub
		return iUserDao.getUser(start, limit,searchStr,usid);
	}

	public int editUser(SYS_USER user) {
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		
		int result=iUserDao.editUser(user);
//		commitTransaction(entityManager);
		return result;
	}

	public int addUser(SYS_USER user,int puerid) throws Exception{
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		List userList = iUserDao.getUserId();
		int userId=0;
		if(userList!=null&&userList.size()>0){
			String userIds=userList.get(0).toString();
			userId=Integer.parseInt(userIds);
		}
		user.setUSERID(userId);
		int usNum=iUserDao.addUser(user,puerid);
		int userRole = iUserDao.addRoleUser(user.getUSERID(), "0");
//		commitTransaction(entityManager);
		return usNum; 
	}

	public int deleteUser(int userid) {
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		int result=iUserDao.deleteUser(userid);
//		commitTransaction(entityManager);
		return result;
	}

	public List getRoleById(String trid,String userid) {
		// TODO Auto-generated method stub
		List<JSONObject> list = iUserDao.getRoleById(trid);
		List list2 = iUserDao.getRoleByUserId(userid);
		for(int i=0;i<list.size();i++){
			JSONObject json = list.get(i);
			String _trid=json.getString("trid");
			for(int j=0;j<list2.size();j++){
				if(list2.get(j).equals(_trid)){
					json.accumulate("checked", true);
					list.remove(i);
					list.add(i, json);
					break;
				}
			}
		}
		return list;
	}

	public int deleteUser(int[] userid) {
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		int result=iUserDao.deleteUser(userid);
//		commitTransaction(entityManager);
		return result;
	}

	public int addUserRole(int userid, String[] trids) {
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		iUserDao.deleteUserRole(userid);
		int usNum=0;
		for(int i=0;i<trids.length;i++){
			usNum+=iUserDao.addRoleUser(userid,trids[i]);			
		}
//		commitTransaction(entityManager);
		return usNum;
	}

	public List getUser(int userid) {
		// TODO Auto-generated method stub
		
		return iUserDao.getUser(userid);
	}

	public List getParentUser(int userId) {
		// TODO Auto-generated method stub
		return iUserDao.getParentUser(userId);
	}

	public List getPUserId(int userId) {
		// TODO Auto-generated method stub
		return iUserDao.getPUserId(userId);
	}

	public boolean valiateAdd(String username) {
		// TODO Auto-generated method stub
		List list = iUserDao.valiateAdd(username);
		boolean flag=false;
		if(list!=null&&list.size()>0){
			String i=list.get(0).toString();
			int result=Integer.parseInt(i);
			if(result>0){
				flag=false;
			}else{
				flag=true;
			}
		}
		return flag;
	}

	public boolean valiateUpdate(String userid, String username) {
		// TODO Auto-generated method stub
		List<JSONObject> list=iUserDao.valiateUpdate(userid, username);
		boolean flag=false;
		if(list!=null&&list.size()>0){
			
			JSONObject i=list.get(0);
			String mcoun=i.getString("MCOUN");
			String ncoun=i.getString("NCOUN");
			int mc=Integer.parseInt(mcoun);
			int nc=Integer.parseInt(ncoun);
			if(mc>0){
				flag=true;
			}else if(nc>0){
				flag=false;
			}else if(nc==0){
				flag=true;
			}
		}
		return flag;
	}
}

