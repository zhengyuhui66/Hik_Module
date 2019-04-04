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
@Deprecated
@Service
public class VehicleWifiServiceImpl  extends BaseService  implements VehicleWifiService{
	protected Log log = LogFactory.getLog(this.getClass()); 
	
	@Autowired
	private VehicleWifiDao vehicleWifiDao;
	
	@Autowired
	EventMangerDao eventMangerDao;
	
	@Autowired
	private ITimeSetDao iTimeSetDao;
	
    @Value("${serverhttp}")
	private String serverhttp;
	//��֤��¼����
    @Value("${defaultwifiauth_url}")
	private String defaultwifiauth_url;
    
    //��֤һ����¼
    @Value("${defaultwifiauthdirect_url}")
	private String defaultwifiauthdirect_url;
    
	//��֤�ɹ�
    @Value("${defaultwifiauthsuccess_url}")
	private String defaultwifiauthsuccess_url;
	
	@Transactional(transactionManager="smsDatatransactionManager")
	public void sendSMSCode(String phone,String code) {
		// TODO Auto-generated method stub
//		log.info("���յ���code:"+code);
		List smss=iTimeSetDao.querySMS();
		String smsContent=null;
		if(!smss.isEmpty()&&smss.size()>0){
			smsContent=smss.get(0).toString();
		}
		String _smsContent=smsContent.replace("XXXXXX",code);
//		log.info("׼�����͵Ķ�����==>"+_smsContent);
//		vehicleWifiDao.sendSMSCodeDao(phone,_smsContent,DateUtil.getCurrentDate(),1);
	}
	@Transactional(readOnly=true)
	public String login(String mac,String phonemac,HttpSession session,Happen happen) {
		// TODO Auto-generated method stub
		
		String URL=null;
		List<JSONObject> getLastimeList=vehicleWifiDao.getLoginDate(phonemac);
		List<JSONObject> result =null;
		List<JSONObject> getApInfo=vehicleWifiDao.getApInfo(mac);
		String lat="";
		String longit="";//"speed","timeout","longitude","latitude
		if(getApInfo.size()>0){
			JSONObject apInfo=getApInfo.get(0);
			String speed=JSONUtils.getString(apInfo, "speed");
			String timeout=JSONUtils.getString(apInfo, "timeout");
			lat=JSONUtils.getString(apInfo, "longitude");
			longit=JSONUtils.getString(apInfo, "latitude");
			if(StringUtils.isNotBlank(longit)&&StringUtils.isNotBlank(lat)){
				happen.setNowpoint(new Area(Double.parseDouble(longit),Double.parseDouble(lat)));				
			}
			session.setAttribute("speed",speed);
			session.setAttribute("timeout",timeout);
		}
		if(getLastimeList!=null&&getLastimeList.size()>0){
			JSONObject lastTime = getLastimeList.get(0);
			String _lastTime=lastTime.getString("authtime");
			String phone = lastTime.getString("phone");
			session.setAttribute("phone", phone);
			Date lastdate = DateUtil.getStrToDate(_lastTime);
//			Date tempdate=DateUtil.getAddaDay(lastdate);
			Date tempdate = DateUtil.getAddaMonth(lastdate);
			Date now = new Date();
			if(now.after(tempdate)){
				//����һ��ʱ���������֤��½
				log.info("����һ��ʱ��:������֤��½");
				//��ѯ��ǰAP��Ͷ�ŵ����еĹ�漯��ģ�棬���λ
//				result = vehicleWifiDao.getAlladvgroups(mac, PROCEDURCES.AUTHFLAG);
//				URL=authlogin(result,mac,phone,phonemac,happen);
//				if(URL==null){
					URL=serverhttp+defaultwifiauth_url;
//				}
				return  URL;
			}else{
				//����һ��ʱ����һ����½
				log.info("����һ��ʱ�� һ����½");
				//��ѯ��ǰAP��Ͷ�ŵ����еĹ�漯��ģ�棬���λ
//				result= vehicleWifiDao.getAlladvgroups(mac, PROCEDURCES.DIRECTFLAG);
//				URL=authlogin(result,mac,phone,phonemac,happen);
//				
//				if(URL==null){
					URL=serverhttp+defaultwifiauthdirect_url+"?phone="+phone;
//				}else{
//					URL+="&phone="+phone;
//				}
				return  URL;
			}
		}else{
			//û����֤��������֤��½
			log.info("û����֤��������֤��½");
			//��ѯ��ǰAP��Ͷ�ŵ����еĹ�漯��ģ�棬���λ
//			result = vehicleWifiDao.getAlladvgroups(mac, PROCEDURCES.AUTHFLAG);
//			URL=authlogin(result,mac,null,phonemac,happen);
//			if(URL==null){
				URL=serverhttp+defaultwifiauth_url;
//			}
			return  URL;
		}
	}
	
