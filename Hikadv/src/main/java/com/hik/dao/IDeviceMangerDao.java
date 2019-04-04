package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IDeviceMangerDao {
	/**
	 * 分页查询线路
	 * @param start 开始条数
	 * @param limit 每页几条
	 * @param searchStr 筛选条件
	 * @return
	 */
		Page queryDeviceManger(int start,int limit,String searchStr);
		/**
		 * 新增线路
		 * @param putproduct
		 * @return
		 */
		int addDeviceManger(DEVICE device);
		/**
		 * 新增未注册线路
		 * @param putproduct
		 * @return
		 */
		int addDeviceManger_unregister(DEVICE device);
		/**
		 * 更新线路
		 * @param putproduct
		 * @return
		 */
		int updateDeviceManger(DEVICE device);
		/**
		 * 删除线路
		 * @param ids
		 * @return
		 */
		String deleteDeviceManger(String[] ids);
		
		int updateDeviceManger(String equipmentid,int isonline,String ver);
		
		int updateDeviceManger(String equipmentid, int isonline);
		
		List queryDeviceManger(String equipmentid);
		
		List queryDeviceGPSsupport(String equipmentid);
		
		
		List queryDeviceGPSsupport();
		
		
		List queryDevices(List equipmentids);
//		List queryifRouteManger(String[] ids);
		
	

}
