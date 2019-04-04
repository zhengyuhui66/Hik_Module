package com.hik.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.util.PROCEDURCES;


@Repository
@Transactional
public class ICommonDaoImpl extends BaseHIKDao implements ICommonDao{
	
	public List queryAllModel(String cycid){
		String[] str=new String[]{"id","name"};
		String sql="SELECT DISTINCT id,NAME FROM ADVERTMODELMANGER";
		if(cycid!=null){
			List param = new ArrayList();
			param.add(cycid);
			sql+=" where cycid=?";
			return getNoPageObject(sql, param,str,"查询所有的广告模版");
		}
		return getNoPageObject(sql, str,"查询所有的广告模版");
	}

	@Override
	public List queryAdvgroup() {
		// TODO Auto-generated method stub
		String sql="select id,name from ADVGROUP";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}
	
	@Override
	public List queryAllAdvert(String userid,String id,String pid) {
		// TODO Auto-generated method stub
		String sql="select ID,NAME from ADVERTMANGER t left join MATERIEL_INFO m  on(t.mater_id=m.mater_id)";
		String[] str = new String[]{"advertid","name"};
		if(!StringUtils.isEmpty(id)){
			List<String> param = new ArrayList<String>();
			param.add(id);
			param.add(pid);
			sql+=" where advertproperty=? and advertpropertys=?";
			if(userid!=null){
				sql+=" and m.customer_id=?";
				param.add(userid);
			}
			return getNoPageObject(sql,param,str);
		}else if(StringUtils.isEmpty(id)&&!StringUtils.isEmpty(pid)){
			List<String> param = new ArrayList<String>();
			param.add(pid);
			sql+=" where advertpropertys=?";
			if(userid!=null){
				sql+=" and m.customer_id=?";
				param.add(userid);
			}
			return getNoPageObject(sql,param,str);
		}else{
			List<String> param = new ArrayList<String>();
			if(userid!=null){
				sql+=" where m.customer_id=?";
				param.add(userid);
				return getNoPageObject(sql,param,str);
			}else{
				return getNoPageObject(sql,str);
			}
		}
	}
	@Override
	public List queryAllMmpp(String userid,String id,String pid) {
		// TODO Auto-generated method stub
		String sql="select t.ID,t.NAME from mmpp t";
		String[] str = new String[]{"id","name"};
		if(!StringUtils.isEmpty(id)){
			List<String> param = new ArrayList<String>();
			param.add(id);
			param.add(pid);
			sql+=" where t.PROPERTY=? and t.propertys=?";
			if(userid!=null){
				sql+=" and t.user_adv=?";
				param.add(userid);
			}
			return getNoPageObject(sql,param,str);
		}else if(StringUtils.isEmpty(id)&&!StringUtils.isEmpty(pid)){
			List<String> param = new ArrayList<String>();
			param.add(pid);
			sql+=" where t.propertys=?";
			if(userid!=null){
				sql+=" and t.user_adv=?";
				param.add(userid);
			}
			return getNoPageObject(sql,param,str);
		}else{
			List<String> param = new ArrayList<String>();
			if(userid!=null){
				sql+=" where t.user_adv=?";
				param.add(userid);
				return getNoPageObject(sql,param,str);
			}else{
				return getNoPageObject(sql,str);
			}
		}
	}
	@Override
	public List queryplayfun() {
		// TODO Auto-generated method stub
		String[] str= new String[]{"id","name"};
		String sql="select cid,name from play_fun";
		return getNoPageObject(sql, str);
	}
	
	@Override
	public List queryAuthCyc() {
		// TODO Auto-generated method stub
		String[] str = new String[]{"id","name"};
		String sql="select cid,name from AUTHCYC";
		return getNoPageObject(sql, str);
	}
	
