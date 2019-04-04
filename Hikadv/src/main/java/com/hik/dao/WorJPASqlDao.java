package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

public interface WorJPASqlDao {
	public List getElementById();
	public int update();
	public int update2();
	public int add(String workername,String SALARY,String EMAIL,String EMPLOYEDDATES,String DEPARTMENT);
	
	public List queryCount();
}
