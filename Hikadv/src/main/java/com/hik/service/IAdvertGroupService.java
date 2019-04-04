package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IAdvertGroupService {

	/**
	 * 分页得到所有的广告集
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getAdvertgroup(int start,int limit,String searchStr);
	
/**3
 * 新增或者保存
 * @param advgroup
 * @return
 */
	public int saveOrupdateAdvertgroup(ADVGROUP advgroup);
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public int deleteAdvertgroup(String[] ids);
	/**
	 * 查询所在的id是否存在于
	 * @param ids
	 * @return
	 */
	public List queryGroupPutproduct(String[] ids);
	

}
