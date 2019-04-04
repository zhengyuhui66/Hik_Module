package com.hik.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.AddressMapDao;
import com.hik.dao.BaseService;
import com.hik.framework.utils.Page;

@Service
@Transactional
public class AddressMapServiceImpl  extends BaseService  implements AddressMapService{

	@Autowired
	private AddressMapDao addressMapDao;

	@Override
	public int saveaddress(String nameid, String descrid,String userid,String[] latlongid) {
		// TODO Auto-generated method stub
		return addressMapDao.saveaddress(nameid, descrid,userid, latlongid);
	}

	@Override
	public List getAllAddress() {
		// TODO Auto-generated method stub
		return addressMapDao.getAllAddress();
	}

	@Override
	public int deleteAddress(String[] ids) {
		// TODO Auto-generated method stub
		return addressMapDao.deleteAddress(ids);
	}
	
	@Override
	public List queryListgaddress(String id) {
		// TODO Auto-generated method stub
		return addressMapDao.queryListgaddress(id);
	}
	
//	@Override
//	public Page queryaddress(int start,int limit,String search) {
//		// TODO Auto-generated method stub
//		return addressMapDao.queryaddress(start,limit,search);
//	}
	
	@Override
	public List queryListaddress(String id) {
		// TODO Auto-generated method stub
		return addressMapDao.queryListaddress(id);
	}
	
	@Override
	public Page querygaddress(int start,int limit,String search) {
		// TODO Auto-generated method stub
		return addressMapDao.querygaddress(start,limit,search);
	}

	@Override
	public int saveOrupdategaddress(String id, String name,String userid,String descr,String addressids) {
		// TODO Auto-generated method stub
		int result =0;
		List idgaddressList = addressMapDao.getgaddressid();
		String gaddressid=idgaddressList.get(0).toString();
		if(StringUtils.isEmpty(id)){
			//新增
			result=addressMapDao.savegaddress(gaddressid,name,userid,descr);
			if(addressids!=null){
				String[] ids = addressids.split(",");
				addressMapDao.insertgcs(gaddressid, ids);
			}
		}else{
			//更新
			result+=addressMapDao.updategaddress(id,name,userid,descr);
			if(addressids!=null){
				String[] ids = addressids.split(",");
				result +=  addressMapDao.deletegcs(id);
				result+=addressMapDao.insertgcs(id, ids);
			}
		}
		return result;
	}

	@Override
	public int deletegaddress(String[] ids) {
		// TODO Auto-generated method stub
		int result=0;
		result=addressMapDao.deletegaddress(ids);
		for(int i=0;i<ids.length;i++){
			result+=addressMapDao.deletegcs(ids[i]);			
		}
		return result;
	}


}
