package org.juefan.pcauto;

import java.util.ArrayList;
import java.util.List;

import org.juefan.spider.basic.GetCode;
import org.juefan.spider.basic.GetContent;
import org.juefan.spider.basic.TextMatch;

public class CmsDiscussContent extends GetContent{

	
	public List<String> memberList = new ArrayList<String>();

	public CmsDiscussContent(final String textString){
		setRegexMember();
		setMember(textString);
	}
	
	@Override
	public void setRegexMember() {
		regexMember.add(new Regex("(?s)<p class=\"commentContent\">.*?</p>", "(?s)<.*?>|\n|(?s)\t"));
		
	}

	@Override
	public void setMember(String textString) {
		for(Regex regex: regexMember){
		memberList.addAll(TextMatch.MatchList(regex.regex, regex.replace, textString));
		}
		
	}
	
	
	public static void main(String[] args) {
		GetCode getCode = new GetCode();
		GetCode.setUrl("http://cmt.pcauto.com.cn/topic/a0/r1/p1/ps30/t13087962.html");
		GetCode.Visit();
		CmsDiscussContent content = new CmsDiscussContent("");
		String context = getCode.getCodeString();
		content.setRegexMember();
		content.setMember(context);
		System.out.println(content.memberList);
	}

}
