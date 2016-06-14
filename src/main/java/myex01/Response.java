package myex01;

import lombok.Setter;

import java.io.*;
import java.net.Socket;

/**
 * Created by wangsong09 on 2016/6/14.
 */
public class Response {

    private Socket socket;

    @Setter
    private Request request;

    public Response(Socket socket) {
        this.socket = socket;
    }

    public void sendStaticResource() {
        OutputStream outputStream = null;
        PrintWriter printWriter = null;
        try {
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "gbk"), true);

            String uri = request.getUri();
            if ("shutdown".equalsIgnoreCase(HttpServer.SHUT_DOWN_COMMAND)) {
                printWriter.println("关闭服务器");
                printWriter.close();
                System.exit(1);
            }

            System.out.println("请求文件路径：" + HttpServer.WEB_ROOT + uri);

            File file = new File(HttpServer.WEB_ROOT, uri);

            if (file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while (br.ready()) {
                    printWriter.println(br.readLine());
                }
            } else {
                printWriter.println("HTTP/1.1 404 File Not Found");
                printWriter.println();
                Thread.sleep(3000);
                printWriter.println("<h1>File Not Found</h1>");
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

}
