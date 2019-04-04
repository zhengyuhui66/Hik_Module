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
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IPutProductDaoImpl extends BaseHIKDao implements IPutProductDao{

	@Override
	public Page queryputProduct(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","proname","modelid","name","advgroup1","advgroup2","advgroup3","advgroup4","advgroup5","advgroupname1","advgroupname2","advgroupname3","advgroupname4","advgroupname5","creatime","creator","modifier","modifytime","isdelete","descr"};
		String sql="SELECT p.id,"+
					 "p.proname,"+
			         "p.modelid,"+
			         "am.name,"+
			         "p.advgroup1,"+
			         "p.advgroup2,"+
			         "p.advgroup3,"+
			         "p.advgroup4,"+
			         "p.advgroup5,"+
			        "a1.name as advgroupname1,"+
			        "a2.name as advgroupname2,"+
			        "a3.name as advgroupname3,"+
			        "a4.name as advgroupname4,"+
			        "a5.name as advgroupname5,"+
			        "p.createtime,"+
			        "p.creator,"+
			        "p.modifier,"+
			        "p.modifytime,"+
			        "p.isdelete,"+
			        "p.descr FROM PUTPRODUCT p "+
			        "LEFT JOIN advgroup a1 ON(a1.id=p.advgroup1) "+
			        "LEFT JOIN advgroup a2 ON(a2.id=p.advgroup2) "+
			        "LEFT JOIN advgroup a3 ON(a3.id=p.advgroup3) "+
			        "LEFT JOIN advgroup a4 ON(a4.id=p.advgroup4) "+
			        "LEFT JOIN advgroup a5 ON(a5.id=p.advgroup5) "+
			        "LEFT JOIN advertmodelmanger am ON(am.id=p.modelid) where p.isdelete='1' ";
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+=" and (a1.name like ? or "+
		       "a2.name like ? or "+
		       "a3.name like ? or "+
		       "a4.name like ? or "+
		       "a5.name like ? or "+
		       "p.createtime like ? or "+
		       "p.creator like ? or "+
		       "p.modifier like ? or "+
		       "p.modifytime like ? or "+
		       "p.descr like ?)";
			for(int i=0;i<10;i++){
				param.add(temparam);
			}
		}
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询物料信息");			
		}
		return page;
	}
	
	@Override
	public int deleteputProduct(String[] ids) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		if(ids.length>0){
			String sql="update PUTPRODUCT set isdelete=0 where id in(";
			for(int i=0;i<ids.length;i++){
				param.add(ids[i]);
				sql+="?,";
			}
			sql=sql.replace(sql.charAt(sql.length()-1)+"",")");
			return executeUpdateSQL(sql, param);
		}
		return 0;
	}

	@Override
	public int addputProduct(PUTPRODUCT putproduct) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(putproduct.getProname());
		param.add(putproduct.getModelid());
		param.add(putproduct.getAdvgroup1());
		param.add(putproduct.getAdvgroup2());
		param.add(putproduct.getAdvgroup3());
		param.add(putproduct.getAdvgroup4());
		param.add(putproduct.getAdvgroup5());
		param.add(putproduct.getCreator());
		param.add(putproduct.getDescr());
		String sql="insert into PUTPRODUCT(id,proname,modelid,advgroup1,advgroup2,advgroup3,advgroup4,advgroup5,creator,descr,isdelete,createtime) values(PUTPRODUCT_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,'1',TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'))";
		return executeUpdateSQL(sql, param);
	}

	@Override
	public int updateputProduct(PUTPRODUCT putproduct) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(putproduct.getProname());
		param.add(putproduct.getModelid());
		param.add(putproduct.getAdvgroup1());
		param.add(putproduct.getAdvgroup2());
		param.add(putproduct.getAdvgroup3());
		param.add(putproduct.getAdvgroup4());
		param.add(putproduct.getAdvgroup5());
		param.add(putproduct.getModifier());
		param.add(putproduct.getDescr());
		param.add(putproduct.getId());
		String sql="update PUTPRODUCT set proname=?,modelid=?,advgroup1=?,advgroup2=?,advgroup3=?,advgroup4=?,advgroup5=?,modifier=?,descr=?,modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') where id=?";
		return executeUpdateSQL(sql, param);
	}

	@Override
	public List queryifputProduct(String[] ids) {
		// TODO Auto-generated method stub
		List param =new ArrayList();
		String sql="SELECT COUNT(*), S.PRODUCTID, P.PRONAME "+
				  "FROM SCHEDULE S "+
				  "LEFT JOIN TIMESETTING T "+
				    "ON (S.INTERVAL = T.ID) "+
				  "LEFT JOIN PUTPRODUCT P "+
				    "ON (S.PRODUCTID = P.ID) "+
				 "WHERE S.DATETIME || ' ' || T.STARTTIME > "+
				       "TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
				   "AND S.PRODUCTID IN ( ";
				    for(int i=0;i<ids.length;i++){
				    	sql+="?,";
				    	param.add(ids[i]);
				    } 
			    sql=sql.substring(0, sql.length()-1);
			    sql+=") ";
				sql+="GROUP BY S.PRODUCTID, P.PRONAME ";
				String[] str = new String[]{"count","productid","productname"};
		return getNoPageObject(sql, param, str);
	}
}
