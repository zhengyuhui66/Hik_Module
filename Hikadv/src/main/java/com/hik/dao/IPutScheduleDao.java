package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IPutScheduleDao {

	public Page queryPutLog(int start,int limit,List<String> date,String[] timeslice,JSONArray authcycid,JSONObject mmppJson,String routeid,String busids,String userid);
	
	
	public List queryPutLogList(List<String> date,String[] timeslice,JSONArray authcycid,JSONObject mmppJson,String routeid,String busids,String userid) throws Exception;
	
//	public List queryPutLogList(String date,String[] timeslice,JSONObject authcycid,String routeid,String busids,String userid);

//	public int deletePutLog(List<String> date,String[] timeslice,JSONArray authcycid,String routeid,String busids,String userid);
	
	public int setPutState(List<String> ids,String state);
	
	public List<JSONObject> queryPut(List<String> ids);
	
	public int deletePutState(List<String> ids);
//	/**
//	 * 暂停记录
//	 * @param ids
//	 * @return
//	 */
//	public int pausePut(String ids);
//	/**
//	 * 恢复记录
//	 * @param ids
//	 * @return
//	 */
//	public int normalPut(String ids);
//	/**
//	 * 取消记录
//	 * @param ids
//	 * @return
//	 */
//	public int cancelPut(String ids);
	
	/**
	 * 移动端投放记录接口
	 * @param date
	 * @param timeslice
	 * @param authcycid
	 * @param busids
	 * @param userid
	 * @return
	 */
	public List queryPutLog4Mobile(String[] date,String[] timeslice,String[] authcycid,String[] busids,String userid);
}
