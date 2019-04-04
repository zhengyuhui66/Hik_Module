package com.hik.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

@Repository
public class IAdvertGroupDaoImpl extends BaseHIKDao implements IAdvertGroupDao{
	public Page getAdvertgroup(int start, int limit,String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		
		
		String[] str = new String[]{"id","advname1","advname2","advname3","advname4","advname5","advname6","advname7","advname8","advname9","advid1","advid2","advid3","advid4","advid5","advid6","advid7","advid8","advid9","creator","creatime", "modifier","modifytime","playstrager","playstragerid","descr","name"};
		String sql="SELECT T.ID,"+
			       "A1.NAME AS adv1,"+
			       "A2.NAME AS adv2,"+
			       "A3.NAME AS adv3,"+
			       "A4.NAME AS adv4,"+
			       "A5.NAME AS adv5,"+
			       "A6.NAME AS adv6,"+
			       "A7.NAME AS adv7,"+
			       "A8.NAME AS adv8,"+
			       "A9.NAME AS adv9,"+
			       "A1.ID as advid1,"+
			       "A2.ID as advid2,"+
			       "A3.ID as advid3,"+
			       "A4.ID as advid4,"+
			       "A5.ID as advid5,"+
			       "A6.ID as advid6,"+
			       "A7.ID as advid7,"+
			       "A8.ID as advid8,"+
			       "A9.ID as advid9,"+
			       "t.CREATOR,"+
			       "t.CREATIME,"+
			       "T.MODIFIER,"+
			       "T.MODIFYTIME,"+
			       "P.NAME AS PLAYSTRAGERNAME,"+
//			       "P2.NAME AS PLAYPRISTRAGERNAME,"+
			       "P.CID AS PLAYSTRAGERID,"+
//			       "P2.CID AS PLAYPRISTRAGERID,"+
			       "T.DESCR,"+
			       "T.NAME "+
			  "FROM ADVGROUP T "+
			  "LEFT JOIN ADVERTMANGER A1 "+
			    "ON (T.ADVID1 = A1.ID) "+
			  "LEFT JOIN ADVERTMANGER A2 "+
			    "ON (T.ADVID2 = A2.ID) "+
			  "LEFT JOIN ADVERTMANGER A3 "+
			    "ON (T.ADVID3 = A3.ID) "+
			  "LEFT JOIN ADVERTMANGER A4 "+
			    "ON (T.ADVID4 = A4.ID) "+
			  "LEFT JOIN ADVERTMANGER A5 "+
			    "ON (T.ADVID5 = A5.ID) "+
			  "LEFT JOIN ADVERTMANGER A6 "+
			    "ON (T.ADVID6 = A6.ID) "+
			  "LEFT JOIN ADVERTMANGER A7 "+
			    "ON (T.ADVID7 = A7.ID) "+
			  "LEFT JOIN ADVERTMANGER A8 "+
			    "ON (T.ADVID8 = A8.ID) "+
			  "LEFT JOIN ADVERTMANGER A9 "+
			    "ON (T.ADVID9 = A9.ID) "+
			  "LEFT JOIN PLAY_FUN P "+
			    "ON (T.PLAYSTRAGER = P.CID) ";
//			  "LEFT JOIN PLAY_FUN P2 "+
//			    "ON (T.PLAYPRISTRAGER = P2.CID)";
		if(StringUtils.isNotBlank(searchStr)){
			String temparam = "%"+searchStr+"%";
//			sql+= " where "+
			sql+= " where T.ID like ? or "+
					 "A1.NAME like ? or "+
					 "A2.NAME like ? or "+
					 "A3.NAME like ? or "+
					 "A4.NAME like ? or "+
					 "A5.NAME like ? or "+
					 "A6.NAME like ? or "+
					 "A7.NAME like ? or "+
					 "A8.NAME like ? or "+
					 "A9.NAME like ? or "+
					 "t.CREATOR like ? or "+
					 "t.CREATIME like ? or "+
					 "T.MODIFIER like ? or "+
					 "T.MODIFYTIME like ? or "+
					 "P.NAME like ? or "+
//					 "P2.NAME like ? or "+
					 "T.DESCR like ? or "+
					 "T.NAME like ? ";
			for(int i=0;i<17;i++){
				param.add(temparam);				
			}
		}
		return getPageObject(sql, start, limit, param,str, "分页查询所有的广告信息");
	}

