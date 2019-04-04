package com.hik.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.TimeMangerDao;
import com.hik.framework.utils.Page;

@Service
@Transactional
public class TimeMangerServiceImpl  extends BaseService  implements TimeMangerService{

	@Autowired
	private TimeMangerDao timeMangerDao;

	@Override
	public Page querytime(int start,int limit,String search) {
		// TODO Auto-generated method stub
		return timeMangerDao.querytime(start,limit,search);
	}
	
	@Override
	public List queryListtime(String id) {
		// TODO Auto-generated method stub
		return timeMangerDao.queryListtime(id);
	}
	
	@Override
	public List queryListgtime(String id) {
		// TODO Auto-generated method stub
		return timeMangerDao.queryListgtime(id);
	}
	
	@Override
	public int saveOrupdatetime(String id, String name, String stime,String etime,String sdtime,String edtime,String userid, String descr) {
		// TODO Auto-generated method stub
		int result =0;
		if(StringUtils.isEmpty(id)){
			//新增
			result=timeMangerDao.savetime(name,userid,descr,stime,etime,sdtime,edtime);
		}else{
			//更新
			result=timeMangerDao.updatetime(id,name,userid,descr,stime,etime,sdtime,edtime);
		}
		return result;
	}

	@Override
	public int deletetime(String[] ids) {
		// TODO Auto-generated method stub
		return timeMangerDao.deletetime(ids);
	}

	@Override
	public Page querygtime(int start,int limit,String search) {
		// TODO Auto-generated method stub
		return timeMangerDao.querygtime(start,limit,search);
	}

	@Override
	public int saveOrupdategtime(String id, String name,String userid,String descr,String timeids) {
		// TODO Auto-generated method stub
		int result =0;
		List idgtimeList = timeMangerDao.getgtimeid();
		String gtimeid=idgtimeList.get(0).toString();
		if(StringUtils.isEmpty(id)){
			//新增
			result=timeMangerDao.savegtime(gtimeid,name,userid,descr);
			if(timeids!=null){
				String[] ids = timeids.split(",");
				timeMangerDao.insertgcs(gtimeid, ids);
			}
		}else{
			//更新
			result+=timeMangerDao.updategtime(id,name,userid,descr);
			if(timeids!=null){
				String[] ids = timeids.split(",");
				result +=  timeMangerDao.deletegcs(id);
				result+=timeMangerDao.insertgcs(id, ids);
			}
		}
		return result;
	}

	@Override
	public int deletegtime(String[] ids) {
		// TODO Auto-generated method stub
		int result=0;
		result=timeMangerDao.deletegtime(ids);
		for(int i=0;i<ids.length;i++){
			result+=timeMangerDao.deletegcs(ids[i]);			
		}
		return result;
	}


}
