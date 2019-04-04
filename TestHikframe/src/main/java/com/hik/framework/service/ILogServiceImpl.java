package com.hik.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.framework.dao.ILogDao;
import com.hik.framework.dao.ILoginDao;
import com.hik.framework.utils.Page;

@Service
@Transactional(readOnly=true)
public class ILogServiceImpl implements ILogService{
	@Autowired
	private ILogDao iLogDao;

	public int insertLogInfo(String userId, String content, String curd,String aopMethod) {
		// TODO Auto-generated method stub
		return iLogDao.insertLogInfo(userId, content, curd,aopMethod);
	}

	public Page getLogInfo(String condition,int start,int limit) {
		// TODO Auto-generated method stub
		return iLogDao.getLogInfo(condition,start,limit);
	}


}
