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
public class EventMangerDaoImpl extends BaseHIKDao implements EventMangerDao{

	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbc;

	@Override
	public List queryListevent(String id) {
		// TODO Auto-generated method stub
		String sql=null;
		if(id==null||StringUtils.isEmpty(id)){
			sql="select id,stime,etime,name,creatime,descr from eventSIGN order by name desc";	
			String[] str = new String[]{"id","stime","etime","name","creatime","descr"};
			return getNoPageObject(sql,str);
		}else{
			sql="select id,stime,etime,name,creatime,descr from eventSIGN  where id not in (select EVENTID from eventRELATION where groupid in (?))  order by name desc";	
			List param = new ArrayList();
			param.add(Integer.parseInt(id));
			String[] str = new String[]{"id","stime","etime","name","creatime","descr"};
			return getNoPageObject(sql,param,str);
		}
	}
	
	@Override
	public List queryListgevent(String id) {
		String sql="select id,stime,etime,name,creatime,descr from eventSIGN  where id in (select EVENTID from eventRELATION where groupid=?)  order by name desc";
		List param = new ArrayList();
		param.add(Integer.parseInt(id));
		String[] str = new String[]{"id","stime","etime","name","creatime","descr"};
		return getNoPageObject(sql,param,str,"查询分组中的手机号");
	}
	
	@Override
	public Page queryevent(int start,int limit,String search) {
		// TODO Auto-generated method stub
		String sql="select id,stime,etime,name,creatime,descr from eventSIGN";
		String[] str = new String[]{"id","stime","etime","name","creatime","descr"};
		if(StringUtils.isEmpty(search)){
			sql+="  order by name desc";
			return getPageObject(sql, start, limit,str);
		}else{
			sql+=" where name like ? "
					+ "or descr like ? "
					+ "or creatime like ? "
					+ "or stime like ? "
					+ "or etime like ? "
					+ "order by name desc";
			List param  = new ArrayList();
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			param.add("%"+search+"%");
			return getPageObject(sql, start, limit, param, str, "查询所有的手机号");
		}
	
	}

	@Override
	public int saveevent(String name, String userid,String descr,String stime,String etime) {
		// TODO Auto-generated method stub
		String sql="insert into eventSIGN(id,name,stime,etime,creator,creatime,descr) values(eventSIGN_SEQ.nextval,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List param = new ArrayList();
		param.add(name);
		param.add(stime);
		param.add(etime);
		param.add(userid);
		param.add(descr);
		return executeUpdateSQL(sql, param, "新增优先手机号");
	}

	@Override
	public int updateevent(String id,String name,String userid,String descr,String stime,String etime) {
		// TODO Auto-generated method stub
		
		String sql="update eventSIGN set modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),name=?,descr=?,stime=?,etime=? where id=?";
		List param = new ArrayList();
		param.add(userid);
		param.add(name);
		param.add(descr);
		param.add(stime);
		param.add(etime);
		param.add(id);
		return executeUpdateSQL(sql, param, "更新手机号");
	}

	@Override
	public int deleteevent(String[] ids) {
		// TODO Auto-generated method stub
		String sql="delete from eventSIGN where id in(";
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
	public Page querygevent(int start,int limit,String search) {
		// TODO Auto-generated method stub
		String sql="select id,name,creatime,descr from eventGROUP";
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
	public int savegevent(String id,String name, String userid, String descr) {
		// TODO Auto-generated method stub
		String sql="insert into eventGROUP(id,name,creator,creatime,descr) values(?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List params= new ArrayList();
		params.add(id);
		params.add(name);
		params.add(userid);
		params.add(descr);
		return executeUpdateSQL(sql, params,"新增手机号分组");
	}

	@Override
	public int updategevent(String id, String name,  String userid,String descr) {
		// TODO Auto-generated method stub
		String sql="update eventGROUP set name=?,modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),descr=? where id=?";
		List params= new ArrayList();
		params.add(name);
		params.add(userid);
		params.add(descr);
		params.add(id);
		return executeUpdateSQL(sql, params,"修改手机号分组");
	}

	@Override
	public int deletegevent(String[] ids) {
		// TODO Auto-generated method stub
		String sql="delete from eventGROUP where id in(";
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
	public int insertgcs(String gpid, String[] ids){
		// TODO Auto-generated method stub
		String sql="insert into eventrelation values(eventRELATION_seq.nextval,?,?)";
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
		String sql="delete from eventRELATION where groupid in(?)";
		List param = new ArrayList();
		param.add(ids);
		return executeUpdateSQL(sql, param, "删除分组关系数据");
	}

	@Override
	public List getgeventid() {
		// TODO Auto-generated method stub
		String sql="select eventGROUP_seq.nextval from dual";
		return getNoPageObject(sql);
	}

	@Override
	public List getEventHappen() {
		// TODO Auto-generated method stub
		String sql="SELECT id,name,stime,etime FROM eventsign WHERE stime< to_char(TO_CHAR(SYSDATE, 'hh24:mi:ss')) AND etime> to_char(TO_CHAR(SYSDATE, 'hh24:mi:ss'))";
		String[] str = new String[]{"id","name","stime","etime"};
		return getNoPageObject(sql, str);
	}
}
