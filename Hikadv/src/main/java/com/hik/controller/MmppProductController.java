package com.hik.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hik.app.entity.MMPPPRODUCT;
import com.hik.framework.controller.BaseController;
import com.hik.framework.utils.Page;
import com.hik.service.ImmppProductService;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/mmppProductController")
public class MmppProductController  extends BaseController{
	
	@Autowired
	private ImmppProductService immppProductService;
    
	@RequestMapping("/mmppProduct")
    public String materiel(HttpServletRequest request,HttpServletResponse response){
    	return "putManger/mmppProduct";
    }
	/**
	 * 分页得到所有的广告
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getmmppgroup")
	public void getmmppgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			int start=getStartOrLimit(request, "start");
			int limit=getStartOrLimit(request, "limit");
			String searchStr= request.getParameter("searchStr");
			Page page = immppProductService.getMmppgroup(start, limit, searchStr);//.getAdvertgroup(start, limit,searchStr);
			this.returnResponse(response, JSONObject.fromObject(page));
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
			this.returnResponse(response, this.getResultInfo());
		}
		return;
	 }
	/**
	 * 新增或者修改广告集
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveOrupdatemmppgroup")
	public void saveOrupdatemmppgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("id");
			String mmppid1=request.getParameter("mmppid1");
			String mmppid2=request.getParameter("mmppid2");
			String mmppid3=request.getParameter("mmppid3");
			String mmppid4=request.getParameter("mmppid4");
			String mmppid5=request.getParameter("mmppid5");
			String mmppid6=request.getParameter("mmppid6");
			String mmppid7=request.getParameter("mmppid7");
			String mmppid8=request.getParameter("mmppid8");
			String mmppid9=request.getParameter("mmppid9");
//			String creator=request.getParameter("creator");
			String creatime=request.getParameter("creatime");
//			String modifier=request.getParameter("modifier");
			String modifytime=request.getParameter("modifytime");
			String playstrager=request.getParameter("playstrager");
			String playpristrager=request.getParameter("playpristrager");
			String descr=request.getParameter("descr");
			String name=request.getParameter("name");
			HttpSession session = request.getSession();
			String userid = (String) session.getAttribute("userId");
			MMPPPRODUCT mmppproduct = new MMPPPRODUCT(id, mmppid1, mmppid2, mmppid3, mmppid4, mmppid5, mmppid6, mmppid7, mmppid8, mmppid9, userid, creatime, userid, modifytime, playstrager, playpristrager, descr, name);
			
			int result = immppProductService.saveOrupdateMmppgroup(mmppproduct);//.saveOrupdateAdvertgroup(advgroup);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }
	
	/**
	 * 删除广告集
	 * @param request  
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deletemmppgroup")
	public void deletemmppgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String id=request.getParameter("ids");
			String[] ids=id.split(",");
//			List ifid = immppProductService.queryGroupPutproduct(ids);//.queryGroupPutproduct(ids);
//			if(ifid!=null&&ifid.size()>0){
//				String _result=ifid.get(0).toString();
//				if(Integer.parseInt(_result)>0){
//					this.setResultInfo(QUERY_FAILURE_INFO, _result);					
//				}else{
			String result = immppProductService.deleteMmppgroup(ids);//.deleteAdvertgroup(ids);
			this.setResultInfo(QUERY_SUCCESS_INFO, result);
//				}
//			}
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	 }


}
