package com.hik.service;

import java.util.List;
import java.util.Set;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IGetAdvertService {
	/**
	 * �õ���ǰ�Ĺ��
	 * @return
	 */
	public List<JSONObject> getAdvert(String busId);
	/**
	 * 
	 * @param busId
	 * @return
	 */
	public List<JSONObject> getLoginedAdvert(String busId);
	/**
	 * ��ҳ�õ����еĹ��
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getAdvert(int start,int limit,String searchStr);
	
	/**
	 * 
	 * @param modelid
	 * @param json
	 * @return
	 */
	public List getAdvertView(String modelid,JSONObject json);
	/**
	 * �鿴�жϹ�������Ƿ��ظ�
	 * @param name
	 * @return
	 */
	public boolean getrepeatAdvertName(String name);
	/**
	 * �õ����е�����
	 * @return
	 */
	public List getMater(String materTypeid,String materSizeid,String materCreatorid);
	/**
	 * �������������Ϣ
	 * @param materId ����ID
	 * @param adverturl  ����ַ
	 * @param description  �������
	 * @param advertname �������
	 * @return
	 */
	public int saveAdvert(String materId,String adverturl,String description,String advertname,String userid,Set<String> putconIdSet,String advertpropertys,String advertproperty);
	
	/**
	 * ���¹����Ϣ
	 * @param adverid ����ID
	 * @param materId ����ID
	 * @param adverturl  ����ַ
	 * @param description  �������
	 * @param advertname �������
	 * @return
	 */
	public int  updateAdvert(String adverid,String materId,String adverturl,String description,String advertname,Set<String> putconIdSet,String advertpropertys,String advertproperty);
	/**
	 * ɾ�����
	 * @param advertids ��Ҫɾ���Ĺ��
	 * @return
	 */
	public int deleteAdvert(String[] advertids);
	public List getAdvGrounpById(String[] advertids);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List getConditionByAdvid(String id);
	
	
	public List getAdvBymaterId(String[] materid);

}
