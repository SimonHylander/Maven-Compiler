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

import com.shy.application.database.SQL;
import com.shy.application.pojo.MavenArguments;
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
	PrintStream sysoutPrintStream = System.out;
	File mavenOutput = new File("maven/maven_output.txt");
	
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
	ContextMenu contextMenu;
	
	TextArea consoleOutput;
	
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

		contextMenu = getContextMenu();
		
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
        
		consoleOutput = TextAreaBuilder.create().prefWidth(425).prefHeight(150).wrapText(true).build();
		consoleOutput.setLayoutX(35);
		consoleOutput.setLayoutY(250);
		consoleOutput.setEditable(false);
		
		Maven maven = new Maven();
		
		Button compileBtn = new Button("Compile");
		compileBtn.setLayoutX(150);
		compileBtn.setLayoutY(205);
		compileBtn.setOnAction(compile -> {
			MavenArguments mvnArgs = new MavenArguments();
			mvnArgs.setAction("compile");
			mvnArgs.setProject(listView.getSelectionModel().getSelectedItem());
			consoleOutput.setText(maven.getAction(mvnArgs));
			
		});
		
		Button ciBtn = new Button("Clean Install");
		ciBtn.setLayoutX(215);
		ciBtn.setLayoutY(205);
		ciBtn.setOnAction(cleanInstall -> {
			MavenArguments mvnArgs = new MavenArguments();
			mvnArgs.setAction("clean install");
			mvnArgs.setProject(listView.getSelectionModel().getSelectedItem());
			consoleOutput.setText(maven.getAction(mvnArgs));
		});
		
		Button warBtn = new Button("War:War");
		warBtn.setLayoutX(300);
		warBtn.setLayoutY(205);
		warBtn.setOnAction(war -> {
			MavenArguments mvnArgs = new MavenArguments();
			mvnArgs.setAction("war:war");
			mvnArgs.setProject(listView.getSelectionModel().getSelectedItem());
			consoleOutput.setText(maven.getAction(mvnArgs));
		/*	
			try {
				AppUtil.setOut(new FileOutputStream(mavenOutput));
				
				String selectedProject = listView.getSelectionModel().getSelectedItem();
				String projectPath = sql.getProjectPath(selectedProject);			
				mavenCli.doMain(new String[]{"war:war"}, projectPath,System.out, System.out);
				
				AppUtil.setOut(sysoutPrintStream);
				
				Path path = Paths.get(mavenOutput.getPath());
				Stream<String> mvnOutput = Files.lines(path);
				String buildSuccess = mvnOutput.filter(a -> a.contains("BUILD SUCCESS")).findFirst().get();
				String buildStatus = "";
				if(buildSuccess.equals("[INFO] BUILD SUCCESS")) {
					buildStatus = "Build Success on project: "+selectedProject;
				}else {
					buildStatus = "Build Failure on project: "+selectedProject;
				}
				System.out.println(buildStatus);
				consoleOutput.setText(buildStatus);
				mvnOutput.close();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		});
		
		Button eclipseBtn = new Button("Eclipse:Eclipse");
		
		eclipseBtn.setLayoutX(36);
		eclipseBtn.setLayoutY(205);
		eclipseBtn.setOnAction(war -> {
			MavenArguments mvnArgs = new MavenArguments();
			mvnArgs.setAction("eclipse:eclipse");
			mvnArgs.setProject(listView.getSelectionModel().getSelectedItem());
			consoleOutput.setText(maven.getAction(mvnArgs));
		});
		
		/*pane.getChildren().add(compileBtn);	
		pane.getChildren().add(ciBtn);	
		pane.getChildren().add(warBtn);	*/
		pane.getChildren().addAll(compileBtn,ciBtn, warBtn,eclipseBtn);
		pane.getChildren().add(listView);	
		pane.getChildren().add(consoleOutput);
		
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
	
	public ContextMenu getContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		
		Menu gitMenu = new Menu("Git");
		MenuItem gitCommit = new MenuItem("Commit");
		MenuItem gitPush = new MenuItem("Push");
		MenuItem gitPull = new MenuItem("Pull");
		
		
		Menu mavenMenu = new Menu("Maven");
		MenuItem mavenCompile = new MenuItem("Compile");
		MenuItem mavenClean = new MenuItem("Clean Install");
		MenuItem mavenWar = new MenuItem("War");
		
		/*mavenCompile.setOnAction(action -> {
			getMavenActions("compile");
		});
		
		mavenClean.setOnAction(action -> {
			getMavenActions("clean");
		});
		
		mavenWar.setOnAction(action -> {
			getMavenActions("war:war");
		});*/
		
		
		gitMenu.getItems().addAll(gitCommit,gitPush,gitPull);
		mavenMenu.getItems().addAll(mavenCompile,mavenClean,mavenWar);
		
		contextMenu.getItems().addAll(gitMenu,mavenMenu);
		
		return contextMenu;
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