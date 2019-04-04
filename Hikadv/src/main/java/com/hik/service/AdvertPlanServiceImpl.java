package com.hik.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.IAdvertPlanDao;
import com.jcraft.jsch.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Deprecated
@Service
@Transactional
public class AdvertPlanServiceImpl  extends BaseService  implements IAdvertPlanService{
//	@Autowired
//	private IAdvertPlanDao iAdvertPlanDao;
////	@Autowired
////	private UpdateAdvertPlanCallable advertPlanCallable;
//	public List queryRoadInfo() {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.queryRoadInfo();
//	}
//
//	public List queryBusInfo(String lineId) {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.queryBusInfo(lineId);
//	}
//
////	public List queryAllModel(String cycid) {
////		// TODO Auto-generated method stub
////		return iAdvertPlanDao.queryAllModel(cycid);
////	}
//
//	
//	private int NumforThread=10;
//	
//	private List<List<String>> getList(String busId){
//		List<List<String>> list = new ArrayList<List<String>>();
//		String[] busIds=busId.split(",");
//		for(int i=0;i<busIds.length;i++){
//			List<String> listBusIds=new ArrayList<String>();
//			int num=((i+NumforThread)<busIds.length?(i+NumforThread):busIds.length);
//			for(int j=i;j<num;j++){
//				listBusIds.add(busIds[j]);
//			}
//			i=(num-1);
//			list.add(listBusIds);
//		}
//		return list;
//	}
//	
//	public int updateAdvertPlan(String stime, String etime, String modelId, String busId,String modelmodeId,String advertId) throws InterruptedException, ExecutionException {
//		// TODO Auto-generated method stub
////		//分多线程;
//		long times=System.currentTimeMillis();
//		int result=0;
//		List<List<String>> list = getList(busId);
//		
//		ExecutorService executor = Executors.newCachedThreadPool();
//		Map<Integer,Future<Integer>> futures=new HashMap<Integer,Future<Integer>>();
//		for(int i=0;i<list.size();i++){
//			UpdateAdvertPlanCallable advertPlanCallable=new UpdateAdvertPlanCallable(stime, etime, modelId, list.get(i), modelmodeId, advertId,iAdvertPlanDao);
//			Future<Integer> future = executor.submit(advertPlanCallable);
//			futures.put(i, future);
//		}
//		executor.shutdown();
//		Set<Integer> set = futures.keySet();
//		for (Integer key : set) {
//			Future<Integer> fut = futures.get(key);
//			result+=fut.get();
//		}
//		System.out.println("认证界面一共花了:"+(System.currentTimeMillis()-times)+"ms");
//		return result;
//	}
//	public int updateLoginedAdvertPlan(String stime, String etime, String modelId, String busId,String modelmodeId,String advertId) throws InterruptedException, ExecutionException {
//		// TODO Auto-generated method stub
//		int result=0;
//		long times=System.currentTimeMillis();
//		List<List<String>> list = getList(busId);
//		
//		ExecutorService executor = Executors.newCachedThreadPool();
//		Map<Integer,Future<Integer>> futures=new HashMap<Integer,Future<Integer>>();
//		for(int i=0;i<list.size();i++){
//			UpdateLoginedAdvertPlanCallable advertPlanCallable=new UpdateLoginedAdvertPlanCallable(stime, etime, modelId, list.get(i), modelmodeId, advertId,iAdvertPlanDao);
//			Future<Integer> future = executor.submit(advertPlanCallable);
//			futures.put(i, future);
//		}
//		executor.shutdown();
//		Set<Integer> set = futures.keySet();
//		for (Integer key : set) {
//			Future<Integer> fut = futures.get(key);
//			result+=fut.get();
//		}
//		System.out.println("认证成功一共花了:"+(System.currentTimeMillis()-times)+"ms");
////		String[] busIds=busId.split(",");
////		for(int i=0;i<busIds.length;i++){
////			boolean flag=iAdvertPlanDao.getLoginedProduceUpdateBusId(stime, etime, busIds[i], modelId, modelmodeId, advertId);	
////			if(flag){
////				result++;
////			}
////		}
//		return result;
//	}
//	public List getBusAdvertPlanHistory(String busid) {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.getBusAdvertPlanHistory(busid);
//	}
//
//	public List getBusLoginedAdvertPlanHistory(String busid) {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.getBusLoginedAdvertPlanHistory(busid);
//	}
//	public List queryModelModeById(String modelid) {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.queryModelModeById(modelid);
//	}
//
//
//	
//	private JSONObject switchAdvJson(String modeAdvertid){
//		JSONObject jsons = JSONObject.fromObject(modeAdvertid);
//		Iterator iterator = jsons.keys();
//		while(iterator.hasNext()){
//	        String key = (String) iterator.next();
//	        String value = jsons.getString(key);
//	        List result = iAdvertPlanDao.queryAdvidByAdvName(value);
//	        if(result!=null&&result.size()>0){
//	        	String reValue=result.get(0).toString();
//	        	jsons.put(key, reValue);
//	        }
//		}
//		return jsons;
//	}
//
//
//	public int updateRouteAdvertPlan(String stime, String etime, String routeId, String modelId, String modeAdvertid) throws InterruptedException, ExecutionException {
//		// TODO Auto-generated method stub
//		long times=System.currentTimeMillis();
////		JSONArray routeIds=JSONArray.fromObject(routeId);
//		JSONObject routeIds=JSONObject.fromObject(routeId);
//		JSONObject modeAdvJson=switchAdvJson(modeAdvertid);
//		int result=0;
//		
//		
//		
//		ExecutorService executor = Executors.newCachedThreadPool();
//		Map<Integer,Future<Integer>> futures=new HashMap<Integer,Future<Integer>>();
////		for(int i=0;i<routeIds.size();i++){
////			JSONObject routeid=routeIds.getJSONObject(i);
//			List listBusId=iAdvertPlanDao.queryBusInfos(routeIds.getString("name"));
//			UpdateAdvertRoutePlanCallable advertPlanCallable=new UpdateAdvertRoutePlanCallable(stime, etime, modelId, listBusId, modeAdvJson, NumforThread, iAdvertPlanDao);
//			Future<Integer> future = executor.submit(advertPlanCallable);
//			futures.put(0, future);
////		}
//		executor.shutdown();
//		Set<Integer> set = futures.keySet();
//		for (Integer key : set) {
//			Future<Integer> fut = futures.get(key);
//			result+=fut.get();
//		}
//		System.out.println("路线认证界面一共花了:"+(System.currentTimeMillis()-times)+"ms");
//
//		return result;
//	}
//
//	public int updateLoginedRouteAdvertPlan(String stime, String etime, String routeId, String modelId,
//			String modeAdvertid) throws InterruptedException, ExecutionException {
//		// TODO Auto-generated method stub
//		long times=System.currentTimeMillis();
////		JSONArray routeIds=JSONArray.fromObject(routeId);
//		JSONObject routeIds=JSONObject.fromObject(routeId);
//		JSONObject modeAdvJson=switchAdvJson(modeAdvertid);
//		int result=0;
//		
//		ExecutorService executor = Executors.newCachedThreadPool();
//		Map<Integer,Future<Integer>> futures=new HashMap<Integer,Future<Integer>>();
////		for(int i=0;i<routeIds.size();i++){
////			JSONObject routeid=routeIds.getJSONObject(i);
//			List listBusId=iAdvertPlanDao.queryBusInfos(routeIds.getString("name"));
//			
//			UpdateLoginedAdvertRoutePlanCallable advertPlanCallable=new UpdateLoginedAdvertRoutePlanCallable(stime, etime, modelId, listBusId, modeAdvJson, NumforThread, iAdvertPlanDao);
//			Future<Integer> future = executor.submit(advertPlanCallable);
//			futures.put(0, future);
////		}
//		executor.shutdown();
//		Set<Integer> set = futures.keySet();
//		for (Integer key : set) {
//			Future<Integer> fut = futures.get(key);
//			result+=fut.get();
//		}
//		System.out.println("路线认证成功一共花了:"+(System.currentTimeMillis()-times)+"ms");
//
//
//		return result;
//	}
//
//	public int cancelPutAdvert(List busids) {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.cancelPutAdvert(busids);
//	}
//
//	public int cancelPutLoginedAdvert(List busids) {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.cancelPutLoginedAdvert(busids);
//	}
//
//	public int pausePutAdvert(List busids,String value) {
//		// TODO Auto-generated method stub
//		int result=0;
//		for(int i=0;i<busids.size();i++){
//			result+=iAdvertPlanDao.pausePutAdvert(busids.get(i).toString(),value);
//		}
//		return result;
//	}
//
//	public int pausePutLoginedAdvert(List busids,String value) {
//		// TODO Auto-generated method stub
////		return iAdvertPlanDao.pausePutLoginedAdvert(busids,value);
//		int result=0;
//		for(int i=0;i<busids.size();i++){
//			result+=iAdvertPlanDao.pausePutLoginedAdvert(busids.get(i).toString(),value);
//		}
//		return result;
//	}
//
//	public List queryBusidByRouteIds(List routeids) {
//		// TODO Auto-generated method stub
//		return iAdvertPlanDao.queryBusidByRouteIds(routeids);
//	}

}