	@Transactional(readOnly=true)
	public String portal(String mac,String phone,String phonemac,HttpSession session,String url,Happen happen){
		// TODO Auto-generated method stub
		String URL=null;
		log.info("��֤�ɹ�");
//		List priids = getPriids();
//		List<JSONObject> result = vehicleWifiDao.getAlladvgroups(mac, PROCEDURCES.AUTHSUCCESSFLAG);
//		URL=authlogin(result,mac,phone,phonemac,happen);
//		if(URL==null){
			URL=serverhttp+defaultwifiauthsuccess_url+"?url='"+url+"'";
//		}else{
//			URL+="&url='"+url+"'";			
//		}
//		log.info("***'===URL"+URL);
//		int wifiAuthId=vehicleWifiDao.getWifiAuthId();
//		log.info("**************phonemac_value:"+phonemac);
		vehicleWifiDao.saveRecordNet(phonemac,phone,mac);
		return  URL;
	}
	
	
	
	public String authlogin(List<JSONObject> result,String apmac,String phone,String phonemac,Happen happen){
//		List<JSONObject> result = vehicleWifiDao.getAlladvgroups(phonemac, PROCEDURCES.AUTHFLAG);
		java.util.Random random=new java.util.Random();// ���������
		List<JSONObject> advinfoList=null;
//		log.info("=======>:��ʼ��ѯ��֤��"+result);
		if(result!=null&&result.size()>0){
			JSONObject products = result.get(0);
			//��ȡ��ƷͶ�����ڵĳ�������·��ģ�棬Ƥ����ģ��ID
			String busid=JSONUtils.getString(products, "busid");
			String routeid=JSONUtils.getString(products, "routeid");
			String URL=JSONUtils.getString(products, "url");
			String skinname=JSONUtils.getString(products, "skinname");
			String modelid=JSONUtils.getString(products, "modelid");
			URL+="?modelid='"+modelid+"'&modelskin="+skinname+"&";
			for(int i=1;i<=5;i++){
				//��������������ѯ��漯
				/**
				 * products Ͷ�ŵĲ�Ʒ,(�����˽����)
				 * happen ����������
				 * apmac AP�����ַ
				 * ����һ�����λ��һ�����Ľ��
				 */
				 
				List<JSONObject> advgroup_adv=getAdvInfo(random, products,"advgroup"+i,happen,apmac);
				String advposid=JSONUtils.getString(products, "advposid"+i);
				if(advgroup_adv!=null&&!advgroup_adv.isEmpty()&&advgroup_adv.size()>0&&StringUtils.isNotEmpty(advposid)){
					JSONObject tempJson = advgroup_adv.get(0);
					String advertid= JSONUtils.getString(tempJson, "advertid");
					String advertUrl=JSONUtils.getString(tempJson, "adverturl");
					String materpath=JSONUtils.getString(tempJson, "materpath");
//					log.info("advertUrl"+i+": "+advertUrl+" materpath:"+i+": "+materpath+" advposid:"+advposid);
					//ƴ��
					URL+="materurl"+advposid+"='"+materpath+"'&"+"advertid"+advposid+"='"+advertid+"'&";
				}
			}
			URL=URL.substring(0, URL.length()-1);
			return URL;
		}else{
			return null;//Ĭ�Ͻ���
		}
	}
	/**
	 * ��ȡ��漯�е�ÿһ�����
	 * @param priids ��������
	 * @param random �����
	 * @param products һ��AP��Ӧ��һ�����
	 * @param advgroupid
	 * @return
	 */
	private List<JSONObject> getAdvInfo(java.util.Random random, JSONObject products,String advgroupid,Happen happen,String apmac) {
		List<JSONObject> advinfoList=new ArrayList<JSONObject>();
		/**
		 * advgroup1 Ͷ�ŵĹ�漯
		 * datetime ��漯��Ч��ʼʱ��
		 * edatetime ��漯��Ч����ʱ��
		 */
		String advgroup1=JSONUtils.getString(products,advgroupid);//products.getString(advgroupid);//products.getString("advgroup1");
		String stime = JSONUtils.getString(products,"stime");//products.getString("stime");
		String etime = JSONUtils.getString(products,"etime");
		String date = JSONUtils.getString(products, "date");
		String datetime=date+" "+stime;
		String edatetime=date+" "+etime;
		
		if(!StringUtils.isEmpty(advgroup1)){
			//ȡ����ǰ��漯�����ȼ��ֲ�����,
			String advid=null;
//			log.info("ȡ�ù�漯��Ϣ");
			List<JSONObject> advgroups=vehicleWifiDao.getAdvGroup(advgroup1);
			if(advgroups==null||advgroups.size()==0){
				return null;
			}
			JSONObject advgroup=advgroups.get(0);
			//ȡ����漯�����еĹ��
			List advs=new ArrayList();
			for(int i=1;i<=9;i++){
				String adv=JSONUtils.getString(advgroup,"advid"+i);
				if(StringUtils.isNotEmpty(adv)){
					advs.add(adv);					
				}
			}
//			log.info("ȡ����Ч�Ĺ�沢�����µĹ�漯");
//			---------------------------------------------------------
			//���ݿͻ���������Ϣ��ȡ����������Ͷ�ŵĹ�漯��
			log.info("==============>�������ȼ�ѡ��");
			List<String> priids=getPriAdv(advs,happen,apmac);
			log.info("==============>�������ȼ�ѡ��===��"+priids+"  ����:"+priids.size());
//			---------------------------------------------------------
//			log.info("===========����Ͷ�Ź��Ĺ�漯��"+priids);
			//���������ȥɸѡ�Ƿ��з�������������Ͷ�ŵĹ��
			if(!priids.isEmpty()&&priids.size()>0){
				int radomNumAdv=random.nextInt(priids.size());
				log.info("========�����Ϊ��"+radomNumAdv);
				log.info("==============>�õ��Ĺ��Ϊ"+priids.get(radomNumAdv));
				advid=priids.get(radomNumAdv).toString();
//				log.info("�з�������������Ͷ�Ź�漯��ֱ�ӽ��������ѡ����棺"+advid);
			}else{
				//���û�����ȼ��������������ֲ�
				advid=getCommonadv(random, advgroup1, datetime,edatetime, advgroup, advs);
//				log.info("���û�����ȼ��������������ֲ�:"+advid);
			}
			if(advid!=null){
				advinfoList= vehicleWifiDao.getAdvInfo(advid);				
			}
		}
		return advinfoList;
	}
	/**
	 * ��ȡ��������Ϣ
	 * @param random
	 * @param advgroup1
	 * @param stime
	 * @param playtype
	 * @param advs
	 * @return
	 */
	private String  getCommonadv(java.util.Random random, String advgroup1, String stime,String edatetime, JSONObject playtype, List advs) {
		String advid=null;
		//���û�з������� �����ȼ���漯�������ֲ�
		String playstrage=JSONUtils.getString(playtype,"playstrager");
		
		//�������
		int stragertime=0;
		String stragertimes=JSONUtils.getString(playtype,"interval");
		if(!StringUtils.isEmpty(stragertimes)){
			stragertime=Integer.parseInt(stragertimes);
		}
		if(PROCEDURCES.DATESTREGER.equals(playstrage)){
//			log.info("==============��ʱ��������ֲ���");
			long nowTime=System.currentTimeMillis();
			Date starttime=DateUtil.getStrToDate(stime, "yyyy-MM-dd hh:mm:ss");
			Date endtime=DateUtil.getStrToDate(stime, "yyyy-MM-dd hh:mm:ss");
			long sttime=starttime.getTime();
			int totaltimes=(int)(nowTime-sttime)/1000;
//			log.info("==============��ǰʱ������ֲ�ʱ����ʼʱ��:"+stime+" ��ǰʱ��:"+DateUtil.getCurrentDate()+" ����ʱ��:"+edatetime+" һ����"+totaltimes+"��");
//			advs.size()
//			int advnumber=totaltimes%stragertime;
			int advsSize=advs.size();
//			log.info("���������:"+advsSize);
			int advnumber=totaltimes/stragertime;
			int advsnum=advnumber%advsSize;
//			log.info("�ֲ����Լ������:"+stragertime+"�룬ִ���˵�"+advnumber+"�β��� �ֵ���"+advsnum+"��");
			advid=advs.get(advsnum).toString();
		}else if(PROCEDURCES.TIMESSTREGER.equals(playstrage)){
			String actualtimes=JSONUtils.getString(playtype,"playtimesseral");
			if(StringUtils.isEmpty(actualtimes)){
				actualtimes="0";
			}
			int actualtime=0;
			if(!StringUtils.isEmpty(actualtimes)){
				actualtime=Integer.parseInt(actualtimes);
			}
			if(actualtime>=advs.size()*stragertime){
				actualtime=0;
			}
			//�������ʵ��չʾ����
			
			int advnumber=actualtime/stragertime;
//			log.info("���ǵ�"+actualtime+"�β���     �ֲ�������:"+stragertime+" ��"+advnumber+"�����");
			advid=advs.get(advnumber).toString();
			
			int updatetimes=vehicleWifiDao.updateAdvgrouptimes(advgroup1, ++actualtime);
		}else if(PROCEDURCES.RANDOMSTREGER.equals(playstrage)){
			int radomNumAdv=random.nextInt(advs.size());
			advid=advs.get(radomNumAdv).toString();
		}
		return advid;
	}

