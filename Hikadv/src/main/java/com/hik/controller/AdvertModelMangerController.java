package com.hik.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hik.app.entity.ADVERTMODELMANGER;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IAdvertModelService;
import com.hik.service.IGetAdvertService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller  
@RequestMapping("/advertMmController")
public class AdvertModelMangerController extends BaseController{
//    @Value("${advertModelurl}")
//	private String advertModelurl;
	@Autowired
	private IAdvertModelService iAdvertModelService;
    @RequestMapping("/advertMm")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "advertManger/advertModelManger";
    }
	/**
	 * 分页得到所有广告模版
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryadvertModel")
	@HikLog(content="查询广告模版",curd=LogCommon.QUERY)
	public void getAdvert(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr=request.getParameter("searchStr");
			Page page = iAdvertModelService.queryadvertModel(start, limit,searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	
	
	/**
	 * 保存新增的广告模版
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveadvertModel")
	@HikLog(content="保存广告模版",curd=LogCommon.NEWADD)
	public void saveadvertModel(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("保存广告模版============>");
		try{
			String path2 = request.getSession().getServletContext().getRealPath("pages/advertModel");
	         String path = request.getContextPath();  
	         String fileName = file.getOriginalFilename();  
//	         String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/pages/advertModel/"+fileName;  
	         String basePath = "pages/advertModel/"+fileName;

	         File targetFile = new File(path2, fileName);
	         if(!targetFile.exists()){
	             targetFile.mkdirs();
	         }  
			String subModelid = request.getParameter("subModelid");
			String[] subModelids=subModelid.split(",");
			String subModelname = request.getParameter("subModelname");
			String[] subModelnames=subModelname.split(",");
			String description=request.getParameter("description");
			String modelname=request.getParameter("modelname");
			String modelskin=request.getParameter("modelskin");
			String cycid=request.getParameter("modelcyc");
			String userid=CommonUtil.getUserId(request);
			Map modemodelMap=new HashMap();
			for(int i=0;i<subModelids.length;i++){
				if(subModelids[i]!=null&&subModelnames[i]!=null){
					modemodelMap.put(subModelids[i],subModelnames[i]);
				}
			}
			int result = iAdvertModelService.saveadvertModel(description,modelname,modemodelMap,basePath,targetFile,file,userid,fileName,modelskin,cycid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	     
	/**
	 * 更新的模版信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateadvertModel")
	@HikLog(content="更新广告模版信息",curd=LogCommon.UPDATE)
	public void updateadvertModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
//			String modelid=request.getParameter("modelid");
			String id=request.getParameter("id");
			String mMname=request.getParameter("mMname");
			String desc=request.getParameter("desc");
//			String msubMname=request.getParameter("msubMname");
//			String mMId=request.getParameter("mMId");
			String cycid=request.getParameter("modelcyc");
			Double _double=Double.parseDouble(cycid);
			String modelskin=request.getParameter("modelskin");
			String advposid1=request.getParameter("advposid1");
			String advposid2=request.getParameter("advposid2");
			String advposid3=request.getParameter("advposid3");
			String advposid4=request.getParameter("advposid4");
			String advposid5=request.getParameter("advposid5");
			String advposname1=request.getParameter("advposname1");
			String advposname2=request.getParameter("advposname2");
			String advposname3=request.getParameter("advposname3");
			String advposname4=request.getParameter("advposname4");
			String advposname5=request.getParameter("advposname5");
			ADVERTMODELMANGER ad = new ADVERTMODELMANGER(id, mMname, null,null,null,null,_double,desc,null, modelskin, advposid1, advposid2, advposid3, advposid4, advposid5, advposname1, advposname2, advposname3, advposname4, advposname5);
			int result= iAdvertModelService.updateadvertModel(ad);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	/**
	 * 删除的模版信息
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteadvertModel")
	@HikLog(content="删除广告模版",curd=LogCommon.DELETE)
	public void deleteadvertModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String str = request.getParameter("obj");
			JSONObject jsons = JSONObject.fromObject(str);
			
			String path2 = request.getSession().getServletContext().getRealPath("pages/advertModel");
//			String id=request.getParameter("id");
//			String modelname=request.getParameter("modelname");
//			path2+=modelname;
			String result= iAdvertModelService.deleteadvertModel(jsons,path2);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }

	@RequestMapping("/queryModelModeById")
	public void queryModelModeById(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String modelid=request.getParameter("id");
	    	List<?> result = iAdvertModelService.queryModelModeById(modelid);
	    	this.setResultInfo(QUERY_SUCCESS_INFO,JSONArray.fromObject(result));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
}
