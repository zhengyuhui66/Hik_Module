package com.hik.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hik.app.entity.WechatManger;
import com.hik.dao.BaseService;
import com.hik.dao.IGetAdvertDao;
import com.hik.dao.IWechatMangerDao;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.Page;
import com.hik.util.HttpClientUtil;
import net.sf.json.JSONObject;


@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class IWechatMangerServiceImpl  extends BaseService  implements IWechatMangerService{

	@Autowired
	private IWechatMangerDao iWechatMangerDao;
	
	@Autowired
	private IGetAdvertDao iGetAdvertDao;
	
	@Override
	public Page queryForPage(int start, int limit, String searchStr){
		// TODO Auto-generated method stub
		return iWechatMangerDao.queryForPage(start, limit, searchStr);
	}

	@Override
	public int addorUpdateWechat(WechatManger wechatManger,Set<String> putconIdSet) {
		// TODO Auto-generated method stub
		String id=wechatManger.getId();
		int result=0;
		if("".equals(id)){
			List ids = iWechatMangerDao.getId();
			id=ids.get(0).toString();
			//新增
			wechatManger.setId(id);
			result=iWechatMangerDao.addWechat(wechatManger);
			/**
			 * 保存新增广告优先投放条件
			 */
		}else{
			//更新
			result=iWechatMangerDao.updateWechat(wechatManger);
		}
		/**
		 * 保存新增广告优先投放条件
		 */
		if(putconIdSet!=null){
			Iterator<String> iterator=putconIdSet.iterator();
			while(iterator.hasNext()){
				String temp = iterator.next();
				if(StringUtils.isNotEmpty(temp)){
					iGetAdvertDao.saveAdvPutset(id,temp);					
				}
			}			
		}
		return result;
	}

	@Override
	public String deleteWechat(String[] id) {
		// TODO Auto-generated method stub
		if(id.length==0){
			return "没有选择";
		}
		List<JSONObject> inmmpp=iWechatMangerDao.getIfWeChatInMMPP(id);
		boolean flag=true;
		if(inmmpp!=null&&inmmpp.size()>0){
			String tempStr="";
			for(JSONObject json:inmmpp){
				String count=JSONUtils.getString(json, "count");
				if(!count.equals("0")){
					String productName=JSONUtils.getString(json, "name");
					tempStr+=productName+"有"+count+" 记录 \n";
					flag=false;
				}
			}
			if(!flag){
				tempStr+="在微信号产品中";
				return tempStr;
			}
		}
		if(flag){
			int result=iWechatMangerDao.deleteWechat(id);
			 for(int i=0;i<id.length;i++){
				 iGetAdvertDao.deleteAdvputSet(id[i]);			 
			 }
			 return "共删除"+result+"条记录";
		}
		
		return "内部异常";
	}

	@Override
	public List queryMMPP() {
		// TODO Auto-generated method stub
		return iWechatMangerDao.queryMMPP();
	}
	/**
	 * 定时任务
	 * @throws InterruptedException
	 */
	@Scheduled(fixedRate = 7000000)
	public void fixedRate() throws InterruptedException {
		List<JSONObject> list =iWechatMangerDao.queryMMPP();
		List<Object[]> params = new ArrayList<Object[]>();
		for(JSONObject json:list){
			String appid = JSONUtils.getString(json, "appid");
			String appsecret = JSONUtils.getString(json, "appsecret");
			HttpClientUtil h = new HttpClientUtil();
			String accessToken = h.requestByGetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret);
			Object[] obj = new Object[]{accessToken,appid,appsecret};
			params.add(obj);
		}
		iWechatMangerDao.updateAccessToken(params);
	}
}
