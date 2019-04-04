package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IHomeDaoImpl extends BaseHIKDao implements IHomeDao{

	public List<JSONObject> getTop10ShowCountByAdv() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"counts","name","id"};
		String sql="SELECT * "+
				"FROM (SELECT COUNT(*) AS COUNTS, "+
				             "DECODE(A.NAME, NULL, 'δ����', '', 'δ����', A.NAME), "+
				             "T.ADVERTID "+
				        "FROM SHOW_COUNT_LOG T "+
				        "LEFT JOIN ADVERTMANGER A "+
				          "ON (T.ADVERTID = A.ID) "+
				       "GROUP BY T.ADVERTID, A.NAME ORDER BY COUNTS DESC)  WHERE ROWNUM<=10";

		return getNoPageObject(sql, str, "��ѯ���еĹ��չʾ��");
	}

	public List<JSONObject> getTop10ShowCountByRoute() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"counts","name"};
		String sql="SELECT * "+
				  "FROM (SELECT COUNT(*) AS COUNTS, "+
				               "DECODE(V.NAME, '', 'δ����', NULL, 'δ����', V.NAME) AS LINENAME "+
				          "FROM SHOW_COUNT_LOG S LEFT JOIN route V ON(S.LINEID=V.ID) "+
				         "GROUP BY V.NAME ORDER BY COUNTS DESC)  WHERE ROWNUM<=10 ";
		return getNoPageObject(sql, str, "��ѯ���е�·��չʾ");
	}

	public List<JSONObject> getTop10ClickCountByAdv() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"counts","name","id"};
		String sql="SELECT * "+
				"FROM (SELECT COUNT(*) AS COUNTS, "+
				             "DECODE(A.NAME, NULL, 'δ����', '', 'δ����', A.NAME), "+
				             "T.ADVERTID "+
				        "FROM CLICK_COUNT_LOG T "+
				        "LEFT JOIN ADVERTMANGER A "+
				          "ON (T.ADVERTID = A.ID) "+
				       "GROUP BY T.ADVERTID, A.NAME ORDER BY COUNTS DESC)  WHERE ROWNUM<=10 ";
		return getNoPageObject(sql, str, "��ѯ���еĹ������");
	}

	public List<JSONObject> getTop10ClickCountByRoute() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"counts","name"};
		String sql="SELECT * "+
				  "FROM (SELECT COUNT(*) AS COUNTS, "+
				               "DECODE(V.NAME, '', 'δ����', NULL, 'δ����', V.NAME) AS LINENAME "+
				          "FROM CLICK_COUNT_LOG  S LEFT JOIN route V ON(S.LINEID=V.ID) "+
				         "GROUP BY V.NAME ORDER BY COUNTS DESC)  WHERE ROWNUM<=10 ";
		return getNoPageObject(sql, str, "��ѯ���е�·�ߵ����");
	}

	public List<JSONObject> getTotalUser() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"totaluser"};
		String sql="select to_char(COUNT(*)) from WIFIAUTH t";
		return getNoPageObject(sql, str, "��������ͳ��");
	}

	public List<JSONObject> getTotalAdv() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"totaladv"};
		String sql="select to_char(COUNT(*)) from ADVERTMANGER t";
		return getNoPageObject(sql, str, "�������");
	}

	public List<JSONObject> getTotalRoute() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"totalroute"};
		String sql="select to_char(count(*)) from ROUTE t";
		return getNoPageObject(sql, str, "������·����");
	}

	public List<JSONObject> getTotalBus() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"totalbus"};
		String sql="select to_char(COUNT(*)) from VEHICLE t";
		return getNoPageObject(sql, str, "������������");
	}
}