	@Transactional
	public String saveClickCount(CLICK_COUNT_LOG logg) {
//		// TODO Auto-generated method stub
		String url=null;
		String mac=logg.getApmac();
		String advertid=logg.getAdvertid();
		List<JSONObject> list = vehicleWifiDao.queryDetailInfo(mac,advertid);
		if(list!=null&&!list.isEmpty()){
			JSONObject tempJson =list.get(0);
			String busid=JSONUtils.getString(tempJson, "busid");
			String adverturl=JSONUtils.getString(tempJson, "adverturl");
			String materid=JSONUtils.getString(tempJson, "materid");
			String routeid=JSONUtils.getString(tempJson, "routeid");
			logg.setBusid(busid);
			logg.setAdvert_url(adverturl);
			logg.setMaterid(materid);
			logg.setLineid(routeid);
			url=adverturl;
		}
		int result=vehicleWifiDao.saveClickCount(logg);
//		log.info("��� �Ĺ��������:"+url);
		return url;
	}
	@Transactional(readOnly=true)
	public boolean toVaild(String code, String phone) {
		// TODO Auto-generated method stub
			return true;
	}
	
	@Override
	@Transactional(readOnly=true)
	public int saveShowCount(SHOW_COUNT_LOG count_LOG) {
		// TODO Auto-generated method stub
		String mac=count_LOG.getApmac();
		String advertid=count_LOG.getAdvertid();
		List<JSONObject> list = vehicleWifiDao.queryDetailInfo(mac,advertid);
		if(list!=null&&!list.isEmpty()){
			JSONObject tempJson =list.get(0);
			String busid=JSONUtils.getString(tempJson, "busid");
			String adverturl=JSONUtils.getString(tempJson, "adverturl");
			String materid=JSONUtils.getString(tempJson, "materid");
			String routeid=JSONUtils.getString(tempJson, "routeid");
			count_LOG.setBusid(busid);
			count_LOG.setAdvert_url(adverturl);
			count_LOG.setMaterid(materid);
			count_LOG.setLineid(routeid);
		}
		int result=vehicleWifiDao.saveShowCount(count_LOG);
		return result;
	}
	
