package com.hik.framework.service;

import java.util.List;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IUserService {
	/**
	 * 
	 * @param start
	 *            ��ѯ��ʼҳ
	 * @param limit
	 *            ��ѯÿҳ����
	 * @return
	 */
	public Page getUser(int start, int limit, String searchStr, String usid);

	/**
	 * 
	 * @param user
	 *            ��Ҫ�༭������
	 */
	public int editUser(SYS_USER user);

	/**
	 * 
	 * @param user
	 *            ��ӵ�����
	 */
	int addUser(SYS_USER user, int puserid) throws Exception;

	/**
	 * 
	 * @param usid
	 *            ɾ������������
	 */
	int deleteUser(int userid);

	/**
	 * 
	 * @param usid
	 *            ɾ������������
	 */
	int deleteUser(int[] userid);

	/**
	 * @param _trid
	 * @param usid
	 *            ����
	 * @return��ȡ��������
	 */
	List getRoleById(String _trid,String userid);

	/**
	 *
	 * @param usid
	 *            �û�ID
	 * @param pusid
	 *            �����û�ID
	 * @param trid
	 *            ��ɫID
	 * @return
	 */
	public int addUserRole(int userid, String[] trid);

	/**
	 * ��ѯ�²��������
	 * 
	 * @param userid
	 * @return
	 */
	public List getUser(int userid);

	/**
	 * ��ѯ���еĸ����û�
	 * 
	 * @param userId
	 * @return
	 */
	public List getParentUser(int userId);

	/**
	 * ��ѯ��ǰ�û��ĸ���ID
	 * 
	 * @param userId
	 * @return
	 */
	public List getPUserId(int userId);

	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean valiateAdd(String username);

	/**
	 * 
	 * @param userid
	 * @param username
	 * @return
	 */
	public boolean valiateUpdate(String userid, String username);
}
