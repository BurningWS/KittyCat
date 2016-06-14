package myex01;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by wangsong09 on 2016/6/14.
 */
public class Request {

    private Socket socket;

    @Getter
    private String uri;

    public Request(Socket socket) {
        this.socket = socket;
    }

    public void parse() {
        try {
            System.out.println("处理端口号：" + socket.getPort());
            System.out.println("服务端口号：" + socket.getLocalPort());

            //请求信息都写入inputstram里了
            InputStream inputStream = socket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //打印请求
            for (boolean first = true;bufferedReader.ready();) {
                String str = bufferedReader.readLine();
                if (first) {
                    uri = str.split(" ")[1];  //从请求头获取uri
                    first = false;
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
