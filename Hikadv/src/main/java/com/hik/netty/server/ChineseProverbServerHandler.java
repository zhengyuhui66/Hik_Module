package com.hik.netty.server;

import java.nio.charset.Charset;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{
	private static final String[] DICTIONARY={"ֻҪ���������ĥ����","��ʱ��л��ǰ�࣬����Ѱ�����ռ�","�������������ʣ�һƬ���������","һ�����һ��𣬴������罱����","����������־��ǧ���ʿĹ�꣬׳�Ĳ���"};
	
	private String nextQuote(){
		int quoteId=ThreadLocalRandom.current().nextInt(DICTIONARY.length);
		return DICTIONARY[quoteId];
	}
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		// TODO Auto-generated method stub
		String req = packet.content().toString(CharsetUtil.UTF_8);
		System.out.println(req);
		
		if("�����ֵ��ѯ?".equals(req)){
			ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("�����ѯ���:"+nextQuote(),CharsetUtil.UTF_8), packet.sender()));
		}
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
		ctx.close();
	}

}
