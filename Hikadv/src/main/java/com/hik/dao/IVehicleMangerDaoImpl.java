package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.VEHICLE;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;



@Repository
@Transactional
public class IVehicleMangerDaoImpl extends BaseHIKDao implements IVehicleMangerDao{

	@Override
	public Page queryVehicleList(String UserId, int start, int limit, String searchStr) {
		List param = new ArrayList();
		String[] jsonStr=new String[]{"ID","CODE","ROUTENAME","BRAND","MODEL","COLOR","USEDATE","STATE","CREATOR","MODIFIER","CREATETIME","MODIFYTIME","MARK","OWERCODE","ROUTEID","CORPID","CORPNAME"};
		String sql="select t.ID,t.CODE,r.name,t.BRAND,t.MODEL,t.COLOR,t.USEDATE,t.STATE,t.CREATOR,t.MODIFIER,t.CREATETIME,t.MODIFYTIME,t.MARK,t.OWERCODE,t.ROUTEID,t.CORPID,t.CORPNAME from VEHICLE t left join route r on(t.routeid=r.id)";
		if (StringUtils.isNotEmpty(searchStr)) {
			String temparam = "%"+searchStr+"%";
			sql+=" where ";
			  sql+="  ("+
					   "t.CODE like ? or "+
				       "t.OWERCODE like ? or "+
				       "r.name like ? or "+
				       "t.CORPNAME like ? or "+
				       "t.STATE like ? or "+
				       "t.MARK like ?)";
				for(int i=0;i<6;i++){
					param.add(temparam);
				}
		}
		
		
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询车辆线路");			
		}
		return page;
		
//		Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询车辆信息");
//		return page;
	}

	@Override
	public int deleteVehicle(String[] vehicleIds) {

		String sql="delete from VEHICLE t where t.ID in(";
		List param = new ArrayList();
		for(int i=0;i<vehicleIds.length;i++){
			param.add(vehicleIds[i]);
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		return executeUpdateSQL(sql, param, "删除车辆");
		
		
	}



	@Override
	public List getVehicleDetail(String vehicleId) {
		List params = new ArrayList();
		params.add(vehicleId);
		String[] str = new String[]{"ID","CODE","ROUTENAME","BRAND","MODEL","COLOR","USEDATE","STATE","CREATOR","MODIFIER","CREATETIME","MODIFYTIME","MARK","OWERCODE","ROUTEID","CORPID","CORPNAME"};
		String sql="select t.ID,t.CODE,r.name,t.BRAND,t.MODEL,t.COLOR,t.USEDATE,t.STATE,t.CREATOR,t.MODIFIER,t.CREATETIME,t.MODIFYTIME,t.MARK,t.OWERCODE,t.ROUTEID,t.CORPID,t.CORPNAME from VEHICLE t left join route r on(t.routeid=r.id) where t.ID=?";
		return getNoPageObject(sql, params,str,"查询车辆信息");
	}

	@Override
	public int saveVehicle(VEHICLE vehicle) {
		List<String> param = new ArrayList<String>();
		param.add(vehicle.getCODE());
//		param.add(vehicle.getROUTENAME());
		param.add(vehicle.getBRAND());
		param.add(vehicle.getMODEL());
		param.add(vehicle.getCOLOR());
		param.add(vehicle.getUSEDATE());
		param.add(vehicle.getSTATE());
		param.add(vehicle.getCREATOR());
		param.add(vehicle.getMODIFIER());
//		param.add(vehicle.getCREATETIME());
//		param.add(vehicle.getMODIFYTIME());
		param.add(vehicle.getMARK());
		param.add(vehicle.getOWERCODE());
		param.add(vehicle.getROUTEID());
		param.add(vehicle.getCORPID());
		param.add(vehicle.getCORPNAME());
//		param.add(vehicle.getIFADVERT());
		
		String sql="insert into VEHICLE(id,code,brand,model,color,usedate,state,creator,modifier,createtime,modifytime,mark,owercode,routeid,corpid,corpname) "
				+ "values(VEHICLE_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?)";
		return executeUpdateSQL(sql, param,"新增车辆信息");
	}

	@Override
	public int updateVehicle(VEHICLE vehicle) {
		List<String> param = new ArrayList<String>();
		param.add(vehicle.getCODE());
//		param.add(vehicle.getROUTENAME());
		param.add(vehicle.getBRAND());
		param.add(vehicle.getMODEL());
		param.add(vehicle.getCOLOR());
		param.add(vehicle.getUSEDATE());
		param.add(vehicle.getSTATE());
//		param.add(vehicle.getCREATOR());
		param.add(vehicle.getMODIFIER());
//		param.add(vehicle.getCREATETIME());
//		param.add(vehicle.getMODIFYTIME());
		param.add(vehicle.getMARK());
		param.add(vehicle.getOWERCODE());
		param.add(vehicle.getROUTEID());
		param.add(vehicle.getCORPID());
		param.add(vehicle.getCORPNAME());
//		param.add(vehicle.getIFADVERT());
		param.add(vehicle.getID());
		
		String sql="update VEHICLE set code=?,brand=?,model=?,color=?,usedate=?,state=?,modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')"
				+ ",mark=?,owercode=?,routeid=?,corpid=?,corpname=? where id=?";
		return executeUpdateSQL(sql, param,"更新车辆数据");
	}
	
	@Override
	public int setting(String[] ids, String speed, String timeout) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(speed);
		param.add(timeout);
		String sql="UPDATE device set speed=?,TIMEOUT=? WHERE vehicleid IN(";
		for(int i=0;i<ids.length;i++){
			sql+="?,";
			param.add(ids[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		return executeUpdateSQL(sql, param,"设置线路参数");
	}

	@Override
	public List<JSONObject> queryDeviceList(String id) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(id);
		String sql="select equipmentid,reporttime,state,isonline,speed,timeout,ver from DEVICE where vehicleid=? ";
		String[] str = new String[]{"equipmentid","reporttime","state","isonline","speed","timeout","ver"};
		return getNoPageObject(sql, param, str);
	}
	
}