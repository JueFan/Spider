package org.juefan.spider.product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;

public class VisitProduct extends GetCode{

	/**产品名称*/
	private String categoryString;
	/**用户评论总数*/
	private int discuss;

	public String getCategory(){
		return this.categoryString;
	}

	public int getDiscuss(){
		return this.discuss;
	}

	public void setCategory(String categoryString){
		this.categoryString = categoryString;
	}
	public void setDiscuss(int discuss){
		this.discuss = discuss;
	}

	public VisitProduct(){
		super();
		categoryString = new String();
		discuss = 0;
	}

	/**匹配网页源码的产品信息*/
	public void ReturnCategory(){
		String tmpString = new String();
		String regex = "<title>.*?</title>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(getCodeString());
		while(matcher.find()){
			tmpString = matcher.group();
		}
		setCategory(tmpString.replaceAll("<title>【|】.*?</title>", ""));
	}

	/**匹配网页源码产品的评论数*/
	public void ReturnDiscuss(){
		String tmpString = new String("0");
		String regex = "<em>（共\\d{1,10}条）</em></a></i>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(getCodeString());
		while(matcher.find()){
			tmpString = matcher.group();
		}
		setDiscuss(Integer.parseInt(tmpString.replaceAll("<em>（共|条）</em></a></i>", "")));
	}

	/**匹配产品评论内容的页数*/
	public String ReturnPage(){
		Boolean res = false;
		String adString = new String();
		String tmpString = new String();
		String regex = "&nbsp;1/\\d{1,4}";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(getCodeString());
		while(matcher.find()){
			tmpString = matcher.group();
			res = true;
		}
		if(res == false)
			return "0";
		adString = tmpString.replaceAll("&nbsp;1/", "");
		return adString;
	}

	/**匹配用户评论内容块*/
	public List<Discuss> ReturnDiscussion(){
		Boolean trac = false;
		List<Discuss> list = new ArrayList<Discuss>();
		Discuss tmpDiscuss = new Discuss();
		String tmpString = new String();
		String regex = "<div class=\"idDate\">([\\s\\S]+?)<i class=\"idLink\">";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(getCodeString());
		while(matcher.find()){
			tmpString = matcher.group();
			trac = true;
			tmpDiscuss.time = ReturnTime(tmpString);
			tmpDiscuss.advantage = ReturnAdvantage(tmpString);
			tmpDiscuss.disadvantage = ReturnDisadvantage(tmpString);
			tmpDiscuss.summary = ReturnSummary(tmpString);
			list.add(tmpDiscuss);
			tmpDiscuss = new Discuss();
		}
		if(trac){
			System.out.println("success...");
		}else {
			System.out.println("fail...");
		}
		return list;
	}

	/**匹配用户评论的时间*/
	public String ReturnTime(String context){
		String adString = new String();
		String tmpString = new String();
		String regex = "<div class=\"idDate\">.*?</div></i>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<div class=\"idDate\">|</div></i>", "");
		return adString;
	}

	/**匹配用户评论的优点内容*/
	public String ReturnAdvantage(String context){
		String adString = new String();
		String tmpString = new String();
		String regex = "<i class=\"idSum idSum1\"><em>优点：</em>.*?</i>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<i class=\"idSum idSum1\"><em>优点：</em>|</i>", "");
		return adString;
	}

	/**匹配用户评论的缺点内容*/
	public String ReturnDisadvantage(String context){
		String adString = new String();
		String tmpString = new String();
		String regex = "<i class=\"idSum idSum2\"><em>缺点：</em>.*?</i>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<i class=\"idSum idSum2\"><em>缺点：</em>|</i>", "");
		return adString;
	}

	/**匹配用户评论的总结内容*/
	public String ReturnSummary(String context){
		String adString = new String();
		String tmpString = new String();
		String regex = "<i class=\"idSum idSum3\"><em>总结：</em>.*?</i>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<i class=\"idSum idSum3\"><em>总结：</em>|</i>", "");
		return adString;
	}

	public static void main(String[] args){
		VisitProduct  getCode = new VisitProduct();
		UrlExpand urlExpand = new UrlExpand();
		List<Discuss> list = new ArrayList<Discuss>();
		Files files = new Files();
		files.SetfileName("file01.txt");
		files.FileRead();
		List<String> tmpList = new ArrayList<String>();
		tmpList = files.cloneList();
		int trac = 0;
		for(String e:tmpList){
			list.clear();
			urlExpand.urList.clear();
			urlExpand.setCategory(e.split("\t")[0]);
			urlExpand.setIniturl();
			GetCode.setUrl(urlExpand.initurl);
			GetCode.Visit();
			/*int pages = Integer.parseInt(getCode.ReturnPage());
			System.out.println("访问链接 " + urlExpand.initurl);
			System.out.println(e.split("\t")[1] + "评论内容有 " + pages + " 页");
			System.out.println("目前共收集 " + sum + "页");*/
			urlExpand.pages = Integer.parseInt(getCode.ReturnPage());
			urlExpand.Expand();
			for(int i = 0; i < urlExpand.pages; i++){
				System.out.print("The " + (++trac) + "time\t\t");
				String tmpRul = new String();
				tmpRul = urlExpand.urList.get(i);
				GetCode.setUrl(tmpRul);
				GetCode.Visit();
				list.addAll(getCode.ReturnDiscussion());
			}
			files.FileOutput(list, "F:\\Users\\pc\\workspace\\Spider\\output\\"
					+ e.split("\t")[0] + "_" + e.split("\t")[1]);
		}
	}
}

/**
 * 文件操作类
 * 继承父类后增加针对Discuss类的文件写入功能
 * @author juefan
 *
 */
class Files extends FileIO{
	public Files(){
		super();
	}

	/**
	 * 将评论内容写进文件中
	 * @param discuss 评论内容结构化数据
	 * @param fileName 存放文件名
	 */
	public void FileOutput(List<Discuss> discuss, String fileName){
		for(int i = 0; i < discuss.size(); i++){
			FileWrite(fileName + ".txt", "发表时间: " + discuss.get(i).time + "\n");
			FileWrite(fileName + ".txt", "优点: " + discuss.get(i).advantage + "\n");
			FileWrite(fileName + ".txt", "缺点: " + discuss.get(i).disadvantage + "\n");
			FileWrite(fileName + ".txt", "总结: " + discuss.get(i).summary + "\n");
		}
	}
}


/**
 * 用户评论
 * 包含四个成员变量
 * 分别记录评论时间以及产品评论中的优点、缺点与总结
 * @author juefan
 */
class Discuss{
	public String time;
	public String advantage;
	public String disadvantage;
	public String summary;
	public Discuss(){
		time = new String();
		advantage = new String();
		disadvantage = new String();
		summary = new String();
	}
}
