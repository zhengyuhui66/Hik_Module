package com.hik.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.Happen;
import com.hik.app.entity.SHOW_COUNT_LOG;
import com.hik.dao.BaseService;
import com.hik.dao.EventMangerDao;
import com.hik.dao.VehicleWifiDao;
import com.hik.framework.dao.ITimeSetDao;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.util.Area;
import com.hik.util.GraphicalMain;
import com.hik.util.PROCEDURCES;
import net.sf.json.JSONObject;

@Service
public class VehicleWifi_ningbo_ServiceImpl  extends BaseService  implements VehicleWifi_ningbo_Service{
	protected Log log = LogFactory.getLog(this.getClass()); 
	
	@Autowired
	private VehicleWifiDao vehicleWifiDao;
	@Override
	public List<JSONObject> getSpeedAndTime(String gw_id) {
		// TODO Auto-generated method stub
		return vehicleWifiDao.getSpeedAndTime(gw_id);
	}
}