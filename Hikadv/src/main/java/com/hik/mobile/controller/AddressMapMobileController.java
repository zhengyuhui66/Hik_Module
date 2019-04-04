package com.hik.mobile.controller;

import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.PUTPRODUCT;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.AddressMapService;
import com.hik.service.IPutMangerSetService;
import com.hik.service.IPutProductService;

import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/mobile/amapc")
public class AddressMapMobileController extends BaseController {
	
	@Autowired
	private AddressMapService addressMapService;
	
    @RequestMapping("/priorityPutManger")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "priorityPutManger/addressMap";
    }
    
	/**
	 * ��ҳ��ѯ���Ͷ������
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryAddress")
	@HikLog(content="ҳ��ѯ���еĵ�ַ",curd=LogCommon.QUERY)
	public void queryAddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result = addressMapService.getAllAddress();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			
		}
		 this.returnResponse(response, this.getResultInfo());   
		return;
	 }
	/**
	 * ��ҳ��ѯ���Ͷ������
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveaddress")
	@HikLog(content="�����ַ",curd=LogCommon.NEWADD)
	public void saveaddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String nameid = request.getParameter("name");  
			String descrid = request.getParameter("descr");  
			String latlongid = request.getParameter("latlong");
			String latlongids[]=null;
			if(latlongid==null){
				throw new Exception();
			}else{
				latlongids=latlongid.split(",");
				if(latlongids.length!=0&&latlongids.length%2!=0){
					throw new Exception();
				}else{
					HttpSession session = request.getSession();
					String userid=session.getAttribute("userId").toString();
					int result=addressMapService.saveaddress(nameid, descrid,userid,latlongids);
					this.setResultInfo(QUERY_SUCCESS_INFO, result);
				}
			}
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * ��ҳ��ѯ���Ͷ������
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteAddress")
	@HikLog(content="ҳ��ѯ���еĵ�ַ",curd=LogCommon.QUERY)
	public void deleteAddress(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
			int result = addressMapService.deleteAddress(ids);
//			this.returnResponse(response, result);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
