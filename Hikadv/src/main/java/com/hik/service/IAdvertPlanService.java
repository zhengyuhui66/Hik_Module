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
//	 * ��ѯ���е�·����Ϣ
//	 * @return
//	 */
//	public List queryRoadInfo();
//	/**
//	 * ����·��ID ��ѯ·�������еĳ�����Ϣ
//	 * @param lineId
//	 * @return
//	 */
//	public List queryBusInfo(String lineId);
////	/**
////	 * ��ѯ���е�ģ��
////	 * @return
////	 */
////	public List queryAllModel(String cycid);
////	
//	public int updateRouteAdvertPlan(String stime,String etime,String routeId,String modelId,String modeAdvertid) throws InterruptedException, ExecutionException;
//	public int updateLoginedRouteAdvertPlan(String stime,String etime,String routeId,String modelId,String modeAdvertid) throws InterruptedException, ExecutionException;
//	/**
//	 * ����ǰ�������
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param modelId ģ��ID
//	 * @param busId ����ID �����ַ���
//	 * @return
//	 */
//	 public int updateAdvertPlan(String stime,String etime,String modelId,String busId,String modelmodeId,String advertId) throws InterruptedException, ExecutionException ;
//		/**
//		 * ��½�������
//		 * @param stime ��ʼʱ��
//		 * @param etime ����ʱ��
//		 * @param modelId ģ��ID
//		 * @param busId ����ID �����ַ���
//		 * @return
//		 */
//	 
//	 public int updateLoginedAdvertPlan(String stime, String etime, String modelId, String busId,String modelmodeId,String advertId) throws InterruptedException, ExecutionException ;
//	 
//	 /**
//	  * �鿴��ʷͶ�ż�¼
//	  * @param busid ����ID
//	  * @return
//	  */
//	 public List getBusAdvertPlanHistory(String busid);
//	 /**
//	  * ��½��鿴��ʷͶ�ż�¼
//	  * @param busid
//	  * @return
//	  */
//	 public List getBusLoginedAdvertPlanHistory(String busid);
//	 /**
//	  * ����ģ�Ͳ�ģ��
//	  * @param modelid
//	  * @return
//	  */
//	 public List queryModelModeById(String modelid);
//	
//	 /**
//	  * ����ID
//	  * @return
//	  */
//	 public int cancelPutAdvert(List busids);
//	 
//	 /**
//	  * ����ID
//	  * @return
//	  */
//	 public int cancelPutLoginedAdvert(List busids);
//	 
//	 
//	 /**
//	  * ����ID
//	  * @return
//	  */
//	 public int pausePutAdvert(List busids,String value);
//	 
//	 /**
//	  * ����ID
//	  * @return
//	  */
//	 public int pausePutLoginedAdvert(List busids,String value);
//	 
//	 /**
//	  * ����·�߲�ѯ���еĳ���
//	  * 
//	  * @param routeids
//	  * @return
//	  */
//	 public List queryBusidByRouteIds(List routeids);
}
