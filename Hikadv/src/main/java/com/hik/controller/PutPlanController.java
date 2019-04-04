package com.hik.controller;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.LogCommon;
import com.hik.interceptor.HikLog;
import com.hik.service.ICommonService;
import com.hik.service.IPutPlanService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/ppr")
public class PutPlanController  extends BaseController{
	
	@Autowired
	private IPutPlanService iputPlanService;
	@Autowired
	private ICommonService iCommonService;
	@RequestMapping("/putPlanRoute")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "putManger/putPlan";
    }
	
	@RequestMapping("/queryputProduct")
	@HikLog(content="ҳ��ѯ���Ͷ������",curd=LogCommon.QUERY)
	public void queryputProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("authid");
			List result = iputPlanService.getProduct(id);
			this.returnResponse(response,result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	
	@RequestMapping("/queryBusInfo")
	@HikLog(content="ҳ��ѯ���Ͷ������",curd=LogCommon.QUERY)
	public void queryBusInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String routeId=request.getParameter("routeId");
			List<JSONObject> result = iputPlanService.getBus(routeId);
			this.returnResponse(response,result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
//	@RequestMapping("/putPlan")
//	public void putPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		try{
//			String stime=request.getParameter("stime");
//			String etime=request.getParameter("etime");
//			String[] timeslice=request.getParameterValues("timeslice");
//			String apid=request.getParameter("apid");
//			String[] apids=apid.split(",");
//			List<JSONObject> list = iCommonService.queryAuthCyc();
//			JSONObject jsonObject = new JSONObject();
//			for(int i=0;i<list.size();i++){
//				String aythcycs=request.getParameter("authcyc"+list.get(i).getString("id"));
//				jsonObject.accumulate("authcyc"+list.get(i).getString("id"), aythcycs);
//			}
//			HttpSession session = request.getSession();
//			String userId = (String) session.getAttribute("userId");
//			Object result = iputPlanService.putPlan(stime,etime,timeslice,apids,jsonObject,userId);
////			JSONObject json1=JSONObject.fromObject(result);
//			JSONArray json2 = JSONArray.fromObject(result);
//			JSONObject json=json2.getJSONObject(0);
//			if(json.containsKey("status")){
//				this.setResultInfo(QUERY_FAILURE_INFO, JSONArray.fromObject(result));
//			}else{
//				this.setResultInfo(QUERY_SUCCESS_INFO, JSONArray.fromObject(result));
//			}
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, "ϵͳ�ڲ��쳣");
//		}
//		this.returnResponse(response, this.getResultInfo());
//		return;
//	 }
//	@RequestMapping("/putPlan2")
//	public void putPlan2(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		try{
//			String stime=request.getParameter("stime");
//			String timeslice=request.getParameter("timeslice");
//			String apid=request.getParameter("apid");
//			String[] apids=apid.split(",");
//			String authcyc = request.getParameter("authcyc");
//			JSONObject jsonauthcyc = JSONObject.fromObject(authcyc);
//			String i = request.getParameter("i");
//			HttpSession session = request.getSession();
//			String userId = (String) session.getAttribute("userId");
//			Object result = iputPlanService.putPlan(stime,timeslice,apids,jsonauthcyc,userId);
//			System.out.println("====stime:"+stime+"timeslice"+timeslice+" i:"+i);
//			JSONArray json2 = JSONArray.fromObject(result);
//			JSONObject json=json2.getJSONObject(0);
//			if(json.containsKey("status")){
//				this.setResultInfo(QUERY_FAILURE_INFO, JSONArray.fromObject(result));
//			}else{
//				this.setResultInfo(QUERY_SUCCESS_INFO, JSONArray.fromObject(result));
//			}
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, "ϵͳ�ڲ��쳣");
//		}
//		this.returnResponse(response, this.getResultInfo());
//		return;
//	 }
//	
//	@RequestMapping("/putPan2")
//	public void putPan2(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		try{
//			String stime=request.getParameter("stime");
//			String[] stimes = stime.split(",");
//			String timeslice=request.getParameter("timeslice");
//			String[] timeslices = timeslice.split(",");
//			String apid=request.getParameter("apid");
//			String[] apids=apid.split(",");
//			String authcyc = request.getParameter("authcyc");
//			JSONObject jsonauthcyc = JSONObject.fromObject(authcyc);
//			HttpSession session = request.getSession();
//			String userId = (String) session.getAttribute("userId");
//			long st=System.currentTimeMillis();
//			System.out.println("Contollerʱ��"+st);
//			Object result = iputPlanService.putPan(stimes,timeslices,apids,jsonauthcyc,userId,st);
//			System.out.println("Controller������ʱ"+(System.currentTimeMillis()-st));
//	    } catch (Exception e) {
//			e.printStackTrace();
//			this.setResultInfo(QUERY_FAILURE_INFO, "ϵͳ�ڲ��쳣");
//		}
//		this.returnResponse(response, this.getResultInfo());
//		return;
//	 }
	@RequestMapping("/putPan3")
	public void putPan3(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String stime=request.getParameter("stime");
			final String[] stimes = stime.split(",");
			String timeslice=request.getParameter("timeslice");
			final String[] timeslices = timeslice.split(",");
			String apid=request.getParameter("apid");
			final String[] apids=apid.split(",");
			String authcyc = request.getParameter("authcyc");
			final JSONObject jsonauthcyc = JSONObject.fromObject(authcyc);
			HttpSession session = request.getSession();
			final String userId = (String) session.getAttribute("userId");
			
			List<JSONObject> flagList = iCommonService.queryPutFlag();
			JSONObject json=flagList.get(0);
			String id=JSONUtils.getString(json, "flag");
			System.out.println("Ͷ�ű�ʶ�ǣ�"+id);
			if("0".equals(id)){
				System.out.println("Ͷ�ű�ʶ====");
				new Thread(new Runnable() {
					@Override
					public void run() {
						long st=System.currentTimeMillis();
						// TODO Auto-generated method stub
						iCommonService.setPutFlag("1");
						System.out.println("Ͷ�ſ�ʼ1");
						iputPlanService.putPan(stimes,timeslices,apids,jsonauthcyc,userId,st);
						System.out.println("Ͷ�Ž���0");
						iCommonService.setPutFlag("0");
					}
				}).start();
				this.setResultInfo(QUERY_SUCCESS_INFO, "����Ͷ����");
			}else{
				System.out.println("����Ͷ�ű�ʶ====");
				this.setResultInfo(QUERY_FAILURE_INFO, "���������ڴ����У����Ժ�");				
			}
			
	    } catch (Exception e) {
			e.printStackTrace();
			iCommonService.setPutFlag("0");
			this.setResultInfo(QUERY_FAILURE_INFO, "ϵͳ�ڲ��쳣");
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	@RequestMapping("/putPlantest")
	public void putPlantest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String stime=request.getParameter("stime");
			String etime=request.getParameter("etime");
//			String[] timeslice=request.getParameterValues("timeslice");
			String timeslices = request.getParameter("timeslice");
			String[] timeslice= timeslices.split(",");
			String apid=request.getParameter("apid");
			String[] apids=apid.split(",");
			String authcyc = request.getParameter("authcyc");
			JSONObject jsonObject =JSONObject.fromObject(authcyc);
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			List result = iputPlanService.putPlantest(stime,etime,timeslice,apids,jsonObject,userId);
				this.setResultInfo(QUERY_SUCCESS_INFO, result);

	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "ϵͳ�ڲ��쳣");
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
}
