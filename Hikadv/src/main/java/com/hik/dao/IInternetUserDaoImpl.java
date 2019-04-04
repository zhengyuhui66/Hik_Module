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
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;

@Repository
@Transactional
public class IInternetUserDaoImpl extends BaseHIKDao implements IInternetUserDao{

	@Override
	public Page queryInternetUser(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","mac","phone","authtime","equipmentid","apmac","busid","code","routeid","routename","creator","modifier","createtime","modifytime","mark"};
		String sql="SELECT W.ID, "+
				       "W.MAC, "+
				       "W.PHONE, "+
				       "W.AUTHTIME, "+
				       "D.EQUIPMENTID, "+
				       "D.APMAC, "+
				       "V.ID as busid, "+
				       "V.CODE, "+
				       "R.ID as routeid, "+
				       "R.NAME as routename, "+
				       "W.CREATOR, "+
				       "W.MODIFIER, "+
				       "W.CREATETIME, "+
				       "W.MODIFYTIME, "+
				       "W.MARK "+
				  "FROM WIFIAUTH W "+
				  "LEFT JOIN DEVICE D "+
				    "ON (D.ID = W.DEVICEID) "+
				  "LEFT JOIN VEHICLE V "+
				    "ON (V.ID = D.VEHICLEID) "+
				  "LEFT JOIN ROUTE R "+
				    "ON (R.ID = V.ROUTEID)";
	  
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+=" where W.MAC like ? or "+
		      " W.PHONE like ? or "+
		      " W.AUTHTIME like ? or "+
		      " D.EQUIPMENTID like ? or "+
		      " D.APMAC like ? or "+
		      " V.CODE like ? or "+
		      " R.NAME like ? or "+
		      " W.CREATOR like ? or "+
		      " W.MODIFIER like ? or "+
		      " W.CREATETIME like ? or "+
		      " W.MODIFYTIME like ?";
			for(int i=0;i<11;i++){
				param.add(temparam);
			}
		}
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询用户上网信息");			
		}
		return page;
	}
}
