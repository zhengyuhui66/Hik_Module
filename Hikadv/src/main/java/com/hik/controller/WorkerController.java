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
//		System.out.println("==========������Ϣ:"+gaiResp.toXMLString());
//		
//		GetReaderStatus reg = new GetReaderStatus();
//		GetReaderStatusResp result=reg.getReaderStatus(deviceid);
//		System.out.println("==========��д��״̬:"+result.toXMLString());
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
//		System.out.println("==========����������Ϣ:"+antennaResp.toXMLString());
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
//		System.out.println("==========GPO��Ϣ:"+controlResp.toXMLString());
//
//		GetGpiStatus getGpiStatus = new GetGpiStatus();
//		GetGpiStatusResp getGpiStatusResp = getGpiStatus.getGpoControlResp(deviceid);
//		System.out.println("==========GPI��Ϣ:"+getGpiStatusResp.toXMLString());
//
//		GetGpoStatus getGpoStatus = new GetGpoStatus();
//		GetGpoStatusResp getGpoStatusResp = getGpoStatus.getGpoControlResp(deviceid);
//		System.out.println("==========GPO��Ϣ:"+getGpoStatusResp.toXMLString());
//
//		GetSWR getSWR = new GetSWR();
//		GetSWRResp getSWRResp = getSWR.getSWRResp(deviceid);
//		System.out.println("==========��ѯ��������Ϣ:"+getSWRResp.toXMLString());
//
//		TimeSync sync = new TimeSync();
//		TimeSyncReqParams timeSyncReqParams = new TimeSyncReqParams("090710522013");
//		TimeSyncResp resp = sync.getTimeSyncResp(deviceid, timeSyncReqParams);
//		System.out.println("==========ʱ���첽��Ϣ:"+resp.toXMLString());
//
//		CancelOperation cancelOperation = new CancelOperation();
//		CancelOperationResp cancelOperationResp = cancelOperation.getCancelOperationRespInfo(deviceid);
//		System.out.println("==========ȡ��������Ϣ:"+cancelOperationResp.toXMLString());
//
//		Kill kill = new Kill();
//		KillReqParams killReqParams = new KillReqParams("0", "111", "111", null, "11221222", "8");
//		KillResp killResp = kill.getKillRespInfo(deviceid, killReqParams);
//		System.out.println("==========�����Ϣ:"+killResp.toXMLString());
////
//		BlockErase blockErase = new BlockErase();
//		BlockEraseReqParams blockEraseReqParams = new BlockEraseReqParams("0-1", "100", "0-3", "1", "1", "111", "0-3", "11221222","8");
//		BlockEraseResp blockEraseResp = blockErase.getBlockEraseInfo(deviceid, blockEraseReqParams);
//		System.out.println("==========�����Ϣ:"+blockEraseResp.toXMLString());
//
//		ReadSingle readSingle = new ReadSingle();
//		ReadSingleReqParams readSingleReqParams = new ReadSingleReqParams("0-1", "0-3", "1", "1", "111", "0-3", "11221222","8", "0","0");
//		ReadSingleResp readSingleResp = readSingle.getKillRespInfo(deviceid, readSingleReqParams);
//		System.out.println("==========������Ϣ:"+readSingleResp.toXMLString());

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
//		System.out.println("==========��д��Ϣ:"+writeSingleResp.toXMLString());
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
//		System.out.println("==========д��Ϣ:"+writeResp.toXMLString());
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
//		System.out.println("==========��д��Ϣ:"+blockWriteSingleResp.toXMLString());
//
//		OperQTnone operQTnone = new OperQTnone();
//		QTnoneReqParams qTnoneReqParams = new QTnoneReqParams("0", "1", "1", "0", "111", "0-3", "11221222", "8");
//		QTnoneResp qTnoneResp = operQTnone.getQTnoneRespInfo(deviceid, qTnoneReqParams);
//		System.out.println("==========QT������Ϣ:"+qTnoneResp.toXMLString());

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
		System.out.println("���������ж�");
