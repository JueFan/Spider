package org.juefan.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.juefan.spider.basic.FileIO;


public class Etao {
	
	public int id = 0;
	public String name = new String();
	public int dis = 0;
	public Set<String> words;
	public double score;
	public String matchString;
	public static Map<String, Integer> word = new HashMap<String, Integer>();
	
	public Etao(String string){
		String[] w = string.split("\t");
		this.id = Integer.parseInt(w[0]);
		this.name = w[1];
		this.dis = Integer.parseInt(w[2]);
		setwords();
	}
	
	public void setwords(){
		this.words = new HashSet<String>();
		String[] w = this.name.split(" |/|\\(|\\)");
		for(int i = 0; i < w.length; i++)
			this.words.add(w[i]);
		for(String sw: this.words){
			if(word.containsKey(sw)){
				word.put(sw, word.get(sw) + 1);
			}else {
				word.put(sw, 1);
			}
		}
	}
	
	public void set(final List<Pconline> pconlines){
		this.matchString = new String();
		this.score = 0;
		for(Pconline p: pconlines){
			if(this.score < find(p.name)){
				this.matchString = p.name;
				this.score = find(p.name);
			}
		}
		if(this.score >= 0.25 && this.score <= 0.4)
		System.out.println(this.name + "\t\t" + this.matchString + "\t\t" + this.score);
	}
	
	public double find(String pString){
		double f = 0;
		for(String s: words){
			if (pString.indexOf(s) != -1) {
				f = f + (double)1/word.get(s);
			}
		}
		return f;
	}
	
	public static void main(String[] args) {
		FileIO file1 = new FileIO();
		file1.SetfileName("etao.cellphone.productids.txt");
		file1.FileRead();
		List<String> tmpList = new ArrayList<String>();
		tmpList = file1.cloneList();
		List<Etao> etaos = new ArrayList<Etao>();
		List<Pconline> pconlines = new ArrayList<Pconline>();
		for(String s: tmpList)
			etaos.add(new Etao(s.toLowerCase()));
		FileIO file2 = new FileIO();
		file2.SetfileName("pconline.cellphone.productids.txt");
		file2.FileRead();
		tmpList.clear();
		tmpList = file2.cloneList();
		for(String s: tmpList)
			pconlines.add(new Pconline(s.toLowerCase()));
		for(int i = 0; i < 1000; i++){
			etaos.get(i).set(pconlines);
		}
	}
	
}
