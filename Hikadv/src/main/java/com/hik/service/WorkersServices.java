package com.hik.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hik.app.entity.Workers_Info;

public interface WorkersServices {
	public List<Workers_Info> getWorkerInfoById(long id);
	public Page<Workers_Info> getByNameForPage(String email,Pageable pageable);
	
	public List getElementsById();
	public int updateId()  throws Exception;
//	public void updateId2()  throws Exception;
	public void tupdateid2() throws Exception;
	public void tupdateid() throws Exception;
	public int add();
//	public void deleteId2(int id);
}
