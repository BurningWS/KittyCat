package ws.http;

/**
 * Created by wangsong09 on 2016/6/15.
 */

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
