package com.hik.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseService;
import com.hik.mobile.dao.IMainSumDao;
import com.hik.mobile.entity.CONVERT_RATE;

import net.sf.json.JSONObject;

@Service
@Transactional
public class MainSumServiceImpl extends BaseService implements IMainSumService {
	@Autowired
	private IMainSumDao iMainSumDao;

	@Override
	public List<JSONObject> getTolUser(String userid) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getTolUser(userid);
	}

	@Override
	public List<JSONObject> getTotalAdServing(String user) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getTotalAdServing(user);
	}

	@Override
	public CONVERT_RATE getConverRate(String userid, int coutType) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getConverRate(userid, coutType);
	}

	@Override
	public List<JSONObject> getAdvCount(String userid) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getAdvCount(userid);
	}

	@Override
	public List<JSONObject> getChartOfSurfNetSum(String userid, String startDate, String endDate) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getChartOfSurfNetSum(userid, startDate, endDate);
	}

	@Override
	public List<JSONObject> getChartOfAdDelv(String userid, String startDate, String endDate) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getChartOfAdDelv(userid, startDate, endDate);
	}

	@Override
	public List<JSONObject> getUpdateInfo(String type) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getUpdateInfo(type);
	}

	@Override
	public List<JSONObject> getAdUsersHisTrend(String sDate, String eDate, String userid) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getAdUsersHisTrend(sDate, eDate, userid);
	}

	@Override
	public List<JSONObject> getAdExpoHisTrend(String sDate, String eDate, String userid) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getAdExpoHisTrend(sDate, eDate, userid);
	}

	@Override
	public List<JSONObject> getAdClickHisTrend(String sDate, String eDate, String userid) {
		// TODO 自动生成的方法存根
		return iMainSumDao.getAdClickHisTrend(sDate, eDate, userid);
	}
	
	
	
}
