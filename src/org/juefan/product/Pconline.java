package org.juefan.product;

public class Pconline {
	
	public int id;
	public String name;
	public Pconline(String string){
		this.id = Integer.parseInt(string.split("\t")[0]);
		this.name = string.split("\t")[1].trim().replace(" ", "");
	}
}