	@Override
	public List querySKIN() {
		// TODO Auto-generated method stub
		String sql="select id,name from SKIN_MANGER";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,str);
	}
	
	@Override
	public List getPropertys() {
		// TODO Auto-generated method stub
		String sql="select id,name from ADVPROPERTY where leve=1";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}
	@Override
	public List getProperty(String id) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(id);
		String sql="select id,name from ADVPROPERTY where leve=2 and pid=?";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,param,str);
	}

	@Override
	public List querytimeSet() {
		// TODO Auto-generated method stub
		String sql="select id,name from TIMESETTING";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,str);
	}
	
	@Override
	public List getRoute(String linename) {
		// TODO Auto-generated method stub
		String sql="SELECT ID,NAME FROM ROUTE";
		List param = new ArrayList();
		String[] str = new String[]{"id","name"};
		if(StringUtils.isNotBlank(linename)){
			sql+=" where name like ?";
			param.add("%"+linename+"%");
			return getNoPageObject(sql, param, str);
		}
		return getNoPageObject(sql, str);
	}
	@Override
	public List queryRoute(String id) {
		// TODO Auto-generated method stub
		String sql="SELECT ID,NAME FROM ROUTE";
		List param = new ArrayList();
		String[] str = new String[]{"id","name"};
		if(StringUtils.isNotBlank(id)){
			sql+=" where id= ?";
			param.add(id);
			return getNoPageObject(sql, param, str);
		}
		return getNoPageObject(sql, str);
	}
	@Override
	public List queryBusInfo(String lineId, String busName) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="select v.id,v.code from VEHICLE v";
		if(StringUtils.isNotEmpty(lineId)){
			
			sql+="  where v.routeid=?";
			
			param.add(lineId);
			if(StringUtils.isNotEmpty(busName)){
				sql+="  and v.code like ?";
				param.add("%"+busName+"%");
			}
					
		}else if(StringUtils.isEmpty(lineId)&&StringUtils.isNotEmpty(busName)){
			sql+="  where v.code like ?";
			param.add("%"+busName+"%");
			
		}
		
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, param,str,"查询线路");
	}

	@Override
	public List queryBusInfo() {
		// TODO Auto-generated method stub
		String sql="select v.id,v.code from VEHICLE v";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryProduct(String cycid, String modelid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="select T.ID,T.PRONAME from PUTPRODUCT t LEFT JOIN advertmodelmanger a ON(t.modelid= a.id) ";
		String sql2="";
		if(cycid!=null){
			sql2+= "a.cycid=?";
			param.add(cycid);
		}
		if(modelid!=null){
			if(sql2.length()!=0){
				sql2+="  OR";
			}
			sql2+="  a.id=?";
			param.add(modelid);
		}
		if(sql2.length()>0){
			sql2=" where "+sql2;
		}
		sql+=sql2;
		
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,param,str,"查询产品");
	}

	@Override
	public List queryAp() {
		// TODO Auto-generated method stub
		String sql="select T.ID,T.EQUIPMENTID,T.APMAC,T.VEHICLEID from DEVICE t";
		String[] str = new String[]{"apid","equipmentid","name","vehicleid"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryModelNum(String modelid) {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		String sql="SELECT COUNT(*) FROM ( "+
					"SELECT advposid1 AS a1 FROM ADVERTMODELMANGER WHERE ID=? AND advposid1 IS NOT NULL "+
					"UNION "+
					"SELECT advposid2 AS a2 FROM ADVERTMODELMANGER WHERE ID=? AND advposid2 IS NOT NULL "+
					"UNION "+
					"SELECT advposid3 AS a3 FROM ADVERTMODELMANGER WHERE ID=? AND advposid3 IS NOT NULL "+
					"UNION "+
					"SELECT advposid4 AS a4 FROM ADVERTMODELMANGER WHERE ID=? AND advposid4 IS NOT NULL "+
					"UNION "+
					"SELECT advposid5 AS a5 FROM ADVERTMODELMANGER WHERE ID=? AND advposid5 IS NOT NULL "+
					")";
		params.add(modelid);
		params.add(modelid);
		params.add(modelid);
		params.add(modelid);
		params.add(modelid);
		
		return getNoPageObject(sql, params);
	}

	@Override
	public List queryProEvent() {
		// TODO Auto-generated method stub
		String sql="select id,name from EVENTGROUP";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryProClientType() {
		// TODO Auto-generated method stub
		String sql="select id,name from CLIENTTYPEGROUP";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryProClientSys() {
		// TODO Auto-generated method stub
		String sql="select id,name from SYSOPERGROUP";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryProPhone() {
		// TODO Auto-generated method stub
		String sql="select id,name from TELPHONEGROUP";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryProAddress() {
		// TODO Auto-generated method stub
		String sql="select id,name from ADDRESSGROUP";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryProTime() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sql="select id,name from TIMEGROUP";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryPutFlag() {
		// TODO Auto-generated method stub
		String sql="select FLAG from PUTTEMPFLAG";
		String[] str = new String[]{"flag"};
		return getNoPageObject(sql,str);
	}

	@Override
	public int setPutFlag(String flag) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="update PUTTEMPFLAG set flag=? where id='1'";
		param.add(flag);
		return executeUpdateSQL(sql, param);
	}

	@Override
	public List getAllUser() {
		// TODO Auto-generated method stub
		String sql="select userid,user_name from SYS_USER";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql,str);
	}

	@Override
	public List queryAllRoute() {
		// TODO 自动生成的方法存根
		String sql="SELECT ID,NAME,STATE,CREATOR,MODIFIER,CREATETIME,MODIFYTIME,MARK FROM ROUTE";
		String[] str = new String[]{"ID","NAME","STATE","CREATOR","MODIFIER","CREATETIME","MODIFYTIME","MARK"};
		return getNoPageObject(sql, str);
	}

	@Override
	public List queryAllCorp() {
		// TODO 自动生成的方法存根
		String sql="SELECT ID,NAME,ADDRESS,MARK FROM CORP";
		String[] str = new String[]{"ID","NAME","ADDRESS","MARK"};
		return getNoPageObject(sql, str);
	}
//
//	@Override
//	public List queryBusInfo(String lineId, String busName) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List getDeviceOnlineTimeout() {
		// TODO Auto-generated method stub
		String sql="select value from BASEPARAM where name='"+PROCEDURCES.REDISTIMEOUT+"'";
		String[] str = new String[]{"value"};
		return getNoPageObject(sql, str);
	}

//	@Override
//	public List queryDeviceInfo() {
//		// TODO Auto-generated method stub
//		String[] str = new String[]{"id","equipmentid"};
//		String sql="select id,equipmentid from DEVICE";
//		return getNoPageObject(sql, str);
//	}

	@Override
	public List queryDeviceInfo(String busid) {
		// TODO Auto-generated method stub
		String[] str = new String[]{"id","name"};
		String sql="select id,equipmentid from DEVICE";
		if(StringUtils.isNotEmpty(busid)){
			List param = new ArrayList();
			param.add(busid);
			 sql+=" where VEHICLEID=?";
				return getNoPageObject(sql, param, str);
		}else{
			return getNoPageObject(sql, str);
		}
	
		
	
	}

	@Override
	public List queryIfhaveDevice(String equipmentid,String apmac) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(equipmentid);
		param.add(apmac);
		String[] str = new String[]{"name","APMAC"};
		String sql="select equipmentid,APMAC from DEVICE where EQUIPMENTID=? or APMAC=?";
		return getNoPageObject(sql, param,str,"查询设备是否重复");
	}

	@Override
	public List queryIfhaveBusInfo(String name) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(name);
		String[] str = new String[]{"CODE"};
		String sql="select CODE from VEHICLE where CODE=?";
		return getNoPageObject(sql, param,str,"查询车辆是否重复");
	}

	@Override
	public List queryIfhaveRouteInfo(String name) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(name);
		String[] str = new String[]{"name"};
		String sql="select NAME from ROUTE where name=?";
		return getNoPageObject(sql, param,str,"查询线路是否重复");
	}

	@Override
	public List queryIfhaveCorpInfo(String name) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(name);
		String[] str = new String[]{"name"};
		String sql="select NAME from CORP where name=?";
		return getNoPageObject(sql, param,str,"查询公司是否重复");
	}
}
