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
import com.hik.app.entity.MMPPPRODUCT;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

@Repository
public class ImmppProductDaoImpl extends BaseHIKDao implements ImmppProductDao{
	public Page getmmppgroup(int start, int limit,String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		
		
		String[] str = new String[]{"id","mmppname1","mmppname2","mmppname3","mmppname4","mmppname5","mmppname6","mmppname7","mmppname8","mmppname9","mmppid1","mmppid2","mmppid3","mmppid4","mmppid5","mmppid6","mmppid7","mmppid8","mmppid9","creator","creatime", "modifier","modifytime","playstrager","playstragerid","descr","name"};
		String sql="SELECT T.ID,"+
			       "A1.NAME AS mmpp1,"+
			       "A2.NAME AS mmpp2,"+
			       "A3.NAME AS mmpp3,"+
			       "A4.NAME AS mmpp4,"+
			       "A5.NAME AS mmpp5,"+
			       "A6.NAME AS mmpp6,"+
			       "A7.NAME AS mmpp7,"+
			       "A8.NAME AS mmpp8,"+
			       "A9.NAME AS mmpp9,"+
			       "A1.ID as mmppid1,"+
			       "A2.ID as mmppid2,"+
			       "A3.ID as mmppid3,"+
			       "A4.ID as mmppid4,"+
			       "A5.ID as mmppid5,"+
			       "A6.ID as mmppid6,"+
			       "A7.ID as mmppid7,"+
			       "A8.ID as mmppid8,"+
			       "A9.ID as mmppid9,"+
			       "t.CREATOR,"+
			       "t.CREATIME,"+
			       "T.MODIFYOR,"+
			       "T.MODIFYTIME,"+
			       "P.NAME AS PLAYSTRAGERNAME,"+
//			       "P2.NAME AS PLAYPRISTRAGERNAME,"+
			       "P.CID AS PLAYSTRAGERID,"+
//			       "P2.CID AS PLAYPRISTRAGERID,"+
			       "T.DESCR,"+
			       "T.NAME "+
			  "FROM mmpp_product T "+
			  "LEFT JOIN mmpp A1 "+
			    "ON (T.mmppID1 = A1.ID) "+
			  "LEFT JOIN mmpp A2 "+
			    "ON (T.mmppID2 = A2.ID) "+
			  "LEFT JOIN mmpp A3 "+
			    "ON (T.mmppID3 = A3.ID) "+
			  "LEFT JOIN mmpp A4 "+
			    "ON (T.mmppID4 = A4.ID) "+
			  "LEFT JOIN mmpp A5 "+
			    "ON (T.mmppID5 = A5.ID) "+
			  "LEFT JOIN mmpp A6 "+
			    "ON (T.mmppID6 = A6.ID) "+
			  "LEFT JOIN mmpp A7 "+
			    "ON (T.mmppID7 = A7.ID) "+
			  "LEFT JOIN mmpp A8 "+
			    "ON (T.mmppID8 = A8.ID) "+
			  "LEFT JOIN mmpp A9 "+
			    "ON (T.mmppID9 = A9.ID) "+
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
					 "T.MODIFYOR like ? or "+
					 "T.MODIFYTIME like ? or "+
					 "P.NAME like ? or "+
					 "T.DESCR like ? or "+
					 "T.NAME like ? ";
			for(int i=0;i<17;i++){
				param.add(temparam);				
			}
		}
		return getPageObject(sql, start, limit, param,str, "分页查询所有的广告信息");
	}

	@Override
	public int savemmppgroup(MMPPPRODUCT mmppproduct) {
		// TODO Auto-generated method stub
		  
		List<String> param = new ArrayList<String>();
		param.add(mmppproduct.getmmppid1());
		param.add(mmppproduct.getmmppid2());
		param.add(mmppproduct.getmmppid3());
		param.add(mmppproduct.getmmppid4());
		param.add(mmppproduct.getmmppid5());
		param.add(mmppproduct.getmmppid6());
		param.add(mmppproduct.getmmppid7());
		param.add(mmppproduct.getmmppid8());
		param.add(mmppproduct.getmmppid9());
		param.add(mmppproduct.getCreator());
		param.add(mmppproduct.getPlaystrager());
		param.add(mmppproduct.getDescr());
		param.add(mmppproduct.getName());
		
		String sql="insert into MMPP_PRODUCT(id,mmppid1,mmppid2,mmppid3,mmppid4,mmppid5,mmppid6,mmppid7,mmppid8,mmppid9,creator,creatime,playstrager,descr,name) "
				+ "values(MMPP_PRODUCT_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		return executeUpdateSQL(sql, param,"新增新的公众号产品数据");
	}

	@Override
	public int updatemmppgroup(MMPPPRODUCT mmppproduct) {
		// TODO Auto-generated method stub
		List<String> param = new ArrayList<String>();
		param.add(mmppproduct.getmmppid1());
		param.add(mmppproduct.getmmppid2());
		param.add(mmppproduct.getmmppid3());
		param.add(mmppproduct.getmmppid4());
		param.add(mmppproduct.getmmppid5());
		param.add(mmppproduct.getmmppid6());
		param.add(mmppproduct.getmmppid7());
		param.add(mmppproduct.getmmppid8());
		param.add(mmppproduct.getmmppid9());
		param.add(mmppproduct.getCreator());
		param.add(mmppproduct.getPlaystrager());
//		param.add(advgroup.getPlaypristrager());
		param.add(mmppproduct.getDescr());
		param.add(mmppproduct.getName());
		param.add(mmppproduct.getId());
		String sql="update MMPP_PRODUCT set mmppid1=?,mmppid2=?,mmppid3=?,mmppid4=?,mmppid5=?,mmppid6=?,mmppid7=?,mmppid8=?,mmppid9=?,MODIFYOR=?,MODIFYTIME=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),"
				+ " playstrager=?,descr=?,name=? where id=?";
		return executeUpdateSQL(sql, param,"更新公众号产品数据");
	}

	@Override
	public int deletemmppgroup(String[] ids) {
		// TODO Auto-generated method stub
		List param = Arrays.asList(ids);
		String sql="delete from MMPP_PRODUCT where id in (";
		if(!param.isEmpty()&&param.size()>0){
			for(int i=0;i<param.size();i++){
				sql+="?,";
			}
			sql=sql.replace(sql.charAt(sql.length()-1)+"",")");
			return executeUpdateSQL(sql, param, "删除公众号产品数据");
		}else{
			return 0;			
		}
	}

	@Override
	public List queryifputProduct(String[] ids){
		// TODO Auto-generated method stub
		List param =new ArrayList();
		String sql="SELECT COUNT(*), S.PRODUCTID, P.NAME "+
				  "FROM SCHEDULE S "+
				  "LEFT JOIN TIMESETTING T "+
				    "ON (S.INTERVAL = T.ID) "+
				  "LEFT JOIN MMPP_PRODUCT P "+
				    "ON (S.PRODUCTID = P.ID) "+
				 "WHERE S.DATETIME || ' ' || T.STARTTIME > "+
				       "TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
				       "AND S.PRODUCTID IN (";
				    for(int i=0;i<ids.length;i++){
				    	sql+="?,";
				    	param.add(ids[i]);
				    } 
				    sql=sql.substring(0, sql.length()-1);
				    sql+=") ";
				 sql+="GROUP BY S.PRODUCTID, P.name ";
		String[] str = new String[]{"count","productid","productname"};
		return null;
	}

