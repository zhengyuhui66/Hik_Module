package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.BLACKLIST;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IBlackListMangerService {
/**
 * ��ҳ��ѯ��������Ϣ
 * @param start ��ʼ����
 * @param limit ÿҳ����
 * @param searchStr ɸѡ����
 * @return
 */
	Page queryBlackListManger(int start,int limit,String searchStr);
	/**
	 * ��������º�������Ϣ
	 * @param blacklist
	 * @return
	 */
	int addorUpdateBlackListManger(BLACKLIST blacklist);
	/**
	 * ɾ����������Ϣ
	 * @param ids
	 * @return
	 */
	String deleteBlackListManger(String[] ids);


}
