package com.shy.application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Workspace> getData() {
		List<Workspace> list = new ArrayList<>();
		Connection connection = null;
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			String query = "select * from workspace";
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Workspace workspace = new Workspace();
				workspace.setName(rs.getString("name"));
				workspace.setPath(rs.getString("path"));
				list.add(workspace);
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
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
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
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
			String query = "insert into workspace (name, path) values (?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			String name = ""+path.substring(path.lastIndexOf("\\")+1);
			pstmt.setString(1, name);
			pstmt.setString(2, path);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
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
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost:3306/mavencompiler";
		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}