package com.hik.framework.service;

import java.util.List;

import com.hik.framework.utils.Page;

public interface ILogService {
	public int insertLogInfo(String userId,String content,String curd,String aopMethod);
	
	public Page getLogInfo(String condition,int start,int limit);
}
