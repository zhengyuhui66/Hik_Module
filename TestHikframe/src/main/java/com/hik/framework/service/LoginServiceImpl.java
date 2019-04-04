package com.hik.framework.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.framework.dao.ILoginDao;

@Service
@Transactional(readOnly=true)
public class LoginServiceImpl implements ILoginService{
	//Log log = LogFactory.getLog(Object.class); 
	@Autowired
	private ILoginDao iLoginDao;
	
	public List getIfLoginInfo(String user_name, String password,Log log) {
		// TODO Auto-generated method stub
		log.info("======>>进入登陆，开始登陆");
		System.out.println(")))))))))======>>进入登陆，开始登陆");
		return iLoginDao.getIfLoginInfo(user_name, password,log);
	}

	public boolean getUserInfo(String user_name) {
		// TODO Auto-generated method stub
		return iLoginDao.getUserInfo(user_name);
	}

}
