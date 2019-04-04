package com.hik.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.dao.BaseService;
import com.hik.dao.SYS_RIGHT_jpa;
import com.hik.framework.dao.ILoginDao;
import com.hik.framework.dao.IMenuDao;
import com.hik.framework.dao.LoginDaoImpl;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class MenuServiceImpl  extends BaseService implements IMenuService{
    private final Log log = LogFactory.getLog(MenuServiceImpl.class);
	@Autowired
	private IMenuDao iMenuDao;
	@Autowired
	private SYS_RIGHT_jpa jpa;

	public List getIMenuInfo(String userid){
		// TODO Auto-generated method stub
		@SuppressWarnings({ "unused", "unchecked" })
		List<JSONObject> list = iMenuDao.getMenuInfo(userid);
		List<JSONObject> roleList = iMenuDao.getRoleInfo();
		List<JSONObject> resultList=treeMenu(list,CommonSence.HYPE_RIGHT);
		log.info("当前用户菜单目录:"+JSONArray.fromObject(resultList));
		return resultList;
	}
	
	//菜单树形结构  
	   public List<JSONObject> treeMenu(List<JSONObject> menuList, String parentId){ 
	       JSONArray childMenu = new JSONArray();  
	       for (JSONObject object : menuList) {
	           JSONObject jsonMenu = JSONObject.fromObject(object);  
	           if("3".equals(jsonMenu.get("leve").toString())){
	        	   continue;
	           }
	           String menuId = jsonMenu.getString("rrid");  
	           String pid = jsonMenu.getString("parent_id");  
	           if (parentId.equals(pid)) {
	        	   List<JSONObject> c_node = treeMenu(menuList, menuId);  
	        	   if(c_node.size()==0||c_node==null){
	        		   jsonMenu.put("leaf",true);
	        	   }else{
	        		   jsonMenu.put("expanded", true);
	        		   jsonMenu.put("children", c_node);
	        	   }
	        		   childMenu.add(jsonMenu);  
	           }  
	       }  
	       return childMenu;  
	   }

	   
	   //菜单树形结构  
	   public List<JSONObject> gettreeMenu(List<JSONObject> menuList, String parentId){ 
	       JSONArray childMenu = new JSONArray();  
	       for (JSONObject object : menuList) {
	           JSONObject jsonMenu = JSONObject.fromObject(object);  
	           String menuId = jsonMenu.getString("rrid");  
	           String pid = jsonMenu.getString("parent_id"); 
	           if (parentId.equals(pid)) {
	        	   List<JSONObject> c_node = gettreeMenu(menuList, menuId);  
	        	   if(c_node.size()==0||c_node==null){
	        		   jsonMenu.put("leaf",true);
	        	   }else{
	        		   jsonMenu.put("expanded", true);
	        		   jsonMenu.put("children", c_node);
	        	   }
	        	   childMenu.add(jsonMenu);  
	           }  
	       }  
	       return childMenu;  
	   }
	public List getIEditMenuInfo(String userid) {
		// TODO Auto-generated method stub
		@SuppressWarnings({ "unused", "unchecked"})
		List<JSONObject> list = iMenuDao.getEditMenuInfo(userid);
		List<JSONObject> resultList=gettreeMenu(list,CommonSence.HYPE_RIGHT);
		log.info("需要编辑的列是:"+JSONArray.fromObject(resultList));
		return resultList;
	}
	public int updataOrAddMenu(SYS_RIGHT right,JSONObject json){
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		int result = iMenuDao.saveAndFlush(right);
		List rolesList = new ArrayList();
		 Iterator it = json.keys();  
		 while(it.hasNext()){  
			 String key=it.next().toString();
			 if("true".equals(json.get(key).toString())){
				 result+=iMenuDao.saveAndFlushRightRole(right.getRRID(),key);
			 }else if("false".equals(json.get(key).toString())){
				 result+=iMenuDao.deleteRightRole(right.getRRID(),key);
			 }
		 }
//		 commitTransaction(entityManager);
		return result;
		
	} 

	public void deleteMenu(String rrid) {
		// TODO Auto-generated method stub
		jpa.delete(rrid);
	}

	public int addMenu(SYS_RIGHT right,String[] roles) {
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		List list = iMenuDao.getSeq_sysrightseq();
		String id=list.get(0).toString();
		right.setRRID(id);
		int t=iMenuDao.addMenu(right);
		
		for(int i=0;i<roles.length;i++){
//			int _role=Integer.parseInt(roles[i]);
			int result=iMenuDao.addRightRole(id,roles[i]);
			t+=result;
		}		
//		commitTransaction(entityManager);
		return t;
	}

	public List<JSONObject> getRoleInfo() {
		// TODO Auto-generated method stub
		List<JSONObject> list = iMenuDao.getRoleInfo();
		return list;
	}

	public List getRightRoles(String rrid) {
		// TODO Auto-generated method stub
		List list = iMenuDao.getRightRoles(rrid);
		return list;
	}

	public boolean getHaveRight(String description,HttpServletRequest request) {
		// TODO Auto-generated method stub
		return iMenuDao.getHaveRight(description,CommonUtil.getUserId(request));
	}

	public boolean updatepword(String userid, String newpword) {
		// TODO Auto-generated method stub
		int t=iMenuDao.updatepword(userid,newpword);
		if(t==1){
			return true;
		}
		return false;
	}


	
	  
}

