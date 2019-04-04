package com.hik.framework.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hik.dao.BaseDao;
import com.hik.dao.BaseHIKDao;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;


@Repository
public class LogDaoImpl extends BaseHIKDao implements ILogDao{

	public int insertLogInfo(String userId,String content, String curd,String aopMethod) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(userId);
		param.add(content);
		param.add(curd);
		param.add(aopMethod);
		String sql="INSERT INTO SYS_OPERATE_LOG(id,username,operate_time,content,curd,aopMethod) VALUES (sysoperatelog_seq.nextval,?,to_char(SYSDATE,'yyyy-MM-dd hh24:mi:ss'),?,?,?)";
		return executeUpdateSQL(sql, param, "记录操作日志!");
	}

	public Page getLogInfo(String condition,int start,int limit) {
		// TODO Auto-generated method stub
		String[] str = new String[]{"username","operatetime","content","curd","aopMethod"};
		String sql="select username,operate_time,content,curd,aopMethod from SYS_OPERATE_LOG t";
		if(StringUtils.isNotBlank(condition)){
			List param =new ArrayList();
			String temparam = "%"+condition+"%";
			param.add(temparam);
			param.add(temparam);
			param.add(temparam);
			param.add(temparam);
			sql+=" WHERE username LIKE ? OR operate_time LIKE ? OR CONTENT LIKE ? OR curd LIKE ?";
			sql+=" order by operate_time desc";
			return getPageObject(sql, start, limit, param, str);
//			return getNoPageObject(sql, param, str, "查询日志");
		}else{
			sql+=" order by operate_time desc";
			return getPageObject(sql, start, limit,str);
//			return getNoPageObject(sql,str,"查询日志");
		}
	}


}
