package com.shy.application;
	
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.maven.cli.MavenCli;
import org.controlsfx.dialog.Dialogs;

import com.shy.application.ConsoleOutput;
import com.shy.application.database.SQL;
import com.shy.application.pojo.ConsoleWindow;
import com.shy.application.pojo.PreferenceTags;
import com.shy.application.pojo.Workspace;
import com.shy.application.xml.ReadXML;
import com.shy.application.xml.WriteXML;

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
	MenuBar menuBar;
	Menu menu;
	Menu fileMenu;
	Menu editMenu;
	Menu workspacesMenu;
	Menu preferencesMenu;
	MenuItem resetItem;
	MenuItem addWorkspaceItem;
	MenuItem existingWorkspace;
	CheckMenuItem preferenceCheck;
	ListView<String> listView;
	ObservableList<String> items;
	TextArea consoleOutput;
	
	Pane pane;
	SQL sql = new SQL();	
//	WriteXML writeXml = new WriteXML();
//	ReadXML readXml = new ReadXML();
//	PreferenceTags prefTags = new PreferenceTags();
	
	
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
//		consoleOutput = new TextArea();
		consoleOutput.setLayoutX(35);
		consoleOutput.setLayoutY(250);
		consoleOutput.setEditable(false);
		
		Console console = new Console(consoleOutput);
		PrintStream ps = new PrintStream(console, true);
		ps.flush();
		System.setOut(ps);
		System.setErr(ps);
		
		
		
		Button compileBtn = new Button("Compile");
		compileBtn.setLayoutX(150);
		compileBtn.setLayoutY(205);
		compileBtn.setOnAction(e -> {
			
			/*readXml.readXMLPreferences(getPreferencesFile()).forEach(preferences -> {
				if (preferences.getStatus().equals("true")) {
					ConsoleOutput outputWindow = new ConsoleOutput();
					Stage outputStage = new Stage();
					outputWindow.getOutputPane(outputStage);
				}
			});*/
			
			String selectedProject = listView.getSelectionModel().getSelectedItem();
			String projectPath = sql.getProjectPath(selectedProject);			
			maven.doMain(new String[]{"compile"}, projectPath,System.out, System.out);
			maven.doMain(new String[]{"eclipse:eclipse"}, projectPath,System.out, System.out);
		});
		
		Button ciBtn = new Button("Clean Install");
		ciBtn.setLayoutX(215);
		ciBtn.setLayoutY(205);
		ciBtn.setOnAction(e -> {
			
			/*readXml.readXMLPreferences(getPreferencesFile()).forEach(preferences -> {
				if (preferences.getStatus().equals("true")) {
					ConsoleOutput outputWindow = new ConsoleOutput();
					Stage outputStage = new Stage();
					outputWindow.getOutputPane(outputStage);
				}
			});*/
			
			String selectedProject = listView.getSelectionModel().getSelectedItem();
			String projectPath = sql.getProjectPath(selectedProject);			
			maven.doMain(new String[]{"clean install"}, projectPath,System.out, System.out);
			maven.doMain(new String[]{"eclipse:eclipse"}, projectPath,System.out, System.out);
		});
		
		Button warBtn = new Button("War:War");
		warBtn.setLayoutX(300);
		warBtn.setLayoutY(205);
		warBtn.setOnAction(e -> {
			String selectedProject = listView.getSelectionModel().getSelectedItem();
			String projectPath = sql.getProjectPath(selectedProject);			
			maven.doMain(new String[]{"war:war"}, projectPath,System.out, System.out);
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
		preferencesMenu = new Menu("Preferences");
		preferenceCheck = new CheckMenuItem("Show console");		
		
		/*readXml.readXMLPreferences(getPreferencesFile()).forEach(preferences -> {
			if(preferences.getStatus().equals("true")) {
				preferenceCheck.setSelected(true);
			}
			if(preferences.getStatus().equals("false")) {
				preferenceCheck.setSelected(false);
			}
		});*/
		
		/*preferenceCheck.setOnAction(check -> {
			readXml.readXMLPreferences(getPreferencesFile()).forEach(preferences -> {
				if(preferences.getStatus().equals("true")) {
					prefTags.setConsolewindow(false);		
					
				}
				if(preferences.getStatus().equals("false")) {
					prefTags.setConsolewindow(true);
				}
			});
			
			try {
				writeXml.savePreferences(getPreferencesFile(), prefTags);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});*/
		
		preferencesMenu.getItems().add(preferenceCheck);
		getExistingWorkspaces(stage);
		
		resetItem = new MenuItem("Reset");
		resetItem.setOnAction(e -> {
			sql.resetTable("workspace");
			sql.resetTable("project");
			System.out.println("tables has been reset!");
			getExistingWorkspaces(stage);
		});
		MenuItem closeItem = new MenuItem("Close");
		
		menu.getItems().addAll(workspacesMenu,preferencesMenu,resetItem, new SeparatorMenuItem(), closeItem);
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
				}
			}
		});
		workspacesMenu.getItems().add(addWorkspaceItem);
	}
	
	private void getExistingWorkspaces(Stage stage) {
		workspacesMenu.getItems().clear();
		getAddWorkspaceButton(stage);
		SQL workspaceSQL = new SQL();
		List<Workspace> list = workspaceSQL.getData();
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
	
	public String getPreferencesFile() {
		return "C:/Users/b21019/desktop/preferences.xml";
	}
	
}