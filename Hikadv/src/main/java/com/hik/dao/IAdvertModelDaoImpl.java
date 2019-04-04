package com.hik.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.hik.app.entity.ADVERTMODELMANGER;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.util.PROCEDURCES;
import net.sf.json.JSONObject;

@Repository
@Transactional
public class IAdvertModelDaoImpl extends BaseHIKDao implements IAdvertModelDao{

	public Page queryadvertModel(int start,int limit,String searchStr){
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] str = new String[]{"id",/*"modelid","modelmodeid",*/"url","name",/*"modename",*/"filename","username","telphone","createtime","updatetime","modelskin","modelskinid","cycid","cycname","advposid1","advposid2","advposid3","advposid4","advposid5","advposname1","advposname2","advposname3","advposname4","advposname5","desc"};
		String sql="select t.id,"
//				+ "t.modelid,"
//				+ "t.modelmodeid,"
				+ "t.url,"
				+ "t.name,"
//				+ "t.modename,"
				+ "T.filename,"
				+ "s.user_name,"
				+ "s.telphone,"
				+ "t.createtime,"
				+ "t.updatetime,"
				+ "k.name as modelskin,"
				+ "k.id as modelskinid, "
				+ "t.cycid, "
				+ "a.name as cycname, "
				+ "t.advposid1, "
				+ "t.advposid2, "
				+ "t.advposid3, "
				+ "t.advposid4, "
				+ "t.advposid5, "
				+ "t.advposname1, "
				+ "t.advposname2, "
				+ "t.advposname3, "
				+ "t.advposname4, "
				+ "t.advposname5, "
				+ "t.description "
				+ "from ADVERTMODELMANGER t "
				+ "LEFT JOIN sys_user s ON "
				+ "(t.userid=s.userid) " 
				+ "LEFT JOIN SKIN_MANGER k ON "
				+ "(t.modelskin=k.id) "
				+ "LEFT JOIN AUTHCYC a ON "
				+ "(t.cycid=a.cid) ";
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+=" where "
				+ "t.url like ? or "
				+ "t.name like ? or "
				+ "T.filename like ? or "
				+ "s.user_name like ? or "
				+ "s.telphone like ? or "
				+ "t.createtime like ? or "
				+ "t.updatetime like ? or "
				+ "k.name  like ? or "
				+ "a.name  like ? or "
				+ "t.advposname1 like ? or  "
				+ "t.advposname2 like ? or  "
				+ "t.advposname3 like ? or  "
				+ "t.advposname4 like ? or  "
				+ "t.advposname5 like ? or  "
				+ "t.description  like ?";
					
//				+"t.id like ? or "
//				+ "t.url like ? or "
//				+ "t.name like ? or "
//				+ "k.name like ? or "
//				+ "a.name like ? or "
//				+ "s.user_name like ? or "
//				+ "s.telphone like ?";
			for(int i=0;i<15;i++){
				param.add(temparam);				
			}
		}
		return getPageObject(sql, start, limit,param, str, "分页查询");
	}

//	public int saveadvertModel(String modelid,String modelmodeid, String url, String name, String modelname, String userid,
//			String description,String fileName,String modelskin) {
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(modelid);
//		param.add(modelmodeid);
//		param.add(url);
//		param.add(name);
//		param.add(modelname);
//		param.add(userid);
//		param.add(description);
//		param.add(fileName);
//		param.add(modelskin);
//		String sql="INSERT INTO ADVERTMODELMANGER(ID,modelid,Modelmodeid,URL,NAME,MODENAME,USERID,CREATETIME,DESCRIPTION,FILENAME,modelskin) "
//				+ "VALUES (ADVERTMODELMANGER_SEQ.NEXTVAL,?,?,?,?,?,?,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),?,?,?)";
//		return executeUpdateSQL(sql, param, "保存最新的模块");
//	}
//	public String getModelId(){
//		String sql="select ADVERTMODELMANGER_MODELID.nextval from dual";
//		List result = getNoPageObject(sql);
//		return result.get(0).toString(); 
//	}

	public int updateadvertModel(ADVERTMODELMANGER ad) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
//		double cycid=3;
		param.add(ad.getNAME());
//		param.add(cycid);
		param.add(ad.getCYCID());
		param.add(ad.getADVPOSID1());
		param.add(ad.getADVPOSID2());
		param.add(ad.getADVPOSID3());
		param.add(ad.getADVPOSID4());
		param.add(ad.getADVPOSID5());
		param.add(ad.getADVPOSNAME1());
		param.add(ad.getADVPOSNAME2());
		param.add(ad.getADVPOSNAME3());
		param.add(ad.getADVPOSNAME4());
		param.add(ad.getADVPOSNAME5());
		param.add(ad.getMODELSKIN());
		param.add(ad.getDESCRIPTION());
		param.add(ad.getID());
		