//		CancelOperationResp resp = new CancelOperation().getCancelOperationRespInfo(deviceid);
//		System.out.println("ȡ������:"+ir.toXMLString());
//		CancelOperationReq cancelOperationReq =new CancelOperationReq(requestMessage);
	 }
	@RequestMapping("/inventory2")
	public void add2(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String deviceid=request.getParameter("id");
		Inventory inventory = new Inventory();
		InventoryReqParams params = new InventoryReqParams("0", "0", "1");
		InventoryResp inventoryResp=inventory.getInventorySingleInfo(deviceid, params);
		System.out.println("���̵���"+inventoryResp);

	 }
	
	@RequestMapping("/cancel")
	public void add3(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String deviceid=request.getParameter("id");
		CancelOperationResp resp = new CancelOperation().getCancelOperationRespInfo(deviceid);
//		System.out.println("ȡ������:"+ir.toXMLString());
//		CancelOperationReq cancelOperationReq =new CancelOperationReq(requestMessage);

	 }

	@RequestMapping("/invoke")
	public void invoke(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		testService.add();
		String deviceid=request.getParameter("id");
		CancelOperationResp resp = new CancelOperation().getCancelOperationRespInfo(deviceid);
		System.out.println("ȡ������:"+resp.toXMLString());
	}
	@RequestMapping("/add2")
	public void add2() throws Exception {
//		boolean b2 = redisBaseDao.add(new RedisBean("27","67ddd"));
//		boolean b=redisBaseDao.add(new RedisBean(PROCEDURCES.REDISTIMEOUT, "3322222"));
//		myUserRepository.set("25", "zhengyuh");
//		if(b2){
//			log.info("====>��ӳɹ�");			
//		}else{
//			log.info("====>���ʧ��");	
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
//			log.info("====>���³ɹ�");			
//		}else{
//			log.info("====>����ʧ��");	
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
//			log.info("Redis����Ϊ��");
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
//			log.info("Redis����Ϊ��");
//		}
////		String value=myUserRepository.get("25");
////		log.info("====>ID:25=========>"+value);
//	    return;
//	 }
	
	@RequestMapping("/delete")
	public void delete() throws Exception {
//		memberDao.delete("24");
//		redisBaseDao.delete("25");
		log.info("====>ɾ���ɹ�");
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
	        json.accumulate("address","������");
	        json.accumulate("tel", "15085943968");
	        json.accumulate("sex","��");
	        list.add(json);
        }
//        Student student1 = new Student(1, "Jeelon", 21, "������ ", "15085943968",'��');  
//        Student student2 = new Student(2, "fengxu", 21, "������ ", "15085943968",'��');  
//        Student student3 = new Student(3, "jerry", 21, "������ ", "15085943968",'��');  
//        Student student4 = new Student(4, "merry", 21, "������ ", "15085943968",'��');  
//        Student student5 = new Student(5, "lariiy", 21, "������ ", "15085943968",'��');  
//        Student student6 = new Student(6, "monr", 21, "������ ", "15085943968",'��');  
//        Student student7 = new Student(7, "join", 21, "������ ", "15085943968",'��');  
//        Student student8 = new Student(8, "jay chou", 21, "������ ", "15085943968", '��');  
//        Student student9 = new Student(9, "laonb", 21, "������ ", "15085943968",'��');  
//        Student student0 = new Student(0, "mudg", 21, "������ ", "15085943968", '��');  
  
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
        rowtitles.accumulate("id", "���");
        rowtitles.accumulate("name", "����");
        rowtitles.accumulate("age", "���");
        rowtitles.accumulate("address", "��ַ");
        rowtitles.accumulate("tel", "�ֻ���");
//        rowtitles.accumulate("sex", "�Ա�");
        export(response, list, rowtitles, "��Ϣ��");
//        String fileName = "����Excel.xls";  
//        fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
//        response.reset();  
//        response.setHeader("Content-Disposition", "attachment;filename="  
//                + fileName);// ָ�����ص��ļ���  
//        response.setContentType("application/vnd.ms-excel");  
//        response.setHeader("Pragma", "no-cache");  
//        response.setHeader("Cache-Control", "no-cache");  
//        response.setDateHeader("Expires", 0);  
//        OutputStream output = response.getOutputStream();  
//        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
//  
//        // ���嵥Ԫ��ͷ  
//        String worksheetTitle = "Excel����Student��Ϣ";  
//  
//        HSSFWorkbook wb = new HSSFWorkbook();  
//  
//        // ������Ԫ����ʽ  
//        HSSFCellStyle cellStyleTitle = wb.createCellStyle();  
//        // ָ����Ԫ����ж���  
//        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//        // ָ����Ԫ��ֱ���ж���  
//        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//        // ָ������Ԫ��������ʾ����ʱ�Զ�����  
//        cellStyleTitle.setWrapText(true);  
//        // ------------------------------------------------------------------  
//        HSSFCellStyle cellStyle = wb.createCellStyle();  
//        // ָ����Ԫ����ж���  
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//        // ָ����Ԫ��ֱ���ж���  
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//        // ָ������Ԫ��������ʾ����ʱ�Զ�����  
//        cellStyle.setWrapText(true);  
//        // ------------------------------------------------------------------  
//        // ���õ�Ԫ������  
//        HSSFFont font = wb.createFont();  
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
//        font.setFontName("����");  
//        font.setFontHeight((short) 200);  
//        cellStyleTitle.setFont(font);  
//  
//        // ��������  
//        String id = "id";
//        String name = "name";
//        String age = "age";
//        String address = "address";
//        String tel = "tel";  
//        String sex = "sex";  
//  
//        HSSFSheet sheet = wb.createSheet();  
//        ExportExcel exportExcel = new ExportExcel(wb, sheet);  
//        // ��������ͷ��  
//        exportExcel.createNormalHead(worksheetTitle, 6);  
//        // �����һ��  
//        HSSFRow row1 = sheet.createRow(1);  
//        HSSFCell cell1 = row1.createCell(0);  
//  
//        //��һ�е�һ��  
//        cell1 = row1.createCell(0);
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(id));  
//        //��һ�е�er��  
//        cell1 = row1.createCell(1);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(name));  
//  
//        //��һ�е�san��  
//        cell1 = row1.createCell(2);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(age));  
//  
//        //��һ�е�si��  
//        cell1 = row1.createCell(3);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(address));  
//  
//        //��һ�е�wu��  
//        cell1 = row1.createCell(4);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(tel));  
//  
//        //��һ�е�liu��  
//        cell1 = row1.createCell(5);  
//        cell1.setCellStyle(cellStyleTitle);  
//        cell1.setCellValue(new HSSFRichTextString(sex));  
//  
//        //����ڶ���  
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
