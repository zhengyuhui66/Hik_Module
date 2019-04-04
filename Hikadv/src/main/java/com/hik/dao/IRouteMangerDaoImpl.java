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
@Transactional
public class IRouteMangerDaoImpl extends BaseHIKDao implements IRouteMangerDao{

	@Override
	public Page queryRouteManger(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","name","state","createtime","creator","modifier","modifytime","mark"};
		String sql="select p.id,p.name,p.state,p.createtime,p.creator,p.modifier,p.modifytime,p.mark from route p ";
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+="where p.name like ? or "+
		       "p.state like ? or "+
		       "p.CREATETIME like ? or "+
		       "p.creator like ? or "+
		       "p.modifier like ? or "+
		       "p.modifytime like ? or "+
		       "p.mark like ? ";
			for(int i=0;i<7;i++){
				param.add(temparam);
			}
		}
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询线路");			
		}
		return page;
	}
	
	@Override
	public String deleteRouteManger(String[] ids) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="delete from route where id in(";
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
	public int addRouteManger(ROUTE route) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(route.getName());
		param.add(route.getState());
		param.add(route.getCreator());
		param.add(route.getCreatime());
//		param.add(corp.getModifier());
//		param.add(corp.getModifytime());
		param.add(route.getMark());
		String sql="insert into route(id,name,state,creator,CREATETIME,modifier,modifytime,mark) values(ROUTE_SEQ.NEXTVAL,?,?,?,?,null,null,?)";
		return executeUpdateSQL(sql, param,"添加新的公司信息");
	}

	@Override
	public int updateRouteManger(ROUTE route) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(route.getName());
		param.add(route.getState());
//		param.add(corp.getCreator());
//		param.add(corp.getCreatetime());
		param.add(route.getModifier());
		param.add(route.getModifytime());
		param.add(route.getMark());
		param.add(route.getId());
		String sql="update ROUTE set name=?,state=?,modifier=?,modifytime=?,mark=? where id=?";
		return executeUpdateSQL(sql, param,"更改公司信息");
	}

	@Override
	public int setting(String[] ids, String speed, String timeout) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(speed);
		param.add(timeout);
		String sql="UPDATE device set speed=?,TIMEOUT=? WHERE vehicleid IN(SELECT ID from VEHICLE WHERE routeid IN(";
		for(int i=0;i<ids.length;i++){
			sql+="?,";
			param.add(ids[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+="))";
		return executeUpdateSQL(sql, param,"设置线路参数");
	}

	@Override
	public List<JSONObject> queryDeviceList(String id) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(id);
		String sql="select t.equipmentid,t.isonline,t.reporttime,t.state,v.state as bustate,t.ver,t.speed,t.timeout,v.code from DEVICE t LEFT JOIN vehicle v ON(t.vehicleid=v.id) LEFT JOIN route r ON(v.routeid=r.id) WHERE r.id=?";
		String[] str = new String[]{"equipmentid","isonline","reporttime","state","bustate","ver","speed","timeout","code"};
		return getNoPageObject(sql, param, str);
	}

//	@Override
//	public List queryifRouteManger(String[] ids) {
//		// TODO Auto-generated method stub
//		List param =new ArrayList();
//		String sql="SELECT COUNT(*), S.PRODUCTID, P.PRONAME "+
//				  "FROM SCHEDULE S "+
//				  "LEFT JOIN TIMESETTING T "+
//				    "ON (S.INTERVAL = T.ID) "+
//				  "LEFT JOIN PUTPRODUCT P "+
//				    "ON (S.PRODUCTID = P.ID) "+
//				 "WHERE S.DATETIME || ' ' || T.STARTTIME > "+
//				       "TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
//				   "AND S.PRODUCTID IN ( ";
//				    for(int i=0;i<ids.length;i++){
//				    	sql+="?,";
//				    	param.add(ids[i]);
//				    } 
//			    sql=sql.substring(0, sql.length()-1);
//			    sql+=") ";
//				sql+="GROUP BY S.PRODUCTID, P.PRONAME ";
//				String[] str = new String[]{"count","productid","productname"};
//		return getNoPageObject(sql, param, str);
//	}
}
