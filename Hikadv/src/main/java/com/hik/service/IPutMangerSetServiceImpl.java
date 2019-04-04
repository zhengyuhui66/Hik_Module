package com.hik.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

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
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.dao.AUDIT_INFO_jpa;
import com.hik.dao.AUDIT_USERINFO_jpa;
import com.hik.dao.BaseService;
import com.hik.dao.IHomeDao;
import com.hik.dao.IMaterialDao;
import com.hik.dao.IMaterialDaoImpl;
import com.hik.dao.IPutMangerSetDao;
import com.hik.dao.MATERIEL_INFO_jpa;
import com.hik.framework.service.IUserService;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.Page;
import com.hik.util.SFTP;
import com.jcraft.jsch.ChannelSftp;

import net.sf.json.JSONObject;


@Service
@Transactional
public class IPutMangerSetServiceImpl  extends BaseService  implements IPutMangerSetService{

	@Autowired
	private IPutMangerSetDao iPutMangerSetDao;

	@Override
	public Page queryForPage(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		return iPutMangerSetDao.queryForPage(start, limit, searchStr);
	}

	@Override
	public int addorUpdateAdvputSet(PRICONDITION pricondition) {
		// TODO Auto-generated method stub
		int id=pricondition.getId();
		int result=0;
		if(id==0){
			//新增
			result=iPutMangerSetDao.addAdvputSet(pricondition);
		}else{
			//更新
			result=iPutMangerSetDao.updateAdvputSet(pricondition);
		}
		return result;
	}

	@Override
	public int deleteAdvputSet(String[] id) {
		// TODO Auto-generated method stub
		int result=iPutMangerSetDao.deleteAdvputSet(id);
		return result;
	}

	@Override
	public List queryputSet() {
		// TODO Auto-generated method stub
		return iPutMangerSetDao.queryputSet();
	}
}
