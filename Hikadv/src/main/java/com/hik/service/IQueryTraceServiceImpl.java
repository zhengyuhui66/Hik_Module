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
import com.hik.app.entity.CORP;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.dao.AUDIT_INFO_jpa;
import com.hik.dao.AUDIT_USERINFO_jpa;
import com.hik.dao.BaseService;
import com.hik.dao.ICorMangerDao;
import com.hik.dao.IHomeDao;
import com.hik.dao.IMaterialDao;
import com.hik.dao.IMaterialDaoImpl;
import com.hik.dao.IPutMangerSetDao;
import com.hik.dao.IPutProductDao;
import com.hik.dao.IQueryTraceDao;
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


@Service("iQueryTraceServiceImpl")
@Transactional
public class IQueryTraceServiceImpl  extends BaseService  implements IQueryTraceService{

	@Autowired
	private IQueryTraceDao iQueryTraceDao;

	@Override
	public List QueryTrace(String stime, String etime, String equipmentid) {
		// TODO Auto-generated method stub
		return iQueryTraceDao.queryTrace(stime, etime, equipmentid);
	}

	@Override
	public int addTrace(String date,String captime, String longitude, String latidude, String equipmentid) {
		// TODO Auto-generated method stub
		log.info("月份时间:"+date+"   经纬上传时间:"+captime+"  经度:"+longitude+" 纬度:"+latidude+" 设备ID:"+equipmentid);
		int result=iQueryTraceDao.addTrace(date, captime, longitude, latidude, equipmentid);
		return result;
	}
}
