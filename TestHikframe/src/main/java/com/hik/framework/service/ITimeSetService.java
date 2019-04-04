package com.hik.framework.service;

import java.util.List;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.app.entity.TIMESETTING;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface ITimeSetService {
		/**
		 * @return
		 */
			public List getTimeSet();
			
		/**
		 * 
		 * @param user 需要编辑的数据
		 */
			public int editTimeSet(TIMESETTING user);
		/**
		 * 
		 * @param user 添加的数据
		 */
			int addTimeSet(TIMESETTING user);
		/**
		 * 
		 * @param usid 删除的数据索引
		 */
			int deleteTimeSet(String[] id);
			/**
			 * 
			 * @param smscontent
			 * @return
			 */
			int udpateSMS(String smscontent);
			/**
			 * 查询短信设置
			 * @return
			 */
			List querySMS();
			/**
			 * 查询皮肤设置
			 * @return
			 */
			List querySKIN();
			/**
			 * 新增皮肤
			 * @return
			 */
			int insertSKIN(String id,String skinname,String name,String descr);
			/**
			 * 删除皮肤
			 * @param id
			 * @return
			 */
			int delelte(String[] id);
			
			List getSKINById(String id);
			/**
			 * 查询模版属性信息
			 * @return
			 */
			public List queryModelPro();
			/**
			 * 删除模版属性值
			 * @param id
			 * @return
			 */
			public int deleteModelPro(String[] id);
			/**
			 * 查询广告属性
			 * @return
			 */
			public List queryAdvPro(String pid,String leve);
			/**
			 * 删除广告属性设置
			 * @param id
			 * @return
			 */
			public int deleteAdvpro(String[] id);
			/**
			 * 新增模版属性
			 * @param cid ID
			 * @param name 名称
			 * @param descr 描述
			 * @param userid 创建人
			 * @return
			 */
			public int saveModelPro(String id,String cid,String name,String descr,String userid);
			/**
			 * 新增广告模版属性
			 * @param id ID
			 * @param pid 父类ID
			 * @param name 名称
			 * @param descr 描述
			 * @param userid 创建人
			 * @return
			 */
			public int saveadvPro(String id,String pid,String name,String userid,String leve);
			/**
			 * 
			 * @param id
			 * @param cid
			 * @param name
			 * @param interval
			 * @param descr
			 * @param userid
			 * @return
			 */
			public int savePutStragerPro(String id,String cid,String name,String interval,String descr,String userid);
			
			public List queryPutStragerPro();
			
			public int deletePutStragerPro(String id);
			
			/**
			 * 更新用户认证方式
			 * @param state
			 * @return
			 */
			public int updateUserAuth(String state);
			
			public List queryUserAuth();
}
