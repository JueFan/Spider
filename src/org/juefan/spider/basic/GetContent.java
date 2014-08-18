package org.juefan.spider.basic;

import java.util.ArrayList;
import java.util.List;

public abstract class GetContent {

	public   List<Regex> regexMember = new ArrayList<Regex>();
	
	/**正则表示式存储*/
	public abstract  void setRegexMember();

	/**获取结构内容*/
	public abstract void setMember(final String textString);


	/**存储正则表达式*/
	public class Regex{
		public String regex;
		public String replace;
		public Regex(String reg, String rep){
			regex = reg;
			replace = rep;
		}
	}


}
