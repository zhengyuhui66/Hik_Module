package com.hik.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.ICommonDao;

import net.sf.json.JSONObject;


@Service
@Transactional
public class ICommonServiceImpl  extends BaseService  implements ICommonService{

	@Autowired
	private ICommonDao iCommonDao;

	@Override
	public List queryAllModel(String cycid) {
		// TODO Auto-generated method stub
//		return iCommonDao.queryAllModel(cycid);
		List<JSONObject> result=iCommonDao.queryAllModel(cycid);
		JSONObject json = new JSONObject();
		json.accumulate("id", "");
		json.accumulate("name", "全部");
		result.add(0, json);
		return result;
	}

	@Override
	public List queryAdvgroup() {
		// TODO Auto-generated method stub
		return iCommonDao.queryAdvgroup();
	}
	@Override
	public List queryAllAdvert(String userid,String id,String pid) {
		// TODO Auto-generated method stub
		return iCommonDao.queryAllAdvert(userid,id,pid);
	}
	@Override
	public List queryAllMmpp(String userid,String id,String pid) {
		// TODO Auto-generated method stub
		return iCommonDao.queryAllMmpp(userid,id,pid);
	}
	@Override
	public List queryplayfun() {
		// TODO Auto-generated method stub
		return iCommonDao.queryplayfun();
	}
	@Override
	public List queryAuthCyc() {
		// TODO Auto-generated method stub
		return iCommonDao.queryAuthCyc();
	}
	@Override
	public List querySKIN() {
		// TODO Auto-generated method stub
		return iCommonDao.querySKIN();
	}
	@Override
	public List getPropertys() {
		// TODO Auto-generated method stub
		return iCommonDao.getPropertys();
	}
	@Override
	public List getProperty(String id) {
		// TODO Auto-generated method stub
		return iCommonDao.getProperty(id);
	}

	@Override
	public List querytimeSet() {
		// TODO Auto-generated method stub
		return iCommonDao.querytimeSet();
	}

	@Override
	public List getRoute(String linename) {
		// TODO Auto-generated method stub
		List<JSONObject> result=iCommonDao.getRoute(linename);
		JSONObject json = new JSONObject();
		json.accumulate("id", "");
		json.accumulate("name", "全部");
		result.add(0, json);
		return result;
	}
	@Override
	public List queryRoute(String id) {
		// TODO Auto-generated method stub
		return iCommonDao.queryRoute(id);
	}
	
	@Override
	public List queryBusInfo(String lineId,String busName) {
		// TODO Auto-generated method stub
		List<JSONObject> result=iCommonDao.queryBusInfo(lineId,busName);
		JSONObject json = new JSONObject();
		json.accumulate("id", "");
		json.accumulate("name", "全部");
		result.add(0, json);
		return  result;
	}

	@Override
	public List queryBusInfo() {
		// TODO Auto-generated method stub
		return iCommonDao.queryBusInfo();
	}

	@Override
	public List queryProduct(String cycid, String modelid) {
		// TODO Auto-generated method stub
		List<JSONObject> result=iCommonDao.queryProduct(cycid, modelid);
		JSONObject json = new JSONObject();
		json.accumulate("id", "");
		json.accumulate("name", "全部");
		result.add(0, json);
		return result;
	}

	@Override
	public String queryModelNum(String modelid) {
		// TODO Auto-generated method stub
		List aList = iCommonDao.queryModelNum(modelid);
		if(aList==null||aList.size()==0){
			return "0";
		}
		String str=aList.get(0).toString();
//		int result =Integer.parseInt(str);
		return str;
	}

	@Override
	public List queryProEvent() {
		// TODO Auto-generated method stub
		return iCommonDao.queryProEvent();
	}

	@Override
	public List queryProClientType() {
		// TODO Auto-generated method stub
		return iCommonDao.queryProClientType();
	}

	@Override
	public List queryProClientSys() {
		// TODO Auto-generated method stub
		return iCommonDao.queryProClientSys();
	}

	@Override
	public List queryProPhone() {
		// TODO Auto-generated method stub
		return iCommonDao.queryProPhone();
	}

	@Override
	public List queryProAddress() {
		// TODO Auto-generated method stub
		return iCommonDao.queryProAddress();
	}
	@Override
	public List queryProTime() {
		// TODO Auto-generated method stub
		return iCommonDao.queryProTime();
	}

	@Override
	public List queryPutFlag() {
		// TODO Auto-generated method stub
		return iCommonDao.queryPutFlag();
	}

	@Override
	public int setPutFlag(String flag) {
		// TODO Auto-generated method stub
		return iCommonDao.setPutFlag(flag);
	}

	@Override
	public List getAllUser() {
		// TODO Auto-generated method stub
		return iCommonDao.getAllUser();
	}

	@Override
	public List queryAllRoute() {
		// TODO 自动生成的方法存根
		return iCommonDao.queryAllRoute();
	}

	@Override
	public List queryAllCorp() {
		// TODO 自动生成的方法存根
		return iCommonDao.queryAllCorp();
	}

//	@Override
//	public List queryDeviceInfo() {
//		// TODO Auto-generated method stub
//		return iCommonDao.queryDeviceInfo();
//	}

	@Override
	public List queryDeviceInfo(String busid) {
		// TODO Auto-generated method stub
		return iCommonDao.queryDeviceInfo(busid);
	}

	@Override
	public List queryIfhaveDevice(String equipmentid,String apmac) {
		// TODO Auto-generated method stub
		return iCommonDao.queryIfhaveDevice(equipmentid, apmac);
	}

	@Override
	public List queryIfhaveBusInfo(String name) {
		// TODO Auto-generated method stub
		return iCommonDao.queryIfhaveBusInfo(name);
	}

	@Override
	public List queryIfhaveRouteInfo(String name) {
		// TODO Auto-generated method stub
		return iCommonDao.queryIfhaveRouteInfo(name);
	}

	@Override
	public List queryIfhaveCorpInfo(String name) {
		// TODO Auto-generated method stub
		return iCommonDao.queryIfhaveCorpInfo(name);
	}

	
}
