package com.hik.framework.dao;

import java.util.List;

import org.apache.commons.logging.Log;

public interface ILoginDao {
	/**
	 * 
	 * @param userName �û���
	 * @param password ����
	 * @return �����û��������Ƿ���ȷ
	 */
	public List getIfLoginInfo(String userName,String password,Log log);
	
	
	/**
	 * 
	 * @param userName �û���
	 * @return �����û����Ƿ����
	 */
	public boolean getUserInfo(String userName);
}
