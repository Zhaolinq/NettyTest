package com.phey.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {
    
    public static void main(String[] args) throws IOException {
        int port = 8080;
        
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            out.println("QUERY_TIME_ORDER");
            String resp = in.readLine();
            System.out.println("resp : " + resp);
        } catch(Exception e) {
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
        
    }

}
