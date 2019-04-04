package com.hik.service;

import java.io.File;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;
@Deprecated
public interface IAdvertPlanService {
//	/**
//	 * 查询所有的路线信息
//	 * @return
//	 */
//	public List queryRoadInfo();
//	/**
//	 * 根据路线ID 查询路线上所有的车辆信息
//	 * @param lineId
//	 * @return
//	 */
//	public List queryBusInfo(String lineId);
////	/**
////	 * 查询所有的模型
////	 * @return
////	 */
////	public List queryAllModel(String cycid);
////	
//	public int updateRouteAdvertPlan(String stime,String etime,String routeId,String modelId,String modeAdvertid) throws InterruptedException, ExecutionException;
//	public int updateLoginedRouteAdvertPlan(String stime,String etime,String routeId,String modelId,String modeAdvertid) throws InterruptedException, ExecutionException;
//	/**
//	 * 更新前车辆广告
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param modelId 模型ID
//	 * @param busId 车辆ID 数组字符串
//	 * @return
//	 */
//	 public int updateAdvertPlan(String stime,String etime,String modelId,String busId,String modelmodeId,String advertId) throws InterruptedException, ExecutionException ;
//		/**
//		 * 登陆后车辆广告
//		 * @param stime 开始时间
//		 * @param etime 结束时间
//		 * @param modelId 模型ID
//		 * @param busId 车辆ID 数组字符串
//		 * @return
//		 */
//	 
//	 public int updateLoginedAdvertPlan(String stime, String etime, String modelId, String busId,String modelmodeId,String advertId) throws InterruptedException, ExecutionException ;
//	 
//	 /**
//	  * 查看历史投放记录
//	  * @param busid 车辆ID
//	  * @return
//	  */
//	 public List getBusAdvertPlanHistory(String busid);
//	 /**
//	  * 登陆后查看历史投放记录
//	  * @param busid
//	  * @return
//	  */
//	 public List getBusLoginedAdvertPlanHistory(String busid);
//	 /**
//	  * 根据模型查模块
//	  * @param modelid
//	  * @return
//	  */
//	 public List queryModelModeById(String modelid);
//	
//	 /**
//	  * 车辆ID
//	  * @return
//	  */
//	 public int cancelPutAdvert(List busids);
//	 
//	 /**
//	  * 车辆ID
//	  * @return
//	  */
//	 public int cancelPutLoginedAdvert(List busids);
//	 
//	 
//	 /**
//	  * 车辆ID
//	  * @return
//	  */
//	 public int pausePutAdvert(List busids,String value);
//	 
//	 /**
//	  * 车辆ID
//	  * @return
//	  */
//	 public int pausePutLoginedAdvert(List busids,String value);
//	 
//	 /**
//	  * 根据路线查询所有的车辆
//	  * 
//	  * @param routeids
//	  * @return
//	  */
//	 public List queryBusidByRouteIds(List routeids);
}
