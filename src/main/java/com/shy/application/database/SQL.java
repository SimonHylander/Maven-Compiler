package com.shy.application.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.shy.application.pojo.Workspace;

public class SQL {
	
	public String getProjectPath(String name) {
		String result = "";
		Connection connection = null;
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			String query = "select * from project where name = '"+name+"'";
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getString("path");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public List<Workspace> getWorkspaces() {
		List<Workspace> list = new ArrayList<>();
		Connection connection = null;
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
//			String query = "select * from workspace order by name";
			String query = "select * from workspace order by date desc";
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Workspace workspace = new Workspace();
				workspace.setName(rs.getString("name"));
				workspace.setPath(rs.getString("path"));
				list.add(workspace);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<String> checkDupWorkspace() {
		List<String>list = new ArrayList<>();
		
		Connection connection = null;
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			String query = "select path from workspace";
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("path"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void insertWorkspace(String path) {
		Connection connection = null;
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			String query = "insert into workspace (name, path, date) values (?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			String name = ""+path.substring(path.lastIndexOf("\\")+1);
			pstmt.setString(1, name);
			pstmt.setString(2, path);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertProject(String path) {
		Connection connection = null;
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			String query = "insert into project (name, path) values (?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			
			String name = ""+path.substring(path.lastIndexOf("\\")+1);
			
			pstmt.setString(1, name);
			pstmt.setString(2, path);
			pstmt.executeUpdate();
			
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetTable(String table) {
		Connection connection = null;
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			String query = "truncate table "+table;
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}