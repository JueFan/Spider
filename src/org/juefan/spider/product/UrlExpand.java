package org.juefan.spider.product;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定产品ID，按特定规则扩展成符合要求的点击页URL
 * @author juefan
 */
public class UrlExpand {
	public String categoryId;
	public String initurl;
	public List<String> urList;
	public final String TEXT1;
	public final String TEXT2;
	public final String TEXT3;
	public int pages;
	
	public UrlExpand(){
		TEXT1 = "http://product.pconline.com.cn/pdlib/";
		TEXT2 = "_comment1";
		TEXT3 = ".html";
		categoryId = new String();
		initurl = new String();
		urList = new ArrayList<String>();
		pages = 0;
	}
	
	/**设置产品ID*/
	public void setCategory(String caString){
		this.categoryId = caString;
	}
	/**扩展后的点评库第一页*/
	public void setIniturl(){
		this.initurl = TEXT1 + categoryId + TEXT2 + TEXT3;
	}
	/**按照特定规则扩展点评页URL*/
	public void Expand(){
		String tmpString = new String();
		tmpString = TEXT1 + categoryId + TEXT2 + TEXT3;
		urList.add(tmpString);
		for(int i = 2; i <= pages; i++){
			tmpString = TEXT1 + categoryId + TEXT2 + "_" + Integer.toString(i) + TEXT3;
			urList.add(tmpString);
		}
	}
	
	public static void main(String[] arsgStrings){
		UrlExpand urlExpand = new UrlExpand();
		urlExpand.categoryId = "000001";
		urlExpand.pages = 5;
		urlExpand.Expand();
		for(String e:urlExpand.urList){
			System.out.println(e);
		}
	}

}
