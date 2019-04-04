package com.hik.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.cursor.spi.RefCursorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.OracleTypes;

@Repository
public class ShowClickDaoImpl extends BaseHIKDao implements ShowClickDao{
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public List<JSONObject> getAllAdvert() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"id","name"};
		String sql="select t.id,t.name from ADVERTMANGER t";
		return getNoPageObject(sql, str);
	}

	public List<JSONObject> getMaterByAdvertId(String advertId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(advertId);
		String[] str = new String[]{"id","name"};
		String sql="select DISTINCT C.MATERID,M.MATER_NAME from CLICK_COUNT_LOG C LEFT JOIN MATERIEL_INFO M ON(C.MATERID=M.MATER_ID) WHERE C.ADVERTID=?";
		return getNoPageObject(sql, param, str, "根据广告ID查询所有的物料");
	}

	public List<JSONObject> getAllRold() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"id","name"};
		String sql="select DISTINCT id as id,name as name from route t";
		return getNoPageObject(sql, str);
	}

	public List<JSONObject> getBusIdByRoldId(String RoldId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(RoldId);
		String[] str = new String[]{"id","name"};
		String sql="select t.ID,t.CODE from VEHICLE t where t.routeid=?"; 
		return getNoPageObject(sql, param, str, "根据广告ID查询所有的物料");
	}

	public List<JSONObject> queryShowInfo(String stime, String etime,String roldId,String busId,String advertid,String materid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		Date starttime = DateUtil.getStrToDate(stime);
		Date endtime = DateUtil.getStrToDate(etime);
		String sql="";
		String[] str = new String[]{"count","time"};
		while(starttime.compareTo(endtime)<0){
			sql+="SELECT COUNT(*) as count,'"+DateUtil.getDateToStr(starttime)+"' as time"+
				" FROM SHOW_COUNT_LOG T  "+
				" WHERE SHOW_TIME >? "+
				" AND SHOW_TIME <=? ";
				param.add(DateUtil.getDateToStr(starttime));
				starttime=DateUtil.getAddaDay(starttime);
				param.add(DateUtil.getDateToStr(starttime));
			if(StringUtils.isNotEmpty(roldId)&&!PROCEDURCES.NOCHOSE_FLAG.equals(roldId)){
				sql+=" AND t.busid IN (SELECT v.id FROM VEHICLE v WHERE v.routeid=?) ";
				param.add(roldId);
			}
			
			if(StringUtils.isNotEmpty(busId)&&!PROCEDURCES.NOCHOSE_FLAG.equals(busId)){
				sql+=" AND busid=? ";
				param.add(busId);
			}
			
			if(StringUtils.isNotEmpty(advertid)&&!PROCEDURCES.NOCHOSE_FLAG.equals(advertid)){
				sql+=" AND advertid=? ";
				param.add(advertid);
			}
			
			if(StringUtils.isNotEmpty(materid)&&!PROCEDURCES.NOCHOSE_FLAG.equals(materid)){
				sql+=" AND materid=? ";
				param.add(materid);
			}
			if(starttime.compareTo(endtime)!=0){
				sql+=" union all ";
			}
		}
		return getNoPageObject(sql, param, str, "查询展示信息");
	}
	public List<JSONObject> queryClickInfo(String stime, String etime,String roldId,String busId,String advertid,String materid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		Date starttime = DateUtil.getStrToDate(stime);
		Date endtime = DateUtil.getStrToDate(etime);
		String sql="";
		String[] str = new String[]{"count","time"};
		while(starttime.compareTo(endtime)<0){
			sql+="SELECT COUNT(*) as count,'"+DateUtil.getDateToStr(starttime)+"' as time"+
				" FROM CLICK_COUNT_LOG T  "+
				" WHERE CLICK_TIME >? "+
				" AND CLICK_TIME <=? ";
				param.add(DateUtil.getDateToStr(starttime));
				starttime=DateUtil.getAddaDay(starttime);
				param.add(DateUtil.getDateToStr(starttime));
			if(StringUtils.isNotEmpty(roldId)&&!PROCEDURCES.NOCHOSE_FLAG.equals(roldId)){
				sql+=" AND t.busid IN (SELECT v.id FROM VEHICLE v WHERE v.routeid=?) ";
				param.add(roldId);
			}
			if(StringUtils.isNotEmpty(busId)&&!PROCEDURCES.NOCHOSE_FLAG.equals(busId)){
				sql+=" AND busid=? ";
				param.add(busId);
			}
			if(StringUtils.isNotEmpty(advertid)&&!PROCEDURCES.NOCHOSE_FLAG.equals(advertid)){
				sql+=" AND advertid=? ";
				param.add(advertid);
			}
			if(StringUtils.isNotEmpty(materid)&&!PROCEDURCES.NOCHOSE_FLAG.equals(materid)){
				sql+=" AND materid=? ";
				param.add(materid);
			}
			if(starttime.compareTo(endtime)!=0){
				sql+=" union all ";
			}
		}
		return getNoPageObject(sql, param, str, "查询展示信息");
	}
	
	private class TYPEE{
			private String name;
			private String count;
			public TYPEE(String name, String count) {
				super();
				this.name = name;
				this.count = count;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getCount() {
				return count;
			}
			public void setCount(String count) {
				this.count = count;
			}
		}
	
	@Override
	public Page queryRouteSufCount(String name, String stime, String etime,int start,int limit) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="SELECT COUNT(*) as count,r.name "+
			       "FROM wifiauth w  "+
			       "LEFT JOIN device d ON(w.deviceid=d.id) "+
			       "LEFT JOIN vehicle v on(d.vehicleid=v.id) "+
			       "LEFT JOIN route r ON(r.id=v.routeid) ";
			String whereStr=" ";
			if(StringUtils.isNotEmpty(name)){
				param.add("%"+name+"%");
				whereStr+= "and r.name like ? ";
			}
			
			if(StringUtils.isNotEmpty(stime)){
				param.add(stime);
				whereStr+= "and  w.authtime>? ";
			}
			
			if(StringUtils.isNotEmpty(etime)){
				param.add(etime);
				whereStr+= "and  w.authtime<?";
			}
			
			if(StringUtils.isNotEmpty(whereStr)){
				sql+=" where "+whereStr.substring(4, whereStr.length());
			}
		sql+=" GROUP BY r.name";      
			String[] str = new String[]{"count","name"};
			Page page=null;
			if(param.size()==0){
				page = getPageObject(sql, start, limit,str,"查询线路上网人数");
			}else{
				page = getPageObject(sql, start, limit, param, str,"查询线路上网人数");
			}
		return page;
	}
	
	@Override
	public Page queryBusSufCount(String name, String stime, String etime,int start,int limit) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="SELECT COUNT(*) as count,v.code "+
			       "FROM wifiauth w "+
			       "LEFT JOIN device d ON(w.deviceid=d.id) "+
			       "LEFT JOIN vehicle v on(d.vehicleid=v.id) ";
		String whereStr=" ";
		if(StringUtils.isNotEmpty(name)){
			param.add("%"+name+"%");
			whereStr+= "and v.code like ? ";
		}
		
		if(StringUtils.isNotEmpty(stime)){
			param.add(stime);
			whereStr+= "and  w.authtime>? ";
		}
		
		if(StringUtils.isNotEmpty(etime)){
			param.add(etime);
			whereStr+= "and  w.authtime<?";
		}
		
		if(StringUtils.isNotEmpty(whereStr)){
			sql+=" where "+whereStr.substring(4, whereStr.length());
		}
		
		sql+="  GROUP BY v.code";
		String[] str = new String[]{"count","name"};
		Page page=null;
		if(param.size()==0){
			page = getPageObject(sql, start, limit,str,"查询车辆上网人数");
		}else{
			page = getPageObject(sql, start, limit, param, str,"查询车辆上网人数");
		}
		return page;
	}

	@Override
	public List queryRouteSufCountTotal(String name, String stime, String etime) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="SELECT COUNT(*) as counts "+
			       "FROM wifiauth w  "+
			       "LEFT JOIN device d ON(w.deviceid=d.id) "+
			       "LEFT JOIN vehicle v on(d.vehicleid=v.id) "+
			       "LEFT JOIN route r ON(r.id=v.routeid) ";
			String whereStr=" ";
			if(StringUtils.isNotEmpty(name)){
				param.add("%"+name+"%");
				whereStr+= "and r.name like ? ";
			}
			
			if(StringUtils.isNotEmpty(stime)){
				param.add(stime);
				whereStr+= "and  w.authtime>? ";
			}
			
			if(StringUtils.isNotEmpty(etime)){
				param.add(etime);
				whereStr+= "and  w.authtime<?";
			}
			
			if(StringUtils.isNotEmpty(whereStr)){
				sql+=" where "+whereStr.substring(4, whereStr.length());
			}
			String[] str = new String[]{"total"};
			return getNoPageObject(sql, param, str, "查询上网全统计");
	}

	@Override
	public List queryBusSufCountTotal(String name, String stime, String etime) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="SELECT COUNT(*) as count "+
			       "FROM wifiauth w "+
			       "LEFT JOIN device d ON(w.deviceid=d.id) "+
			       "LEFT JOIN vehicle v on(d.vehicleid=v.id) ";
		String whereStr=" ";
		if(StringUtils.isNotEmpty(name)){
			param.add("%"+name+"%");
			whereStr+= "and v.code like ? ";
		}
		
		if(StringUtils.isNotEmpty(stime)){
			param.add(stime);
			whereStr+= "and  w.authtime>? ";
		}
		
		if(StringUtils.isNotEmpty(etime)){
			param.add(etime);
			whereStr+= "and  w.authtime<?";
		}
		
		if(StringUtils.isNotEmpty(whereStr)){
			sql+=" where "+whereStr.substring(4, whereStr.length());
		}
		String[] str = new String[]{"total"};
		
		return getNoPageObject(sql, param, str, "查询上网全统计");
	}
	
