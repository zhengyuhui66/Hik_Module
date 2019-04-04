package com.hik.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.ICommonDao;
import com.hik.dao.IPutPlanDao;
import com.hik.dao.IPutPlanDaoImpl;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import net.sf.json.JSONObject;


@Service
@Transactional
public class IPutPlanServiceImpl  extends BaseService  implements IPutPlanService{
	public static final int maxList = 10000;
	public static final int maxsat=800;
	@Autowired
	private IPutPlanDao iPutPlanDao;
	@Autowired
	private IPutPlanDao iPutPlanDao1;
	@Autowired
	private IPutPlanDao iPutPlanDao2;
	@Autowired
	private IPutPlanDao iPutPlanDao3;
	@Autowired
	private IPutPlanDao iPutPlanDao4;
	@Autowired
	private IPutPlanDao iPutPlanDao5;
	@Autowired
	private ICommonDao icommonDao;
	
	@Override
	public List getProduct(String authid) {
		return iPutPlanDao.getProduct(authid);
	}
	
	public List<JSONObject> getBus(String roId){
//		List result=new ArrayList();
		List<JSONObject> routeList = icommonDao.queryRoute(roId);
		List<JSONObject> busList = iPutPlanDao.getBus();
		Map<String,List<JSONObject>> busMap = new HashMap<String,List<JSONObject>>();
		for(int i=0;i<busList.size();i++){
			JSONObject tempbusJson = busList.get(i);
			String routeId=JSONUtils.getString(tempbusJson, "routeid");
			List<JSONObject> tempBusJSArray=busMap.get(routeId);
			if(tempBusJSArray==null||tempBusJSArray.isEmpty()){
				List<JSONObject> tempJsonArray = new ArrayList<JSONObject>();
				tempJsonArray.add(tempbusJson);
				busMap.put(routeId, tempJsonArray);
			}else{
				tempBusJSArray.add(tempbusJson);
				busMap.put(routeId, tempBusJSArray);
			}
		}
		
		List<JSONObject> apList = icommonDao.queryAp();
		Map<String,List<JSONObject>> apMap = new HashMap<String,List<JSONObject>>();
		for(int i=0;i<apList.size();i++){
			JSONObject tempapJson = apList.get(i);
			String busid=JSONUtils.getString(tempapJson, "vehicleid");
			List<JSONObject> tempApJSArray=apMap.get(busid);
			if(tempApJSArray==null||tempApJSArray.isEmpty()){
				List<JSONObject> tempJsonArray = new ArrayList<JSONObject>();
				tempJsonArray.add(tempapJson);
				apMap.put(busid, tempJsonArray);
			}else{
				tempApJSArray.add(tempapJson);
				apMap.put(busid, tempApJSArray);
			}
		}
		
		
		for(int j=0;j<routeList.size();j++){
			JSONObject tempRouteJson=routeList.get(j);
			String routeId=JSONUtils.getString(tempRouteJson, "id");
			List<JSONObject> tempBusList = busMap.get(routeId);
			
			if(tempBusList==null){
				routeList.get(j).accumulate("leaf",true);
				routeList.get(j).accumulate("checked",false);
				continue;
			}
			routeList.get(j).accumulate("leaf",false);
			routeList.get(j).accumulate("expanded", true);
			routeList.get(j).accumulate("checked",false);
			for(int i=0;i<tempBusList.size();i++){
				JSONObject tempBusJson = tempBusList.get(i);
				String busId=JSONUtils.getString(tempBusJson, "bid");
//				System.out.println("busid:"+busId);
				List<JSONObject> tempApList = apMap.get(busId);
//				System.out.println("tempApList:"+tempApList);
				
				if(tempApList==null){
					tempBusList.get(i).accumulate("leaf", true);
					tempBusList.get(i).accumulate("checked",false);
					continue;
				}
				tempBusList.get(i).accumulate("leaf", false);
				tempBusList.get(i).accumulate("expanded", true);
				tempBusList.get(i).accumulate("checked",false);
				for(int k=0;k<tempApList.size();k++){
					tempApList.get(k).accumulate("leaf", true);
					tempApList.get(k).accumulate("checked", false);
				}
				tempBusList.get(i).accumulate("children", tempApList);
			}
			routeList.get(j).accumulate("children",tempBusList);
		}
		List<JSONObject> resultList = new ArrayList();
		JSONObject resultJson=new JSONObject();
		resultJson.accumulate("name", "全部AP");
		resultJson.accumulate("expanded", true);
		resultJson.accumulate("leaf",false);
		resultJson.accumulate("children",routeList);
		resultJson.accumulate("checked",false);
		resultList.add(resultJson);
		return resultList;
	}
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
//	@Override
//	public Object putPlan(String stime, String etime, String[] timeslices, String[] apids, JSONObject jsonObject,String userid) {
//		// TODO Auto-generated method stub
//		List timeList=getTimes(stime,etime);
//		List<Object[]> resultList=new ArrayList<Object[]>();
//		for(int a=0;a<timeList.size();a++){
//			for(int b=0;b<timeslices.length;b++){
//				for(int c=0;c<apids.length;c++){
//					Iterator iterator = jsonObject.keys();
//					while(iterator.hasNext()){
//			        	String authid = (String) iterator.next();
//			        	String product = jsonObject.getString(authid);
//			        	if(!StringUtils.isEmpty(product)){
//			        		String[] auth=authid.split("authcyc");
//				        	Object[] obj = new Object[8];
//				        	obj[0]="2";
//				        	obj[1]=timeList.get(a);
//				        	obj[2]=timeslices[b];
//				        	obj[3]=product;
//				        	obj[4]=auth[1];
//				        	obj[5]=apids[c];
//				        	obj[6]=1;
//				        	obj[7]=userid;
//				        	resultList.add(obj);
//			        	}
//					}
//				}
//			}
//		}
//		Object result=iPutPlanDao.putPlan(resultList);
//		return result;
//	}
	/**
	 * 获取两个时间的所有日期
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @return
	 */
//	public List gettestTimes(String stime,String etime){
////		System.out.println("stime:"+stime+" etime:"+etime);
//		stime = stime.substring(0,10);
//		etime = etime.substring(0,10);
//		Date sDate=DateUtil.getStrToDate(stime,"yyyy-MM-dd");
//		Date eDate=DateUtil.getStrToDate(etime,"yyyy-MM-dd");
//		List list = new ArrayList();
//		while(!sDate.equals(eDate)){
//			sDate=DateUtil.getAddaDay(sDate);
//			String stimes=DateUtil.getDateToStr(sDate,"yyyy-MM-dd");
//			list.add(stimes);
//		}
//		return list;
//	}
	@Override
	public List putPlantest(String stime, String etime, String[] timeslices, String[] apids, JSONObject jsonObject,
			String userid) {
		// TODO Auto-generated method stub
		List timeList=getTimes(stime,etime);
		List result=null;
		List apidList=Arrays.asList(apids);
		int num = apidList.size()%maxsat==0?apidList.size()/maxsat:apidList.size()/maxsat+1;
		for(int i=0;i<num;i++){
			int start=i*maxsat;
			int end= (i+1)*maxsat<apidList.size()?(i+1)*maxsat:apidList.size();
			List tempapidList = apidList.subList(start, end);
			result=iPutPlanDao.putPlanTest(timeList, timeslices, tempapidList, jsonObject);	
			if(result.size()>0){
				break;
			}
		}
		return result;
	}

//	@Override
//	public Object putPlan(String stime, String timeslices, String[] apids, JSONObject jsonObject,
//			String userid) {
//		// TODO Auto-generated method stub
//		List<Object[]> resultList=new ArrayList<Object[]>();
//				for(int c=0;c<apids.length;c++){
//					Iterator iterator = jsonObject.keys();
//					while(iterator.hasNext()){
//			        	String authid = (String) iterator.next();
//			        	String product =JSONUtils.getString(jsonObject, authid); //jsonObject.getString(authid);
//			        	if(!StringUtils.isEmpty(product)){
////			        		String[] auth=authid.split("authcyc");
//				        	Object[] obj = new Object[8];
//				        	obj[0]="2";
//				        	obj[1]=stime;
//				        	obj[2]=timeslices;
//				        	obj[3]=product;
//				        	obj[4]=authid;
//				        	obj[5]=apids[c];
//				        	obj[6]=1;
//				        	obj[7]=userid;
//				        	resultList.add(obj);
//			        	}
//					}
//		}
//		Object result=iPutPlanDao.putPlan(resultList);
//		return result;
//	}

