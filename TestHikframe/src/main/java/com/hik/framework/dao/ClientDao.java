package com.hik.framework.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ClientDao{
	/**
	 * 分页查询客户端信息
	 * @return
	 */
	public Page queryClientInfo(int start,int limit);
	/**
	 * 保存新增的版本
	 * @return
	 */
	public int insertClient(String name,String type,String version,String versiondesc,String versionname,String url,String userid);
}
