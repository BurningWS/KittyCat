package test;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangsong09 on 2016/6/16.
 */
public class TestServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("**********GET INTO TestServlet*************");
        System.out.println("代理类:" + req + "|类加载器:" + req.getClass().getClassLoader());
        PrintWriter pw = res.getWriter();

        pw.print("TestServlet正在处理");
        try {
            Thread.sleep(2000); //让多线程处理展示明显
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pw.close();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
