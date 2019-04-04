package com.hik.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.RedisBean;
import com.hik.dao.BaseService;
import com.hik.dao.IDeviceMangerDao;
import com.hik.dao.RedisBaseDao;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

@Service("iDeviceMangerServiceImpl")
@Transactional
public class IDeviceMangerServiceImpl  extends BaseService  implements IDeviceMangerService{
	public static void main(String[] args) {
		List<JSONObject> tempList=new ArrayList<JSONObject>();
		for(int i=0;i<10;i++){
			JSONObject json=new JSONObject();
			json.accumulate("key"+i, "value"+i);
			tempList.add(json);
		}
		System.out.println(tempList);
	}
	@Autowired
	private IDeviceMangerDao iDeviceMangerDao;

	@Autowired
	private RedisBaseDao redisBaseDao;
	@Override
	public Page queryDeviceManger(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		
//		,"isonline","onlinetime","offlinetime","lastonlinetime","lastreporttime","reporttime",
		
		Page page = iDeviceMangerDao.queryDeviceManger(start, limit, searchStr);
		List<JSONObject> tempList=page.getElementList();
		for(int i=0;i<tempList.size();i++){
			String logs="";
			String equipmentid=JSONUtils.getString(tempList.get(i), "equipmentid");;
			String isonline =redisBaseDao.getValueFromJSON(equipmentid, PROCEDURCES.ISONLINE);
			if(isonline!=null){
				int isonlines=Integer.parseInt(isonline);
				if(isonlines<PROCEDURCES.OFFLINETIME){
					tempList.get(i).put("isonline","1");
				}else if(isonlines>=PROCEDURCES.OFFLINETIME){

					tempList.get(i).put("isonline","0");
				}
				
				if(isonlines<PROCEDURCES.OFFLINETIME){
					String lastontime =redisBaseDao.getValueFromJSON(equipmentid, PROCEDURCES.LASTONLINETIME);
					tempList.get(i).put("lastonlinetime",lastontime);
				}
				
			}
			logs+="  oracle:GPS上报时间"+tempList.get(i).get("lastreporttime");
			String gpslastreporttime =redisBaseDao.getValueFromJSON(equipmentid, PROCEDURCES.GPSLASTREPORTTIME);
			if(gpslastreporttime!=null){
				logs+="  redis:最后上报时间"+gpslastreporttime;
				tempList.get(i).put("lastreporttime",gpslastreporttime);
			}else{
				logs+="  redis:最后上报时间为空";
			}
		
		}
		Page pages = new PageObject(start, limit, page.getTotal(), tempList);
		return pages;
	}

	@Override
	public int addorUpdateDeviceManger(DEVICE device) {
		// TODO Auto-generated method stub
		String id=device.getId();
		int result=0;
		redisBaseDao.delete(device.getEquipmentid());
//		redisBaseDao.
		if(StringUtils.isEmpty(id)){
			//新增
			result=iDeviceMangerDao.addDeviceManger(device);
		}else{
			//更新
			result=iDeviceMangerDao.updateDeviceManger(device);
		}
		return result;
	}

	@Override
	public String deleteDeviceManger(String[] ids) {
		// TODO Auto-generated method stub
		if(ids.length==0){
			return "没有选择";
		}
		return iDeviceMangerDao.deleteDeviceManger(ids);
	}

	@Override
	public String queryDeviceGPSsupport(String equipmentid) {
		// TODO Auto-generated method stub
		String rb=redisBaseDao.getValueFromJSON(equipmentid, PROCEDURCES.GPSREPORTINTERVAL);
		if(rb==null){
			//如果缓存里没有上报时间则从数据库查询上报时间后，缓存到Redis数据库
			List<JSONObject> list = iDeviceMangerDao.queryDeviceGPSsupport(equipmentid);
			if(list==null||list.size()==0){
				return PROCEDURCES.GPSREPORTTIME;
			}else{
				JSONObject js=list.get(0);
				String reporttime=JSONUtils.getString(js, "reporttime");
				JSONObject map = new JSONObject();
				map.put(PROCEDURCES.GPSREPORTINTERVAL, reporttime);
				RedisBean<JSONObject> redisBean = new RedisBean<JSONObject>(equipmentid,map);
				redisBaseDao.setToJSONPutkey(redisBean);
//				redisBaseDao.setToValueDefaultPeriod(new RedisBean(equipmentid,reporttime));
				return reporttime;
			}
		}else{
			return rb;
		}
	}

	@Override
	public List queryDevices(List equipmentids) {
		// TODO Auto-generated method stub
		List list = iDeviceMangerDao.queryDevices(equipmentids);
		return list;
	}
	
	
	
//	@Override
//	public String  queryDeviceGPSflag(String equipmentid) {
//		// TODO Auto-generated method stub
//		RedisBean rb=redisBaseDao.get(equipmentid+"_gps");
//		String reporttime=rb.getValue();
//		return reporttime;
//	}

//	@Override
//	public void setDeviceGPSsupport(String equipmentid,int reporttime, int count) {
//		// TODO Auto-generated method stub
//		redisBaseDao.add(new RedisBean(equipmentid+"_gps",reporttime+"|"+count));
//	}

//	@Override
//	public void setDeviceGPSsupport(String flag) {
//		// TODO Auto-generated method stub
//		List<JSONObject> devices=iDeviceMangerDao.queryDeviceGPSsupport();
//		for(JSONObject json:devices){
//			String equipmentid=JSONUtils.getString(json, "equipmentid");
//			String reporttime=JSONUtils.getString(json, "reporttime");
//			redisBaseDao.add(new RedisBean(equipmentid+"_gps",reporttime+"|"+flag));
//			
//		}
//	}

}
