package com.hik.service;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.hik.dao.BaseService;
import com.hik.framework.dao.ClientDao;
import com.hik.framework.utils.Page;

@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class FeedBackServiceImpl  extends BaseService implements FeedBackService{
	
    private final Log log = LogFactory.getLog(FeedBackServiceImpl.class);
//    @Value("${clientPath}")
//	private String clientPath;
    
    @Autowired
    private ClientDao clientDao;
    
    
	@Override
	public Page queryClientInfo(int start,int limit) {
		// TODO Auto-generated method stub
		Page page = clientDao.queryClientInfo(start, limit);
		return page;
	}
//	
//	@Override
//	public int saveClient(String path,MultipartFile file,HttpServletRequest request,String version,String versionName,String versiondesc,String userid) {
//		// TODO Auto-generated method stub
//   	
//   	 String fileName = file.getOriginalFilename();
//   	 File targetFile = new File(path, fileName);
//   	 if(!targetFile.exists()){
//   		 targetFile.mkdirs();  
//   	 } 
//   	 try {
//		file.transferTo(targetFile);
//		clientDao.insertClient(fileName, "android", version, versiondesc, versionName, clientPath+fileName, userid);
//	} catch (Exception e) {
//		if(targetFile.exists()){
//			targetFile.delete();
//		}
//		e.printStackTrace();
//		throw new RuntimeException();
//	}
//   	 
//		return 0;
//	}

}

