package com.hik.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.Workers_Info;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IHomeService {
	/**
	 * 广告为单位的展示统计top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByAdv();
	/**
	 * 线路为单位的展示统计top10
	 * @return
	 */
	public List<JSONObject> getTop10ShowCountByRoute();
	/**
	 * 广告为单位的点击统计次数
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByAdv();
	/**
	 *线路为单位的点击统计次数
	 * @return
	 */
	public List<JSONObject> getTop10ClickCountByRoute();
	/**
	 * 全局统计
	 * @return
	 */
	public List<JSONObject> getTotalInfo();
}
