package com.hik.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hik.app.entity.RedisBean;
import com.hik.app.entity.SHOW_COUNT_LOG;
import com.hik.dao.BaseService;
import com.hik.dao.EventMangerDao;
import com.hik.dao.RedisBaseDao;
import com.hik.dao.VehicleWifiDao;
import com.hik.dao.VehicleWifiDao2;
import com.hik.framework.dao.ITimeSetDao;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.util.Area;
import com.hik.util.GraphicalMain;
import com.hik.util.PROCEDURCES;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Deprecated
@Service
public class VehicleWifiServiceImpl2 extends BaseService implements VehicleWifiService2 {
	protected Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private VehicleWifiDao2 vehicleWifiDao;
    //缓存redis数据
    @Autowired
    private RedisBaseDao redis;
	@Autowired
	EventMangerDao eventMangerDao;

	@Autowired
	private ITimeSetDao iTimeSetDao;

	@Value("${serverhttp}")
	private String serverhttp;
	// 认证登录界面
	@Value("${defaultwifiauth_url}")
	private String defaultwifiauth_url;

	// 认证一键登录
	@Value("${defaultwifiauthdirect_url}")
	private String defaultwifiauthdirect_url;

	// 认证成功
	@Value("${defaultwifiauthsuccess_url}")
	private String defaultwifiauthsuccess_url;

	@Transactional(transactionManager = "smsDatatransactionManager")
	public void sendSMSCode(String phone, String code,HttpSession session) {
		// TODO Auto-generated method stub
		// log.info("接收到的code:"+code);
		String sessionId = com.hik.util.Utils.getSessionId(session);
		JSONObject phoneMap = new JSONObject();
		phoneMap.put("phone", phone);
		RedisBean redisBean = new RedisBean(sessionId,phoneMap);
		redis.setToJSONPutkey(redisBean);
		List smss = iTimeSetDao.querySMS();
		String smsContent = null;
		if (!smss.isEmpty() && smss.size() > 0) {
			smsContent = smss.get(0).toString();
		}
		String _smsContent = smsContent.replace("XXXXXX", code);
		// log.info("准备发送的短信是==>"+_smsContent);
//		vehicleWifiDao.sendSMSCodeDao(phone, _smsContent, DateUtil.getCurrentDate(), 1);
	}

	@Transactional(readOnly = true)
	public String login(JSONObject gwMap, HttpSession session, Happen happen) {
		String URL;
		String mac=JSONUtils.getString(gwMap, "gw_id");
		String phonemac=JSONUtils.getString(gwMap, "mac");
		
		long f = System.currentTimeMillis();
		// log.info("开始查询最后登陆时间");
		List<JSONObject> getLastimeList = vehicleWifiDao.getLoginDate(phonemac);
		// log.info("开始查询最后登陆时间所花费的时间"+(System.currentTimeMillis()-f));
		// log.info("开始查询最后AP信息");
		long f2 = System.currentTimeMillis();
		List<JSONObject> getApInfo = vehicleWifiDao.getApInfo(mac);
		String lat = "";
		String longit = "";// "speed","timeout","longitude","latitude
		if (getApInfo.size() > 0) {
			JSONObject apInfo = getApInfo.get(0);
			String speed = JSONUtils.getString(apInfo, "speed");
			String timeout = JSONUtils.getString(apInfo, "timeout");
			lat = JSONUtils.getString(apInfo, "longitude");
			longit = JSONUtils.getString(apInfo, "latitude");
			if (StringUtils.isNotBlank(longit) && StringUtils.isNotBlank(lat)) {
				happen.setNowpoint(new Area(Double.parseDouble(longit), Double.parseDouble(lat)));
			}
			gwMap.put("speed", speed);
			gwMap.put("timeout", timeout);
			
		}
		List<JSONObject> result = null;

		// log.info("最后登陆记录："+getLastimeList);
		if (getLastimeList != null && getLastimeList.size() > 0) {
			JSONObject lastTime = getLastimeList.get(0);
			String _lastTime = lastTime.getString("authtime");
			String phone = lastTime.getString("phone");
			happen.setPhone(phone);
//			session.setAttribute("phone", phone);
			gwMap.put("phone", phone);
			Date lastdate = DateUtil.getStrToDate(_lastTime);
			// Date tempdate=DateUtil.getAddaDay(lastdate);
			Date tempdate = DateUtil.getAddaMonth(lastdate);
			Date now = new Date();
			long f3 = System.currentTimeMillis();
			if (now.after(tempdate)) {
				URL=getURL(vehicleWifiDao,PROCEDURCES.AUTHFLAG, defaultwifiauth_url,gwMap,happen);
			} else {
				URL=getURL(vehicleWifiDao,PROCEDURCES.DIRECTFLAG, defaultwifiauthdirect_url + "?phone=" + phone,gwMap,happen);
			}
		} else {
			URL=getURL(vehicleWifiDao,PROCEDURCES.AUTHFLAG,defaultwifiauth_url,gwMap,happen);
		}
		
		//把所有的数据缓存起来
		String sessionId=com.hik.util.Utils.getSessionId(session);
		log.info("放入redis中的数据是："+gwMap.toString());
		RedisBean<JSONObject> redisBean = new RedisBean<JSONObject>(sessionId, gwMap);
		redis.setToJSONPutkey(redisBean);
		return URL;
	}

