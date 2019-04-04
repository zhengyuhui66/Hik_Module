package com.hik.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.IAdvertPlanDao;

import net.sf.json.JSONObject;

@Deprecated
public class UpdateLoginedAdvertRoutePlanCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
//	private String stime;
//	private String etime;
//	private String modelId;
//	private List<String> busId;
//	private JSONObject modeAdvJson;
//	private IAdvertPlanDao iAdvertPlanDao;
//	private int numforThread;
//	public UpdateLoginedAdvertRoutePlanCallable() {
//	};
//
//	public UpdateLoginedAdvertRoutePlanCallable(String stime, String etime, String modelId, List<String> busId, JSONObject modeAdvJson,int numforThread,IAdvertPlanDao iAdvertPlanDao) {
//		this.stime=stime;
//		this.etime=etime;
//		this.busId=busId;
//		this.modelId=modelId;
//		this.modeAdvJson=modeAdvJson;
//		this.iAdvertPlanDao=iAdvertPlanDao;
//		this.numforThread=numforThread;
//	};
//	private static List<List<String>> getList(List<String> list,int number){
//		List<List<String>> nList = new ArrayList<List<String>>();
//		int fnum=number;
//		for(int i=0;i<list.size();i+=fnum){
//			if(list.size()-i<number){
//				fnum=list.size()-i;
//			}
//			nList.add(list.subList(i, i+fnum));
//		}
//		return nList;
//	}
//	public Integer call() throws Exception {
//		// TODO Auto-generated method stub
//		int result=0;
//		Iterator iterator = modeAdvJson.keys();
//		while(iterator.hasNext()){
//	        //广告位ID
//			String modelmodeId = (String) iterator.next();
//	        //广告ID
//			String advertId = modeAdvJson.getString(modelmodeId);
//	        
//			List<List<String>> list=getList(busId,numforThread);
//			ExecutorService executor = Executors.newCachedThreadPool();
//			Map<Integer,Future<Integer>> futures=new HashMap<Integer,Future<Integer>>();
//			for(int i=0;i<list.size();i++){
//				UpdateLoginedAdvertPlanCallable advertPlanCallable=new UpdateLoginedAdvertPlanCallable(stime, etime, modelId, list.get(i), modelmodeId, advertId,iAdvertPlanDao);
//				Future<Integer> future = executor.submit(advertPlanCallable);
//				futures.put(i, future);
//			}
//			executor.shutdown();
//			Set<Integer> set = futures.keySet();
//			for (Integer key : set) {
//				Future<Integer> fut = futures.get(key);
//				result+=fut.get();
//			}
//		}
//		return result;
//	}

}
