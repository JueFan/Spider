package org.juefan.spider.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextMatch {

	
	/**
	 * 循环查找匹配串
	 * @param regex 正则
	 * @return 匹配到的字符串链表
	 */
	public  static List<String> MatchList(String regex, String textString){
		List<String> list = new ArrayList<>();
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(textString);
		while (matcher.find()) {
			list.add(matcher.group());		
		}
		return list;
	}
	
	/**
	 * 循环查找匹配串
	 * @param regex 正则
	 * @return 匹配到的字符串链表
	 */
	public  static List<String> MatchList(String regex, String replace, String textString){
		List<String> list = new ArrayList<>();
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(textString);
		while (matcher.find()) {
			list.add(matcher.group().replaceAll(replace, ""));		
		}
		return list;
	}
	
	
	/**
	 * 查找最近的匹配串
	 * @param regex 正则
	 * @return 第一次匹配到的字符串 或者 NULL
	 */
	public  static String MatchString(String regex, String textString){
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(textString);
		if (matcher.find())
			return matcher.group();
		return null;
	}
	
	/**
	 * 查找最近的匹配串
	 * @param regex 正则
	 * @param replace 替换正则
	 * @return 第一次匹配到的字符串清理后的结果
	 */
	public  static String MatchString(String regex, String replace, String textString){
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(textString);
		if (matcher.find()){
			return matcher.group().replaceAll(replace, "");
		}
		return "失败";
	}
	
	



	public static void main(String[] args) {

	}
}
