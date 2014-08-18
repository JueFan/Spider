package org.juefan.spider.etao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;
import org.juefan.spider.product.VisitProduct;

public class EtaoVisitProduct extends VisitProduct{

	public String id = "";
	public String title = "";
	public int score = 0;
	public String ctime = "";
	public int source = 0;
	public int usefull_count = 0;
	public String good = "";
	public String bad = "";
	public String summary = "";
	public String content = "";
	public int hasgood = 0;
	public int hasbad = 0;
	public int hassummary = 0;
	public int hascontent = 0;
	public String author_id = "";
	public String author_nick = "";
	public String author_img = "";
	public String url = "";
	public String webname = "";

	/**
	 * 从个人评论内容中提取信息
	 * @param context 网页源码抽取的个人评论内容
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void setAllValue(String context) throws Exception{
		this.id = setValue("[^_]id", context);
		this.title = setValue("title", context);
		this.ctime = setValue("ctime", context);
		this.good = setValue("[^s]good", context);
		this.bad = setValue("[^s]bad", context);
		this.summary = setValue("[^s]summary", context);
		this.content = setValue("[^s]content", context);
		this.author_id = setValue("author_id", context);
		this.author_nick = setValue("author_nick", context);
		this.author_img = setValue("author_img", context);
		this.url = setValue("url", context);
		this.webname = setValue("webname", context);
		this.score = Integer.parseInt(setValue("score", context).trim());
		this.source = Integer.parseInt(setValue("source", context).trim());
		this.usefull_count = Integer.parseInt(setValue("usefull_count", context).trim());
		this.hasgood = Integer.parseInt(setValue("hasgood", context).trim());
		this.hasbad = Integer.parseInt(setValue("hasbad", context).trim());
		this.hassummary = Integer.parseInt(setValue("hassummary", context).trim());
		this.hascontent = Integer.parseInt(setValue("hascontent", context).trim());
	}


	/**
	 * 从个人评论中匹配关键字段的值
	 * @param key 字段名
	 * @param context 网页源码抽取的个人评论内容
	 * @return 字段名对应的内容
	 */
	public static String setValue(String key, String context) throws Exception{
		String adString = new String();
		String tmpString = new String();
		boolean tra = false;
		String regex = key + ":\".*?\",";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(context);
		while(matcher.find()){
			tmpString = matcher.group();
			tra = true;
		}
		if(!tra)
			return "0";
		adString = tmpString.replaceAll(key + ":\"|\",|\t", "");
		return adString;
	}


	public List<EtaoVisitProduct> test(String string){
		string = string.substring(200);
		List<EtaoVisitProduct> list = new ArrayList<EtaoVisitProduct>();
		EtaoVisitProduct tmpDiscuss = new EtaoVisitProduct();
		String tmpString = new String();
		String regex = "\\{.*?\\}";
		Pattern p = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher matcher = p.matcher(string);
		while(matcher.find()){
			tmpString = matcher.group();
			try {
				tmpDiscuss.setAllValue(tmpString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(tmpDiscuss);
			tmpDiscuss = new EtaoVisitProduct();
		}
		return list;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args){
		List<EtaoVisitProduct> list = new ArrayList<EtaoVisitProduct>();
		EtaoVisitProduct visit = new EtaoVisitProduct();
		EtaoUrlExpand urlExpand = new EtaoUrlExpand();
		Files files = new Files();
		files.SetfileName("etao.notebook.productids.txt");
		files.FileRead();
		List<String> tmpList = new ArrayList<String>();
		List<Product> pList = new ArrayList<Product>();
		tmpList = files.cloneList();
		int tra = 0;
		for(int i = 0; i < tmpList.size(); i++){
			System.out.println(i+1);
			urlExpand.categoryId = tmpList.get(i).split("\t")[0];
			urlExpand.categoryName = tmpList.get(i).split("\t")[1];
			urlExpand.page = Integer.parseInt(tmpList.get(i).split("\t")[2]);
			urlExpand.setIniturl(5000);
			tra = tra + urlExpand.page;
			String e = urlExpand.initurl;
			System.out.println(e);
			GetCode.setUrl(e);
			GetCode.Visit();
			try {
				list.addAll(visit.test(visit.getCodeString()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			files.FileOutput(list, "F:\\eclipse-SDK-4.2.2-win32\\User\\workspace\\Spider\\output\\etao\\notebook\\"
					+ urlExpand.categoryId);
			list.clear();
		} 
		System.out.println(tra);
	}
}

/**
 * 文件操作类
 * 继承父类后增加针对Discuss类的文件写入功能
 * @author juefan
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
	public void FileOutput(List<EtaoVisitProduct> discuss,  String fileName){
		for(int i = 1; i < discuss.size(); i++){
			FileWrite(fileName + ".txt",  discuss.get(i).id + "\t" + discuss.get(i).title + "\t"
					+ discuss.get(i).score + "\t" + discuss.get(i).ctime + "\t"
					+ discuss.get(i).source + "\t" + discuss.get(i).usefull_count + "\t"
					+ discuss.get(i).good + "\t" + discuss.get(i).bad + "\t"
					+ discuss.get(i).summary + "\t" /*+ discuss.get(i).content + "\t"*/
					+ discuss.get(i).hasgood + "\t" + discuss.get(i).hasbad + "\t"
					+ discuss.get(i).hassummary + "\t" + discuss.get(i).hascontent + "\t"
					+ discuss.get(i).author_id + "\t" + discuss.get(i).author_nick + "\t"
					+ discuss.get(i).author_img + "\t" + discuss.get(i).url + "\t"
					+ discuss.get(i).webname + "\n");
		}
	}
}
