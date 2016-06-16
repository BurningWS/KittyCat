package myex01;

import lombok.Setter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.Locale;

/**
 * Created by wangsong09 on 2016/6/14.
 */
public class Response implements ServletResponse {

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

            if (Constants.SHUT_DOWN_COMMAND.equalsIgnoreCase(uri)) {
                HttpServer.setShutdown(true);
                printWriter.println("关闭服务器");
                return;
            }

            if (uri != null) {

                System.out.println("请求文件路径：" + Constants.WEB_ROOT + uri);

                File file = new File(Constants.WEB_ROOT, uri);
                if (file.isFile()) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    while (br.ready()) {
                        printWriter.println(br.readLine());
                    }
                    return;
                }
            }
            printWriter.println("HTTP/1.1 404 File Not Found");
            printWriter.println();
            Thread.sleep(3000);
            printWriter.println("<h1>File Not Found</h1>");

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        return new PrintWriter(new OutputStreamWriter(outputStream, "gbk"), true);
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
