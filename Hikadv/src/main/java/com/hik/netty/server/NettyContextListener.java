package com.hik.netty.server;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.hik.Exception.ReallyStopException;
import com.hik.framework.utils.DateUtil;
import com.hik.netty.client.NettyServer;
import com.hik.rfid.ClientRfid;
import com.hik.rfid.ServerRfid;
import com.hik.rfid.message.CallBack;
import com.hik.rfid.message.ClientReport;
import com.hik.rfid.message.RequestMessage;
import com.hik.rfid.message.ServerReport;
import com.hik.rfid.message.clientactive.AccessReportReq;
import com.hik.rfid.message.clientactive.ErrorCodeReportReq;
import com.hik.rfid.message.clientactive.InventoryReportReq;
import com.hik.rfid.message.clientactive.ReadReportReq;
import com.hik.rfid.message.clientactive.RegisterReq;
import com.hik.rfid.message.cmsactive.OverMaxConnumReq;

public class NettyContextListener implements ServletContextListener{

	private Server echoServer=new EchoServer();
	private Server nettyServer= new NettyServer();
//	private ServerRfid rfidServer = new ServerRfid();
//	private ClientRfid clientRfid = new ClientRfid(); 
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		try {
			echoServer.StartServer();
			nettyServer.StartServer();
			
			
			
//			rfidServer.StartServer(new ServerReport() {
//				
//				@Override
//				public void registCallBackEvent(RegisterReq registerReq) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void readReportCallBackEvent(ReadReportReq readReportReq) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void inventoryReportCallBackEvent(InventoryReportReq inventoryReportReq) {
//					// TODO Auto-generated method stub
//					System.out.println("连续盘点结果盘点结果:"+inventoryReportReq.toString());
//				}
//				
//				@Override
//				public void errorCodeReportCallBackEvent(ErrorCodeReportReq errorCodeReportReq) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void accessReportCallBackEvent(AccessReportReq accessReportReq) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
//		System.out.println("服务器关闭！");
		try {
			echoServer.StopServer();
			nettyServer.StopServer();
//			try {
//				rfidServer.StopServer();
//			} catch (com.hik.rfid.exception.ReallyStopException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (ReallyStopException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
