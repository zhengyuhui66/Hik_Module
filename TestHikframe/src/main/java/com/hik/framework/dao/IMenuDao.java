package com.hik.framework.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hik.app.entity.SYS_RIGHT;

import net.sf.json.JSONObject;

public interface IMenuDao{
	/**
	 * 
	 * @param userName �û���
	 * @param password ����
	 * @return �����û��������Ƿ���ȷ
	 */
	public List getMenuInfo(String userName);
	
	/**
	 * 
	 * @param userName �û���
	 * @param password ����
	 * @return ������༭����
	 */
	public List getEditMenuInfo(String userName);
	/**
	 * ����Ȩ�ޱ�����
	 * @param right  ��Ҫ���µ�����
	 * @return ����������
	 */
	
	public int addMenu(SYS_RIGHT right);
//	boolean updataMenu(SYS_RIGHT right);
	/**
	 * ��ѯ���еĽ�ɫ����
	 * @return
	 */
	public List<JSONObject> getRoleInfo();
	/**
	 * 
	 * @param str
	 * @return
	 */
	public List<JSONObject> getRoleInfo(String[] str);
	
	/**
	 * 
	 * @param right Ȩ������
	 * @param entityManager ����
	 * @return ����Ȩ�ޱ�
	 */
	public int saveAndFlush(SYS_RIGHT right);
	/**
	 * 
	 * @param rrid Ȩ��ID
	 * @param trid ��ɫID
	 * @param entityManager ����
	 * @return ���½�ɫȨ�ޱ�
	 */
	public int saveAndFlushRightRole(String rrid,String trid);
	
	/**
	 * 
	 * @param roles����Ҫ���������ķ���
	 * @return
	 */
	public int addRightRole(String rrid,String role);
	/**
	 * 
	 * @param rrid Ȩ��ID
	 * @param trid ��ɫID
	 * @param entityManager ����
	 * @return ɾ����ɫȨ�ޱ�
	 */
	public int deleteRightRole(String rrid, String trid);
	/**
	 * ĳ���͵���Ȩ��ӵ����
	 * @param rrid
	 * @return
	 */
	public List getRightRoles(String rrid);
	/**
	 * 
	 * @param description ��������
	 * @return ��ǰ�û��Ƿ���Ȩ��
	 */

	public boolean getHaveRight(String description,String UserId);
	
	
	public int updatepword(String userid,String newpword);
	
	public List getSeq_sysrightseq();
	
}
