package ws.http;

import lombok.Setter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by wangsong09 on 2016/6/14.
 */
public class HttpResponse implements HttpServletResponse {

    private Socket socket;

    @Setter
    private HttpRequest httpRequest;

    public HttpResponse(Socket socket) {
        this.socket = socket;
    }

    public void sendStaticResource() {
        OutputStream outputStream = null;
        PrintWriter printWriter = null;
        try {
            outputStream = socket.getOutputStream();
            printWriter = getWriter();

            String uri = httpRequest.getUri();

            if (Constants.SHUT_DOWN_COMMAND.equalsIgnoreCase(uri)) {
                HttpConnector.setStopped(true);
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
        return new PrintWriter(new OutputStreamWriter(outputStream, "utf-8"), true);
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

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String name) {
        return false;
    }

    @Override
    public String encodeURL(String url) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return null;
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {

    }

    @Override
    public void sendError(int sc) throws IOException {

    }

    @Override
    public void sendRedirect(String location) throws IOException {

    }

    @Override
    public void setDateHeader(String name, long date) {

    }

    @Override
    public void addDateHeader(String name, long date) {

    }

    @Override
    public void setHeader(String name, String value) {

    }

    @Override
    public void addHeader(String name, String value) {

    }

    @Override
    public void setIntHeader(String name, int value) {

    }

    @Override
    public void addIntHeader(String name, int value) {

    }

    @Override
    public void setStatus(int sc) {

    }

    @Override
    public void setStatus(int sc, String sm) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }
}
