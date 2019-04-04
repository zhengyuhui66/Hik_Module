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

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONObject;

@Repository
public class GetAdvertDaoImpl extends BaseHIKDao implements IGetAdvertDao{

	public List<JSONObject> getAdvert(String busId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(busId);
		String[] str=new String[]{"modelid","modelmodeid","modelurl","modelname","advertid","advertname","adverturl","clicktotal","matername","materpath","clicknumber","advertproperty"};
		String sql="SELECT "+
				   "B.MODEL_ID, "+//模型ID
			       "B.MODEL_MODE_ID, "+ //子模块ID
			       "A.URL AS MODELURL, "+ //获取模型的地址
			       "A.NAME AS MODELNAME, "+ //模型名称
			       "B.ADVERT_ID, "+ //广告ID
			       "AD.NAME AS ADVERTNAME, "+//广告名称
			       "AD.ADVERT_URL, "+//用户点击广告时触发的链接地址
			       "AD.CLICK_TOTAL, "+//用户点击广告地次数
			       "M.MATER_NAME, "+//物料名称
			       "M.MATER_PATH, "+//获取物料的地址
			       "M.CLICK_NUMBER, "+//物料点击次数
			       "M.advertproperty "+
			  "FROM BUSADVERTMANGER B "+
			  "LEFT JOIN ADVERTMODELMANGER A "+
			    "ON (B.MODEL_ID = A.MODELID AND B.MODEL_MODE_ID = A.MODELMODEID) "+
			  "LEFT JOIN ADVERTMANGER AD "+
			    "ON (AD.ID = B.ADVERT_ID) "+
			  "LEFT JOIN MATERIEL_INFO M "+
			    "ON (AD.MATER_ID = M.MATER_ID) "+
			 "WHERE B.BUS_ID = ? "+
			   "AND B.STATE = 1 "+
			   "AND B.STIME < TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
			   "AND B.ETIME > TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')";

		return getNoPageObject(sql, param, str, "查询广告信息，形成发布的广告");
	}
	public List<JSONObject> getLoginedAdvert(String busId) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(busId);
		String[] str=new String[]{"modelid","modelmodeid","modelurl","modelname","advertid","advertname","adverturl","clicktotal","matername","materpath","clicknumber"};
		String sql="SELECT "+
				   "B.MODEL_ID, "+//模型ID
			       "B.MODEL_MODE_ID, "+ //子模块ID
			       "A.URL AS MODELURL, "+ //获取模型的地址
			       "A.NAME AS MODELNAME, "+ //模型名称
			       "B.ADVERT_ID, "+ //广告ID
			       "AD.NAME AS ADVERTNAME, "+ //广告名称
			       "AD.ADVERT_URL, "+//用户点击广告时触发的链接地址
			       "AD.CLICK_TOTAL, "+//用户点击广告地次数
			       "M.MATER_NAME, "+//物料名称
			       "M.MATER_PATH, "+//获取物料的地址
			       "M.CLICK_NUMBER "+//物料点击次数
			  "FROM LOGINED_BUSADVERTMANGER B "+
			  "LEFT JOIN ADVERTMODELMANGER A "+
			    "ON (B.MODEL_ID = A.MODELID AND B.MODEL_MODE_ID = A.MODELMODEID) "+
			  "LEFT JOIN ADVERTMANGER AD "+
			    "ON (AD.ID = B.ADVERT_ID) "+
			  "LEFT JOIN MATERIEL_INFO M "+
			    "ON (AD.MATER_ID = M.MATER_ID) "+
			 "WHERE B.BUS_ID = ? "+
			   "AND B.STATE = 1 "+
			   "AND B.STIME < TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') "+
			   "AND B.ETIME > TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')";

		return getNoPageObject(sql, param, str, "查询广告信息，形成发布的广告");
	}
	public Page getAdvert(int start, int limit,String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] str = new String[]{"advertid","userid","username","matername","matertype","materpath",/*"clickenumber",*/"materid","adverturl",/*"clicktotal",*/"createtime","name","desc","advertpropertyid","advertproperty","pid"};
		String sql="SELECT T.ID,"+
			       "T.USER_ID,"+
			       "SU.USER_NAME,"+
			       "MI.MATER_NAME,"+
			       "MI.MATER_TYPE,"+
			       "MI.MATER_PATH,"+
