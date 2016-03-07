package com.phey.netty.nio;

import java.io.IOException;

public class TimeClient {
    
    public static void main(String[] args) throws IOException {
        int port = 8081;
        
        new Thread(new TimeClientHandle("127.0.0.1", port)).start();
    }

}
