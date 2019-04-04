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
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.dao.AUDIT_INFO_jpa;
import com.hik.dao.AUDIT_USERINFO_jpa;
import com.hik.dao.BaseService;
import com.hik.dao.IHomeDao;
import com.hik.dao.IMaterialDao;
import com.hik.dao.IMaterialDaoImpl;
import com.hik.dao.IPutMangerSetDao;
import com.hik.dao.IPutProductDao;
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
public class IPutProductServiceImpl  extends BaseService  implements IPutProductService{

	@Autowired
	private IPutProductDao iPutProductDao;

	@Override
	public Page queryputProduct(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		return iPutProductDao.queryputProduct(start, limit, searchStr);
	}

	@Override
	public int addorUpdateputProduct(PUTPRODUCT putproduct) {
		// TODO Auto-generated method stub
		String id=putproduct.getId();
		int result=0;
		if(StringUtils.isEmpty(id)){
			//����
			result=iPutProductDao.addputProduct(putproduct);
		}else{
			//����
			result=iPutProductDao.updateputProduct(putproduct);
		}
		return result;
	}

	@Override
	public String deleteputProduct(String[] ids) {
		// TODO Auto-generated method stub
		if(ids.length==0){
			return "û��ѡ��";
		}
		List<JSONObject> result=iPutProductDao.queryifputProduct(ids);
		boolean flag=true;
		if(result!=null&&result.size()>0){
			String tempStr="";
			for(JSONObject json:result){
				String count=JSONUtils.getString(json, "count");
				if(!count.equals("0")){
					String productName=JSONUtils.getString(json, "productname");
					tempStr+=productName+"��"+count+" ��¼ \n";
					flag=false;
				}
			}
			if(!flag){
				tempStr+="����Ͷ����";
				return tempStr;
			}
		}
		if(flag){
			int num=iPutProductDao.deleteputProduct(ids);
			return "��ɾ��"+num+"��¼";
		}
		return "�ڲ��쳣";
	}


}