	public String getURL(VehicleWifiDao2 vehicleWifiDao,String authFlag,String defaultUrl,Map<String,String> _gwMap,Happen happen){
		String mac=_gwMap.get("gw_id");
		String phonemac=_gwMap.get("mac");
		List<JSONObject> result = vehicleWifiDao.getAlladvgroups(mac,
				new String[] { authFlag, PROCEDURCES.AUTHSUCCESSFLAG });
		_gwMap.put("authInfo", result.toString());
		String URL = authlogin(getByAuthFlag(result, authFlag), mac, null, phonemac, happen);
		if(URL==null){
			URL = serverhttp+defaultUrl;
		}
		return URL;
	};
	
	/**
	 * 获取认证周期里的数据
	 * 
	 * @param json
	 * @param flag
	 * @return
	 */
	public JSONObject getByAuthFlag(List<JSONObject> json, String flag) {
		List<JSONObject> tJson = new ArrayList<JSONObject>();
		for (JSONObject js : json) {
			String authorder = JSONUtils.getString(js, "authorder");
			if (flag.equals(authorder)) {
				tJson.add(js);
			}
		}
		JSONObject rJson = setADVOrder(tJson);
		return rJson;
	}

	public JSONObject setADVOrder(List<JSONObject> json) {
		if (json.size() == 0) {
			return null;
		}
		JSONObject result = new JSONObject();
		JSONObject tsj = json.get(0);
		result.accumulate("date", tsj.get("date"));
		result.accumulate("stime", tsj.get("stime"));
		result.accumulate("etime", tsj.get("etime"));
		result.accumulate("modelid", tsj.get("modelid"));
		result.accumulate("busid", tsj.get("busid"));
		result.accumulate("routeid", tsj.get("routeid"));
		result.accumulate("url", tsj.get("url"));
		result.accumulate("skinname", tsj.get("skinname"));
		// 以广告集广告位为单位整理 放入产品的子集中
		JSONArray posJson = new JSONArray();
		for (int i = 0; i < json.size(); i++) {

			JSONObject advgroup = new JSONObject();
			advgroup.accumulate("advposid", JSONUtils.getString(json.get(i), "advposid"));
			advgroup.accumulate("advgroupid", JSONUtils.getString(json.get(i), "advgroupid"));
			advgroup.accumulate("playstrager", JSONUtils.getString(json.get(i), "playstrager"));
			advgroup.accumulate("playintervalseral", JSONUtils.getString(json.get(i), "playintervalseral"));
			advgroup.accumulate("playtimesseral", JSONUtils.getString(json.get(i), "playtimesseral"));

			List<JSONObject> advs = new ArrayList<JSONObject>();

			for (int j = 1; j <= 9; j++) {
				String advert = JSONUtils.getString(json.get(i), "advertid" + j);
				if (StringUtils.isNotBlank(advert)) {
					JSONObject advJson = new JSONObject();
					advJson.accumulate("materid", JSONUtils.getString(json.get(i), "materid" + j));
					advJson.accumulate("adverturl", JSONUtils.getString(json.get(i), "adverturl" + j));
					advJson.accumulate("advertid", JSONUtils.getString(json.get(i), "advertid" + j));
					advJson.accumulate("name", JSONUtils.getString(json.get(i), "advertname" + j));
					advJson.accumulate("materpath", JSONUtils.getString(json.get(i), "materpath" + j));
					advs.add(advJson);
				}
			}
			advgroup.accumulate("advList", advs);
			posJson.add(advgroup);
		}
		result.accumulate("groupList", posJson);
		return result;
	}

