package com.hik.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseService;
import com.hik.framework.utils.Page;
import com.hik.mobile.dao.IAdvertQueryDao;
import com.hik.mobile.dao.ISchedulDao;

import net.sf.json.JSONObject;

@Service
@Transactional
public class SchedulServiceImpl extends BaseService implements ISchedulService {
	@Autowired
	private ISchedulDao iSchedulDao;
	
	@Override
	public List<JSONObject> getSchedul(String startDate, String endDate) {
		// TODO �Զ����ɵķ������
		return iSchedulDao.getSchedul(startDate, endDate);
	}

	@Override
	public List<JSONObject> getSchedulByDate(String Date) {
		// TODO �Զ����ɵķ������
		return iSchedulDao.getSchedulByDate(Date);
	}

	@Override
	public List<JSONObject> getAllLine() {
		// TODO �Զ����ɵķ������
		return iSchedulDao.getAllLine();
	}

	@Override
	public List<JSONObject> getCarByLine(String lineId) {
		// TODO �Զ����ɵķ������
		return iSchedulDao.getCarByLine(lineId);
	}

	@Override
	public List<JSONObject> getAllCar() {
		// TODO �Զ����ɵķ������
		return iSchedulDao.getAllCar();
	}

	@Override
	public Page getSchedulPut4Mobile(int start, int limit, String stime, String etime, String[] timeslice,
			String[] periodArr, String routeid, String busid, String userid,String orderby) {
		// TODO �Զ����ɵķ������
		return iSchedulDao.getSchedulPut4Mobile(start, limit, stime, etime, timeslice, periodArr, routeid, busid, userid,orderby);
	}
}
