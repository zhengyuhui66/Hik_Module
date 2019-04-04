package com.hik.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.ADVERTMODELMANGER;

import com.hik.dao.BaseService;
import com.hik.dao.IAdvertModelDao;

import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;


@Service
@Transactional
public class IAdvertModelServiceImpl  extends BaseService  implements IAdvertModelService{
	@Autowired
	private IAdvertModelDao iAdvertModelDao;

	public Page queryadvertModel(int start,int limit,String searchStr) {
		// TODO Auto-generated method stub
		return iAdvertModelDao.queryadvertModel(start,limit,searchStr);
	}
	@Transactional(rollbackFor=Exception.class)
	public int saveadvertModel(String description, String modelname,Map modemodel /*String[] subModelids, String[] subModelnames*/,String basePath,File targetFile,MultipartFile file,String userid,String fileName,String modelskin,String cycid) throws Exception {
		// TODO Auto-generated method stub
		int result=0;
		try {
			file.transferTo(targetFile);
			result=iAdvertModelDao.saveadvertModel(modemodel,basePath,modelname,userid,description,fileName,modelskin,cycid);			
//			String modelid=iAdvertModelDao.getModelId();
			
//			for(int i=0;i<subModelids.length;i++){
//				result=iAdvertModelDao.saveadvertModel(modelid, subModelids[i], basePath, modelname, subModelnames[i],userid, description,fileName,modelskin);			
//			}
		} catch (Exception e) {
			// TODO: handle exception
			if(targetFile.exists()){
				targetFile.delete();
			}
			throw e;
		}
		return 0;
	}
//	public int updateadvertModel(String id, String modelid,String mMname, String msubMname, String mMId,String modelskin) {
//		// TODO Auto-generated method stub
//		int result=iAdvertModelDao.updateadvertModel(id, msubMname, mMId);
//		result+=iAdvertModelDao.updateModel(modelskin, mMname, modelid);
//		return result;
//	}
	public String deleteadvertModel(JSONObject jsons,String modelUrl) {
		// TODO Auto-generated method stub
		Iterator jss=jsons.keys();
		List ids=new ArrayList();
		while(jss.hasNext()){
			String key=jss.next().toString();
			ids.add(key);
		}
		List<JSONObject> tJson = iAdvertModelDao.queryIfInproduct(ids);
		boolean flag=true;
		if(tJson!=null&&tJson.size()>0){
			String tempStr="";
			for(JSONObject json:tJson){
				String count=JSONUtils.getString(json, "count");
				if(!count.equals("0")){
					String productName=JSONUtils.getString(json, "name");
					tempStr+=productName+"有"+count+" 记录 \n";
					flag=false;
				}
			}
			if(!flag){
				tempStr+="在投放产品中";
				return tempStr;
			}
		}
		if(flag){
			int result=iAdvertModelDao.deleteadvertModel(ids);
			Iterator jsss=jsons.keys();
			while(jsss.hasNext()){
				String key=jsss.next().toString();
				File file = new File(modelUrl+"\\"+jsons.getString(key));
				if(file.exists()){
					file.delete();
				}
			}
			return "删除了"+result+"条记录";
		}
		return "内部异常";
	}
	@Override
	public int updateadvertModel(ADVERTMODELMANGER ad) {
		// TODO Auto-generated method stub
		return iAdvertModelDao.updateadvertModel(ad);
	}
	
//	public List queryModelModeById(String modelid) {
//	// TODO Auto-generated method stub
//	return iAdvertPlanDao.queryModelModeById(modelid);
//}
	@Override
	public List queryModelModeById(String modelid) {
		// TODO Auto-generated method stub
		return iAdvertModelDao.queryModelModeById(modelid);
	}

}
