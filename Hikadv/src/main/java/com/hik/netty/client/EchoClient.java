package com.hik.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
		private EventLoopGroup group = new NioEventLoopGroup();
//		private Executor executor = Executors.newScheduledThreadPool(1);
		public void connect(String host, int port) throws Exception{
			try{
				Bootstrap bootstrap = new Bootstrap();
				bootstrap.group(group);
				bootstrap.option(ChannelOption.TCP_NODELAY, true);
				bootstrap.channel(NioSocketChannel.class);
				bootstrap.handler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
//						ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
//						byte[] separate = {(byte) 0x89};
				        byte[] separate = {0x6a, 0x69};
				        ByteBuf delimiter = Unpooled.copiedBuffer(separate);
						System.out.println("初始化  符号4·");
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192000, delimiter));
							System.out.println("初始化  符号5·");
//							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new ByteArrayDecoder());
							ch.pipeline().addLast(new ByteArrayEncoder());
							System.out.println("初始化  符号6·");
							ch.pipeline().addLast(new EchoClientHandler());
					}
				});
				ChannelFuture future = bootstrap.connect(host,port).sync();
				future.channel().closeFuture().sync();
			}finally{
				group.shutdownGracefully();
			}
		}
		public static void main(String[] args) {
			try {
				new EchoClient().connect("127.0.0.1",18080);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
