package com.hik.framework.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hik.app.entity.SYS_RIGHT;

import net.sf.json.JSONObject;

public interface IMenuService {
	/**
	 * 
	 * @param userName �û���
	 * @param password ����
	 * @return �����û��������Ƿ���ȷ
	 */
	public List getIMenuInfo(String userid);
	
	/**
	 * 
	 * @param userName �û���
	 * @param password ����
	 * @return ������Ҫ�༭����
	 */
	public List getIEditMenuInfo(String userid);
	/**
	 * ���»�����Ȩ�ޱ�����
	 * @param right  ��Ҫ���»�����������
	 * @return ���»�����������
	 */
	public int updataOrAddMenu(SYS_RIGHT right,JSONObject json);
	/**
	 * ���������Ƿ�ɹ�
	 * @param userid �û���
	 * @param newpword ����
	 * @return
	 */
	public boolean updatepword(String userid,String newpword);
	/**
	 * ���»�����Ȩ�ޱ�����
	 * @param right  ��Ҫ���»�����������
	 * @return ���»�����������
	 */
	/**
	 * ɾ��Ȩ�޸�
	 * @param rrid
	 */
	void deleteMenu(String rrid);
	/**
	 * 
	 */
	int addMenu(SYS_RIGHT right,String[] roles);
	/**
	 * �������еĽ�ɫ��Ϣ
	 * @return
	 */
	public List<JSONObject> getRoleInfo();
	
	/**
	 * ĳ���͵�Ȩ��ӵ����
	 */
	public List getRightRoles(String rrid);
	
	/**
	 * 
	 * @param description ��������
	 * @return ��ǰ�û��Ƿ���Ȩ��
	 */

	public boolean getHaveRight(String description,HttpServletRequest request);
	
//	/**
//	 * ����Ȩ�ޱ�����
//	 * @param right  ��Ҫ����������
//	 * @return ����������
//	 */
//	void addMenu(SYS_RIGHT right);
//	public int addRightRole(String[] roles);
	
}
