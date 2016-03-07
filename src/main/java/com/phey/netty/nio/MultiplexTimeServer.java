package com.phey.netty.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;









public class MultiplexTimeServer implements Runnable {
    //多路复用器
    private Selector selector;
    
    private ServerSocketChannel channel;
    
    private volatile boolean stop;
    
    
    
    
    
    
    public MultiplexTimeServer(int port) {
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);//异步非阻塞
            channel.socket().bind(new InetSocketAddress(port), 1024);
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void stop() {
        stop = true;
    }

    
    public void run() {
        while(!stop) {
            try {
//                System.out.println(selector.isOpen());
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();//准备好的事件集合
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                SelectionKey key = null;
                while(iter.hasNext()) {
                    key = iter.next();
                    iter.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(key != null) {
                            key.cancel();
                            if(key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if(selector != null) {
            try {
                selector.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()) {
            if(key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();//完成三次握手
                sc.configureBlocking(false);
                
                sc.register(selector, SelectionKey.OP_READ);
            }
            
            if(key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("receive code : " + body);

                    String currentTime = "QUERY_TIME_ORDER".equalsIgnoreCase(body) 
                            ? new Date().toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                }else if(readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel sc, String response) throws IOException {
        if(response != null) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        };
    }
    
}
