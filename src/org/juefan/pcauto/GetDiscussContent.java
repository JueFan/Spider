package org.juefan.pcauto;

import java.util.ArrayList;
import java.util.List;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;
import org.juefan.spider.basic.TextMatch;

public class GetDiscussContent {

	public static final String TEXT1 = "http://price.pcauto.com.cn/comment/sg";
	public static String url = new String();
	public static int page;
	public static String titleString = new String();
	public static void setPage(String pa){
		try {
			page = (int) Math.ceil(Double.parseDouble(pa)/10);
		} catch (Exception e) {
			page = 0;
		}
	}
	
	public static void setTitle(String tString){
		titleString = TextMatch.MatchString("<h1 class=\"dTit black\">.*?</h1>", "<h1 class=\"dTit black\">|</h1>", tString);
	}

	public static void main(String[] args) {
		List<DiscussContent> contents = new ArrayList<DiscussContent>();
		GetCode getCode = new GetCode();

		for(int i = 1; i < 10000; i++){
			contents.clear();
			page = 0;
			url = TEXT1 + Integer.toString(i) + "/p1.html";
			GetCode.setUrl(url);
			GetCode.Visit();
			setPage(TextMatch.MatchString("<em>\\(\\d{1,3}条\\)</em>", "<em>\\(|条\\)</em>", getCode.getCodeString()));
			setTitle(getCode.getCodeString());
			System.out.println(titleString + "\t" + page);
			for(int j = 1; j <= page; j++){
				url = TEXT1 + Integer.toString(i) + "/p" + Integer.toString(j) + ".html";
				GetCode.setUrl(url);
				GetCode.Visit();
				List<String> list = TextMatch.MatchList("(?s)<table width='100%'.*?</table>", getCode.getCodeString());
				for(String string: list){
					contents.add(new DiscussContent(string));
				}
				for(DiscussContent content: contents){
					for(String string: content.memberList)
						FileIO.FileWrite(System.getProperty("user.dir") + "\\output\\pcauto\\" + Integer.toString(i) + "_" + titleString, string + "\n", true);
				}
			}
		}
	}
}
