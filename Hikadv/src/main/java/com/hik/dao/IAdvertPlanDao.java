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
////	 * ��ѯ���еĹ��
////	 * @return
////	 */
////	public List queryAllModel(String cycid);
//	/**
//	 * ���³������
//	 * @param stime ��ʼʱ��
//	 * @param etime ����ʱ��
//	 * @param modelId ģ��ID
//	 * @param busId ����ID
//	 * @return
//	 */
//	 public int updateAdvertPlan(String stime,String etime,String modelId,String busId);
//		/**
//		 * ���µ�½�������
//		 * @param stime ��ʼʱ��
//		 * @param etime ����ʱ��
//		 * @param modelId ģ��ID
//		 * @param busId ����ID
//		 * @return
//		 */
//	 public int updateLoginedAdvertPlan(String stime, String etime, String modelId, String busId);
//	 /**
//	  * ��½ǰ
//	  * ���¿�ʼʱ��
//	  * @param stime
//	  * @param busId
//	  */
//	 public boolean getProduceUpdateBusId(String stime,String etime,String busId,String modelId,String modelmodeId,String advertId);
//	 
//	 /**
//	  * ��½��
//	  * ���¿�ʼʱ��
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
////	  * ���½���ʱ��
////	  * @param etime
////	  * @param busId
////	  */
////	 public void getProduceUpdateBusId_etime(String etime,String busId);
//	 /**
//	  * �鿴��ʷͶ�ż�¼
//	  * @param busid ����ID
//	  * @return
//	  */
//	 public List getBusAdvertPlanHistory(String busid);
//	 /**
//	  * ��½�����鿴��ʷ��¼
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
//	 
//	 
//	 public List queryAdvidByAdvName(String advName);
//	 
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
//	 public int pausePutAdvert(String busids,String value);
//	 
//	 /**
//	  * ����ID
//	  * @return
//	  */
//	 public int pausePutLoginedAdvert(String busids,String value);
//	 
//	 
//	 /**
//	  * ���ݶ���·�߲�ѯ���еĳ���
//	  * 
//	  * @param routeids
//	  * @return
//	  */
//	 public List queryBusidByRouteIds(List routeids);
}
