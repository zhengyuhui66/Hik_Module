package com.hik.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.Workers_Info;
import com.hik.dao.BaseService;
import com.hik.dao.WorJPADao;
import com.hik.dao.WorJPASqlDao;

import net.sf.json.JSONObject;


@Service
public class WorkersServicesImpl2  extends BaseService  implements WorkersServices2{
	
	@Autowired
	private WorJPADao worJPADao;
	
	@Autowired
	private WorJPASqlDao worJPASqlDao;
	
	@Override
	public List<Workers_Info> getWorkerInfoById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Page<Workers_Info> getByNameForPage(String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE)
	public List getElementsById() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Object[]> list = worJPASqlDao.getElementById();
		for(int i=0;i<list.size();i++){
			Object[] str = (Object[])list.get(i);
			System.out.println("==>"+str[0]+"=="+str[1]+"=="+str[2]);
		}
//		System.out.println("==>1"+);
		@SuppressWarnings("rawtypes")
		List list2 = worJPASqlDao.getElementById();
		for(int i=0;i<list2.size();i++){
			Object[] str = (Object[])list2.get(0);
			System.out.println("==>"+str[0]+"=="+str[1]);
		}
		return null;
	}
//	@Override
//	public void deleteId(int id) {
//		// TODO Auto-generated method stub
//		
//		
//	}
//	@Override
//	public void deleteId2(int id) {
//		// TODO Auto-generated method stub
//		
//	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int updateId() throws Exception {
		// TODO Auto-generated method stub
		int list = worJPASqlDao.update2();
		tupdateid();
//		int i=0;
//		if(i==0){
//			i=1/0;
//		}
		return 0;
	}
	
//	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
//	public void tupdateid2() throws Exception{
//
//		return;
//	}

	
	
	
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional
	public int add() {
		// TODO Auto-generated method stub
		
		List list=worJPASqlDao.queryCount();
		
		Object str=list.get(0);
		System.out.println("str:==="+str.toString());
		int i1=worJPASqlDao.add("2", "SALARY2", "EMAIL2", "EMPLOYEDDATES2", "DEPARTMENT2");
		int i2=worJPASqlDao.add("3", "SALARY3", "EMAIL3", "EMPLOYEDDATES3", "DEPARTMENT3");
		int i3=worJPASqlDao.add("4", "SALARY4", "EMAIL4", "EMPLOYEDDATES4", "DEPARTMENT4");
		int i4=worJPASqlDao.add("5", "SALARY5", "EMAIL5", "EMPLOYEDDATES5", "DEPARTMENT5");
		System.out.println("i1:"+i1+"i2:"+i2+"i3:"+i3+"i4:"+i4+"str:"+str);
		return 0;
	}
//	@Override
//	public int updateId2() throws Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void tupdateid2() throws Exception {
		// TODO Auto-generated method stub
		int list = worJPASqlDao.update2();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void tupdateid(){
		int list = worJPASqlDao.update();
		System.out.println("=======>"+list);
		int i=0;
		if(i==0){
			i=1/0;
		}
		return;
	}
	
//	public List<Workers_Info> getWorkerInfoById(long id) {
//		// TODO Auto-generated method stub
//		return worJPADao.findById(id);
//	}
//	
//	public Page<Workers_Info> getByNameForPage(String email,Pageable pageable) {
//		// TODO Auto-generated method stub
//			return worJPADao.findByNameForPage(email, pageable);
//	}
//	
//	public List getElementsById() {
//		// TODO Auto-generated method stub
//		return worJPASqlDao.getElementById("1");
//	}
//	
//	public void deleteId(int id){
//		  System.out.println("-------hello deleteId1---------");
//		 worJPADao.delete(id);
//	}
//	@Transactional
//	public void deleteId2(int id){
//		EntityManager entityManager  = getTransEm();
//		beginTransaction(entityManager);
//		  System.out.println("-------hello deleteId1---------");
//		worJPASqlDao.deleteId2(id,entityManager);
//		commitTransaction(entityManager);
//	}
}
