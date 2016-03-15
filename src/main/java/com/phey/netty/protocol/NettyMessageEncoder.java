package com.phey.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage>{

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
            List<Object> out) throws Exception {
        if(msg == null || msg.getHeader() == null) {
            throw new Exception("encode message is null");
        }
        
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionID());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());
        sendBuf.writeInt(msg.getHeader().getAttachment().size());
    }

}