	@Transactional(readOnly = true)
	public String portal(HttpSession session) {
		String URL = null;
		String sessionId = com.hik.util.Utils.getSessionId(session);
		String url=redis.getValueFromJSON(sessionId, "url");
		String mac=redis.getValueFromJSON(sessionId, "gw_id");
		String phonemac=redis.getValueFromJSON(sessionId, "mac");
		String phone=redis.getValueFromJSON(sessionId, "phone");
		
		if(url==null||mac==null||phonemac==null||phone==null){
			return null;
		}
		Happen happen = new Happen(null,DateUtil.getCurrentDate(),phone.toString(),null);
		
		String authInfos=redis.getValueFromJSON(sessionId,"authInfo");
		List<JSONObject> authInfo = JSONArray.fromObject(authInfos);
		log.info("从redis中取出的认证信息是："+authInfo);
		JSONObject result = getByAuthFlag(authInfo, PROCEDURCES.AUTHSUCCESSFLAG);
		URL = authlogin(result, mac, phone, phonemac, happen);
		if (URL == null) {
			log.info("返回认证成功默认界面");
			URL = serverhttp + defaultwifiauthsuccess_url + "?url='" + url + "'";
		} else {
			log.info("返回认证成功广告界面");
			URL += "&url='" + url + "'";
		}
		vehicleWifiDao.saveRecordNet( phonemac, phone, mac);
//		int wifiAuthId = vehicleWifiDao.getWifiAuthId();
//		log.info("记录上网日志");
//		vehicleWifiDao.saveRecordNet(wifiAuthId, phonemac, phone, mac);
		return URL;
	}

	public String authlogin(JSONObject products, String apmac, String phone, String phonemac, Happen happen) {
		java.util.Random random = new java.util.Random();// 定义随机类
		List<JSONObject> advinfoList = null;
//		 log.info("=======>:开始查询认证：");
		log.info("products:"+products);
		if (products != null && products.size() > 0) {
			// 获取产品投放所在的车辆，线路，模版，皮肤，模版ID
			String busid = JSONUtils.getString(products, "busid");
			String routeid = JSONUtils.getString(products, "routeid");
			String URL = JSONUtils.getString(products, "url");
			String skinname = JSONUtils.getString(products, "skinname");
			String modelid = JSONUtils.getString(products, "modelid");
			URL=serverhttp+URL;
			URL += "?modelid='" + modelid + "'&modelskin=" + skinname + "&";
			log.info("URL2:"+URL);
			for (int i = 0; i < products.getJSONArray("groupList").size(); i++) {
				// 依据优先条件查询广告集
				/**
				 * products 投放的产品,(包含了结果集) happen 优先条件集 apmac AP物理地址
				 * 返回一个广告位，一个广告的结果
				 */

				JSONObject tempJson = getAdvInfo(random, products, i, happen, apmac);
				String advposid = JSONUtils.getString(products.getJSONArray("groupList").getJSONObject(i), "advposid");
				if (tempJson != null && !tempJson.isEmpty() && tempJson.size() > 0
						&& StringUtils.isNotEmpty(advposid)) {
					String advertid = JSONUtils.getString(tempJson, "advertid");
					String materpath = JSONUtils.getString(tempJson, "materpath");
					URL += "materurl" + advposid + "='" + serverhttp+materpath + "'&" + "advertid" + advposid + "='" + advertid
							+ "'&";
				}
			}
			URL = URL.substring(0, URL.length() - 1);
			log.info("筛选出来的URL："+URL);
			return URL;
		} else {
			return null;// 默认界面
		}
	}

