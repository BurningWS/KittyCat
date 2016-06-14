package test;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultConfig {

	private static final String LINK = "https://www.zhihu.com/question/34895254";
	private static final String ASK = "https://www.zhihu.com/question/askpeople";
	private static final String COOKIE = "PUT YOUR COOKIE HERE";
			
	// test basic connect
	public static void main(String[] args) throws IOException {
		//HttpGet hg = new HttpGet(LINK);

		HttpPost hp = new HttpPost("http://qywx.dper.com/oauth/oauthCode?url=http%3a%2f%2fqywx.dper.com%2fapp%2fcheckin.html%23%2fanimation%3fagentId%3d46&agentId=46");
		//addH(hp);

		// addPost(hp);

		// postForm(httpclient);

		CloseableHttpClient httpclient = deaultHttpClient(hp, false);
		readHtml(httpclient, hp); // parse html
	}

	public static CloseableHttpClient deaultHttpClient(HttpRequestBase hb,
			boolean isProxy) {
		return buildClient(hb, isProxy);
	}

	// configure httpclient and request header
	public static CloseableHttpClient deaultHttpClient(HttpRequestBase hb) {
		return buildClient(hb, false);
	}

	private static CloseableHttpClient buildClient(HttpRequestBase hb,
			boolean isProxy) {
		HttpClientBuilder hcb = HttpClients.custom();
		if (isProxy) {
			hcb.setProxy(new HttpHost("127.0.0.1", 9999)); // proxy to listen
		}
		// addC(hcb); // add certificate
		addH(hb); // add header
		CloseableHttpClient httpclient = hcb.build();
		return httpclient;

	}

	public static void postForm(CloseableHttpClient httpclient)
			throws ClientProtocolException, IOException {
		int i = 1;
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			System.out.println(i++);
			String uid = it.next();

			System.out.println(uid);

			HttpPost hp = new HttpPost(ASK);
			addH(hp); // add header
			addPost(hp, uid, "remove");
			CloseableHttpResponse resp = httpclient.execute(hp);
			HttpEntity entity = resp.getEntity();
			entity.getContent().close(); // close continuely
		}
	}

	// add cookie and other info
	public static void addH(HttpRequestBase get) {
		// cookie is here..
		get.addHeader("Host", "qywx.dper.com");
		get.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; Mi-4c Build/LMY47V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile MQQBrowser/6.2 TBS/036215 Safari/537.36 MicroMessenger/6.3.15.49_r8aff805.760 NetType/WIFI Language/zh_CN");
		get.addHeader("Accept-Encoding", "gzip,deflate");
		get.addHeader("X-Requested-With", "com.tencent.mm");
		get.addHeader("Accept-Language", "zh-CN,en-US;q=0.8");
		//get.addHeader("", "");
		//get.addHeader("", "");
		//get.addHeader("", "");

		//get.addHeader("QQ-S-Encrypt", "rsapostaes");
		//get.addHeader("Q-UA2", "QV=3&PL=ADR&PR=WX&PP=com.tencent.mm&PPVN=6.3.15.49_r8aff805&TBSVC=25436&CO=BK&COVC=036215&PB=GE&VE=GA&DE=PHONE&CHID=0&LCID=9422&MO= Mi-4c &RL=1080*1920&OS=5.1.1&API=22");
		//get.addHeader("Referer", "http://101.226.68.112:8080");
		//get.addHeader("Q-GUID", "a8cadfd14a9e938818f9866613b788cb");
		//get.addHeader("qbkey", "4f1574d78a77bdac95948f9dfca301b1");
		//get.addHeader("QQ-S-ZIP", "gzip");
		//get.addHeader("Content-Type", "application/x-www-form-urlencoded");
		//get.addHeader("Accept", "*/*");

	}

	public static void addPost(HttpPost httppost, String uid, String op) {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("qid", "34895254")); // question
																	// id
		formparams.add(new BasicNameValuePair("uid", uid)); // user id
		formparams.add(new BasicNameValuePair("_source_type", "recommended"));
		formparams.add(new BasicNameValuePair("op", op)); // add or remove
		formparams.add(new BasicNameValuePair("_xsrf",
				"e6ad6299f5d150516ba67cddb5880040"));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				Consts.UTF_8);
		httppost.setEntity(entity);
	}

	static HashSet<String> set = new HashSet<>();

	// for testing..
	public static void readHtml(CloseableHttpClient httpclient,
			HttpRequestBase hb) throws ClientProtocolException, IOException {

		CloseableHttpResponse response = httpclient.execute(hb);
		HttpEntity entity = response.getEntity();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				entity.getContent(), "utf-8"));
		String con = null;
		while ((con = br.readLine()) != null) {
			// con = new String(con.getBytes(),"utf-8");
			System.out.println(con);
		}
	}

	public static void parse() throws IOException {
		Pattern p = Pattern.compile("(?<=data-uid=\")\\w+");
		Matcher m = null;

		BufferedReader br = new BufferedReader(new FileReader("D:/a.html"));
		String con = null;
		while ((con = br.readLine()) != null) {
			con = new String(con.getBytes(), "utf-8");
			m = p.matcher(con);
			while (m.find()) {
				// System.out.println(m.group());
				set.add(m.group());
			}
		}
		br.close();
	}
}
