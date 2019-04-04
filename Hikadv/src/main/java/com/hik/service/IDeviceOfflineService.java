package com.hik.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CORP;
import com.hik.app.entity.DEVICE;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.app.entity.ROUTE;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IDeviceOfflineService {
/**
 * 分页查询设备上下线信息
 * @param start 开始条数
 * @param limit 每页几条
 * @param searchStr 筛选条件
 * @return
 */
	Page queryDeviceOffline(int start,int limit,String searchStr);
	void getOnline(String deviceid);
}
