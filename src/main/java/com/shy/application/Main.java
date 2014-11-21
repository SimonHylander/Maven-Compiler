package com.shy.application;

import java.io.File;
import java.io.PrintStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.cli.MavenCli;
import org.controlsfx.dialog.Dialogs;

import com.shy.application.components.Components;
import com.shy.application.database.SQL;
import com.shy.application.pojo.Workspace;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextBuilder;

public class Main extends Application {
	PrintStream systemOut = System.out;
	ObservableList<String> items = FXCollections.observableArrayList();
	
	TextArea mavenOutput;
	
	SQL sql = new SQL();	
	MavenCli mavenCli = new MavenCli();
	Components components = new Components();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		setupStage(primaryStage);
		
		try {
			BorderPane root = new BorderPane();
			root.setTop(getMenu(primaryStage));
			root.setCenter(getPane(primaryStage));
			
			Scene scene = new Scene(root, 500, 450);
			primaryStage.setTitle("Maven Compiler");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setupStage(Stage stage)  {
		stage.setMaxWidth(500);
		stage.setMaxHeight(500);
		stage.setResizable(false);
	}
	
	public Pane getPane(Stage stage) {
		Pane pane = new Pane();
		
		
		ListView<String> listView = components.getListView();	
		listView.setItems(items);
		
		ContextMenu contextMenu = components.getContextMenu(listView);
		listView.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.isSecondaryButtonDown()) {
					 contextMenu.show(pane, event.getScreenX(), event.getScreenY());
				}else if(event.isPrimaryButtonDown()) {
					contextMenu.hide();
				}
			}
		});
		pane.setOnMousePressed(mouseEvent -> {
			if(mouseEvent.isPrimaryButtonDown()) {
				contextMenu.hide();
			}
		});
		
		mavenOutput = TextAreaBuilder.create().prefWidth(425).prefHeight(150).wrapText(true).build();
		mavenOutput.setLayoutX(35);
		mavenOutput.setLayoutY(250);
		mavenOutput.setEditable(false);
		
		Maven maven = new Maven();
		
		Button compileBtn = new Button("Compile");
		compileBtn.setLayoutX(150);
		compileBtn.setLayoutY(205);
		compileBtn.setOnAction(compile -> {
			String project = listView.getSelectionModel().getSelectedItem();
			if(project!= null && project.length() > 0) {
				mavenOutput.setText(maven.compile(project));				
			}
		});
		
		Button ciBtn = new Button("Clean Install");
		ciBtn.setLayoutX(215);
		ciBtn.setLayoutY(205);
		ciBtn.setOnAction(cleanInstall -> {
			String project = listView.getSelectionModel().getSelectedItem();
			if(project!= null && project.length() > 0) {
				mavenOutput.setText(maven.cleanInstall(listView.getSelectionModel().getSelectedItem()));
			}
		});
		
		Button warBtn = new Button("War:War");
		warBtn.setLayoutX(300);
		warBtn.setLayoutY(205);
		warBtn.setOnAction(war -> {
			String project = listView.getSelectionModel().getSelectedItem();
			if(project!= null && project.length() > 0) {
				mavenOutput.setText(maven.generateWar(listView.getSelectionModel().getSelectedItem()));
			}
		});
		
		pane.getChildren().addAll(compileBtn,ciBtn, warBtn);
		pane.getChildren().add(listView);	
		pane.getChildren().add(mavenOutput);
		return pane;
	}
	
	public MenuBar getMenu(Stage stage) {
		MenuBar menuBar = new MenuBar();;
		Menu menu = new Menu("Menu");
		Menu workspaceMenu = components.getWorkspaceMenu(items);
		
		MenuItem resetItem = new MenuItem("Reset");
		resetItem.setOnAction(action -> {
			sql.resetTable("workspace");
			sql.resetTable("project");
			workspaceMenu.getItems().remove(1,workspaceMenu.getItems().size());
			System.out.println("tables has been reset");
		});
		
		menu.getItems().addAll(workspaceMenu,resetItem, new SeparatorMenuItem(), new MenuItem("Close"));
		menuBar.getMenus().add(menu);
		return menuBar;
	}	
}