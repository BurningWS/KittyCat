package ws.http;


import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wangsong09 on 2016/6/14.
 */
public class HttpConnector implements Runnable{

    @Setter
    private static boolean stopped = false;

    @Override
    public void run() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8888);
            System.out.println("====开始监听===");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stopped) {
            try {
                Socket socket = ss.accept(); //注意并发
                System.out.println("===有请求===");

                new HttpProcessor(socket).process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        new Thread(this).start();
    }
}