package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.SHOW_COUNT_LOG;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface SendSMSCodeDao {
	/**
	 * 发送一条信息
	 * @return
	 */
//	public void sendSMSCodeDao(String phone,String smsContent,String time,int type);
	/**
	 * 
	 * @param mac  设备物理地址
	 * @return
	 */
	public List queryBusId(String mac);
	/**
	 * 
	 * @param mac  设备物理地址
	 * @return
	 */
	public List queryLoginedBusId(String mac);
	/**
	 * 得到地址
	 * @param advertId 广告ID
	 * @return
	 */
	public List<JSONObject> queryAdvertUrl(String advertId);
	/**
	 * 
	 * @param log 广告日志
	 * @return
	 */
	public int saveClickCount(CLICK_COUNT_LOG log);
	/**
	 * 查询mac  的BUSID
	 * @param mac
	 * @return
	 */
	public List queryBusiByMAC(String mac);
	/**
	 * 
	 * @param showlogs 插入的数据信息
	 * @return
	 */
	public int saveShowCount(SHOW_COUNT_LOG showlogs);
	/**
	 * 
	 * @param mac
	 * @return
	 */
	public List queryPausedByMac(String mac);
	
	/**
	 * 
	 * @param mac
	 * @return
	 */
	public List queryLoginedPausedByMac(String mac);
	/**
	 * 
	 * @param phone_mac 手机物理地址
	 * @param phone 手机号
	 * @return
	 */
	public int saveRecordNet(String phone_mac,String phone,String apmac);
//	/**
//	 * 取得WIFIAuth的ID
//	 * @return
//	 */
//	public int getWifiAuthId();
	
	
	public List getLineIdByMac(String mac);
//	/**
//	 * 验证码真为
//	 * @param code 验证码
//	 * @param phone 手机
//	 * @return
//	 */
//	public List toVaild(String code,String phone);
}
