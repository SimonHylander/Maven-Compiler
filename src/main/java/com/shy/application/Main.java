package com.shy.application;
	

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.maven.cli.MavenCli;
import org.controlsfx.dialog.Dialogs;

import com.shy.application.components.Components;
import com.shy.application.database.SQL;
import com.shy.application.pojo.Workspace;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextBuilder;

public class Main extends Application {
	PrintStream systemOut = System.out;
	
	MenuBar menuBar;
	Menu menu;
	Menu gitMenu;
	Menu mavenMenu;
	Menu workspacesMenu;
	MenuItem resetItem;
	MenuItem addWorkspaceItem;
	MenuItem existingWorkspace;
	ListView<String> listView;
	ObservableList<String> items;
	
	
	TextArea mavenOutput;
	
	Pane pane;
	SQL sql = new SQL();	
	MavenCli mavenCli = new MavenCli();
	
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
		pane = new Pane();
		
		listView = new ListView<>();
		items = FXCollections.observableArrayList();
		listView.setItems(items);
		
		listView.setLayoutX(150);
		listView.setLayoutY(50);
		listView.setPrefWidth(210);
		listView.setPrefHeight(150);
		
		Components compontents = new Components();
		
		ContextMenu contextMenu = compontents.getContextMenu();
		listView.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.isSecondaryButtonDown()) {
					 contextMenu.show(pane, event.getScreenX(), event.getScreenY());
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
			mavenOutput.setText(maven.compile(listView.getSelectionModel().getSelectedItem()));
		});
		
		Button ciBtn = new Button("Clean Install");
		ciBtn.setLayoutX(215);
		ciBtn.setLayoutY(205);
		ciBtn.setOnAction(cleanInstall -> {
			mavenOutput.setText(maven.cleanInstall(listView.getSelectionModel().getSelectedItem()));
		});
		
		Button warBtn = new Button("War:War");
		warBtn.setLayoutX(300);
		warBtn.setLayoutY(205);
		warBtn.setOnAction(war -> {
			mavenOutput.setText(maven.generateWar(listView.getSelectionModel().getSelectedItem()));
		});
		
		pane.getChildren().addAll(compileBtn,ciBtn, warBtn);
		pane.getChildren().add(listView);	
		pane.getChildren().add(mavenOutput);
		
		return pane;
	}
	
	public MenuBar getMenu(Stage stage) {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		gitMenu = new Menu("Git");
		mavenMenu = new Menu("Maven");
		workspacesMenu = new Menu("Workspaces");
		getExistingWorkspaces(stage);
		
		resetItem = new MenuItem("Reset");
		resetItem.setOnAction(e -> {
			sql.resetTable("workspace");
			sql.resetTable("project");
			System.out.println("tables has been reset!");
			getExistingWorkspaces(stage);
		});
		MenuItem closeItem = new MenuItem("Close");
		
		menu.getItems().addAll(workspacesMenu,resetItem, new SeparatorMenuItem(), closeItem);
		menuBar.getMenus().addAll(menu, gitMenu, mavenMenu);
		return menuBar;
	}
	
	
	
	private void getAddWorkspaceButton(Stage stage) {
		addWorkspaceItem = new MenuItem("Add Workspace");
		addWorkspaceItem.setOnAction(action -> {
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			
			File workspaceDir = dirChooser.showDialog(null);
			if(workspaceDir != null) {
				getExistingWorkspaces(stage);
				
				if(sql.checkDupWorkspace().contains(workspaceDir.getPath())) {
					System.out.println("Workspace already exists");
					Dialogs.create()
						      .title("Duplicate Workspace")
						      .message( "Workspace already exists")
						      .showWarning();
				}else {
					sql.insertWorkspace(workspaceDir.getPath());
					List<File> list = getProjects(workspaceDir.getPath());
					list.forEach(project -> {
						sql.insertProject(project.getPath());
					});
					getExistingWorkspaces(stage);
					getWorkspaceProjects(workspaceDir.getPath());
				}
			}
		});
		workspacesMenu.getItems().add(addWorkspaceItem);
	}
	
	private void getExistingWorkspaces(Stage stage) {
		workspacesMenu.getItems().clear();
		getAddWorkspaceButton(stage);
		
		SQL sql = new SQL();
		List<Workspace> list = sql.getWorkspaces();
		list.forEach(workspace -> {
			existingWorkspace = new MenuItem(workspace.getPath());
			existingWorkspace.setOnAction(existing -> {
				getWorkspaceProjects(workspace.getPath());
			});
			workspacesMenu.getItems().add(existingWorkspace);
		});
	}
	
	private void getWorkspaceProjects(String path) {
		items.clear();
		List<File> projects = getProjects(path);
		projects.forEach(proj -> {
			items.add(proj.getName());
		});
	}
	
	private List<File> getProjects(String path) {
		List<File> list = new ArrayList<>();
		File directory = new File(path);
		File[] fileList = directory.listFiles();
		for(int i=0;i<fileList.length;i++) {
			if(fileList[i].isDirectory()) {
				for(File file : fileList[i].listFiles()) {
					if(file.toString().contains("pom.xml")) {
						list.add(fileList[i]);
					}
				}
			}
		}
		return list;
	}
}