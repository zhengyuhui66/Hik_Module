package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IRouteMangerService {
/**
 * 分页查询线路
 * @param start 开始条数
 * @param limit 每页几条
 * @param searchStr 筛选条件
 * @return
 */
	Page queryRouteManger(int start,int limit,String searchStr);
	
	
	List<JSONObject> queryDeviceList(String id);
	/**
	 * 新增或更新线路
	 * @param putproduct
	 * @return
	 */
	int addorUpdateRouteManger(ROUTE route);
	/**
	 * 删除线路
	 * @param ids
	 * @return
	 */
	String deleteRouteManger(String[] ids);
	
	
	String setting(String[] ids,String speed,String timeout);


}
