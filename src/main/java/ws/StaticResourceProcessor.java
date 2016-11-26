package ws;

/**
 * Created by wangsong09 on 2016/6/15.
 */

import ws.http.HttpRequest;
import ws.http.HttpResponse;

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
