package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class IPutScheduleDaoImpl extends BaseHIKDao implements IPutScheduleDao{
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public Page queryPutLog(int start, int limit, List<String> date, String[] timeslice, JSONArray authcycid,JSONObject mmppJson,
			String routeid,String busids, String userid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="";
		if(authcycid!=null&&authcycid.size()>0){
				sql="SELECT s.id,v.code as busname,vd.apmac,a.name as authcycname,s.datetime as datetime,t.name as timeslice,p.proname as proname,"+ 
					"decode(s.state,'1','正常投放','2','暂停投放','3','取消投放','其它状态') as state FROM SCHEDULE s "+
			        "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
			        "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
			        "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
			        "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
			        "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
				String sql0="";
				if(date!=null&&date.size()>0){
					sql0+="and s.datetime in(";
					for(int i=0;i<date.size();i++){
						sql0+="?,";
						param.add(date.get(i).toString());
					}
					sql0=sql0.substring(0, sql0.length()-1);
					sql0+=")";
				}
				String sql1="";
				if(timeslice!=null&&timeslice.length>0){
					sql1+=" and s.interval in(";
					for(int i=0;i<timeslice.length;i++){
						sql1+="?,";
						param.add(timeslice[i]);
					}
					sql1=sql1.substring(0, sql1.length()-1);
					sql1+=") ";
				}
				
				String sql2="";
				if(authcycid!=null&&authcycid.size()>0){
					sql2+= "and ( ";
					for(int i=0;i<authcycid.size();i++){
						JSONObject json=authcycid.getJSONObject(i);
						String cycid=json.getString("cycid");
						String tempsql=" (";
						if(!StringUtils.isEmpty(cycid)&&!"null".equals(cycid)){
							tempsql+=" s.authorder = ?";
							param.add(cycid);
						}
						String modelid=json.getString("modelid");
						if(!StringUtils.isEmpty(modelid)&&!"null".equals(modelid)){
							if(tempsql.length()>1){
								tempsql+=" and";
							}
							tempsql+=" p.MODELID = ?";
							param.add(modelid);
						}
						String productid=json.getString("productid");
						if(!StringUtils.isEmpty(productid)&&!"null".equals(productid)){
							if(tempsql.length()>1){
								tempsql+=" and";
							}
							tempsql+=" p.ID = ?";
							param.add(productid);
						}
						tempsql+=")";
						sql2+=tempsql+" or";
					}
					sql2=sql2.substring(0,sql2.length()-2);
					sql2+=") ";
				}
				String sql3="";
				if(!StringUtils.isEmpty(routeid)){
					sql3+="and v.routeid = ? ";
					param.add(routeid);
				}
				String sql4="";
				if(!StringUtils.isEmpty(busids)){
					sql4+="and v.id =? ";
					param.add(busids);
				}
				String sql5 = sql0+sql1+sql2+sql3+sql4;
				if(!StringUtils.isEmpty(sql5)){
					sql5=sql5.substring(3, sql5.length());
					sql+=" where "+ sql5;
				}

		}
//		===============联合公众号查询========================================================================================
		String tempsql="";
		if(mmppJson!=null&&mmppJson.size()>0){
			tempsql+="SELECT s.id,v.code as busname,vd.apmac,a.name as authcycname,s.datetime as datetime,t.name as timeslice,p.name as proname,"+ 
				"decode(s.state,'1','正常投放','2','暂停投放','3','取消投放','其它状态') as state FROM SCHEDULE s "+
				"LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
				"LEFT JOIN  MMPP_PRODUCT p ON(p.id=s.productid) "+
				"LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
				"LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
				"LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
			String sql10="";
			if(date!=null&&date.size()>0){
				sql10+="and s.datetime in(";
				for(int i=0;i<date.size();i++){
					sql10+="?,";
					param.add(date.get(i).toString());
				}
				sql10=sql10.substring(0, sql10.length()-1);
				sql10+=")";
			}
			String sql11="";
			if(timeslice!=null&&timeslice.length>0){
				sql11+=" and s.interval in(";
				for(int i=0;i<timeslice.length;i++){
					sql11+="?,";
					param.add(timeslice[i]);
				}
				sql11=sql11.substring(0, sql11.length()-1);
				sql11+=") ";
			}
			String sql12=" and s.authorder = ? ";
			String cycid=JSONUtils.getString(mmppJson, "cycid");
			param.add(cycid);
			String productid=JSONUtils.getString(mmppJson, "productid");
			
			
					
			if(StringUtils.isNotBlank(productid)){
				sql12+= "and  p.ID = ?";
				
				param.add(productid);
			}
			String sql13="";
			if(!StringUtils.isEmpty(routeid)){
				sql13+="and v.routeid = ? ";
				param.add(routeid);
			}
			String sql14="";
			if(!StringUtils.isEmpty(busids)){
				sql14+="and v.id =? ";
				param.add(busids);
			}
			String sql15 = sql10+sql11+sql12+sql13+sql14;
			if(StringUtils.isNotEmpty(tempsql)){
				sql15=sql15.substring(3, sql15.length());
				tempsql+=" where "+ sql15;
			}

		}
		if(StringUtils.isNotEmpty(sql)&&StringUtils.isNotEmpty(tempsql)){
			sql=sql+" union all "+tempsql;
		}
		if(StringUtils.isEmpty(sql)&&StringUtils.isNotEmpty(tempsql)){
			sql=tempsql;
		}
		
		sql+=" order by id";			
		String[] jsonStr=new String[]{"id","busname","apmac","authcycname","datetime","timeslice","proname","state"};
		Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询排期日志");
		return page;
	}
	
	@Override
	public List queryPutLogList(List<String> date, String[] timeslice, JSONArray authcycid, JSONObject mmppJson,String routeid,
			String busids, String userid)  throws Exception{
		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		String sql="SELECT s.id,s.datetime,s.interval,s.productid,s.authorder,s.apid,s.state FROM SCHEDULE s "+
//		          "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
//		           "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
//		           "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
//		           "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
//		           "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
//		String sql0="";
//		if(date!=null&&date.size()>0){
//			sql0+="and s.datetime in(";
//			for(int i=0;i<date.size();i++){
//				sql0+="?,";
//				param.add(date.get(i).toString());
//			}
//			sql0=sql0.substring(0, sql0.length()-1);
//			sql0+=")";
//		}
//		String sql1="";
//		if(timeslice!=null&&timeslice.length>0){
//			sql1+="and s.interval in(";
//			for(int i=0;i<timeslice.length;i++){
//				sql1+="?,";
//				param.add(timeslice[i]);
//			}
//			sql1=sql1.substring(0, sql1.length()-1);
//			sql1+=") ";
//		}
//		
//		String sql2="";
//		if(authcycid!=null&&authcycid.size()>0){
//			sql2+= "and ( ";
//			
//			for(int i=0;i<authcycid.size();i++){
//				JSONObject json=authcycid.getJSONObject(i);
//				String cycid=json.getString("cycid");
//				if(PROCEDURCES.MMPPFLAG.equals(cycid)){
//					continue;
//				}
//				String tempsql=" (";
//				if(!StringUtils.isEmpty(cycid)&&!"null".equals(cycid)){
//					tempsql+=" s.authorder = ?";
//					param.add(cycid);
//				}
//				String modelid=json.getString("modelid");
//				if(!StringUtils.isEmpty(modelid)&&!"null".equals(modelid)){
//					if(tempsql.length()>1){
//						tempsql+=" and";
//					}
//					tempsql+=" p.MODELID = ?";
//					param.add(modelid);
//				}
//				String productid=json.getString("productid");
//				if(!StringUtils.isEmpty(productid)&&!"null".equals(productid)){
//					if(tempsql.length()>1){
//						tempsql+=" and";
//					}
//					tempsql+=" p.ID = ?";
//					param.add(productid);
//				}
//				tempsql+=")";
//				sql2+=tempsql+" or";
//			}
//			
//			sql2=sql2.substring(0,sql2.length()-2);
//			sql2+=") ";
//		}
//		String sql3="";
//		if(!StringUtils.isEmpty(routeid)){
//			sql3+="and v.routeid = ? ";
//			param.add(routeid);
//		}
//		String sql4="";
//		if(!StringUtils.isEmpty(busids)){
//			sql4+="and v.id =? ";
//			param.add(busids);
//		}
//		String sql5 = sql0+sql1+sql2+sql3+sql4;
//		
//		if(!StringUtils.isEmpty(sql5)){
//			sql5=sql5.substring(3, sql5.length());
//			sql+=" where "+ sql5;
//		}
		List param = new ArrayList();
		String sql="";
		if(authcycid!=null&&authcycid.size()>0){
//			String sql="SELECT s.id,s.datetime,s.interval,s.productid,s.authorder,s.apid,s.state FROM SCHEDULE s "+
//	          "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
//	           "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
//	           "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
//	           "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
//	           "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
//				sql="SELECT s.id,v.code as busname,vd.apmac,a.name as authcycname,s.datetime as datetime,t.name as timeslice,p.proname as proname,"+ 
//					"decode(s.state,'1','正常投放','2','暂停投放','3','取消投放','其它状态') as state FROM SCHEDULE s "+
				sql="SELECT s.id,s.datetime,s.interval,s.productid,s.authorder,s.apid,s.state FROM SCHEDULE s "+
			        "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
			        "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
			        "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
			        "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
			        "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
				String sql0="";
				if(date!=null&&date.size()>0){
					sql0+="and s.datetime in(";
					for(int i=0;i<date.size();i++){
						sql0+="?,";
						param.add(date.get(i).toString());
					}
					sql0=sql0.substring(0, sql0.length()-1);
					sql0+=")";
				}
				String sql1="";
				if(timeslice!=null&&timeslice.length>0){
					sql1+=" and s.interval in(";
					for(int i=0;i<timeslice.length;i++){
						sql1+="?,";
						param.add(timeslice[i]);
					}
					sql1=sql1.substring(0, sql1.length()-1);
					sql1+=") ";
				}
				
				String sql2="";
				if(authcycid!=null&&authcycid.size()>0){
					sql2+= "and ( ";
					for(int i=0;i<authcycid.size();i++){
						JSONObject json=authcycid.getJSONObject(i);
						String cycid=json.getString("cycid");
						String tempsql=" (";
						if(!StringUtils.isEmpty(cycid)&&!"null".equals(cycid)){
							tempsql+=" s.authorder = ?";
							param.add(cycid);
						}
						String modelid=json.getString("modelid");
						if(!StringUtils.isEmpty(modelid)&&!"null".equals(modelid)){
							if(tempsql.length()>1){
								tempsql+=" and";
							}
							tempsql+=" p.MODELID = ?";
							param.add(modelid);
						}
						String productid=json.getString("productid");
						if(!StringUtils.isEmpty(productid)&&!"null".equals(productid)){
							if(tempsql.length()>1){
								tempsql+=" and";
							}
							tempsql+=" p.ID = ?";
							param.add(productid);
						}
						tempsql+=")";
						sql2+=tempsql+" or";
					}
					sql2=sql2.substring(0,sql2.length()-2);
					sql2+=") ";
				}
				String sql3="";
				if(!StringUtils.isEmpty(routeid)){
					sql3+="and v.routeid = ? ";
					param.add(routeid);
				}
				String sql4="";
				if(!StringUtils.isEmpty(busids)){
					sql4+="and v.id =? ";
					param.add(busids);
				}
				String sql5 = sql0+sql1+sql2+sql3+sql4;
				if(!StringUtils.isEmpty(sql5)){
					sql5=sql5.substring(3, sql5.length());
					sql+=" where "+ sql5;
				}

		}
//		===============联合公众号查询========================================================================================
		String tempsql="";
		if(mmppJson!=null&&mmppJson.size()>0){
			
//			tempsql+="SELECT s.id,v.code as busname,vd.apmac,a.name as authcycname,s.datetime as datetime,t.name as timeslice,p.name as proname,"+ 
//				"decode(s.state,'1','正常投放','2','暂停投放','3','取消投放','其它状态') as state FROM SCHEDULE s "+
			tempsql="SELECT s.id,s.datetime,s.interval,s.productid,s.authorder,s.apid,s.state FROM SCHEDULE s "+
				"LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
				"LEFT JOIN  MMPP_PRODUCT p ON(p.id=s.productid) "+
				"LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
				"LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
				"LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
			String sql10="";
			if(date!=null&&date.size()>0){
				sql10+="and s.datetime in(";
				for(int i=0;i<date.size();i++){
					sql10+="?,";
					param.add(date.get(i).toString());
				}
				sql10=sql10.substring(0, sql10.length()-1);
				sql10+=")";
			}
			String sql11="";
			if(timeslice!=null&&timeslice.length>0){
				sql11+=" and s.interval in(";
				for(int i=0;i<timeslice.length;i++){
					sql11+="?,";
					param.add(timeslice[i]);
				}
				sql11=sql11.substring(0, sql11.length()-1);
				sql11+=") ";
			}
			String sql12=" and s.authorder = ? ";
			String cycid=JSONUtils.getString(mmppJson, "cycid");
			param.add(cycid);
			String productid=JSONUtils.getString(mmppJson, "productid");
			
			
					
			if(StringUtils.isNotBlank(productid)){
				sql12+= "and  p.ID = ?";
				
				param.add(productid);
			}
			String sql13="";
			if(!StringUtils.isEmpty(routeid)){
				sql13+="and v.routeid = ? ";
				param.add(routeid);
			}
			String sql14="";
			if(!StringUtils.isEmpty(busids)){
				sql14+="and v.id =? ";
				param.add(busids);
			}
			String sql15 = sql10+sql11+sql12+sql13+sql14;
			if(StringUtils.isNotEmpty(tempsql)){
				sql15=sql15.substring(3, sql15.length());
				tempsql+=" where "+ sql15;
			}

		}
		if(StringUtils.isNotEmpty(sql)&&StringUtils.isNotEmpty(tempsql)){
			sql=sql+" union all "+tempsql;
		}
		if(StringUtils.isEmpty(sql)&&StringUtils.isNotEmpty(tempsql)){
			sql=tempsql;
		}
		
		sql+=" order by id";	
//		SELECT s.id,s.datetime,s.interval,s.productid,s.authorder,s.apid,s.state FROM SCHEDULE
		String[] str=new String[]{"id","datetime","interval","productid","authorder","apid","state"};
		return getNoPageObject(sql,param, str,"不分页查询排期");
	}

	@Override
	public int setPutState(List<String> ids,String state) {
		// TODO Auto-generated method stub
		
		String sql="update SCHEDULE set state=? where id=?";
		List params = new ArrayList();
		for(String str:ids){
			Object[] obj = new Object[2];
			obj[0]=state;
			obj[1]=str;
			params.add(obj);
		}
		int[] result=jdbcTemplate.batchUpdate(sql, params);
		return result.length;
		
//		if(ids==null||ids.size()==0){
//			return 0;			
//		}
//		String sql = "update SCHEDULE set state=? where id in (";
//		for(int i=0;i<ids.size();i++){
//			sql+="?,";
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=")";
////		ids.set(0, state);
//		List arrayList = new ArrayList(ids);
//		arrayList.add(0, state);
//		return executeUpdateSQL(sql, arrayList, "设置状态");
	}
	@Override
	public int deletePutState(List<String> ids) {
		// TODO Auto-generated method stub
//		if(ids==null||ids.size()==0){
//			return 0;			
//		}
//		String sql = "delete SCHEDULE where id in (";
//		for(int i=0;i<ids.size();i++){
//			sql+="?,";
//		}
//		sql=sql.substring(0, sql.length()-1);
//		sql+=")";
//		return executeUpdateSQL(sql, arrayList, "取消投放");
		List arrayList = new ArrayList(ids);
		String sql="delete from SCHEDULE where id=?";
		List params = new ArrayList();
		for(String str:ids){
			Object[] obj = new Object[1];
			obj[0]=str;
			params.add(obj);
		}
		int[] result=jdbcTemplate.batchUpdate(sql, params);
		return result.length;
	}

	@Deprecated
	@Override
	public List queryPutLog4Mobile(String[] date, String[] timeslice, String[] authcycid, String[] busids,
			String userid) {
		List param = new ArrayList();
		String sql="SELECT s.id,v.code as busname,vd.apmac,a.name as authcycname,s.datetime as datetime,t.name as timeslice,p.proname as proname,p.id as proid,s.state FROM SCHEDULE s "+
		          "LEFT JOIN  TIMESETTING t ON (t.id=s.interval) "+
		           "LEFT JOIN  PUTPRODUCT p ON(p.id=s.productid) "+
		           "LEFT JOIN AUTHCYC a ON(a.cid=s.authorder) "+
		           "LEFT JOIN DEVICE VD ON(VD.id=s.apid) "+
		           "LEFT JOIN vehicle v ON (v.id=VD.VEHICLEID) "; 
		String sql0="";
		if(date!=null&&date.length>0){
			sql0+="and s.datetime in(";
			for(int i=0;i<date.length;i++){
				sql0+="?,";
				param.add(date[i].toString());
			}
			sql0=sql0.substring(0, sql0.length()-1);
			sql0+=")";
		}
		String sql1="";
		if(timeslice!=null&&timeslice.length>0){
			sql1+="and s.interval in(";
			for(int i=0;i<timeslice.length;i++){
				sql1+="?,";
				param.add(timeslice[i]);
			}
			sql1=sql1.substring(0, sql1.length()-1);
			sql1+=") ";
		}
		
		String sql2="";
		if(authcycid!=null&&authcycid.length>0){
			sql2+="and a.cid in(";
			for(int i=0;i<authcycid.length;i++){
				sql2+="?,";
				param.add(authcycid[i]);
			}
			sql2=sql2.substring(0, sql2.length()-1);
			sql2+=") ";
		}
		
		String sql3="";
		if(busids!=null&&busids.length>0){
			sql3+="and v.code in(";
			for(int i=0;i<busids.length;i++){
				sql3+="?,";
				param.add(busids[i]);
			}
			sql3=sql3.substring(0, sql3.length()-1);
			sql3+=") ";
		}
		String sql4="";
		String sql5 = sql0+sql1+sql2+sql3+sql4;
		
		if(!StringUtils.isEmpty(sql5)){
			sql5=sql5.substring(3, sql5.length());
			sql+=" where "+ sql5;
		}
		System.err.println("==>"+sql);
		String[] jsonStr=new String[]{"id","busname","apmac","authcycname","datetime","timeslice","proname","proid","state"};
		return getNoPageObject(sql, param, jsonStr, "不分页查询排期");
	}

	@Override
	public List<JSONObject> queryPut(List<String> ids) {
		// TODO Auto-generated method stub
		String sql="select datetime,interval,productid,authorder,state,apid from SCHEDULE where id in(";
		for(int i=0;i<ids.size();i++){
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		String[] str = new String[]{"datetime","interval","productid","authorder","state","apid"};
		return getNoPageObject(sql, ids, str);
	}





}
