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
	 * ����һ����Ϣ
	 * @return
	 */
//	public void sendSMSCodeDao(String phone,String smsContent,String time,int type);
	/**
	 * 
	 * @param mac  �豸�����ַ
	 * @return
	 */
	public List queryBusId(String mac);
	/**
	 * 
	 * @param mac  �豸�����ַ
	 * @return
	 */
	public List queryLoginedBusId(String mac);
	/**
	 * �õ���ַ
	 * @param advertId ���ID
	 * @return
	 */
	public List<JSONObject> queryAdvertUrl(String advertId);
	/**
	 * 
	 * @param log �����־
	 * @return
	 */
	public int saveClickCount(CLICK_COUNT_LOG log);
	/**
	 * ��ѯmac  ��BUSID
	 * @param mac
	 * @return
	 */
	public List queryBusiByMAC(String mac);
	/**
	 * 
	 * @param showlogs �����������Ϣ
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
	 * @param phone_mac �ֻ������ַ
	 * @param phone �ֻ���
	 * @return
	 */
	public int saveRecordNet(String phone_mac,String phone,String apmac);
//	/**
//	 * ȡ��WIFIAuth��ID
//	 * @return
//	 */
//	public int getWifiAuthId();
	
	
	public List getLineIdByMac(String mac);
//	/**
//	 * ��֤����Ϊ
//	 * @param code ��֤��
//	 * @param phone �ֻ�
//	 * @return
//	 */
//	public List toVaild(String code,String phone);
}
