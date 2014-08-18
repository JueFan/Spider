package org.juefan.pcauto;

import java.util.ArrayList;
import java.util.List;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;
import org.juefan.spider.basic.TextMatch;

public class GetCmsDiscussContent {

	public static final String TEXT1 = "http://cmt.pcauto.com.cn/topic/a0/r1/p";
	public static final String TEXT2 = "/ps30/";

	public static final String ID_STRING = "t13088452.html;"+
			"t13087814.html;"+
			"t13087806.html;";

	public static String url = new String();
	public static int page;
	public static String titleString = new String();
	public static void setPage(String pa){
		try {
			page = (int) Math.ceil(Double.parseDouble(pa)/30);
		} catch (Exception e) {
			page = 0;
		}
	}

	public static void setTitle(String tString){
		titleString = TextMatch.MatchString("(?s)<title>.*?</title>", "(?s)<title>|(?s)</title>", tString);
	}

	public static void main(String[] args) {
		List<CmsDiscussContent> contents = new ArrayList<CmsDiscussContent>();
		GetCode getCode = new GetCode();
		for(String id: ID_STRING.split(";")){
			page = 0;
			url = TEXT1 + Integer.toString(1) + TEXT2 + id;
			GetCode.setUrl(url);
			GetCode.Visit();
			setPage(TextMatch.MatchString("<em id=\"comment_total\" class=\"red\">\\d{1,3}</em>", 
					"<em id=\"comment_total\" class=\"red\">|</em>", getCode.getCodeString()));
			setTitle(getCode.getCodeString());
			for(int i = 1; i <= page; i++){
				url = TEXT1 + Integer.toString(i) + TEXT2 + id;
				GetCode.setUrl(url);
				GetCode.Visit();
				contents.add(new CmsDiscussContent(getCode.getCodeString()));
			}

			for(CmsDiscussContent content: contents){
				for(String string: content.memberList)
					FileIO.FileWrite(System.getProperty("user.dir") + "\\output\\pcauto\\广州车展\\" + "HB_" + "菲翔"+ ".txt", string.trim() + "\n", true);
			}
		}
	}
}
