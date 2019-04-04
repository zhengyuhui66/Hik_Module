package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ICorMangerService {
/**
 * ��ҳ��ѯͶ�Ų�Ʒ
 * @param start ��ʼ����
 * @param limit ÿҳ����
 * @param searchStr ɸѡ����
 * @return
 */
	Page queryCorpManger(int start,int limit,String searchStr);
	/**
	 * ���������Ͷ�Ų�Ʒ
	 * @param putproduct
	 * @return
	 */
	int addorUpdateCorpManger(CORP corp);
	/**
	 * ɾ��Ͷ�Ų�Ʒ
	 * @param ids
	 * @return
	 */
	String deleteCorpManger(String[] ids);


}
