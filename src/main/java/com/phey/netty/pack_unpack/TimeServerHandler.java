package com.phey.netty.pack_unpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

    private int  counter;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8").substring(0, req.length-System.getProperty("line.separator").length());
//        System.out.println("receive code : " + body + "; counter :" + (++counter));
//        String currentTime = "QUERY_TIME_ORDER".equalsIgnoreCase(body) ? new java.util.Date().toString() : "BAD ORDER";
//        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//        ctx.write(resp);
        
        String body = (String)msg;
        System.out.println("receive code : " + body + "; counter :" + (++counter));
        String currentTime = "QUERY_TIME_ORDER".equalsIgnoreCase(body) ? new java.util.Date().toString() : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
        ctx.flush();
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