//		param.add(msubMname);
//		param.add(id);
//		String sql="update"
		String sql="update ADVERTMODELMANGER "
				+ "set NAME = ?,"
				+ "cycid=?,"
				+ "UPDATETIME=to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'), "
				+ "ADVPOSID1=?,"
				+ "ADVPOSID2=?,"
				+ "ADVPOSID3=?,"
				+ "ADVPOSID4=?,"
				+ "ADVPOSID5=?,"
				+ "ADVPOSNAME1=?,"
				+ "ADVPOSNAME2=?,"
				+ "ADVPOSNAME3=?,"
				+ "ADVPOSNAME4=?,"
				+ "ADVPOSNAME5=?,"
				+ "modelskin=?,"
				+ "DESCRIPTION=?  where id = ?";
		return executeUpdateSQL(sql, param,"");
	}
//	@Override
//	public int updateModel(String modelskin, String name, String modelid){
//		// TODO Auto-generated method stub
//		List param = new ArrayList();
//		param.add(name);
//		param.add(modelskin);
//		param.add(modelid);
//		String sql="update ADVERTMODELMANGER set name =?,modelskin = ? where id = ?";
//		return executeUpdateSQL(sql, param);
//	}
	public int deleteadvertModel(List id) {
		// TODO Auto-generated method stub
		String sql="DELETE FROM ADVERTMODELMANGER WHERE ID in (";
		for(int i=0;i<id.size();i++){
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		return executeUpdateSQL(sql, id);
	}

	@Override
	public int saveadvertModel(Map modemodel, String url, String name, String userid, String description,
			String fileName, String modelskin,String cycid) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(url);
		param.add(name);
		param.add(userid);
		param.add(description);
		param.add(fileName);
		param.add(modelskin);
		param.add(cycid);
		String sql="INSERT INTO ADVERTMODELMANGER(ID,URL,NAME,USERID,CREATETIME,DESCRIPTION,FILENAME,modelskin,"
				+ "CYCID,ADVPOSID1,ADVPOSID2,ADVPOSID3,ADVPOSID4,ADVPOSID5,"
				+ "ADVPOSNAME1,ADVPOSNAME2,ADVPOSNAME3,ADVPOSNAME4,ADVPOSNAME5) "
				+ "values(ADVERTMODELMANGER_SEQ.NEXTVAL,?,?,?,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),?,?,?,?";
		Set<String> keys =modemodel.keySet();
		for(String key :keys){
			param.add(key);
			sql+=",?";
		}
		if(modemodel.size()<5){
			for(int i=0;i<5-modemodel.size();i++){
				sql+=",null";
			}
		}
		
		for(String key :keys){
			param.add(modemodel.get(key));
			sql+=",?";
		}
		if(modemodel.size()<5){
			for(int i=0;i<5-modemodel.size();i++){
				sql+=",null";
			}
		}
		sql+=")";
//		String sql="INSERT INTO ADVERTMODELMANGER(ID,modelid,Modelmodeid,URL,NAME,MODENAME,USERID,CREATETIME,DESCRIPTION,FILENAME,modelskin) "
//		+ "VALUES (ADVERTMODELMANGER_SEQ.NEXTVAL,?,?,?,?,?,?,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),?,?,?)";
		return executeUpdateSQL(sql, param,"保存新增的模版");
	}

	@Override
	public List queryIfInproduct(List id) {
		// TODO Auto-generated method stub
		
		String sql="SELECT T.ID, T.NAME, COUNT(*) "+
				  "FROM ADVERTMODELMANGER T "+
				  "JOIN PUTPRODUCT P "+
				    "ON (T.ID = P.MODELID) "+
				 "WHERE T.ID IN (";
				for(int i=0;i<id.size();i++){
					sql+="?,";
				}
				sql=sql.substring(0, sql.length()-1);
				sql+=") ";
				sql+="GROUP BY T.ID, T.NAME";
		String[] str = new String[]{"id","name","count"};
		return getNoPageObject(sql, id, str);
	}

	@Override
	public List queryModelModeById(String modelid) {
		// TODO Auto-generated method stub
		List<String> param = new ArrayList<String>();
		param.add(modelid);
		param.add(modelid);
		param.add(modelid);
		param.add(modelid);
		param.add(modelid);
		String[] str = new String[]{"advposid","advposname","advertid"};
		logger.info("查询到的modelid======>"+modelid);
//		String sql="select modelmodeid,modename,'请选择...' as advertid from ADVERTMODELMANGER t WHERE id=?";
		String sql="SELECT advposid1,advposname1,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid1 IS NOT NULL "+
					"Union ALL "+
					"SELECT advposid2,advposname2,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid2 IS NOT NULL "+
					"Union ALL "+
					"SELECT advposid3,advposname3,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid3 IS NOT NULL "+
					"Union ALL "+
					"SELECT advposid4,advposname4,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid4 IS NOT NULL "+
					"Union ALL "+
					"SELECT advposid5,advposname5,'请选择...' as advertid from ADVERTMODELMANGER t  WHERE ID=? AND advposid5 IS NOT NULL";
		return getNoPageObject(sql, param, str, "查询子模块");
	}
}
