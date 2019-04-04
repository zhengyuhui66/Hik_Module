package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.WechatManger;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IWechatMangerDaoImpl extends BaseHIKDao implements IWechatMangerDao{
	@Autowired
	@Qualifier(value="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Override
	public Page queryForPage(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
//		String[] jsonStr=new String[]{"id","timeid","timename","addredssid","addressname","eventid","eventname",/*"phonetype","ptypename","phonesystem","psysname",*/"phone","pname",/*"clicktype","viewtype",*/"creator","creatime","modifier","modifytime","descr","name"};
		String[] jsonStr = new String[]{"id","name","wechatid","type","introduce",
				   "identify","phone","address","maininfo",
				   "owner","email","orgid","appid",
				   "appsecret","accesstoken","creator","creatime","modifyer","modifytime","user_adv","advertpropertys","advertproperty"};
		String sql="select ID,NAME,wechatid,TYPE,introduce,identify,phone,address,maininfo,owner,email,orgid,appid,appsecret,accesstoken,creator,creatime,modifyer,modifytime,user_adv,propertys,property from MMPP t";
			    
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+=" where "+
			"NAME like ? or " +
			"wechatid like ? or " +
			"TYPE like ? or " +
			"introduce  like ? or " +
			"identify like ? or " +
			"phone  like ? or "
			+ "address  like ? or "
			+ "maininfo  like ? or "
			+ "owner  like ? or "
			+ "email  like ? or "
			+ "orgid  like ? or "
			+ "appid  like ? or "
			+ "appsecret  like ? or "
			+ "accesstoken  like ?";
			for(int i=0;i<14;i++){
				param.add(temparam);
			}
		}
		Page page = getPageObject(sql, start, limit, param,jsonStr, "分页查询物料信息");
		return page;
	}

	@Override
	public int addWechat(WechatManger wechatManger) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(wechatManger.getId());
		param.add(wechatManger.getName());
		param.add(wechatManger.getWechatid());
		param.add(wechatManger.getType());
		param.add(wechatManger.getIntroduce());
		param.add(wechatManger.getIdentify());
		param.add(wechatManger.getPhone());
		param.add(wechatManger.getAddress());
		param.add(wechatManger.getMaininfo());
		param.add(wechatManger.getOwner());
		param.add(wechatManger.getEmail());
		param.add(wechatManger.getOrgid());
		param.add(wechatManger.getAppid());
		param.add(wechatManger.getAppsecret());
		param.add(wechatManger.getAccesstoken());
		param.add(wechatManger.getCreator());
		param.add(wechatManger.getUsreid());
		param.add(wechatManger.getPropertys());
		param.add(wechatManger.getProperty());
		
//		user_adv,propertys,property
//		ID,NAME,wechatid,TYPE,introduce,identify,phone,address,maininfo,owner,email,orgid,appid,appsecret,accesstoken,creator,creatime,modifyer,modifytime 
		String sql="insert into MMPP(ID,NAME,wechatid,TYPE,introduce,identify,phone,address,maininfo,owner,email,orgid,appid,appsecret,accesstoken,creator,creatime,user_adv,propertys,property) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		return executeUpdateSQL(sql, param,"新增公众号数据");
	}

	@Override
	public int updateWechat(WechatManger wechatManger) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(wechatManger.getName());
		param.add(wechatManger.getWechatid());
		param.add(wechatManger.getType());
		param.add(wechatManger.getIntroduce());
		param.add(wechatManger.getIdentify());
		param.add(wechatManger.getPhone());
		param.add(wechatManger.getAddress());
		param.add(wechatManger.getMaininfo());
		param.add(wechatManger.getOwner());
		param.add(wechatManger.getEmail());
		param.add(wechatManger.getOrgid());
		param.add(wechatManger.getAppid());
		param.add(wechatManger.getAppsecret());
		param.add(wechatManger.getAccesstoken());
		param.add(wechatManger.getModifyer());
		param.add(wechatManger.getUsreid());
		param.add(wechatManger.getPropertys());
		param.add(wechatManger.getProperty());
		param.add(wechatManger.getId());
