package com.hik.netty.client;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hik.util.CETCProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter{
	private int counter;
//	static final String ECHO_REQ="Hi LeiFeng welcome to Netty .$_";
	static final byte[] ECHO_REQ={(byte) 0xAB,(byte)0x67,(byte) 0x59,(byte)0x89,(byte)0x6a,(byte)0x69};//"Hi LeiFeng welcome to Netty .$_";

	private boolean sendalive;
	public EchoClientHandler(){};
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
    	byte[] arrContext = new byte[1];
    	arrContext[0]=(byte)0x0a;
    	arrContext = CETCProtocol.MakePacket(arrContext, (int)CETCProtocol.ClientPacketTypes.CLINET_PACKET_TYPE_REGISTER_COMMAND);
    	ctx.writeAndFlush(arrContext);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
//		byte[] req = (byte[])msg;
//		String result="This is"+ ++counter+" times recevie server:[";
//		for(int i=0;i<req.length;i++){
//			result+="==>"+req[i];
//		}
//		result+="]";
//		System.out.println("result:"+result);
//		RegestRepPacket reg = new RegestRepPacket(req);
//		if (reg.reg_status != 0) {
//			sendalive=true;
//			sendAlive(ctx);
//		}else{
//			
//		}
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
    /**
     * 定时发送心跳包
     * @param ctx
     */
    private void sendAlive(final ChannelHandlerContext ctx)
    {
    	if (!sendalive) {
            return;
          }
    	
    	if (ctx.channel().isActive()) {
    		byte[] arrContext = new byte[17];
    		arrContext[16] = 0x0a; // 订阅MAC
	    	arrContext = CETCProtocol.MakePacket(arrContext, (int)CETCProtocol.ClientPacketTypes.CLINET_PACKET_TYPE_ALIVE_COMMAND);
	    	ctx.writeAndFlush(Unpooled.copiedBuffer(arrContext));
    	}
    	
    	ctx.channel().eventLoop().schedule(new Runnable() {
			public void run() {
				sendAlive(ctx);
			}
		}, 5, TimeUnit.SECONDS);
    }
}
