package com.hik.util;

import java.io.File;    
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
    
public class test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("COL0", "a");
		map1.put("COL1", "2017-01");
		map1.put("COL2", "11");
		map1.put("COL3", "ssss");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("COL0", "a");
		map2.put("COL1", "2017-02");
		map2.put("COL2", "12");
		map2.put("COL3", "serrr");
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("COL0", "a");
		map3.put("COL1", "2017-03");
		map3.put("COL2", "13");
		map3.put("COL3", "adddd");
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("COL0", "b");
		map4.put("COL1", "2017-04");
		map4.put("COL2", "11");
		map4.put("COL3", "fdss");
		
		mapList.add(map1);
		mapList.add(map2);
		mapList.add(map3);
		mapList.add(map4);
		System.out.println(mapList.toString());
		
		List<String> mengdian=new ArrayList<String>();
		mengdian.add("a");
		mengdian.add("b");
		
		
		
		Map<String,Map> result=new HashMap<String,Map>();
		for(int i=0;i<mengdian.size();i++){
			
			Map<String,Map<String,String>> tempMonethmap=new HashMap<String,Map<String,String>>();
			for(int m=1;m<=12;m++){
				Map<String,String> tempValue=new HashMap<String,String>();
				tempValue.put("COL0",null);
				tempValue.put("COL1",null);
				tempValue.put("COL2",null);
				tempValue.put("COL3",null);
				if(m<10){
					tempMonethmap.put("2017-0"+m, tempValue);
				}else{
					tempMonethmap.put("2017-"+m, tempValue);
				}
				
			}
			result.put(mengdian.get(i), tempMonethmap);
		}
		System.out.println(result);
		
		
		
//		for(int i=0;i<mengdian.size();i++){
//			for(int j=0;j<mapList.size();j++){
//				Map tempMap=mapList.get(j);
//				String mendianStr=(String) tempMap.get("COL0");
//				String monthStr=(String) tempMap.get("COL1");
//				Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
//				if(result.get(mendianStr)!=null){
//					Map<String,Map<String,Map<String,String>>> mendianMap=result.get(mendianStr);
////					Map tempValue=(Map) mendianMap.get(monthStr);
//					Map<String,Map<String,String>> tempValue=mendianMap.get(monthStr);
//					tempValue.put("COL0",tempMap.get("COL0"));
//					tempValue.put("COL1",tempMap.get("COL1"));
//					tempValue.put("COL2",tempMap.get("COL2"));
//					tempValue.put("COL3",tempMap.get("COL3"));
//				}
//			}
//		}
		
		
//		String date="2017-02";
//		
////		List<Map<String,Object>> colList=new ArrayList<>();
//		Map<String, Object> col = new HashMap<String, Object>();
//		col.put("COL0", "a");
//		col.put("COL01", "11");//2017-01
//		col.put("COL02", "111");
//		col.put("COL03", "111");
//		col.put("COL04", "111");
//		col.put("COL05", "111");
//		
//		Map<String, Object> amap=new HashMap();
//			for (int i = 0; i < mapList.size(); i++) {
//				Map m=mapList.get(i);
//				amap.put("COL0", m.get("COL0"));
//				amap.put(m.get("COL1")+"", m.get("COL2"));
//				if(m.get("COL1").equals(date)){
//				amap.put("COL3",m.get("COL3"));
//			}
//		}
//		System.out.println(amap.toString());
		
		
	}

}
	 