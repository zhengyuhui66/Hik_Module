package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.WechatManger;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IWechatMangerService {
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
	int addorUpdateWechat(WechatManger wechatManger,Set<String> putconIdSet);
	String deleteWechat(String[] id);
	
	List queryMMPP();
}