package com.hik.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IMaterialService;
import com.hik.service.ShowClickService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/showClickController")
public class ShowClickController extends BaseController {
	@Autowired
	private ShowClickService showClickService;
	private String endTimedetail = " 00:00:00";
	
	@RequestMapping("/clickTotal")
    public String clickTotal(HttpServletRequest request,HttpServletResponse response){
    	return "showEcharts/clickTotal";
    }
	@RequestMapping("/showTotal")
    public String showTotal(HttpServletRequest request,HttpServletResponse response){
    	return "showEcharts/showTotal";
    }

	
//	/**
//	 * 得到所有的广告
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/getAllAdvert")
//	public void getAllAdvert(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		try {
//			List<JSONObject> result = showClickService.getAllAdvert();
//			this.setResultInfo(QUERY_SUCCESS_INFO, result);
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//		}
//		this.returnResponse(response, this.getResultInfo());
//	};

	/**
	 * 根据广告得到物料
	 * 
	 * @return
	 */
	@RequestMapping("/getMaterByAdvertId")
	public void getMaterByAdvertId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String advertId = request.getParameter("advertId");
			List<JSONObject> result = showClickService.getMaterByAdvertId(advertId);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};

	/**
	 * 得到所有的线路
	 */
	@RequestMapping("/getAllRold")
	public void getAllRold(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> result = showClickService.getAllRold();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	}

	/**
	 * 根据线路ID得到当前线路所有的公交车
	 * 
	 * @param RoldId
	 * @return
	 */
	@RequestMapping("/getBusIdByRoldId")
	public void getBusIdByRoldId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String RoldId = request.getParameter("roldId");
			List<JSONObject> result = showClickService.getBusIdByRoldId(RoldId);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};

	/**
	 * 查询车辆信息为粒度的信息
	 * 
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
			List<JSONObject> result = showClickService.queryShowInfo(stime + endTimedetail, etime + endTimedetail,
					roldId, busId, advertid, materid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};

	/**
	 * 根据广告物料为信息的
	 * 
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
			List<JSONObject> result = showClickService.queryClickInfo(stime + endTimedetail, etime + endTimedetail,
					roldId, busId, advertid, materid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};
	@RequestMapping("/queryRouteSufCount")
	@HikLog(content="查询广告路线统计信息",curd=LogCommon.QUERY)
	public void queryRouteSufCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String name = request.getParameter("name");
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page page = showClickService.queryRouteSufCount(name, stime, etime,start,limit);
			this.returnResponse(response, JSONObject.fromObject(page));
//		 this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	}
	@RequestMapping("/queryRouteSufCountTotal")
	@HikLog(content="查询广告路线所有上网人数统计信息",curd=LogCommon.QUERY)
	public void queryRouteSufCountTotal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String name = request.getParameter("name");
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			log.info("接收到的name:"+name+" stime:"+stime+" etime"+etime);
			List<JSONObject> page = showClickService.queryRouteSufCountTotal(name, stime, etime);
//			this.returnResponse(response, JSONObject.fromObject(page));
			this.setResultInfo(QUERY_SUCCESS_INFO, page);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	}
	
	/**
	 * 根据广告物料为信息的
	 * 
	 * @param RoldId
	 * @return
	 */
	@RequestMapping("/queryBusSufCount")
	@HikLog(content="查询广告车辆统计信息",curd=LogCommon.QUERY)
	public void queryBusSufCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String name = request.getParameter("name");
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			 Page page = showClickService.queryBusSufCount(name, stime, etime,start,limit);
//			 this.setResultInfo(QUERY_SUCCESS_INFO, result);
			this.returnResponse(response, JSONObject.fromObject(page));
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};
	
	/**
	 * 根据广告物料为信息的
	 * 
	 * @param RoldId
	 * @return
	 */
	@RequestMapping("/queryBusSufCountTotal")
	@HikLog(content="查询广告车辆统计信息",curd=LogCommon.QUERY)
	public void queryBusSufCountTotal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String name = request.getParameter("name");
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
//			int start = getStartOrLimit(request, "start");
//			int limit = getStartOrLimit(request, "limit");
			log.info("接收到的name:"+name+" stime:"+stime+" etime"+etime);
			 List<JSONObject> page = showClickService.queryBusSufCountTotal(name, stime, etime);
			 this.setResultInfo(QUERY_SUCCESS_INFO, page);
//			this.returnResponse(response, JSONObject.fromObject(page));
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
	};
//	/**
//	 * 根据广告物料为信息的
//	 * 
//	 * @param RoldId
//	 * @return
//	 */
//	@RequestMapping("/queryRouteSufCount")
//	@HikLog(content="查询广告路线统计信息",curd=LogCommon.QUERY)
//	public void queryRouteSufCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		try {
//			String name = request.getParameter("name");
//			String stime = request.getParameter("stime");
//			String etime = request.getParameter("etime");
//			int start = getStartOrLimit(request, "start");
//			int limit = getStartOrLimit(request, "limit");
//			Page page = showClickService.queryRouteSufCount(name, stime, etime, start, limit);
//			this.returnResponse(response, JSONObject.fromObject(page));
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//			this.returnResponse(response, this.getResultInfo());
//		}
//	};
// 
//	/**
//	 * 根据广告物料为信息的
//	 * 
//	 * @param RoldId
//	 * @return
//	 */
//	@RequestMapping("/queryBusSufCount")
//	@HikLog(content="查询广告车辆统计信息",curd=LogCommon.QUERY)
//	public void queryBusSufCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		try {
//			String name = request.getParameter("name");
//			String stime = request.getParameter("stime");
//			String etime = request.getParameter("etime");
//			int start = getStartOrLimit(request, "start");
//			int limit = getStartOrLimit(request, "limit");
//			Page page = showClickService.queryBusSufCount(name, stime, etime, start, limit);
//			this.returnResponse(response, JSONObject.fromObject(page));
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, null);
//			this.returnResponse(response, this.getResultInfo());
//		}
//	};

}
