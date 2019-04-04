package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.framework.utils.Page;


@Repository
public class AddressMapDaoImpl extends BaseHIKDao implements AddressMapDao{
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbc;
	@Override
	public int saveaddress(String nameid, String descrid,String userid, String[] latlongid) {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		String sql="insert into ADDRESSSIGN(id,name,longitude1,latitude1,"
				+ "longitude2,latitude2,"
				+ "longitude3,latitude3,"
				+ "longitude4,latitude4,"
				+ "longitude5,latitude5,"
				+ "longitude6,latitude6,"
				+ "longitude7,latitude7,"
				+ "longitude8,latitude8,"
				+ "longitude9,latitude9,"
				+ "longitude10,latitude10,CREATOR,CREATIME,DESCR ) "
				+ "values(ADDRESSSIGN_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		params.add(nameid);
		for(int i=0;i<20;i++){
			if(i>=latlongid.length){
				params.add(null);
			}else{
				params.add(latlongid[i]);								
			}
			
		}
		params.add(userid);
		params.add(descrid);
		return executeUpdateSQL(sql, params,"新增新的地址");
	}

	@Override
	public List getAllAddress() {
		// TODO Auto-generated method stub
		String sql="select id,name,"
				+ "longitude1,latitude1,"
				+ "longitude2,latitude2,"
				+ "longitude3,latitude3,"
				+ "longitude4,latitude4,"
				+ "longitude5,latitude5,"
				+ "longitude6,latitude6,"
				+ "longitude7,latitude7,"
				+ "longitude8,latitude8,"
				+ "longitude9,latitude9,"
				+ "longitude10,latitude10 from ADDRESSSIGN";
		String[] str = new String[]{
				"id","name",
		"longitude1","latitude1",
		 "longitude2","latitude2",
		 "longitude3","latitude3",
		 "longitude4","latitude4",
		 "longitude5","latitude5",
		 "longitude6","latitude6",
		 "longitude7","latitude7",
		 "longitude8","latitude8",
		 "longitude9","latitude9",
		 "longitude10","latitude10"};
		return getNoPageObject(sql, str);
	}

	@Override
	public int deleteAddress(String[] ids) {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		String sql="delete from ADDRESSSIGN where id in (";
		for(int i=0;i<ids.length;i++){
			params.add(ids[i]);
			sql+="?,";
		}
		sql=sql.substring(0,sql.length()-1);
		sql+=")";
		
		return executeUpdateSQL(sql, params);
	}

	@Override
	public List queryListaddress(String id) {
		// TODO Auto-generated method stub
		String sql=null;
		if(id==null||StringUtils.isEmpty(id)){
			sql="select id,name,creatime,descr from ADDRESSSIGN order by name desc";	
			String[] str = new String[]{"id","name","creatime","descr"};
			return getNoPageObject(sql,str);
		}else{
			sql="select id,name,creatime,descr from ADDRESSSIGN  where id not in (select ADDRESSid from ADDRESSRELATION where groupid in (?))  order by name desc";	
			List param = new ArrayList();
			param.add(Integer.parseInt(id));
			String[] str = new String[]{"id","name","creatime","descr"};
			return getNoPageObject(sql,param,str);
		}
		
	}
	
	@Override
	public List queryListgaddress(String id) {
		String sql="select id,name,creatime,descr from ADDRESSSIGN  where id in (select ADDRESSid from ADDRESSRELATION where groupid=?)  order by name desc";
		List param = new ArrayList();
		param.add(id);
		String[] str = new String[]{"id","name","creatime","descr"};
		return getNoPageObject(sql,param,str,"查询分组中的手机号");
	}

	@Override
	public Page querygaddress(int start,int limit,String search) {
		// TODO Auto-generated method stub
		String sql="select id,name,creatime,descr from ADDRESSGROUP";
		String[] str = new String[]{"id","name","creatime","descr"};
		if(StringUtils.isEmpty(search)){
			sql+="  order by name desc";
			return getPageObject(sql, start, limit,str);
		}else{
			sql+=" where name like ? or descr like ? or creatime like ? order by name desc";
			List param  = new ArrayList();
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			return getPageObject(sql, start, limit, param, str, "查询所有的手机号");
		}
	}

	@Override
	public int savegaddress(String id,String name, String userid, String descr) {
		// TODO Auto-generated method stub
		String sql="insert into ADDRESSGROUP(id,name,creator,creatime,descr) values(?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List params= new ArrayList();
		params.add(id);
		params.add(name);
		params.add(userid);
		params.add(descr);
		return executeUpdateSQL(sql, params,"新增手机号分组");
	}

	@Override
	public int updategaddress(String id, String name,  String userid,String descr) {
		// TODO Auto-generated method stub
		String sql="update ADDRESSGROUP set name=?,modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),descr=? where id=?";
		List params= new ArrayList();
		params.add(name);
		params.add(userid);
		params.add(descr);
		params.add(id);
		return executeUpdateSQL(sql, params,"修改手机号分组");
	}

	@Override
	public int deletegaddress(String[] ids) {
		// TODO Auto-generated method stub
		String sql="delete from ADDRESSGROUP where id in(";
		List param = new ArrayList();
		for(int i=0;i<ids.length;i++){
			param.add(ids[i]);
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		return executeUpdateSQL(sql, param, "删除手机分组");
	}

	@Override
	public int insertgcs(String gpid, String[] ids) {
		// TODO Auto-generated method stub
		String sql="insert into ADDRESSrelation values(ADDRESSRELATION_seq.nextval,?,?)";
		List list = new ArrayList();
		for(int i=0;i<ids.length;i++){
			Object[] obj = new Object[]{ids[i],gpid};
			list.add(obj);
		}
		int[] result=jdbc.batchUpdate(sql,list);
		return result.length;
	}

	@Override
	public int deletegcs(String ids) {
		String sql="delete from ADDRESSRELATION where groupid in(?)";
		List param = new ArrayList();
		param.add(ids);
		return executeUpdateSQL(sql, param, "删除分组关系数据");
	}

	@Override
	public List getgaddressid() {
		// TODO Auto-generated method stub
		String sql="select ADDRESSGROUP_seq.nextval from dual";
		return getNoPageObject(sql);
	}
	
}