//			       "MI.CLICK_NUMBER,"+
			       "T.MATER_ID,"+
			       "T.ADVERT_URL,"+
//			       "T.CLICK_TOTAL,"+
			       "T.CREATE_TIME,"+
			       "t.NAME,"+ 
			       "t.description,"+ 
			       "t.advertproperty as advertpropertyid,"+
			       "a.name as advertproperty,"+
			       "a.pid "+
			  "FROM ADVERTMANGER T "+
			  "left join ADVPROPERTY a on(a.id=t.advertproperty) "+
			  "LEFT JOIN SYS_USER SU "+
			    "ON (T.USER_ID = SU.USERID) "+
			  "LEFT JOIN MATERIEL_INFO MI "+
			    "ON (T.MATER_ID = MI.MATER_ID) ";
		if(StringUtils.isNotBlank(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+= " where "+
		       "T.USER_ID like ? or "+
		       "SU.USER_NAME like ? or "+
		       "MI.MATER_NAME like ? or "+
		       "MI.MATER_TYPE like ? or "+
		       "MI.MATER_PATH like ? or "+
		       "a.NAME like ? or "+
		       "T.ADVERT_URL like ? or "+
		       "T.CREATE_TIME ? or "+
		       "t.NAME like ? or " +
		       "t.description like ?";
			for(int i=0;i<10;i++){
				param.add(temparam);				
			}
		}
		sql+=" order by t.id";
		return getPageObject(sql, start, limit, param,str, "分页查询所有的广告信息");
	}

	public List getMater(String materTypeid,String materSizeid,String materCreatorid) {
		// TODO Auto-generated method stub
		String[] str = new String[]{"materids","matername","materpath"};
		List param = new ArrayList();
		String sql="select t.mater_id,t.mater_name,t.mater_path from MATERIEL_INFO t where t.mater_state='审核已全部通过'";
		if(!StringUtils.isEmpty(materTypeid)){
			param.add(materTypeid);
			sql+=" and MATER_TYPE=?";
		}
		if(!StringUtils.isEmpty(materSizeid)){
			String[] materSizeids=materSizeid.split("\\*");
			param.add(materSizeids[0]);
			param.add(materSizeids[1]);
			sql+=" and MATER_WIDTH=? AND MATER_HEIGHT=?";
		}
		if(!StringUtils.isEmpty(materCreatorid)){
			param.add(materCreatorid);
			sql+=" and CUSTOMER_ID=?";
		}
		return getNoPageObject(sql, param,str, "查询所有的物料");
	}

	public int saveAdvert(String id,String materId, String adverturl, String description, String advertname, String userid,String advertpropertys,String advertproperty) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(id);
		param.add(userid);
		param.add(materId);
		param.add(adverturl);
		param.add(advertname);
		param.add(description);
		param.add(advertpropertys);
		param.add(advertproperty);
		
		String sql="insert into ADVERTMANGER(id,create_time,user_id,mater_id,advert_url,name,description,advertpropertys,advertproperty)"
				+ " values(?,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),?,?,?,?,?,?,?)";
		int result = executeUpdateSQL(sql, param, "新增新的广告数据");
		return result;
	}
	
	
	public int saveAdvPutset(String id,String conditionid){
		List param = new ArrayList();
		param.add(id);
		param.add(conditionid);
		String sql="insert into ADV_PRICONDITION(id,advid,PRICONDITIONID) values(ADV_PRICONDITION_SEQ.NEXTVAL,?,?)";
		return executeUpdateSQL(sql,param);
	}
	public List AdvertId(){
		String sql="select ADVERTMANGNER_SEQ.nextval from dual";
		return getNoPageObject(sql);
	}
	public int updateAdvert(String adverid, String materId, String adverturl, String description, String advertname,String advertpropertys,String advertproperty) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(materId);
		param.add(adverturl);
		param.add(advertname);
		param.add(description);
		param.add(advertpropertys);
		
		param.add(advertproperty);
		param.add(adverid);
		String sql="update ADVERTMANGER set MATER_ID =?,ADVERT_URL = ?, NAME = ?, DESCRIPTION=?,update_time=to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),advertpropertys=?,advertproperty=?  where id = ?";
		int result = executeUpdateSQL(sql, param, "更新的广告数据");
		return result;
	}

	public int deleteAdvert(String[] advertids) {
		// TODO Auto-generated method stub
		if(advertids==null||advertids.length==0){
			return 0;
		}else{
			List param = new ArrayList();
			 String sql="DELETE FROM ADVERTMANGER WHERE ID IN (";
			 for(int i=0;i<advertids.length;i++){
				 sql+="?,";
				 param.add(advertids[i]);				 
			 }
			 sql=sql.substring(0,sql.length()-1);
			 sql+=")";
			 return executeUpdateSQL(sql, param,"删除记录");
		}
	}
	public List<JSONObject> getModelUrl(String modelid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(modelid);
		String[] str = new String[]{"modelid","url","modelskin"};
		String sql="select DISTINCT t.id,t.URL,k.skinname from ADVERTMODELMANGER t left join SKIN_MANGER k on(t.modelskin=k.id) WHERE t.id=?";
		return getNoPageObject(sql, param, str, "根据模版ID查询模版URL");
	}
	public List<JSONObject> getAdvertUrl(String name) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(name);
		String[] str=new String[]{"advert_url","mater_path","name"};
		String sql="select t.advert_url,m.mater_path,t.name from ADVERTMANGER t LEFT JOIN materiel_info m on(t.mater_id=m.mater_id) WHERE t.name=?";
		return getNoPageObject(sql, param, str, "根据广告名称查询其它广告信息");
	}
	public List getrepeatAdvertName(String name) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(name);
		String sql="select * from ADVERTMANGER t WHERE NAME=?";
		return getNoPageObject(sql, param);
	}

	@Override
	public int deleteAdvputSet(String advertid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(advertid);
		String sql="delete from ADV_PRICONDITION where advid=?";
		return executeUpdateSQL(sql, param);
	}
	@Override
	public List getConditionByAdvid(String id) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(Integer.parseInt(id));
		String sql="select PRICONDITIONID from ADV_PRICONDITION where advid=?";
		return getNoPageObject(sql, param);
	}
	@Override
	public List getadvingroup(String[] id) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="select a.name AS ad from ADVGROUP t   LEFT JOIN ADVERTMANGER a ON (t.advid1=a.id) WHERE "+
					"t.advid1 IN( ";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid2=a.id)  WHERE "+
					"t.advid2 IN(";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid3=a.id)  WHERE "+
					"t.advid3 IN(";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid4=a.id)  WHERE  "+
					"t.advid4 IN(";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid5=a.id)  WHERE "+ 
					"t.advid5 IN(";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid6=a.id)  WHERE  "+
					"t.advid6 IN( ";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid7=a.id)  WHERE  "+
					"t.advid7 IN( ";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid8=a.id)  WHERE  "+
					"t.advid8 IN(";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					sql+=" UNION "+
					"select a.name AS ad from ADVGROUP t  LEFT JOIN ADVERTMANGER a ON (t.advid9=a.id)  WHERE  "+
					"t.advid9 IN(";
					for(int i=0;i<id.length;i++){
						sql+="?,";
						param.add(id[i]);
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")";
					
		return getNoPageObject(sql, param,"查询是否在广告集中存在");
	}
	@Override
	public List getAdvBymaterId(String[] materid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="select COUNT(*) from ADVERTMANGER t WHERE mater_id IN(";
		for(int i=0;i<materid.length;i++){
			sql+="?,";
			param.add(materid[i]);
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		String[] str = new String[]{"count"};
		return getNoPageObject(sql, param, str);
	}
}