	/**
	 * 获取广告集中的每一个广告
	 * 
	 * @param priids
	 *            优先条件
	 * @param random
	 *            随机数
	 * @param products
	 *            一个AP对应的一个结果
	 * @param advgroupid
	 * @return
	 */
	private JSONObject getAdvInfo(java.util.Random random, JSONObject products, int advgroupid, Happen happen,
			String apmac) {
		List<JSONObject> advinfoList = new ArrayList<JSONObject>();
		/**
		 * advgroup1 投放的广告集 datetime 广告集有效开始时间 edatetime 广告集有效节束时间
		 */
		String stime = JSONUtils.getString(products, "stime");// products.getString("stime");
		String etime = JSONUtils.getString(products, "etime");
		String date = JSONUtils.getString(products, "date");
		String datetime = date + " " + stime;
		String edatetime = date + " " + etime;
		// 取出当前广告集的优先级轮播类型,
		JSONObject radvid = null;
		JSONArray advs = products.getJSONArray("groupList").getJSONObject(advgroupid).getJSONArray("advList");
		// 取出广告集中所有的广告
		// 根据客户端输入信息来取出符合优先投放的广告集。
		JSONArray priids = getPriAdv(advs, happen, apmac);
		// 如果有则先去筛选是否有符合条件的优先投放的广告
		if (!priids.isEmpty() && priids.size() > 0) {
			log.info("==============>进入优先级选项===》" + priids + "  长度:" + priids.size());
			int radomNumAdv = random.nextInt(priids.size());
			radvid = priids.getJSONObject(radomNumAdv);
			// log.info("有符合条件的优先投放广告集则直接进行其随机选出广告："+advid);
		} else {
			// 如果没有优先级的条件则正常轮播
			radvid = getCommonadv(random, datetime, edatetime, products, advgroupid, advs);
			// log.info("如果没有优先级的条件则正常轮播:"+advid);
		}
		return radvid;
	}

	/**
	 * 获取具体广告信息
	 * 
	 * @param random
	 * @param advgroup1
	 * @param stime
	 * @param playtype
	 * @param advs
	 * @return
	 */
	private JSONObject getCommonadv(java.util.Random random, String stime, String edatetime, JSONObject products,
			int advgroupid, JSONArray advs) {
		JSONObject radvid = null;

		JSONObject group = products.getJSONArray("groupList").getJSONObject(advgroupid);
		// 如果没有符合条件 的优先级广告集则正常轮播
		String playstrage = JSONUtils.getString(group, "playstrager");
		String advgroupids = JSONUtils.getString(group, "advgroupid");
		// 间隔次数"playstrager","playintervalseral","playtimesseral
		int stragertime = 0;
		String stragertimes = JSONUtils.getString(group, "playintervalseral");
		if (!StringUtils.isEmpty(stragertimes)) {
			stragertime = Integer.parseInt(stragertimes);
		}
		if (PROCEDURCES.DATESTREGER.equals(playstrage)) {
			// log.info("==============按时间策略来轮播！");
			long nowTime = System.currentTimeMillis();
			Date starttime = DateUtil.getStrToDate(stime, "yyyy-MM-dd hh:mm:ss");
			Date endtime = DateUtil.getStrToDate(stime, "yyyy-MM-dd hh:mm:ss");
			long sttime = starttime.getTime();
			int totaltimes = (int) (nowTime - sttime) / 1000;
			// log.info("==============当前时间戳，轮播时间起始时间:"+stime+"
			// 当前时间:"+DateUtil.getCurrentDate()+" 结束时间:"+edatetime+"
			// 一共有"+totaltimes+"秒");

			int advsSize = advs.size();
			// log.info("广告数量是:"+advsSize);
			int advnumber = totaltimes / stragertime;
			int advsnum = advnumber % advsSize;
			// log.info("轮播策略间隔间是:"+stragertime+"秒，执行了第"+advnumber+"次播放
			// 轮到第"+advsnum+"个");
			radvid = advs.getJSONObject(advsnum);
		} else if (PROCEDURCES.TIMESSTREGER.equals(playstrage)) {
			String actualtimes = JSONUtils.getString(group, "playtimesseral");
			if (StringUtils.isEmpty(actualtimes)) {
				actualtimes = "0";
			}
			int actualtime = 0;
			if (!StringUtils.isEmpty(actualtimes)) {
				actualtime = Integer.parseInt(actualtimes);
			}
			if (actualtime >= advs.size() * stragertime) {
				actualtime = 0;
			}
			// 保存更新实际展示次数

			int advnumber = actualtime / stragertime;
			// log.info("这是第"+actualtime+"次播放 轮播次数是:"+stragertime+"
			// 第"+advnumber+"个广告");
			radvid = advs.getJSONObject(advnumber);
			// 后续放缓存
			int updatetimes = vehicleWifiDao.updateAdvgrouptimes(advgroupids, ++actualtime);
		} else if (PROCEDURCES.RANDOMSTREGER.equals(playstrage)) {
			int radomNumAdv = random.nextInt(advs.size());
			radvid = advs.getJSONObject(radomNumAdv);
		}
		return radvid;
	}

