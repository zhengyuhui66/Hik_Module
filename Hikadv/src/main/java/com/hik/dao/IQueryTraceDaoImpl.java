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
import com.hik.app.entity.CORP;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;

@Repository
public class IQueryTraceDaoImpl extends BaseHIKDao implements IQueryTraceDao{

	@Override
	public List queryTrace(String stime, String etime, String equipmentid) {
		// TODO Auto-generated method stub
		List param =new ArrayList();
		param.add(equipmentid);
		param.add(stime);
		param.add(etime);
		String[] times = stime.split("-");
		String date = times[0]+times[1];
		String[] str = new String[]{"deviceid","apmac","busid","busname","routeid","routename","longitude","latitude","capturetime"};
		String sql="SELECT D.id  AS deviceid,"+
			"D.APMAC,"+
			"V.ID AS busid,"+
			"V.CODE,"+
			"R.ID  as routeid,"+
			"R.NAME,"+
			"DK.LONGITUDE,"+
			"DK.LATITUDE,"+
			"DK.CAPTURETIME "+
			"FROM DEVICETRACK"+date+" DK "+
			"LEFT JOIN DEVICE D "+
			"ON (DK.DEVICEID = D.equipmentid) "+
			"LEFT JOIN VEHICLE V "+
			"ON (D.VEHICLEID = V.ID) "+
			"LEFT JOIN ROUTE R "+
			"ON (V.ROUTEID = R.ID) "+
			"WHERE D.equipmentid = ? "+
//			"WHERE V.CODE = ? "+
			"AND dk.capturetime>? "+
			"AND dk.capturetime<? order by capturetime";
		List result= getNoPageObject(sql, param, str,"²éÑ¯¹ì¼£");
		return result;
	}

	@Override
	public int addTrace(String date,String captime, String longitude, String latidude, String equipmentid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(equipmentid);
		param.add(longitude);
		param.add(latidude);
		param.add(captime);
		String sql="insert into DEVICETRACK"+date+"(DEVICEID,LONGITUDE,LATITUDE,CAPTURETIME) values(?,?,?,?)";
		return executeUpdateSQL(sql, param);
	}
}
