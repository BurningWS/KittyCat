package myex01;

/**
 * Created by wangsong09 on 2016/6/15.
 */

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 静态资源处理
 */
public class StaticResourceProcessor {

    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
