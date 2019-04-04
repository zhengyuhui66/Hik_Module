package com.hik.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;
import com.hik.service.IMaterialService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/materialController")
public class MaterialController  extends BaseController{
	@Autowired
	private IMaterialService iMaterialService;
//    @Value("${material_upload_tempurl}")
//	private String uploadFileUrl;
    
    @Value("${serverhttp}")
    private String serverhttp;
	/**
	 * 上传物料
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
    
    @RequestMapping("/materiel")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "materielManger/materialManger";
    }
    @RequestMapping("/auditManger")
    public String auditManger(HttpServletRequest request,HttpServletResponse response){
    	return "materielManger/auditManger";
    }
	@RequestMapping("/uploadMateriel")
	@HikLog(content="上传新的物料",curd=LogCommon.NEWADD)
	public void contest(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("上传新的物料============>");
	    try{
//	    	 String path3 = request.getSession().getServletContext().getRealPath("/");
//	    	 log.info("=====>项目绝对路径"+path3);
	         String path2 = request.getSession().getServletContext().getRealPath("upload");
//	         log.info("=====>UPLOAD绝对路径"+path2);
//	         File fe=new File(path3);
//	         String pathnn=fe.getParent();
//	         log.info("====>绝对路径的上上一条记录");
//	         String path3 = request.getSession().getServletContext().getRealPath("");
//	         String t=System.getProperty("user.dir").replace("bin", "webapps");
//	         log.info("===>项目的tomcat路径1==》"+t);
//	         log.info("===>项目的tomcat路径2==》"+t+"\resource");
//	         log.info("=====>UPLOAD绝对路径"+path3);
//	         String path = request.getContextPath();
	         String basePath = "upload/";
//	         String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+uploadFileUrl+"/";  
	         String fileName = file.getOriginalFilename();
	         File targetFile = new File(path2, fileName);
	         long size=file.getSize();
	         
//	         String type=file.getContentType();
	         String type=getFileType(fileName);
	         String original=file.getOriginalFilename(); 
	         if(!targetFile.exists()){
	             targetFile.mkdirs();  
	         } 
//			 MATERIEL_INFO info = new MATERIEL_INFO(
//    		 "0", CommonUtil.getUserId(request), original, 
//    		 size+"", type, DateUtil.getCurrentDate(), 
//    		 null, basePath+fileName, "未审核","0","0");
	         
	         
			 MATERIEL_INFO info = new MATERIEL_INFO(
    		 "0", CommonUtil.getUserId(request), original, 
    		 size+"", type, DateUtil.getCurrentDate(), 
    		 null, basePath+fileName, "未审核","0","0");
	         
	         
	         String userid=CommonUtil.getUserId(request);
        	 iMaterialService.saveMateriel(info,file,targetFile,userid);
             log.info("上传时间");
		this.setResultInfo(QUERY_SUCCESS_INFO, true);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "上传异常");
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	public String getFileType(String fileName) {
        String[] strArray = fileName.split("\\.");
        int suffixIndex = strArray.length -1;
        return strArray[suffixIndex];        
    }
	/**
	 * 更新物料
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateMateriel")
	@HikLog(content="更新物料",curd=LogCommon.UPDATE)
	public void updateMateriel(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("更新物料============>");
	    try{
	         String path2 = request.getSession().getServletContext().getRealPath("upload");
//	         String path = request.getContextPath();
	         String materid=request.getParameter("materId");
//	         String basePath = serverhttp+path+"/upload/";
	         String basePath = "upload/";
//	         String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+uploadFileUrl+"/";  
	         String fileName = file.getOriginalFilename();
	         File targetFile = new File(path2, fileName);
	         long size=file.getSize();
//	         String type=file.getContentType();
	         String type=getFileType(fileName);
	         String original=file.getOriginalFilename();
	         if(!targetFile.exists()){
	             targetFile.mkdirs();  
	         }
		      MATERIEL_INFO info = new MATERIEL_INFO(
		    		  materid, CommonUtil.getUserId(request), original, 
		        		 size+"", type, DateUtil.getCurrentDate(), 
		        		 null, basePath+fileName, "未审核","0","0");
	         String userid=CommonUtil.getUserId(request);
	         iMaterialService.updateMateriel(info, file, targetFile, userid);
//        	 iMaterialService.saveMateriel(info,file,targetFile,userid);
             log.info("上传时间");
		this.setResultInfo(QUERY_SUCCESS_INFO, true);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "上传异常");
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	 }
	
	
	/**
	 * 查询物料
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMateriel")
	@HikLog(content="查询物料信息",curd=LogCommon.QUERY)
	public void queryMateriel(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try{
	    	String UserId=null;
	    	String RoleId = CommonUtil.getTrid(request);
	    	int limit=getStartOrLimit(request, "limit");
	    	int start=getStartOrLimit(request, "start");
	    	String searchStr=request.getParameter("searchStr");
	    	if(CommonSence.ADERT_USER.equals(RoleId)){
	    		UserId=CommonUtil.getUserId(request);
	    	}
	    	Page page = iMaterialService.queryMateriel(UserId, start, limit,searchStr);
	    	this.returnResponse(response,JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());   
		}
	    return;
	 }
	/**
	 * 审核物料查询
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterAudit")
	public void queryMaterAudit(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    try{
	    	String UserId=CommonUtil.getUserId(request);
	    	int limit=getStartOrLimit(request, "limit");
	    	int start=getStartOrLimit(request, "start");
	    	String searchStr= request.getParameter("searchStr");
	    	Page page = iMaterialService.queryMaterAudit(UserId,start,limit,searchStr);
	    	this.returnResponse(response,JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());   
		}
	    return;
	 }
	@RequestMapping("/auditUpdate")
	public void auditUpdate(HttpServletRequest request,HttpServletResponse response){
		try{
			String auditId=request.getParameter("auditId");  
			String audit_state=request.getParameter("audit_state"); 
			String audit_result=request.getParameter("audit_result");
			String audit_desc=request.getParameter("audit_desc");
			String mater_id=request.getParameter("mater_id");
			String audit_proce=request.getParameter("audit_proce");
			String userid=CommonUtil.getUserId(request);
			String path = request.getSession().getServletContext().getRealPath("upload");
			
			int result = iMaterialService.auditUpdate2(auditId,audit_state,audit_result,audit_desc,mater_id,audit_proce,path,userid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, "内部有异常");
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	@RequestMapping("/getAuditInfo")
	public void getAuditInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			String materId=request.getParameter("materId");  
			List<JSONObject> result = iMaterialService.getAuditInfo(materId);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	
	@RequestMapping("/getAuditUser")
	public void getAuditUser(HttpServletRequest request,HttpServletResponse response){
		try{ 
			String UserId=CommonUtil.getUserId(request);
			List<JSONObject> result = iMaterialService.getAuditUser(UserId);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	/**
	 * 获取权限用户
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getAuditChecked")
	public void getAuditChecked(HttpServletRequest request,HttpServletResponse response){
		try{  
			String UserId=CommonUtil.getUserId(request);
			
			List<JSONObject> result = iMaterialService.getAuditChecked(UserId);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	/**
	 * 得到当前用户所有的子用户
	 */
	@RequestMapping("/getSubUser")
	public void getSubUser(HttpServletRequest request,HttpServletResponse response){
		try{
			String UserId=CommonUtil.getUserId(request);
			
			List<JSONObject> result = iMaterialService.getSubUser(UserId);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	
	/**
	 * 新增或者更新审核权限
	 */
	@RequestMapping("/saveOrUpdateAudituser")
	public void saveOrUpdateAudituser(HttpServletRequest request,HttpServletResponse response){
		try{  
			String getReviceId=request.getParameter("getReviceId");
			String[] listParam = getReviceId.split(",");
			List<AUDIT_USERINFO> list = new ArrayList();
			for(int i=0;i<listParam.length;i++){
				String[] tempid=request.getParameterValues(listParam[i]);
				if(tempid!=null&&tempid.length>0){
					for(int m=0;m<tempid.length;m++){
						String userId=tempid[m];
						String puserId = CommonUtil.getUserId(request);
						String audit_id=listParam[i];
						AUDIT_USERINFO audit_USERINFO = new AUDIT_USERINFO(puserId, audit_id, userId);
						list.add(audit_USERINFO);
					}
				}
			}
			String userid=CommonUtil.getUserId(request);
			int result=iMaterialService.saveOrUpdateAudituser(list,userid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	/**
	 * 删除物料
	 */
	@RequestMapping("/deleteMaterInfo")
	@HikLog(content="删除物料",curd=LogCommon.DELETE)
	public void deleteMaterInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			String materId = request.getParameter("materId");
			String[] materIds = materId.split(",");
			String fileName = request.getParameter("fileNames");
			String[] fileNames=fileName.split(",");
			List params = new ArrayList();
			Collections.addAll(params,materIds);
			List files = new ArrayList();
			Collections.addAll(files, fileNames);
			String path2 = request.getSession().getServletContext().getRealPath("upload");
			int result=iMaterialService.deleteMaterInfo(params,path2,files);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	@RequestMapping("/checkiPassAudit")
	public void checkiPassAudit(HttpServletRequest request,HttpServletResponse response){
		try{
			String audit_proce = request.getParameter("audit_proce");
			String mater_id = request.getParameter("mater_id");
			List result=iMaterialService.checkiPassAudit(audit_proce,mater_id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	
	@RequestMapping("/checkAllPassAudit")
	public void checkAllPassAudit(HttpServletRequest request,HttpServletResponse response){
		try{
			String mater_id = request.getParameter("mater_id");
			boolean result=iMaterialService.checkAllPassAudit(mater_id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	@RequestMapping("/queryMaterCreator")
	public void queryMaterCreator(HttpServletRequest request,HttpServletResponse response){
		try{
//			String mater_id = request.getParameter("mater_id");
			List result=iMaterialService.queryMaterCreator();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}

	@RequestMapping("/queryMaterType")
	public void queryMaterType(HttpServletRequest request,HttpServletResponse response){
		try{
//			String mater_id = request.getParameter("mater_id");
			List result=iMaterialService.queryMaterType();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	@RequestMapping("/queryMaterSize")
	public void queryMaterSize(HttpServletRequest request,HttpServletResponse response){
		try{
//			String mater_id = request.getParameter("mater_id");
			List result=iMaterialService.queryMaterSize();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
	   this.returnResponse(response, this.getResultInfo());   
	    return;
	}
	
}
