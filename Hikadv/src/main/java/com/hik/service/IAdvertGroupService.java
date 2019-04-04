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
	 * ��ҳ�õ����еĹ�漯
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getAdvertgroup(int start,int limit,String searchStr);
	
/**3
 * �������߱���
 * @param advgroup
 * @return
 */
	public int saveOrupdateAdvertgroup(ADVGROUP advgroup);
	/**
	 * ɾ��
	 * @param ids
	 * @return
	 */
	public int deleteAdvertgroup(String[] ids);
	/**
	 * ��ѯ���ڵ�id�Ƿ������
	 * @param ids
	 * @return
	 */
	public List queryGroupPutproduct(String[] ids);
	

}
