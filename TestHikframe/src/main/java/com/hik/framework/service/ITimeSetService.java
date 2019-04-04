package com.hik.framework.service;

import java.util.List;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.app.entity.TIMESETTING;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ITimeSetService {
		/**
		 * @return
		 */
			public List getTimeSet();
			
		/**
		 * 
		 * @param user ��Ҫ�༭������
		 */
			public int editTimeSet(TIMESETTING user);
		/**
		 * 
		 * @param user ��ӵ�����
		 */
			int addTimeSet(TIMESETTING user);
		/**
		 * 
		 * @param usid ɾ������������
		 */
			int deleteTimeSet(String[] id);
			/**
			 * 
			 * @param smscontent
			 * @return
			 */
			int udpateSMS(String smscontent);
			/**
			 * ��ѯ��������
			 * @return
			 */
			List querySMS();
			/**
			 * ��ѯƤ������
			 * @return
			 */
			List querySKIN();
			/**
			 * ����Ƥ��
			 * @return
			 */
			int insertSKIN(String id,String skinname,String name,String descr);
			/**
			 * ɾ��Ƥ��
			 * @param id
			 * @return
			 */
			int delelte(String[] id);
			
			List getSKINById(String id);
			/**
			 * ��ѯģ��������Ϣ
			 * @return
			 */
			public List queryModelPro();
			/**
			 * ɾ��ģ������ֵ
			 * @param id
			 * @return
			 */
			public int deleteModelPro(String[] id);
			/**
			 * ��ѯ�������
			 * @return
			 */
			public List queryAdvPro(String pid,String leve);
			/**
			 * ɾ�������������
			 * @param id
			 * @return
			 */
			public int deleteAdvpro(String[] id);
			/**
			 * ����ģ������
			 * @param cid ID
			 * @param name ����
			 * @param descr ����
			 * @param userid ������
			 * @return
			 */
			public int saveModelPro(String id,String cid,String name,String descr,String userid);
			/**
			 * �������ģ������
			 * @param id ID
			 * @param pid ����ID
			 * @param name ����
			 * @param descr ����
			 * @param userid ������
			 * @return
			 */
			public int saveadvPro(String id,String pid,String name,String userid,String leve);
			/**
			 * 
			 * @param id
			 * @param cid
			 * @param name
			 * @param interval
			 * @param descr
			 * @param userid
			 * @return
			 */
			public int savePutStragerPro(String id,String cid,String name,String interval,String descr,String userid);
			
			public List queryPutStragerPro();
			
			public int deletePutStragerPro(String id);
			
			/**
			 * �����û���֤��ʽ
			 * @param state
			 * @return
			 */
			public int updateUserAuth(String state);
			
			public List queryUserAuth();
}
