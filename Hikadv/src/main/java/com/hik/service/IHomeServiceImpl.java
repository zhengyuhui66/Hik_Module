package com.hik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseService;
import com.hik.dao.IHomeDao;

import net.sf.json.JSONObject;


@Service
@Transactional
public class IHomeServiceImpl  extends BaseService  implements IHomeService{
	@Autowired
	private IHomeDao iHomeDao;

	public List<JSONObject> getTop10ShowCountByAdv() {
		// TODO Auto-generated method stub
		return iHomeDao.getTop10ShowCountByAdv();
	}

	public List<JSONObject> getTop10ShowCountByRoute() {
		// TODO Auto-generated method stub
		return iHomeDao.getTop10ShowCountByRoute();
	}

	public List<JSONObject> getTop10ClickCountByAdv() {
		// TODO Auto-generated method stub
		return iHomeDao.getTop10ClickCountByAdv();
	}

	public List<JSONObject> getTop10ClickCountByRoute() {
		// TODO Auto-generated method stub
		return iHomeDao.getTop10ClickCountByRoute();
	}

	public List<JSONObject> getTotalInfo() {
		// TODO Auto-generated method stub
		List<JSONObject> adv=iHomeDao.getTotalAdv();
		adv.addAll(iHomeDao.getTotalBus());
		adv.addAll(iHomeDao.getTotalRoute());
		adv.addAll(iHomeDao.getTotalUser());
		return adv;
	}
	

}
