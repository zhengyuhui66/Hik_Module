package com.hik.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import com.hik.app.entity.RedisBean;

import net.sf.json.JSONObject;

@Repository
public class RedisBaseDaoImpl  implements RedisBaseDao{

	@Autowired
	protected RedisTemplate<String,String> redisTemplate;
	
	private  RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();  
	}  
//	/**
//	 * 添加对象
//	 */
//	@Override
//	public boolean add(final RedisBean redisBean) {  
//		boolean b=redisTemplate.execute(new RedisCallback<Boolean>() {  
//			public Boolean doInRedis(RedisConnection connection)  
//					throws DataAccessException {  
//				RedisSerializer<String> serializer = getRedisSerializer();  
//				byte[] key  = serializer.serialize(redisBean.getKey());  
//				byte[] name = serializer.serialize(redisBean.getValue());  
////				boolean s=connection.setNX(key, name);  
//				connection.set(key, name);  
//				return true;
//			}  
//		});  
//		return b;  
//	}  
//
//	/**
//	 * 添加集合
//	 */
//	@Override
//	public boolean add(final List<RedisBean> list) {
//		Assert.notEmpty(list);  
//		boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {  
//			public Boolean doInRedis(RedisConnection connection)  
//					throws DataAccessException {  
//				RedisSerializer<String> serializer = getRedisSerializer();  
//				for (RedisBean redisBean : list) {  
//					byte[] key  = serializer.serialize(redisBean.getKey());  
//					byte[] name = serializer.serialize(redisBean.getValue());  
//					connection.setNX(key, name);  
//				}  
//				return true;  
//			}  
//		}, false, true);  
//		return result; 
//	}  
	
	/**
	 * 删除对象 ,依赖key
	 */
	public void delete(String key) {  
		List<String> list = new ArrayList<String>();  
		list.add(key);  
		delete(list);  
	}  
//  
//	/**
//	 * 删除集合 ,依赖key集合
//	 */
	public void delete(List<String> keys) {
		RedisSerializer<String> serializer = getRedisSerializer();  
		for(int i=0;i<keys.size();i++){
			redisTemplate.delete(serializer.deserialize(keys.get(i).getBytes()));  			
		}
	}  
	
//	/**
//	 * 修改对象 
//	 */
//	public boolean update(final RedisBean redisBean) {
//		String key =redisBean.getKey();
//		if (get(key) == null) {  
//			throw new NullPointerException("数据行不存在, key = " + key);  
//		}  
//		boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {  
//			public Boolean doInRedis(RedisConnection connection)  
//					throws DataAccessException {  
//				RedisSerializer<String> serializer = getRedisSerializer();  
//				byte[] key  = serializer.serialize(redisBean.getKey());  
//				byte[] name = serializer.serialize(redisBean.getValue());  
//				connection.set(key, name);  
//				return true;  
//			}  
//		});  
//		return result;  
//	}  
//	
	/**
	 * 根据key获取对象
	 */
	public RedisBean get(final String keyId) {  
		RedisBean result = (RedisBean) redisTemplate.execute(new RedisCallback<RedisBean>() {  
			public RedisBean doInRedis(RedisConnection connection)  
					throws DataAccessException {  
				RedisSerializer<String> serializer = getRedisSerializer();  
				byte[] key = serializer.serialize(keyId);  
				byte[] value = connection.get(key);  
				if (value == null) {  
					return null;  
				}  
				String nickname = serializer.deserialize(value);  
				return new RedisBean(keyId, nickname);  
			}  
		});  
		return result;  
	}

//	//新增map集合
//	public void setToHashPutAll(RedisBean<Map<String,String>> redisBean){
//		Map<String,String> map=redisBean.getValue(); 
//		redisTemplate.opsForHash().putAll(redisBean.getKey(), map);
//		redisTemplate.expire(redisBean.getKey(), 30l, TimeUnit.SECONDS);
//	}
//	
//	public void setToHashPutkey(RedisBean<Map<String,String>> redisBean){
//		Map<String,String> map=redisBean.getValue(); 
//		Iterator iter = map.entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			Object key = entry.getKey();
//			Object val = entry.getValue();
//			Boolean putIfAbsent = redisTemplate.opsForHash().putIfAbsent(redisBean.getKey(),key, val);  
//		}
//		redisTemplate.expire(redisBean.getKey(), 30l, TimeUnit.SECONDS);
//	}
//	
	
	//删除redis数据里的一个Map;
//	public void deleteToHashBykey(String key,String mapKey){
//		redisTemplate.opsForHash().delete(key, mapKey);
//	}
//	
//	//获取redis里面Hash类型的key集合的value集合
//	public List getHashListBykey(String key,List str){
//		List result=redisTemplate.opsForHash().multiGet(key, str);
//		return result;
//	}
////	
//	public Map getHashAllBykey(String key){
//		Map map=redisTemplate.opsForHash().entries(key);
//		return map;
//	}
//	
	
//	public String getHashByKey1(String key ,String mapKey){
//		String result = (String) redisTemplate.opsForHash().get(key, mapKey);  
//		return result;
//	}
//	public void setToValueBykey1(RedisBean<String> redisBean){
//		redisTemplate.opsForValue().set(redisBean.getKey(),redisBean.getValue());
//	}
	
	
	
	public String getValueByKey(String key){
		String str= redisTemplate.opsForValue().get(key);
		return str;
	}
	
	public void setToValuePeriod(RedisBean<String> redisBean,Long mill){
		redisTemplate.opsForValue().set(redisBean.getKey(),redisBean.getValue(),mill,TimeUnit.SECONDS);
	}
	
	public void setToValueDefaultPeriod(RedisBean<String> redisBean){
		setToValuePeriod(redisBean,120L);
	}

	@Override
	public boolean hasKey(String key) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.hasKey(key);
		return result;
	}
	
	
	
	public void setToJSONPutkey(RedisBean<JSONObject> redisBean){
		//获取新的值
		JSONObject mapNew =redisBean.getValue();
		//根据redisId,取出原始里面的值
		String redisId=redisBean.getKey();
		String srcMap=getValueByKey(redisId);
		JSONObject jasonObject;
		if(StringUtils.isEmpty(srcMap)){
			 jasonObject = new JSONObject();
		}else{
			 jasonObject = JSONObject.fromObject(srcMap);
		}
//		Map<String,String> desMap = (Map<String,String>)jasonObject;
		//将新值覆盖原始值中
		jasonObject.putAll((Map)mapNew);
		//重新存入
		RedisBean<String> result= new RedisBean<String>(redisId,jasonObject.toString());
		setToValueDefaultPeriod(result);
	}
	
	
	
	public String getValueFromJSON(String key,String mapKey){
		String srcMap=getValueByKey(key);
		if(srcMap==null){
			return null;
		}
		JSONObject jasonObject=JSONObject.fromObject(srcMap);
		Object result=jasonObject.get(mapKey);
		if(result!=null){
			return result.toString();
		}else{
			return null;
		}
	}

	
}