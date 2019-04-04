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
		// TODO �Զ����ɵķ������
		return iMainSumDao.getTolUser(userid);
	}

	@Override
	public List<JSONObject> getTotalAdServing(String user) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getTotalAdServing(user);
	}

	@Override
	public CONVERT_RATE getConverRate(String userid, int coutType) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getConverRate(userid, coutType);
	}

	@Override
	public List<JSONObject> getAdvCount(String userid) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getAdvCount(userid);
	}

	@Override
	public List<JSONObject> getChartOfSurfNetSum(String userid, String startDate, String endDate) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getChartOfSurfNetSum(userid, startDate, endDate);
	}

	@Override
	public List<JSONObject> getChartOfAdDelv(String userid, String startDate, String endDate) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getChartOfAdDelv(userid, startDate, endDate);
	}

	@Override
	public List<JSONObject> getUpdateInfo(String type) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getUpdateInfo(type);
	}

	@Override
	public List<JSONObject> getAdUsersHisTrend(String sDate, String eDate, String userid) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getAdUsersHisTrend(sDate, eDate, userid);
	}

	@Override
	public List<JSONObject> getAdExpoHisTrend(String sDate, String eDate, String userid) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getAdExpoHisTrend(sDate, eDate, userid);
	}

	@Override
	public List<JSONObject> getAdClickHisTrend(String sDate, String eDate, String userid) {
		// TODO �Զ����ɵķ������
		return iMainSumDao.getAdClickHisTrend(sDate, eDate, userid);
	}
	
	
	
}
