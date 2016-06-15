package test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by wangsong09 on 2016/6/15.
 */
public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        URL url = new URL("file", null, "d:/test1/");
        System.out.println(url);

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
        Class<?> aClass = urlClassLoader.loadClass("test.AA");
        System.out.println(aClass);

        int[][] a = new int[2][5];
        System.out.println(SimpleClient.class.getName());
        System.out.println(SimpleClient.class.getCanonicalName());

        File file = new File("c:/a/d/c/s.x");
        System.out.println(file.getCanonicalPath());
    }
}