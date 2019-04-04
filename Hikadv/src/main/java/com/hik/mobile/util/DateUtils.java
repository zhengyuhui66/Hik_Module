package com.hik.mobile.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hik.framework.utils.DateUtil;

public  class DateUtils {
	/**
	 * ��ȡ����ʱ�����������
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @return
	 */
	public static List getTimes(String stime,String etime){
		Date sDate=DateUtil.getStrToDate(stime,"yyyy-MM-dd");
		Date eDate=DateUtil.getStrToDate(etime,"yyyy-MM-dd");
		List list = new ArrayList();
		while(!sDate.equals(eDate)){
			sDate=DateUtil.getAddaDay(sDate);
			String stimes=DateUtil.getDateToStr(sDate,"yyyy-MM-dd");
			list.add(stimes);
		}
		return list;
	}
}
