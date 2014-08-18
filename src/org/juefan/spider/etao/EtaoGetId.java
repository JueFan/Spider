package org.juefan.spider.etao;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;

public class EtaoGetId extends GetCode{

	public List<Product> list;

	public FileIO fileIO;

	public EtaoGetId(){
		list = new ArrayList<Product>();
		fileIO = new FileIO();	
	}

	/**产品ID入库*/
	public void AddList(){
		String tmpString = new String();
		String string = new String();
		String regex = "<a class=\"img-wrap LS_history\" .*?'itemImg'([\\s\\S]+?)共\\d{1,6}个点评</a>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(getCodeString());
		while(matcher.find()){
			tmpString = matcher.group();
			string = tmpString.trim().replaceAll("<a class=\"img-wrap LS_history\" .*?/item/|.html|itemTitle':'|itemImg'([\\s\\S]+?)position=\\d{1,6}\" >共|个点评</a>", "");
			System.out.println(string);
			Product tmProduct = new Product();
			tmProduct.id = string.split("','")[0];
			tmProduct.name = string.split("','")[1];
			tmProduct.dis = Integer.parseInt(string.split("','")[2]);
			list.add(tmProduct);
		}
		for(Product p1:list){
			try{
				FileWriter fileWriter = new FileWriter("etao.cellphone.productids.txt", true);
				fileWriter.write(p1.id + "\t" + p1.name + "\t" + p1.dis + "\n");
				fileWriter.flush();
			}catch (Exception e) {
			}
		}
		list.clear();
	}

	public static void main(String[] args) {
		EtaoGetId getId = new EtaoGetId();
		getId.fileIO.SetfileName("file02.txt");
		getId.fileIO.FileRead();
		List<String> tmpList = new ArrayList<String>();
		tmpList = getId.fileIO.cloneList();
		int tra = 0;
		for(String e:tmpList){
			System.out.println(++tra);
			GetCode.setUrl(e);
			GetCode.Visit();
			getId.AddList();		
		}
		
	}
}


class Product{
	String id;
	String name;
	int dis;
	public Product() {
		id = new String();
		name = new String();
		dis = 0;
	}
}