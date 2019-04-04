package com.hik.framework.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;
import com.hik.app.entity.TIMESETTING;
import com.hik.framework.service.IMenuService;
import com.hik.framework.service.ITimeSetService;
import com.hik.framework.service.IUserService;
import com.hik.framework.utils.CommonUtil;
import com.hik.framework.utils.LogCommon;
import com.hik.framework.utils.Page;
import com.hik.interceptor.HikLog;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/timeSetController")
public class TimeSetController extends BaseController{
    private final Log log = LogFactory.getLog(TimeSetController.class); 
	
	@Autowired(required=true)
	private ITimeSetService iTimeSetService;
	
	
	
	@RequestMapping("/settingManger")
    public String settingManger(HttpServletRequest request,HttpServletResponse response){
    	return "systemManger/settingManger/settingManger";
    }
	@RequestMapping("/getTime")
	@HikLog(content="查询时间设置",curd=LogCommon.QUERY)
	public String getUser(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iTimeSetService.getTimeSet();
//			this.returnResponse(response, result);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);			
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	@RequestMapping("/editTime")
	@HikLog(content="编辑时间设置",curd=LogCommon.UPDATE)
	public String editUser(HttpServletRequest request,HttpServletResponse response){
		try{
			 String id=request.getParameter("id");
			 String name=request.getParameter("name");
			 String starttime=request.getParameter("starttime");
			 String endtime=request.getParameter("endtime");
			 String descr=request.getParameter("descr");
			 TIMESETTING sysUser = new TIMESETTING();
			 sysUser.setID(id);
			 sysUser.setNAME(name);
			 sysUser.setSTARTTIME(starttime);
			 sysUser.setENDTIME(endtime);
			 sysUser.setDESCR(descr);
			 int result = iTimeSetService.editTimeSet(sysUser);
			this.setResultInfo(UPDATE_SUCCESS_INFO, result);			
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(UPDATE_FAILURE_INFO, null);
			}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}

	@RequestMapping("/addTime")
	@HikLog(content="新增时间设置",curd=LogCommon.NEWADD)
	public String addUser(HttpServletRequest request,HttpServletResponse response){
		try{
			//用户基本信息
			 String name=request.getParameter("name");
			 String starttime=request.getParameter("starttime");
			 String endtime=request.getParameter("endtime");
			 String descr=request.getParameter("descr");
			 TIMESETTING sysUser = new TIMESETTING();
			 sysUser.setNAME(name);
			 sysUser.setSTARTTIME(starttime);
			 sysUser.setENDTIME(endtime);
			 sysUser.setDESCR(descr);
			 int result = iTimeSetService.addTimeSet(sysUser);
			 this.setResultInfo(ADD_SUCCESS_INFO, result);
		    } catch (Exception e) {
				e.printStackTrace();
				this.setResultInfo(ADD_FAILURE_INFO, null);
			}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/deleteTime")
	@HikLog(content="删除时间设置",curd=LogCommon.DELETE)
	public String deleteUser(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		try{
			String[] ids=id.split(",");
			int result = iTimeSetService.deleteTimeSet(ids);
			this.setResultInfo(DELETE_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/updateSMS")
	@HikLog(content="短信设置",curd=LogCommon.UPDATE)
	public String updateSMS(HttpServletRequest request,HttpServletResponse response){
		String smscontent=request.getParameter("smscontent");
		try{
			int result = iTimeSetService.udpateSMS(smscontent);
			this.setResultInfo(DELETE_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/querySMS")
	@HikLog(content="查询短信设置",curd=LogCommon.QUERY)
	public String querySMS(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iTimeSetService.querySMS();
			this.setResultInfo(DELETE_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	@RequestMapping("/querySKIN")
	@HikLog(content="查询皮肤管理设置",curd=LogCommon.QUERY)
	public String querySKIN(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iTimeSetService.querySKIN();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	/**
	 * 新增皮肤
	 * @return
	 */
	@RequestMapping("/insertSKIN")
	@HikLog(content="新增皮肤",curd=LogCommon.QUERY)
	public String insertSKIN(HttpServletRequest request,HttpServletResponse response){
		try{
			String id=request.getParameter("id");
			String skinname=request.getParameter("skinname");
			String descr=request.getParameter("descr");
			String name=request.getParameter("name");
			int result = iTimeSetService.insertSKIN(id,skinname,name,descr);
			this.setResultInfo(INSERT_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(INSERT_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/deleteSKIN")
	@HikLog(content="删除皮肤",curd=LogCommon.QUERY)
	public String delelteSKIN(HttpServletRequest request,HttpServletResponse response){
		try{
			String ids=request.getParameter("ids");
			String[] id = ids.split(",");
			int result = iTimeSetService.delelte(id);
			this.setResultInfo(DELETE_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	@RequestMapping("/getSKINById")
	public String getSKINById(HttpServletRequest request,HttpServletResponse response){
		try{
			String ids=request.getParameter("id");
			List result = iTimeSetService.getSKINById(ids);
			this.setResultInfo(DELETE_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	@RequestMapping("/queryModelPro")
	public String queryModelPro(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iTimeSetService.queryModelPro();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/saveModelPro")
	public String saveModelPro(HttpServletRequest request,HttpServletResponse response){
		try{
			String id=request.getParameter("id");
			String cid=request.getParameter("cid");
			String name=request.getParameter("name");
			String descr=request.getParameter("descr");
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userId");
			int result = iTimeSetService.saveModelPro(id,cid,name,descr,userid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/deleteModelPro")
	public String deleteModelPro(HttpServletRequest request,HttpServletResponse response){
		try{
			String ids=request.getParameter("ids");
			String[] id = ids.split(",");
			int result = iTimeSetService.deleteModelPro(id);
			this.setResultInfo(DELETE_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}

	@RequestMapping("/saveadvPro")
	public String saveadvPro(HttpServletRequest request,HttpServletResponse response){
		try{
			String id=request.getParameter("id");
			String pid=request.getParameter("pid");
			String name=request.getParameter("name");
			String leve=request.getParameter("leve");
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userId");
			int result = iTimeSetService.saveadvPro(id,pid,name,userid,leve);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/queryAdvPro")
	public String queryAdvPro(HttpServletRequest request,HttpServletResponse response){
		try{
			String leve=request.getParameter("leve");
			String pid=request.getParameter("pid");
			List result = iTimeSetService.queryAdvPro(pid,leve);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	@RequestMapping("/deleteAdvpro")
	public String deleteAdvpro(HttpServletRequest request,HttpServletResponse response){
		try{
			String ids=request.getParameter("ids");
			String[] id = ids.split(",");
			int result = iTimeSetService.deleteAdvpro(id);
			this.setResultInfo(DELETE_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(DELETE_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/savePutStragerPro")
	public String savePutStragerPro(HttpServletRequest request,HttpServletResponse response){
		try{
			String id=request.getParameter("id");
			String cid=request.getParameter("cid");
			String name=request.getParameter("name");
			String interval=request.getParameter("interval");
			String descr=request.getParameter("descr");
//			String pid=request.getParameter("pid");
//			String name=request.getParameter("name");
//			String leve=request.getParameter("leve");
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userId");
			int result = iTimeSetService.savePutStragerPro(id,cid,name,interval,descr,userid);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/queryPutStragerPro")
	public String queryPutStragerPro(HttpServletRequest request,HttpServletResponse response){
		try{
			List result = iTimeSetService.queryPutStragerPro();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	@RequestMapping("/deletePutStragerPro")
	public String deletePutStragerPro(HttpServletRequest request,HttpServletResponse response){
		try{
			String id=request.getParameter("id");
			int result = iTimeSetService.deletePutStragerPro(id);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return null;
	}
	
	@RequestMapping("/updateUserAuth")
	@HikLog(content="更新用户认证方式",curd=LogCommon.UPDATE)
	public void updateUserAuth(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			String state=request.getParameter("state");
			int result = iTimeSetService.updateUserAuth(state);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	@RequestMapping("/queryUserAuth")
	@HikLog(content="查询用户认证方式",curd=LogCommon.UPDATE)
	public void queryUserAuth(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			List result = iTimeSetService.queryUserAuth();
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
}
