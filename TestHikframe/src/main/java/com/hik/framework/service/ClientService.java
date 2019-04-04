package com.hik.framework.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ClientService {
	/**
	 * ��ҳ��ѯ�ͻ�����Ϣ
	 * @return
	 */
	public Page queryClientInfo(int start,int limit);
	/**
	 * ���������İ汾
	 * @return
	 */
	public int saveClient(String path,MultipartFile file,HttpServletRequest request,String version,String versionName,String versiondesc,String userid);
	
}
