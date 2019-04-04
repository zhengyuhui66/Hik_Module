package com.hik.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.ICommonDao;
import com.hik.dao.IPutPlanDao;
import com.hik.dao.IPutScheduleDao;
import com.hik.dao.IPutScheduleLogDao;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
@Transactional
public class IPutScheduleServiceImpl  extends BaseService  implements IPutScheduleService{

	@Autowired
	private IPutScheduleDao iPutScheduleDao;
	
	@Autowired
	private IPutScheduleLogDao iPutScheduleLogDao;
	
	@Autowired
	private ICommonDao iCommonDao;
	/**
	 * 获取两个时间的所有日期
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @return
	 */
	public List getTimes(String stime,String etime){
		Date sDate=DateUtil.getStrToDate(stime,"yyyy-MM-dd");
		Date eDate=DateUtil.getStrToDate(etime,"yyyy-MM-dd");
		List list = new ArrayList();
		while(!sDate.equals(eDate)){
			String stimes=DateUtil.getDateToStr(sDate,"yyyy-MM-dd");
			list.add(stimes);
			sDate=DateUtil.getAddaDay(sDate);
		}
		return list;
	}
	@Override
	public Page queryPutLog(int start, int limit, String stime, String etime, String[] timeslices,JSONArray authcycids,JSONObject mmppJson, 
			String routeid,String busids, String userid) {
		// TODO Auto-generated method stub
		List date=null;
		System.out.println("stime:"+stime+" etime:"+etime);
		if(!StringUtils.isEmpty(stime)&&!StringUtils.isEmpty(stime)){
			date=getTimes(stime.substring(0, 10),etime.substring(0, 10));			
		}
		return iPutScheduleDao.queryPutLog(start, limit, date, timeslices, authcycids,mmppJson,routeid, busids, userid);
	}
	@Override
	public List queryPutLogList(String stime, String etime, String[] timeslice, JSONArray authcycid,JSONObject mmppJson, String routeid,
			String busids, String userid)  throws Exception {
		// TODO Auto-generated method stub
		List date=null;
		System.out.println("stime:"+stime+" etime:"+etime);
		if(!StringUtils.isEmpty(stime)&&!StringUtils.isEmpty(stime)){
			date=getTimes(stime.substring(0, 10),etime.substring(0, 10));			
		}
		return iPutScheduleDao.queryPutLogList(date, timeslice, authcycid, mmppJson,routeid, busids, userid);
	}
	
//	private List getidList(final String time, final String[] timeslice,final JSONArray authcycid,final String routeid,
//			 final String busids,final String userid){
//		long times = System.currentTimeMillis();
//		System.out.println("开始时间："+times);
//	  ExecutorService executor = Executors.newFixedThreadPool(100);
//	  List<Future<List>> listFuture = new ArrayList<Future<List>>(); 
//		  for(int j=0;j<authcycid.size();j++){
//			  final int temp = j;
//			  Callable<List> task= new Callable<List>(){
//				@Override
//				public List call() throws Exception {
//					// TODO Auto-generated method stub
//					List<JSONObject> _ids=iPutScheduleDao.queryPutLogList(time, timeslice, authcycid.getJSONObject(temp), routeid, busids, userid);
//					return _ids;
//				}
//			  };
//		        Future<List> r = executor.submit(task);
//		        listFuture.add(r);
//		  }
//	  executor.shutdown();
//	  List ids= new ArrayList();
//	  int m=0;
//	  for (Future<List> future : listFuture) {
//          try {
//        	  ids.addAll(future.get());   
//          } catch (InterruptedException e) {
//              e.printStackTrace();
//          } catch (ExecutionException e) {
//        	  executor.shutdownNow();
//              e.printStackTrace();
//          }
//      }
//		System.out.println("一共耗时："+(System.currentTimeMillis()-times));
//		return ids;
//	}
	@Override
	public int deletePutLog(List time,String[] timeslice,JSONArray authcycid,JSONObject mmppJson, String routeid,
		 String busids,String userid) throws Exception {
		// TODO Auto-generated method stub
		long times = System.currentTimeMillis();
		System.out.println("开始时间："+times);
//		List ids=getidList(time,timeslice,authcycid,routeid,busids,userid);
		List<JSONObject> ids=iPutScheduleDao.queryPutLogList(time, timeslice, authcycid, mmppJson,routeid, busids, userid);
		System.out.println("查询所要删除的信息一共耗时："+(System.currentTimeMillis()-times));
		int _result = deleteAll(userid, ids);
		System.out.println("一共耗时："+(System.currentTimeMillis()-times));
		return _result;
	}
	@Override
	public int pausePutLog(List time, String[] timeslice, JSONArray authcycid,JSONObject mmppJson, String routeid,
			String busids, String userid) throws Exception {
		// TODO Auto-generated method stub
		List date=null;
//		"datetime","interval","productid","authorder","state","apid"
		List<JSONObject> ids=iPutScheduleDao.queryPutLogList(time, timeslice, authcycid,mmppJson, routeid, busids, userid);
		int _result = pauseAll(userid, ids);
		return _result;
	}
	