	@Override
	public List<JSONObject> getSpeedAndTime(String gw_id) {
		// TODO Auto-generated method stub
		return vehicleWifiDao.getSpeedAndTime(gw_id);
	}
	
	private  List<String> getPriAdv(List<String> advids,Happen happen,String apmac){
//		List<String> advids = new ArrayList<String>();
//		for(int i=0;i<advs.size();i++){
//			advids.add(JSONUtils.getString(advs.getJSONObject(i),"advertid"));
//		}
		long f = System.currentTimeMillis();
//		log.info("��ѯ����Ͷ����Ϣ");
		List<JSONObject> json = vehicleWifiDao.getPriadvByCondit(advids, happen, apmac);
//		log.info("��ѯ����Ͷ����Ϣ  ������ʱ��"+(System.currentTimeMillis()-f));
		List tempadvIds = new ArrayList();
		for(int i=0;i<json.size();i++){
			JSONObject tempJson = json.get(i);
			String adrid=JSONUtils.getString(tempJson, "adrid");
			String adrname=JSONUtils.getString(tempJson, "adrname");
			String advid=JSONUtils.getString(tempJson, "advid");
			if(StringUtils.isBlank(adrid)&&StringUtils.isBlank(adrname)){
				tempadvIds.add(advid);
			}else{
				Area area = happen.getNowpoint();
				if(area==null){
					continue;
				}
				String long1=JSONUtils.getString(tempJson, "long1");
				String lat1=JSONUtils.getString(tempJson, "lat1");
				String long2=JSONUtils.getString(tempJson, "long2");
				String lat2=JSONUtils.getString(tempJson, "lat2");
				String long3=JSONUtils.getString(tempJson, "long3");
				String lat3=JSONUtils.getString(tempJson, "lat3");
				String long4=JSONUtils.getString(tempJson, "long4");
				String lat4=JSONUtils.getString(tempJson, "lat4");
				String long5=JSONUtils.getString(tempJson, "long5");
				String lat5=JSONUtils.getString(tempJson, "lat5");
				String long6=JSONUtils.getString(tempJson, "long6");
				String lat6=JSONUtils.getString(tempJson, "lat6");
				String long7=JSONUtils.getString(tempJson, "long7");
				String lat7=JSONUtils.getString(tempJson, "lat7");
				String long8=JSONUtils.getString(tempJson, "long8");
				String lat8=JSONUtils.getString(tempJson, "lat8");
				String long9=JSONUtils.getString(tempJson, "long9");
				String lat9=JSONUtils.getString(tempJson, "lat9");
				List<Area> areas=new ArrayList<Area>();  
				 if(StringUtils.isNoneEmpty(long1)&&StringUtils.isNoneEmpty(lat1)){
					 Area a=new Area(Double.parseDouble(long1),Double.parseDouble(lat1)); 
					 areas.add(a);
				 }
				 
				 if(StringUtils.isNoneEmpty(long2)&&StringUtils.isNoneEmpty(lat2)){
					 Area a=new Area(Double.parseDouble(long2),Double.parseDouble(lat2)); 
					 areas.add(a);
				 }
				 
				 if(StringUtils.isNoneEmpty(long3)&&StringUtils.isNoneEmpty(lat3)){
					 Area a=new Area(Double.parseDouble(long3),Double.parseDouble(lat3)); 
					 areas.add(a);
				 }
				 
				 if(StringUtils.isNoneEmpty(long4)&&StringUtils.isNoneEmpty(lat4)){
					 Area a=new Area(Double.parseDouble(long4),Double.parseDouble(lat4)); 
					 areas.add(a);
				 }
				 if(StringUtils.isNoneEmpty(long5)&&StringUtils.isNoneEmpty(lat5)){
					 Area a=new Area(Double.parseDouble(long5),Double.parseDouble(lat5)); 
					 areas.add(a);
				 }
				 
				 if(StringUtils.isNoneEmpty(long6)&&StringUtils.isNoneEmpty(lat6)){
					 Area a=new Area(Double.parseDouble(long6),Double.parseDouble(lat6)); 
					 areas.add(a);
				 }
				 
				 if(StringUtils.isNoneEmpty(long7)&&StringUtils.isNoneEmpty(lat7)){
					 Area a=new Area(Double.parseDouble(long7),Double.parseDouble(lat7)); 
					 areas.add(a);
				 }
				 
				 if(StringUtils.isNoneEmpty(long8)&&StringUtils.isNoneEmpty(lat8)){
					 Area a=new Area(Double.parseDouble(long8),Double.parseDouble(lat8)); 
					 areas.add(a);
				 }
				 
				 if(StringUtils.isNoneEmpty(long9)&&StringUtils.isNoneEmpty(lat9)){
					 Area a=new Area(Double.parseDouble(long9),Double.parseDouble(lat9)); 
					 areas.add(a);
				 }
				 if(GraphicalMain.isPointInPolygon(area.getPy(),area.getPx(),areas)){
					  tempadvIds.add(advid);
				 }
			}
			
		}
//		JSONArray result = new JSONArray();
//		for(int i=0;i<advs.size();i++){
//			String advertId=JSONUtils.getString(advs.getJSONObject(i),"advertid");
//			for(int j=0;j<tempadvIds.size();j++){
//				if(advertId.equals(tempadvIds.get(j))){
//					result.add(advs.getJSONObject(i));
//					break;
//				}				
//			}
//		}
		return tempadvIds;
	}
//	private List<String> getPriAdv(List<String> advids,Happen happen,String apmac){
//		List<JSONObject> getEvents=eventMangerDao.getEventHappen();
//		log.info("��ѯ�¼�:"+getEvents);
//		happen.setEvent(getEvents);
//		
//		List resultList = new ArrayList();
//		for(String advid:advids){
//			//��ѯ��������Ͷ������  
//			//Ϊ��������������Ҫ�Զ�����һ��������
//			log.info("�������ID"+advid);
//			List<JSONObject> advpri=vehicleWifiDao.getConditionByadvid(advid);
//			
//			for(JSONObject js:advpri){
//				log.info("�������ID"+advid+"  ����Ͷ������"+js);
//				List<JSONObject> _result=vehicleWifiDao.getifadv(advid, happen, js);
////				=======================================
//				log.info("���������Ľ��:"+_result);
//				//��ѯ��ǰAP��λ��
//				List<JSONObject> cur=vehicleWifiDao.getCurrentLatLongByMac(apmac);
//				//������������������������һ������ŵ�����Ͷ�ż�
//				log.info("��ǰAP��λ��:"+cur);
//				if(_result!=null&&_result.size()>0){
//				for(JSONObject addrres:_result){
//					String addressid=JSONUtils.getString(addrres, "addredssid");
//					log.info("�õ��Ĺ������ҪͶ�ŵ�ַ��addressid"+addressid);
//					if(StringUtils.isEmpty(addressid)||cur.size()==0){
//						log.info("���û�е�ַͶ������ֱ��ɸѡΪ����Ͷ��");
//	//					log.info("���������û�����ȵ�ַѡ��ģ�ֱ�Ӽӵ������漯���ŵĹ�漯��");
//						resultList.add(advid);
//					}else{
//						log.info("����е�ַͶ������");
//						JSONObject curaddress=cur.get(0);
//						List<JSONObject> addJson = vehicleWifiDao.getLatLong(addressid);
//						log.info("����ַͶ������Ϊ"+addJson.toString());
//	//					log.info("��������������ȵ�ַѡ��ģ��жϵ�ǰ��ַ�Ƿ��������");
//						for(JSONObject adJson:addJson){
//							List<Area> areas=new ArrayList<Area>();  
//						 String longitude1=(String) JSONUtils.getObject(adJson, "longitude1");
//						 String latitude1=(String) JSONUtils.getObject(adJson, "latitude1");
//						 if(StringUtils.isNoneEmpty(longitude1)&&StringUtils.isNoneEmpty(latitude1)){
//							 Area a=new Area(Double.parseDouble(longitude1),Double.parseDouble(latitude1)); 
//							 areas.add(a);
//						 }
//						 String longitude2=(String) JSONUtils.getObject(adJson, "longitude2");
//						 String latitude2=(String) JSONUtils.getObject(adJson, "latitude2");
//						 if(StringUtils.isNoneEmpty(longitude2)&&StringUtils.isNoneEmpty(latitude2)){
//							 Area a=new Area(Double.parseDouble(longitude2),Double.parseDouble(latitude2)); 
//							 areas.add(a);
//						 }
//						 String longitude3=(String) JSONUtils.getObject(adJson, "longitude3");
//						 String latitude3=(String) JSONUtils.getObject(adJson, "latitude3");
//						 if(StringUtils.isNoneEmpty(longitude3)&&StringUtils.isNoneEmpty(latitude3)){
//							 Area a=new Area(Double.parseDouble(longitude3),Double.parseDouble(latitude3)); 
//							 areas.add(a);
//						 }
//						 String longitude4=(String) JSONUtils.getObject(adJson, "longitude4");
//						 String latitude4=(String) JSONUtils.getObject(adJson, "latitude4");
//						 if(StringUtils.isNoneEmpty(longitude4)&&StringUtils.isNoneEmpty(latitude4)){
//							 Area a=new Area(Double.parseDouble(longitude4),Double.parseDouble(latitude4)); 
//							 areas.add(a);
//						 }
//						 String longitude5=(String) JSONUtils.getObject(adJson, "longitude5");
//						 String latitude5=(String) JSONUtils.getObject(adJson, "latitude5");
//						 if(StringUtils.isNoneEmpty(longitude5)&&StringUtils.isNoneEmpty(latitude5)){
//							 Area a=new Area(Double.parseDouble(longitude5),Double.parseDouble(latitude5)); 
//							 areas.add(a);
//						 }
//						 String longitude6=(String) JSONUtils.getObject(adJson, "longitude6");
//						 String latitude6=(String) JSONUtils.getObject(adJson, "latitude6");
//						 if(StringUtils.isNoneEmpty(longitude6)&&StringUtils.isNoneEmpty(latitude6)){
//							 Area a=new Area(Double.parseDouble(longitude6),Double.parseDouble(latitude6)); 
//							 areas.add(a);
//						 }
//						 String longitude7=(String) JSONUtils.getObject(adJson, "longitude7");
//						 String latitude7=(String) JSONUtils.getObject(adJson, "latitude7");
//						 if(StringUtils.isNoneEmpty(longitude7)&&StringUtils.isNoneEmpty(latitude7)){
//							 Area a=new Area(Double.parseDouble(longitude7),Double.parseDouble(latitude7)); 
//							 areas.add(a);
//						 }
//						 String longitude8=(String) JSONUtils.getObject(adJson, "longitude8");
//						 String latitude8=(String) JSONUtils.getObject(adJson, "latitude8");
//						 if(StringUtils.isNoneEmpty(longitude8)&&StringUtils.isNoneEmpty(latitude8)){
//							 Area a=new Area(Double.parseDouble(longitude8),Double.parseDouble(latitude8)); 
//							 areas.add(a);
//						 }
//						 String longitude9=(String) JSONUtils.getObject(adJson, "longitude9");
//						 String latitude9=(String) JSONUtils.getObject(adJson, "latitude9");
//						 if(StringUtils.isNoneEmpty(longitude9)&&StringUtils.isNoneEmpty(latitude9)){
//							 Area a=new Area(Double.parseDouble(longitude9),Double.parseDouble(latitude9)); 
//							 areas.add(a);
//						 }
//						 String longitude10=(String) JSONUtils.getObject(adJson, "longitude10");
//						 String latitude10=(String) JSONUtils.getObject(adJson, "latitude10");
//						 if(StringUtils.isNoneEmpty(longitude10)&&StringUtils.isNoneEmpty(latitude10)){
//							 Area a=new Area(Double.parseDouble(longitude10),Double.parseDouble(latitude10)); 
//							 areas.add(a);
//						 }
//						 Object longi=JSONUtils.getObject(curaddress,"longitude");
//						 Object lati=JSONUtils.getObject(curaddress,"latitude");
//	//					 log.info("��ǰ��λ�ľ�γ��"+longi.toString()+"   "+lati.toString());
//						 if(GraphicalMain.isPointInPolygon(Double.parseDouble(longi.toString()),Double.parseDouble(lati.toString()),areas)){
//							  log.info("===���ϵ�ַ����������������ŵ�������");
//							  resultList.add(advid);
//							  break;
//						  }
//						}
//				 }
//				}
//				}
//			}
//	}
//		return resultList;
//	}
}