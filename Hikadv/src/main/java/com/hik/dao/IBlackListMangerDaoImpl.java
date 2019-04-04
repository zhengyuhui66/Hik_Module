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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.BLACKLIST;
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
@Transactional()
public class IBlackListMangerDaoImpl extends BaseHIKDao implements IBlackListMangerDao{

	@Override
	public Page queryBlackListManger(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String[] jsonStr=new String[]{"id","phone","creator","modifier","createtime","modifytime","mark"};
		String sql="select b.id,b.phone,b.creator,b.modifier,b.createtime,b.modifytime,b.mark from blacklist b ";
		if(StringUtils.isNotEmpty(searchStr)){
			String temparam = "%"+searchStr+"%";
			sql+="where b.phone like ? or "+
		       "b.creator like ? or "+
		       "b.createtime like ? or "+
		       "b.modifytime like ? or "+
		       "b.modifier like ? or "+
		       "b.mark like ? ";
			for(int i=0;i<6;i++){
				param.add(temparam);
			}
		}
		Page page=null;
		if(start==-1&&limit==-1){
			List result=getNoPageObject(sql, param, jsonStr);
			page = new PageObject(start, limit, result.size(), result);
		}else{
			page = getPageObject(sql, start, limit, param,jsonStr, "分页查询黑名单");			
		}
		return page;
	}
	
	@Override
	public String deleteBlackListManger(String[] ids) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		String sql="delete from blacklist where id in(";
		for(int i=0;i<ids.length;i++){
			param.add(ids[i]);
			sql+="?,";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=")";
		int str =executeUpdateSQL(sql, param);
		return str+"";
	}

	@Override
	public int addBlackListManger(BLACKLIST blacklist) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(blacklist.getPhone());
		param.add(blacklist.getCreator());
		param.add(blacklist.getCreatetime());
		param.add(blacklist.getMark());
		String sql="insert into blacklist(id,phone,creator,createtime,mark) values(blacklist_seq.nextval,?,?,?,?)";
		return executeUpdateSQL(sql, param,"添加黑名单信息");
	}

	@Override
	public int updateBlackListManger(BLACKLIST device) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(device.getPhone());
		param.add(device.getCreator());
		param.add(device.getCreatetime());
		param.add(device.getMark());
		param.add(device.getId());
		String sql="update blacklist set phone=?,modifier=?,modifytime=?,mark=? where id=?";
		return executeUpdateSQL(sql, param,"更改黑名单信息");
	}

}
