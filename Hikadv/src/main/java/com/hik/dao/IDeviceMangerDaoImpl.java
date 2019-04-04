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
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;

@Repository("iDeviceMangerDaoImpl")
@Transactional
public class IDeviceMangerDaoImpl extends BaseHIKDao implements IDeviceMangerDao{

	@Override
	public Page queryDeviceManger(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","equipmentid","apmac","vehicleid","busname","owercode","routename","routeid","isonline","onlinetime","offlinetime","lastonlinetime","lastreporttime","reporttime","state","creator","modifier","createtime","modifytime","mark","ver","speed","timeout"};
		
		String sql="WITH t AS (SELECT D.DEVICEID, D.ONLINETIME, D.OFFLINETIME "+
				  "FROM DEVICEONOFFLOG D "+
				 "RIGHT JOIN (SELECT DEVICEID, MAX(ONLINETIME) AS ONLINETIMES "+
				               "FROM DEVICEONOFFLOG "+
				              "GROUP BY DEVICEID) S "+
				   " ON (D.DEVICEID = S.DEVICEID AND D.ONLINETIME = S.ONLINETIMES) "+
				") "+
				"SELECT D.ID,  "+
				         "D.EQUIPMENTID,  "+
				         "D.APMAC,  "+
				         "D.VEHICLEID,  "+
				         "v.code AS busname,  "+
				         "v.owercode, "+
				         "r.name AS routename,  "+
				         "r.id AS routeid,  "+
				         "D.ISONLINE,  "+
				         "t.ONLINETIME, "+
				         "t.OFFLINETIME, "+
				         "D.LASTONLINETIME,  "+
				         "D.LASTREPORTTIME,  "+
				         "D.REPORTTIME,  "+
				         "D.STATE,  "+
				         "D.CREATOR,  "+
				         "D.MODIFIER,  "+
				         "D.CREATETIME,  "+
				         "D.MODIFYTIME,  "+
				         "D.MARK,  "+
				         "D.VER,  "+
				         "D.SPEED,  "+
				         "D.TIMEOUT  "+
				    "FROM DEVICE D  "+
				    "LEFT JOIN vehicle v ON(d.vehicleid=v.id)  "+
				    "LEFT JOIN route r ON(r.id=v.routeid) "+
				   " LEFT JOIN t ON(t.DEVICEID=D.EQUIPMENTID) ";
	  
		if(StringUtils.isNotEmpty(searchStr)){
			if("在线".equals(searchStr)||"离线".equals(searchStr)){
				String str="在线".equals(searchStr)?"1":"0";
				sql+="where d.isonline= ?";
				param.add(str);
			}else if("正常".equals(searchStr)||"注销".equals(searchStr)||"未登记".equals(searchStr)){
				
				String tempi="0";
				if("正常".equals(searchStr)){
					tempi="1";
				}else if("注销".equals(searchStr)){
					tempi="0";
				}else if("未登记".equals(searchStr)){
					tempi="2";
				}
				sql+="where d.state= ?";
				param.add(tempi);
			}else{
				String temparam = "%"+searchStr+"%";
				sql+="where d.equipmentid like ? or "+
			       "d.apmac like ? or "+
			       "v.code like ? or "+
			       "r.name like ? or "+
			       "v.owercode like ? or "+
			       "d.reporttime like ? or "+
			       "d.creator like ? or "+
			       "d.createtime like ? or "+
			       "d.modifytime like ? or "+
			       "d.speed like ? or "+
			       "d.timeout like ? or "+
			       "d.ver like ? or "+
			       "d.modifier like ? ";
				for(int i=0;i<13;i++){
					param.add(temparam);
				}
			}
			
		}
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询设备");			
		}
		return page;
	}
	
	@Override
	public String deleteDeviceManger(String[] ids) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="delete from device where id in(";
		for(int i=0;i<ids.length;i++){
			param.add(ids[i]);
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		int str =executeUpdateSQL(sql, param);
		return str+"";
	}

	@Override
	public int addDeviceManger(DEVICE device) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(device.getEquipmentid());
		param.add(device.getApmac());
		param.add(device.getState());
		param.add(device.getVehicleid());
		param.add(device.getReporttime());
		param.add(device.getCreator());
		param.add(device.getCreatetime());
		param.add(device.getMark());
		param.add(device.getSpeed());
		param.add(device.getTimeout());
		String sql="insert into device(id,equipmentid,apmac,state,vehicleid,reporttime,creator,createtime,mark,speed,timeout) values(device_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
		return executeUpdateSQL(sql, param,"添加设备信息");
	}
	@Override
	public int addDeviceManger_unregister(DEVICE device) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(device.getEquipmentid());
		param.add(device.getApmac());
		param.add(device.getIsonline());
		param.add(device.getReporttime());
		param.add(device.getState());
		param.add(device.getMark());
		param.add(device.getVar());
		param.add(device.getSpeed());
		param.add(device.getTimeout());
		String sql="insert into device(id,equipmentid,apmac,isonline,reporttime,state,mark,ver,speed,timeout) values(device_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";
		return executeUpdateSQL(sql, param,"添加设备信息");
	}
	@Override
	public int updateDeviceManger(DEVICE device) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(device.getEquipmentid());
		param.add(device.getApmac());
		param.add(device.getState());
		param.add(device.getVehicleid());
		param.add(device.getReporttime());
		param.add(device.getCreator());
		param.add(device.getCreatetime());
		param.add(device.getMark());
		param.add(device.getSpeed());
		param.add(device.getTimeout());
		param.add(device.getId());
		String sql="update device set equipmentid=?,apmac=?,state=?,vehicleid=?,reporttime=?,modifier=?,modifytime=?,mark=?,speed=?,timeout=? where id=?";
		return executeUpdateSQL(sql, param,"更改设备信息");
	}

	@Override
	public int updateDeviceManger(String equipmentid, int isonline,String ver) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(isonline);
		param.add(ver);
		param.add(equipmentid);
		String sql="update device set isonline=?,ver=? where equipmentid=?";
		return executeUpdateSQL(sql, param);
	}
	
	@Override
	public int updateDeviceManger(String equipmentid, int isonline) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(isonline);
		param.add(equipmentid);
		String sql="update device set isonline=?,lastonlinetime=to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') where equipmentid=?";
		return executeUpdateSQL(sql, param);
	}
	@Override
	public List queryDeviceManger(String equipmentid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(equipmentid);
		String sql="select count(*) from device d where d.equipmentid=?";
		String[] str = new String[]{"count"};
		return getNoPageObject(sql, param, str);
	}

	@Override
	public List queryDeviceGPSsupport(String equipmentid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(equipmentid);
		String sql="select reporttime from DEVICE t where equipmentid=? ";
		String[] str = new String[]{"reporttime"};
		return getNoPageObject(sql, param, str);
	}

	@Override
	public List queryDeviceGPSsupport() {
		// TODO Auto-generated method stub
		String sql="select t.equipmentid,t.reporttime from DEVICE t WHERE t.isonline=1 AND t.state=1";
		String[] str = new String[]{"equipmentid","reporttime"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryDevices(List equipmentids) {
		// TODO Auto-generated method stub
		String[] jsonStr=new String[]{"id","equipmentid","busname","routename","reporttime","ver","speed","timeout"};
		String sql=
				"SELECT D.ID,  "+
				         "D.EQUIPMENTID,  "+
				         "v.code AS busname,  "+
				         "r.name AS routename,  "+
				         "D.REPORTTIME,  "+
				         "D.VER,  "+
				         "D.SPEED,  "+
				         "D.TIMEOUT  "+
				    "FROM DEVICE D  "+
				    "LEFT JOIN vehicle v ON(d.vehicleid=v.id)  "+
				    "LEFT JOIN route r ON(r.id=v.routeid)  where D.EQUIPMENTID in(";
		for(int i=0;i<equipmentids.size();i++){
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		return getNoPageObject(sql, equipmentids, jsonStr);
	}
}
