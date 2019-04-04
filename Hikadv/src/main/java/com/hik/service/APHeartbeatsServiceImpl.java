package com.hik.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.app.entity.RedisBean;
import com.hik.dao.BaseService;
import com.hik.dao.RedisBaseDao;
import com.hik.framework.utils.DateUtil;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

@Service("apHeartbeatsServiceImpl")
@Transactional
public class APHeartbeatsServiceImpl  extends BaseService implements APHeartbeatsService{
	
	@Autowired
	private RedisBaseDao redisBaseDao;
	
	@Override
	public void refreshHeartTime(String equipmentid){
		// TODO Auto-generated method stub
//		long temptime = System.currentTimeMillis();
//		log.info("============>>>>>>设备编号:"+equipmentid);
//		redisBaseDao.add(new RedisBean(equipmentid, temptime+"|0"));
		
		JSONObject map = new JSONObject();
		map.put(PROCEDURCES.LASTONLINETIME,DateUtil.getCurrentDate());
		map.put(PROCEDURCES.ISONLINE, "1");
		RedisBean<JSONObject> redisBean = new RedisBean<JSONObject>(equipmentid,map);
		redisBaseDao.setToJSONPutkey(redisBean);
//		redisBaseDao.setToValueDefaultPeriod(new RedisBean(equipmentid, "0|0"));
	}
	
	
	public void refreshGpsTime(String equipmentid){
		// TODO Auto-generated method stub
		long temptime = System.currentTimeMillis();
//		log.info("============>>>>>>设备编号:"+equipmentid);
//		redisBaseDao.add(new RedisBean(equipmentid, temptime+"|0"));
		JSONObject map = new JSONObject();
		map.put(PROCEDURCES.GPSLASTREPORTTIME,temptime+"");
		RedisBean<JSONObject> redisBean = new RedisBean<JSONObject>(equipmentid,map);
		redisBaseDao.setToJSONPutkey(redisBean);
//		redisBaseDao.setToValueDefaultPeriod(new RedisBean(equipmentid, "0|0"));
	}

}