	@Override
	public Object putPan(String[] stime, String[] timeslices, String[] apids, JSONObject jsonObject, String userid,long st) {
		// TODO Auto-generated method stub
		List<Object[]> resultList=new ArrayList<Object[]>();
		int result=0;
		for(int c=0;c<apids.length;c++){
			for(int b=0;b<stime.length;b++){
				for(int a =0;a<timeslices.length;a++){
					Iterator iterator = jsonObject.keys();
					while(iterator.hasNext()){
			        	String authid = (String) iterator.next();
			        	String product =JSONUtils.getString(jsonObject, authid); //jsonObject.getString(authid);
			        	if(!StringUtils.isEmpty(product)){
		//	        		String[] auth=authid.split("authcyc");
				        	Object[] obj = new Object[7];
//				        	obj[0]="2";
				        	obj[0]=stime[b];
				        	obj[1]=timeslices[a];
				        	obj[2]=product;
				        	obj[3]=authid;
				        	obj[4]=apids[c];
				        	obj[5]=1;
				        	obj[6]=userid;
				        	resultList.add(obj);
			        	}
					}
				}
			}
		}
		
		result=iPutPlanDao.putPanSave(resultList);
		iPutPlanDao.putPanSaveLog(resultList);
		long st2 = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+"日志:"+st2+"   用时："+(st2-st));
//		================================================
//		  ExecutorService executor = Executors.newFixedThreadPool(100);
//        List<Future<Object>> results=new ArrayList<Future<Object>>();
//        int t=resultList.size();
//        List<IPutPlanDao> ip=new ArrayList<IPutPlanDao>();
//        ip.add(iPutPlanDao);
//        ip.add(iPutPlanDao1);
//        ip.add(iPutPlanDao2);
//        ip.add(iPutPlanDao3);
//        ip.add(iPutPlanDao4);
//        ip.add(iPutPlanDao5);
//        int max =t%maxList==0?t/maxList:t/maxList+1;
//        for(int i=0;i<max;i++){
//        	List<Object[]> tempList = resultList.subList(i*maxList, (i+1)*maxList>t?t:(i+1)*maxList);
//        	 System.out.println(ip.get(i%ip.size()));
//        	 
//	        Task task = new Task(tempList,st,ip.get(i%ip.size()));
//	        Future<Object> r = executor.submit(task);
//	        results.add(r);
//        }
//        executor.shutdown();
//        System.out.println("主线程在执行任务");
//        try {
//        	int mm=0;
//        	for(Future<Object> r:results){
//        		Object rr=r.get();
//        		System.out.println("task运行结果"+rr+"===>"+(++mm));	     
//        	}
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//         
//        System.out.println("所有任务执行完毕");

//		========================================================
//		  ExecutorService executor = Executors.newFixedThreadPool(50);
//	        List<Future<Object>> results=new ArrayList<Future<Object>>();
//	        int t=resultList.size();
//	        
//	        int max =t%maxList==0?t/maxList:t/maxList+1;
//	        for(int i=0;i<max;i++){
//	        	List<Object[]> tempList = resultList.subList(i*maxList, (i+1)*maxList>t?t:(i+1)*maxList);
//		        Task task = new Task(tempList,st,iPutPlanDao);
//		        Future<Object> r = executor.submit(task);
//		        results.add(r);
//	        }
//	        executor.shutdown();
//	        System.out.println("主线程在执行任务");
//	        try {
//	        	int mm=0;
//	        	for(Future<Object> r:results){
//	        		Object rr=r.get();
//	        		System.out.println("task运行结果"+rr+"===>"+(++mm));	     
//	        	}
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        } catch (ExecutionException e) {
//	            e.printStackTrace();
//	        }
//	         
//	        System.out.println("所有任务执行完毕");
//	        ==============================
		System.out.println("服务层初始化参数结束时间:"+System.currentTimeMillis()+"用时："+(System.currentTimeMillis()-st));
//		Object result=iPutPlanDao.putPan(resultList,st);
		
//		==================================================
		return result;
	}
}
//@Transactional(propagation=Propagation.REQUIRED)
//class Task implements Callable<Object>{
//	List params;
//	long time;
//	IPutPlanDao iPutPlanDao;
//	public Task(List i,long time,IPutPlanDao iPutPlanDao){
//		this.params=i;	
//		this.time=time;
//		this.iPutPlanDao=iPutPlanDao;
//	}
//    @Override
//    public Object call() throws Exception {
////		Object result=iPutPlanDao.putPan(params,time);
//		int i=iPutPlanDao.putPanSave(params);
//		iPutPlanDao.putPanSaveLog(params);
//        return i;
//    }
//}
