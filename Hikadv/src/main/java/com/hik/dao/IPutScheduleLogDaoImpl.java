package com.hik.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

@Repository
public class IPutScheduleLogDaoImpl extends BaseHIKDao implements IPutScheduleLogDao{
	
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Page queryPutLog(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="SELECT s.id,v.code as busname,a.name as authcycname,vd.apmac,s.datetime as datetime,t.name as timeslice,p.proname as proname,su.user_name,s.creattime,"
				+ " decode(s.state,'1','正常投放','2','暂停投放','3','取消投放','其它状态') as state FROM SCHEDULELOG s "+ 
		              "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
		              "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
		              "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
		              "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
			           "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "+
		              "LEFT JOIN Sys_User su ON(su.userid=s.creator)";
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			  sql+=" where "+
				   "v.code like ? or "+
			       "a.name like ? or "+
			       "vd.apmac like ? or "+
			       "s.datetime like ? or "+
			       "t.name like ? or "+
			       "p.proname like ? or "+
			       "su.user_name like ? or "+
			       "s.creattime like ? or "+
			       "decode(s.state,'1','正常投放','2','暂停投放','3','取消投放','其它状态') like ?";
			for(int i=0;i<9;i++){
				param.add(temparam);
			}
		}
		sql+=" order by s.creattime desc";
		String[] jsonStr=new String[]{"id","busname","authcycname","apmac","datetime","timeslice","proname","creator","creattime","state"};
		Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询排期日志");
		return page;
	}
	
	public int[] savePutLog(List<JSONObject> list,String userid){
		String sql="insert into SCHEDULELOG(id,datetime,interval,productid,authorder,creator,creattime,state,apid) values(SCHEDULELOG_SEQ.nextval,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?,?)";
		List params = new ArrayList();
		for(JSONObject json:list){
			Object[] obj = new Object[7];
			obj[0]=JSONUtils.getObject(json, "datetime");
			obj[1]=JSONUtils.getObject(json, "interval");
			obj[2]=JSONUtils.getObject(json, "productid");
			obj[3]=JSONUtils.getObject(json, "authorder");
			obj[4]=userid;
			obj[5]=JSONUtils.getObject(json, "state");
			obj[6]=JSONUtils.getObject(json, "apid");
			params.add(obj);
		}
		int[] result=jdbcTemplate.batchUpdate(sql, params);
		return result;
	}
}
