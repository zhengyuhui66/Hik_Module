package com.hik.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.app.entity.ADVGROUP;
import com.hik.app.entity.MMPPPRODUCT;
import com.hik.dao.BaseService;
import com.hik.dao.IAdvertGroupDao;
import com.hik.dao.IGetAdvertDao;
import com.hik.dao.ImmppProductDao;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;


import net.sf.json.JSONObject;


@Service
@Transactional
public class ImmppProductServiceImpl  extends BaseService  implements ImmppProductService{
	@Autowired
	private ImmppProductDao immppProductDao;

	public Page getMmppgroup(int start, int limit,String searchStr) {
		// TODO Auto-generated method stub
		return immppProductDao.getmmppgroup(start, limit, searchStr);
//				getAdvertgroup(start, limit, searchStr);
	}

	@Override
	public int saveOrupdateMmppgroup(MMPPPRODUCT mmppproduct) {
		// TODO Auto-generated method stub
		int result=0;
		String id=mmppproduct.getId();
		if(StringUtils.isEmpty(id)){
			result=immppProductDao.savemmppgroup(mmppproduct);//saveAdvertgroup(mmppproduct);
		}else{
			result=immppProductDao.updatemmppgroup(mmppproduct);//.updateAdvertgroup(mmppproduct);
		}
		return result;
	}

	@Override
	public String deleteMmppgroup(String[] ids) {
		// TODO Auto-generated method stub
//		return immppProductDao.deletemmppgroup(ids);//.deleteAdvertgroup(ids);
		if(ids.length==0){
			return "没有选择";
		}
		List<JSONObject> result=immppProductDao.queryifputProduct(ids);
		boolean flag=true;
		if(result!=null&&result.size()>0){
			String tempStr="";
			for(JSONObject json:result){
				String count=JSONUtils.getString(json, "count");
				if(!count.equals("0")){
					String productName=JSONUtils.getString(json, "productname");
					tempStr+=productName+"有"+count+" 记录 \n";
					flag=false;
				}
			}
			if(!flag){
				tempStr+="正在投放中";
				return tempStr;
			}
		}
		if(flag){
			int num=immppProductDao.deletemmppgroup(ids);
			return "共删除"+num+"记录";
		}
		return "内部异常";
	}

//	@Override
//	public List queryGroupPutproduct(String[] ids) {
//		// TODO Auto-generated method stub
//		return immppProductDao.queryGroupPutproduct(ids);
//	}



}
