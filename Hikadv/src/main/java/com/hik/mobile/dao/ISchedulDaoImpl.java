package com.hik.mobile.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.BaseHIKDao;
import com.hik.framework.utils.Page;
import com.hik.mobile.util.DateUtils;

import net.sf.json.JSONObject;
@Repository
@Transactional
public class ISchedulDaoImpl extends BaseHIKDao implements ISchedulDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> getSchedul(String startDate, String endDate) {
		List<String> param = new ArrayList<String>();
		param.add(startDate);
		param.add(endDate);
		String [] str=new String[]{"DATETIME"};
		String sql="SELECT DISTINCT DATETIME  FROM SCHEDULE T WHERE DATETIME<=TO_DATE(?,'YYYY-MM-DD') AND DATETIME>=TO_DATE(?,'YYYY-MM-DD')";
		return getNoPageObject(sql, param, str, "查询排期");
	}

	@Override
	public List<JSONObject> getSchedulByDate(String Date) {
		List<String> param = new ArrayList<String>();
		param.add(Date);
		String []str=new String[]{"DATETIME","DESCR"};
		String sql="SELECT T.DATETIME DATETIME,P.DESCR DESCR  FROM SCHEDULE T , PUTPRODUCT P WHERE"
				+ " T.PRODUCTID = P.MODELID AND T.DATETIME=TO_DATE(?,'YYYY-MM-DD')";
		return getNoPageObject(sql,param,str,"查询特定日期的广告排期详情");
	}

	@Override
	public List<JSONObject> getAllLine() {
		String [] str=new String[]{"ID","NAME"};
		String sql="SELECT ID,NAME FROM ROUTE ORDER BY NAME ASC";
		return getNoPageObject(sql, str, "查询线路");
	}

	@Override
	public List<JSONObject> getCarByLine(String lineId) {
		List<String> param = new ArrayList<String>();
		param.add(lineId);
		String []str=new String[]{"ID","CODE","ROUTE"};
		String sql="SELECT DISTINCT * FROM VEHICLE T WHERE T.ROUTEID=? ";
		return getNoPageObject(sql,param,str,"根据线路查询车辆");
	}

	@Override
	public List<JSONObject> getAllCar() {
		String []str=new String[]{"ID","CODE","ROUTE"};
		String sql="SELECT DISTINCT * FROM VEHICLE ORDER BY ROUTE ASC";
		return getNoPageObject(sql, str, "查询所有车辆");
	}

	@Override
	public Page getSchedulPut4Mobile(int start, int limit, String stime, String etime, String[] timeslice,
			String[] periodArr, String routeid, String busid, String userid,String orderby) {
		// TODO 自动生成的方法存根
		List param = new ArrayList();
		String sql="SELECT s.id,v.code as busname,vd.apmac,a.name as authcycname,s.datetime as datetime,t.name as timeslice,p.proname as proname,"
				+ "decode(s.state,'1','正常投放','2','暂停投放','3','取消投放','其它状态') as state FROM SCHEDULE s "+
		          "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
		           "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
		           "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
		           "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
		           "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
		String sql0="";
		List date=null;
		if(!StringUtils.isEmpty(stime)&&!StringUtils.isEmpty(stime)){
			date=DateUtils.getTimes(stime.substring(0, 10),etime.substring(0, 10));			
		}
		
		if(date!=null&&date.size()>0){
			sql0+="and s.datetime in(";
			for(int i=0;i<date.size();i++){
				sql0+="?,";
				param.add(date.get(i).toString());
			}
			sql0=sql0.substring(0, sql0.length()-1);
			sql0+=")";
		}
		String sql1="";
		if(timeslice!=null&&timeslice.length>0){
			sql1+="and s.interval in(";
			for(int i=0;i<timeslice.length;i++){
				sql1+="?,";
				param.add(timeslice[i]);
			}
			sql1=sql1.substring(0, sql1.length()-1);
			sql1+=") ";
		}
		
		String sql2="";
		if(periodArr!=null&&periodArr.length>0){
			sql1+="and s.interval in(";
			for(int i=0;i<periodArr.length;i++){
				sql2+="?,";
				param.add(periodArr[i]);
			}
			sql2=sql2.substring(0, sql2.length()-1);
			sql2+=") ";
		}
		String sql3="";
		if(!StringUtils.isEmpty(routeid)){
			sql3+="and v.routeid = ? ";
			param.add(routeid);
		}
		String sql4="";
		if(!StringUtils.isEmpty(busid)){
			sql4+="and v.id =? ";
			param.add(busid);
		}
		String sql5 = sql0+sql1+sql2+sql3+sql4;
		
		if(!StringUtils.isEmpty(sql5)){
			sql5=sql5.substring(3, sql5.length());
			sql+=" where "+ sql5;
		}
		if (!StringUtils.isEmpty(orderby)) {
			sql+=" order by "+orderby;
		}
		System.err.println("==>"+sql);
		String[] jsonStr=new String[]{"id","busname","apmac","authcycname","datetime","timeslice","proname","state"};
		Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询排期日志");
		return page;
	}

}
