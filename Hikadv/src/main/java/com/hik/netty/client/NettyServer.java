package com.hik.netty.client;

import com.hik.Exception.ReallyRunException;
import com.hik.Exception.ReallyStopException;
import com.hik.netty.server.Server;
import com.hik.util.PROCEDURCES;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
public class NettyServer extends Server{
//	public static void main(String[] args) {
//		
//		new NettyServer().run();
//	}
	private Thread thread=null;
	public void Bind(int port){
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new ChildChannelHandler());
			
			System.out.println("服务端开启等待客户端连接 ... ...");
			
			Channel ch = b.bind(7397).sync().channel();
			
			ch.closeFuture().sync();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}

	@Override
	public void StartServer(final int port) throws ReallyRunException {
		// TODO Auto-generated method stub
		if(thread!=null&&thread.isAlive()){
			throw new ReallyRunException("服务已经在跑");	
		}
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bind(port);
			}
		});
		thread.start();
	}

	@Override
	public void StartServer() throws ReallyRunException {
		// TODO Auto-generated method stub
		StartServer(PROCEDURCES.POSITION_PORT);
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