	@Transactional
	public String saveClickCount(String advertid,String modelid,String modelmodelid,HttpSession session) {
		// // TODO Auto-generated method stub
		String sessionId = com.hik.util.Utils.getSessionId(session);
		String gw_id=redis.getValueFromJSON(sessionId, "gw_id");
		String mac=redis.getValueFromJSON(sessionId, "mac");
		String phone=redis.getValueFromJSON(sessionId, "phone");
		
		String busid=null;
		String adverturl=null;
		String materid=null;
		String routeid=null;
		Object authInfos=session.getAttribute("authInfo");
		List<JSONObject> authInfo = JSONArray.fromObject(authInfos);
		boolean flag = true;
		log.info("获取的数据集是："+authInfo);
		for(JSONObject json:authInfo){
			for(int i=1;i<=9;i++){
				String tadvertId = JSONUtils.getString(json, "advertid"+i);
				if(advertid.equals(tadvertId)){
					busid=JSONUtils.getString(json, "busid");
					adverturl=JSONUtils.getString(json, "adverturl"+i);
					materid=JSONUtils.getString(json, "materid"+i);
					routeid=JSONUtils.getString(json, "routeid");
					log.info("获取点击基础信息为1:BUSID:"+busid+" 广告URL:"+adverturl+" 物料:"+materid+" 线路:"+routeid);
					flag=false;
					break;
				}
			}	
			if(flag==false){
				break;
			}
		}
		if(StringUtils.isBlank(advertid)||StringUtils.isBlank(modelid)||StringUtils.isBlank(modelmodelid)||authInfos==null){
			log.info("点击数据为无效数据");
			return adverturl;
		}

		if(authInfo==null||authInfo.size()==0){
			log.info("点击数据为无效会话");
			return adverturl;
		}
		log.info("获取点击基础信息为2:BUSID:"+busid+" 广告URL:"+adverturl+" 物料:"+materid+" 线路:"+routeid);
		CLICK_COUNT_LOG logg = new CLICK_COUNT_LOG(null, DateUtil.getCurrentDate(), busid, gw_id, phone, mac, modelid, modelmodelid, materid, adverturl, advertid, routeid);
		int result = vehicleWifiDao.saveClickCount(logg);
		return adverturl;
	}

	@Transactional(readOnly = true)
	public boolean toVaild(String code, String phone) {
		// TODO Auto-generated method stub
		return true;
	}
//
	@Override
	@Transactional(readOnly = true)
	public int saveShowCount(String advertid,String modelid,String advposid,HttpSession session) {
		// TODO Auto-generated method stub
		String sessionId = com.hik.util.Utils.getSessionId(session);
		String authInfos=redis.getValueFromJSON(sessionId, "authInfo");
		String apmac=redis.getValueFromJSON(sessionId, "gw_id");
		//客户端地址
		String mac=redis.getValueFromJSON(sessionId, "mac");
		log.info("从redis中取出的展示数据保存信息是=>apmac:"+apmac+"  mac:"+mac+"   authInfos"+authInfos);
		String busid=null;
		String adverturl=null;
		String materid=null;
		String routeid=null;
		
		if(StringUtils.isBlank(advertid)||StringUtils.isBlank(modelid)||StringUtils.isBlank(advposid)||authInfos==null){
			log.info("展示数据为无效数据");
			return 0;
		}
//		log.info("authInfos=======>"+authInfos.toString());
		List<JSONObject> authInfo = JSONArray.fromObject(authInfos);
		if(authInfo==null||authInfo.size()==0){
			log.info("展示数据为无效会话");
			return 0;
		}
		boolean flag = true;
		for(JSONObject json:authInfo){
			for(int i=1;i<=9;i++){
				String tadvertId = JSONUtils.getString(json, "advertid"+i);
				if(advertid.equals(tadvertId)){
					busid=JSONUtils.getString(json, "busid");
					adverturl=JSONUtils.getString(json, "adverturl"+i);
					materid=JSONUtils.getString(json, "materid"+i);
					routeid=JSONUtils.getString(json, "routeid");
					flag=false;
					break;
				}
			}
			if(flag==false){
				break;
			}
		}
		if(apmac==null||mac==null){
			return 0;
		}
	
		log.error("=====>>>>:统计展示次数advertid:"+advertid+" modelid:"+modelid+" advposid:"+advposid+" apmac:"+apmac+" mac:"+mac);
		
		
		SHOW_COUNT_LOG count_LOG = new SHOW_COUNT_LOG(null, DateUtil.getCurrentDate(), busid, apmac.toString(), null, mac.toString(), modelid, advposid, materid, adverturl, advertid, routeid);

		int result = vehicleWifiDao.saveShowCount(count_LOG);
		return result;
	}

