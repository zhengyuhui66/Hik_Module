package com.hik.service;


import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.IAdvertPlanDao;
import com.hik.dao.IAdvertPlanLogDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Deprecated
@Service
@Transactional
public class IAdvertPlanLogServiceImpl  extends BaseService  implements IAdvertPlanLogService{
	@Autowired
	private IAdvertPlanLogDao iAdvertPlanLogDao;

}
