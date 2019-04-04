package com.hik.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hik.Exception.SftpuploadException;
import com.hik.app.entity.AUDIT_INFO;
import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.dao.AUDIT_INFO_jpa;
import com.hik.dao.AUDIT_USERINFO_jpa;
import com.hik.dao.BaseService;
import com.hik.dao.IMaterialDao;
import com.hik.dao.IMaterialDaoImpl;
import com.hik.dao.MATERIEL_INFO_jpa;
import com.hik.framework.service.IUserService;
import com.hik.framework.utils.CommonSence;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.Page;
import com.hik.util.SFTP;
import com.jcraft.jsch.ChannelSftp;

import net.sf.json.JSONObject;


@Service
@Transactional
public class MaterialServiceImpl  extends BaseService  implements IMaterialService{
	@Autowired
	private IMaterialDao iMaterialDao;
	@Autowired
	private MATERIEL_INFO_jpa info_jpa;
	@Autowired
	private AUDIT_INFO_jpa audit_INFO_jpa;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private AUDIT_USERINFO_jpa audit_USERINFO_jpa;
    @Value("${serverhttp}")
    private String serverhttp;
//	@Value("${material_local_location}")
//	private String material_local_location;
	
//	@Value("${material_remote_location}")
//	private String material_remote_location;
    
//    @Value("${material_upload_HTTP}")
//	private String materStoreHttp;
    
	
	public Page queryMateriel(String UserId,int start,int limit,String searchStr) {
		// TODO Auto-generated method stub
		Page page = iMaterialDao.queryMateriel(UserId, start, limit,searchStr);
		return page;
	}
	@Transactional
	public void saveMateriel(MATERIEL_INFO info,MultipartFile file,File targetFile,String userid) throws Exception{
		// TODO Auto-generated method stub
		try {
			file.transferTo(targetFile);
			BufferedImage bi = null;
			bi = ImageIO.read(targetFile);
			int width = bi.getWidth();
			int height = bi.getHeight();
			info.setMATER_WIDTH(width+"");
			info.setMATER_HEIGHT(height+"");
			String materid=iMaterialDao.getSEQToMater();
			info.setMATER_ID(materid);
			info_jpa.saveAndFlush(info);
			List<JSONObject> proce=iMaterialDao.getProce_desc();
			for(JSONObject obj:proce){
				String aUDIT_PROCE=obj.get("proce_id").toString();
				String auditid=iMaterialDao.getSEQToAudit();
				AUDIT_INFO audit_INFO = new AUDIT_INFO(auditid, info.getMATER_ID(), aUDIT_PROCE, null, CommonSence.UNAUDIT, CommonSence.UNAUDIT, null,CommonSence.UNAUDIT,userid);
				audit_INFO_jpa.save(audit_INFO);
			}
		} catch (Exception e){
			// TODO Auto-generated catch block
			if(targetFile.exists()){
				targetFile.delete();
			}
			e.printStackTrace();
			throw new RuntimeException();
		}
		return ;
	}
	
	public int updateMateriel(MATERIEL_INFO info, MultipartFile file, File targetFile, String userid)throws Exception {
		// TODO Auto-generated method stub
		try {
			file.transferTo(targetFile);
			BufferedImage bi = null;
			bi = ImageIO.read(targetFile);
			int width = bi.getWidth();
			int height = bi.getHeight();
			info.setMATER_WIDTH(width+"");
			info.setMATER_HEIGHT(height+"");
			info_jpa.saveAndFlush(info);
			iMaterialDao.updateAuditInfo(info.getMATER_ID());
			
		} catch (Exception e){
			// TODO Auto-generated catch block
			if(targetFile.exists()){
				targetFile.delete();
			}
			e.printStackTrace();
			throw new RuntimeException();
		}
		return 0;
	}
	