//	@Override
//	public List queryGroupPutproduct(String[] ids) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		String sql="select COUNT(*) from MMPP_PRODUCT t WHERE t.advgroup1 in(";
//		for(int i=0;i<ids.length;i++){
//			sql+="?,";
//			param.add(ids[i]);
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=") or ";
//		
//		sql+="t.advgroup2 in(";
//		for(int i=0;i<ids.length;i++){
//			sql+="?,";
//			param.add(ids[i]);
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=") or ";
//		
//		sql+="t.advgroup3 in(";
//		for(int i=0;i<ids.length;i++){
//			sql+="?,";
//			param.add(ids[i]);
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=") or ";
//		
//		sql+="t.advgroup4 in(";
//		for(int i=0;i<ids.length;i++){
//			sql+="?,";
//			param.add(ids[i]);
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=") or ";
//		
//		sql+="t.advgroup5 in(";
//		for(int i=0;i<ids.length;i++){
//			sql+="?,";
//			param.add(ids[i]);
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=")";
////		select COUNT(*) from PUTPRODUCT t WHERE t.advgroup1 in(61,102) OR t.advposid2 IN (61,102) OR  t.advposid3 in (61,102) OR t.advposid4 IN (61,102) OR t.advposid5 IN (61,102) 
//
//		return getNoPageObject(sql, param);
//	}



}
