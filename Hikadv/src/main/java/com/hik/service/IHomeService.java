package com.hik.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IHomeService {
	/**
	 * ���Ϊ��λ��չʾͳ��top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByAdv();
	/**
	 * ��·Ϊ��λ��չʾͳ��top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByRoute();
	/**
	 * ���Ϊ��λ�ĵ��ͳ�ƴ���
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByAdv();
	/**
	 *��·Ϊ��λ�ĵ��ͳ�ƴ���
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByRoute();
	/**
	 * ȫ��ͳ��
	 * @return
	 */
	public List<JSONObject> getTotalInfo();
}
