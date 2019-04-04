package com.hik.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.SHOW_COUNT_LOG;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.OracleTypes;

@Repository
public class SendSMSCodeDaoImpl extends BaseHIKDao implements SendSMSCodeDao{
	protected Log log = LogFactory.getLog(this.getClass()); 
	
	
//	@Autowired
//	@Qualifier(value="smsDataSource")
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbc;

//	public void sendSMSCodeDao(String phone,String smsContent,String time,int type){
//		// TODO Auto-generated method stub
//		jdbcTemplate.update( "insert into sms_sxggjtjt_send(Col_receiver,Col_content,Col_sdate,Col_type) values(?,?,?,?)" ,
//                new Object[]{phone,smsContent,time,type},
//                new int[]{java.sql.Types.VARCHAR,java.sql.Types.VARCHAR,java.sql.Types.DATE,java.sql.Types.INTEGER});
//	}

	public List queryBusId(String mac) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(mac);
		String[] str = new String[]{"url","modelid","materid","advertId","advertUrl","materPath","modelmodeid","modelskin"};
        String sql=
        		"WITH MOTALL AS "+
        		 "(SELECT B.* "+
        		    "FROM BUSADVERTMANGER B "+
        		   "WHERE B.BUS_ID = (SELECT VEHICLEID "+
        		                       "FROM DEVICE D "+
        		                      "WHERE D.APMAC =?) "+
        		     "AND B.STIME < TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
        		     "AND B.ETIME > TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
        		     "AND B.STATE = 1) "+
        		"SELECT A.URL,A.MODELID,T.MATER_ID,T.id,T.ADVERT_URL, M.MATER_PATH, A.MODELMODEID,k.skinname "+
        		  "FROM MOTALL "+
        		  "LEFT JOIN ADVERTMODELMANGER A "+
        		    "ON (MOTALL.MODEL_ID = A.MODELID AND "+
        		       "MOTALL.MODEL_MODE_ID = A.MODELMODEID) "+
        		  "LEFT JOIN ADVERTMANGER T "+
        		    "ON (MOTALL.ADVERT_ID = T.ID) "+
        		  "LEFT JOIN MATERIEL_INFO M "+
        		    "ON (M.MATER_ID = T.MATER_ID) "+
    				"LEFT JOIN SKIN_MANGER k ON "+
    				"(A.modelskin=k.id) ";
		return getNoPageObject(sql, param,str,"根据MAC查询车辆ID");
	}
	
	public List queryLoginedBusId(String mac) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(mac);
		String[] str = new String[]{"url","modelid","materid","advertId","advertUrl","materPath","modelmodeid","modelskin"};
        String sql=
        		"WITH MOTALL AS "+
        		 "(SELECT B.* "+
        		    "FROM LOGINED_BUSADVERTMANGER B "+
        		   "WHERE B.BUS_ID = (SELECT VEHICLEID "+
        		                       "FROM DEVICE D "+
        		                      "WHERE D.APMAC =?) "+
        		     "AND B.STIME < TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
        		     "AND B.ETIME > TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
        		     "AND B.STATE = 1) "+
        		"SELECT A.URL,A.MODELID,T.MATER_ID,T.id,T.ADVERT_URL, M.MATER_PATH, A.MODELMODEID,k.skinname "+
        		  "FROM MOTALL "+
        		  "LEFT JOIN ADVERTMODELMANGER A "+
        		    "ON (MOTALL.MODEL_ID = A.MODELID AND "+
        		       "MOTALL.MODEL_MODE_ID = A.MODELMODEID) "+
        		  "LEFT JOIN ADVERTMANGER T "+
        		    "ON (MOTALL.ADVERT_ID = T.ID) "+
        		  "LEFT JOIN MATERIEL_INFO M "+
        		    "ON (M.MATER_ID = T.MATER_ID) "+
    				"LEFT JOIN SKIN_MANGER k ON "+
    				"(A.modelskin=k.id) ";
		return getNoPageObject(sql, param,str,"根据MAC查询车辆ID");
	}

	public List<JSONObject> queryAdvertUrl(String advertId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(advertId);
		String[] str = new String[]{"materId","advertUrl"};
		String sql="select mater_id,advert_url from ADVERTMANGER t where t.id=?";
		return getNoPageObject(sql, param,str,"根据广告信息查询广告地址");
	}

	public int saveClickCount(CLICK_COUNT_LOG logg) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(logg.getClick_time());
		param.add(logg.getBusid());
		param.add(logg.getApmac());
		param.add(logg.getPhone());
		param.add(logg.getPhonemac());
		param.add(logg.getModelid());
		param.add(logg.getModelmodeid());
		param.add(logg.getMaterid());
		param.add(logg.getAdvert_url());
		param.add(logg.getAdvertid());
		String sql="insert into CLICK_COUNT_LOG(id,click_time,busid,apmac,phone,phonemac,modelid,modelmodeid,materid,advert_url,advertid) values(CLICKOUNTLOG_SEQ.nextval,?,?,?,?,?,?,?,?,?,?)";
		int t=executeUpdateSQL(sql, param, "记录点击日志");

		return t;
	}

	
	public List queryBusiByMAC(String mac){
		List param = new ArrayList();
		param.add(mac);
		String sql="select vehicleid from DEVICE t WHERE apmac=?";
		return getNoPageObject(sql, param, "");
	}

	public int saveShowCount(SHOW_COUNT_LOG showlogs) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(showlogs.getShow_time());
		param.add(showlogs.getBusid());
		param.add(showlogs.getApmac());
		param.add(showlogs.getPhone());
		param.add(showlogs.getPhonemac());
		param.add(showlogs.getModelid());
		param.add(showlogs.getModelmodeid());
		param.add(showlogs.getMaterid());
		param.add(showlogs.getAdvert_url());
		param.add(showlogs.getAdvertid());
		String sql="INSERT INTO SHOW_COUNT_LOG(ID,show_time,busid,apmac,phone,phonemac,Modelid,modelmodeid,Materid,Advert_Url,advertid) "+
				   "VALUES  "+
				   "(SHOWCOUNTLOG_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?) ";
		int t=executeUpdateSQL(sql, param,"记录查询次数");
		return t;
	}

	public List queryPausedByMac(String mac) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(mac);
		String sql="SELECT pause_flag "+
				  "FROM (SELECT * "+
				          "FROM LOGIN_SETTING T "+
				         "WHERE BUS_ID IN (SELECT VEHICLEID "+
                            "FROM DEVICE T "+
                           "WHERE APMAC = ?) "+
				         "ORDER BY SETTING_TIME DESC) "+
				 "WHERE ROWNUM <= 1";
		return getNoPageObject(sql, param, "查询认证界面当前的暂停情况");
	}

	public List queryLoginedPausedByMac(String mac) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(mac);
		String sql="SELECT pause_flag "+
				  "FROM (SELECT * "+
				          "FROM LOGINed_SETTING T "+
				         "WHERE BUS_ID IN (SELECT VEHICLEID "+
                            "FROM DEVICE T "+
                           "WHERE APMAC = ?) "+
				         "ORDER BY SETTING_TIME DESC) "+
				 "WHERE ROWNUM <= 1";
		 
		return getNoPageObject(sql, param, "查询认证成功后当前的暂停情况");
	}

	public int saveRecordNet(String phone_mac, String phone,String apmac) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(phone_mac);
		param.add(phone);
		param.add(apmac);
		String sql="INSERT INTO WIFIAUTH(ID,mac,phone,deviceid,authtime,createtime) values(wifiauth_seq.nextval,?,?,(select ID from device t where apmac=?),to_date(to_char(SYSDATE, 'yyyy-MM-dd hh24:mi:ss'),'yyyy-MM-dd hh24:mi:ss'), to_date(to_char(SYSDATE, 'yyyy-MM-dd hh24:mi:ss'),'yyyy-MM-dd hh24:mi:ss'))";
		return executeUpdateSQL(sql, param,"插入WIFIauth的语句是");
	}

//	public int getWifiAuthId() {
//		// TODO Auto-generated method stub
//		String sql="select wifiauth_seq.nextval from dual";
//		return executeUpdateSQL(sql);
//		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("SP_GETTABLESEQUENCE");
//		storedProcedure.registerStoredProcedureParameter("TABLE_NAME", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("TABLE_PREFIX", String.class, ParameterMode.IN);
//		storedProcedure.registerStoredProcedureParameter("TABLE_SEQUENCE", Integer.class, ParameterMode.OUT);
//		storedProcedure.setParameter("TABLE_NAME","WIFIAUTH");
//		storedProcedure.setParameter("TABLE_PREFIX","");
//		Object obj=storedProcedure.getOutputParameterValue("TABLE_SEQUENCE");
//		int result=Integer.parseInt(obj.toString());
//		return result;
		
//	}

	public List getLineIdByMac(String mac) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(mac);
		String sql="select routeid from VEHICLE t WHERE t.id=(select ID from DEVICE t WHERE apmac=?)";
		return getNoPageObject(sql, param, "依据mac查询线路");
	}

//	@Override
//	public int saveRecordNet(String phone_mac, String phone, String apmac) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
