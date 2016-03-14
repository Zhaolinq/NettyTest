package com.phey.netty.pack_unpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
    
    private int counter;
    
    private byte[] req;
    
    public TimeClientHandler() {
        req = ("QUREY_TIME_ORDER" + System.getProperty("line.separator")).getBytes();
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for(int i=0; i<100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.write(message);
            ctx.flush();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8");
        String body = (String) msg;
        System.out.println("resp_" + (++counter) + ":" + body);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warning("exception : " + cause.getMessage());
        ctx.close();
    }
}
