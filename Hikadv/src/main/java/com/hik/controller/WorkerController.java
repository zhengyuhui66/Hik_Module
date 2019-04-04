package com.hik.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hik.framework.controller.ExportBaseController;
import com.hik.rfid.business.CancelOperation;
import com.hik.rfid.business.Inventory;
import com.hik.rfid.business.Read;
import com.hik.rfid.message.cmsactive.CancelOperationResp;
import com.hik.rfid.message.cmsactive.InventoryReqParams;
import com.hik.rfid.message.cmsactive.InventoryResp;
import com.hik.rfid.message.cmsactive.ReadReqParams;
import com.hik.service.WorkersServices;

@Controller
public class WorkerController extends ExportBaseController{
	
	@Autowired(required=false)
	private WorkersServices testService;
	
	@RequestMapping(value="/Wifi/androidTestGet",method= RequestMethod.GET)
	public void  androidHttp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject json=new JSONObject();
		try{
			Object arn=request.getAttribute("para");
			Object arn2=request.getParameter("para");
			log.info("paraget:"+arn2);

			json.accumulate("Result", "200");
			json.accumulate("Description", "this. is very wondfully");
			JSONObject data = new JSONObject();
			data.accumulate("a", "a1");
			data.accumulate("b", "b1");
			data.accumulate("c", "c1");
			data.accumulate("d", "d1");
			json.accumulate("Data",data);
//			this.setResultInfo(QUERY_SUCCESS_INFO, json);
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, json);
		return;
	}
	
	
	@RequestMapping(value="/Wifi/androidTestPost",method= RequestMethod.POST)
	public void  androidHttpost(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			Object  arn=request.getAttribute("para");
			Object arn2=request.getParameter("para");
			log.info("parapost:"+arn2);
			this.setResultInfo(QUERY_SUCCESS_INFO, "successzyhpsot");
	    } catch (Exception e) {
			e.printStackTrace();
			this.setResultInfo(QUERY_FAILURE_INFO, null);
		}
		this.returnResponse(response, this.getResultInfo());
		return;
	}
	@RequestMapping("/rfid")
	public void rfid(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		testService.add();
		String deviceid=request.getParameter("id");
		
//		GetAnaInfo anaInfo= new GetAnaInfo();
//		GetAnaInfoResp gaiResp = anaInfo.getAnaInfo(deviceid);
//		System.out.println("==========天线信息:"+gaiResp.toXMLString());
//		
//		GetReaderStatus reg = new GetReaderStatus();
//		GetReaderStatusResp result=reg.getReaderStatus(deviceid);
//		System.out.println("==========读写器状态:"+result.toXMLString());
//		
//		
//		SetAntenna antenna = new SetAntenna();
//		SetAntennaReqParams antennaReqParams = new SetAntennaReqParams();
//		List<Map> antennas = new ArrayList<Map>();
//		for(int i=0;i<5;i++){
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("AnaID",i+"");
//			map.put("AnaStatus","0/1");
//			map.put("Power","30");
//			map.put("DwellTime","1000");
//			map.put("InvCycle","2000");
//			antennas.add(map);
//		}
//		antennaReqParams.setAntenna(antennas);
//		SetAntennaResp antennaResp = antenna.getSetAntennaResp(deviceid, antennaReqParams);
//		System.out.println("==========设置天线信息:"+antennaResp.toXMLString());
////
//		
//		GpoControl control = new GpoControl();
//		List<Map> ates = new ArrayList<Map>();
//		for(int i=0;i<5;i++){
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("GpoPin",i+"");
//			map.put("EleLevel",i%2+"");
//			ates.add(map);
//		}
//		GpoControlReqParams gpoControlReqParams = new GpoControlReqParams(ates);
//		GpoControlResp controlResp = control.getGpoControlResp(deviceid, gpoControlReqParams);
//		System.out.println("==========GPO信息:"+controlResp.toXMLString());
//
//		GetGpiStatus getGpiStatus = new GetGpiStatus();
//		GetGpiStatusResp getGpiStatusResp = getGpiStatus.getGpoControlResp(deviceid);
//		System.out.println("==========GPI信息:"+getGpiStatusResp.toXMLString());
//
//		GetGpoStatus getGpoStatus = new GetGpoStatus();
//		GetGpoStatusResp getGpoStatusResp = getGpoStatus.getGpoControlResp(deviceid);
//		System.out.println("==========GPO信息:"+getGpoStatusResp.toXMLString());
//
//		GetSWR getSWR = new GetSWR();
//		GetSWRResp getSWRResp = getSWR.getSWRResp(deviceid);
//		System.out.println("==========查询主播比信息:"+getSWRResp.toXMLString());
//
//		TimeSync sync = new TimeSync();
//		TimeSyncReqParams timeSyncReqParams = new TimeSyncReqParams("090710522013");
//		TimeSyncResp resp = sync.getTimeSyncResp(deviceid, timeSyncReqParams);
//		System.out.println("==========时间异步信息:"+resp.toXMLString());
//
//		CancelOperation cancelOperation = new CancelOperation();
//		CancelOperationResp cancelOperationResp = cancelOperation.getCancelOperationRespInfo(deviceid);
//		System.out.println("==========取消操作信息:"+cancelOperationResp.toXMLString());
//
//		Kill kill = new Kill();
//		KillReqParams killReqParams = new KillReqParams("0", "111", "111", null, "11221222", "8");
//		KillResp killResp = kill.getKillRespInfo(deviceid, killReqParams);
//		System.out.println("==========灭活信息:"+killResp.toXMLString());
////
//		BlockErase blockErase = new BlockErase();
//		BlockEraseReqParams blockEraseReqParams = new BlockEraseReqParams("0-1", "100", "0-3", "1", "1", "111", "0-3", "11221222","8");
//		BlockEraseResp blockEraseResp = blockErase.getBlockEraseInfo(deviceid, blockEraseReqParams);
//		System.out.println("==========块擦信息:"+blockEraseResp.toXMLString());
//
//		ReadSingle readSingle = new ReadSingle();
//		ReadSingleReqParams readSingleReqParams = new ReadSingleReqParams("0-1", "0-3", "1", "1", "111", "0-3", "11221222","8", "0","0");
//		ReadSingleResp readSingleResp = readSingle.getKillRespInfo(deviceid, readSingleReqParams);
//		System.out.println("==========单读信息:"+readSingleResp.toXMLString());

		Read read = new Read();
		ReadReqParams readReqParams = new ReadReqParams("0-1", "0-3", "1", "1", "111", "0-3", "11221222","8", "0","0");
		read.getReadRespInfo(deviceid, readReqParams);

//		WriteSingle writeSingle = new WriteSingle();
//		List<Map<String,String>> dataInfo = new ArrayList<Map<String,String>>();
//		for(int i=0;i<10;i++){
//			Map map = new HashMap();
//			map.put("Data", 1);
//			dataInfo.add(map);
//		}
//		WriteSingleReqParams writeSingleReqParams = new WriteSingleReqParams("0",  "0-3", "1", "1", dataInfo, "111", "0-3", "11221222","8", "0", "0");
//		WriteSingleResp writeSingleResp = writeSingle.getWriteSingleRespInfo(deviceid, writeSingleReqParams);
//		System.out.println("==========单写信息:"+writeSingleResp.toXMLString());
//
//		Write write = new Write();
//		List<Map<String,String>> dataInfo2 = new ArrayList<Map<String,String>>();
//		for(int i=0;i<10;i++){
//			Map map = new HashMap();
//			map.put("Data", 1);
//			dataInfo2.add(map);
//		}
//		WriteReqParams writeReqParams = new WriteReqParams("0",  "0-3", "1", "1", dataInfo2, "111", "0-3", "11221222","8", "0", "0");
//		WriteResp writeResp = write.getWriteRespInfo(deviceid, writeReqParams);
//		System.out.println("==========写信息:"+writeResp.toXMLString());
//
//		
//		BlockWriteSingle blockWriteSingle = new BlockWriteSingle();
//		List<Map<String,String>> dataInfo3 = new ArrayList<Map<String,String>>();
//		for(int i=0;i<10;i++){
//			Map map = new HashMap();
//			map.put("Data", 1);
//			dataInfo3.add(map);
//		}
//		BlockWriteSingleReqParams blockWriteSingleReqParams = new BlockWriteSingleReqParams("0",  "0-3", "1", "1", dataInfo3,"111", "0-3", "11221222","8");
//		BlockWriteSingleResp blockWriteSingleResp = blockWriteSingle.getBlockWriteSingleRespInfo(deviceid, blockWriteSingleReqParams);
//		System.out.println("==========块写信息:"+blockWriteSingleResp.toXMLString());
//
//		OperQTnone operQTnone = new OperQTnone();
//		QTnoneReqParams qTnoneReqParams = new QTnoneReqParams("0", "1", "1", "0", "111", "0-3", "11221222", "8");
//		QTnoneResp qTnoneResp = operQTnone.getQTnoneRespInfo(deviceid, qTnoneReqParams);
//		System.out.println("==========QT操作信息:"+qTnoneResp.toXMLString());

	 }
	
	
