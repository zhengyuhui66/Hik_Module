package com.hik.framework.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_ROLE;

import net.sf.json.JSONObject;

public interface IRoleDao{
	/**
	 * 查询所有的角色名称
	 * @return
	 */
	public List<JSONObject> getRoleInfo();
	/**
	 * 
	 * @param trid 角色ID
	 * @return
	 */
	public int deleteRoleRight(String[] trid);
	
	public int deleteRole(String[] trid);
	
	
	public List getPRoleRight(String trid);
	
	int saveRoleRight(String _trid, String rrid);
	
	
	public List getRoleRightBytrid(String trid);
	
	public List getSeqSysrole();
	
	
	
	public int addRoleInfo(SYS_ROLE sysrole);
	
	public int UpdateRoleInfo(SYS_ROLE sysrole);
}