	public Page queryMaterAudit(String userId,int start, int limit,String searchStr){
		List<JSONObject> pUserid = iMaterialDao.getPUserFromAudit_userinfo(userId);
		List audit_state = new ArrayList();
		String _pUser=null;
		for(JSONObject obj:pUserid){
			_pUser=obj.getString("puserid");
			audit_state.add(obj.getString("audit_id"));
		}
//		String trid=CommonUtil.getTrid();
		Page page=null;
//		if(CommonSence.SENIOR_ID.equals(trid)){
//			page=iMaterialDao.queryMaterSeniorAudit(userId,start,limit,searchStr);			
//		}else if(CommonSence.HYPER_ID.equals(trid)){
			page=iMaterialDao.queryMaterhyperAudit(start,limit,searchStr);
//		}else{
//			page = iMaterialDao.queryMaterAudit(_pUser,audit_state,start,limit,searchStr);			
//		}
		return page;
	}
	
//	@Transactional
//	public int auditUpdate(String auditId, String audit_state, String audit_result,String audit_desc,String mater_id,String audit_proce,String path,String userid) throws Exception{
//		// TODO Auto-generated method stub
//		String stime=DateUtil.getCurrentDate();
//		int result=iMaterialDao.auditUpdate(auditId,audit_state,audit_result,audit_desc,userid,stime);
//		List maxProceId =iMaterialDao.queryMaxProce_desc();
//		String maxproceId=maxProceId.get(0).toString();
//		if(audit_proce.equals(maxproceId)&&CommonSence.AUDITED.equals(audit_state)&&CommonSence.AUDITED_PASS.equals(audit_result)){
//			//迁移物料
//			List list = iMaterialDao.queryMaterById(mater_id);
//			String materName=list.get(0).toString();
//		
//				String localFilepath = path+"/"+materName;
//				SFTP sftp = new SFTP();
//				ChannelSftp channelSftp=sftp.connect(sftpHost, Integer.parseInt(sftpPort), sftpUserName, sftpPassword);
//				if(channelSftp==null){
//					throw new SftpuploadException();
//				}
//				sftp.upload(sftpUrl, localFilepath, channelSftp);
//				File file = new File(path,materName);
//				/**
//				 * 删除缓存中的物料
//				 * 更新物料表中的URL地址
//				 * 后续开发
//				 * 
//				 */
//				if(file.exists()){
//					file.delete();
//				}
//				iMaterialDao.uploadMaterTempPath(mater_id,materStoreHttp+""+materName);
//				iMaterialDao.uploadMaterState(mater_id);
//			}else{
//				iMaterialDao.uploadMaterState(audit_result, audit_proce, mater_id);
//			}
//		return result;			
//	}
	@Transactional
	public int auditUpdate2(String auditId, String audit_state, String audit_result,String audit_desc,String mater_id,String audit_proce,String path,String userid) throws Exception{
		// TODO Auto-generated method stub
		String stime=DateUtil.getCurrentDate();
		int result=iMaterialDao.auditUpdate(auditId,audit_state,audit_result,audit_desc,userid,stime);
		List maxProceId =iMaterialDao.queryMaxProce_desc();
		String maxproceId=maxProceId.get(0).toString();
		if(audit_proce.equals(maxproceId)&&CommonSence.AUDITED.equals(audit_state)&&CommonSence.AUDITED_PASS.equals(audit_result)){
			//迁移物料
			List list = iMaterialDao.queryMaterById(mater_id);
			String materName=list.get(0).toString();
			//项目路径
			String tempUrl=System.getProperty("user.dir").replace("bin", "webapps");
//			temp
			String oldPath=path+"/"+materName;
			String newPath=tempUrl+"/resource/"+materName;
//			String newPath=material_remote_location+"/"+materName;
			   SFTP.copyFile(oldPath, newPath);
				File file = new File(path,materName);
				/**
				 * 删除缓存中的物料
				 * 更新物料表中的URL地址
				 * 后续开发
				 * 
				 */
				if(file.exists()){
					file.delete();
				}
//				iMaterialDao.uploadMaterTempPath(mater_id,serverhttp+"resource/"+materName);
				iMaterialDao.uploadMaterTempPath(mater_id,"resource/"+materName);
//				iMaterialDao.uploadMaterState(mater_id);
			}else{
				iMaterialDao.uploadMaterState(audit_result, audit_proce, mater_id);
			}
		return result;			
	}
	public List<JSONObject> getAuditInfo(String materId) {
		// TODO Auto-generated method stub
		List<JSONObject> result=iMaterialDao.getAuditInfo(materId);
		return result;
	}
	public List<JSONObject> getAuditUser(String UserId){
		// TODO Auto-generated method stub
		List<JSONObject> result=iMaterialDao.getAuditUser(UserId);
		return result;
	}
	
	public List<JSONObject> getSubUser(String UserId) {
		// TODO Auto-generated method stub
		return iMaterialDao.getSubUser(UserId);
	}
	
	public int saveOrUpdateAudituser(List<AUDIT_USERINFO> list,String userid){
		int t=iMaterialDao.deleteAuditUser(userid);
		int result=0;
		for(AUDIT_USERINFO em:list){
			result+=iMaterialDao.saveOrUpdateAudituser(em);
		}
		return result-t;
	}

	public List<JSONObject> getAuditChecked(String UserId) {
		// TODO Auto-generated method stub
		return iMaterialDao.getAuditChecked(UserId);
	}
	public int deleteMaterInfo(List<String> params,String path,List<String> files) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<files.size();i++){
			File file = new File(path,files.get(i));
			if(file.exists()){
				file.delete();
			}
		}
		return iMaterialDao.deleteMaterInfo(params);
	}
	public List checkiPassAudit(String audit_proce,String mater_id) {
		// TODO Auto-generated method stub
		return iMaterialDao.checkiPassAudit(audit_proce,mater_id);
	}
//	public int uploadMaterTempPath(String materid, String tempURL) {
//		// TODO Auto-generated method stub
//		return iMaterialDao.uploadMaterTempPath(materid,tempURL);
//	}
	public boolean checkAllPassAudit(String mater_id) {
		// TODO Auto-generated method stub
		List<JSONObject> list=iMaterialDao.checkAllPassAudit(mater_id);
		boolean flag=true;
		for(JSONObject json:list){
			String audit_state=json.getString("audit_state");
			String audit_result=json.getString("audit_result");
			if(!CommonSence.AUDITED.equals(audit_state)||!CommonSence.AUDITED_PASS.equals(audit_result)){
				flag=false;
			}  
		}
		return flag;
	}
	@Override
	public List queryMaterType() {
		// TODO Auto-generated method stub
		return iMaterialDao.queryMaterType();
	}
	@Override
	public List queryMaterCreator() {
		// TODO Auto-generated method stub
		return iMaterialDao.queryMaterCreator();
	}
	@Override
	public List queryMaterSize() {
		// TODO Auto-generated method stub
		return iMaterialDao.queryMaterSize();
	}




}
