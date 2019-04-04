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
public class TimeMangerDaoImpl extends BaseHIKDao implements TimeMangerDao{

	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbc;

	@Override
	public List queryListtime(String id) {
		// TODO Auto-generated method stub
		String sql=null;
		if(id==null||StringUtils.isEmpty(id)){
			sql="select id,name,stime,sdtime,etime,edtime,creatime,descr from timeSIGN order by name desc";	
			String[] str = new String[]{"id","name","stime","sdtime","etime","edtime","creatime","descr"};
			return getNoPageObject(sql,str);
		}else{
			sql="select id,name,stime,sdtime,etime,edtime,creatime,descr from timeSIGN  where id not in (select timeID from timeRELATION where groupid in (?))  order by name desc";	
			List param = new ArrayList();
			param.add(Integer.parseInt(id));
			String[] str = new String[]{"id","name","stime","sdtime","etime","edtime","creatime","descr"};
			return getNoPageObject(sql,param,str);
		}
		
	}
	
	@Override
	public List queryListgtime(String id) {
		String sql="select id,name,stime,sdtime,etime,edtime,creatime,descr from timeSIGN  where id in (select timeID from timeRELATION where groupid=?)  order by name desc";
		List param = new ArrayList();
		param.add(Integer.parseInt(id));
		String[] str = new String[]{"id","name","stime","sdtime","etime","edtime","creatime","descr"};
		return getNoPageObject(sql,param,str,"查询分组中的手机号");
	}
	
	@Override
	public Page querytime(int start,int limit,String search) {
		// TODO Auto-generated method stub
		String sql="select id,name,stime,sdtime,etime,edtime,creatime,descr from timeSIGN";
		String[] str = new String[]{"id","name","stime","sdtime","etime","edtime","creatime","descr"};
		if(StringUtils.isEmpty(search)){
			sql+="  order by name desc";
			return getPageObject(sql, start, limit,str);
		}else{
			sql+=" where name like ? "
					+ "or descr like ? "
					+ "or stime like ? "
					+ "or sdtime like ? "
					+ "or etime like ? "
					+ "or edtime like ? "
					+ "or creatime like ? "
					+ "order by name desc";
			List param  = new ArrayList();
			for(int i=0;i<7;i++){
				param.add("%"+search+"%");				
			}
//			param.add("%"+search+"%");
			return getPageObject(sql, start, limit, param, str, "查询所有的手机号");
		}
	
	}

	@Override
	public int savetime(String name, String userid,String descr,String stime,String etime,String sdtime,String edtime) {
		// TODO Auto-generated method stub
		String sql="insert into timeSIGN(id,name,stime,sdtime,etime,edtime,creator,creatime,descr) values(timeSIGN_SEQ.nextval,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List param = new ArrayList();
		param.add(name);
		param.add(stime);
		param.add(sdtime);
		param.add(etime);
		param.add(edtime);
		param.add(userid);
		param.add(descr);
		return executeUpdateSQL(sql, param, "新增优先手机号");
	}

	@Override
	public int updatetime(String id,String name,String userid,String descr,String stime,String etime,String sdtime,String edtime) {
		// TODO Auto-generated method stub
		String sql="update timeSIGN set modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),name=?,stime=?,etime=?,sdtime=?,edtime=?,descr=? where id=?";
		List param = new ArrayList();
		param.add(userid);
		param.add(name);
		param.add(stime);
		param.add(etime);
		param.add(sdtime);
		param.add(edtime);
		param.add(descr);
		param.add(id);
		return executeUpdateSQL(sql, param, "更新手机号");
	}

	@Override
	public int deletetime(String[] ids) {
		// TODO Auto-generated method stub
		String sql="delete from timeSIGN where id in(";
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
	public Page querygtime(int start,int limit,String search) {
		// TODO Auto-generated method stub
		String sql="select id,name,creatime,descr from timeGROUP";
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
	public int savegtime(String id,String name, String userid, String descr) {
		// TODO Auto-generated method stub
		String sql="insert into timeGROUP(id,name,creator,creatime,descr) values(?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List params= new ArrayList();
		params.add(id);
		params.add(name);
		params.add(userid);
		params.add(descr);
		return executeUpdateSQL(sql, params,"新增手机号分组");
	}

	@Override
	public int updategtime(String id, String name,  String userid,String descr) {
		// TODO Auto-generated method stub
		String sql="update timeGROUP set name=?,modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),descr=? where id=?";
		List params= new ArrayList();
		params.add(name);
		params.add(userid);
		params.add(descr);
		params.add(id);
		return executeUpdateSQL(sql, params,"修改手机号分组");
	}

	@Override
	public int deletegtime(String[] ids) {
		// TODO Auto-generated method stub
		String sql="delete from timeGROUP where id in(";
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
		String sql="insert into timerelation values(timeRELATION_seq.nextval,?,?)";
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
		String sql="delete from timeRELATION where groupid in(?)";
		List param = new ArrayList();
		param.add(ids);
		return executeUpdateSQL(sql, param, "删除分组关系数据");
	}

	@Override
	public List getgtimeid() {
		// TODO Auto-generated method stub
		String sql="select timeGROUP_seq.nextval from dual";
		return getNoPageObject(sql);
	}


}
