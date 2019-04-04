package com.hik.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.app.entity.DEVICE;
import com.hik.framework.controller.ExportBaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IDeviceMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/devicemangercontroller")
public class DeviceMangerController extends ExportBaseController {
	
	@Autowired
	private IDeviceMangerService iDeviceMangerService;
	
    @RequestMapping("/devicemanger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	System.out.println("deviceManger...............");
    	return "busManger/deviceManger";
    }
    
	/**
	 * 分页查询设备信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querydevicemanger")
	@HikLog(content="分页查询设备信息",curd=LogCommon.QUERY)
	public void queryroutemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			log.info("<>>>>>>searchStr:"+searchStr);
			Page page = iDeviceMangerService.queryDeviceManger(start, limit, searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 新增或更新设备
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addorUpdatedevicemanger")
	@HikLog(content="新增更或新设备信息",curd=LogCommon.NEWADD)
	public void addorUpdatedeviceManger(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		int _id=0;"id","name","address","createtime","creator","modifier","modifytime","mark"
		
//			Ext.getCmp('_id').setValue(tempdata.id);
//				Ext.getCmp('_equipmentid').setValue(tempdata.equipmentid);
//				Ext.getCmp('_apmac').setValue(tempdata.apmac); 
//				 Ext.getCmp('_reporttime').setValue(tempdata.reporttime); 
//				 Ext.getCmp('_routeid').setValue(tempdata.routeid); 
//				 Ext.getCmp('_busid').setValue(tempdata.busid); 
//				 Ext.getCmp(' _mark').setValue(tempdata.mark);
		String id=request.getParameter("id");
//		String name = request.getParameter("name");
//		String state = request.getParameter("state");
		String equipmentid=request.getParameter("equipmentid");
		String apmac=request.getParameter("apmac");
		String mark = request.getParameter("mark");
		String state = request.getParameter("state");
		String vehicleid=request.getParameter("vehicleid");
//		String isonline=request.getParameter("isonline");
//		String lastonlinetime=request.getParameter("lastonlinetime");
//		String lastreporttime=request.getParameter("lastreporttime");
		String reporttime=request.getParameter("reporttime");
//		String var=request.getParameter("var");
		String speed=request.getParameter("speed");
		String timeout=request.getParameter("timeout");
//		String 
		try{	
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			String dateTime=DateUtil.getCurrentDate();
			DEVICE  device = new DEVICE(id, equipmentid, apmac, vehicleid, null, null,null, reporttime,state, userId, userId,dateTime, dateTime, mark, null, speed, timeout);
			int result=iDeviceMangerService.addorUpdateDeviceManger(device);
			String TIP=id==null?UPDATE_SUCCESS_INFO:QUERY_SUCCESS_INFO;
			this.setResultInfo(TIP, result);
	    } catch (Exception e) {
			e.printStackTrace();
			String TIP2=id==null?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO;
			this.setResultInfo(id==null?UPDATE_FAILURE_INFO:QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 删除设备
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletedevicemanger")
	@HikLog(content="删除设备信息",curd=LogCommon.DELETE)
	public void deletedevicemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
//			String[] ids=request.getParameterValues("ids");
			String ids = request.getParameter("ids");
			String[] str = ids.split(",");
			String result=iDeviceMangerService.deleteDeviceManger(str);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	@RequestMapping("/exportdevicemanger")
	@HikLog(content="导出设备信息",curd=LogCommon.QUERY)
	public void exportdevicemanger(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String _start=request.getParameter("start");
			String _limit=request.getParameter("limit");
			int start=Integer.parseInt(_start);
			int limit=Integer.parseInt(_limit);
			String searchStr=request.getParameter("searchStr");
			String title=request.getParameter("title");
			Object obj = request.getParameter("rowtitles");
			JSONObject rowtitles=JSONObject.fromObject(obj);
			log.info("<>>>>>>:"+title+" rowtitles:"+rowtitles);
			Page page = iDeviceMangerService.queryDeviceManger(start, limit, searchStr);
			List<JSONObject> resultList = page.getElementList();
			export(response, resultList, rowtitles, title);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return;
	 }
	
	
	
	@RequestMapping("/queryDevices")
	@HikLog(content="查询设备信息",curd=LogCommon.QUERY)
	public void queryDevices(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String searchStr=request.getParameter("equipmentids");
			String[] s = searchStr.split(",");
			List equipmentids = Arrays.asList(s);
			List result = iDeviceMangerService.queryDevices(equipmentids);
			this.returnResponse(response, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
//	@RequestMapping("/setdevicegpssupport")
//	@HikLog(content="设置设备信息",curd=LogCommon.QUERY)
//	public void setdevicegpssupport(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		try{
//			String equipments=request.getParameter("equipmentids");
//			String flag=request.getParameter("flag");
//			log.info("设置设备信息===>:"+equipments+"   FLAG:::::=>"+flag);
//			JSONObject json=JSONObject.fromObject(equipments);
//			Iterator<String> keys = json.keys();  
//			while (keys.hasNext()) {
//				String equipmentid=keys.next();
//				String times=json.getString(equipmentid);
//				iDeviceMangerService.setDeviceGPSsupport(equipmentid, Integer.parseInt(times),Integer.parseInt(flag));//setdevicegpssupport(start, limit, searchStr);				
//			}
//	    } catch (Exception e) {
//			e.printStackTrace();
//		}
//		return;
//	 }
//	@RequestMapping("/setdevicegpssupportall")
//	@HikLog(content="设置设备信息",curd=LogCommon.QUERY)
//	public void setdevicegpssupportall(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		try{
//				String flag=request.getParameter("flag");
//				iDeviceMangerService.setDeviceGPSsupport(flag);//setdevicegpssupport(start, limit, searchStr);				
//	    } catch (Exception e) {
//			e.printStackTrace();
//		}
//		return;
//	 }
}
