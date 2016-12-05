package ws.connector.http;


import lombok.Setter;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.util.StringManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by wangsong09 on 2016/6/14.
 */
public class HttpConnector implements Runnable, Lifecycle {

    /**
     * Timeout value on the incoming connection.
     * Note : a value of 0 means infinite timeout.
     */
    private int connectionTimeout = Constants.DEFAULT_CONNECTION_TIMEOUT;
    /**
     * Use TCP no delay ?
     */
    private boolean tcpNoDelay = true;
    @Setter
    private static boolean stopped = false;

    private int maxProcessors = 20;
    private int curProcessors = 0;
    private Stack<HttpProcessor> processors = new Stack(); //是支持并发的

    /**
     * The string manager for this package.
     */
    private StringManager sm =
            StringManager.getManager(Constants.Package);

    @Override
    public void run() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8888, 1);
            System.out.println("====开始监听===");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stopped) {
            try {
                Socket socket = ss.accept(); //注意并发,我猜chrome可能发了不只一个请求，但只返回第一个成功的响应
                System.out.println("===有请求===");

                socket.setSoTimeout(connectionTimeout); //这个可以解决上面的问题。没请求则返回
                socket.setTcpNoDelay(tcpNoDelay);

                HttpProcessor processor = createProcessor();
                if (processor != null) {
                    processor.process(socket);
                } else {
                    socket.close();
                    throw new Exception(sm.getString("httpConnector.noProcessor"));
                    //continue;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private HttpProcessor createProcessor() {
        synchronized (processors) {
            if (processors.size() > 0) {
                return processors.pop();
            }
            if (curProcessors < maxProcessors) {
                return newProcessor();
            }
            if (maxProcessors < 0) {
                return newProcessor();
            } else {
                return null;
            }
        }
    }

    private HttpProcessor newProcessor() {
        return new HttpProcessor(this, curProcessors++);
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void stop() throws LifecycleException {

    }
}
