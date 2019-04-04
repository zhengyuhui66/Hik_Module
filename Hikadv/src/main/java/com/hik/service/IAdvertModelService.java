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
	 * ��ѯ���еĹ��ģ��
	 * @return
	 */
	public Page queryadvertModel(int start,int limit,String searchStr);
	/**
	 * ����ģ����Ϣ
	 * @param description ����
	 * @param modelname ģ������
	 * @param subModelids ��ģ��
	 * @param subModelnames ��ģ������
	 * @return
	 */
	public int saveadvertModel(String description,String modelname,/*String[] subModelids,String[] subModelnames*/Map modemodel ,String basePath,File targetFile,MultipartFile file,String userid,String fileName,String modelskin,String cycid) throws Exception;
	/**
	 * 
	 * @param id id
	 * @param mMname  ģ������
	 * @param msubMname ��ģ������
	 * @param mMId  ��ģ��ID
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
	  * ����ģ�Ͳ�ģ��
	  * @param modelid
	  * @return
	  */
	 public List queryModelModeById(String modelid);
	

}
