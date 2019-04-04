package com.hik.dao;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutScheduleLogDao {

	public Page queryPutLog(int start,int limit,String sreachStr);
	
	public int[] savePutLog(List<JSONObject> list,String userid);
	
}
