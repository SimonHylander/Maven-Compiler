package com.shy.application;


import java.io.PrintStream;

import javax.swing.SwingUtilities;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ConsoleOutput {
	private TextArea txtArea;
	
	public Pane getOutputPane(Stage stage) {
        Pane pane = new Pane();
        
        @SuppressWarnings("deprecation")
		TextArea txt = TextAreaBuilder.create().prefWidth(850).prefHeight(300).wrapText(true).build();
		Console console = new Console(txt);
		PrintStream ps = new PrintStream(console, true);
		ps.flush();
		System.setOut(ps);
		System.setErr(ps);
		
		Scene scene = new Scene(txt);
		stage.setScene(scene);
		stage.show();
		return pane;
	}
}
