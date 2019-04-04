package com.hik.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.PhoneMangerDao;
import com.hik.framework.utils.Page;

@Service
@Transactional
public class PhoneMangerServiceImpl  extends BaseService  implements PhoneMangerService{

	@Autowired
	private PhoneMangerDao phoneMangerDao;

	@Override
	public Page queryphone(int start,int limit,String search) {
		// TODO Auto-generated method stub
		return phoneMangerDao.queryphone(start,limit,search);
	}
	
	@Override
	public List queryListphone(String id) {
		// TODO Auto-generated method stub
		return phoneMangerDao.queryListphone(id);
	}
	
	@Override
	public List queryListgphone(String id) {
		// TODO Auto-generated method stub
		return phoneMangerDao.queryListgphone(id);
	}
	
	@Override
	public int saveOrupdatephone(String id, String name, String userid, String descr) {
		// TODO Auto-generated method stub
		int result =0;
		if(StringUtils.isEmpty(id)){
			//新增
			result=phoneMangerDao.savephone(name,userid,descr);
		}else{
			//更新
			result=phoneMangerDao.updatephone(id,name,userid,descr);
		}
		return result;
	}

	@Override
	public int deletephone(String[] ids) {
		// TODO Auto-generated method stub
		return phoneMangerDao.deletephone(ids);
	}

	@Override
	public Page querygphone(int start,int limit,String search) {
		// TODO Auto-generated method stub
		return phoneMangerDao.querygphone(start,limit,search);
	}

	@Override
	public int saveOrupdategphone(String id, String name,String userid,String descr,String phoneids) {
		// TODO Auto-generated method stub
		int result =0;
		List idgphoneList = phoneMangerDao.getgphoneid();
		String gphoneid=idgphoneList.get(0).toString();
		if(StringUtils.isEmpty(id)){
			//新增
			result=phoneMangerDao.savegphone(gphoneid,name,userid,descr);
			if(phoneids!=null){
				String[] ids = phoneids.split(",");
				phoneMangerDao.insertgp(gphoneid, ids);
			}
		}else{
			//更新
			result+=phoneMangerDao.updategphone(id,name,userid,descr);
			if(phoneids!=null){
				String[] ids = phoneids.split(",");
				result +=  phoneMangerDao.deletegp(id);
				result+=phoneMangerDao.insertgp(id, ids);
			}
		}
		return result;
	}

	@Override
	public int deletegphone(String[] ids) {
		// TODO Auto-generated method stub
		int result=0;
		result=phoneMangerDao.deletegphone(ids);
		for(int i=0;i<ids.length;i++){
			result+=phoneMangerDao.deletegp(ids[i]);			
		}
		return result;
	}
}
