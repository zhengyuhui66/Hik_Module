package com.hik.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hik.Exception.SftpuploadException;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.BLACKLIST;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.dao.AUDIT_INFO_jpa;
import com.hik.dao.AUDIT_USERINFO_jpa;
import com.hik.dao.BaseService;
import com.hik.dao.IBlackListMangerDao;
import com.hik.dao.ICorMangerDao;
import com.hik.dao.IDeviceMangerDao;
import com.hik.dao.IHomeDao;
import com.hik.dao.IMaterialDao;
import com.hik.dao.IMaterialDaoImpl;
import com.hik.dao.IPutMangerSetDao;
import com.hik.dao.IPutProductDao;
import com.hik.dao.IRouteMangerDao;
import com.hik.dao.MATERIEL_INFO_jpa;
import com.hik.framework.service.IUserService;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;
import com.hik.util.SFTP;
import com.jcraft.jsch.ChannelSftp;

import net.sf.json.JSONObject;


@Service
@Transactional
public class IBlackListMangerServiceImpl  extends BaseService  implements IBlackListMangerService{

	@Autowired
	private IBlackListMangerDao iBlackListMangerDao;

	@Override
	public Page queryBlackListManger(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		return iBlackListMangerDao.queryBlackListManger(start, limit, searchStr);
	}

	@Override
	public int addorUpdateBlackListManger(BLACKLIST blacklist) {
		// TODO Auto-generated method stub
		String id=blacklist.getId();
		int result=0;
		if(StringUtils.isEmpty(id)){
			//新增
			result=iBlackListMangerDao.addBlackListManger(blacklist);
		}else{
			//更新
			result=iBlackListMangerDao.updateBlackListManger(blacklist);
		}
		return result;
	}

//	@Override
//	public String deleteCorpManger(String[] ids) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String deleteBlackListManger(String[] ids) {
		// TODO Auto-generated method stub
		if(ids.length==0){
			return "没有选择";
		}
		return iBlackListMangerDao.deleteBlackListManger(ids);
	}
}
