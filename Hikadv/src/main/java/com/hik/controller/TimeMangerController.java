package com.hik.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.TimeMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/time")
public class TimeMangerController extends BaseController {
	
	@Autowired
	private TimeMangerService timeMangerService;
	
    @RequestMapping("/timeManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/timeManger";
    }
    
    
    /**
     * ���ݷ���༭ʱʱ�������б�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListtime")
	@HikLog(content="ҳ��ѯʱ������",curd=LogCommon.QUERY)
	public void queryListtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			List result = timeMangerService.queryListtime(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
    /**
     * ���ݷ���༭ʱʱ��������ѡ���б�    
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListgtime")
	@HikLog(content="ҳ��ѯʱ������",curd=LogCommon.QUERY)
	public void queryListgtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			if(id==null||StringUtils.isEmpty(id)){
				this.setResultInfo(QUERY_SUCCESS_INFO, null);
			}else{
				List result = timeMangerService.queryListgtime(id);
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
	 * ��ҳ��ѯʱ������
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querytime")
	@HikLog(content="��ҳʱ������",curd=LogCommon.QUERY)
	public void querytime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = timeMangerService.querytime(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * �������е��ն�ϵͳ��
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdatetime")
	@HikLog(content="����ʱ������",curd=LogCommon.NEWADD)
	public void saveOrupdatetime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");  
			String name = request.getParameter("name");  
			String stime = request.getParameter("stime");
			String etime = request.getParameter("etime");
			String sdtime = request.getParameter("sdtime");
			String edtime = request.getParameter("edtime");
			String descr = request.getParameter("descr");
			HttpSession session =request.getSession();
			String userid=session.getAttribute("userId").toString();
			int result = timeMangerService.saveOrupdatetime(id,name,stime,etime,sdtime,edtime,userid,descr);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * ɾ���ն�ϵͳ��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletetime")
	@HikLog(content="ɾ��ʱ������",curd=LogCommon.QUERY)
	public void deletetime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = timeMangerService.deletetime(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	/**
	 * ��ҳ��ѯ�ն�ϵͳ����
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querygtime")
	@HikLog(content="��ҳ��ѯ�ն�ϵͳ����",curd=LogCommon.QUERY)
	public void querygtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = timeMangerService.querygtime(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * ���������ն�ϵͳ����
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdategtime")
	@HikLog(content="���������ն�ϵͳ����",curd=LogCommon.NEWADD)
	public void saveOrupdategtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			  String gtimename=request.getParameter("gtimename");
			  String gtimeid=request.getParameter("gtimeid");
			  String timeids=request.getParameter("timeids");
			  String gtimedesc=request.getParameter("gtimedesc");
			  HttpSession session =request.getSession();
				String userid=session.getAttribute("userId").toString();
				
			int result = timeMangerService.saveOrupdategtime(gtimeid,gtimename,userid,gtimedesc,timeids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * ɾ���ն�ϵͳ����
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletegtime")
	@HikLog(content="ɾ���ն�ϵͳ����",curd=LogCommon.QUERY)
	public void deletegtime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = timeMangerService.deletegtime(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	

	
}
