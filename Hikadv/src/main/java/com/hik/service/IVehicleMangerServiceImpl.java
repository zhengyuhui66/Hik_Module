package com.hik.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.VEHICLE;
import com.hik.dao.BaseService;
import com.hik.dao.IVehicleMangerDao;
import com.hik.dao.IWechatMangerDao;
import com.hik.framework.utils.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class IVehicleMangerServiceImpl extends BaseService implements IVehicleMangerService  {

	@Autowired
	private IVehicleMangerDao iVehicleMangerDao;
	
	@Override
	public Page queryVehicleList(String UserId, int start, int limit, String searchStr) {
		// TODO 自动生成的方法存根
		return iVehicleMangerDao.queryVehicleList(UserId, start, limit, searchStr);
	}

	@Override
	public int deleteVehicle(String[] vehicleId) {
		// TODO 自动生成的方法存根
		return iVehicleMangerDao.deleteVehicle(vehicleId);
	}

	@Override
	public List getVehicleDetail(String vehicleId) {
		// TODO 自动生成的方法存根
		return iVehicleMangerDao.getVehicleDetail(vehicleId);
	}

	@Override
	public int saveOrUpdateVehicle(VEHICLE vehicle) {
		String vId=vehicle.getID();
		if (StringUtils.isNotEmpty(vId)) {
			return iVehicleMangerDao.updateVehicle(vehicle);
		}else{
			return iVehicleMangerDao.saveVehicle(vehicle);
		}
	}

	@Override
	public String setting(String[] ids,String speed,String timeout) {
		// TODO Auto-generated method stub
		if(ids.length==0){
			return "没有选择";
		}
		int result=iVehicleMangerDao.setting(ids,speed,timeout);
		return "共有"+result+"个设备参数被修改";
	}

	@Override
	public List<JSONObject> queryDeviceList(String id) {
		// TODO Auto-generated method stub
		return iVehicleMangerDao.queryDeviceList(id);
	}
	
}
