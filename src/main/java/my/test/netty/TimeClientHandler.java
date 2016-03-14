package my.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
    
    private final ByteBuf firstMessage;
    
    public TimeClientHandler() {
        byte[] req = "QUERY_TIME_ORDER".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //TCP连接建立成功时候，Netty的NIO会调用该方法
        ctx.write(firstMessage);
        ctx.flush();
    }
    
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //服务器返回应答消息时，该方法被调用
        try {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "UTF-8");
            System.out.println("Now is : " + body);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warning(cause.getMessage());
        ctx.close();
    }
}
