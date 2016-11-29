package ws;

/**
 * Created by wangsong09 on 2016/6/15.
 */

import ws.connector.http.HttpRequest;
import ws.connector.http.HttpResponse;

/**
 * 静态资源处理
 */
public class StaticResourceProcessor {

    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.sendStaticResource();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
