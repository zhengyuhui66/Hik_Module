package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.DEVICEONOFFLOG;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IDeviceOfflineDao {
	/**
	 * ��ҳ��ѯ�豸��������Ϣ
	 * @param start ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ɸѡ����
	 * @return
	 */
		Page queryDeviceOffline(int start,int limit,String searchStr);
		
		List queryByEquipment(String equipmentid);
		
		List queryDeviceOffline(String equipmentid);
		
		int addDeviceOnline(String deviceid);

		int updateDeviceOffline(String id);
}
