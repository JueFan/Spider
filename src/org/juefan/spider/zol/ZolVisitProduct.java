package org.juefan.spider.zol;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;

public class ZolVisitProduct extends org.juefan.spider.product.VisitProduct{


	/**匹配产品评论内容的页数*/
	public String ReturnPage(){
		Boolean res = false;
		String adString = new String();
		String tmpString = new String();
		String regex = "<span class='pagenum'><b>\\d{1,2}</b>/\\d{1,3}</span>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(getCodeString());
		while(matcher.find()){
			tmpString = matcher.group();
			res = true;
		}
		if(res == false)
			return "1";
		adString = tmpString.replaceAll("<span class='pagenum'><b>\\d{1,2}</b>/|</span>", "");
		return adString;
	}

	/**匹配用户评论内容块*/
	public List<Discuss> ZolReturnDiscussion(){
		Boolean trac = false;
		List<Discuss> list = new ArrayList<Discuss>();
		Discuss tmpDiscuss = new Discuss();
		String tmpString = new String();
		String regex = "<div class=\"feed_box\">([\\s\\S]+?)<div class=\"comment_from\">";
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
		String regex = "<span class=\"date\">发表于：.*?</span>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<span class=\"date\">发表于：|</span>", "");
		return adString;
	}

	/**匹配用户评论的优点内容*/
	public String ReturnAdvantage(String context){
		String adString = new String();
		String tmpString = new String();
		String regex = "<dt class=\"good\">优点</dt>([\\s\\S]+?)<dd>.*?([\\s\\S]+?)</dd>.?";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<dt class=\"good\">优点</dt>([\\s\\S]+?)<dd>|</dd>|\n", "");
		return adString;
	}

	/**匹配用户评论的缺点内容*/
	public String ReturnDisadvantage(String context){
		String adString = new String();
		String tmpString = new String();
		String regex = "<dt class=\"bad\">缺点</dt>([\\s\\S]+?)<dd>.*?([\\s\\S]+?)</dd>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<dt class=\"bad\">缺点</dt>([\\s\\S]+?)<dd>|</dd>|\n", "");
		return adString;
	}

	/**匹配用户评论的总结内容*/
	public String ReturnSummary(String context){
		String adString = new String();
		String tmpString = new String();
		String regex = "<dt>总结</dt>([\\s\\S]+?)<dd>.*?([\\s\\S]+?)</dd>";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
		}
		adString = tmpString.replaceAll("<dt>总结</dt>([\\s\\S]+?)<dd>|</dd>|\n", "");
		return adString;
	}

	public static void main(String[] args){
		ZolVisitProduct  getCode = new ZolVisitProduct();
		ZolUrlExpand urlExpand = new ZolUrlExpand();
		List<Discuss> list = new ArrayList<Discuss>();

		Files files = new Files();
		files.SetfileName("zol.notebook.productid.txt");
		files.FileRead();
		List<String> tmpList = new ArrayList<String>();
		List<Product> pList = new ArrayList<Product>();
		tmpList = files.cloneList();
		int trac = 0;
		for(String e:tmpList){
			Product tmProduct = new Product();
			tmProduct.id = e.split("\t")[0];
			tmProduct.bid = e.split("\t")[2];
			tmProduct.name = e.split("\t")[1];
			pList.add(tmProduct);
		}
		for(Product e:pList){
			list.clear();
			urlExpand.urList.clear();
			urlExpand.setCategory(e);
			urlExpand.setIniturl();
			GetCode.setUrl(urlExpand.initurl);
			GetCode.Visit();
			int pages = Integer.parseInt(getCode.ReturnPage());
			System.out.println("访问链接 " + urlExpand.initurl);
			System.out.println(e.name + "评论内容有 " + pages + " 页");
			urlExpand.pages = Integer.parseInt(getCode.ReturnPage());
			urlExpand.Expand();
			for(int i = 0; i < urlExpand.pages; i++){
				System.out.print("The " + (++trac) + "time\t\t");
				String tmpRul = new String();
				tmpRul = urlExpand.urList.get(i);
				GetCode.setUrl(tmpRul);
				GetCode.Visit();
				list.addAll(getCode.ZolReturnDiscussion());
			}
			System.out.println(list.size());
			files.FileOutput(list, e.name, "F:\\Users\\pc\\workspace\\Spider\\output\\zol\\notebook\\"
					+ e.id);
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
	public void FileOutput(List<Discuss> discuss, String proString, String fileName){
		for(int i = 0; i < discuss.size(); i++){
			FileWrite(fileName + ".txt", "产品名称: " + proString + "\n");
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