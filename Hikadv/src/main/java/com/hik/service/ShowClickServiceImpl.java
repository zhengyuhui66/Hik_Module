package com.hik.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.dao.AUDIT_INFO_jpa;
import com.hik.dao.AUDIT_USERINFO_jpa;
import com.hik.dao.BaseService;
import com.hik.dao.IGetAdvertDao;
import com.hik.dao.IMaterialDao;
import com.hik.dao.IMaterialDaoImpl;
import com.hik.dao.MATERIEL_INFO_jpa;
import com.hik.dao.ShowClickDao;
import com.hik.framework.service.IUserService;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.Page;
import com.hik.util.SFTP;
import com.jcraft.jsch.ChannelSftp;

import net.sf.json.JSONObject;


@Service
@Transactional
public class ShowClickServiceImpl  extends BaseService  implements ShowClickService{
	@Autowired
	private ShowClickDao showClickDao;

	private JSONObject getDefaultCombobox(){
		JSONObject json = new JSONObject();
		json.accumulate("id", "0");
		json.accumulate("name", "Î´Ñ¡Ôñ");
		return json;
	}
	public List<JSONObject> getAllAdvert() {
		// TODO Auto-generated method stub
		List<JSONObject> param=showClickDao.getAllAdvert();
		param.add(0,getDefaultCombobox());
		return param;
	}

	public List<JSONObject> getMaterByAdvertId(String advertId) {
		// TODO Auto-generated method stub
		List<JSONObject> param= showClickDao.getMaterByAdvertId(advertId);
		param.add(0,getDefaultCombobox());
		return param;
	}

	public List<JSONObject> getAllRold() {
		// TODO Auto-generated method stub
		List<JSONObject> param= showClickDao.getAllRold();
		param.add(0,getDefaultCombobox());
		return param;
	}

	public List<JSONObject> getBusIdByRoldId(String RoldId) {
		// TODO Auto-generated method stub
		List<JSONObject> param= showClickDao.getBusIdByRoldId(RoldId);
		param.add(0,getDefaultCombobox());
		return param;
	}

	public List<JSONObject> queryShowInfo(String stime, String etime,String roldId,String busId,String advertid,String materid) {
		// TODO Auto-generated method stub
		return showClickDao.queryShowInfo(stime, etime,roldId,busId,advertid,materid);
	}

	public List<JSONObject> queryClickInfo(String stime, String etime,String roldId,String busId,String advertid,String materid) {
		// TODO Auto-generated method stub
		return showClickDao.queryClickInfo(stime, etime,roldId,busId,advertid,materid);
	}
//	public Page queryRouteSufCount(String name, String stime, String etime, int start, int limit) {
//		// TODO Auto-generated method stub
//		start--;
//		return showClickDao.queryRouteSufCount(name, stime, etime, start, limit);
//	}
//	public Page queryBusSufCount(String name, String stime, String etime, int start, int limit) {
//		// TODO Auto-generated method stub
//		start--;
//		return showClickDao.queryBusSufCount(name, stime, etime, start, limit);
//	}
	@Override
	public Page queryRouteSufCount(String name, String stime, String etime,int start,int limit) {
		// TODO Auto-generated method stub
		return showClickDao.queryRouteSufCount(name, stime, etime,start,limit);
	}
	@Override
	public Page queryBusSufCount(String name, String stime, String etime,int start,int limit) {
		// TODO Auto-generated method stub
		return showClickDao.queryBusSufCount(name, stime, etime,start,limit);
	}
	@Override
	public List queryRouteSufCountTotal(String name, String stime, String etime) {
		// TODO Auto-generated method stub
		return showClickDao.queryRouteSufCountTotal(name, stime, etime);
	}
	@Override
	public List queryBusSufCountTotal(String name, String stime, String etime) {
		// TODO Auto-generated method stub
		return showClickDao.queryBusSufCountTotal(name, stime, etime);
	}

}
