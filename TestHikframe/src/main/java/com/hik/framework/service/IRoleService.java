package com.hik.framework.service;

import java.util.List;
import java.util.Map;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_ROLE;

import net.sf.json.JSONObject;

public interface IRoleService {

	public List<JSONObject> getRoleInfo();
	public int addOrUpdateRoleInfo(SYS_ROLE role);
	public int deleteRoleById(String[] trid);
	public List getPRoleRight(String trid,String ptrid);
	public int saveRoleRight(String _trid,String[] rrid);
	public int deleteRoleRightByTridRrid(String _trid,String[] rrid);
//	public List getRoleRightBytrid(String trid);
}
