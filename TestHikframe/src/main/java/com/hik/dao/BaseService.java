package com.hik.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.hik.framework.utils.Page;
import com.hik.framework.utils.PageObject;

import net.sf.json.JSONObject;

public class BaseService {
	
	protected Log log = LogFactory.getLog(this.getClass()); 
	
	protected  EntityManager entityManager;
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	
	
	public EntityManager getEm(){
		if(entityManagerFactory!=null){
			if(entityManager==null){
				entityManager=entityManagerFactory.createEntityManager();
			}
			if(!entityManager.isOpen()){
				entityManager.clear();
				entityManager=entityManagerFactory.createEntityManager();
			}
			return entityManager;
		}else{
			return null;
		}
	}
	
	public EntityManager getTransEm(){
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		return entityManager;
	}
	
	public void beginTransaction(EntityManager em){
		if(em!=null){
			em.getTransaction().begin();
		}
	}
	
	public void commitTransaction(EntityManager em){
		if(em.getTransaction().isActive()){
			em.getTransaction().commit();
		}
		em.close();
	}
	
	
	/**
	 * 
	 * @param list 需要转换成JSON的List
	 * @param str key集合
	 * @return
	 */
	protected List<JSONObject> listToJson(List list,String[] str){
		List<JSONObject> resultList = new ArrayList<JSONObject>();
		
		int tjsonstr=str.length;
		for(int i=0;i<list.size();i++){
			Object[] tempObj = (Object[]) list.get(i);
			JSONObject tempJson = new JSONObject();
			for(int j=0;j<tjsonstr;j++){
				tempJson.accumulate(str[j],tempObj[j]);
			}
			resultList.add(tempJson);
		}
		return resultList;
	}


	
	
}
