package com.phey.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {

    private int port;
    
    CountDownLatch latch;
    
    AsynchronousServerSocketChannel channel;
    
    public AsyncTimeServerHandler(int port) {
        this.port = port;
        
        try {
            channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(this.port));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }
    
    public void run() {
        //完成一组任务之前，允许当前线程一直阻塞
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 接受客户端连接
     */
    private void doAccept() {
        channel.accept(this, new AcceptCompletionHandler());
    }
}
