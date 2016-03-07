package com.phey.netty.bio.threadpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.phey.netty.bio.TimeServerHandler;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            Socket socket = null;
            while(true) {
                socket = server.accept();
                
                TimeServerHandlerExecutePool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(server != null) {
                server.close();
                server = null;
            }
        }
    }
}
