package org.juefan.pcauto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;

public class GetCarsInfo {

	/**
	 * 车系提取
	 * @param word 网页源码
	 * @return 去掉标记的车系
	 */
	public static String getCar(final String word){
		String cars = new String();
		String regex = "<a href=\"http://price.pcauto.com.cn/price/sg\\d{1,5}/\">.*?</a>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(word);
		while(matcher.find()){
			cars = matcher.group();//
		}
		return cars.replaceAll("<a href=\"http://price.pcauto.com.cn/price/sg\\d{1,5}/\">|</a>", "");
	}
	
	/**
	 * 类别提取
	 * @param word 网页源码
	 * @return 车系的类别
	 */
	public static String getCategory(final String word){
		String category = new String();
		String regex = "<span style=\"margin-left: 30px;\">级 别：</span><em title=\".*?\">";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(word);
		while(matcher.find()){
			category = matcher.group();//
		}
		return category.replaceAll("<span style=\"margin-left: 30px;\">级 别：</span><em title=\"|\">", "");
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args){
		StringBuilder builder = new StringBuilder();
		int Count = 0;
		try{
			for(int i = 0 ; i < 15000; i++){
				System.out.println("第 " + (i + 1) + " 个抓取车系...");
				String urlString = "http://price.pcauto.com.cn/price/sg" + Integer.toString(i) + "/";
				GetCode.setUrl(urlString);
				GetCode.Visit();
				if(GetCode.buffer.length() >= 1000){
					Count++;
					builder.append(getCar(GetCode.buffer.toString())).append("\t");
					builder.append(getCategory(GetCode.buffer.toString())).append("\n");
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("共有 " + Count + " 个车系");
		FileIO.FileWrite(System.getProperty("user.dir") + "\\output\\pcauto\\carsInfo.txt", builder.toString(), false);
	}
}
