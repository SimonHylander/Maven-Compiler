package com.shy.application.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Wamp {
	public static void main(String[]rgs) {
		Wamp w = new Wamp();
		w.startWamp();
	}
	
	
	public void startWamp() {
		String command = "";
		
		String os = System.getProperty("os.name");
		
		/*try {
			Process process = Runtime.getRuntime().exec("start /wamp/wampmanager.exe");
			process.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String str = "";
			while((str = br.readLine())!= null) {
				System.out.println(str);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}*/
	/*	
		try {
			Process process = new ProcessBuilder("C:\\Users\\b21019\\workspaces\\workspace\\Maven-Compiler\\wampwampmanager.exe","","").start();
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			BufferedReader br = new BufferedReader(input);
			String str = "";
			while((str = br.readLine()) != null) {
				System.out.println(str);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		
	}
}
