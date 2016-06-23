package ws.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by wangsong09 on 2016/6/17.
 */
public class HttpProcessor {

    private Socket socket;

    public HttpProcessor(Socket socket) {
        this.socket = socket;
    }

    public void process() {
        try {
            HttpRequest httpRequest = new HttpRequest(socket);
            HttpResponse httpResponse = new HttpResponse(socket);
            httpResponse.setHttpRequest(httpRequest);

            parse(httpRequest, httpResponse);

            String uri = httpRequest.getUri();
            //动态资源处理
            if (uri != null && uri.startsWith("/servlet/")) {
                ServletProcessor processor = new ServletProcessor();
                processor.process(httpRequest, httpResponse);
            } else {
                //静态资源处理
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(httpRequest, httpResponse);
            }

            socket.close();
            System.out.println("===应答结束==");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {

            System.out.println("处理端口号：" + socket.getPort());
            System.out.println("服务端口号：" + socket.getLocalPort());

            //请求信息都写入inputstram里了
            InputStream inputStream = socket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //打印请求
            for (boolean first = true; bufferedReader.ready(); ) {
                String str = bufferedReader.readLine();
                if (first) {
                    String uri = str.split(" ")[1];  //从请求头获取uri
                    httpRequest.setUri(uri);
                    first = false;
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
