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
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.DEVICEONOFFLOG;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;

@Repository("iDeviceOfflineDaoImpl")
@Transactional
public class IDeviceOfflineDaoImpl extends BaseHIKDao implements IDeviceOfflineDao{

	@Override
	public Page queryDeviceOffline(int start, int limit, String searchStr){
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","equipmentid","apmac","vehicleid","busname","routeid","routename","onlinetime","offlinetime","alarmtype","isoffline"};
		String sql="SELECT t.id,"+
		 	"D.EQUIPMENTID, "+
			"D.APMAC, "+
			"V.ID as busid, "+
			"V.CODE, "+
			"R.ID as routeid, "+
			"R.NAME, "+
			"T.ONLINETIME, "+
			"T.OFFLINETIME, "+
			"T.ALARMTYPE, "+
			"T.ISOFFLINE "+
			"FROM DEVICEONOFFLOG T "+
			"LEFT JOIN DEVICE D "+
			"ON (T.DEVICEID = D.equipmentid) "+
			"LEFT JOIN VEHICLE V "+
			"ON (D.VEHICLEID = V.ID) "+
			"LEFT JOIN ROUTE R "+
			"ON (R.ID = V.routeid)";
	  
		if(StringUtils.isNotEmpty(searchStr)){
			if("在线".equals(searchStr)||"离线".equals(searchStr)){
				String str="在线".equals(searchStr)?"true":"false";
				sql+="where T.ISOFFLINE= ?";
				param.add(str);
			}else{
				String temparam = "%"+searchStr+"%";
				sql+="where d.equipmentid like ? or "+
			       "d.apmac like ? or "+
			       "v.code like ? or "+
			       "r.name like ? or "+
			       "t.onlinetime like ? or "+
			       "t.offlinetime like ? or "+
			       "t.alarmtype like ? or "+
			       "t.isoffline like ?";
				for(int i=0;i<8;i++){
					param.add(temparam);
				}
			}
			
		}
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询设备上下线信息");			
		}
		return page;
	}

	@Override
	public int addDeviceOnline(String deviceid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(deviceid);
		String sql="insert into DEVICEONOFFLOG(id,deviceid,onlinetime,offlinetime,alarmtype,isoffline) values(DEVICEONOFFLOG_seq.nextval,?,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),null,null,'true')";
		return executeUpdateSQL(sql, param);
	}

	@Override
	public int updateDeviceOffline(String id) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(id);
		String sql="update DEVICEONOFFLOG set offlinetime=to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),isoffline='false' where id=?";
		return executeUpdateSQL(sql, param);  
	}
	
	@Override
	public List queryByEquipment(String equipmentid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(equipmentid);
		String sql="SELECT id FROM (select ID from DEVICEONOFFLOG t WHERE deviceid=? ORDER BY onlinetime desc) WHERE ROWNUM=1";
		String[] jsonStr=new String[]{"id"};
		return getNoPageObject(sql, param, jsonStr);
	}

	@Override
	public List queryDeviceOffline(String equipmentid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(equipmentid);
		String sql="select d.onlinetime,d.offlinetime from DEVICEONOFFLOG d  WHERE d.deviceid=? ORDER BY d.onlinetime desc";
		String[] str = new String[]{"onlinetime","offlinetime"};
		return getNoPageObject(sql, param, str);
	}
	
	

//	@Override
//	public List queryByEquipment(String equipmentid) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
