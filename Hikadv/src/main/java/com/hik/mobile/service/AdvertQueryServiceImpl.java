package com.hik.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseService;
import com.hik.mobile.dao.IAdvertQueryDao;

import net.sf.json.JSONObject;


@Service
@Transactional
public class AdvertQueryServiceImpl extends BaseService implements IAdvertQueryService{
	@Autowired
	private IAdvertQueryDao iAdvertDao;
	@Override
	public List<JSONObject> getAllTemplate() {
		// TODO 自动生成的方法存根
		return iAdvertDao.getAllTemplate();
	}

	@Override
	public List<JSONObject> getSpaceByTemplate(String userid, String tempid) {
		// TODO 自动生成的方法存根
		return iAdvertDao.getSpaceByTemplate(userid, tempid);
	}

	@Override
	public List<JSONObject> getAllAd() {
		// TODO 自动生成的方法存根
		return iAdvertDao.getAllAd();
	}

	@Override
	public List<JSONObject> getAllAd(String userid) {
		// TODO 自动生成的方法存根
		return iAdvertDao.getAllAd(userid);
	}



}
