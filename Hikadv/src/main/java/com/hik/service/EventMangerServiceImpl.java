package com.hik.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.EventMangerDao;
import com.hik.framework.utils.Page;

@Service
@Transactional
public class EventMangerServiceImpl  extends BaseService  implements EventMangerService{

	@Autowired
	private EventMangerDao eventMangerDao;

	@Override
	public Page queryevent(int start,int limit,String search) {
		// TODO Auto-generated method stub
		return eventMangerDao.queryevent(start,limit,search);
	}
	
	@Override
	public List queryListevent(String id) {
		// TODO Auto-generated method stub
		return eventMangerDao.queryListevent(id);
	}
	
	@Override
	public List queryListgevent(String id) {
		// TODO Auto-generated method stub
		return eventMangerDao.queryListgevent(id);
	}
	
	@Override
	public int saveOrupdateevent(String id, String name,String stime,String etime, String userid, String descr) {
		// TODO Auto-generated method stub
		int result =0;
		if(StringUtils.isEmpty(id)){
			//新增
			result=eventMangerDao.saveevent(name,userid,descr,stime,etime);
		}else{
			//更新
			result=eventMangerDao.updateevent(id,name,userid,descr,stime,etime);
		}
		return result;
	}

	@Override
	public int deleteevent(String[] ids) {
		// TODO Auto-generated method stub
		return eventMangerDao.deleteevent(ids);
	}

	@Override
	public Page querygevent(int start,int limit,String search) {
		// TODO Auto-generated method stub
		return eventMangerDao.querygevent(start,limit,search);
	}

	@Override
	public int saveOrupdategevent(String id, String name,String userid,String descr,String eventids) {
		// TODO Auto-generated method stub
		int result =0;
		List idgeventList = eventMangerDao.getgeventid();
		String geventid=idgeventList.get(0).toString();
		if(StringUtils.isEmpty(id)){
			//新增
			result=eventMangerDao.savegevent(geventid,name,userid,descr);
			if(eventids!=null){
				String[] ids = eventids.split(",");
				eventMangerDao.insertgcs(geventid, ids);
			}
		}else{
			//更新
			result+=eventMangerDao.updategevent(id,name,userid,descr);
			if(eventids!=null){
				String[] ids = eventids.split(",");
				result +=  eventMangerDao.deletegcs(id);
				result+=eventMangerDao.insertgcs(id, ids);
			}
		}
		return result;
	}

	@Override
	public int deletegevent(String[] ids) {
		// TODO Auto-generated method stub
		int result=0;
		result=eventMangerDao.deletegevent(ids);
		for(int i=0;i<ids.length;i++){
			result+=eventMangerDao.deletegcs(ids[i]);			
		}
		return result;
	}

	@Override
	public List getEventHappen() {
		// TODO Auto-generated method stub
		return eventMangerDao.getEventHappen();
	}
}
