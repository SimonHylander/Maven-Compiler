package com.shy.application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.SwingUtilities;

import org.apache.maven.cli.MavenCli;
import org.controlsfx.dialog.Dialogs;

import com.shy.application.ConsoleOutput;
import com.shy.application.database.SQL;
import com.shy.application.database.SQLH2;
import com.shy.application.pojo.ConsoleWindow;
import com.shy.application.pojo.PreferenceTags;
import com.shy.application.pojo.Workspace;
import com.shy.application.xml.ReadXML;
import com.shy.application.xml.WriteXML;
import com.shy.application.xml.pojo.Workspaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
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
import javafx.scene.text.TextBuilder;

public class Main extends Application {
	
	PrintStream console = System.out;
//	PrintStream console = 
	
	MenuBar menuBar;
	Menu menu;
	Menu fileMenu;
	Menu editMenu;
	Menu workspacesMenu;
	MenuItem resetItem;
	MenuItem addWorkspaceItem;
	MenuItem existingWorkspace;
	ListView<String> listView;
	ObservableList<String> items;
	TextArea consoleOutput;
	
	File mavenOutput = new File("compiler/maven_output.txt");
	
	Pane pane;
	SQL sql = new SQL();	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
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
	
	public Pane getPane(Stage stage) {
		pane = new Pane();
		MavenCli maven = new MavenCli();
		
		
		listView = new ListView<>();
		items = FXCollections.observableArrayList();
		listView.setItems(items);
		
		listView.setLayoutX(150);
		listView.setLayoutY(50);
		listView.setPrefWidth(210);
		listView.setPrefHeight(150);
		
		consoleOutput = TextAreaBuilder.create().prefWidth(425).prefHeight(150).wrapText(true).build();
		consoleOutput.setLayoutX(35);
		consoleOutput.setLayoutY(250);
		consoleOutput.setEditable(false);
		
		Button compileBtn = new Button("Compile");
		compileBtn.setLayoutX(150);
		compileBtn.setLayoutY(205);
		compileBtn.setOnAction(compile -> {
			try {
				setOut(new FileOutputStream(mavenOutput));	
				
				String selectedProject = listView.getSelectionModel().getSelectedItem();
				String projectPath = sql.getProjectPath(selectedProject);			
				maven.doMain(new String[]{"compile"}, projectPath,System.out, System.out);
				maven.doMain(new String[]{"eclipse:eclipse"}, projectPath,System.out, System.out);
				
				setOut(console);
				
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
			}
		});
		
		Button ciBtn = new Button("Clean Install");
		ciBtn.setLayoutX(215);
		ciBtn.setLayoutY(205);
		ciBtn.setOnAction(cleanInstall -> {
			try {
				setOut(new FileOutputStream(mavenOutput));
				
				String selectedProject = listView.getSelectionModel().getSelectedItem();
				String projectPath = sql.getProjectPath(selectedProject);			
				maven.doMain(new String[]{"clean install"}, projectPath,System.out, System.out);
				maven.doMain(new String[]{"eclipse:eclipse"}, projectPath,System.out, System.out);
				
				setOut(console);
				
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
			}
		});
		
		Button warBtn = new Button("War:War");
		warBtn.setLayoutX(300);
		warBtn.setLayoutY(205);
		warBtn.setOnAction(war -> {
			try {
				setOut(new FileOutputStream(mavenOutput));
				
				String selectedProject = listView.getSelectionModel().getSelectedItem();
				String projectPath = sql.getProjectPath(selectedProject);			
				maven.doMain(new String[]{"war:war"}, projectPath,System.out, System.out);
				
				setOut(console);
				
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
			}
		});
		
		pane.getChildren().add(compileBtn);	
		pane.getChildren().add(ciBtn);	
		pane.getChildren().add(warBtn);	
		pane.getChildren().add(listView);	
		pane.getChildren().add(consoleOutput);
		return pane;
	}
	
	public MenuBar getMenu(Stage stage) {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		fileMenu = new Menu("File");
		editMenu = new Menu("Edit");
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
		menuBar.getMenus().addAll(menu, fileMenu, editMenu);
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
				
				/*if(sql.checkDupWorkspace().contains(workspaceDir.getPath())) {
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
				}*/
				
				SQLH2 sql = new SQLH2();
				if(sql.checkDupWorkspace().contains(workspaceDir.getPath())) {
					System.out.println("Workspace already exists");
				}else {
					sql.insertWorkspace(workspaceDir.getPath());
					List<File> list = getProjects(workspaceDir.getPath());
					list.forEach(project -> {
						sql.insertProject(project.getPath());
					});
					getExistingWorkspaces(stage);
				}
				
			}
		});
		workspacesMenu.getItems().add(addWorkspaceItem);
	}
	
	private void getExistingWorkspaces(Stage stage) {
		workspacesMenu.getItems().clear();
		getAddWorkspaceButton(stage);
		SQLH2 sql = new SQLH2();
		/*SQL workspaceSQL = new SQL();
		
		List<Workspace> list = workspaceSQL.getWorkspaces();
		list.forEach(workspace -> {
			existingWorkspace = new MenuItem(workspace.getPath());
			existingWorkspace.setOnAction(existing -> {
				items.clear();
				List<File> project = getProjects(workspace.getPath());
				project.forEach(e -> {
					items.add(e.getName());
				});
			});
			workspacesMenu.getItems().add(existingWorkspace);
		});*/
		
		List<Workspace> list = sql.getWorkspaces();
		list.forEach(workspace -> {
			System.out.println(workspace.getPath());
			existingWorkspace = new MenuItem(workspace.getPath());
			existingWorkspace.setOnAction(existing -> {
				items.clear();
				List<File> project = getProjects(workspace.getPath());
				project.forEach(e -> {
					items.add(e.getName());
				});
			});
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
	
	private void setOut(OutputStream output)  {
		try {
			PrintStream ps = new PrintStream(output);
			System.setOut(ps);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}