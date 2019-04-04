package com.hik.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.ICommonService;
import com.hik.service.IPutScheduleService;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/psc")
public class PutScheduleController  extends BaseController{
	
	@Autowired
	private IPutScheduleService iputScheduleService;
	
	@Autowired
	private ICommonService iCommonService;
	
	@RequestMapping("/putSchedule")
    public String getputSchedule(HttpServletRequest request,HttpServletResponse response){
    	return "putManger/putSchedule";
    }
	
	@RequestMapping("/queryPutLog")
	@HikLog(content="��ѯ����",curd=LogCommon.QUERY)
	public void queryPutLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String stime=request.getParameter("stime");
			String etime=request.getParameter("etime");
			String timeslice2=request.getParameter("timeslice");
			String[] timeslice=null;
			if(!StringUtils.isEmpty(timeslice2)){
				timeslice=timeslice2.split(",");
			}
			String authcycids=request.getParameter("cycinfo");
			JSONObject mmppJson = new JSONObject();
			JSONArray array=null;
			if(authcycids!=null){
				array= JSONArray.fromObject(authcycids);	
				for(int i=0;i<array.size();i++){
					JSONObject tj = array.getJSONObject(i);
					String cycid = JSONUtils.getString(tj, "cycid");
					if(PROCEDURCES.MMPPFLAG.equals(cycid)){
						mmppJson=tj;
						array.remove(i);
					}
				}
			}
			String busid=request.getParameter("busid");
			String routeid=request.getParameter("routeid");
			HttpSession session = request.getSession();
			String userid=session.getAttribute("userId").toString();
			Page page = iputScheduleService.queryPutLog(start,limit,stime,etime,timeslice,array,mmppJson,routeid,busid,userid);
			this.returnResponse(response,JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	public List getTimes(String stime,String etime){
		Date sDate=DateUtil.getStrToDate(stime,"yyyy-MM-dd");
		Date eDate=DateUtil.getStrToDate(etime,"yyyy-MM-dd");
		List list = new ArrayList();
		while(!sDate.equals(eDate)){
			String stimes=DateUtil.getDateToStr(sDate,"yyyy-MM-dd");
			list.add(stimes);
			sDate=DateUtil.getAddaDay(sDate);
		}
		return list;
	}
	@RequestMapping("/deletePutLog")
	@HikLog(content="ɾ��ȫ������",curd=LogCommon.QUERY)
	public void deletePutLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String stime=request.getParameter("stime");
			String etime=request.getParameter("etime");
			String timeslice2=request.getParameter("timeslice");
			final String[] timeslice=timeslice2.split(",");
			String authcycids=request.getParameter("cycinfo");
			JSONArray _array= JSONArray.fromObject(authcycids);
			JSONObject _mmppJson = new JSONObject();
			for(int i=0;i<_array.size();i++){
				JSONObject tj = _array.getJSONObject(i);
				String cycid = JSONUtils.getString(tj, "cycid");
				if(PROCEDURCES.MMPPFLAG.equals(cycid)){
					_mmppJson=tj;
					_array.remove(i);
				}
			}
			final JSONArray array=_array;
			final JSONObject mmppJson=_mmppJson;
			final String busid=request.getParameter("busid");
			final String routeid=request.getParameter("routeid");
			HttpSession session = request.getSession();
			final List date=getTimes(stime.substring(0, 10),etime.substring(0, 10));			
			final String userid=session.getAttribute("userId").toString();
			List<JSONObject> flagList = iCommonService.queryPutFlag();
			JSONObject json=flagList.get(0);
			String id=JSONUtils.getString(json, "flag");
			if("0".equals(id)){
				new Thread(new Runnable() {
					@Override
					public void run() {
						long st=System.currentTimeMillis();
						// TODO Auto-generated method stub
						iCommonService.setPutFlag("1");
						try {
							iputScheduleService.deletePutLog(date,timeslice,array,mmppJson,routeid,busid,userid);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							iCommonService.setPutFlag("0");
							e.printStackTrace();
						}
						iCommonService.setPutFlag("0");
					}
				}).start();
				this.setResultInfo(QUERY_SUCCESS_INFO, "����ɾ����");
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "���������ڴ����У����Ժ�");				
			}
	    } catch (Exception e) {
			e.printStackTrace();
			iCommonService.setPutFlag("0");
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }

	@RequestMapping("/pausePutLog")
	@HikLog(content="��ͣȫ�� ����",curd=LogCommon.QUERY)
	public void pausePutLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String stime=request.getParameter("stime");
			String etime=request.getParameter("etime");
			String timeslice2=request.getParameter("timeslice");
			final String[] timeslice=timeslice2.split(",");
			String authcycids=request.getParameter("cycinfo");
			JSONArray _array= JSONArray.fromObject(authcycids);
			JSONObject _mmppJson = new JSONObject();
			for(int i=0;i<_array.size();i++){
				JSONObject tj = _array.getJSONObject(i);
				String cycid = JSONUtils.getString(tj, "cycid");
				if(PROCEDURCES.MMPPFLAG.equals(cycid)){
					_mmppJson=tj;
					_array.remove(i);
				}
			}
			final JSONArray array=_array;
			final JSONObject mmppJson=_mmppJson;
			final String busid=request.getParameter("busid");
			final String routeid=request.getParameter("routeid");
			HttpSession session = request.getSession();
			final String userid=session.getAttribute("userId").toString();
			final List date=getTimes(stime.substring(0, 10),etime.substring(0, 10));	
			List<JSONObject> flagList = iCommonService.queryPutFlag();
			JSONObject json=flagList.get(0);
			String id=JSONUtils.getString(json, "flag");
			if("0".equals(id)){
				new Thread(new Runnable() {
					@Override
					public void run(){
							iCommonService.setPutFlag("1");
							try {
								iputScheduleService.pausePutLog(date,timeslice,array,mmppJson,routeid,busid,userid);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								iCommonService.setPutFlag("0");
								e.printStackTrace();
							}
							iCommonService.setPutFlag("0");			
						}
				}).start();
				this.setResultInfo(QUERY_SUCCESS_INFO, "������ͣ��");
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "���������ڴ����У����Ժ�");				
			}
	    } catch (Exception e) {
			e.printStackTrace();
			iCommonService.setPutFlag("0");
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	@RequestMapping("/normalPutLog")
	@HikLog(content="�ָ�ȫ������",curd=LogCommon.QUERY)
	public void normalPutLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String stime=request.getParameter("stime");
			String etime=request.getParameter("etime");
			String timeslice2=request.getParameter("timeslice");
			final String[] timeslice=timeslice2.split(",");
			String authcycids=request.getParameter("cycinfo");
			JSONArray _array=JSONArray.fromObject(authcycids);		
			JSONObject _mmppJson = new JSONObject();
			for(int i=0;i<_array.size();i++){
				JSONObject tj = _array.getJSONObject(i);
				String cycid = JSONUtils.getString(tj, "cycid");
				if(PROCEDURCES.MMPPFLAG.equals(cycid)){
					_mmppJson=tj;
					_array.remove(i);
				}
			}
			final JSONArray array=_array;
			final JSONObject mmppJson=_mmppJson;
			final String busid=request.getParameter("busid");
			final String routeid=request.getParameter("routeid");
			HttpSession session = request.getSession();
			final String userid=session.getAttribute("userId").toString();
			final List date=getTimes(stime.substring(0, 10),etime.substring(0, 10));
			List<JSONObject> flagList = iCommonService.queryPutFlag();
			JSONObject json=flagList.get(0);
			String id=JSONUtils.getString(json, "flag");
			if("0".equals(id)){
				new Thread(new Runnable() {
					@Override
					public void run() {
						long st=System.currentTimeMillis();
						iCommonService.setPutFlag("1");
						try {
							iputScheduleService.normalPutLog(date,timeslice,array,mmppJson,routeid,busid,userid);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							iCommonService.setPutFlag("0");
							e.printStackTrace();
						}
						iCommonService.setPutFlag("0");				
						}
				}).start();
				this.setResultInfo(QUERY_SUCCESS_INFO, "���ڻָ���");
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "���������ڴ����У����Ժ�");				
			}
	    } catch (Exception e) {
			e.printStackTrace();
			iCommonService.setPutFlag("0");
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	@RequestMapping("/queryPutLogList")
	@HikLog(content="��ѯ����",curd=LogCommon.QUERY)
	public void queryPutLogList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String stime=request.getParameter("stime");
			String etime=request.getParameter("etime");
			String timeslice2=request.getParameter("timeslice");
			String[] timeslice=null;
			if(!StringUtils.isEmpty(timeslice2)){
				timeslice=timeslice2.split(",");
			}
			String authcycids=request.getParameter("cycinfo");
			JSONArray array=null;
			JSONObject mmppJson = new JSONObject();
			if(authcycids!=null){
				array= JSONArray.fromObject(authcycids);
				for(int i=0;i<array.size();i++){
					JSONObject tj = array.getJSONObject(i);
					String cycid = JSONUtils.getString(tj, "cycid");
					if(PROCEDURCES.MMPPFLAG.equals(cycid)){
						mmppJson=tj;
						array.remove(i);
					}
				}
			}
			String busid=request.getParameter("busid");
			String routeid=request.getParameter("routeid");
			HttpSession session = request.getSession();
			String userid=session.getAttribute("userId").toString();
			List result = iputScheduleService.queryPutLogList(stime,etime,timeslice,array,mmppJson,routeid,busid,userid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	@RequestMapping("/pausePut")
	@HikLog(content="��ͣ����",curd=LogCommon.UPDATE)
	public void pausePut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String ids=request.getParameter("ids");
			HttpSession session = request.getSession();
			String userid=session.getAttribute("userId").toString();
			List<JSONObject> flagList = iCommonService.queryPutFlag();
			JSONObject json=flagList.get(0);
			String id=JSONUtils.getString(json, "flag");
			if("0".equals(id)){
			   int result = iputScheduleService.pausePut(ids,userid);
			   this.setResultInfo(QUERY_SUCCESS_INFO, "��ͣ��"+result+"������");
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "���������ڴ�����");
			}
			
	    }catch (Exception e){
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO,"��ͣʧ��");
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	@RequestMapping("/normalPut")
	@HikLog(content="�ָ���������",curd=LogCommon.UPDATE)
	public void normalPut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String ids=request.getParameter("ids");
			HttpSession session = request.getSession();
			String userid=session.getAttribute("userId").toString();
			List<JSONObject> flagList = iCommonService.queryPutFlag();
			JSONObject json=flagList.get(0);
			String id=JSONUtils.getString(json, "flag");
			if("0".equals(id)){
				int result = iputScheduleService.normalPut(ids,userid);
				this.setResultInfo(QUERY_SUCCESS_INFO, "�ָ���"+result+"������");
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "���������ڴ�����");
			}
			
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "�ָ�ʧ��");
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	@RequestMapping("/cancelPut")
	@HikLog(content="ȡ������",curd=LogCommon.UPDATE)
	public void cancelPut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String ids=request.getParameter("ids");
			HttpSession session = request.getSession();
			String userid=session.getAttribute("userId").toString();
			List<JSONObject> flagList = iCommonService.queryPutFlag();
			JSONObject json=flagList.get(0);
			String id=JSONUtils.getString(json, "flag");
			if("0".equals(id)){
				int result = iputScheduleService.cancelPut(ids,userid);
				this.setResultInfo(QUERY_SUCCESS_INFO, "�ɹ�ȡ����"+result+"������");
			}else{
				this.setResultInfo(QUERY_FAILURE_INFO, "���������ڴ�����");
			}
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "ȡ��ʧ��");
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
