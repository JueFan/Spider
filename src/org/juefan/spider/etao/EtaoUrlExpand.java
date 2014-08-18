package org.juefan.spider.etao;

import java.util.ArrayList;
import java.util.List;


public class EtaoUrlExpand {
	public String categoryId;
	public String categoryName;
	public String initurl;
	public int page;
	public List<String> urList;
	
	public EtaoUrlExpand(){
		categoryId = new String();
		initurl = new String();
		urList = new ArrayList<String>();
		page = 1;
	}
	
	/**设置产品ID*/
	public void setCategory(String caString){
		this.categoryId = caString;
	}
	
	/**扩展后的点评库第一页*/
	public void setIniturl(int dis){
		if(this.page > dis)
			this.page = dis;
		this.initurl = "http://dianping.etao.com/inf/json-comment-list-" + categoryId + "-1-0-0-" + this.page + "-0.htm";
	}
	
	/**按照特定规则扩展点评页URL*/
	public void Expand(){
		urList.clear();
		String tmpString = new String();
		for(int i = 1; i <= page; i++){
			tmpString = "http://dianping.etao.com/inf/json-comment-list-" + categoryId + "-1-0-0-0-"
					+ i + ".htm";
			urList.add(tmpString);
		}
	}

	public static void main(String[] arsgStrings){
		EtaoUrlExpand urlExpand = new EtaoUrlExpand();
		urlExpand.categoryId = "000001";
		urlExpand.Expand();
		for(String e:urlExpand.urList){
			System.out.println(e);
		}
	}
}
