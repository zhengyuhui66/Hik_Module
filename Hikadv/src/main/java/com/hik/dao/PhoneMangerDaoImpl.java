package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hik.framework.utils.Page;


@Repository
public class PhoneMangerDaoImpl extends BaseHIKDao implements PhoneMangerDao{

	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbc;

	@Override
	public List queryListphone(String id) {
		// TODO Auto-generated method stub
		String sql=null;
		if(id==null||StringUtils.isEmpty(id)){
			sql="select id,name,creatime,descr from TELPHONESIGN order by name desc";	
			String[] str = new String[]{"id","name","creatime","descr"};
			return getNoPageObject(sql,str);
		}else{
			sql="select id,name,creatime,descr from TELPHONESIGN  where id not in (select telphoneid from TELPHONERELATION where groupid in (?))  order by name desc";	
			List param = new ArrayList();
			param.add(Integer.parseInt(id));
			String[] str = new String[]{"id","name","creatime","descr"};
			return getNoPageObject(sql,param,str);
		}
		
	}
	
	@Override
	public List queryListgphone(String id) {
		String sql="select id,name,creatime,descr from TELPHONESIGN  where id in (select telphoneid from TELPHONERELATION where groupid=?)  order by name desc";
		List param = new ArrayList();
		param.add(Integer.parseInt(id));
		String[] str = new String[]{"id","name","creatime","descr"};
		return getNoPageObject(sql,param,str,"查询分组中的手机号");
	}
	
	@Override
	public Page queryphone(int start,int limit,String search) {
		// TODO Auto-generated method stub
		String sql="select id,name,creatime,descr from TELPHONESIGN";
		String[] str = new String[]{"id","name","creatime","descr"};
		if(StringUtils.isEmpty(search)){
			sql+="  order by name desc";
			return getPageObject(sql, start, limit,str);
		}else{
			sql+=" where name like ? or descr like ?  or creatime like ? order by name desc";
			List param  = new ArrayList();
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			return getPageObject(sql, start, limit, param, str, "查询所有的手机号");
		}
	
	}

	@Override
	public int savephone(String name, String userid,String descr) {
		// TODO Auto-generated method stub
		String sql="insert into TELPHONESIGN(id,name,creator,creatime,descr) values(TELPHONESIGN_SEQ.nextval,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List param = new ArrayList();
		param.add(name);
		param.add(userid);
		param.add(descr);
		return executeUpdateSQL(sql, param, "新增优先手机号");
	}

	@Override
	public int updatephone(String id,String name,String userid,String descr) {
		// TODO Auto-generated method stub
		
		String sql="update TELPHONESIGN set modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),name=?,descr=? where id=?";
		List param = new ArrayList();
		param.add(userid);
		param.add(name);
		param.add(descr);
		param.add(id);
		return executeUpdateSQL(sql, param, "更新手机号");
	}

	@Override
	public int deletephone(String[] ids) {
		// TODO Auto-generated method stub
		String sql="delete from TELPHONESIGN where id in(";
		List param = new ArrayList();
		for(int i=0;i<ids.length;i++){
			param.add(ids[i]);
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		return executeUpdateSQL(sql, param, "删除手机号");
	}

	@Override
	public Page querygphone(int start,int limit,String search) {
		// TODO Auto-generated method stub
		String sql="select id,name,creatime,descr from TELPHONEGROUP";
		String[] str = new String[]{"id","name","creatime","descr"};
		if(StringUtils.isEmpty(search)){
			sql+="  order by name desc";
			return getPageObject(sql, start, limit,str);
		}else{
			sql+=" where name like ? or descr like ? order by name desc";
			List param  = new ArrayList();
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			return getPageObject(sql, start, limit, param, str, "查询所有的手机号");
		}
	}

	@Override
	public int savegphone(String id,String name, String userid, String descr) {
		// TODO Auto-generated method stub
		String sql="insert into TELPHONEGROUP(id,name,creator,creatime,descr) values(?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List params= new ArrayList();
		params.add(id);
		params.add(name);
		params.add(userid);
		params.add(descr);
		return executeUpdateSQL(sql, params,"新增手机号分组");
	}

	@Override
	public int updategphone(String id, String name,  String userid,String descr) {
		// TODO Auto-generated method stub
		String sql="update TELPHONEGROUP set name=?,modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),descr=? where id=?";
		List params= new ArrayList();
		params.add(name);
		params.add(userid);
		params.add(descr);
		params.add(id);
		return executeUpdateSQL(sql, params,"修改手机号分组");
	}

	@Override
	public int deletegphone(String[] ids) {
		// TODO Auto-generated method stub
		String sql="delete from TELPHONEGROUP where id in(";
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
	public int insertgp(String gpid, String[] ids) {
		// TODO Auto-generated method stub
		String sql="insert into TELPHONERELATION values(TELPHONERELATION_seq.nextval,?,?)";
		List list = new ArrayList();
		for(int i=0;i<ids.length;i++){
			Object[] obj = new Object[]{ids[i],gpid};
			list.add(obj);
		}
		int[] result=jdbc.batchUpdate(sql,list);
		return result.length;
	}

	@Override
	public int deletegp(String ids) {
		String sql="delete from TELPHONERELATION where groupid in(?)";
		List param = new ArrayList();
		param.add(ids);
		return executeUpdateSQL(sql, param, "删除分组关系数据");
	}

	@Override
	public List getgphoneid() {
		// TODO Auto-generated method stub
		String sql="select TELPHONEGROUP_seq.nextval from dual";
		
		return getNoPageObject(sql);
	}




	
}
