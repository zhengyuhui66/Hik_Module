package com.hik.framework.service;

import java.util.List;

import org.apache.commons.logging.Log;

public interface ILoginService {
	/**
	 * 
	 * @param userName �û���
	 * @param password ����
	 * @return �����û��������Ƿ���ȷ
	 */
	public List getIfLoginInfo(String userId,String password,Log log);
	
	
	/**
	 * 
	 * @param userName �û���
	 * @return �����û����Ƿ����
	 */
	public boolean getUserInfo(String userId);
}
