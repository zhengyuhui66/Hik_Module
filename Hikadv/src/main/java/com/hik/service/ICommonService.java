package com.hik.service;


import java.util.List;


public interface ICommonService {
	/**
	 * ��ѯģ��
	 * @param cycid ģ������
	 * @return
	 */
	public List queryAllModel(String cycid);
	
	/**
	 * ��ѯģ�����ݵĹ��λ��
	 */
	public String queryModelNum(String modelid);
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
	  * ��ѯ���еĹ��ں�
	  * @return
	  */
	 public List queryAllMmpp(String userid,String id,String pid);
	 
	 /**
	  * ��ѯ���Ų���
	  * @return
	  */
	 public List queryplayfun();
	 /**
	  * ��ѯ��֤����
	  * @return
	  */
	public List queryAuthCyc();
	
	/**
	 * ��ѯƤ������
	 * @return
	 */
	public List querySKIN();
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
	
	/**
	 * ��ѯ��·��Ϣ
	 */
	public List queryRoute(String id);
	
	/**
	 * ��ѯ��·��Ϣ
	 */
	public List queryAllRoute();
	/**
	 * ����·��ID ��ѯ·�������еĳ�����Ϣ
	 * @param lineId3
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
	 * ���ݳ�����ѯ�豸��Ϣ
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
	 * ���ȵ�ַ
	 * @return
	 */
	public List queryProTime();
	
	public List queryPutFlag();
	
	public int setPutFlag(String flag);
	
	public List getAllUser();
	/**
	 * ��ѯ��·��Ϣ
	 */
	public List queryAllCorp();
	
	
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