	@Override
	public int saveAdvertgroup(ADVGROUP advgroup) {
		// TODO Auto-generated method stub
		  
		List<String> param = new ArrayList<String>();
		param.add(advgroup.getAdvid1());
		param.add(advgroup.getAdvid2());
		param.add(advgroup.getAdvid3());
		param.add(advgroup.getAdvid4());
		param.add(advgroup.getAdvid5());
		param.add(advgroup.getAdvid6());
		param.add(advgroup.getAdvid7());
		param.add(advgroup.getAdvid8());
		param.add(advgroup.getAdvid9());
		param.add(advgroup.getCreator());
		param.add(advgroup.getPlaystrager());
//		param.add(advgroup.getPlaypristrager());
		param.add(advgroup.getDescr());
		param.add(advgroup.getName());
		
		String sql="insert into ADVGROUP(id,advid1,advid2,advid3,advid4,advid5,advid6,advid7,advid8,advid9,creator,creatime,playstrager,descr,name) "
				+ "values(ADVGROUP_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		return executeUpdateSQL(sql, param,"新增新的广告集数据");
	}

	@Override
	public int updateAdvertgroup(ADVGROUP advgroup) {
		// TODO Auto-generated method stub
		List<String> param = new ArrayList<String>();
		param.add(advgroup.getAdvid1());
		param.add(advgroup.getAdvid2());
		param.add(advgroup.getAdvid3());
		param.add(advgroup.getAdvid4());
		param.add(advgroup.getAdvid5());
		param.add(advgroup.getAdvid6());
		param.add(advgroup.getAdvid7());
		param.add(advgroup.getAdvid8());
		param.add(advgroup.getAdvid9());
		param.add(advgroup.getCreator());
		param.add(advgroup.getPlaystrager());
//		param.add(advgroup.getPlaypristrager());
		param.add(advgroup.getDescr());
		param.add(advgroup.getName());
		param.add(advgroup.getId());
		String sql="update ADVGROUP set advid1=?,advid2=?,advid3=?,advid4=?,advid5=?,advid6=?,advid7=?,advid8=?,advid9=?,modifier=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),"
				+ " playstrager=?,descr=?,name=? where id=?";
		return executeUpdateSQL(sql, param,"更新广告集数据");
	}

	@Override
	public int deleteAdvertgroup(String[] ids) {
		// TODO Auto-generated method stub
		List param = Arrays.asList(ids);
		String sql="delete from ADVGROUP where id in (";
		if(!param.isEmpty()&&param.size()>0){
			for(int i=0;i<param.size();i++){
				sql+="?,";
			}
			sql=sql.replace(sql.charAt(sql.length()-1)+"",")");
			return executeUpdateSQL(sql, param, "删除广告集数据");
		}else{
			return 0;			
		}
	}

	@Override
	public List queryGroupPutproduct(String[] ids) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="select COUNT(*) from PUTPRODUCT t WHERE t.advgroup1 in(";
		for(int i=0;i<ids.length;i++){
			sql+="?,";
			param.add(ids[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=") or ";
		
		sql+="t.advgroup2 in(";
		for(int i=0;i<ids.length;i++){
			sql+="?,";
			param.add(ids[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=") or ";
		
		sql+="t.advgroup3 in(";
		for(int i=0;i<ids.length;i++){
			sql+="?,";
			param.add(ids[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=") or ";
		
		sql+="t.advgroup4 in(";
		for(int i=0;i<ids.length;i++){
			sql+="?,";
			param.add(ids[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=") or ";
		
		sql+="t.advgroup5 in(";
		for(int i=0;i<ids.length;i++){
			sql+="?,";
			param.add(ids[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
//		select COUNT(*) from PUTPRODUCT t WHERE t.advgroup1 in(61,102) OR t.advposid2 IN (61,102) OR  t.advposid3 in (61,102) OR t.advposid4 IN (61,102) OR t.advposid5 IN (61,102) 

		return getNoPageObject(sql, param);
	}



}
