package com.hik.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface SendSMSCodeService {
	public void sendSMSCode(String phone,String code);
	
	public String queryBusId(String mac,String phone,String phonemac,HttpSession session);
	/**
	 * 
	 * @param mac  �豸�����ַ
	 * @return
	 */
	public String queryLoginedBusId(String mac,String phone,String phonemac);
	
	/**
	 * 
	 * @param advertId  ���ID
	 * @return
	 */
	public String queryAdvertUrl(String advertId);
	/**
	 * 
	 * @param log ��־
	 * @return
	 */
	public String saveClickCount(CLICK_COUNT_LOG log,String advertId);
	/**
	 * ��֤����Ϊ
	 * @param code ��֤��
	 * @param phone �ֻ�
	 * @return
	 */
	public boolean toVaild(String code,String phone);
	/**
	 * չʾͳ��
	 * @param jsons
	 */
	public void showCount(JSONArray jsons,String mac,String phone,String phonemac);
	
//	public int test();
}
