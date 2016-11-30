package ws.connector.http;

/**
 * Created by wangsong09 on 2016/6/15.
 */
public class Constants {

    //public static String WEB_ROOT = System.getProperty("user.dir");
    public static String WEB_ROOT;//获取根目录

    public static String SHUT_DOWN_COMMAND = "/shutdown";

    public static final String Package = "ws.http";

    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;

    static {
        String str = Constants.class.getResource("/").toString().replaceAll("/target.*", "").replace("file:", "");
        if (str.startsWith("/home")) { //linuxOS
            WEB_ROOT = str;
        } else {
            WEB_ROOT = str.replaceFirst("/",""); //windows
        }
    }
}
