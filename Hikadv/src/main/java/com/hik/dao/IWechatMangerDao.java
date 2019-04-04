package com.hik.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.WechatManger;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IWechatMangerDao {
	/**
	 * ��ҳ��ѯ
	 * @param start  ��ʼ����
	 * @param limit ÿҳ����
	 * @param searchStr ����ģ����ѯ
	 * @return
	 */
	Page queryForPage(int start,int limit,String searchStr);
	/**
	 * ����
	 * @param pricondition
	 * @return
	 */
	int addWechat(WechatManger wechatManger);
	/**
	 * ����
	 * @param pricondition
	 * @return
	 */
	int updateWechat(WechatManger wechatManger);
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	int deleteWechat(String[] id);
	
	List getIfWeChatInMMPP(String[] id);
	
	
	List queryMMPP();
	
	int updateAccessToken(List<Object[]> obj);
	
	public List getId();
}
