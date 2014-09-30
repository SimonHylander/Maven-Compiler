package com.shy.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application {
	private BorderPane root;
	private StackPane centerPane;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			/*BorderPane root = new BorderPane();
			root.setTop(getMenu());
			root.setCenter(getPane());
			
			
			
			Scene scene = new Scene(root, 500, 450);
			primaryStage.setTitle("Maven Compiler");
			primaryStage.setScene(scene);
			primaryStage.show();*/
			
			root = new BorderPane();
		    root.setTop(getMenu());
		    centerPane = getCenterPane();
		    root.setCenter(centerPane);
		     
		    Scene scene = new Scene(root, 900, 500);
		    scene.getStylesheets().add("com/shy/application/application.css");
		    primaryStage.setTitle("BorderPane Example");
		    primaryStage.setScene(scene);
		    primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private StackPane getCenterPane()
	   {
	      StackPane stackPane = new StackPane();
	      stackPane.setAlignment(Pos.CENTER);
	      
	      Pane p = new Pane();
	      p.getStyleClass().add("pane");
	      
	      ListView<String> listView = new ListView<>();
	      listView.setPrefWidth(210);
	      listView.setPrefHeight(150);
	      
	      
	      
//	      p.getChildren().add(listView);
	      stackPane.getChildren().addAll(p);

	      return stackPane;
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
		
		
		/*ListView<String> listView = new ListView<>();
		ObservableList<String> items = FXCollections.observableArrayList();
		listView.setItems(items);
		
		listView.setLayoutX(150);
		listView.setLayoutY(50);
		listView.setPrefWidth(210);
		listView.setPrefHeight(150);
		
		stackPane.getChildren().add(listView);	*/
		
		return stackPane;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
