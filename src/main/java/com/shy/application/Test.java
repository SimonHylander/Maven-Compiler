package com.shy.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Test extends Application {
	/*MenuBar menuBar;
	Menu menu;
	Menu fileMenu;
	Menu editMenu;
	Menu workspacesMenu;
	Menu preferencesMenu;
	MenuItem resetItem;
	MenuItem addWorkspaceItem;
	MenuItem existingWorkspace;*/
	/*CheckMenuItem preferenceCheck;
	ListView<String> listView;
	ObservableList<String> items;
	TextArea consoleOutput;*/
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			root.setTop(getMenu());
			root.setCenter(getPane());
			
			Scene scene = new Scene(root, 500, 450);
			primaryStage.setTitle("Maven Compiler");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public MenuBar getMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		menuBar.getMenus().addAll(menu, fileMenu, editMenu);
		return menuBar;
	}
	
	public StackPane getPane() {
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER);
		
		CheckMenuItem preferenceCheck;
		ListView<String> listView;
		ObservableList<String> items;
		TextArea consoleOutput;
		
		listView = new ListView<>();
		items = FXCollections.observableArrayList();
		listView.setItems(items);
		
		listView.setLayoutX(150);
		listView.setLayoutY(50);
		listView.setPrefWidth(210);
		listView.setPrefHeight(150);
		
		consoleOutput = TextAreaBuilder.create().prefWidth(425).prefHeight(150).wrapText(true).build();
//		consoleOutput = new TextArea();
		consoleOutput.setLayoutX(35);
		consoleOutput.setLayoutY(250);
		consoleOutput.setEditable(false);
		
		stackPane.getChildren().add(listView);	
		stackPane.getChildren().add(consoleOutput);
		
		return stackPane;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
