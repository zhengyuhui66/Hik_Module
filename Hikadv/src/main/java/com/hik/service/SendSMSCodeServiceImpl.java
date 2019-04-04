package com.hik.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.SHOW_COUNT_LOG;
import com.hik.dao.BaseService;
import com.hik.dao.SendSMSCodeDao;
import com.hik.framework.dao.ITimeSetDao;
import com.hik.framework.service.IUserService;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.Page;
import com.hik.util.SFTP;
import com.hik.util.SMSUtils;
import com.jcraft.jsch.ChannelSftp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class SendSMSCodeServiceImpl  extends BaseService  implements SendSMSCodeService{
	protected Log log = LogFactory.getLog(this.getClass()); 
	@Autowired
	private SendSMSCodeDao codeDao;
	@Autowired
	private ITimeSetDao iTimeSetDao;
	@Transactional(transactionManager="smsDatatransactionManager")
	public void sendSMSCode(String phone,String code) {
		// TODO Auto-generated method stub
		log.info("接收到的code:"+code);
		List smss=iTimeSetDao.querySMS();
		String smsContent=null;
		if(!smss.isEmpty()&&smss.size()>0){
			smsContent=smss.get(0).toString();
		}
		String _smsContent=smsContent.replace("XXXXXX",code);
		log.info("准备发送的短信是==>"+_smsContent);
//		codeDao.sendSMSCodeDao(phone,_smsContent,DateUtil.getCurrentDate(),1);
	}
	@Transactional(readOnly=true)
	public String queryBusId(String mac,String phone,String phonemac,HttpSession session) {
		// TODO Auto-generated method stub
		String URL=null;
		//查询当前的暂停情况，若为0则为暂停
		List pause = codeDao.queryPausedByMac(mac);
		if(pause!=null&&pause.size()>0){
			String pauseFlag=pause.get(0).toString();
			if("0".equals(pauseFlag)){
				return URL;
			}
		}
		List list= codeDao.queryBusId(mac);
		JSONArray jsons = JSONArray.fromObject(list);
		if(jsons!=null&&jsons.size()>0){
			session.setAttribute("showCountLogSession", jsons.toString());
			JSONObject json=jsons.getJSONObject(0);
			URL=json.get("url").toString();
			String modelid=json.get("modelid").toString();
			String modelskin=json.get("modelskin").toString();
			URL+="?modelid='"+modelid+"'&modelskin="+modelskin+"&";
			for(int i=0;i<jsons.size();i++){
				JSONObject jsonl=jsons.getJSONObject(i);
				String materPath=jsonl.get("materPath").toString();
				String modelmodeid=jsonl.get("modelmodeid").toString();
				String advertid=jsonl.get("advertId").toString();
				String materid=jsonl.getString("materid").toString();
				String advertUrl=jsonl.getString("advertUrl").toString();
				URL+="materurl"+modelmodeid+"='"+materPath+"'&";
				URL+="advertid"+modelmodeid+"='"+advertid+"'&";
			}
			URL=URL.substring(0, URL.length()-1);
		}
		return URL;
	}
	@Transactional(readOnly=true)
	public String queryLoginedBusId(String mac,String phone,String phonemac){
		// TODO Auto-generated method stub
		
		String URL=null;
		List pause = codeDao.queryLoginedPausedByMac(mac);
		if(pause!=null&&pause.size()>0){
			String pauseFlag=pause.get(0).toString();
			if("0".equals(pauseFlag)){
				return URL;
			}
		}
		
		List  list2 = codeDao.getLineIdByMac(mac);
		
		String lineid=(list2==null||list2.size()==0?"":list2.get(0).toString());
		List list= codeDao.queryLoginedBusId(mac);
		JSONArray jsons = JSONArray.fromObject(list);
		if(jsons!=null&&jsons.size()>0){
			JSONObject json=jsons.getJSONObject(0);
			URL=json.get("url").toString();
			String modelid=json.get("modelid").toString();
			String modelskin=json.get("modelskin").toString();
			URL+="?modelid='"+modelid+"'&modelskin"+modelskin+"&";
			for(int i=0;i<jsons.size();i++){
				JSONObject jsonl=jsons.getJSONObject(i);
				String materPath=jsonl.get("materPath").toString();
				String modelmodeid=jsonl.get("modelmodeid").toString();
				String advertid=jsonl.get("advertId").toString();
				String materid=jsonl.getString("materid").toString();
				String advertUrl=jsonl.getString("advertUrl").toString();
				URL+="materurl"+modelmodeid+"='"+materPath+"'&";
				URL+="advertid"+modelmodeid+"='"+advertid+"'&";
				SHOW_COUNT_LOG logg = new SHOW_COUNT_LOG(null,DateUtil.getCurrentDate(), queryBusiByMac(mac), mac, phone, phonemac, modelid, modelmodeid, materid, advertUrl,advertid,lineid);
				codeDao.saveShowCount(logg);
			}
			URL=URL.substring(0, URL.length()-1);
		}
//		int wifiAuthId=codeDao.getWifiAuthId();
		log.info("**************phonemac_value:"+phonemac);
		codeDao.saveRecordNet(phonemac,phone,mac);
		return URL;
	}
	@Transactional(readOnly=true)
	public String queryAdvertUrl(String advertId){
		// TODO Auto-generated method stub
		List<JSONObject> list = codeDao.queryAdvertUrl(advertId);
		if(list!=null&&list.size()>0){
			JSONObject json = list.get(0);
			String materId=json.get("materId").toString();
			String advertUrl=json.get("advertUrl").toString();
		}
		return null;
	}
	@Transactional(readOnly=true)
	private String queryBusiByMac(String apmac){
		List busidList = codeDao.queryBusiByMAC(apmac);	
		String busId="";
		if(busidList!=null&&busidList.size()>0){
			busId=busidList.get(0).toString();
		}
		return busId;
	}
	@Transactional
	public String saveClickCount(CLICK_COUNT_LOG logg,String advertId) {
		// TODO Auto-generated method stub
		String mac=logg.getApmac();
		String url=null;
		List  list2 = codeDao.getLineIdByMac(mac);
		String lineid=(list2==null?"":list2.get(0).toString());
		logg.setLineid(lineid);
		List<JSONObject> adverUrlList = codeDao.queryAdvertUrl(advertId);
		log.info("查询当前广告的具体信息"+JSONArray.fromObject(adverUrlList));
		if(adverUrlList!=null&&adverUrlList.size()>0){
			JSONObject json=adverUrlList.get(0);
			String advertURL=json.get("advertUrl").toString();
			log.info("得到当前广告信息的URL"+advertURL);
			String materid=json.get("materId").toString();
			logg.setAdvert_url(advertURL);
			logg.setMaterid(materid);
			url=advertURL;
		}
		logg.setBusid(queryBusiByMac(mac));
		
		int result=codeDao.saveClickCount(logg);
		return url;
	}
	@Transactional(readOnly=true)
	public boolean toVaild(String code, String phone) {
		// TODO Auto-generated method stub
			return true;
	}
	@Transactional
	public void showCount(JSONArray jsons,String mac,String phone,String phonemac) {
		// TODO Auto-generated method stub
		if(jsons!=null&&jsons.size()>0){
			List  list = codeDao.getLineIdByMac(mac);
			String lineid=(list==null?"":list.get(0).toString());
			JSONObject json=jsons.getJSONObject(0);
			String modelid=json.get("modelid").toString();
			for(int i=0;i<jsons.size();i++){
				JSONObject jsonl=jsons.getJSONObject(i);
				String materPath=jsonl.get("materPath").toString();
				String modelmodeid=jsonl.get("modelmodeid").toString();
				String advertid=jsonl.get("advertId").toString();
				String materid=jsonl.getString("materid").toString();
				String advertUrl=jsonl.getString("advertUrl").toString();
				SHOW_COUNT_LOG logg = new SHOW_COUNT_LOG(null,DateUtil.getCurrentDate(), queryBusiByMac(mac), mac, phone, phonemac, modelid, modelmodeid, materid, advertUrl,advertid,lineid);
				codeDao.saveShowCount(logg);
			}
		}
	}
}