//		param.add(pricondition.getTimeid());
////		param.add(pricondition.getEtime());
//		param.add(pricondition.getAddredssid());
//		param.add(pricondition.getEventid());
////		param.add(pricondition.getPhonetype());
////		param.add(pricondition.getPhonesystem());
//		param.add(pricondition.getPhone());
////		param.add(pricondition.getClicktype());			
////		param.add(pricondition.getViewtype());	
//		param.add(pricondition.getModifier());
//		param.add(pricondition.getDescr());
//		param.add(pricondition.getName());
//		param.add(pricondition.getId());
//		param.add("11061");
//		String sql="update PRICONDITION SET "+
//	       "TIMEID=?,"+
////	       "ETIME=?,"+
//	       "ADDREDSSID=?,"+
//	       "EVENTID=?,"+
////	       "PHONETYPE=?,"+
////	       "PHONESYSTEM=?,"+
//	       "PHONE=?,"+
////	       "CLICKTYPE=?,"+
////	       "VIEWTYPE=?,"+
//	       "MODIFIER=?,"+
//	       "MODIFYTIME=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss'),"+
//	       "DESCR=?,"+
//	       "NAME=? where id=?";
		
		String sql="update MMPP set "+
								"NAME=?,"+
								"wechatid=?,"+
								"TYPE=?,"+
								"introduce=?,"+
								"identify=?,"+
								"phone=?,"+
								"address=?,"+
								"maininfo=?,"+
								"owner=?,"+
								"email=?,"+
								"orgid=?,"+
								"appid=?,"+
								"appsecret=?,"+
								"accesstoken=?,"+
								"modifyer=?,"+
								"user_adv=?,"+
								"propertys=?,"+
								"property=?,"+
								"modifytime=TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') where id=?";
//				ID,NAME,wechatid,TYPE,introduce,identify,phone,address,maininfo,owner,email,orgid,appid,appsecret,accesstoken,creator,creatime,modifyer,modifytime 

		return executeUpdateSQL(sql, param,"更新公众号数据");
	}

	@Override
	public int deleteWechat(String[] id) {
		// TODO Auto-generated method stub
		
		if(id.length>0){
			List param = new ArrayList();
			String sql="delete from MMPP where id in(";
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
	public List queryMMPP() {
		// TODO Auto-generated method stub
		String sql="select appid,appsecret from MMPP";
		String[] str = new String[]{"appid","appsecret"};
		return getNoPageObject(sql, str);
	}

	@Override
	public int updateAccessToken(List<Object[]> obj) {
		// TODO Auto-generated method stub
		String sql="update MMPP set accesstoken=? where appid=? and appsecret=?";
			int[] result=jdbcTemplate.batchUpdate(sql,obj);
			return result.length;
	}

	@Override
	public List getId() {
		// TODO Auto-generated method stub
		String sql="select MMPP_seq.nextval from dual";
		return getNoPageObject(sql);
	}

	@Override
	public List getIfWeChatInMMPP(String[] ids){
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="WITH temp AS(";
		for(int i=1;i<=9;i++){
			sql+="SELECT  M.ID, M.NAME "+
				  "FROM MMPP_PRODUCT T "+
				  "LEFT JOIN MMPP M "+
				    "ON (T.MMPPID"+i+" = M.ID) "+
				 "WHERE T.MMPPID"+i+" IN (";
			for(int j=0;j<ids.length;j++){
				sql+="?,";
				param.add(ids[j]);
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
			if(i!=9){
				sql+=" union all ";
			}
		}
		sql+=")  SELECT COUNT(*),temp.id,temp.name FROM temp GROUP BY temp.id,temp.name";
		String[] str = new String[]{"count","id","name"};
		return getNoPageObject(sql, param, str,"查询是否有公众号在公众号产品中");
	}
	
}
