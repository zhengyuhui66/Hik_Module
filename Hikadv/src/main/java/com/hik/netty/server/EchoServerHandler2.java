package com.hik.netty.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hik.app.entity.DEVICE;
import com.hik.controller.DeviceMangerController;
import com.hik.dao.IDeviceMangerDao;
import com.hik.dao.IDeviceOfflineDao;
import com.hik.framework.utils.DateUtil;
import com.hik.framework.utils.JSONUtils;
import com.hik.service.APHeartbeatsService;
import com.hik.service.IDeviceMangerService;
import com.hik.service.IQueryTraceService;
import com.hik.util.Area;
import com.hik.util.ByteUtil;
import com.hik.util.CETCProtocol;
import com.hik.util.DataUtils;
import com.hik.util.PROCEDURCES;
import com.hik.util.SpringBeanUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.sf.json.JSONObject;


public class EchoServerHandler2 extends ChannelHandlerAdapter{
	protected Log log = LogFactory.getLog(this.getClass()); 
	
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	};
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		byte[] req = (byte[])msg;
		byte[] deBytes = CETCProtocol.DismantlingPackage(req);
		if (deBytes != null) {
			short s=ByteUtil.getReverseBytesShort(deBytes, 5);
			switch(s){
				case CETCProtocol.HostPacketTypes.HOST_PACKET_TYPE_REGISTER_REP_COMMAND: {
					// 注册请求，回复注册请求成功
					//记录设备编号
					try{
			    	String result_="";
			    	for(int i=7;i<28;i++){
						result_+=(char)deBytes[i];
					}
			    	//记录固件版本号
			    	String ver="V";
			    		ver+=deBytes[42];
			    		ver+="."+(deBytes[43]>9?deBytes[43]:"0"+deBytes[43]);
			    		ver+="."+(deBytes[44]>9?deBytes[44]:"0"+deBytes[44])+(deBytes[45]>9?deBytes[45]:"0"+deBytes[45]);
			    			IDeviceMangerService iDeviceMangerService =  (IDeviceMangerService) SpringBeanUtil.getBean("iDeviceMangerServiceImpl");
					    	String reporttime=iDeviceMangerService.queryDeviceGPSsupport(result_);
//					    	log.info("rtime::::"+rtime);
//					    	String[] rtimes=rtime.split("\\|");
//					    	int reporttime=0;
//					    	if("0".equals(rtimes[1])){
//					    		reporttime=Integer.parseInt(rtimes[0]);
//					    	}else{
//					    		reporttime=PROCEDURCES.GPSREPORTTIME;
//					    	}
//					    	log.info("==>注册-GPS上报时间间隔"+reporttime+"秒");
					    	byte[] reporttimes=new byte[4];
					    	ByteUtil.putReverseBytesInt(reporttimes, Integer.parseInt(reporttime),0);
					    	reporttimes = CETCProtocol.MakePacket(reporttimes, (int)CETCProtocol.HostPacketTypes.HOST_PACKET_TYPE_GPS_INTERVAL_COMMAND);
					    	ctx.write(reporttimes);
					    	
			    			byte[] arrContext = new byte[1];
					    	arrContext[0] = (byte)0x00;
					    	arrContext = CETCProtocol.MakePacket(arrContext, (int)CETCProtocol.ClientPacketTypes.CLINET_PACKET_TYPE_REGISTER_COMMAND);
					    	ctx.write(reporttimes);
					    	ctx.write(arrContext);
					    	APHeartbeatsService apHeartbeatsService= (APHeartbeatsService) SpringBeanUtil.getBean("apHeartbeatsServiceImpl");
					    	apHeartbeatsService.refreshHeartTime(result_);
//					    	log.info("已更新redis内部的心跳时间");
					    	IDeviceOfflineDao iDeviceOfflineDao =  (IDeviceOfflineDao) SpringBeanUtil.getBean("iDeviceOfflineDaoImpl");
					    	
//					    	queryDeviceOffline
					    	List<JSONObject> ifonline=iDeviceOfflineDao.queryDeviceOffline(result_);
//					    	---------------------------------------------------------------------------------------------------------
					    	IDeviceMangerDao iDeviceMangerdao= (IDeviceMangerDao) SpringBeanUtil.getBean("iDeviceMangerDaoImpl");
					    	List<JSONObject> list =iDeviceMangerdao.queryDeviceManger(result_);
					    	JSONObject r = list.get(0);
					    	String count = JSONUtils.getString(r, "count");
					    	if("0".equals(count)){
					    		String apmac=DataUtils.getMACStr(result_.substring(9, 21));
					    		DEVICE device = new DEVICE(null, result_, apmac, null, "1", null, null, reporttime+"", "2", null, null, null, null, "1", ver, PROCEDURCES.SPEED,PROCEDURCES.TIMEOUT);
					    		iDeviceMangerdao.addDeviceManger_unregister(device);
					    		int tempadddeviceOnline=iDeviceOfflineDao.addDeviceOnline(result_);
//						    	log.info("注册设备未有上线日志，则新增上线日志------->"+tempadddeviceOnline);
						    	iDeviceMangerdao.updateDeviceManger(result_, 1,ver);
					    	}else{
//					    		log.info("==>"+result_+"已注册");
						    	if(ifonline!=null&&ifonline.size()>0){
						    		JSONObject js=ifonline.get(0);
						    		String offlinetime=JSONUtils.getString(js, "offlinetime");
						    		if(StringUtils.isNotEmpty(offlinetime)){
//						    			log.info("注册设备已经下线，则新增上线日志");
						    			int tempadddeviceOnline=iDeviceOfflineDao.addDeviceOnline(result_);
								    	iDeviceMangerdao.updateDeviceManger(result_, 1,ver);
						    		}else{
//						    			log.info("注册设备处于在线状态");
						    		}
						    	}else{
							    	int tempadddeviceOnline=iDeviceOfflineDao.addDeviceOnline(result_);
//							    	log.info("注册设备未有上线日志，则新增上线日志------->"+tempadddeviceOnline);
							    	iDeviceMangerdao.updateDeviceManger(result_, 1,ver);
						    	}
			    		   }
						}catch(Exception e){
							e.printStackTrace();
							break;
						}
						ctx.flush();
						break;
				}									
				case CETCProtocol.HostPacketTypes.HOST_PACKET_TYPE_ALIVE_REP_COMMAND:{
					// 心跳
					String equipments="";
					for(int i=7;i<deBytes.length-1;i++){
						equipments+=(char)deBytes[i];
					}
					if(equipments.length()!=21){
						return;
					}
				   	IDeviceMangerService iDeviceMangerService =  (IDeviceMangerService) SpringBeanUtil.getBean("iDeviceMangerServiceImpl");
			    	String reporttime=iDeviceMangerService.queryDeviceGPSsupport(equipments);
			    	
//			    	String[] rtimes=rtime.split("\\|");
//			    	int reporttime=0;
//			    	if("0".equals(rtimes[1])){
//			    		reporttime=Integer.parseInt(rtimes[0]);
//			    	}else{
//			    		reporttime=PROCEDURCES.GPSREPORTTIME;
//			    	}
//			    	log.info("接收到的设备编码为:"+equipments+"==>心跳——GPS上报时间间隔"+reporttime+"秒");
			    	
			    	byte[] reporttimes=new byte[4];
			    	ByteUtil.putReverseBytesInt(reporttimes, Integer.parseInt(reporttime),0);
			    	reporttimes = CETCProtocol.MakePacket(reporttimes, (int)CETCProtocol.HostPacketTypes.HOST_PACKET_TYPE_GPS_INTERVAL_COMMAND);
			    	ctx.write(reporttimes);
					APHeartbeatsService apHeartbeatsService= (APHeartbeatsService) SpringBeanUtil.getBean("apHeartbeatsServiceImpl");
			    	apHeartbeatsService.refreshHeartTime(equipments);
					byte[] arrContex={};
					byte[] result = CETCProtocol.MakePacket(arrContex, (int)CETCProtocol.HostPacketTypes.HOST_PACKET_TYPE_ALIVE_REQ_COMMAND);
			    	ctx.write(result);
			    	ctx.flush();
			    	 break;
				}
				case CETCProtocol.HostPacketTypes.SJ_PACKET_TYPE_MOBILE_DEV_GPS_COMMAND:{
					//设备编号
					String log_="设备编号:";
					String equipmentid="";
					for(int i=7;i<28;i++){
						equipmentid+=(char)deBytes[i];
					}
					int ptime=ByteUtil.getReverseBytesInt(deBytes, 28);
					
					String time=DateUtil.stampToDate(ptime+"000");
					String[] tempdate=time.split("-");
//					String date=tempdate[0]+tempdate[1];
					
					log_+=equipmentid+"   采集时间："+time+" 经度:";
					String longitude="";
					for(int i=32;i<42;i++){
						log_+=(char)deBytes[i];
						longitude+=(char)deBytes[i];
					}
					
					log_+=" 纬度:";
					String latidude="";
					for(int i=42;i<52;i++){
						log_+=(char)deBytes[i];
						latidude+=(char)deBytes[i];
					}
					
					
				    Pattern pattern = Pattern.compile("^\\-?\\+?[0-9]{0,3}\\.[0-9]{4,10}$");
				    String regex = "\\s+";
				    longitude=longitude.trim().replaceAll(regex, "").replace('\0','0');
				    latidude=latidude.trim().replaceAll(regex, "").replace('\0','0');
//				    
//				    String[] lats = latidude.split("\\.");
//					String[] longs = longitude.split("\\.");
//
//					String _lats=Long.parseLong(lats[1])*100/60+"";
//					String _longs=Long.parseLong(longs[1])*100/60+"";
//					if(_lats.length()<5){
//						StringBuffer ss=new StringBuffer(_lats);
//						for(int i=ss.length();i<5;i++){
//							ss.insert(i-ss.length(), 0);
//						}
//						_lats=ss.toString();
//					}
//					if(_longs.length()<5){
//						StringBuffer ss=new StringBuffer(_longs);
//						for(int i=ss.length();i<5;i++){
//							ss.insert(i-ss.length(), 0);
//						}
//						_longs=ss.toString();
//					}
//					latidude=lats[0]+"."+_lats;
//					longitude=longs[0]+"."+_longs;
				    Matcher longitudematcher = pattern.matcher(longitude);
				    Matcher latidudematcher = pattern.matcher(latidude);
//				    log.info(equipmentid+"  经纬上报====:经度:|"+longitude+"|| 纬度:"+latidude+"|");
					if(longitudematcher.matches()&&latidudematcher.matches()){
						
//						IDeviceMangerService iDeviceMangerService =  (IDeviceMangerService) SpringBeanUtil.getBean("iDeviceMangerServiceImpl");
//						String rtime=iDeviceMangerService.queryDeviceGPSsupport(equipmentid);
//				    	String[] rtimes=rtime.split("\\|");
//				    	int times=Integer.parseInt(rtimes[0]);
//				    	int count=Integer.parseInt(rtimes[1]);
//						-------------------------------------------------------------------------
//						 Date d = new Date();
//					        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
//					        String date=dateFormat.format(d);
						APHeartbeatsService apHeartbeatsService= (APHeartbeatsService) SpringBeanUtil.getBean("apHeartbeatsServiceImpl");
				    	apHeartbeatsService.refreshGpsTime(equipmentid);
//					    Area area=DataUtils.gps84_To_Gcj02(Double.parseDouble(latidude),Double.parseDouble(longitude));
//				    	IQueryTraceService iQueryTraceService= (IQueryTraceService) SpringBeanUtil.getBean("iQueryTraceServiceImpl");
//						iQueryTraceService.addTrace(date,time, area.getPx()+"",area.getPy()+"", equipmentid);
//				    	-------------------------------------------------------------------------------------------------------
//				    	if(count==0){
//				    		//没有打开实时定位
//				    		log.info("没有打开实时定位");
				    		
//				    	}else{
//				    		//打开实时定位
//				    		 if(count*PROCEDURCES.GPSREPORTTIME<times){
//				    			log.info("没有定位次数没有超过上GPS定位时间");
////				    			 iDeviceMangerService.setDeviceGPSsupport(equipmentid,times,count+1);
//				    		 }else{
//				    			 log.info("没有定位次数超过上GPS定位时间  次数置0重新来");
//				    			 iDeviceMangerService.setDeviceGPSsupport(equipmentid, times,1);
//				    			 IQueryTraceService iQueryTraceService= (IQueryTraceService) SpringBeanUtil.getBean("iQueryTraceServiceImpl");
//								 iQueryTraceService.addTrace(date,time, longitude, latidude, equipmentid);
//				    		 }
				    		 //websocket重新传给界面展示
				    		TextWebSocketFrame tws = new TextWebSocketFrame("{\"equipmentid\":\""+equipmentid+"\",\"longitude\":\""+longitude+"\",\"latidude\":\""+latidude+"\"}");
							// 群发
//			    		TextWebSocketFrame tws = new TextWebSocketFrame("{\"equipmentid\":\""+equipmentid+"\",\"longitude\":\""+longitude+"\",\"latidude\":\""+latidude+"\"}");
			    		
							Global.group.writeAndFlush(tws);
//				    	}
//				    	log.info(log_);
						
					}else{
//						log.info("经纬上报异常2====:经度:|"+longitude+"|| 纬度:"+latidude+"|");
//						Pattern patterns = Pattern.compile("\\s+");
//						 Matcher longitudematchers = patterns.matcher(latidude);
						 String sq="是否有空格 ASSIC码:";
						 char[] ss=latidude.toCharArray();   
					    for(int i=0;i<ss.length;i++){
					    	sq+=" "+(int)ss[i];
					    }
//						log.info(sq);
						
						longitude=longitude.replace('\0','0');
						latidude=latidude.replace('\0','0');
//						log.info("经纬上报异常3====:经度:|"+longitude+"|| 纬度:"+latidude+"|");
					}
				}
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
//		log.info("链接空闲断开");
		super.channelInactive(ctx);
		ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
//		cause.printStackTrace();
//		log.info("异常断开链接:"+ cause.getMessage());
		ctx.close();
	}
	
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		super.userEventTriggered(ctx, evt);
		log.info("触发事件");
	}
public static void main(String[] args) {
//	  Pattern pattern = Pattern.compile("^\\-?\\+?[0-9]{0,3}\\.[0-9]{5}$");
//	   
//	    Matcher longitudematcher = pattern.matcher("123.11102");
//	    System.out.println(longitudematcher.matches());
	  Pattern pattern = Pattern.compile("^\\-?\\+?[0-9]{0,3}\\.[0-9]{4,10}$");
	  Matcher longitudematcher = pattern.matcher("120.08492"); 
	  System.out.println(longitudematcher.matches());
}
}
