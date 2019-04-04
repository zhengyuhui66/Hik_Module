package com.hik.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

@Repository
@Scope("prototype")
public class IPutPlanDaoImpl extends BaseHIKDao implements IPutPlanDao{
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List getProduct(String authid) {
		// TODO Auto-generated method stub
		String sql=null;
		String[] str = new String[]{"proname","id"};
		if(PROCEDURCES.MMPPFLAG.equals(authid)){
			sql="SELECT NAME,ID FROM MMPP_PRODUCT";
			return getNoPageObject(sql,str,"strree");
		}else{
			List param = new ArrayList();
			param.add(authid);
			sql="select t.proname,t.id from PUTPRODUCT t LEFT JOIN ADVERTMODELMANGER a ON (t.modelid=a.id) WHERE a.cycid=?";
		 	return getNoPageObject(sql,param,str,"strree");
		}
		
		
	
	}

	
	
	@Override
	public List getBus() {
		// TODO Auto-generated method stub
		String sql="SELECT t.id,t.code,t.brand,t.MODEL,t.color,t.corpid,t.corpname,t.routeid FROM vehicle T";
		String[] str= new String[]{"bid","name","brand","model","color","corpid","corpname","routeid"};
		return getNoPageObject(sql, str);
	}
//	@Override
//	public Object putPlan(final List<Object[]> object) {
//	 final long  st=System.currentTimeMillis();
//		@SuppressWarnings("unchecked")
//		Object obj=jdbcTemplate.execute(new CallableStatementCreator(){
//						@Override
//						public CallableStatement createCallableStatement(Connection con) throws SQLException {
//							// TODO Auto-generated method stub
//							String execuSql = "{call batch51(?,?,?)}";
//							CallableStatement cs = con.prepareCall(execuSql);
//							
//							System.out.println("初始化参数开始时间:"+st);
//							Object[][] obj=new Object[object.size()][8];
//							for(int i=0;i<object.size();i++){
//								obj[i]=object.get(i);
//							}
//							System.out.println("初始化参数结束时间:"+(System.currentTimeMillis()-st));
//							org.apache.commons.dbcp.PoolableConnection  conn=(PoolableConnection) con.getMetaData().getConnection();
//							oracle.jdbc.OracleConnection co=(OracleConnection) conn.getDelegate();
//							ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PUTPARAM",co);
//							ARRAY array = new ARRAY(desc,co,obj);
//							cs.setArray(1,array);
//							 cs.registerOutParameter(2, OracleTypes.CURSOR);// 注册输出参数的类型   
//							 cs.registerOutParameter(3, OracleTypes.NUMBER);// 注册输出参数的类型 
//							return cs;
//						}
//					}, new CallableStatementCallback(){
//				        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException { 
//				        	 List<JSONObject> resultsJson = new ArrayList<JSONObject>();   
//					           cs.execute(); 
//					       	System.out.println("执行结束时间:"+(System.currentTimeMillis()-st));
//					           BigDecimal row_object = (BigDecimal)cs.getObject(3);
//					           ResultSet refcursor = (ResultSet) cs.getObject(2);// 获取游标一行的值  
//					           if(row_object.compareTo(new BigDecimal("0"))!=0){
//					        	   while (refcursor.next()) {// 转换每行的返回值到Map中   
//						        	   JSONObject json = new JSONObject();
//						        	   json.accumulate("status",refcursor.getString("status"));
//						        	   resultsJson.add(json);
//						           } 
//					        	   return resultsJson;
//					           }
//					           while (refcursor.next()) {// 转换每行的返回值到Map中   
//					        	   JSONObject json = new JSONObject();
//					        	   json.accumulate("busname",refcursor.getString("busname"));
//					        	   json.accumulate("authcycname",refcursor.getString("authcycname"));
//					        	   json.accumulate("datetime",refcursor.getString("datetime"));
//					        	   json.accumulate("timeslice",refcursor.getString("timeslice"));
//					        	   json.accumulate("proname",refcursor.getString("proname"));
//					        	   resultsJson.add(json);
//					           }   
//					           refcursor.close();
//					           return resultsJson;
//					            
//				        }
//					});
//
//		return obj; 
//	}

//	@Override
//	public Object putPlantest(final List<Object[]> object) {
//		// TODO Auto-generated method stub
//		
//		@SuppressWarnings("unchecked")
//		Object obj=jdbcTemplate.execute(new CallableStatementCreator(){
//						@Override
//						public CallableStatement createCallableStatement(Connection con) throws SQLException {
//							// TODO Auto-generated method stub
//							String execuSql = "{call batch50(?,?,?)}";
//							CallableStatement cs = con.prepareCall(execuSql);
//							Object[][] obj=new Object[object.size()][8];
//							System.out.println("ID,时间，时间片段，产品，周期，APID，状态,用户"+object.size());
//							
//							for(int i=0;i<object.size();i++){
//								obj[i]=object.get(i);
//								for(int j=0;j<8;j++){
//									System.out.print(obj[i][j]+",");
//								}
//								System.out.println(i);
//							}
//							org.apache.commons.dbcp.PoolableConnection  conn=(PoolableConnection) con.getMetaData().getConnection();
//							oracle.jdbc.OracleConnection co=(OracleConnection) conn.getDelegate();
//							ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PUTPARAM",co);
//							ARRAY array = new ARRAY(desc,co,obj);
//							cs.setArray(1,array);
//							 cs.registerOutParameter(2, OracleTypes.CURSOR);// 注册输出参数的类型   
//							 cs.registerOutParameter(3, OracleTypes.NUMBER);// 注册输出参数的类型 
//							return cs;
//						}
//					}, new CallableStatementCallback(){
//				        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException { 
//				        	 List<JSONObject> resultsJson = new ArrayList<JSONObject>();   
//					           cs.execute(); 
//					           BigDecimal row_object = (BigDecimal)cs.getObject(3);
//					           System.out.println("row_object:"+row_object);
//					           ResultSet refcursor = (ResultSet) cs.getObject(2);// 获取游标一行的值  
//					           if(row_object.compareTo(new BigDecimal("0"))!=0){
//					        	   while (refcursor.next()) {// 转换每行的返回值到Map中   
//						        	   JSONObject json = new JSONObject();
//						        	   json.accumulate("status",refcursor.getString("status"));
//						        	   resultsJson.add(json);
//						        	   System.out.println("status"+resultsJson.toString());
//						           } 
//					        	   return resultsJson;
//					           }
//					           while (refcursor.next()) {// 转换每行的返回值到Map中   
//					        	   JSONObject json = new JSONObject();
//					        	   json.accumulate("busname",refcursor.getString("busname"));
//					        	   json.accumulate("authcycname",refcursor.getString("authcycname"));
//					        	   json.accumulate("datetime",refcursor.getString("datetime"));
//					        	   json.accumulate("timeslice",refcursor.getString("timeslice"));
//					        	   json.accumulate("proname",refcursor.getString("proname"));
//					        	   resultsJson.add(json);
//					           }   
//					           System.out.println("status"+resultsJson.toString());
//					           refcursor.close();
//					           return resultsJson;
//					            
//				        }
//					});
//
//		return obj; 
//	}
//	@Override
//	public Object putPlantest2(final List<Object[]> object) {
//		// TODO Auto-generated method stub
//		Object[][] obj=new Object[object.size()][8];
//		System.out.println("ID,时间，时间片段，产品，周期，APID，状态,用户");
//		for(int i=0;i<object.size();i++){
//			obj[i]=object.get(i);
//			for(int j=0;j<8;j++){
//				System.out.print(obj[i][j]+",");
//			}
//			System.out.println();
//		}
//		String sql="select * from "
//		@SuppressWarnings("unchecked")
//		Object obj=jdbcTemplate.execute(new CallableStatementCreator(){
//						@Override
//						public CallableStatement createCallableStatement(Connection con) throws SQLException {
//							// TODO Auto-generated method stub
//							String execuSql = "{call batch50(?,?,?)}";
//							CallableStatement cs = con.prepareCall(execuSql);
//							Object[][] obj=new Object[object.size()][8];
//							System.out.println("ID,时间，时间片段，产品，周期，APID，状态,用户");
//							for(int i=0;i<object.size();i++){
//								obj[i]=object.get(i);
//								for(int j=0;j<8;j++){
//									System.out.print(obj[i][j]+",");
//								}
//								System.out.println();
//							}
//							org.apache.commons.dbcp.PoolableConnection  conn=(PoolableConnection) con.getMetaData().getConnection();
//							oracle.jdbc.OracleConnection co=(OracleConnection) conn.getDelegate();
//							ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PUTPARAM",co);
//							ARRAY array = new ARRAY(desc,co,obj);
//							cs.setArray(1,array);
//							 cs.registerOutParameter(2, OracleTypes.CURSOR);// 注册输出参数的类型   
//							 cs.registerOutParameter(3, OracleTypes.NUMBER);// 注册输出参数的类型 
//							return cs;
//						}
//					}, new CallableStatementCallback(){
//				        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException { 
//				        	 List<JSONObject> resultsJson = new ArrayList<JSONObject>();   
//					           cs.execute(); 
//					           BigDecimal row_object = (BigDecimal)cs.getObject(3);
//					           System.out.println("row_object:"+row_object);
//					           ResultSet refcursor = (ResultSet) cs.getObject(2);// 获取游标一行的值  
//					           if(row_object.compareTo(new BigDecimal("0"))!=0){
//					        	   while (refcursor.next()) {// 转换每行的返回值到Map中   
//						        	   JSONObject json = new JSONObject();
//						        	   json.accumulate("status",refcursor.getString("status"));
//						        	   resultsJson.add(json);
//						        	   System.out.println("status"+resultsJson.toString());
//						           } 
//					        	   return resultsJson;
//					           }
//					           while (refcursor.next()) {// 转换每行的返回值到Map中   
//					        	   JSONObject json = new JSONObject();
//					        	   json.accumulate("busname",refcursor.getString("busname"));
//					        	   json.accumulate("authcycname",refcursor.getString("authcycname"));
//					        	   json.accumulate("datetime",refcursor.getString("datetime"));
//					        	   json.accumulate("timeslice",refcursor.getString("timeslice"));
//					        	   json.accumulate("proname",refcursor.getString("proname"));
//					        	   resultsJson.add(json);
//					           }   
//					           System.out.println("status"+resultsJson.toString());
//					           refcursor.close();
//					           return resultsJson;
//					            
//				        }
//					});

