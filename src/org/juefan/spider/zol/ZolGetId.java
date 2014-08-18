package org.juefan.spider.zol;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;

public class ZolGetId extends GetCode{

	
	public List<Product> list;
	
	public FileIO fileIO;
	
	public ZolGetId(){
		list = new ArrayList<Product>();
		fileIO = new FileIO();	
	}
	
	/**产品ID入库*/
	public void AddList(){
		String tmpString = new String();
		String string = new String();
		String regex = "data-rel='\\d{1,10},.*?,";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(getCodeString());
		while(matcher.find()){
			tmpString = matcher.group();
			string = tmpString.replaceAll("data-rel='", "");
			Product tmProduct = new Product();
			tmProduct.id = string.split(",")[0];
			tmProduct.name = string.split(",")[1];
			tmProduct.setBid();
			list.add(tmProduct);
		}	
	}
	
	public static void main(String[] args) {
		ZolGetId getId = new ZolGetId();
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
		for(Product p:getId.list){
			try{
				FileWriter fileWriter = new FileWriter("zol.notebook.productid.txt", true);
				fileWriter.write(p.id + "\t" + p.name + "\t" + p.bid + "\n");
				fileWriter.flush();
			}catch (Exception e) {
			}
		}
	}
}


class Product{
	String id;
	String name;
	String bid;
	public Product() {
		id = new String();
		name = new String();
		bid = new String();
	}
	public void setBid(){
		bid = Integer.toString(Integer.parseInt(id.substring(1, 3)) + 1);
	}
}
