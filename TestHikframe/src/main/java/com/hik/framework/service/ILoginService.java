package com.hik.framework.service;

import java.util.List;

import org.apache.commons.logging.Log;

public interface ILoginService {
	/**
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 检验用户名密码是否正确
	 */
	public List getIfLoginInfo(String userId,String password,Log log);
	
	
	/**
	 * 
	 * @param userName 用户名
	 * @return 检验用户名是否存在
	 */
	public boolean getUserInfo(String userId);
}
