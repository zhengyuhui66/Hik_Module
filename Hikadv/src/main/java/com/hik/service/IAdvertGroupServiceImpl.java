package com.hik.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.ADVGROUP;
import com.hik.dao.BaseService;
import com.hik.dao.IAdvertGroupDao;
import com.hik.dao.IGetAdvertDao;
import com.hik.framework.utils.Page;


import net.sf.json.JSONObject;


@Service
@Transactional
public class IAdvertGroupServiceImpl  extends BaseService  implements IAdvertGroupService{
	@Autowired
	private IAdvertGroupDao iAdvertGroupDao;

	public Page getAdvertgroup(int start, int limit,String searchStr) {
		// TODO Auto-generated method stub
		return iAdvertGroupDao.getAdvertgroup(start, limit, searchStr);
	}

	@Override
	public int saveOrupdateAdvertgroup(ADVGROUP advgroup) {
		// TODO Auto-generated method stub
		int result=0;
		String id=advgroup.getId();
		if(StringUtils.isEmpty(id)){
			result=iAdvertGroupDao.saveAdvertgroup(advgroup);
		}else{
			result=iAdvertGroupDao.updateAdvertgroup(advgroup);
		}
		return result;
	}

	@Override
	public int deleteAdvertgroup(String[] ids) {
		// TODO Auto-generated method stub
		return iAdvertGroupDao.deleteAdvertgroup(ids);
	}

	@Override
	public List queryGroupPutproduct(String[] ids) {
		// TODO Auto-generated method stub
		return iAdvertGroupDao.queryGroupPutproduct(ids);
	}



}
