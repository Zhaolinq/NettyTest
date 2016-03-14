package io.netty.handler.codec.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.msgpack.MessagePack;

public class MsgpackDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext arg0, ByteBuf arg1,
            List<Object> arg2) throws Exception {

        final int length = arg1.readableBytes();
        final byte[] array = new byte[length];
        arg1.getBytes(arg1.readerIndex(), array, 0, length);
        
        MessagePack msgpack = new MessagePack();
        arg2.add(msgpack.read(array));
    }

}
