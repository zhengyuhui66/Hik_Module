package com.hik.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutMangerSetService {
	/**
	 * 分页查询
	 * @param start  开始条数
	 * @param limit 每页几条
	 * @param searchStr 条件模糊查询
	 * @return
	 */
	Page queryForPage(int start,int limit,String searchStr);
	/**
	 * 更新或者新增
	 * @param pricondition
	 * @return
	 */
	int addorUpdateAdvputSet(PRICONDITION pricondition);
	int deleteAdvputSet(String[] id);
	
	List queryputSet();
}