//	@Autowired
//	private MemberDao memberDao;
	
//	@Autowired
//	private MyUserRepository myUserRepository;
	@RequestMapping("/inventory")
	public void add(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String deviceid=request.getParameter("id");
		Inventory inventory = new Inventory();
		InventoryReqParams params = new InventoryReqParams("0", "0", "1");
		inventory.getInventoryInfo(deviceid, params);
		System.out.println("设置连续判断");
//		CancelOperationResp resp = new CancelOperation().getCancelOperationRespInfo(deviceid);
//		System.out.println("取消操作:"+ir.toXMLString());
//		CancelOperationReq cancelOperationReq =new CancelOperationReq(requestMessage);
	 }
	@RequestMapping("/inventory2")
	public void add2(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String deviceid=request.getParameter("id");
		Inventory inventory = new Inventory();
		InventoryReqParams params = new InventoryReqParams("0", "0", "1");
		InventoryResp inventoryResp=inventory.getInventorySingleInfo(deviceid, params);
		System.out.println("单盘点结果"+inventoryResp);

	 }
	
	@RequestMapping("/cancel")
	public void add3(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String deviceid=request.getParameter("id");
		CancelOperationResp resp = new CancelOperation().getCancelOperationRespInfo(deviceid);
//		System.out.println("取消操作:"+ir.toXMLString());
//		CancelOperationReq cancelOperationReq =new CancelOperationReq(requestMessage);

	 }

	@RequestMapping("/invoke")
	public void invoke(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		testService.add();
		String deviceid=request.getParameter("id");
		CancelOperationResp resp = new CancelOperation().getCancelOperationRespInfo(deviceid);
		System.out.println("取消操作:"+resp.toXMLString());
	}
	@RequestMapping("/add2")
	public void add2() throws Exception {
//		boolean b2 = redisBaseDao.add(new RedisBean("27","67ddd"));
//		boolean b=redisBaseDao.add(new RedisBean(PROCEDURCES.REDISTIMEOUT, "3322222"));
//		myUserRepository.set("25", "zhengyuh");
//		if(b2){
//			log.info("====>添加成功");			
//		}else{
//			log.info("====>添加失败");	
//		}
	    return;
	 }
	@RequestMapping("/update")
	public void update() throws Exception {
//		testService.updateId();
//		testService.tupdateid2();
//		testService.tupdateid();
////		boolean b=memberDao.update(new Member("24", ":nickname"));
//		boolean b=redisBaseDao.update(new RedisBean("25","5555555"));
////		myUserRepository.set("25", "zhengyuh");
//		if(b){
//			log.info("====>更新成功");			
//		}else{
//			log.info("====>更新失败");	
//		}
	    return;
	 }
	
	@RequestMapping("/get")
	public void get() throws Exception {
//		Member m=memberDao.get("24");
//		RedisBean m=redisBaseDao.get("25");
//		if(m!=null){
//			log.info("====>ID:"+m.getKey()+"=========>"+m.getValue());			
//		}else{
//			log.info("Redis数据为空");
//		}
//		String value=myUserRepository.get("25");
//		log.info("====>ID:25=========>"+value);
	    return;
	 }
	
