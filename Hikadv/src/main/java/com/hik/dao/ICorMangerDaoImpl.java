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
import com.hik.app.entity.CORP;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class ICorMangerDaoImpl extends BaseHIKDao implements ICorMangerDao{

	@Override
	public Page queryCorManger(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","name","address","createtime","creator","modifier","modifytime","mark"};
		String sql="select p.id,p.name,p.address,p.createtime,p.creator,p.modifier,p.modifytime,p.mark from corp p ";
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+="where p.name like ? or "+
		       "p.address like ? or "+
		       "p.createtime like ? or "+
		       "p.creator like ? or "+
		       "p.modifier like ? or "+
		       "p.modifytime like ? or "+
		       "p.mark like ? ";
			for(int i=0;i<7;i++){
				param.add(temparam);
			}
		}
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询公司信息");			
		}
		return page;
	}
	
	@Override
	public String deleteCorManger(String[] ids) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="delete from corp where id in(";
		for(int i=0;i<ids.length;i++){
			param.add(ids[i]);
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		int str =executeUpdateSQL(sql, param);
		return str+"";
//		if(ids.length>0){
//			String sql="update PUTPRODUCT set isdelete=0 where id in(";
//			for(int i=0;i<ids.length;i++){
//				param.add(ids[i]);
//				sql+="?,";
//			}
//			sql=sql.replace(sql.charAt(sql.length()-1)+"",")");
//			return executeUpdateSQL(sql, param);
//		}
//		return 0;
	}

	@Override
	public int addCorManger(CORP corp) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(corp.getName());
		param.add(corp.getAddress());
		param.add(corp.getCreator());
		param.add(corp.getCreatetime());
//		param.add(corp.getModifier());
//		param.add(corp.getModifytime());
		param.add(corp.getMark());
		String sql="insert into corp(id,name,address,creator,createtime,modifier,modifytime,mark) values(CORP_SEQ.NEXTVAL,?,?,?,?,null,null,?)";
		return executeUpdateSQL(sql, param,"添加新的公司信息");
	}

	@Override
	public int updateCorManger(CORP corp) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(corp.getName());
		param.add(corp.getAddress());
//		param.add(corp.getCreator());
//		param.add(corp.getCreatetime());
		param.add(corp.getModifier());
		param.add(corp.getModifytime());
		param.add(corp.getMark());
		param.add(corp.getId());
		String sql="update corp set name=?,address=?,modifier=?,modifytime=?,mark=? where id=?";
		return executeUpdateSQL(sql, param,"更改公司信息");
	}

	@Override
	public List queryifCorManger(String[] ids) {
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
