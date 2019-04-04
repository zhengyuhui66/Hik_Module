package com.hik.framework.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_ROLE;
import com.hik.dao.BaseService;
import com.hik.dao.SYS_RIGHT_jpa;
import com.hik.dao.SYS_ROLE_jpa;
import com.hik.framework.dao.ILoginDao;
import com.hik.framework.dao.IMenuDao;
import com.hik.framework.dao.IRoleDao;
import com.hik.framework.dao.LoginDaoImpl;
import com.hik.framework.utils.CommonSence;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class RoleServiceImpl  extends BaseService implements IRoleService{
    private final Log log = LogFactory.getLog(RoleServiceImpl.class);
	@Autowired
	private IRoleDao iRoleDao;
	@Autowired
	private SYS_ROLE_jpa role_jpa;
	public List<JSONObject> getRoleInfo() {
		// TODO Auto-generated method stub
		List<JSONObject> list = iRoleDao.getRoleInfo();
		return list;
	}
	public int addOrUpdateRoleInfo(SYS_ROLE role) {
		// TODO Auto-generated method stub
//		String id=iRoleDao
		String trid=role.getTrid();
		int result=0;
		if(StringUtils.isEmpty(trid)){
			result=iRoleDao.addRoleInfo(role);
		}else{
			result=iRoleDao.UpdateRoleInfo(role);
		}
////			trid=getUUID();
//			List list = iRoleDao.getSeqSysrole();
//			String id=list.get(0).toString();
//			Date currentTime = new Date();
//		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		   String dateString = formatter.format(currentTime);
//		   role.setCreate_time(dateString);
//		   role.setTrid(id);
////			   create_time=dateString;
//		}
//		SYS_ROLE sl = role_jpa.saveAndFlush(role);
		return result;
	}
	public int deleteRoleById(String[] trid) {
		// TODO Auto-generated method stub
//		role_jpa.delete(id);
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		int result=0;
		result=iRoleDao.deleteRole(trid);
		iRoleDao.deleteRoleRight(trid);
//		commitTransaction(entityManager);
		return result;
	}
	
	public List getPRoleRight(String trid,String ptrid) {
		// TODO Auto-generated method stub
		
		List<JSONObject> list = iRoleDao.getPRoleRight(ptrid);
		List checked=iRoleDao.getRoleRightBytrid(trid);
		List<JSONObject> result=gettreeMenu(list,checked,CommonSence.HYPE_RIGHT);
		System.out.println("==>:"+JSONArray.fromObject(result));
		return result;
	}
   //菜单树形结构  
   public List<JSONObject> gettreeMenu(List<JSONObject> menuList, List checked,String parentId){ 
       JSONArray childMenu = new JSONArray();  
       for (JSONObject object : menuList) {
           JSONObject jsonMenu = JSONObject.fromObject(object);  
//	           int menuId = jsonMenu.getInt("rrid");  
//	           int pid = jsonMenu.getInt("parent_id");  
           String menuId = jsonMenu.getString("rrid");  
           String pid = jsonMenu.getString("parent_tr_id"); 
           if (parentId.equals(pid)) {
        	   List<JSONObject> c_node = gettreeMenu(menuList, checked,menuId);  
        	   if(c_node.size()==0||c_node==null){
        		   jsonMenu.put("leaf",true);
        		   
        	   }else{
        		   jsonMenu.put("expanded", true);
        		   jsonMenu.put("children", c_node);
        	   }
        	   
        	   if(checked!=null&&checked.size()>0&&checked.contains(menuId)){
        		   jsonMenu.put("checked", true);
        	   }else{
        		   jsonMenu.put("checked", false);
        	   }
        	   childMenu.add(jsonMenu);  
           }  
       }  
       return childMenu;  
   }
	public int saveRoleRight(String _trid, String[] rrid) {
		// TODO Auto-generated method stub
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
		String[] tr=new String[]{_trid};
		int temp=iRoleDao.deleteRoleRight(tr);
		int result=0;
		for(int i=0;i<rrid.length;i++){
			result+=iRoleDao.saveRoleRight(_trid, rrid[i]);
		}
//		commitTransaction(entityManager);
		return result;
	}
	
	public int deleteRoleRightByTridRrid(String _trid,String[] rrid){
		return 0;
	}
	
//	public List getRoleRightBytrid(String trid){
//		List list=iRoleDao.getRoleRightBytrid(trid);
//		return map;
//	}
}

