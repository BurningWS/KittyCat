package myex01;


import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wangsong09 on 2016/6/14.
 */
public class HttpServer {


    public static void main(String[] args) throws IOException {
        new HttpServer().await();
    }

    @Setter
    private static boolean shutdown = false;

    private void await() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8088);
            System.out.println("====开始监听===");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!shutdown) {
            try {
                Socket socket = ss.accept(); //注意并发
                System.out.println("===有请求===");
                Request request = new Request(socket);
                request.parse();

                Response response = new Response(socket);
                response.setRequest(request);

                StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                staticResourceProcessor.process(request, response);

                socket.close();
                System.out.println(request.getUri());
                System.out.println("===应答结束==");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
