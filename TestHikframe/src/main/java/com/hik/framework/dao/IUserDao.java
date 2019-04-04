package com.hik.framework.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IUserDao{
	/**
	 * 
	 * @param start ��ѯ��ʼҳ
	 * @param limit ��ѯÿҳ����
	 * @return
	 */
		public Page getUser(int start,int limit,String searchStr,String usid);
		
	/**
	 * 
	 * @param user ��Ҫ�༭������
	 */
		public int editUser(SYS_USER user);
	/**
	 * 
	 * @param user ��ӵ�����
	 */
		int addUser(SYS_USER user,int puserid);
	/**
	 * 
	 * @param usid ɾ������������
	 */
		int deleteUser(int userid);
	/**
	 * 
	 * @param usid ɾ���Ķ����������
	 */
		int deleteUser(int[] userid);
	/**
	 * 
	 * @param usid ����
	 * @return��ȡ��������
	 */
		List getRoleById(String trid);
		List getRoleByUserId(String userId);
		/**
		 * 
		 * @param usid �û�ID
		 * @param trid �û���ɫ
		 * @param pusid �û������ɫ
		 * @return �����û���ɫ��Ϣ
		 */
		public int addRoleUser(int userid,String trid);
		
		public List getUser(int userid);
		/**
		 * ��ѯ���еĸ����û�
		 * @param userId
		 * @return
		 */
		public List getParentUser(int userId);
		/**
		 * ��ѯ��ǰ�û��ĸ����û�ID
		 * @param userId
		 * @return
		 */
		public List getPUserId(int userId);
		
		public List getUserId();
		
		/**
		 * 
		 * @param username
		 * @return
		 */
		public List valiateAdd(String username);

		/**
		 * 
		 * @param userid
		 * @param username
		 * @return
		 */
		public List valiateUpdate(String userid, String username);
		
		
		public int deleteUserRole(int userid);

//		/**
//		 * 
//		 * @param userid
//		 * @return
//		 */
//		public List getUserInfo(int userid);
//		/**
//		 * 
//		 * @param pusid
//		 * @param trid
//		 * @return
//		 */
//		public int addUserRole(int usid,int pusid, int trid);
}
