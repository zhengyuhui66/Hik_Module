package com.hik.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hik.framework.utils.Page;


@Repository
public class MessPushDaoImpl extends BaseHIKDao implements MessPushDao{
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbc;
	@Override
	public Page queryForPage(String UserId, int start, int limit, String searchStr){
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="select id,userid,content,creatime,creator,descr from messpushlog";
		String[] jsonStr = new String[]{"id","userid","content","creatime","creator","descr"};
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			  sql+=" where "+
				   "userid like ? or "+
			       "content like ? or "+
			       "creatime like ? or "+
			       "creator like ? or "+
			       "descr like ? ";
			for(int i=0;i<5;i++){
				param.add(temparam);
			}
		}
		Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询推送日志");
		return page;
	}
	@Override
	public int savePushMess(Map<String,String> map, String content, String name,String descr) {
		// TODO Auto-generated method stub
		String sql="insert into MESSPUSHLOG(id,clientid,userid,content,creator,creatime,descr) values(MESSPUSHLOG_seq.nextval,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		  for (String key : map.keySet()) {
				Object[] tempObj = new Object[]{map.get(key),key,content,name,descr};
				batchArgs.add(tempObj);
		  }
		int[] result=jdbc.batchUpdate(sql, batchArgs);
		return result.length;
	}
	
}
