package com.hik.framework.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.app.entity.TIMESETTING;
import com.hik.dao.BaseService;
import com.hik.dao.SYS_RIGHT_jpa;
import com.hik.dao.SYS_USER_jpa;
import com.hik.framework.dao.ILoginDao;
import com.hik.framework.dao.IMenuDao;
import com.hik.framework.dao.ITimeSetDao;
import com.hik.framework.dao.IUserDao;
import com.hik.framework.dao.LoginDaoImpl;
import com.hik.framework.utils.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false,isolation=Isolation.DEFAULT)
public class ITimeSetServiceImpl extends BaseService implements ITimeSetService{
    private final Log log = LogFactory.getLog(ITimeSetServiceImpl.class);
    
    @Autowired
    private ITimeSetDao iTimeSetdao;
	@Override
	public List getTimeSet() {
		// TODO Auto-generated method stub
		return iTimeSetdao.getTimeSet();
	}

	@Override
	public int editTimeSet(TIMESETTING user) {
		// TODO Auto-generated method stub
		return iTimeSetdao.editTimeSet(user);
	}

	@Override
	public int addTimeSet(TIMESETTING user) {
		// TODO Auto-generated method stub
		return iTimeSetdao.addTimeSet(user);
	}

	@Override
	public int deleteTimeSet(String[] id) {
		// TODO Auto-generated method stub
		return iTimeSetdao.deleteTimeSet(id);
	}

	@Override
	public int udpateSMS(String smscontent) {
		// TODO Auto-generated method stub
		return iTimeSetdao.udpateSMS(smscontent);
	}

	@Override
	public List querySMS() {
		// TODO Auto-generated method stub
		return iTimeSetdao.querySMS();
	}

	@Override
	public List querySKIN() {
		// TODO Auto-generated method stub
		return iTimeSetdao.querySKIN();
	}

	@Override
	public int insertSKIN(String id,String skinname,String name, String descr) {
		// TODO Auto-generated method stub
		int result=0;
		if(StringUtils.isEmpty(id)){
			result=iTimeSetdao.insertSKIN(skinname,name, descr);
		}else{
			result=iTimeSetdao.updateSKIN(id,skinname,name, descr);
		}
		return result;
	}

	@Override
	public int delelte(String[] id) {
		// TODO Auto-generated method stub
		return iTimeSetdao.delelte(id);
	}

	@Override
	public List getSKINById(String id) {
		// TODO Auto-generated method stub
		return iTimeSetdao.getSKINById(id);
	}

	@Override
	public List queryModelPro() {
		// TODO Auto-generated method stub
		return iTimeSetdao.queryModelPro();
	}

	@Override
	public int deleteModelPro(String[] id) {
		// TODO Auto-generated method stub
		return iTimeSetdao.deleteModelPro(id);
	}

	@Override
	public List queryAdvPro(String pid,String leve) {
		// TODO Auto-generated method stub
		return iTimeSetdao.queryAdvPro(pid,leve);
	}

	@Override
	public int deleteAdvpro(String[] id) {
		// TODO Auto-generated method stub
		return iTimeSetdao.deleteAdvpro(id);
	}

	@Override
	public int saveModelPro(String id,String cid, String name, String descr, String userid) {
		// TODO Auto-generated method stub
		int result=0;
		if(StringUtils.isEmpty(id)){
			result=iTimeSetdao.saveModelPro(cid, name, descr, userid);
		}else{
			result=iTimeSetdao.updateModelPro(id, cid, name, descr, userid);
		}
		return result;
	}

	@Override
	public int saveadvPro(String id, String pid,String name, String userid,String leve) {
		// TODO Auto-generated method stub
		int result=0;
		if(StringUtils.isEmpty(id)){
			result=iTimeSetdao.saveadvPro(pid,name, userid,leve);			
		}else{
			result=iTimeSetdao.updateadvPro(id,name,userid);
		}
		 return result;
	}

	@Override
	public int savePutStragerPro(String id, String cid, String name, String interval, String descr, String userid) {
		// TODO Auto-generated method stub
		int result=0;
		
		if(StringUtils.isEmpty(id)){
			result=iTimeSetdao.savePutStragerPro(cid,name,interval,descr,userid);
		}else{
			result=iTimeSetdao.updatePutStragerPro(id,cid,name,interval,descr,userid);
		}
		return result;
	}

	@Override
	public List queryPutStragerPro() {
		// TODO Auto-generated method stub
		return iTimeSetdao.queryPutStragerPro();
	}

	@Override
	public int deletePutStragerPro(String id) {
		// TODO Auto-generated method stub
		return iTimeSetdao.deletePutStragerPro(id);
	}
	@Override
	public int updateUserAuth(String state) {
		// TODO Auto-generated method stub
		return iTimeSetdao.updateUserAuth(state);
	}
	public List queryUserAuth(){
		return iTimeSetdao.queryUserAuth();
	}
}

