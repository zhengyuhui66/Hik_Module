package com.hik.mobile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.interceptor.HikLog;
import com.hik.service.ShowClickService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mobile/analysis")
public class MobileAnalysisController extends BaseController{
	
	@Autowired
	ShowClickService iShowClicktService;
	private String endTimedetail = " 00:00:00";
	/**
	 * 查询广告点击信息
	 * 开始时间 结束时间 线路 车辆 广告id 物料id
	 * @param RoldId
	 * @return
	 */
	@RequestMapping("/queryClickInfo")
	@HikLog(content="查询广告点击统计信息",curd=LogCommon.QUERY)
	public void queryClickInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			String roldId = request.getParameter("roldId");
			String busId = request.getParameter("busId");
			String advertid = request.getParameter("advertid");
			String materid = request.getParameter("materid");
			List<JSONObject> result = iShowClicktService.queryClickInfo(stime + endTimedetail, etime + endTimedetail,
					roldId, busId, advertid, materid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};
	
	/**
	 * 查询车辆信息为粒度的信息
	 * 移动使用
	 * @param RoldId
	 * @return
	 */
	@RequestMapping("/queryShowInfo")
	@HikLog(content="查询广告展示统计信息",curd=LogCommon.QUERY)
	public void queryShowInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			String roldId = request.getParameter("roldId");
			String busId = request.getParameter("busId");
			String advertid = request.getParameter("advertid");
			String materid = request.getParameter("materid");
			List<JSONObject> result = iShowClicktService.queryShowInfo(stime + endTimedetail, etime + endTimedetail,
					roldId, busId, advertid, materid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};
	
	/**
	 * 根据广告得到物料
	 * 
	 * @return
	 */
	@RequestMapping("/getMaterByAdvertId")
	@HikLog(content="根据广告Id查询物料",curd=LogCommon.QUERY)
	public void getMaterByAdvertId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String advertId = request.getParameter("advertId");
			List<JSONObject> result = iShowClicktService.getMaterByAdvertId(advertId);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};
}
