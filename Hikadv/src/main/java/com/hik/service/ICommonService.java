package com.hik.service;


import java.util.List;


public interface ICommonService {
	/**
	 * 查询模版
	 * @param cycid 模版周期
	 * @return
	 */
	public List queryAllModel(String cycid);
	
	/**
	 * 查询模版内容的广告位数
	 */
	public String queryModelNum(String modelid);
	/**
	 * 查询所有的广告集
	 * @return
	 */
	public List queryAdvgroup();
	 /**
	  * 查询所有的广告
	  * @return
	  */
	 public List queryAllAdvert(String userid,String id,String pid);
	 /**
	  * 查询所有的公众号
	  * @return
	  */
	 public List queryAllMmpp(String userid,String id,String pid);
	 
	 /**
	  * 查询播放策略
	  * @return
	  */
	 public List queryplayfun();
	 /**
	  * 查询认证周期
	  * @return
	  */
	public List queryAuthCyc();
	
	/**
	 * 查询皮肤设置
	 * @return
	 */
	public List querySKIN();
	/**
	 * 大类广告分类
	 * @return
	 */
	public List getPropertys();
	/**
	 * 小类广告分类
	 * @param id 大类广告分类ID
	 * @return
	 */
	public List getProperty(String id);
	/**
	 * 查询所有的时间片段
	 * @return
	 */
	public List querytimeSet();
	/**
	 * 查询线路信息
	 */
	public List getRoute(String linename);
	
	/**
	 * 查询线路信息
	 */
	public List queryRoute(String id);
	
	/**
	 * 查询线路信息
	 */
	public List queryAllRoute();
	/**
	 * 根据路线ID 查询路线上所有的车辆信息
	 * @param lineId3
	 * @param busName
	 * @return
	 */
	public List queryBusInfo(String lineId,String busName);
	/**
	 * 根据路线ID 查询路线上所有的车辆信息
	 * @param lineId
	 * @return
	 */
	public List queryBusInfo();
	
//	/**
//	 * 查询全部的设备信息
//	 * @return
//	 */
//	public List queryDeviceInfo();
	/**
	 * 根据车辆查询设备信息
	 * @param busid
	 * @return
	 */
	public List queryDeviceInfo(String busid);
	
	/**
	 * 查询产品
	 * @param cycid  认证周期ID
	 * @param modelid 模型ID
	 * @return
	 */
	public List queryProduct(String cycid,String modelid);
	/**
	 * 优先事件 
	 * @return
	 */
	public List queryProEvent();
	/**
	 * 优先终端型号
	 * @return
	 */
	public List queryProClientType();
	/**
	 * 优先终端系统
	 * @return
	 */
	public List queryProClientSys();
	/**
	 * 优先终端手机号
	 * @return
	 */
	public List queryProPhone();
	/**
	 * 优先地址
	 * @return
	 */
	public List queryProAddress();
	/**
	 * 优先地址
	 * @return
	 */
	public List queryProTime();
	
	public List queryPutFlag();
	
	public int setPutFlag(String flag);
	
	public List getAllUser();
	/**
	 * 查询线路信息
	 */
	public List queryAllCorp();
	
	
	/**
	 * 查询设备名称是否有重复
	 * @param name
	 * @return
	 */
	public List queryIfhaveDevice(String equipmentid,String apmac);
	/**
	 * 查询车辆名称是否有重复
	 * @param name
	 * @return
	 */
	public List queryIfhaveBusInfo(String name);
	/**
	 * 查询线路名称是否有重复
	 * @param name
	 * @return
	 */
	public List queryIfhaveRouteInfo(String name);
	/**
	 * 查询公司名称是否有重复
	 * @param name
	 * @return
	 */
	public List queryIfhaveCorpInfo(String name);
	
}
