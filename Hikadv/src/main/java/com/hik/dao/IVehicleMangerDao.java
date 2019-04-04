package com.hik.dao;
/**
 * By Cookies
 * 
 */
import java.util.List;

import com.hik.app.entity.VEHICLE;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IVehicleMangerDao{
		/**
		 * ��ȡ�����б�
		 * @param UserId
		 * @param start
		 * @param limit
		 * @param searchStr
		 * @return
		 */
		public Page queryVehicleList(String UserId,int start,int limit,String searchStr );
		/**
		 * ɾ������
		 * @param vehicleId
		 * @return
		 */
		public int deleteVehicle(String[] vehicleId);
		/**
		 * ���泵����Ϣ
		 * @param vehicle
		 * @return
		 */
		public int saveVehicle(VEHICLE vehicle);
		/**
		 * ���³�����Ϣ
		 * @param vehicle
		 * @return
		 */
		public int updateVehicle(VEHICLE vehicle);
		/**
		 * ��ȡ������Ϣ
		 * @param vehicleId
		 * @return
		 */
		public List getVehicleDetail(String vehicleId);
		
		int setting(String[] ids,String speed,String timeout);
		
		
		public List<JSONObject> queryDeviceList(String id);
}