	@Override
	public List<JSONObject> getApInfo(String gw_id) {
		// TODO Auto-generated method stub
		return vehicleWifiDao.getApInfo(gw_id);
	}

	private JSONArray getPriAdv(JSONArray advs, Happen happen, String apmac) {
		List<String> advids = new ArrayList<String>();
		JSONArray result = new JSONArray();
		for (int i = 0; i < advs.size(); i++) {
			advids.add(JSONUtils.getString(advs.getJSONObject(i), "advertid"));
		}
		if(advids.size()==0){
			log.info("没有广告，则直接返回");
			return result;
		}
		long f = System.currentTimeMillis();
		// log.info("查询优先投放信息");
		List<JSONObject> json = vehicleWifiDao.getPriadvByCondit(advids, happen, apmac);
		// log.info("查询优先投放信息 所花费时间"+(System.currentTimeMillis()-f));
		List tempadvIds = new ArrayList();
		for (int i = 0; i < json.size(); i++) {
			JSONObject tempJson = json.get(i);
			String adrid = JSONUtils.getString(tempJson, "adrid");
			String adrname = JSONUtils.getString(tempJson, "adrname");
			String advid = JSONUtils.getString(tempJson, "advid");
			if (StringUtils.isBlank(adrid) && StringUtils.isBlank(adrname)) {
				tempadvIds.add(advid);
			} else {
				Area area = happen.getNowpoint();
				if (area == null) {
					continue;
				}
				List<Area> areas = new ArrayList<Area>();
				for(int j=1;j<=9;j++){
					String _long = JSONUtils.getString(tempJson, "long"+j);
					String _lat = JSONUtils.getString(tempJson, "lat"+j);
					if (StringUtils.isNoneEmpty(_long) && StringUtils.isNoneEmpty(_lat)) {
						Area a = new Area(Double.parseDouble(_long), Double.parseDouble(_lat));
						areas.add(a);
					}
				}
				if (GraphicalMain.isPointInPolygon(area.getPy(), area.getPx(), areas)) {
					tempadvIds.add(advid);
				}
			
			}

		}

		for (int i = 0; i < advs.size(); i++) {
			String advertId = JSONUtils.getString(advs.getJSONObject(i), "advertid");
			for (int j = 0; j < tempadvIds.size(); j++) {
				if (advertId.equals(tempadvIds.get(j))) {
					result.add(advs.getJSONObject(i));
					break;
				}
			}
		}
		return result;
	}

	@Override
	public String logined(String pubip,String token,HttpSession session) {
		String sessionId=com.hik.util.Utils.getSessionId(session);
		String  gw_address=redis.getValueFromJSON(sessionId, "gw_address");
		String gw_port=redis.getValueFromJSON(sessionId, "gw_port");
		String phone=redis.getValueFromJSON(sessionId, "phone");
		String gw_id=redis.getValueFromJSON(sessionId, "gw_id");
		
		log.info("从redis中取出的登陆成功信息是=>gw_address:"+gw_address+"  gw_port:"+gw_port+"   phone"+phone+" gw_id"+gw_id);
		if(gw_address==null||gw_port==null||phone==null||gw_id==null){
			return null;
		}
		Map<String,Object> map = new HashMap<String,Object>();
	
		String speed="512";
		String timeout="15000";
		Object speeds=session.getAttribute("speed");
		Object timeouts=session.getAttribute("timeout");
		if(speeds!=null){
			speed=speeds.toString();
		}
		if(timeouts!=null){
			timeout=timeouts.toString();
		}
		String httpUrl="redirect:http://"+gw_address.toString()+":"+gw_port.toString()+"/wifidog/auth?token=" +token+ "&Phone=" +phone + "&Pubip=" + pubip + "&Speed=" +speed + "&Timeout=" +timeout;
		
		return httpUrl;
	}
}