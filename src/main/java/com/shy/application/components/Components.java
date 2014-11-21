package com.shy.application.components;

import java.io.File;
import java.util.List;

import org.controlsfx.dialog.Dialogs;

import com.shy.application.Maven;
import com.shy.application.database.SQL;
import com.shy.application.pojo.Workspace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Components {
	SQL sql = new SQL();
	Maven maven = new Maven();
	
	public Menu getWorkspaceMenu(ObservableList<String> items) {
		Menu workspaceMenu = new Menu("Workspaces");
		
		MenuItem addWorkspaceItem = new MenuItem("Add Workspace");
		workspaceMenu.getItems().addAll(addWorkspaceItem);
		addWorkspaceItem.setOnAction(action -> {
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			File workspaceDirectory = dirChooser.showDialog(null);
			if(workspaceDirectory != null) {
				
				if(sql.checkDupWorkspace().contains(workspaceDirectory.getPath())) {
					System.out.println("Workspace already exists");
					Dialogs.create()
						      .title("Duplicate Workspace")
						      .message( "Workspace already exists")
						      .showWarning();
				}else {
					items.clear();
					sql.insertWorkspace(workspaceDirectory.getPath());
					List<File> projectList = maven.getMavenProjects(workspaceDirectory.getPath());
					projectList.forEach(project -> {
						sql.insertProject(project.getPath());
					});
					workspaceMenu.getItems().remove(1, workspaceMenu.getItems().size());
					
					List<Workspace> workspaceList = sql.getWorkspaces();
					workspaceList.forEach(workspace -> {
						System.out.println(workspace.getPath());
						MenuItem tmpItem = new MenuItem(workspace.getPath());
						workspaceMenu.getItems().add(tmpItem);
					});
				}
			}
		});
		
		List<Workspace> workspaceList = sql.getWorkspaces();
		workspaceList.forEach(workspace -> {
			MenuItem tmpItem = new MenuItem(workspace.getPath());
			tmpItem.setOnAction(tmp -> {
				items.clear();
				Maven maven = new Maven();
				List<File> projects = maven.getMavenProjects(workspace.getPath());
				projects.forEach(proj -> {
					items.add(proj.getName());
				});
			});
			workspaceMenu.getItems().add(tmpItem);
		});
		return workspaceMenu;
	}
	
	public ObservableList<String> getObersvableList() {
		return FXCollections.observableArrayList(); 
	}
	
	public ListView<String> getListView() {
		ListView<String> listView = new ListView<>();
		listView.setLayoutX(150);
		listView.setLayoutY(50);
		listView.setPrefWidth(210);
		listView.setPrefHeight(150);
		return listView;
	}
	
	public ContextMenu getContextMenu(ListView<String>listView) {
		ContextMenu contextMenu = new ContextMenu();
		Menu mavenMenu = getMavenMenu(listView);
		Menu gitMenu = getGitMenu(listView);
		contextMenu.getItems().addAll(gitMenu,mavenMenu);
		return contextMenu;
	}
	
	private Menu getMavenMenu(ListView<String>listView) {
		Menu menu = new Menu("Maven");
		MenuItem mavenCompile = new MenuItem("Compile");
		MenuItem mavenClean = new MenuItem("Clean Install");
		MenuItem mavenWar = new MenuItem("War");
		
		Maven maven = new Maven();
		
		mavenCompile.setOnAction(action -> {
			if(listView.getSelectionModel().getSelectedItem() != null && listView.getSelectionModel().getSelectedItem().length() > 0) {
				maven.compile(listView.getSelectionModel().getSelectedItem());
			}
			
		});
		
		mavenClean.setOnAction(action -> {
			if(listView.getSelectionModel().getSelectedItem() != null && listView.getSelectionModel().getSelectedItem().length() > 0) {
				maven.cleanInstall(listView.getSelectionModel().getSelectedItem());
			}
			
		});
		
		mavenWar.setOnAction(action -> {
			if(listView.getSelectionModel().getSelectedItem() != null && listView.getSelectionModel().getSelectedItem().length() > 0) {
				maven.generateWar(listView.getSelectionModel().getSelectedItem());
			}
			
		});
		menu.getItems().addAll(mavenCompile,mavenClean,mavenWar);
		return menu;
	}
	
	private Menu getGitMenu(ListView<String>listView) {
		Menu menu = new Menu("Git");
		MenuItem gitCommit = new MenuItem("Commit");
		MenuItem gitPush = new MenuItem("Push");
		MenuItem gitPull = new MenuItem("Pull");
		
		gitCommit.setOnAction(action -> {
			if(listView.getSelectionModel().getSelectedItem() != null && listView.getSelectionModel().getSelectedItem().length() > 0) {
				System.out.println("Commit");
				//open new window choose files to commit
				
				String project = sql.getProjectPath(listView.getSelectionModel().getSelectedItem());
				
				
				/*CommitWindow commitWindow = new CommitWindow();
				commitWindow.getCommitWindow();*/
			}
		});
		
		gitPush.setOnAction(action -> {
			if(listView.getSelectionModel().getSelectedItem() != null && listView.getSelectionModel().getSelectedItem().length() > 0) {
				System.out.println("Push");
			}
		});
		
		gitPull.setOnAction(action -> {
			if(listView.getSelectionModel().getSelectedItem() != null && listView.getSelectionModel().getSelectedItem().length() > 0) {
				System.out.println("Pull");
			}
		});
		
		
		menu.getItems().addAll(gitCommit,gitPush,gitPull);
		
		return menu;
	}
}
