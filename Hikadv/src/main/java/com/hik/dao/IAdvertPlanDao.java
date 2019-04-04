package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;
@Deprecated
public interface IAdvertPlanDao {
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
////	 * 查询所有的广告
////	 * @return
////	 */
////	public List queryAllModel(String cycid);
//	/**
//	 * 更新车辆广告
//	 * @param stime 开始时间
//	 * @param etime 结束时间
//	 * @param modelId 模型ID
//	 * @param busId 车辆ID
//	 * @return
//	 */
//	 public int updateAdvertPlan(String stime,String etime,String modelId,String busId);
//		/**
//		 * 更新登陆后车辆广告
//		 * @param stime 开始时间
//		 * @param etime 结束时间
//		 * @param modelId 模型ID
//		 * @param busId 车辆ID
//		 * @return
//		 */
//	 public int updateLoginedAdvertPlan(String stime, String etime, String modelId, String busId);
//	 /**
//	  * 登陆前
//	  * 更新开始时间
//	  * @param stime
//	  * @param busId
//	  */
//	 public boolean getProduceUpdateBusId(String stime,String etime,String busId,String modelId,String modelmodeId,String advertId);
//	 
//	 /**
//	  * 登陆后
//	  * 更新开始时间
//	  * @param stime
//	  * @param busId
//	  */
//	 public boolean getLoginedProduceUpdateBusId(String stime,String etime,String busId,String modelId,String modelmodeId,String advertId);
//	 
//	 /**
//	  * 
//	  * @param routeId
//	  * @return
//	  */
//	 public List queryBusInfos(String routeId);
//	 
////	 /**
////	  * 更新结束时间
////	  * @param etime
////	  * @param busId
////	  */
////	 public void getProduceUpdateBusId_etime(String etime,String busId);
//	 /**
//	  * 查看历史投放记录
//	  * @param busid 车辆ID
//	  * @return
//	  */
//	 public List getBusAdvertPlanHistory(String busid);
//	 /**
//	  * 登陆后界面查看历史记录
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
//	 
//	 
//	 public List queryAdvidByAdvName(String advName);
//	 
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
//	 public int pausePutAdvert(String busids,String value);
//	 
//	 /**
//	  * 车辆ID
//	  * @return
//	  */
//	 public int pausePutLoginedAdvert(String busids,String value);
//	 
//	 
//	 /**
//	  * 根据多条路线查询所有的车辆
//	  * 
//	  * @param routeids
//	  * @return
//	  */
//	 public List queryBusidByRouteIds(List routeids);
}
