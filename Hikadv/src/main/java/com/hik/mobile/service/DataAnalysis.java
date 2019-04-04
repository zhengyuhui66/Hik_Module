package com.hik.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hik.dao.ShowClickDao;
import com.hik.mobile.dao.IMainSumDao;

import net.sf.json.JSONObject;

public class DataAnalysis implements IDataAnalysis {
	@Autowired
	private IMainSumDao iMainSumDao;
	@Autowired
	private ShowClickDao iShowClickDao;
	@Override
	public List<JSONObject> getShowInfo() {
		// TODO 自动生成的方法存根
		return null;
	}

}
