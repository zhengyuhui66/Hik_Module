package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Deprecated
@Repository
public class AdvertPlanDaoImpl extends BaseHIKDao implements IAdvertPlanDao{
	
//	@Autowired
//	protected EntityManagerFactory entityManagerFactory;
//	
//	public List queryRoadInfo(){
//		// TODO Auto-generated method stub
//		String sql="select DISTINCT routename from VEHICLE ORDER BY routename";
//		return getNoPageObject(sql);
//	}
//	
//	public List queryBusInfos(String routeId){
//		List param = new ArrayList();
//		param.add(routeId);	
//		String sql="select to_char(v.id) from VEHICLE v where v.routename=?";
//		return getNoPageObject(sql, param);
//	}
//	
//	public List queryBusInfo(String lineId) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(lineId);		
//		param.add(lineId);
//		String[] str = new String[]{"id","code","routename","brand","model", "color","name","modelid","pname","pmodelid","pause","ppause"};
//
//		String sql="WITH LOGIN_SET AS "+
//				 "(SELECT L.* "+
//			    "FROM (SELECT BUS_ID, MAX(SETTING_TIME) AS MAXTIME "+
//			            "FROM LOGIN_SETTING "+
//			           "GROUP BY BUS_ID) M, "+
//			         "LOGIN_SETTING L "+
//			   "WHERE M.BUS_ID = L.BUS_ID "+
//			     "AND M.MAXTIME = L.SETTING_TIME), "+
//			"T AS "+
//			 "(SELECT DISTINCT V.ID,V.CODE,V.ROUTENAME,V.BRAND,V.MODEL,V.COLOR,A.NAME,A.MODELID,L.PAUSE_FLAG "+
//			    "FROM VEHICLE V "+
//			    "LEFT JOIN BUSADVERTMANGER B "+
//			      "ON (B.BUS_ID = V.ID AND B.STATE = '1' AND "+
//			         "B.STIME <= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AND "+
//			         "B.ETIME >= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')) "+
//			    "LEFT JOIN ADVERTMODELMANGER A "+
//			      "ON (B.MODEL_ID = A.MODELID) "+
//			    "LEFT JOIN LOGIN_SET L "+
//			     " ON (L.BUS_ID = V.ID AND L.MODELID IS NULL AND L.MODELMODEID IS NULL) "+
//			   "WHERE V.ROUTENAME = ? "+
//			   "ORDER BY V.ID), "+
//			"LOGINED_SET AS "+
//			 "(SELECT L.* "+
//			    "FROM (SELECT BUS_ID, MAX(SETTING_TIME) AS MAXTIME "+
//			            "FROM LOGINED_SETTING "+
//			           "GROUP BY BUS_ID) M, "+
//			         "LOGINED_SETTING L "+
//			   "WHERE M.BUS_ID = L.BUS_ID "+
//			     "AND M.MAXTIME = L.SETTING_TIME), "+
//			"TS AS "+
//			 "(SELECT DISTINCT V.ID,V.CODE,V.ROUTENAME,V.BRAND,V.MODEL,V.COLOR,A.NAME,A.MODELID,L.PAUSE_FLAG "+
//			    "FROM VEHICLE V "+
//			    "LEFT JOIN LOGINED_BUSADVERTMANGER B "+
//			      "ON (B.BUS_ID = V.ID AND B.STATE = '1' AND "+
//			         "B.STIME <= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AND "+
//			         "B.ETIME >= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')) "+
//			    "LEFT JOIN ADVERTMODELMANGER A "+
//			      "ON (B.MODEL_ID = A.MODELID) "+
//			    "LEFT JOIN LOGINED_SET L "+
//			      "ON (L.BUS_ID = V.ID AND L.MODELID IS NULL AND L.MODELMODEID IS NULL) "+
//			  "WHERE V.ROUTENAME = ? "+
//			   "ORDER BY V.ID) "+
//			"SELECT T.ID,T.CODE,T.ROUTENAME,T.BRAND,T.MODEL,T.COLOR,T.NAME,T.MODELID,TS.NAME AS TSNAME,TS.MODELID AS TSMODELID,T.PAUSE_FLAG,TS.PAUSE_FLAG AS TSPAUSE_FLAG "+
//			  "FROM T, TS "+
//			 "WHERE T.ID = TS.ID";
//
////		String sql="WITH T AS "+
////				  "(SELECT DISTINCT V.ID,V.CODE,V.ROUTENAME,V.BRAND,V.MODEL,V.COLOR,A.NAME,A.MODELID,l.pause_flag "+ 
////				     "FROM VEHICLE V "+ 
////				     "LEFT JOIN BUSADVERTMANGER B "+
////				       "ON (B.BUS_ID = V.ID AND B.STATE = '1' AND "+ 
////				          "B.STIME <= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AND "+ 
////				          "B.ETIME >= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')) "+ 
////				     "LEFT JOIN ADVERTMODELMANGER A "+ 
////				       "ON (B.MODEL_ID = A.MODELID) "+ 
////				     "LEFT JOIN login_setting l ON(l.bus_id=v.id AND l.modelid IS NULL AND l.modelmodeid IS NULL) "+
////				    "WHERE V.ROUTENAME =? "+ 
////				    "ORDER BY V.ID), "+ 
////				 "TS AS "+ 
////				  "(SELECT DISTINCT V.ID,V.CODE,V.ROUTENAME,V.BRAND,V.MODEL,V.COLOR,A.NAME,A.MODELID,l.pause_flag  "+
////				     "FROM VEHICLE V "+ 
////				     "LEFT JOIN LOGINED_BUSADVERTMANGER B "+ 
////				       "ON (B.BUS_ID = V.ID AND B.STATE = '1' AND "+ 
////				          "B.STIME <= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AND "+ 
////				          "B.ETIME >= TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')) "+ 
////				     "LEFT JOIN ADVERTMODELMANGER A "+ 
////				       "ON (B.MODEL_ID = A.MODELID) "+ 
////				     "LEFT JOIN logined_setting l ON(l.bus_id=v.id AND l.modelid IS NULL AND l.modelmodeid IS NULL)  "+
////				    "WHERE V.ROUTENAME =? "+ 
////				    "ORDER BY V.ID) "+ 
////				 "SELECT T.ID,T.CODE,T.ROUTENAME,T.BRAND,T.MODEL,T.COLOR,T.NAME,T.MODELID,TS.NAME AS TSNAME,TS.MODELID AS TSMODELID,t.pause_flag,ts.pause_flag AS tspause_flag  "+
////				   "FROM T, TS "+ 
////				  "WHERE T.ID = TS.ID ";
//		return getNoPageObject(sql, param, str, "查询车辆信息");
//	}
//
////	public List queryAllModel(String cycid){
////		String[] str=new String[]{"id","name"};
////		String sql="SELECT DISTINCT id,NAME FROM ADVERTMODELMANGER";
////		if(cycid!=null){
////			List param = new ArrayList();
////			param.add(cycid);
////			sql+=" where cycid=?";
////			return getNoPageObject(sql, param,str,"查询所有的广告模版");
////		}
////		return getNoPageObject(sql, str,"查询所有的广告模版");
////	}
//
//	public int updateAdvertPlan(String stime, String etime, String modelId, String busId) {
//		 		List param = new ArrayList();
//		param.add(modelId);
//		param.add(stime);
//		param.add(etime);
//		param.add(busId);
//		String sql="INSERT INTO BUSADVERTMANGER(ID,MODEL_Id,Stime,etime,bus_id) VALUES(bus_advert_sequences.nextval,?,?,?,?,)";
//		return executeUpdateSQL(sql, param);
//	}
//	
//	public int updateLoginedAdvertPlan(String stime, String etime, String modelId, String busId){
// 		List param = new ArrayList();
//		param.add(modelId);
//		param.add(stime);
//		param.add(etime);
//		param.add(busId);
//		String sql="INSERT INTO LOGINED_BUSADVERTMANGER(ID,MODEL_Id,Stime,etime,bus_id) VALUES(BUS_lOGINEDADVERT_SQE.nextval,?,?,?,?,)";
//		return executeUpdateSQL(sql, param);
//	}
//	
//	public boolean getProduceUpdateBusId(String stime,String etime,String busId,String modelId,String modelmodeId,String advertId){
//		EntityManager entityManager2 =entityManagerFactory.createEntityManager();
//		StoredProcedureQuery storedProcedure = entityManager2.createStoredProcedureQuery("updateBuidInfo2");
//		storedProcedure.registerStoredProcedureParameter("s_time", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("e_time", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("busid", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("modelids", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("modelmodeId", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("advertId", String.class, ParameterMode.IN);
//		
//		storedProcedure.setParameter("s_time", stime);
//		storedProcedure.setParameter("e_time", etime);
//		storedProcedure.setParameter("busid", busId);
//		storedProcedure.setParameter("modelids", modelId);
//		storedProcedure.setParameter("modelmodeId", modelmodeId);
//		storedProcedure.setParameter("advertId", advertId);
//		try{
//			storedProcedure.execute();
//	    } catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}finally{
//			entityManager2.close();			
//		}
//			return true;
//	}
//
//	public boolean getLoginedProduceUpdateBusId(String stime,String etime,String busId,String modelId,String modelmodeId,String advertId){
//		EntityManager entityManager2 =entityManagerFactory.createEntityManager();
//		StoredProcedureQuery storedProcedure = entityManager2.createStoredProcedureQuery("LOGINEDUPDATEBUIDINFO2");
//		storedProcedure.registerStoredProcedureParameter("s_time", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("e_time", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("busid", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("modelids", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("modelmodeId", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("advertId", String.class, ParameterMode.IN);
//		
//		storedProcedure.setParameter("s_time", stime);
//		storedProcedure.setParameter("e_time", etime);
//		storedProcedure.setParameter("busid", busId);
//		storedProcedure.setParameter("modelids", modelId);
//		storedProcedure.setParameter("modelmodeId", modelmodeId);
//		storedProcedure.setParameter("advertId", advertId);
//		try{
//			storedProcedure.execute();
//	    } catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}finally{
//			entityManager2.close();			
//		}
//			return true;
//	}
//
//	
//	
//	public List getBusAdvertPlanHistory(String busid) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(busid);	
//		String[] str = new String[]{"name","modename","stime","etime","advertid","advertUrl","advertName","state"};
//		String sql="SELECT A.NAME, A.MODENAME, B.STIME, B.ETIME, AD.ID, AD.ADVERT_URL, AD.NAME as advertName,b.state "+
//		  "FROM BUSADVERTMANGER B "+
//		  "LEFT JOIN ADVERTMODELMANGER A "+
//		    "ON (A.MODELID = B.MODEL_ID AND A.MODELMODEID = B.MODEL_MODE_ID) "+
//		  "LEFT JOIN ADVERTMANGER AD "+
//		    "ON (AD.ID = B.ADVERT_ID) "+
//		 "WHERE B.BUS_ID = ? "+
//		 "ORDER BY b.state desc,B.STIME DESC ";
//		return getNoPageObject(sql, param, str,"单车辆的历史记录");
//	}
//	
//	public List getBusLoginedAdvertPlanHistory(String busid) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(busid);	
//		String[] str = new String[]{"name","modename","stime","etime","advertid","advertUrl","advertName","state"};
//		String sql="SELECT A.NAME, A.MODENAME, B.STIME, B.ETIME, AD.ID, AD.ADVERT_URL, AD.NAME as advertName,b.state "+
//		  "FROM LOGINED_BUSADVERTMANGER B "+
//		  "LEFT JOIN ADVERTMODELMANGER A "+
//		    "ON (A.MODELID = B.MODEL_ID AND A.MODELMODEID = B.MODEL_MODE_ID) "+
//		  "LEFT JOIN ADVERTMANGER AD "+
//		    "ON (AD.ID = B.ADVERT_ID) "+
//		 "WHERE B.BUS_ID = ? "+
//		 "ORDER BY b.state desc,B.STIME DESC ";
//		return getNoPageObject(sql, param, str,"登录后单车辆的历史记录");
//	}
//	
//
//	public List queryModelModeById(String modelid){
//		// TODO Auto-generated method stub
//		List<String> param = new ArrayList<String>();
//		param.add(modelid);
//		param.add(modelid);
//		param.add(modelid);
//		param.add(modelid);
//		param.add(modelid);
//		String[] str = new String[]{"advposid","advposname","advertid"};
//		logger.info("查询到的modelid======>"+modelid);
////		String sql="select modelmodeid,modename,'请选择...' as advertid from ADVERTMODELMANGER t WHERE id=?";
//		String sql="SELECT advposid1,advposname1,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid1 IS NOT NULL "+
//					"Union ALL "+
//					"SELECT advposid2,advposname2,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid2 IS NOT NULL "+
//					"Union ALL "+
//					"SELECT advposid3,advposname3,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid3 IS NOT NULL "+
//					"Union ALL "+
//					"SELECT advposid4,advposname4,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid4 IS NOT NULL "+
//					"Union ALL "+
//					"SELECT advposid5,advposname5,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid5 IS NOT NULL";
//		return getNoPageObject(sql, param, str, "查询子模块");
//	}
//
//	public List queryAdvidByAdvName(String advName) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(advName);
//		String sql="select t.id from ADVERTMANGER t WHERE t.name=?";
//		return getNoPageObject(sql, param);
//	}
//	
//	public int cancelPutAdvert(List busids) {
//		// TODO Auto-generated method stub
//		String sql="UPDATE BUSADVERTMANGER SET state='0' WHERE bus_id in (";
//		for(int i=0;i<busids.size();i++){
//			sql+="?,";
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=")";
//		return executeUpdateSQL(sql, busids);
//	}
//	public int cancelPutLoginedAdvert(List busids) {
//		// TODO Auto-generated method stub
//		String sql="UPDATE logined_busadvertmanger SET state='0' WHERE bus_id in (";
//		for(int i=0;i<busids.size();i++){
//			sql+="?,";
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=")";
//		return executeUpdateSQL(sql, busids);
//	}
//	public int pausePutAdvert(String busid,String value) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(busid);
//		param.add(value);
//		String sql="INSERT INTO LOGIN_SETTING(ID,bus_id,setting_time,pause_flag) VALUES(login_setting_seq.nextval,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
//		return executeUpdateSQL(sql, param,"暂停sql:");
//	}
//	public int pausePutLoginedAdvert(String busid,String value) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(busid);
//		param.add(value);
//		String sql="INSERT INTO LOGINed_SETTING(ID,bus_id,setting_time,pause_flag) VALUES(logined_setting_seq.nextval,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
//		return executeUpdateSQL(sql, param,"暂停sql:");
//	}
//	public List queryBusidByRouteIds(List routeids) {
//		// TODO Auto-generated method stub
//		String sql="select ID from VEHICLE t WHERE routename IN(";
//		for(int i=0;i<routeids.size();i++){
//			sql+="?,";
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=")";
//		return getNoPageObject(sql, routeids);
//	}
}
