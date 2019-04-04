package com.hik.mobile.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseHIKDao;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IWechatOfficialAccDaoImpl extends BaseHIKDao implements IWechatOfficialAccDao {

	@Override
	public List<JSONObject> getRandomOfficial() {
		List<String> param = new ArrayList<String>();

		String[] str = new String[] { "APPID", "APPSECRET", "DESCR" };
		String sql = "SELECT APPID,APPSECRET,DESCR FROM ( SELECT * FROM WECHAT_ACC ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM=1";
		return getNoPageObject(sql, str, "随机获取一个公众账号");
	}

	@Deprecated
	@Override
	public int saveUserInfo(String openId, String tId) {
		List<String> param = new ArrayList<String>();
		param.add(openId);
		param.add(tId);
		String sql = "INSERT INTO WECHAT_AUTH_USER_INFO(ID,OPENID,TID,TIMESTAMP) "
				+ "VALUES(ADVGROUP_SEQ.NEXTVAL,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'))";
		return executeUpdateSQL(sql, param, "保存认证的用户");
	}

	@Override
	public int saveUserInfo(String openId, String tId, String phoneMac, String phoneNum) {
		List<String> param = new ArrayList<String>();
		param.add(openId);
		param.add(tId);
		param.add(phoneMac);
		param.add(phoneNum);
		String sql = "INSERT INTO WECHAT_AUTH_USER_INFO(ID,OPENID,TID,PHONEMAC,PHONENUM,TIMESTAMP) "
				+ "VALUES(ADVGROUP_SEQ.NEXTVAL,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'))";
		return executeUpdateSQL(sql, param, "保存认证的用户");
	}

	@Override
	public List<JSONObject> getUserInfoByMac(String usermac) {
		List<String> param = new ArrayList<String>();
		param.add(usermac);
		String[] str = new String[] { "OPENID", "PHONEMAC", "PHONENUM" };
		String sql = "SELECT * FROM (SELECT * FROM WECHAT_AUTH_USER_INFO) T WHERE ROWNUM<=1 AND T.PHONEMAC=?";
		return getNoPageObject(sql, str, "根据用户mac查询是否存在记录");
	}

	@Override
	public List<JSONObject> getUserInfoByphoneNum(String phonenum) {
		List<String> param = new ArrayList<String>();
		param.add(phonenum);
		String[] str = new String[] { "OPENID", "PHONEMAC", "PHONENUM" };
		String sql = "SELECT * FROM (SELECT * FROM WECHAT_AUTH_USER_INFO) T WHERE ROWNUM<=1 AND T.PHONENUM=?";
		return getNoPageObject(sql, str, "根据用户手机号码查询是否存在记录");
	}

	@Override
	public List<JSONObject> getAccessTokenByUserId(String userid) {
		List<String> param = new ArrayList<String>();
		param.add(userid);
		String[] str = new String[] { "NAME", "APPID","APPSECRET", "ACCESSTOKEN","USER_ADV" };
		String sql = "SELECT NAME,APPID,APPSECRET,ACCESSTOKEN,USER_ADV FROM MMPP where USER_ADV=?";
		return getNoPageObject(sql, param, str, "根据用户ID查询对用公众号的Accesstoken");

	}

}
