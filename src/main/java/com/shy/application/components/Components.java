package com.shy.application.components;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class Components {
	public ContextMenu getContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		
		Menu mavenMenu = new Menu("Maven");
		MenuItem mavenCompile = new MenuItem("Compile");
		MenuItem mavenClean = new MenuItem("Clean Install");
		MenuItem mavenWar = new MenuItem("War");
		
		Menu gitMenu = new Menu("Git");
		MenuItem gitCommit = new MenuItem("Commit");
		MenuItem gitPush = new MenuItem("Push");
		MenuItem gitPull = new MenuItem("Pull");
		
		mavenCompile.setOnAction(action -> {
//			getMavenActions("compile");
			System.out.println("compile");
		});
		
		mavenClean.setOnAction(action -> {
//			getMavenActions("clean");
		});
		
		mavenWar.setOnAction(action -> {
//			getMavenActions("war:war");
		});
		
		
		gitMenu.getItems().addAll(gitCommit,gitPush,gitPull);
		mavenMenu.getItems().addAll(mavenCompile,mavenClean,mavenWar);
		
		contextMenu.getItems().addAll(gitMenu,mavenMenu);
		
		return contextMenu;
	}
	
	public void setOnActions() {
		
	}
}
