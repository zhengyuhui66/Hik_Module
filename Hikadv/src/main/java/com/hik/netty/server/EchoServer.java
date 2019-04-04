package com.hik.netty.server;

import com.hik.Exception.ReallyRunException;
import com.hik.Exception.ReallyStopException;
import com.hik.util.PROCEDURCES;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAppender;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class EchoServer extends Server{
	private Thread thread=null;
	public void TCPbind(int port){
		EventLoopGroup group = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		 try {
		 ServerBootstrap b= new ServerBootstrap();
		 b.group(group, worker).channel(NioServerSocketChannel.class)
			 .option(ChannelOption.SO_BACKLOG, 100)
			 .option(ChannelOption.SO_KEEPALIVE, true)
//			 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000)
//			 .handler(new LoggingHandler(LogLevel.INFO))
			 .childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
			        byte[] separate = {0x6a, 0x69};
			        ByteBuf delimiter = Unpooled.copiedBuffer(separate);
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192000, delimiter));
					ch.pipeline().addLast(new ByteArrayDecoder());
					ch.pipeline().addLast(new ByteArrayEncoder());
					ch.pipeline().addLast(new ReadTimeoutHandler(60));
					ch.pipeline().addLast(new EchoServerHandler2());
				}
			});
			 System.out.println("邦定端口成功");
			 ChannelFuture future = b.bind(port).sync();
			 System.out.println("等待服务监听端口关闭");
			 future.channel().closeFuture().sync();
			 System.out.println("关闭TCP服务器");
		 } catch (InterruptedException e) {
			e.printStackTrace();
		 } finally {
			System.out.println("终点关闭");
			group.shutdownGracefully();
			worker.shutdownGracefully();
		 }
	}
//	public void bind(int port){
//		 EventLoopGroup group = new NioEventLoopGroup();
//		 EventLoopGroup worker = new NioEventLoopGroup();
//		 try {
//			 ServerBootstrap b= new ServerBootstrap();
//			 b.group(group, worker).channel(NioServerSocketChannel.class)
//			 .option(ChannelOption.SO_BACKLOG, 100)
//			 .handler(new LoggingHandler(LogLevel.INFO))
//			 .childHandler(new ChannelInitializer<SocketChannel>() {
//				@Override
//				protected void initChannel(SocketChannel ch) throws Exception {
//					// TODO Auto-generated method stub
//					ch.pipeline().addLast(new StringDecoder());
//					ch.pipeline().addLast(new StringEncoder());
//					ch.pipeline().addLast(new ReadTimeoutHandler(600));
//					ch.pipeline().addLast(new EchoServerHandler());
//				}
//			});
//			 System.out.println("邦定端口成功");
//			 ChannelFuture future = b.bind(port).sync();
//			 System.out.println("等待服务监听端口关闭");
//			 future.channel().closeFuture().sync();
//		 } catch (InterruptedException e) {
//			e.printStackTrace();
//		 } finally {
//			System.out.println("server start! end 1");
//			group.shutdownGracefully();
//			worker.shutdownGracefully();
//		 }
//	}
//	public static void main(String[] args) {
//		try {
//			new EchoServer().TCPbind(16000);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	@Override
	public void StartServer(final int port) throws ReallyRunException{
		// TODO Auto-generated method stub
		if(thread!=null&&thread.isAlive()){
			throw new ReallyRunException("服务已经在跑");	
		}
		thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				TCPbind(port);
			}
		});
		thread.start();
	
	}
	@Override
	public void StartServer() throws ReallyRunException {
		// TODO Auto-generated method stub
		StartServer(PROCEDURCES.ONLINE_PORT);
	}
	@Override
	public void StopServer() throws ReallyStopException {
		// TODO Auto-generated method stub
		if(thread==null||thread.isInterrupted()){
			throw new ReallyStopException("服务线程早已中断");
		}
		thread.interrupt();
	}
}
