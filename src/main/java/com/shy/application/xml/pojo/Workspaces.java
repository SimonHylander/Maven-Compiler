package com.shy.application.xml.pojo;

public class Workspaces {
	String name;
	String path;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		
		return "name = "+name+" path = "+path;
	}
}