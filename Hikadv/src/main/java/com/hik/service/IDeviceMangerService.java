package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IDeviceMangerService {
/**
 * ��ҳ��ѯ�豸
 * @param start ��ʼ����
 * @param limit ÿҳ����
 * @param searchStr ɸѡ����
 * @return
 */
	Page queryDeviceManger(int start,int limit,String searchStr);
	
	List queryDevices(List equipmentids);
	/**
	 * ����������豸
	 * @param putproduct
	 * @return
	 */
	int addorUpdateDeviceManger(DEVICE device);
	/**
	 * ɾ���豸
	 * @param ids
	 * @return
	 */
	String deleteDeviceManger(String[] ids);

	String queryDeviceGPSsupport(String equipmentid);
	
	
//	String queryDeviceGPSflag(String equipmentid);
	
	
//	void setDeviceGPSsupport(String equipmentid,int reporttime,int count);
//	void setDeviceGPSsupport(String flag);
}
