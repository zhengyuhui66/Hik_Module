package com.hik.service;

import java.util.List;

import com.hik.app.entity.VEHICLE;
import com.hik.framework.utils.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IVehicleMangerService {

	public Page queryVehicleList(String UserId,int start,int limit,String searchStr );
	public int deleteVehicle(String[] vehicleId);
//	/**
//	 * 保存车辆信息
//	 * @param vehicle
//	 * @return
//	 */
//	public int saveVehicle(VEHICLE vehicle);
//	/**
//	 * 更新车辆信息
//	 * @param vehicle
//	 * @return
//	 */
//	public int updateVehicle(VEHICLE vehicle);
	/**
	 * 获取车辆信息
	 * @param vehicleId
	 * @return
	 */
	public List getVehicleDetail(String vehicleId);
	
	public int saveOrUpdateVehicle(VEHICLE vehicle);
	
	String setting(String[] ids,String speed,String timeout);
	
	public List<JSONObject> queryDeviceList(String id);
}
