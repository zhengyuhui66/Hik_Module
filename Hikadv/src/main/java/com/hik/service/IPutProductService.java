package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutProductService {
/**
 * 分页查询投放产品
 * @param start 开始条数
 * @param limit 每页几条
 * @param searchStr 筛选条件
 * @return
 */
	Page queryputProduct(int start,int limit,String searchStr);
	/**
	 * 新增或更新投放产品
	 * @param putproduct
	 * @return
	 */
	int addorUpdateputProduct(PUTPRODUCT putproduct);
	/**
	 * 删除投放产品
	 * @param ids
	 * @return
	 */
	String deleteputProduct(String[] ids);


}
