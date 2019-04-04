package com.hik.framework.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hik.app.entity.SYS_RIGHT;

import net.sf.json.JSONObject;

public interface IMenuService {
	/**
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 检验用户名密码是否正确
	 */
	public List getIMenuInfo(String userid);
	
	/**
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 返回需要编辑的列
	 */
	public List getIEditMenuInfo(String userid);
	/**
	 * 更新或新增权限表数据
	 * @param right  需要更新或新增的数据
	 * @return 更新或新增行数据
	 */
	public int updataOrAddMenu(SYS_RIGHT right,JSONObject json);
	/**
	 * 更新密码是否成功
	 * @param userid 用户名
	 * @param newpword 密码
	 * @return
	 */
	public boolean updatepword(String userid,String newpword);
	/**
	 * 更新或新增权限表数据
	 * @param right  需要更新或新增的数据
	 * @return 更新或新增行数据
	 */
	/**
	 * 删除权限个
	 * @param rrid
	 */
	void deleteMenu(String rrid);
	/**
	 * 
	 */
	int addMenu(SYS_RIGHT right,String[] roles);
	/**
	 * 返回所有的角色信息
	 * @return
	 */
	public List<JSONObject> getRoleInfo();
	
	/**
	 * 某个餐单权限拥有者
	 */
	public List getRightRoles(String rrid);
	
	/**
	 * 
	 * @param description 描述名称
	 * @return 当前用户是否有权限
	 */

	public boolean getHaveRight(String description,HttpServletRequest request);
	
//	/**
//	 * 新增权限表数据
//	 * @param right  需要新增的数据
//	 * @return 新增行数据
//	 */
//	void addMenu(SYS_RIGHT right);
//	public int addRightRole(String[] roles);
	
}
