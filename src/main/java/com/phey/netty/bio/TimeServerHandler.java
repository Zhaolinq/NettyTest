package com.phey.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable {
    
    private Socket socket;
    
    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = null;
            PrintWriter out = null;
            
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                String currentTime = null;
                String body = null;
                
                while(true) {
                    body = in.readLine();
                    if(body == null) break;
                    System.out.println("receive code : " + body);
                    currentTime = "QUERY_TIME_ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    out.println(currentTime);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if(in != null) {
                    in.close();
                    in = null;
                }
                
                if(out != null) {
                    out.close();
                    out = null;
                }
                
                if(socket != null) {
                    socket.close();
                    socket = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
