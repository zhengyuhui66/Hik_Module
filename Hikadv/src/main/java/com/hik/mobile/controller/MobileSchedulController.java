package com.hik.mobile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.dao.IAdvertPlanDao;
import com.hik.dao.ICommonDao;
import com.hik.dao.IPutScheduleDao;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.Page;
import com.hik.mobile.dao.ISchedulDao;
import com.hik.service.IPutScheduleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mobile")
public class MobileSchedulController extends BaseController {
//	@Autowired
//	ISchedulService iSchedulService;
	@Autowired
	IAdvertPlanDao iAdvertPlanDao;
	@Autowired
	private IPutScheduleService iputScheduleService;
	@Autowired
	ICommonDao iCommonDao;
	@Autowired
	ISchedulDao iSchedulDao;
	@Autowired
	IPutScheduleDao iPutSchedulDao;

	@RequestMapping("/schedullist")
	public void getSchedul(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
			this.setResultInfo(QUERY_FAILURE_INFO, "时间间隔未指定");
		} else if (startDate.compareToIgnoreCase(endDate) > 0) {
			this.setResultInfo(QUERY_FAILURE_INFO, "起始时间不能大于结束时间");
		} else {
			try {
				List<JSONObject> rst = iSchedulDao.getSchedul(startDate, endDate);

				this.setResultInfo(QUERY_SUCCESS_INFO, rst);
			} catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
			}
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	}

	@RequestMapping("/scheduldetail")
	public void getSchedulByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filterDate = request.getParameter("filterdate");
		if (StringUtils.isBlank(filterDate)) {
			this.setResultInfo(QUERY_FAILURE_INFO, "查询日期未指定");
		} else {
			try {
				List<JSONObject> rst = iSchedulDao.getSchedulByDate(filterDate);
				this.setResultInfo(QUERY_SUCCESS_INFO, rst);
			} catch (Exception e) {
				this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
			}
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	}

//	@RequestMapping("/allrute")
//	public void getAllRute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			List<JSONObject> rst = iAdvertPlanDao.queryRoadInfo();
//			this.setResultInfo(QUERY_SUCCESS_INFO, rst);
//		} catch (Exception e) {
//			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
//		}
//		this.returnResponse(response, this.getResultInfo());
//		return;
//	}

	@RequestMapping("/carByLine")
	public void getAllCarByRute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String lineId = request.getParameter("lineid");
		if (StringUtils.isBlank(lineId)) {
			this.setResultInfo(QUERY_FAILURE_INFO, "线路ID不能为空");
		} else {
			List<JSONObject> rst=null;
			try {
			if (lineId.equals("-1")) {
				rst=iSchedulDao.getAllCar();
			}else{
				 rst = iSchedulDao.getCarByLine(lineId);
			}
				this.setResultInfo(QUERY_SUCCESS_INFO, rst);
			} catch (Exception e) {
				this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
			}
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	}

	@RequestMapping("/allLine")
	public void getAllLine(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> rst = iSchedulDao.getAllLine();
			this.setResultInfo(QUERY_SUCCESS_INFO, rst);
		} catch (Exception e) {
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
	}

	@RequestMapping("/schedulTimePie")
	public void getAllTimePie(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> rst = iCommonDao.querytimeSet();
			this.setResultInfo(QUERY_SUCCESS_INFO, rst);
		} catch (Exception e) {
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
	}

	@RequestMapping("/schedulPeriod")
	public void getAllPeriod(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> rst = iCommonDao.queryAuthCyc();
			this.setResultInfo(QUERY_SUCCESS_INFO, rst);
		} catch (Exception e) {
			this.setResultInfo(QUERY_FAILURE_INFO, "数据获取异常" + e.toString());
		}
		this.returnResponse(response, this.getResultInfo());
	}
	@RequestMapping("/schedulFilterInPage")
	public void getSchedulResult(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			String userid=request.getParameter("userid");
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String stime=request.getParameter("stime");
			String etime=request.getParameter("etime");
			String timeslice2=request.getParameter("timeslice");
			String orderby=request.getParameter("orderby");
			String[] timeslice=null;
			if(!StringUtils.isEmpty(timeslice2)){
				timeslice=timeslice2.split(",");
			}
			
			String period=request.getParameter("period");
			String[] periodArr=null;
			if(!StringUtils.isEmpty(period)){
				periodArr=period.split(",");
			}
			String busid=request.getParameter("busid");
			String routeid=request.getParameter("routeid");
			HttpSession session = request.getSession();
			Page page=iSchedulDao.getSchedulPut4Mobile(start, limit, stime, etime, timeslice, periodArr, routeid, busid, userid,orderby);
			this.setResultInfo(QUERY_SUCCESS_INFO, JSONObject.fromObject(page));
			
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		this.returnResponse(response,this.getResultInfo());
		return;
		
	}
	
	
	
}
