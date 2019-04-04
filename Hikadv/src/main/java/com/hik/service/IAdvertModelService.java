package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.ADVERTMODELMANGER;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IAdvertModelService {
	/**
	 * 查询所有的广告模版
	 * @return
	 */
	public Page queryadvertModel(int start,int limit,String searchStr);
	/**
	 * 保存模型信息
	 * @param description 描述
	 * @param modelname 模型名称
	 * @param subModelids 子模块
	 * @param subModelnames 子模块名称
	 * @return
	 */
	public int saveadvertModel(String description,String modelname,/*String[] subModelids,String[] subModelnames*/Map modemodel ,String basePath,File targetFile,MultipartFile file,String userid,String fileName,String modelskin,String cycid) throws Exception;
	/**
	 * 
	 * @param id id
	 * @param mMname  模版名称
	 * @param msubMname 子模版名称
	 * @param mMId  子模版ID
	 * @return
	 */
	public int updateadvertModel(ADVERTMODELMANGER ad);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String deleteadvertModel(JSONObject ids,String modelname);
	
	 /**
	  * 根据模型查模块
	  * @param modelid
	  * @return
	  */
	 public List queryModelModeById(String modelid);
	

}