//	public Page queryRouteSufCount(final String name,final String stime,final String etime, final int start, final int limit) {
//		// TODO Auto-generated method stub
//		  @SuppressWarnings("unchecked")
//		  Page page = (Page)jdbcTemplate.execute(
//				     new CallableStatementCreator() {   
//				        public CallableStatement createCallableStatement(Connection con) throws SQLException {   
//				           String storedProc = "{call prc_surfedroutetotal_query(?,?,?,?,?,?,?,?)}";// 调用的sql   
//				           CallableStatement cs = con.prepareCall(storedProc);
//				           cs.setString(1,name);// 设置输入参数的值   
//				           cs.setString(2,stime);// 设置输入参数的值   
//				           cs.setString(3,etime);// 设置输入参数的值   
//				           cs.setDouble(4,0);
//				           cs.setDouble(5,start);
//				           cs.setDouble(6,limit);
//				           cs.registerOutParameter(7, OracleTypes.NUMBER);// 设置输入参数的值   
//				           cs.registerOutParameter(8, OracleTypes.CURSOR);// 注册输出参数的类型   
//				           return cs;   
//				        }   
//				     }, new CallableStatementCallback() {   
//				        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException {   
//				           List<JSONObject> resultsJson = new ArrayList<JSONObject>();   
//				           cs.execute();   
//				           BigDecimal totalrecords =  (BigDecimal) cs.getObject(7);// 获取游标一行的值   
//				           ResultSet refcursor = (ResultSet) cs.getObject(8);// 获取游标一行的值   
//				           while (refcursor.next()) {// 转换每行的返回值到Map中   
//				        	   JSONObject json = new JSONObject();
//				        	   json.accumulate("name", refcursor.getString("name"));
//				        	   json.accumulate("count", refcursor.getString("count"));
//				        	   resultsJson.add(json);
//				           }   
//				           refcursor.close(); 
//				           Page page =new PageObject(start,limit,Integer.parseInt(totalrecords+""),resultsJson);
//				           return page;   
//				        }   
//				  });   
//		return page;
//	}
//
//	public Page queryBusSufCount(final String name,final String stime, final String etime,final int start, final int limit) {
//		// TODO Auto-generated method stub
//		  @SuppressWarnings("unchecked")
//		  Page resultsJson= (Page) jdbcTemplate.execute(
//				     new CallableStatementCreator() {   
//				        public CallableStatement createCallableStatement(Connection con) throws SQLException {   
//				           String storedProc = "{call prc_surfedvehicletotal_query(?,?,?,?,?,?,?,?,?,?,?,?)}";// 调用的sql   
//				           CallableStatement cs = con.prepareCall(storedProc);
//				           
//				           cs.setString(1,null);
//				           cs.setString(2,null);
//				           cs.setString(3, name);// 设置输入参数的值   
//				           cs.setString(4, stime);// 设置输入参数的值   
//				           cs.setString(5, etime);// 设置输入参数的值   
//				           cs.setDouble(6,0);
//				           cs.setString(7,null);
//				           cs.setDouble(8,0);
//				           cs.setDouble(9,start);
//				           cs.setDouble(10,limit);
//				           cs.registerOutParameter(11, OracleTypes.NUMBER);// 设置输入参数的值   
//				           cs.registerOutParameter(12, OracleTypes.CURSOR);// 注册输出参数的类型   
//				           return cs;   
//				        }   
//				     }, new CallableStatementCallback() {   
//				        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException {   
//				           List<JSONObject> resultsJson = new ArrayList<JSONObject>();   
//				           cs.execute();   
//				           BigDecimal totalrecords =  (BigDecimal) cs.getObject(11);// 获取游标一行的值   
//				           ResultSet refcursor = (ResultSet) cs.getObject(12);// 获取游标一行的值   
//				           
//				           while (refcursor.next()) {// 转换每行的返回值到Map中   
//				        	   JSONObject json = new JSONObject();
//				        	   json.accumulate("name", refcursor.getString("name"));
//				        	   json.accumulate("count", refcursor.getString("count"));
//				        	   json.accumulate("R", refcursor.getString("R"));
//				        	   resultsJson.add(json);
//				           }   
//				           refcursor.close(); 
//				           Page page =new PageObject(start,limit,Integer.parseInt(totalrecords+""),resultsJson);
//				           return page;   
//				        }   
//				  });   
//
//		return resultsJson;
//	}



}
