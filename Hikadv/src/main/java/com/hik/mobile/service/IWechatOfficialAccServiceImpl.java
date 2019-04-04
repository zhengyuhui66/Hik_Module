package com.hik.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.framework.utils.Page;
import com.hik.mobile.dao.IWechatOfficialAccDao;

import net.sf.json.JSONObject;

@Service
@Transactional
public class IWechatOfficialAccServiceImpl implements IWechatOfficialAccService {

	@Autowired
	private IWechatOfficialAccDao iWechatOfficialAccDao;
	
	@Override
	public Page getOfficialAccList(int start, int limit, String searchStr) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public int saveOrUpdateOfficialAccList(int officalAcc) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int deleteOfficialAcc(String[] ids) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public List<JSONObject> getRandomOfficial() {
		// TODO 自动生成的方法存根
		return iWechatOfficialAccDao.getRandomOfficial();
	}
	
	@Deprecated
	@Override
	public int saveUserInfo(String openid, String tid) {
		// TODO 自动生成的方法存根
		return iWechatOfficialAccDao.saveUserInfo(openid, tid);
	}

	@Override
	public int saveUserInfo(String openid, String tid, String phoneMac, String phoneNum) {
		// TODO 自动生成的方法存根
		return iWechatOfficialAccDao.saveUserInfo(openid, tid,phoneMac,phoneNum);
	}

	@Override
	public List<JSONObject> getUserInfoByMac(String usermac) {
		// TODO 自动生成的方法存根
		return iWechatOfficialAccDao.getUserInfoByMac(usermac);
	}

	@Override
	public List<JSONObject> getUserInfoByphoneNum(String phonenum) {
		// TODO 自动生成的方法存根
		return iWechatOfficialAccDao.getUserInfoByphoneNum(phonenum);
	}

	@Override
	public List<JSONObject> getAccessTokenByUserId(String userid) {
		// TODO 自动生成的方法存根
		return iWechatOfficialAccDao.getAccessTokenByUserId(userid);
	}

}
