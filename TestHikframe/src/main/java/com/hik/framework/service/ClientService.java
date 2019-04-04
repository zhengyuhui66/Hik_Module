package com.hik.framework.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ClientService {
	/**
	 * 分页查询客户端信息
	 * @return
	 */
	public Page queryClientInfo(int start,int limit);
	/**
	 * 保存新增的版本
	 * @return
	 */
	public int saveClient(String path,MultipartFile file,HttpServletRequest request,String version,String versionName,String versiondesc,String userid);
	
}
