package my.test.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
    
    public void bind(int port) {
        //线程组，包含一组NIO线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();//用户服务器端接受客户端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();//用户SocketChannel的网络读写
        
        try {
            //Netty用户启动NIO服务端的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//对应NIO中的ServerSocketChannel类
                    .option(ChannelOption.SO_BACKLOG, 1024)//NioServerSocketChannel的TCP参数
                    .childHandler(new ChildChannelHandler());//绑定IO事件处理类
            
            ChannelFuture f = b.bind().sync();//同步阻塞方法等待绑定成功
            
            f.channel().closeFuture().sync();//阻塞到服务器链路关闭
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        public void initChannel(SocketChannel arg0) throws Exception {
            arg0.pipeline().addLast(new TimeServerHandler());
        }
        
    }
    
    public static void main(String[] args) {
        int port = 8081;
        new TimeServer().bind(port);
    }
}
