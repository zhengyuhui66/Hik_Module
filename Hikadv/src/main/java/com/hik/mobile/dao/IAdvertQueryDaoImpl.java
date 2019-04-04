package com.hik.mobile.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseHIKDao;
import com.mysql.fabric.xmlrpc.base.Param;
import com.mysql.fabric.xmlrpc.base.Params;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IAdvertQueryDaoImpl extends BaseHIKDao implements IAdvertQueryDao {

	@Override
	public List<JSONObject> getAllTemplate() {
		String[] str = new String[] { "id", "name" };
		String sql = "SELECT DISTINCT id,NAME FROM ADVERTMODELMANGER";
		return getNoPageObject(sql, str, "查询所有的广告");
	}

	@Override
	public List<JSONObject> getSpaceByTemplate(String userid, String modelid) {
		List<String> param = new ArrayList<String>();
		param.add(modelid);
		param.add(modelid);
		param.add(modelid);
		param.add(modelid);
		param.add(modelid);
		String[] str = new String[] { "advposid", "advposname", "advertid" };
		logger.info("查询到的modelid======>" + modelid);
		// String sql="select modelmodeid,modename,'请选择...' as advertid from
		// ADVERTMODELMANGER t WHERE id=?";
		String sql = "SELECT advposid1,advposname1,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid1 IS NOT NULL "
				+ "Union ALL "
				+ "SELECT advposid2,advposname2,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid2 IS NOT NULL "
				+ "Union ALL "
				+ "SELECT advposid3,advposname3,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid3 IS NOT NULL "
				+ "Union ALL "
				+ "SELECT advposid4,advposname4,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid4 IS NOT NULL "
				+ "Union ALL "
				+ "SELECT advposid5,advposname5,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid5 IS NOT NULL";
		return getNoPageObject(sql, param, str, "查询子模块");
	}

	@Override
	public List<JSONObject> getAllAd(String userid) {
		// String sql="SELECT ID,NAME FROM ADVERTMANGER T";
		String sql = "select ID,NAME from ADVERTMANGER t left join MATERIEL_INFO m  on(t.mater_id=m.mater_id)";
		String[] str = new String[] { "advertid", "name" };
		if (userid != null) {
			List<String> param = new ArrayList<String>();
			sql += " where m.customer_id=?";
			param.add(userid);
			return getNoPageObject(sql, param, str);
		}
		// List list = new ArrayList
		return getNoPageObject(sql, str);
	}

	@Override
	public List<JSONObject> getAllAd() {
		String sql = "SELECT ID,NAME FROM ADVERTMANGER T";
		String[] str = new String[] { "advertid", "name" };
		// List list = new ArrayList
		return getNoPageObject(sql, str);
	}

}
