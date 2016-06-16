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

                String uri = request.getUri();

                //动态资源处理
                if (uri != null && uri.startsWith("/servlet/")) {
                    ServletProcessor servletProcessor1 = new ServletProcessor();
                    servletProcessor1.process(request, response);
                } else {
                    //静态资源处理
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request, response);
                }

                socket.close();
                System.out.println("===应答结束==");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
