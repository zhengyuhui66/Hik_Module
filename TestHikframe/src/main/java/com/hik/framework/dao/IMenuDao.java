package com.hik.framework.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hik.app.entity.SYS_RIGHT;

import net.sf.json.JSONObject;

public interface IMenuDao{
	/**
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 检验用户名密码是否正确
	 */
	public List getMenuInfo(String userName);
	
	/**
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 返回需编辑的列
	 */
	public List getEditMenuInfo(String userName);
	/**
	 * 更新权限表数据
	 * @param right  需要更新的数据
	 * @return 更新行数据
	 */
	
	public int addMenu(SYS_RIGHT right);
//	boolean updataMenu(SYS_RIGHT right);
	/**
	 * 查询所有的角色名称
	 * @return
	 */
	public List<JSONObject> getRoleInfo();
	/**
	 * 
	 * @param str
	 * @return
	 */
	public List<JSONObject> getRoleInfo(String[] str);
	
	/**
	 * 
	 * @param right 权限数据
	 * @param entityManager 引擎
	 * @return 更新权限表
	 */
	public int saveAndFlush(SYS_RIGHT right);
	/**
	 * 
	 * @param rrid 权限ID
	 * @param trid 角色ID
	 * @param entityManager 引擎
	 * @return 更新角色权限表
	 */
	public int saveAndFlushRightRole(String rrid,String trid);
	
	/**
	 * 
	 * @param roles所需要批量新增的方法
	 * @return
	 */
	public int addRightRole(String rrid,String role);
	/**
	 * 
	 * @param rrid 权限ID
	 * @param trid 角色ID
	 * @param entityManager 引擎
	 * @return 删除角色权限表
	 */
	public int deleteRightRole(String rrid, String trid);
	/**
	 * 某个餐单的权限拥有者
	 * @param rrid
	 * @return
	 */
	public List getRightRoles(String rrid);
	/**
	 * 
	 * @param description 描述名称
	 * @return 当前用户是否有权限
	 */

	public boolean getHaveRight(String description,String UserId);
	
	
	public int updatepword(String userid,String newpword);
	
	public List getSeq_sysrightseq();
	
}
