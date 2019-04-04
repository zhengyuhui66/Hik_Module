package com.hik.dao;

import java.util.List;


public interface ICommonDao {
	/**
	 * ��ѯģ��
	 * @param cycid ģ������
	 * @return
	 */
	public List queryAllModel(String cycid);
	
	/**
	 * ��ѯģ�����ݵĹ��λ��
	 */
	public List queryModelNum(String modelid);
	/**
	 * ��ѯ���еĹ�漯
	 * @return
	 */
	public List queryAdvgroup();
	 /**
	  * ��ѯ���еĹ��
	  * @return
	  */
	 public List queryAllAdvert(String userid,String id,String pid);
	 
	 /**
	  * ��ѯ���еĹ��
	  * @return
	  */
	 public List queryAllMmpp(String userid,String id,String pid);
	 
	/**
	 * ��ѯ���Ų���
	 * @return
	 */
	public List queryplayfun();
	/**
	 * �� ѯ��֤����
	 * @return
	 */
	public List queryAuthCyc();
	/**
	 * ��ѯƤ������
	 * @return
	 */
	public  List querySKIN();
	/**
	 * ���������
	 * @return
	 */
	public List getPropertys();
	/**
	 * С�������
	 * @param id ���������ID
	 * @return
	 */
	public List getProperty(String id);
	/**
	 * ��ѯ���е�ʱ��Ƭ��
	 * @return
	 */
	public List querytimeSet();
	
	
	/**
	 * ��ѯ��·��Ϣ
	 */
	public List getRoute(String linename);
	
	public List queryRoute(String id);
	public List queryAllRoute();
	/**
	 * ����·��ID ��ѯ·�������еĳ�����Ϣ
	 * @param lineId
	 * @param busName
	 * @return
	 */
	public List queryBusInfo(String lineId,String busName);
	/**
	 * ����·��ID ��ѯ·�������еĳ�����Ϣ
	 * @param lineId
	 * @return
	 */
	public List queryBusInfo();
	
//	/**
//	 * ��ѯȫ�����豸��Ϣ
//	 * @return
//	 */
//	public List queryDeviceInfo();
	/**
	 * 
	 * @param busid
	 * @return
	 */
	public List queryDeviceInfo(String busid);
	/**
	 * ��ѯ��Ʒ
	 * @param cycid  ��֤����ID
	 * @param modelid ģ��ID
	 * @return
	 */
	public List queryProduct(String cycid,String modelid);
	/**
	 * ��ѯ���е�AP
	 * @return
	 */
	public List queryAp();
	
	/**
	 * �����¼� 
	 * @return
	 */
	public List queryProEvent();
	/**
	 * �����ն��ͺ�
	 * @return
	 */
	public List queryProClientType();
	/**
	 * �����ն�ϵͳ
	 * @return
	 */
	public List queryProClientSys();
	/**
	 * �����ն��ֻ���
	 * @return
	 */
	public List queryProPhone();
	/**
	 * ���ȵ�ַ
	 * @return
	 */
	public List queryProAddress();
	/**
	 * ����ʱ��
	 * @return
	 */
	public List queryProTime();
	/**
	 * ��ѯ
	 * @return
	 */
	public List queryPutFlag();
	
	public int setPutFlag(String flag);
	
	
	public List getAllUser();
	
	public List queryAllCorp();
	
	public List getDeviceOnlineTimeout();
	
	
	/**
	 * ��ѯ�豸�����Ƿ����ظ�
	 * @param name
	 * @return
	 */
	public List queryIfhaveDevice(String equipmentid,String apmac);
	/**
	 * ��ѯ���������Ƿ����ظ�
	 * @param name
	 * @return
	 */
	public List queryIfhaveBusInfo(String name);
	/**
	 * ��ѯ��·�����Ƿ����ظ�
	 * @param name
	 * @return
	 */
	public List queryIfhaveRouteInfo(String name);
	/**
	 * ��ѯ��˾�����Ƿ����ظ�
	 * @param name
	 * @return
	 */
	public List queryIfhaveCorpInfo(String name);
	
}
