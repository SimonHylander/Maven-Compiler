package com.shy.application.components;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CommitWindow extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			BorderPane root = new BorderPane();
			root.setCenter(getPane());
			Scene scene = new Scene(root, 500, 450);
			Stage stage = new Stage();
			stage.setTitle("Commit Changes");
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getCommitWindow() {
	 // start(stage); method
	}
	
	public Pane getPane() {
		Pane pane = new Pane();
		getGit();
		
		ListView<String> listView = new ListView<>();
		listView.setLayoutX(150);
		listView.setLayoutY(50);
		listView.setPrefWidth(210);
		listView.setPrefHeight(300);
		
		pane.getChildren().add(listView);
		return pane;
	}
	
	public void getGit() {
		String s = null;
		try {
			// run the Unix "ps -ef" command
	        // using the Runtime exec method:
//	        Process p = Runtime.getRuntime().exec("ps -ef");
			
			Process p = Runtime.getRuntime().exec("git status");
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	        
	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	        while ((s = stdInput.readLine()) != null) {
	        	System.out.println(s);
	        }
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	        	System.out.println(s);
	        }
	        System.exit(0);
	     }catch (Exception e) {
	            System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
	        }
	}
	
}
