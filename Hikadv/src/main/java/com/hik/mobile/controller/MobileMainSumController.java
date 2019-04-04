package com.hik.mobile.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.interceptor.HikLog;
import com.hik.mobile.entity.ACCTYPE;
import com.hik.mobile.service.IMainSumService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mobile")
public class MobileMainSumController extends BaseController {
	// IMainSumDao iMainSumDao;
	// MainSumServiceImpl mainservice;
	@Autowired
	IMainSumService mainservice;

	@RequestMapping("/mainSum")
	@HikLog(content = "��ҳ���ܲ�ѯ", curd = LogCommon.QUERY)
	public void mainSum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = request.getParameter("userid");
		String adverCode = request.getParameter("advercode");
		String advCountType = request.getParameter("advcuttype");
		if (StringUtils.isBlank(userid)) {
			this.setResultInfo(QUERY_FAILURE_INFO, "�û���Ϊ��");
		} else {

			if (StringUtils.isBlank(adverCode)) {
				adverCode = "";
			}
			if (StringUtils.isBlank(advCountType)) {
				// Ĭ�ϰ���CPT�Ʒ�
				advCountType = ACCTYPE.getName(1);
			} else {
				advCountType = ACCTYPE.getName(Integer.valueOf(advCountType));
			}

			// JSONObject result = new JSONObject();
			try {
				// // ���û���
				// JSONObject totalUsers =
				// mainservice.getTolUser(userid).get(0);
				// // �����Ͷ�ŵ���·��
				// JSONObject advTotalRute =
				// mainservice.getTotalAdServing(userid).get(0);
				// // ��������Ͷ������
				// JSONObject advTotal = mainservice.getAdvCount(userid).get(0);

				List<JSONObject> rst = mainservice.getTolUser(userid);
				rst.addAll(mainservice.getTotalAdServing(userid));
				rst.addAll(mainservice.getAdvCount(userid));

				this.setResultInfo(QUERY_SUCCESS_INFO, rst);
			} catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, "���ݻ�ȡ�쳣" + e.toString());
			}
			this.returnResponse(response, this.getResultInfo());
			return;
		}
	}

	@RequestMapping("/mainChart")
	@HikLog(content = "��ҳͼ�����ݲ�ѯ", curd = LogCommon.QUERY)
	public void mainChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = request.getParameter("userid");
		String startDate = request.getParameter("start");
		String endDate = request.getParameter("end");
		if (StringUtils.isBlank(userid)) {
			this.setResultInfo(QUERY_FAILURE_INFO, "�û���Ϊ��");
		} else if (DateUtil.getStrToDate(startDate) == null && DateUtil.getStrToDate(endDate) == null) {
			this.setResultInfo(QUERY_FAILURE_INFO, "���ڸ�ʽ����");
		} else {

			try {
				List<JSONObject> rst = mainservice.getChartOfSurfNetSum(userid, startDate, endDate);
				// rst.addAll(mainservice.getTotalAdServing(userid));

				this.setResultInfo(QUERY_SUCCESS_INFO, rst);
			} catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(QUERY_FAILURE_INFO, "���ݻ�ȡ�쳣" + e.toString());
			}
		}
		this.returnResponse(response, this.getResultInfo());
		return;

	}

	@RequestMapping("/getupdateinfo")
	public void checkupdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String type = request.getParameter("clienttype");
			if (StringUtils.isBlank(type)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "�ͻ������Ͳ���Ϊ��");
			} else {

				List info = mainservice.getUpdateInfo(type);

				this.setResultInfo(QUERY_SUCCESS_INFO, info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	
	@RequestMapping("/getAdUsersHisTrend")
	public void getAdUsersHisTrend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String sdate = request.getParameter("sdate");
			String edate = request.getParameter("edate");
			String userid = request.getParameter("userid");
			if (StringUtils.isBlank(userid)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "���ȵ�¼����в�ѯ");
			} else {
					
//				List info = mainservice.getUpdateInfo(type);
//				this.setResultInfo(QUERY_SUCCESS_INFO, info);
				List<JSONObject> list=mainservice.getAdUsersHisTrend(sdate, edate, userid);
				this.setResultInfo(QUERY_SUCCESS_INFO, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	@RequestMapping("/getAdExpoHisTrend")
	public void getAdExpoHisTrend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String sdate = request.getParameter("sdate");
			String edate = request.getParameter("edate");
			String userid = request.getParameter("userid");
			if (StringUtils.isBlank(userid)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "���ȵ�¼����в�ѯ");
			} else {

				List<JSONObject> list=mainservice.getAdExpoHisTrend(sdate, edate, userid);
				this.setResultInfo(QUERY_SUCCESS_INFO, list);

				this.setResultInfo(QUERY_SUCCESS_INFO, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	//getAdClickHisTrend
	@RequestMapping("/getAdClickHisTrend")
	public void getAdClickHisTrend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String sdate = request.getParameter("sdate");
			String edate = request.getParameter("edate");
			String userid = request.getParameter("userid");
			if (StringUtils.isBlank(userid)) {
				this.setResultInfo(QUERY_FAILURE_INFO, "���ȵ�¼����в�ѯ");
			} else {

				List<JSONObject> list=mainservice.getAdClickHisTrend(sdate, edate, userid);
				this.setResultInfo(QUERY_SUCCESS_INFO, list);

				this.setResultInfo(QUERY_SUCCESS_INFO, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
}
