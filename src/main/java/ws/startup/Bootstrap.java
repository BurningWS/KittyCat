package ws.startup;

import ws.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by wangsong09 on 2016/6/17 .
 */
public class Bootstrap {

    public static void main(String[] args) throws IOException {
        new HttpConnector().start();
    }
}
