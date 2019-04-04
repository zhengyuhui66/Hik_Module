package com.hik.framework.service;

import java.util.List;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IUserService {
	/**
	 * 
	 * @param start
	 *            查询开始页
	 * @param limit
	 *            查询每页条数
	 * @return
	 */
	public Page getUser(int start, int limit, String searchStr, String usid);

	/**
	 * 
	 * @param user
	 *            需要编辑的数据
	 */
	public int editUser(SYS_USER user);

	/**
	 * 
	 * @param user
	 *            添加的数据
	 */
	int addUser(SYS_USER user, int puserid) throws Exception;

	/**
	 * 
	 * @param usid
	 *            删除的数据索引
	 */
	int deleteUser(int userid);

	/**
	 * 
	 * @param usid
	 *            删除的数据索引
	 */
	int deleteUser(int[] userid);

	/**
	 * @param _trid
	 * @param usid
	 *            索引
	 * @return获取单个数据
	 */
	List getRoleById(String _trid,String userid);

	/**
	 *
	 * @param usid
	 *            用户ID
	 * @param pusid
	 *            父类用户ID
	 * @param trid
	 *            角色ID
	 * @return
	 */
	public int addUserRole(int userid, String[] trid);

	/**
	 * 查询新插入的数据
	 * 
	 * @param userid
	 * @return
	 */
	public List getUser(int userid);

	/**
	 * 查询所有的父类用户
	 * 
	 * @param userId
	 * @return
	 */
	public List getParentUser(int userId);

	/**
	 * 查询当前用户的父类ID
	 * 
	 * @param userId
	 * @return
	 */
	public List getPUserId(int userId);

	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean valiateAdd(String username);

	/**
	 * 
	 * @param userid
	 * @param username
	 * @return
	 */
	public boolean valiateUpdate(String userid, String username);
}
