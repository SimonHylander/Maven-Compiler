package com.shy.application;

import java.io.IOException;
import java.io.OutputStream;

import javafx.scene.control.TextArea;

public class Console extends OutputStream {
	private TextArea txt;
	
	public Console(TextArea txt) {
		this.txt = txt;
	}
	
	@Override
	public void write(int i) throws IOException {
		txt.appendText(String.valueOf((char)i));
	}
	
}
