package ws;


import util.FacadeInvocationHandler;
import ws.connector.http.HttpRequest;
import ws.connector.http.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by wangsong09 on 2016/6/15.
 */
public class ServletProcessor {

    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        String uri = httpRequest.getRequestURI();
        String servletName = "test." + uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("servletName:" + servletName);

        //String classPath = Constants.WEB_ROOT + File.separator + "target\\classes";
        //URL[] urls = {new URL("file", null, classPath)};
        URL url = this.getClass().getClassLoader().getResource("");
        System.out.println(url);
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});

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
