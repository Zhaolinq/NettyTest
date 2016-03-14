package my.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 用户对网络事件进行读写操作
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("receive code : " + body);
        
        String currentTime = "QUERY_TIME_ORDER".equalsIgnoreCase(body) ? 
                new java.util.Date().toString() : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        //把待发送的消息放到发送缓冲数组中
        ctx.write(resp);
    }
    
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //将发送缓冲区的消息全部写到SocketChannel中
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
