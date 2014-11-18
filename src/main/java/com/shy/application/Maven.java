package com.shy.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.PrintStream;

import org.apache.maven.cli.MavenCli;

import com.shy.application.database.SQL;

public class Maven {
	PrintStream systemOut = System.out;
	String filename = "maven/maven_output.txt";
	
	public String compile(String project) {
		SQL sql = new SQL();
		MavenCli maven = new MavenCli();
		String buildStatus = "";
		
		try {
			AppUtil.setOut(new FileOutputStream(filename));	
			String projectPath = sql.getProjectPath(project);
			maven.doMain(new String[]{"compile"}, projectPath,System.out, System.out);
			
			if(getBuildStatus()) {
				buildStatus = "Build Success on project: "+project;
			}else {
				buildStatus = "Build Failure during mvn compile on project: "+project;
			}
			AppUtil.setOut(systemOut);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(buildStatus);
		return buildStatus;
	}
	
	public String cleanInstall(String project) {
		SQL sql = new SQL();
		MavenCli maven = new MavenCli();
		String buildStatus = "";
		
		try {
			AppUtil.setOut(new FileOutputStream(filename));	
			String projectPath = sql.getProjectPath(project);
			maven.doMain(new String[]{"clean", "install"}, projectPath,System.out, System.out);
			
			if(getBuildStatus()) {
				buildStatus = "Build Success on project: "+project;
			}else {
				buildStatus = "Build Failure during mvn clean install on project: "+project;
			}
			AppUtil.setOut(systemOut);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(buildStatus);
		return buildStatus;
	}
	
	public String generateWar(String project) {
		SQL sql = new SQL();
		MavenCli maven = new MavenCli();
		String buildStatus = "";
		
		try {
			AppUtil.setOut(new FileOutputStream(filename));	
			String projectPath = sql.getProjectPath(project);
			maven.doMain(new String[]{"war:war"}, projectPath,System.out, System.out);
			
			if(getBuildStatus()) {
				buildStatus = "Build Success on project: "+project;
			}else {
				buildStatus = "Build Failure during mvn clean install on project: "+project;
			}
			AppUtil.setOut(systemOut);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(buildStatus);
		return buildStatus;
	}
	
	public String eclipseProject(String project)  {
		SQL sql = new SQL();
		MavenCli maven = new MavenCli();
		String buildStatus = "";
		
		try {
			AppUtil.setOut(new FileOutputStream(filename));	
			String projectPath = sql.getProjectPath(project);
			maven.doMain(new String[]{"eclipse:eclipse"}, projectPath,System.out, System.out);
			
			if(getBuildStatus()) {
				buildStatus = "Build Success on project: "+project;
			}else {
				buildStatus = "Build Failure during mvn clean install on project: "+project;
			}
			AppUtil.setOut(systemOut);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(buildStatus);
		return buildStatus;
	}
	
	public boolean getBuildStatus() {
		BufferedReader input = null;
		File file = new File(filename);
		boolean buildStatus = false;
		
		try {
			input = new BufferedReader(new FileReader(file));
			String line = null;
			int lineCounter = 0;
			
			while((line = input.readLine())!= null) {
				if(line.contains("BUILD SUCCESS")) {
					buildStatus = true;
				}else if(line.contains("BUILD FAILURE")) {
					buildStatus = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buildStatus;
	}
	public static void main(String[]args) {

	}
}