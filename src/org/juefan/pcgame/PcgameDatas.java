package org.juefan.pcgame;

import java.util.ArrayList;
import java.util.List;

import org.juefan.spider.basic.FileIO;
import org.juefan.spider.basic.GetCode;

public class PcgameDatas {
	public String nameString = new String();
	public String data = new String();
	public int activity = 0;
	public int rank = 0;
	
	public PcgameDatas(String string){
		String[] strings = string.split(",\"");
		this.nameString = strings[0].split("\":\"")[1].replace("\"", "");
		this.data = strings[4].split(":")[1].replace("\"", "");
		this.activity = Integer.parseInt(strings[5].split(":")[1].replace("}", "").replace("\n", ""));
		this.rank = Integer.parseInt(strings[3].split(":")[1].replace("\"", ""));	
	}

	public static void main(String[] args) {
		List<PcgameDatas> gList = new ArrayList<PcgameDatas>();
		FileIO fileIO = new FileIO();
		fileIO.SetfileName("urlfile.txt");
		fileIO.FileRead();
		List<String> urlList = fileIO.cloneList();
		GetCode getCode = new GetCode();		
		for(String s:urlList){
			GetCode.setUrl(s);
			GetCode.Visit();
			String[] coStrings = getCode.getCodeString().replace("]", "").split("},");
			for(String c:coStrings){
				gList.add(new PcgameDatas(c));
			}
		}
		for(PcgameDatas p: gList){
			fileIO.FileWrite("pcgameresult.txt", p.nameString
					+"\t" + p.data
					+"\t" + p.rank
					+"\t" + p.activity + "\n");
		}
	}
}
