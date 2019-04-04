package com.hik.service;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hik.dao.IAdvertPlanDao;

@Deprecated
public class UpdateLoginedAdvertPlanCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
//	
//	private String stime;
//	private String etime;
//	private String modelId;
//	private List<String> busId;
//	private String modelmodeId;
//	private String advertId;
//	private IAdvertPlanDao iAdvertPlanDao;
//	public UpdateLoginedAdvertPlanCallable() {
//	};
//
//	public UpdateLoginedAdvertPlanCallable(String stime, String etime, String modelId, List<String> busId, String modelmodeId,
//			String advertId,IAdvertPlanDao iAdvertPlanDao) {
//		this.advertId=advertId;
//		this.stime=stime;
//		this.etime=etime;
//		this.busId=busId;
//		this.modelmodeId=modelmodeId;
//		this.modelId=modelId;
//		this.advertId=advertId;
//		this.iAdvertPlanDao=iAdvertPlanDao;
//	};
//
//	
//	public Integer call() throws Exception {
//		// TODO Auto-generated method stub
//		int result=0;
//		for(int i=0;i<busId.size();i++){
//			boolean flag=iAdvertPlanDao.getLoginedProduceUpdateBusId(stime,etime,busId.get(i),modelId,modelmodeId,advertId);		
//			if(flag){
//				result++;
//			}
//		}
//		return result;
//	}

}
