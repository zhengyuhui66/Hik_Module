package com.hik.framework.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.hik.framework.utils.ExportExcel;
import net.sf.json.JSONObject;


public class ExportBaseController  extends BaseController{
	public void export(HttpServletResponse response,List<JSONObject> dataList,JSONObject rowtitles,String worksheetTitle) throws IOException{
		if(dataList==null&&dataList.size()==0){
			this.returnResponse(response, "����Ϊ��");
		}
		if(rowtitles==null&&rowtitles.size()==0){
			this.returnResponse(response, "����Ϊ��");
		}
		String fileName =worksheetTitle+".xls";
        fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
        response.reset();  
        response.setHeader("Content-Disposition", "attachment;filename="  
                + fileName);// ָ�����ص��ļ���  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        OutputStream output = response.getOutputStream();  
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
  
        // ���嵥Ԫ��ͷ  
//        String worksheetTitle = "Excel����Student��Ϣ";  
  
        HSSFWorkbook wb = new HSSFWorkbook();  
  
        // ������Ԫ����ʽ  
        HSSFCellStyle cellStyleTitle = wb.createCellStyle();  
        // ָ����Ԫ����ж���  
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // ָ����Ԫ��ֱ���ж���  
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // ָ������Ԫ��������ʾ����ʱ�Զ�����  
        cellStyleTitle.setWrapText(true);  
        // ------------------------------------------------------------------  
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        // ָ����Ԫ����ж���  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // ָ����Ԫ��ֱ���ж���  
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // ָ������Ԫ��������ʾ����ʱ�Զ�����  
        cellStyle.setWrapText(true);  
        // ------------------------------------------------------------------  
        // ���õ�Ԫ������  
        HSSFFont font = wb.createFont();  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        font.setFontName("����");  
        font.setFontHeight((short) 200);  
        cellStyleTitle.setFont(font);   
  
        HSSFSheet sheet = wb.createSheet();  
        ExportExcel exportExcel = new ExportExcel(wb, sheet);  
        // ��������ͷ��  
        exportExcel.createNormalHead(worksheetTitle, rowtitles.size()-1);  
        // �����һ��  
        HSSFRow row1 = sheet.createRow(1);  
        HSSFCell cell1 = row1.createCell(0);  
        
        
        Iterator its = rowtitles.keys();
        int j=0;
        
        while (its.hasNext()) {
            String key = (String) its.next();  
          cell1 = row1.createCell(j++);  
          cell1.setCellStyle(cellStyle);  
          cell1.setCellValue(new HSSFRichTextString(rowtitles.getString(key) + "")); 
        }   
        //����ڶ���  
        HSSFRow row = null;//sheet.createRow(2);  
        HSSFCell cell = null;//row.createCell(1);  
//        int j=0;
        for (int i = 0; i < dataList.size(); i++) {  
//            student = list.get(i);  
            row = sheet.createRow(i + 2);  
            Iterator it = rowtitles.keys();
            j=0;
            while (it.hasNext()){
              String key = (String) it.next();  
              cell = row.createCell(j++);  
              cell.setCellStyle(cellStyle);  
              String str=dataList.get(i).getString(key);
              String cellText="null".equals(str)?"":str;
              cell.setCellValue(new HSSFRichTextString(cellText)); 
            }   
        }  
        try {  
            bufferedOutPut.flush();  
            wb.write(bufferedOutPut);  
            bufferedOutPut.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("Output   is   closed ");  
        }  
	}
}
