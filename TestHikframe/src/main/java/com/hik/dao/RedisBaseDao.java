package com.hik.dao;

import java.util.List;
import java.util.Map;

import com.hik.app.entity.RedisBean;

import net.sf.json.JSONObject;

public interface RedisBaseDao {
//	public boolean add(final RedisBean redisBean);
//	public boolean add(final List<RedisBean> list);
	public void delete(String key);
//	public void delete(List<String> keys);
//	public boolean update(final RedisBean member);
//	public RedisBean get(final String keyId);
	
	public boolean hasKey(String key);
	
	
	
	public void setToJSONPutkey(RedisBean<JSONObject> redisBean);
	
	public String getValueFromJSON(String key,String mapKey);
//	public void setToHashPutAll(RedisBean<Map<String,String>> redisBean);
//	
//	public void setToHashPutkey(RedisBean<Map<String,String>> redisBean);
//	
//	
//	//ɾ��redis�������һ��Map;
//	public void deleteToHashBykey(String key,String mapKey);
	
	//��ȡredis����Hash���͵�key���ϵ�value����
//	public List getHashListBykey(String key,List str);
	
//	public Map getHashAllBykey(String key);
//	
//	public String getHashByKey(String key ,String mapKey);
//	
	
	
	
//	public void setToValueBykey(RedisBean<String> redisBean);
//	
//	public String getValueByKey(String key);
	
	public void setToValuePeriod(RedisBean<String> redisBean,Long mill);
	
	public void setToValueDefaultPeriod(RedisBean<String> redisBean);
	
}
