package com.hik.framework.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IUserDao{
	/**
	 * 
	 * @param start 查询开始页
	 * @param limit 查询每页条数
	 * @return
	 */
		public Page getUser(int start,int limit,String searchStr,String usid);
		
	/**
	 * 
	 * @param user 需要编辑的数据
	 */
		public int editUser(SYS_USER user);
	/**
	 * 
	 * @param user 添加的数据
	 */
		int addUser(SYS_USER user,int puserid);
	/**
	 * 
	 * @param usid 删除的数据索引
	 */
		int deleteUser(int userid);
	/**
	 * 
	 * @param usid 删除的多个数据索引
	 */
		int deleteUser(int[] userid);
	/**
	 * 
	 * @param usid 索引
	 * @return获取单个数据
	 */
		List getRoleById(String trid);
		List getRoleByUserId(String userId);
		/**
		 * 
		 * @param usid 用户ID
		 * @param trid 用户角色
		 * @param pusid 用户父类角色
		 * @return 保存用户角色信息
		 */
		public int addRoleUser(int userid,String trid);
		
		public List getUser(int userid);
		/**
		 * 查询所有的父类用户
		 * @param userId
		 * @return
		 */
		public List getParentUser(int userId);
		/**
		 * 查询当前用户的父类用户ID
		 * @param userId
		 * @return
		 */
		public List getPUserId(int userId);
		
		public List getUserId();
		
		/**
		 * 
		 * @param username
		 * @return
		 */
		public List valiateAdd(String username);

		/**
		 * 
		 * @param userid
		 * @param username
		 * @return
		 */
		public List valiateUpdate(String userid, String username);
		
		
		public int deleteUserRole(int userid);

//		/**
//		 * 
//		 * @param userid
//		 * @return
//		 */
//		public List getUserInfo(int userid);
//		/**
//		 * 
//		 * @param pusid
//		 * @param trid
//		 * @return
//		 */
//		public int addUserRole(int usid,int pusid, int trid);
}
