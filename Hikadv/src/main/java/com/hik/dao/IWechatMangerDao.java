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
	 * 分页查询
	 * @param start  开始条数
	 * @param limit 每页几条
	 * @param searchStr 条件模糊查询
	 * @return
	 */
	Page queryForPage(int start,int limit,String searchStr);
	/**
	 * 新增
	 * @param pricondition
	 * @return
	 */
	int addWechat(WechatManger wechatManger);
	/**
	 * 更新
	 * @param pricondition
	 * @return
	 */
	int updateWechat(WechatManger wechatManger);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int deleteWechat(String[] id);
	
	List getIfWeChatInMMPP(String[] id);
	
	
	List queryMMPP();
	
	int updateAccessToken(List<Object[]> obj);
	
	public List getId();
}
