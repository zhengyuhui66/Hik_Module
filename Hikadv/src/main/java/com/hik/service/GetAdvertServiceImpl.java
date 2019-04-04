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
import com.hik.dao.BaseService;
import com.hik.dao.IGetAdvertDao;
import com.hik.framework.utils.Page;


import net.sf.json.JSONObject;


@Service
@Transactional
public class GetAdvertServiceImpl  extends BaseService  implements IGetAdvertService{
	@Autowired
	private IGetAdvertDao iGetAdvertDao;

	public List<JSONObject> getAdvert(String busId) {
		// TODO Auto-generated method stub
		return iGetAdvertDao.getAdvert(busId);
	}
	public List<JSONObject> getLoginedAdvert(String busId) {
		// TODO Auto-generated method stub
		return iGetAdvertDao.getLoginedAdvert(busId);
	}

	public Page getAdvert(int start, int limit,String searchStr) {
		// TODO Auto-generated method stub
		return iGetAdvertDao.getAdvert(start, limit,searchStr);
	}
	public List getAdvertView(String modelid, JSONObject json) {
		// TODO Auto-generated method stub
		List mourl=iGetAdvertDao.getModelUrl(modelid);
		if(mourl==null||mourl.size()==0){
			return null;
		}
		String modelurl=mourl.get(0).toString();
		Map<String,Object> map = new HashMap<String,Object>();
		Iterator iter = json.keys();
		while(iter.hasNext()){
			String key=iter.next().toString();
			String value = json.getString(key);
			List<JSONObject> list=iGetAdvertDao.getAdvertUrl(value);
			if(list.size()>0&&list!=null){
				JSONObject jso=list.get(0);
				map.put("materurl"+key,jso.get("mater_path"));
				map.put("adverturl"+key,jso.get("advert_url"));				
			}
		}
		List result = new ArrayList();
		result.add(modelurl);
		result.add(map);
		return result;
	}
	public List getMater(String materTypeid,String materSizeid,String materCreatorid) {
		// TODO Auto-generated method stub
		return iGetAdvertDao.getMater(materTypeid,materSizeid,materCreatorid);
	}

	public int saveAdvert(String materId, String adverturl, String description, String advertname,String userid,Set<String> putconIdSet,String advertpropertys,String advertproperty) {
		// TODO Auto-generated method stub
		int result=0;
		List ids = iGetAdvertDao.AdvertId();
		String _id=ids.get(0).toString();
		/**
		 * 保存新增数据
		 */
		result=iGetAdvertDao.saveAdvert(_id,materId, adverturl, description, advertname,userid,advertpropertys,advertproperty);
		/**
		 * 保存新增广告优先投放条件
		 */
		if(putconIdSet!=null){
			Iterator<String> iterator=putconIdSet.iterator();
			while(iterator.hasNext()){
//			System.out.println(iterator.next());
				String temp = iterator.next();
				if(StringUtils.isNotEmpty(temp)){
					iGetAdvertDao.saveAdvPutset(_id,temp);					
				}
			}			
		}
		
		return result;
	}

	public int updateAdvert(String adverid, String materId, String adverturl, String description, String advertname,Set<String> putconIdSet,String advertpropertys,String advertproperty) {
		// TODO Auto-generated method stub
		int result=iGetAdvertDao.updateAdvert(adverid, materId, adverturl, description, advertname,advertpropertys,advertproperty);
		iGetAdvertDao.deleteAdvputSet(adverid);
		/**
		 * 保存新增广告优先投放条件
		 */
		if(putconIdSet!=null){
			Iterator<String> iterator=putconIdSet.iterator();
			while(iterator.hasNext()){
				String temp = iterator.next();
				if(StringUtils.isNotEmpty(temp)){
					iGetAdvertDao.saveAdvPutset(adverid,temp);					
				}
			}			
		}
		return result;
	}

	public int deleteAdvert(String[] advertids) {
		// TODO Auto-generated method stub
		int result=0;
		result=iGetAdvertDao.deleteAdvert(advertids);
		 for(int i=0;i<advertids.length;i++){
			 iGetAdvertDao.deleteAdvputSet(advertids[i]);			 
		 }
		return result;
	}
	public List getAdvGrounpById(String[] advertids){
		List list  = new ArrayList();
		list = iGetAdvertDao.getadvingroup(advertids);
		return list;
	}
	
	public boolean getrepeatAdvertName(String name) {
		// TODO Auto-generated method stub
		List list =iGetAdvertDao.getrepeatAdvertName(name);
		if(list==null||list.size()==0){
			return true;			
		}else{
			return false;
		}
	}

	@Override
	public List getConditionByAdvid(String id) {
		// TODO Auto-generated method stub
		return iGetAdvertDao.getConditionByAdvid(id);
	}
	@Override
	public List getAdvBymaterId(String[] materid) {
		// TODO Auto-generated method stub
		return iGetAdvertDao.getAdvBymaterId(materid);
	}
}