	@Override
	public List putPlanTest(List timeList,String[] timeslices, List apids,JSONObject jsonObject) {
		// TODO Auto-generated method stub
		List param  = new ArrayList();
	String sql="SELECT v.code as busname,a.name as authcycname,s.datetime as datetime,t.name as timeslice,p.proname as proname FROM SCHEDULE s "+
        "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
        "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
        "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
         "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
        "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) WHERE  ";
         if(timeList!=null&&timeList.size()>0){
        	 sql+=" s.DATETIME in(";
        	 for(int i=0;i<timeList.size();i++){
        		 sql+="?,";
        		 param.add(timeList.get(i).toString());
        	 }
        	 sql=sql.substring(0, sql.length()-1);
        	 sql+=")";
         }
          sql+=" and ";
         if(timeslices!=null&&timeslices.length>0){
        	 sql+=" s.INTERVAL in(";
        	 for(int i=0;i<timeslices.length;i++){
        		 sql+="?,";
        		 param.add(timeslices[i]);
        	 }
        	 sql=sql.substring(0, sql.length()-1);
        	 sql+=")";
         }
         sql+=" and ";
         if(apids!=null&&apids.size()>0){
        	 sql+=" s.APID in(";
        	 for(int i=0;i<apids.size();i++){
        		 sql+="?,";
        		 param.add(apids.get(i));
        	 }
        	 sql=sql.substring(0, sql.length()-1);
        	 sql+=")";
         }
         
         
         sql+=" and ";
         
         
         Iterator iterator = jsonObject.keys();
         sql+=" s.AUTHORDER in(";
			while(iterator.hasNext()){
	        	String authid = (String) iterator.next();
	        	String product = JSONUtils.getString(jsonObject, authid);//jsonObject.getString(authid);
	        	if(!StringUtils.isEmpty(product)&&!StringUtils.isEmpty(authid)){
//	        		String[] auth=authid.split("authcyc");
	        		sql+="?,";
	        		param.add(authid);
	        	}
			}
       	 sql=sql.substring(0, sql.length()-1);
       	 sql+=")" ;
       	 
       	 String _sql="select * from ("+sql+") where rownum<20";
       	 String[] str=new String[]{"busname","authcycname","datetime","timeslice","proname"};
		return getNoPageObject(_sql, param,str,"测试是否可以投放");
	}

//	@Override
//	public Object putPan(final List<Object[]> object,final long st) {
//		@SuppressWarnings("unchecked")
//		Object obj=jdbcTemplate.execute(new CallableStatementCreator(){
//						@Override
//						public CallableStatement createCallableStatement(Connection con) throws SQLException {
//							// TODO Auto-generated method stub
//							String execuSql = "{call batch51(?,?,?)}";
//							CallableStatement cs = con.prepareCall(execuSql);
//							Object[][] obj=new Object[object.size()][8];
//							for(int i=0;i<object.size();i++){
//								obj[i]=object.get(i);
//							}
//							org.apache.commons.dbcp.PoolableConnection  conn=(PoolableConnection) con.getMetaData().getConnection();
//							oracle.jdbc.OracleConnection co=(OracleConnection) conn.getDelegate();
//							ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PUTPARAM",co);
//							ARRAY array = new ARRAY(desc,co,obj);
//							cs.setArray(1,array);
//							 cs.registerOutParameter(2, OracleTypes.CURSOR);// 注册输出参数的类型   
//							 cs.registerOutParameter(3, OracleTypes.NUMBER);// 注册输出参数的类型 
//							return cs;
//						}
//					}, new CallableStatementCallback(){
//				        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException { 
//				        	 List<JSONObject> resultsJson = new ArrayList<JSONObject>();
//					           cs.execute(); 
//					          
//					           BigDecimal row_object = (BigDecimal)cs.getObject(3);
//					           ResultSet refcursor = (ResultSet) cs.getObject(2);// 获取游标一行的值  
//					           if(row_object.compareTo(new BigDecimal("0"))!=0){
//					        	   while (refcursor.next()) {// 转换每行的返回值到Map中   
//						        	   JSONObject json = new JSONObject();
//						        	   json.accumulate("status",refcursor.getString("status"));
//						        	   resultsJson.add(json);
//						           } 
//					        	   return resultsJson;
//					           }
//					           while (refcursor.next()) {// 转换每行的返回值到Map中   
//					        	   JSONObject json = new JSONObject();
//					        	   json.accumulate("busname",refcursor.getString("busname"));
//					        	   json.accumulate("authcycname",refcursor.getString("authcycname"));
//					        	   json.accumulate("datetime",refcursor.getString("datetime"));
//					        	   json.accumulate("timeslice",refcursor.getString("timeslice"));
//					        	   json.accumulate("proname",refcursor.getString("proname"));
//					        	   resultsJson.add(json);
//					           }   
//					           refcursor.close();
//					           return resultsJson;
//				        }
//					});
//		return obj; 
//	}

