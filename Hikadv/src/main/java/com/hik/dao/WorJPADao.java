package com.hik.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.Workers_Info;
import com.hik.jpa.SearchAndPageRepository;

/** 
 * @author  Mrz 
 * @date：2016-5-7
 * 
 */
public interface WorJPADao extends JpaRepository<Workers_Info, Integer>{
//空的，可以什么都不用写
	@Query("select o from Workers_Info o where o.id=?1")
	public List<Workers_Info> findById(long id);
	
	@Query("select o from Workers_Info o where o.email=?1")
	public Page<Workers_Info> findByNameForPage(String email,Pageable page);
	
//	@Modifying
//	@Query("delete from Workers_Info o where o.id=?1")
//	public int deleteId(long id);
} 
