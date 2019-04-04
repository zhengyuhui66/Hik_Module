package com.hik.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.VEHICLE;
import com.hik.framework.controller.BaseController;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IVehicleMangerService;
import com.hik.service.TimeMangerService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/vehicleManger")
public class VehicleMangerController extends ExportBaseController {

	@Autowired
	private IVehicleMangerService iVehicleMangerService;

	
	
	
    @RequestMapping("/vehicleManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "busManger/vehicleIndex";
    }
    
	/**
	 * 查询车辆信息列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryVehicleList")
	@HikLog(content = "查询车辆信息列表", curd = LogCommon.QUERY)
	public void queryVehicleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String UserId = CommonUtil.getUserId(request);
			int limit = getStartOrLimit(request, "limit");
			int start = getStartOrLimit(request, "start");
			String searchStr = request.getParameter("searchStr");
			Page page = iVehicleMangerService.queryVehicleList(UserId, start, limit, searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	}

	/**
	 * 查询单条车辆信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryVehicle")
	@HikLog(content = "查询车辆信息", curd = LogCommon.QUERY)
	public void queryListgtime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			if (id == null || StringUtils.isEmpty(id)) {
				this.setResultInfo(QUERY_SUCCESS_INFO, null);
			} else {
				List result = iVehicleMangerService.getVehicleDetail(id);
				this.setResultInfo(QUERY_SUCCESS_INFO, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}

	/**
	 * 保存或者新增车辆
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdateVehicle")
	@HikLog(content = "保存车辆信息", curd = LogCommon.NEWADD)
	public void saveOrupdatetime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			VEHICLE ve = new VEHICLE();
			String code = request.getParameter("code");
			String routename = request.getParameter("routename");
			String brand = request.getParameter("brand");
			String model = request.getParameter("model");
			String color = request.getParameter("color");
			String usedate = request.getParameter("usedate");
			String state = request.getParameter("state");
			String mark = request.getParameter("mark");
			String owercode = request.getParameter("owercode");
			String routeid = request.getParameter("routeid");
			String corpid = request.getParameter("corpid");
			String corpname = request.getParameter("corpname");
//			String ifadvert = request.getParameter("IFADVERT");
			String id = request.getParameter("id");
			ve.setCODE(code);
			ve.setROUTENAME(routename);
			ve.setBRAND(brand);
			ve.setMODEL(model);
			ve.setCOLOR(color);
			ve.setUSEDATE(usedate);
			ve.setMARK(mark);
			ve.setOWERCODE(owercode);
			ve.setROUTEID(routeid);
			ve.setCORPID(corpid);
			ve.setCORPNAME(corpname);
			ve.setID(id);
			//弃用这两个参数
			ve.setSTATE(state==null ? "1": state);
//			ve.setIFADVERT(ifadvert);

			HttpSession session = request.getSession();
			String userid = session.getAttribute("userId").toString();
			ve.setCREATOR(userid);
			ve.setMODIFIER(userid);

			int result = iVehicleMangerService.saveOrUpdateVehicle(ve);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	/**
	 * 设置线路参数信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/setting")
	@HikLog(content="设置参数",curd=LogCommon.UPDATE)
	public void setting(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
//			String[] ids=request.getParameterValues("id");
			String ids=request.getParameter("id");
			String speed = request.getParameter("speed");
			String timeout=request.getParameter("timeout");
			String[] idstr = ids.split(",");
			String result=iVehicleMangerService.setting(idstr,speed,timeout);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除车辆信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteVehicle")
	@HikLog(content = "删除车辆信息", curd = LogCommon.QUERY)
	public void deleteVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("ids");
			String[] ids = id.split(",");
			int result = iVehicleMangerService.deleteVehicle(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	
	@RequestMapping("/queryDeviceList")
	@HikLog(content = "查询车辆信息列表", curd = LogCommon.QUERY)
	public void queryDeviceList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			List list = iVehicleMangerService.queryDeviceList(id);
			this.returnResponse(response,list);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	}
	@RequestMapping("/exportVehiclemanger")
	@HikLog(content="导出车辆信息",curd=LogCommon.QUERY)
	public void exportroutemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
//			int start=getStartOrLimit(request, "start");
//			int limit=getStartOrLimit(request, "limit");
			String _start=request.getParameter("start");
			String _limit=request.getParameter("limit");
			int start=Integer.parseInt(_start);
			int limit=Integer.parseInt(_limit);
			String searchStr=request.getParameter("searchStr");
			String title=request.getParameter("title");
			Object obj = request.getParameter("rowtitles");
			JSONObject rowtitles=JSONObject.fromObject(obj);
			rowtitles.remove("null");
//			Page page = iVehicleMangerService.queryVehicleList(UserId, start, limit, searchStr);
			Page page = iVehicleMangerService.queryVehicleList("",start, limit, searchStr);
//			Page page = iPutProductService.queryputProduct(start, limit,searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }

}