	@Override
	public int normalPutLog(List time, String[] timeslice, JSONArray authcycid,JSONObject mmppJson, String routeid,
			String busids, String userid) throws Exception {
		// TODO Auto-generated method stub
		List date=null;
		List<JSONObject> ids=iPutScheduleDao.queryPutLogList(time, timeslice, authcycid, mmppJson,routeid, busids, userid);
		int _result = normalAll(userid, ids);
		 return _result;
	}
	@Override
	public int pausePut(String ids,String userid) {
		// TODO Auto-generated method stub
		List list = null;
		if(StringUtils.isEmpty(ids)){
			return 0;
		}else{
			String[] tempstr = ids.split(",");
			list = Arrays.asList(tempstr);
		}
		int _result = pause(userid, list);
		return _result;
	}
	
	public int pause(final String userid, List list) {
		
		int _result=iPutScheduleDao.setPutState(list, PROCEDURCES.PAUSE_FLAG);//pausePut(ids);
		List<JSONObject> result = iPutScheduleDao.queryPut(list);
		int[]  rest = iPutScheduleLogDao.savePutLog(result, userid);
		return _result;
	}
	public int pauseAll(final String userid, List<JSONObject> list) {
		int _result=0;
		List ali = new ArrayList();
		for(int k=0;k<list.size();k++){
			ali.add(JSONUtils.getString(list.get(k),"id"));
		}
		_result=iPutScheduleDao.setPutState(ali, PROCEDURCES.PAUSE_FLAG);
		for(int n=0;n<list.size();n++){
			list.get(n).put("state", PROCEDURCES.PAUSE_FLAG);
		}
		int[]  rest = iPutScheduleLogDao.savePutLog(list, userid);
		return _result;
	}
	@Override
	public int normalPut(String ids,String userid) {
		// TODO Auto-generated method stub
		List list = null;
		if(StringUtils.isEmpty(ids)){
			return 0;
		}else{
			String[] tempstr = ids.split(",");
			list = Arrays.asList(tempstr);
		}
		int _result = normal(userid, list);
		 return _result;
	}
	public int normal(String userid, List list) {
		int _result=iPutScheduleDao.setPutState(list, PROCEDURCES.NORMAL_FLAG);//pausePut(ids);
		
		List<JSONObject> result = iPutScheduleDao.queryPut(list);
		int[]  rest = iPutScheduleLogDao.savePutLog(result, userid);
		return _result;
	}
	
	public int normalAll(String userid, List<JSONObject> list) {
		int _result=0;
		List ali = new ArrayList();
		for(int k=0;k<list.size();k++){
			ali.add(JSONUtils.getString(list.get(k),"id"));
		}
		_result=iPutScheduleDao.setPutState(ali, PROCEDURCES.NORMAL_FLAG);
		for(int n=0;n<list.size();n++){
			list.get(n).put("state", PROCEDURCES.NORMAL_FLAG);
		}
		int[]  rest = iPutScheduleLogDao.savePutLog(list, userid);
		return _result;
	}
	
	@Override
	public int cancelPut(String ids,String userid) {
		// TODO Auto-generated method stub
		List list = null;
		if(StringUtils.isEmpty(ids)){
			return 0;
		}else{
			String[] tempstr = ids.split(",");
			list = Arrays.asList(tempstr);
		}
		int _result = delete(userid, list);
		return _result;
	}
	public int delete(String userid, List list) {
		int _result=iPutScheduleDao.deletePutState(list);
		List<JSONObject> result = iPutScheduleDao.queryPut(list);
		for(int i=0;i<result.size();i++){
			result.get(i).accumulate("state",PROCEDURCES.CALNEL_FLAG);
		}
		int[]  rest = iPutScheduleLogDao.savePutLog(result, userid);
		return _result;
	}
	public int deleteAll(String userid, List<JSONObject> list) {
		int _result=0;
		List ali = new ArrayList();
		
		for(int k=0;k<list.size();k++){
			ali.add(JSONUtils.getString(list.get(k),"id"));
		}
		System.out.println("开始删除");
		long stime=System.currentTimeMillis();
		_result=iPutScheduleDao.deletePutState(ali);
		long etime=System.currentTimeMillis();
		System.out.println("删除成功操作 一共耗时："+(etime-stime));		
		
		for(int n=0;n<list.size();n++){
			list.get(n).put("state", PROCEDURCES.CALNEL_FLAG);
		}
		int[]  rest = iPutScheduleLogDao.savePutLog(list, userid);
		System.out.println("记录成功操作 一共耗时："+(System.currentTimeMillis()-etime));
		return _result;
	}
}
