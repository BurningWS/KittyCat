package ws.http;


import util.FacadeInvocationHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by wangsong09 on 2016/6/15.
 */
public class ServletProcessor {

    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        String uri = httpRequest.getUri();
        String servletName = "test." + uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("servletName:" + servletName);

        String classPath = Constants.WEB_ROOT + File.separator + "target\\classes";
        System.out.println("classPath:" + classPath);
        try {
            URL[] urls = {new URL("file", null, classPath)};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);

            //输出类加载器
            /*for (ClassLoader cur = urlClassLoader, parent; cur != null; cur = parent) {
                parent = cur.getParent();
                System.out.println(cur + "----" + parent);
            }*/

            Class<?> servletClass = urlClassLoader.loadClass(servletName);

            ServletRequest requestFacade = (ServletRequest) new FacadeInvocationHandler(httpRequest, ServletRequest.class).getProxy();
            ServletResponse responseFacade = (ServletResponse) new FacadeInvocationHandler(httpResponse, ServletResponse.class).getProxy();

            Servlet s = (Servlet) servletClass.newInstance();
            s.service(requestFacade, responseFacade);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
