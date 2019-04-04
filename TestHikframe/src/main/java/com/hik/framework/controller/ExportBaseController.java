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
			this.returnResponse(response, "数据为空");
		}
		if(rowtitles==null&&rowtitles.size()==0){
			this.returnResponse(response, "标题为空");
		}
		String fileName =worksheetTitle+".xls";
        fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
        response.reset();  
        response.setHeader("Content-Disposition", "attachment;filename="  
                + fileName);// 指定下载的文件名  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        OutputStream output = response.getOutputStream();  
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
  
        // 定义单元格报头  
//        String worksheetTitle = "Excel导出Student信息";  
  
        HSSFWorkbook wb = new HSSFWorkbook();  
  
        // 创建单元格样式  
        HSSFCellStyle cellStyleTitle = wb.createCellStyle();  
        // 指定单元格居中对齐  
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 指定单元格垂直居中对齐  
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 指定当单元格内容显示不下时自动换行  
        cellStyleTitle.setWrapText(true);  
        // ------------------------------------------------------------------  
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        // 指定单元格居中对齐  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 指定单元格垂直居中对齐  
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 指定当单元格内容显示不下时自动换行  
        cellStyle.setWrapText(true);  
        // ------------------------------------------------------------------  
        // 设置单元格字体  
        HSSFFont font = wb.createFont();  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        font.setFontName("宋体");  
        font.setFontHeight((short) 200);  
        cellStyleTitle.setFont(font);   
  
        HSSFSheet sheet = wb.createSheet();  
        ExportExcel exportExcel = new ExportExcel(wb, sheet);  
        // 创建报表头部  
        exportExcel.createNormalHead(worksheetTitle, rowtitles.size()-1);  
        // 定义第一行  
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
        //定义第二行  
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