//	@RequestMapping("/add")
//	public void add() throws Exception {
////		Member m=memberDao.get("24");
//		RedisBean m=redisBaseDao.get("25");
//		if(m!=null){
//			log.info("====>ID:"+m.getKey()+"=========>"+m.getValue());			
//		}else{
//			log.info("Redis数据为空");
//		}
////		String value=myUserRepository.get("25");
////		log.info("====>ID:25=========>"+value);
//	    return;
//	 }
	
	@RequestMapping("/delete")
	public void delete() throws Exception {
//		memberDao.delete("24");
//		redisBaseDao.delete("25");
		log.info("====>删除成功");
	    return;
	 }

	
	@RequestMapping("/Wifi/poi")
	public void  exportPoi(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("helloworld");  
        List<JSONObject> list = new ArrayList<JSONObject>();  
        
        for(int i=0;i<20;i++){
	        JSONObject json = new JSONObject();
	        json.accumulate("id", i);
	        json.accumulate("name","Jeelon"+i);
	        json.accumulate("age",21);
	        json.accumulate("address","贵阳市");
	        json.accumulate("tel", "15085943968");
	        json.accumulate("sex","男");
	        list.add(json);
        }
//        Student student1 = new Student(1, "Jeelon", 21, "贵阳市 ", "15085943968",'男');  
//        Student student2 = new Student(2, "fengxu", 21, "贵阳市 ", "15085943968",'男');  
//        Student student3 = new Student(3, "jerry", 21, "贵阳市 ", "15085943968",'男');  
//        Student student4 = new Student(4, "merry", 21, "贵阳市 ", "15085943968",'男');  
//        Student student5 = new Student(5, "lariiy", 21, "贵阳市 ", "15085943968",'男');  
//        Student student6 = new Student(6, "monr", 21, "贵阳市 ", "15085943968",'男');  
//        Student student7 = new Student(7, "join", 21, "贵阳市 ", "15085943968",'男');  
//        Student student8 = new Student(8, "jay chou", 21, "贵阳市 ", "15085943968", '男');  
//        Student student9 = new Student(9, "laonb", 21, "贵阳市 ", "15085943968",'男');  
//        Student student0 = new Student(0, "mudg", 21, "贵阳市 ", "15085943968", '男');  
  
//        list.add(student1);  
//        list.add(student2);  
//        list.add(student3);  
//        list.add(student4);  
//        list.add(student5);  
//        list.add(student6);  
//        list.add(student7);  
//        list.add(student8);  
//        list.add(student9);  
//        list.add(student0);  
        JSONObject rowtitles=new JSONObject();
        rowtitles.accumulate("id", "序号");
        rowtitles.accumulate("name", "名称");
        rowtitles.accumulate("age", "年纪");
        rowtitles.accumulate("address", "地址");
        rowtitles.accumulate("tel", "手机号");
//        rowtitles.accumulate("sex", "性别");
        export(response, list, rowtitles, "信息表");
//        String fileName = "导出Excel.xls";  
//        fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
//        response.reset();  
//        response.setHeader("Content-Disposition", "attachment;filename="  
//                + fileName);// 指定下载的文件名  
//        response.setContentType("application/vnd.ms-excel");  
//        response.setHeader("Pragma", "no-cache");  
//        response.setHeader("Cache-Control", "no-cache");  
//        response.setDateHeader("Expires", 0);  
//        OutputStream output = response.getOutputStream();  
//        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
//  
//        // 定义单元格报头  
//        String worksheetTitle = "Excel导出Student信息";  
//  
//        HSSFWorkbook wb = new HSSFWorkbook();  
//  
//        // 创建单元格样式  
//        HSSFCellStyle cellStyleTitle = wb.createCellStyle();  
//        // 指定单元格居中对齐  
//        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//        // 指定单元格垂直居中对齐  
//        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//        // 指定当单元格内容显示不下时自动换行  
//        cellStyleTitle.setWrapText(true);  
//        // ------------------------------------------------------------------  
//        HSSFCellStyle cellStyle = wb.createCellStyle();  
//        // 指定单元格居中对齐  
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//        // 指定单元格垂直居中对齐  
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//        // 指定当单元格内容显示不下时自动换行  
//        cellStyle.setWrapText(true);  
//        // ------------------------------------------------------------------  
//        // 设置单元格字体  
//        HSSFFont font = wb.createFont();  
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
//        font.setFontName("宋体");  
//        font.setFontHeight((short) 200);  
//        cellStyleTitle.setFont(font);  
//  
//        // 工作表名  
//        String id = "id";
//        String name = "name";
//        String age = "age";
//        String address = "address";
//        String tel = "tel";  
//        String sex = "sex";  
//  
//        HSSFSheet sheet = wb.createSheet();  
//        ExportExcel exportExcel = new ExportExcel(wb, sheet);  
//        // 创建报表头部  
//        exportExcel.createNormalHead(worksheetTitle, 6);  
//        // 定义第一行  
//        HSSFRow row1 = sheet.createRow(1);  
//        HSSFCell cell1 = row1.createCell(0);  
//  
//        //第一行第一列  
//        cell1 = row1.createCell(0);
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(id));  
//        //第一行第er列  
//        cell1 = row1.createCell(1);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(name));  
//  
//        //第一行第san列  
//        cell1 = row1.createCell(2);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(age));  
//  
//        //第一行第si列  
//        cell1 = row1.createCell(3);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(address));  
//  
//        //第一行第wu列  
//        cell1 = row1.createCell(4);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(tel));  
//  
//        //第一行第liu列  
//        cell1 = row1.createCell(5);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(sex));  
//  
//        //定义第二行  
//        HSSFRow row = null;//sheet.createRow(2);  
//        HSSFCell cell = null;//row.createCell(1);  
//        Student student = new Student();  
//        for (int i = 0; i < list.size(); i++) {  
//            student = list.get(i);  
//            row = sheet.createRow(i + 2);  
//  
//            cell = row.createCell(0);  
//            cell.setCellStyle(cellStyle);  
//            cell.setCellValue(new HSSFRichTextString(student.getId() + ""));  
//              
//            cell = row.createCell(1);  
//            cell.setCellStyle(cellStyle);  
//            cell.setCellValue(new HSSFRichTextString(student.getName()));  
//              
//            cell = row.createCell(2);  
//            cell.setCellStyle(cellStyle);  
//            cell.setCellValue(new HSSFRichTextString(student.getAge() + ""));  
//              
//            cell = row.createCell(3);  
//            cell.setCellStyle(cellStyle);  
//            cell.setCellValue(new HSSFRichTextString(student.getAddress() + ""));  
//              
//            cell = row.createCell(4);  
//            cell.setCellStyle(cellStyle);  
//            cell.setCellValue(new HSSFRichTextString(student.getTel()));  
//              
//            cell = row.createCell(5);  
//            cell.setCellValue(new HSSFRichTextString(String.valueOf(student.getSex())));  
//            cell.setCellStyle(cellStyle);  
//              
//        }  
//        try {  
//            bufferedOutPut.flush();  
//            wb.write(bufferedOutPut);  
//            bufferedOutPut.close();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//            System.out.println("Output   is   closed ");  
//        } finally {  
//            list.clear();  
//        }  
	 }
	
	
}
