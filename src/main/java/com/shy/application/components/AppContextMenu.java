package com.shy.application.components;

import com.shy.application.Maven;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class AppContextMenu {
	public ContextMenu getContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		Maven maven = new Maven();
		
		Menu gitMenu = new Menu("Git");
		MenuItem gitCommit = new MenuItem("Commit");
		MenuItem gitPush = new MenuItem("Push");
		MenuItem gitPull = new MenuItem("Pull");
		
		Menu mavenMenu = new Menu("Maven");
		MenuItem mavenCompile = new MenuItem("Compile");
		MenuItem mavenClean = new MenuItem("Clean Install");
		MenuItem mavenWar = new MenuItem("War");
		
		/*mavenCompile.setOnAction(action -> {
			maven.getAction("compile");
		});
		
		mavenClean.setOnAction(action -> {
			maven.getAction("clean");
		});
		
		mavenWar.setOnAction(action -> {
			maven.getAction("war:war");
		});*/
		
		
		gitMenu.getItems().addAll(gitCommit,gitPush,gitPull);
		mavenMenu.getItems().addAll(mavenCompile,mavenClean,mavenWar);
		
		contextMenu.getItems().addAll(gitMenu,mavenMenu);
		
		return contextMenu;
	}
}
