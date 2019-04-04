package com.hik.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

@Repository
public class WorJSPSqlDaoImpl extends BaseHIKDao implements WorJPASqlDao{
//	private EntityManager entityManager;
	
	public List getElementById(){
		// TODO Auto-generated method stub
		System.out.println("========start query!!!!");
		String sql="select t.workername,t.email from Workers_Info t";
//		String sql=""
		return getNoPageObject(sql);
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		System.out.println("====>");
		
		String sql="update Worker_Info set workername='zhengyuhui222' where id=3";
		return executeUpdateSQL(sql);
	}
	@Override
	public int update2() {
		// TODO Auto-generated method stub
		System.out.println("====>");
		String sql="update Worker_Info set workername='zhengyuhui222' where id=4";
		return executeUpdateSQL(sql);
	}
	@Override
	public int add(String workername, String SALARY, String EMAIL, String EMPLOYEDDATES, String DEPARTMENT) {
		// TODO Auto-generated method stub
		List param = new ArrayList();
		param.add(workername);
		param.add(SALARY);
		param.add(EMAIL);
		param.add(EMPLOYEDDATES);
		param.add(DEPARTMENT);
		
		String sql="insert into Worker_Info(id,WORKERNAME,SALARY,EMAIL,EMPLOYEDDATES,DEPARTMENT) values(Worker_Info_seq.nextval,?,?,?,?,?)";
		return executeUpdateSQL(sql, param, "ÐÂæÒ");
	}

	@Override
	public List queryCount(){
		// TODO Auto-generated method stub
//		String sql="select count(*) from Worker_Info";
		String sql="select COUNT(*) from MACACQ.READMACINFO PARTITION (PART_READMACINFO_2016_08_28)";
		return getNoPageObject(sql);
	}	
}
