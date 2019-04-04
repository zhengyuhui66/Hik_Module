package com.hik.service;

import java.util.List;

import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;

public interface IPutScheduleLogService {

	public Page queryPutLog(int start, int limit, String sreachStr);
}
