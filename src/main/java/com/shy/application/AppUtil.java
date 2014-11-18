package com.shy.application;

import java.io.OutputStream;
import java.io.PrintStream;

public class AppUtil {
	public static void setOut(OutputStream output)  {
		try {
			PrintStream ps = new PrintStream(output);
			System.setOut(ps);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
