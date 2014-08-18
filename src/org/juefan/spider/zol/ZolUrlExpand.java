package org.juefan.spider.zol;

import java.util.ArrayList;
import java.util.List;

public class ZolUrlExpand{

	public Product product;
	public String initurl;
	public List<String> urList;
	public final String TEXT1;
	public final String TEXT2;
	public final String TEXT3;
	public final String TEXT4;
	public int pages;
	
	public ZolUrlExpand(){
		TEXT1 = "http://detail.zol.com.cn/";
		TEXT2 = "/";
		TEXT3 = "/review_0_2_0_";
		TEXT4 = ".shtml#tagNav";
		initurl = new String();
		product = new Product();
		urList = new ArrayList<String>();
		pages = 0;
	}
	
	/**设置产品ID*/
	public void setCategory(Product product){
		this.product.id = product.id;
		this.product.name = product.name;
		this.product.bid = product.bid;
	}
	/**扩展后的点评库第一页*/
	public void setIniturl(){
		this.initurl = TEXT1 + product.bid + TEXT2 + product.id + TEXT3 + "1" + TEXT4;
	}
	/**按照特定规则扩展点评页URL*/
	public void Expand(){
		String tmpString = new String();
		for(int i = 1; i <= pages; i++){
			tmpString = TEXT1 + product.bid + TEXT2 + product.id + TEXT3 + Integer.toString(i) + TEXT4;
			urList.add(tmpString);
		}
	}
	
	public static void main(String[] arsgStrings){
		ZolUrlExpand urlExpand = new ZolUrlExpand();
		Product product = new Product();
		product.id = "1110";
		product.bid = "22";
		urlExpand.setCategory(product);
		urlExpand.pages = 5;
		urlExpand.Expand();
		for(String e:urlExpand.urList){
			System.out.println(e);
		}
	}

}
