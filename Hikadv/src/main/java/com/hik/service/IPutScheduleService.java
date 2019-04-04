package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IPutScheduleService {

	public Page queryPutLog(int start,int limit,String stime,String etime,String[] timeslice,JSONArray authcycid,JSONObject mmppJson,String routeid,String busids,String userid);
	
	public List queryPutLogList(String stime,String etime,String[] timeslice,JSONArray authcycid,JSONObject mmppJson,String routeid,String busids,String userid)  throws Exception;

	public int deletePutLog(List time,String[] timeslice,JSONArray authcycid,JSONObject mmppJson,String routeid,String busids,String userid) throws Exception;
	
	public int normalPutLog(List time,String[] timeslice,JSONArray authcycid,JSONObject mmppJson,String routeid,String busids,String userid) throws Exception;

	public int pausePutLog(List time,String[] timeslice,JSONArray authcycid,JSONObject mmppJson,String routeid,String busids,String userid) throws Exception;
	
	
	public int pausePut(String ids,String userid) throws Exception;
	
	public int normalPut(String ids,String userid) throws Exception;
	
	public int cancelPut(String ids,String userid) throws Exception;
	
}
