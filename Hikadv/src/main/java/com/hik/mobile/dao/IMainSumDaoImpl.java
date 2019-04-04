package com.hik.mobile.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseHIKDao;
import com.hik.framework.utils.DateUtil;
import com.hik.mobile.entity.CONVERT_RATE;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IMainSumDaoImpl extends BaseHIKDao implements IMainSumDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> getTolUser(String userid) {
		// TODO �Զ����ɵķ������
//		List params = new ArrayList();
		String[] str = new String[] { "TOTALUSERS" };
//		params.add(userid);

		String sql = "SELECT TO_CHAR(COUNT(*)) TOTALUSERS FROM WIFIAUTH T";
		List<JSONObject> list = getNoPageObject(sql,str, "�õ���ʷ�û���������");
		
		if (list != null && list.size() > 0) {
			System.out.println(list);
		}
		return list;
	}

	@Override
	public List<JSONObject> getTotalAdServing(String userid) {
		// // TODO �Զ����ɵķ������
		List prams = new ArrayList();
		String[] str = new String[] { "TOTALEXP" };
		prams.add(userid);

		String sql = "SELECT TO_CHAR(COUNT(*)) TOTALEXP FROM SHOW_COUNT_LOG T JOIN  MATERIEL_INFO C ON(T.MATERID=C.MATER_ID) WHERE C.CUSTOMER_ID=?";
		List<JSONObject> list = getNoPageObject(sql, prams, str, "�õ������Ͷ�Ź���ع�������");
		if (list != null && list.size() > 0) {
			System.out.println(list);
		}
		return list;
	}

	@Override
	public CONVERT_RATE getConverRate(String userid, int couType) {
		// TODO �Զ����ɵķ������

		return null;
	}

	@Override
	public List<JSONObject> getAdvCount(String userid) {
		// // TODO �Զ����ɵķ������
		// return null;

		List prams = new ArrayList();
		String[] str = new String[] { "CLICKCOUNT" };
		prams.add(userid);

		String sql = "SELECT TO_CHAR(COUNT(*)) CLICKCOUNT FROM MATERIEL_INFO T JOIN  CLICK_COUNT_LOG C ON(T.MATER_ID=C.MATERID) WHERE T.CUSTOMER_ID=?";
		List<JSONObject> list = getNoPageObject(sql, prams, str, "�õ������������ܴ���");
		if (list != null && list.size() > 0) {
			System.out.println(list);
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> getChartOfSurfNetSum(String userid, String startDate, String endDate) {
		// TODO �Զ����ɵķ������
		String[] str = new String[] { "PERMONTH","USERPERMONTH" };
		String sql="select * from (select sum(decode(to_char(authtime,'mm'),'01',1,0)) \"201601\",sum(decode(to_char(authtime,'mm'),'02',1,0))"
				+ " \"201602\", sum(decode(to_char(authtime,'mm'),'03',1,0)) \"201603\",sum(decode(to_char(authtime,'mm'),'04',1,0)) "
				+ "\"201604\", sum(decode(to_char(authtime,'mm'),'05',1,0)) \"201605\",sum(decode(to_char(authtime,'mm'),'06',1,0))"
				+ " \"201606\", sum(decode(to_char(authtime,'mm'),'07',1,0)) \"201607\",sum(decode(to_char(authtime,'mm'),'08',1,0)) "
				+ "\"201608\", sum(decode(to_char(authtime,'mm'),'09',1,0)) \"201609\",sum(decode(to_char(authtime,'mm'),'10',1,0))"
				+ " \"2016010\", sum(decode(to_char(authtime,'mm'),'11',1,0)) \"2016011\",sum(decode(to_char(authtime,'mm'),'12',1,0))"
				+ " \"2016012\" from WIFIAUTH where to_char(authtime,'yyyy')='2016') unpivot (USERPERMONTH for PERMONTH in "
				+ "(\"201601\", \"201602\",\"201603\",\"201604\",\"201605\",\"201606\",\"201607\",\"201608\",\"201609\",\"2016010\",\"2016011\",\"2016012\") )";	
		List<JSONObject> list = getNoPageObject(sql,str, "�õ������2016��ȹ�����ܴ���");
		
		return list;
	}

	
	@Override
	public List<JSONObject> getChartOfAdDelv(String userid, String startDate, String endDate) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	public List<JSONObject> getUpdateInfo(String type) {
		// TODO �Զ����ɵķ������
		
		List prams = new ArrayList();
		prams.add(type);
		String[] str = new String[] { "NAME","TYPE","VERSION","VERSIONDESC","VERSIONNAME","URL","CREATIME", };
		
		String sql="SELECT * FROM (SELECT NAME,TYPE,VERSION,VERSIONDESC,VERSIONNAME,URL,CREATIME FROM CLIENTMANGER T WHERE TYPE=? ORDER BY T.ID DESC) WHERE ROWNUM=1";
		List<JSONObject> result = getNoPageObject(sql,prams,str, "��ȡ���°汾��Ϣ->sql");
		if (result != null && result.size() > 0) {
			System.out.println(result);
		}
		return result;
	}

	@Override
	public List<JSONObject> getAdUsersHisTrend(String sDate, String eDate, String userid) {
		List param = new ArrayList();
		Date starttime = DateUtil.getStrToDate(sDate);
		Date endtime = DateUtil.getStrToDate(eDate);
		String sql="";
		String[] str = new String[]{"count","time"};
		while(starttime.compareTo(endtime)<0){
			sql+="SELECT COUNT(*) as count,'"+DateUtil.getDateToStr(starttime)+"' as time"+
				" FROM WIFIAUTH T  "+
				" WHERE AUTHTIME >?  "+
				" AND AUTHTIME <=? ";
				param.add(DateUtil.getDateToStr(starttime));
				starttime=DateUtil.getAddaDay(starttime);
				param.add(DateUtil.getDateToStr(starttime));
			
			if(starttime.compareTo(endtime)!=0){
				sql+=" union all ";
			}
			
		}
		return getNoPageObject(sql, param, str, "��ѯ�ܹ���û�����ʷ����");
		
	}

	@Override
	public List<JSONObject> getAdExpoHisTrend(String sDate, String eDate, String userid) {
		List param = new ArrayList();
		Date starttime = DateUtil.getStrToDate(sDate);
		Date endtime = DateUtil.getStrToDate(eDate);
		String sql="";
		String[] str = new String[]{"count","time"};
		while(starttime.compareTo(endtime)<0){
			sql+="SELECT COUNT(*) as count,'"+DateUtil.getDateToStr(starttime)+"' as time"+
				" FROM SHOW_COUNT_LOG T JOIN  MATERIEL_INFO C ON(T.MATERID=C.MATER_ID)  "+
				" WHERE T.SHOW_TIME >? "+
				" AND T.SHOW_TIME <=? "+
				"AND C.CUSTOMER_ID=?";
				param.add(DateUtil.getDateToStr(starttime));
				starttime=DateUtil.getAddaDay(starttime);
				param.add(DateUtil.getDateToStr(starttime));
				param.add(userid);
			if(starttime.compareTo(endtime)!=0){
				sql+=" union all ";
			}
			
		}
		return getNoPageObject(sql, param, str, "��ѯ��ǰ�û�����ع���ʷ����");
	}

	@Override
	public List<JSONObject> getAdClickHisTrend(String sDate, String eDate, String userid) {
		List param = new ArrayList();
		Date starttime = DateUtil.getStrToDate(sDate);
		Date endtime = DateUtil.getStrToDate(eDate);
		String sql="";
		String[] str = new String[]{"count","time"};
		while(starttime.compareTo(endtime)<0){
			sql+="SELECT COUNT(*) as count,'"+DateUtil.getDateToStr(starttime)+"' as time"+
				" FROM MATERIEL_INFO T JOIN  CLICK_COUNT_LOG C ON(T.MATER_ID=C.MATERID)  "+
				" WHERE C.CLICK_TIME >? "+
				" AND C.CLICK_TIME <=? "+
				"AND T.CUSTOMER_ID=?";
				param.add(DateUtil.getDateToStr(starttime));
				starttime=DateUtil.getAddaDay(starttime);
				param.add(DateUtil.getDateToStr(starttime));
				param.add(userid);
			if(starttime.compareTo(endtime)!=0){
				sql+=" union all ";
			}
			
		}
		return getNoPageObject(sql, param, str, "��ѯ��ǰ�û�����Ͷ�Ź������ʷ����");
	}

}
