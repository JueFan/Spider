package org.juefan.pcauto;

import java.util.ArrayList;
import java.util.List;

import org.juefan.spider.basic.GetContent;
import org.juefan.spider.basic.TextMatch;

public class DiscussContent extends GetContent {
	
	public String Advantage;
	public String Disadvantage;
	public String Exterior;
	public String Interior;
	public String Space;
	public String Configuration;
	public String Power;
	public String Control;
	public String Fuel;
	public String Comfort;
	public String Maintenance;
	public List<String> memberList = new ArrayList<String>();
	
	public DiscussContent(final String textString){
		setRegexMember();
		setMember(textString);
	}
	
	@Override
	public void setMember(final String textString) {
		for(Regex regex: regexMember){
			memberList.add(TextMatch.MatchString(regex.regex, regex.replace, textString));
		}	
	}
	
	public  void setRegexMember() {
		regexMember.add(new Regex("(?s)<tr name=\"label_1.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_2.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_3.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_4.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_5.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_6.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_7.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_8.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_9.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_10.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
		regexMember.add(new Regex("(?s)<tr name=\"label_11.*?</td>", "(?s)<tr.*?th>|</th>.*?<td>|(?s)</td>"));
	}
	
	
	
	public static void main(String[] args) {
		/*DiscussContent content = new DiscussContent();
		String context = " <tr name=label_6><th>配置：\n</th><td>高配居然没有疝气大灯，导航，倒车影像</tr>" +
				"<tr name=label_7><th>配置：</th><td>高配居然没有疝气大灯，导航，倒车影像</tr>";
		String regex = "<.*?>";
		String replace = "<|>";
		content.setRegexMember();
		content.setMember(context);
		System.out.println(content.memberList);*/
	}




}