	@Override
	public int putPanSave(List<Object[]> obj) {
		// TODO Auto-generated method stub
		String sql=" INSERT INTO SCHEDULE "+
            "(ID,DATETIME,INTERVAL,PRODUCTID,AUTHORDER,APID,STATE,CREATOR,CREATTIME) "+
            "VALUES "+
            "(SCHEDULE_SEQ.NEXTVAL,?,?,?,?,?,?,?, "+
            "TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')) ";
    	long st = System.currentTimeMillis();
		int[] result=jdbcTemplate.batchUpdate(sql,obj);
		long st2 = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+"排期::"+st2+"   用时："+(st2-st));
		return result.length;
	}

	@Override
	public int putPanSaveLog(List<Object[]> obj) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO SCHEDULELOG "+
         "(ID,DATETIME,INTERVAL,PRODUCTID,AUTHORDER,APID,STATE,CREATOR,CREATTIME) "+
       "VALUES "+
         "(SCHEDULELOG_SEQ.NEXTVAL, "+
         "?,?,?,?,?,?,?, "+
         "TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')) ";
    	long st = System.currentTimeMillis();
		int[] result=jdbcTemplate.batchUpdate(sql,obj);
		long st2 = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+"日志::"+st2+"   用时："+(st2-st));
		return result.length;
	}

	


}
