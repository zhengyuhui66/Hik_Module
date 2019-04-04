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
import com.hik.service.PhoneMangerService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/pmangc")
public class PhoneMangerController extends BaseController {
	
	@Autowired
	private PhoneMangerService phoneMangerService;
	
    @RequestMapping("/priorityPutManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/phoneManger";
    }
    
    
    /**
     * ���ݷ���༭ʱδѡ���ֻ��б�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListphone")
	@HikLog(content="ҳ��ѯ���е��ֻ���",curd=LogCommon.QUERY)
	public void queryListphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			List result = phoneMangerService.queryListphone(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
    /**
     * ���ݷ���༭ʱ�ֻ���ѡ���б�    
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryListgphone")
	@HikLog(content="ҳ��ѯ���е��ֻ���",curd=LogCommon.QUERY)
	public void queryListgphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");
			if(id==null||StringUtils.isEmpty(id)){
				this.setResultInfo(QUERY_SUCCESS_INFO, null);
			}else{
				List result = phoneMangerService.queryListgphone(id);
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
	 * ��ҳ��ѯ���е��ֻ���
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryphone")
	@HikLog(content="��ҳ��ѯ���е��ֻ���",curd=LogCommon.QUERY)
	public void queryphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = phoneMangerService.queryphone(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * �������е��ֻ���
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdatephone")
	@HikLog(content="�������е��ֻ���",curd=LogCommon.NEWADD)
	public void saveOrupdatephone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id = request.getParameter("id");  
			String name = request.getParameter("name");  
			String descr = request.getParameter("descr");
			HttpSession session =request.getSession();
			String userid=session.getAttribute("userId").toString();
			int result = phoneMangerService.saveOrupdatephone(id,name,userid,descr);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * ɾ���ֻ���
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletephone")
	@HikLog(content="ɾ���ֻ�",curd=LogCommon.QUERY)
	public void deletephone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = phoneMangerService.deletephone(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	
	/**
	 * ��ҳ��ѯ�ֻ�����
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/querygphone")
	@HikLog(content="��ҳ��ѯ�ֻ�����",curd=LogCommon.QUERY)
	public void querygphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String search=request.getParameter("searchStr");
			int start = getStartOrLimit(request, "start");
			int limit = getStartOrLimit(request, "limit");
			Page result = phoneMangerService.querygphone(start,limit,search);
			this.returnResponse(response, JSONObject.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * ���������ֻ�����
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdategphone")
	@HikLog(content="���������ֻ�����",curd=LogCommon.NEWADD)
	public void saveOrupdategphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			  String gphonename=request.getParameter("gphonename");
			  String gphoneid=request.getParameter("gphoneid");
			  String phoneids=request.getParameter("phoneids");
			  String gphonedesc=request.getParameter("gphonedesc");
			  HttpSession session =request.getSession();
				String userid=session.getAttribute("userId").toString();
				
			int result = phoneMangerService.saveOrupdategphone(gphoneid,gphonename,userid,gphonedesc,phoneids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * ɾ���ֻ�����
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletegphone")
	@HikLog(content="ɾ���ֻ�����",curd=LogCommon.QUERY)
	public void deletegphone(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = phoneMangerService.deletegphone(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
