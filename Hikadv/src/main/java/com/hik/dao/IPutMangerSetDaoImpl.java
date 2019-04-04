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
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IPutMangerSetDaoImpl extends BaseHIKDao implements IPutMangerSetDao{

	@Override
	public Page queryForPage(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","timeid","timename","addredssid","addressname","eventid","eventname",/*"phonetype","ptypename","phonesystem","psysname",*/"phone","pname",/*"clicktype","viewtype",*/"creator","creatime","modifier","modifytime","descr","name"};
//		String sql="SELECT ID,"+
////			       "STIME,"+
//			       "TIMEID,"+
//			       "ADDREDSSID,"+
//			       "EVENTID,"+
//			       "PHONETYPE,"+
//			       "PHONESYSTEM,"+
//			       "PHONE,"+
//			       "CLICKTYPE,"+
//			       "VIEWTYPE,"+
//			       "CREATOR,"+
//			       "CREATIME,"+
//			       "MODIFIER,"+
//			       "MODIFYTIME,"+
//			       "DESCR,"+
//			       "NAME "+
//				  "FROM PRICONDITION";
		
		String sql=
				"SELECT P.ID, "+
			       "P.TIMEID, "+
			       "TG.NAME AS TIMENAME, "+
			       "P.ADDREDSSID, "+
			       "AG.NAME AS ADDRESSNAME, "+
			       "P.EVENTID, "+
			       "EG.NAME AS EVENTNAME, "+
//			       "P.PHONETYPE, "+
//			       "CTG.NAME AS PTYPENAME, "+
//			       "P.PHONESYSTEM, "+
//			       "SOG.NAME AS PSYSNAME, "+
			       "P.PHONE, "+
			       "TPG.NAME AS PNAME, "+
//			       "P.CLICKTYPE, "+
//			       "P.VIEWTYPE, "+
			       "P.CREATOR, "+
			       "P.CREATIME, "+
			       "P.MODIFIER, "+
			       "P.MODIFYTIME, "+
			       "P.DESCR, "+
			       "P.NAME  "+
			    "FROM PRICONDITION P "+
			    "LEFT JOIN TIMEGROUP TG ON(P.TIMEID=TG.ID) "+
			    "LEFT JOIN EVENTGROUP EG ON(P.EVENTID=EG.ID) "+
//			    "LEFT JOIN SYSOPERGROUP SOG ON(P.PHONESYSTEM=SOG.ID) "+
//			    "LEFT JOIN CLIENTTYPEGROUP CTG ON(P.PHONETYPE=CTG.ID) "+
			    "LEFT JOIN telphonegroup TPG ON(P.PHONE=TPG.ID) "+
			    "LEFT JOIN addressgroup AG ON(P.ADDREDSSID=AG.ID) ";
			    
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+=" where  "+ 
//				"STIME like ? or "+
		       "TG.NAME like ? or "+
		       "AG.NAME like ? or "+
		       "EG.NAME like ? or "+
//		       "CTG.NAME like ? or "+
//		       "SOG.NAME like ? or "+
		       "TPG.NAME like ? or "+
//		       "P.CLICKTYPE like ? or "+
//		       "P.VIEWTYPE like ? or "+
		       "P.CREATOR like ? or "+
		       "P.CREATIME like ? or "+
		       "P.MODIFIER like ? or "+
		       "P.MODIFYTIME like ? or "+
		       "P.DESCR like ? or "+
		       "P.NAME like ?";
			for(int i=0;i<14;i++){
				param.add(temparam);
			}
		}
		Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询物料信息");
		return page;
	}

	@Override
	public int addAdvputSet(PRICONDITION pricondition) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(pricondition.getTimeid());
//		param.add(pricondition.getEtime());
		param.add(pricondition.getAddredssid());
		param.add(pricondition.getEventid());
//		param.add(pricondition.getPhonetype());
//		param.add(pricondition.getPhonesystem());
		param.add(pricondition.getPhone());
//		param.add(pricondition.getClicktype());			
//		param.add(pricondition.getViewtype());	
		param.add(pricondition.getCreator());
		param.add(pricondition.getDescr());
		param.add(pricondition.getName());
		String sql="insert into PRICONDITION(ID,"+
	       "TIMEID,"+
//	       "ETIME,"+
	       "ADDREDSSID,"+
	       "EVENTID,"+
//	       "PHONETYPE,"+
//	       "PHONESYSTEM,"+
	       "PHONE,"+
//	       "CLICKTYPE,"+
//	       "VIEWTYPE,"+
	       "CREATOR,"+
	       "CREATIME,"+
	       "DESCR,"+
	       "NAME) values(usermanger_sqe.nextval,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?,?)";
		return executeUpdateSQL(sql, param,"新增更新广告投放条件数据");
	}

	@Override
	public int updateAdvputSet(PRICONDITION pricondition) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(pricondition.getTimeid());
//		param.add(pricondition.getEtime());
		param.add(pricondition.getAddredssid());
		param.add(pricondition.getEventid());
//		param.add(pricondition.getPhonetype());
//		param.add(pricondition.getPhonesystem());
		param.add(pricondition.getPhone());
//		param.add(pricondition.getClicktype());			
//		param.add(pricondition.getViewtype());	
		param.add(pricondition.getModifier());
		param.add(pricondition.getDescr());
		param.add(pricondition.getName());
		param.add(pricondition.getId());
//		param.add("11061");
		String sql="update PRICONDITION SET "+
	       "TIMEID=?,"+
//	       "ETIME=?,"+
	       "ADDREDSSID=?,"+
	       "EVENTID=?,"+
//	       "PHONETYPE=?,"+
//	       "PHONESYSTEM=?,"+
	       "PHONE=?,"+
//	       "CLICKTYPE=?,"+
//	       "VIEWTYPE=?,"+
	       "MODIFIER=?,"+
	       "MODIFYTIME=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),"+
	       "DESCR=?,"+
	       "NAME=? where id=?";
		return executeUpdateSQL(sql, param,"更新广告投放条件数据");
	}

	@Override
	public int deleteAdvputSet(String[] id) {
		// TODO Auto-generated method stub
		
		if(id.length>0){
			List param = new ArrayList();
			String sql="delete from PRICONDITION where id in(";
			for(int i=0;i<id.length;i++){
				param.add(id[i]);
				sql+="?,";
			}
			sql=sql.replace(sql.charAt(sql.length()-1)+"",")");
			return executeUpdateSQL(sql, param);
		}else{
			return 0;
		}
	}

	@Override
	public List queryputSet() {
		// TODO Auto-generated method stub
		String sql="select id,name from PRICONDITION";
		String[] str = new String[]{"id","name"};
		return getNoPageObject(sql, str);
	}
	
}
