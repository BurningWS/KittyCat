package test;

import java.io.*;
import java.net.Socket;

/**
 * Created by wangsong09 on 2016/5/16.
 */
public class SimpleClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        //simpleConnect();

        Socket s = new Socket("news.163.com", 80);
        InputStream is = s.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk"));

        PrintWriter pw = new PrintWriter(s.getOutputStream(), true); //auto-flush
        pw.println("GET / HTTP/1.1");
        pw.println("Host:news.163.com");
        pw.println();  //要有换行

        String str;

        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }
    }

    static void simpleConnect() throws IOException {
        Socket s = new Socket("localhost", 8080);

        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        pw.println("GET / HTTP/1.1");
        pw.println("Host:news.163.com");
        pw.println();
    }
}

