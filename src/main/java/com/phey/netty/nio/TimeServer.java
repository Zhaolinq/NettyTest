package com.phey.netty.nio;

import java.io.IOException;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8081;

        new Thread(new MultiplexTimeServer(port)).start();
       
    }
}
