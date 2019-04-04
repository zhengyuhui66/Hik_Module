package com.hik.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.ICommonDao;
import com.hik.dao.IPutPlanDao;
import com.hik.dao.IPutScheduleDao;
import com.hik.dao.IPutScheduleLogDao;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.Page;

import net.sf.json.JSONObject;


@Service
@Transactional
public class IPutScheduleLogServiceImpl  extends BaseService  implements IPutScheduleLogService{

	@Autowired
	private IPutScheduleLogDao iPutScheduleLogDao;

	@Override
	public Page queryPutLog(int start, int limit, String sreachStr) {
		// TODO Auto-generated method stub
		return iPutScheduleLogDao.queryPutLog(start, limit, sreachStr);
	}

}
