package com.hik.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVERTMODELMANGER;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IAdvertModelDao {
	/**
	 * ��ѯ���еĹ��ģ��
	 * @return
	 */
	public Page queryadvertModel(int start,int limit,String searchStr);
	
	/**
	 * 
	 * @param modelmodeid ����ģ��ID
	 * @param url �����ַ
	 * @param name ģ������
	 * @param modelname ��ģ������
	 * @param userid �û�id
	 * @param description ����
	 * @return
	 */
//	public int saveadvertModel(String modelid,String modelmodeid,String url,String name,String modelname,String userid,String description,String fileName,String modelskin);
	public int saveadvertModel(Map modemodel,String url,String name,String userid,String description,String fileName,String modelskin,String cycid);

//	/**
//	 * ��ȡģ��ID
//	 * @return
//	 */
//	public String getModelId();
	
	public int updateadvertModel(ADVERTMODELMANGER ad);
	
//	public int updateModel(String modelskin,String name,String modelid);
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int deleteadvertModel(List id);
	
	public List queryIfInproduct(List id);
	
	public List queryModelModeById(String modelid);

}
