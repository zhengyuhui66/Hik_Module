package com.hik.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.app.entity.RedisBean;
import com.hik.dao.BaseService;
import com.hik.dao.ICommonDao;
import com.hik.dao.IDeviceMangerDao;
import com.hik.dao.IDeviceOfflineDao;
import com.hik.dao.RedisBaseDao;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;


@Service
@Transactional
public class IDeviceOfflineServiceImpl  extends BaseService  implements IDeviceOfflineService{

	@Autowired
	private IDeviceOfflineDao iDeviceOfflineDao;
	
//	@Autowired
//	private ICommonDao iCommonDao;
	
	@Autowired
	private RedisBaseDao redisBaseDao;
	
	@Autowired
	private IDeviceMangerDao iDeviceMangerDao;
	private long timeouts=0;
	@Override
	public Page queryDeviceOffline(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		return iDeviceOfflineDao.queryDeviceOffline(start, limit, searchStr);
	}

	@Scheduled(cron="0/10 * *  * * ? ")
	public void bTask(){
//		log.info("6秒执行一次上下线查询");
		
		Page page =iDeviceMangerDao.queryDeviceManger(-1, -1, "");
		List<JSONObject> list = page.getElementList();
		for(JSONObject j:list){
			String equipmentid=JSONUtils.getString(j, "equipmentid");
			getOnline(equipmentid);
		}
	}
	public static void main(String[] args) {
		String[] st="sringgf|00".split("\\|");
		System.out.println(st[0]+"===="+st[1]);
	}

//	@Override
//	public void getOnline(String deviceid) {
//		// TODO Auto-generated method stub
//		String redisStr=redisBaseDao.getValueByKey(deviceid);
//		if(redisStr==null){
//			//下线
//			getOnline2(deviceid);
//		}
//	}
	
	
	
	
	
	
	@Override
	public void getOnline(String deviceid) {
		// TODO Auto-generated method stub
//		RedisBean redisBean=redisBaseDao.get(deviceid);
		boolean redisStr=redisBaseDao.hasKey(deviceid);
		JSONObject map = new JSONObject();
		if(!redisStr){
//			log.info("deviceid："+deviceid+" 为空");
			//如果为空,则设置为下线
			map.put(PROCEDURCES.ISONLINE, PROCEDURCES.OFFLINETIME+1);
			RedisBean<JSONObject> redisBean = new RedisBean<JSONObject>(deviceid,map);
			redisBaseDao.setToJSONPutkey(redisBean);
		}else{
//			String[] str = redisStr.split("\\|");
			String isonline = redisBaseDao.getValueFromJSON(deviceid, PROCEDURCES.ISONLINE);
			int temps=Integer.parseInt(isonline);
			if(temps<PROCEDURCES.OFFLINETIME){
				temps++;
				map.put(PROCEDURCES.ISONLINE, temps);
				RedisBean<JSONObject> redisBean = new RedisBean<JSONObject>(deviceid,map);
				redisBaseDao.setToJSONPutkey(redisBean);
			}else if(temps>=PROCEDURCES.OFFLINETIME){
				temps++;
				if(temps>=PROCEDURCES.OFFLINETIME+2){
					return;
				}
				map.put(PROCEDURCES.ISONLINE, temps);
				RedisBean<JSONObject> redisBean = new RedisBean<JSONObject>(deviceid,map);
				redisBaseDao.setToJSONPutkey(redisBean);
				getOnline2(deviceid);
		}else{
				
		}
	}
	return;
	}

////	@Override
	public void getOnline2(String deviceid) {
		// TODO Auto-generated method stub
		//设置是否在线标识为下线
		iDeviceMangerDao.updateDeviceManger(deviceid,0);
		List<JSONObject> offlineList=iDeviceOfflineDao.queryDeviceOffline(deviceid);
		if(offlineList!=null&&offlineList.size()>0){
			JSONObject oflJson=offlineList.get(0);
			String offlinetime=JSONUtils.getString(oflJson,"offlinetime");
			if(StringUtils.isNotEmpty(offlinetime)){
//					log.info("已存在下线时间");
			}else{
				List<JSONObject> ids=iDeviceOfflineDao.queryByEquipment(deviceid);
				if(ids.size()>0){
					JSONObject jsonId=ids.get(0);
					String id =JSONUtils.getString(jsonId, "id");
					iDeviceOfflineDao.updateDeviceOffline(id);
				}else{
//						log.info(deviceid+"没有这台设备");
				}
//					log.info("不存在下线时间，则更新下线时间");
			}
		}else{
//				log.info("没有上线记录");
		}
//		}
	}
	

}
