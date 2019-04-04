package com.hik.framework.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.dao.BaseHIKDao;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;


@Repository
public class ClientDaoImpl extends BaseHIKDao implements ClientDao{

	@Override
	public Page queryClientInfo(int start,int limit) {
		// TODO Auto-generated method stub
		String sql="select id,name,type,version,versionname,versiondesc,url,creatime,creator from CLIENTMANGER";
		String[] str = new String[]{"id","name","type","version","versionname","versiondesc","url","creatime","creator"};
//		return getPageObject(sql, start, limit);
		return getPageObject(sql, start, limit, str);
	}

	@Override
	public int insertClient(String name, String type, String version, String versiondesc, String versionname,
			String url,String userid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(name);
		param.add(type);
		param.add(version);
		param.add(versionname);
		param.add(versiondesc);
		param.add(url);
		param.add(userid);
		String sql="insert into CLIENTMANGER(id,name,type,version,versionname,versiondesc,url,creatime,creator) "
				+ "values (CLIENTMANGER_SEQ.nextval,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?)";
		
		return executeUpdateSQL(sql, param);
	}
}
