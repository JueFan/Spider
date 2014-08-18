package org.juefan.spider.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * 获取网页的源码
 * @author juefan
 */
public class GetCode {

	private static String urlString;
	private StringBuilder sourceCode;
	public  static StringBuffer buffer;
	private static String codeString;

	public GetCode(){
		urlString = new String();
		sourceCode = new StringBuilder("");
		codeString = new String();
	}

	public static void setUrl(String urlString){
		GetCode.urlString = urlString;
	}

	public static  String getUrl(){
		return GetCode.urlString;
	}

	/**返回指定网页的源代码*/
	public String getCodeString(){
		//System.out.println("返回的长度为 " + GetCode.codeString.length());
		return GetCode.codeString;
	}

	protected void Change1(){
		codeString = sourceCode.toString();
	}
	protected static void Change2(){
		codeString = buffer.toString();
	}

	/**
	 * 访问并获得网页的源码
	 */
	public static void Visit(){
		Properties prop = System.getProperties();   
		 // 设置http访问要使用的代理服务器的地址   
		 prop.setProperty("http.proxyHost", "192.168.11.254");   
		 // 设置http访问要使用的代理服务器的端口   
		 prop.setProperty("http.proxyPort", "8080");   
		 //用户名密码
		/* prop.setProperty("http.proxyUser","baivfhpiaqg@163.com");   
		 prop.setProperty("http.proxyPassword","");*/
		buffer = new StringBuffer();
		String strPostRequest = new String();
		System.setProperty("sun.net.client.defaultConnectTimeout", "2000");
		System.setProperty("sun.net.client.defaultReadTimeout", "2000");
		try{
			URL url = new URL(urlString);
			HttpURLConnection hConnect = (HttpURLConnection) url
					.openConnection();
			if (strPostRequest.length() > 0) {
				hConnect.setDoOutput(true);
				OutputStreamWriter out = new OutputStreamWriter(hConnect
						.getOutputStream());
				out.write(strPostRequest);
				out.flush();
				out.close();
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					hConnect.getInputStream(), "UTF-8"));
			int ch;
			for (; (ch = rd.read()) > -1; )
				buffer.append((char) ch);
		}
		catch (Exception e) {
			//e.printStackTrace();
		}  
		Change2();
	}

	public static void main(String[] args){
		GetCode getCode = new GetCode();
		GetCode.setUrl("http://weibo.com/juefanc/home?topnav=1&wvr=5");
		GetCode.Visit();
		System.out.println(getCode.getCodeString());
	}

}
