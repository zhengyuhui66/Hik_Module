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
	 * @param mac  设备物理地址
	 * @return
	 */
	public String queryLoginedBusId(String mac,String phone,String phonemac);
	
	/**
	 * 
	 * @param advertId  广告ID
	 * @return
	 */
	public String queryAdvertUrl(String advertId);
	/**
	 * 
	 * @param log 日志
	 * @return
	 */
	public String saveClickCount(CLICK_COUNT_LOG log,String advertId);
	/**
	 * 验证码真为
	 * @param code 验证码
	 * @param phone 手机
	 * @return
	 */
	public boolean toVaild(String code,String phone);
	/**
	 * 展示统计
	 * @param jsons
	 */
	public void showCount(JSONArray jsons,String mac,String phone,String phonemac);
	
//	public int test();
}
