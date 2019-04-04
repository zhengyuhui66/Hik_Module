package com.hik.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutMangerSetService {
	/**
	 * ��ҳ��ѯ
	 * @param start  ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ����ģ����ѯ
	 * @return
	 */
	Page queryForPage(int start,int limit,String searchStr);
	/**
	 * ���»�������
	 * @param pricondition
	 * @return
	 */
	int addorUpdateAdvputSet(PRICONDITION pricondition);
	int deleteAdvputSet(String[] id);
	
	List queryputSet();
}
