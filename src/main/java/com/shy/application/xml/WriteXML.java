package com.shy.application.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXML {
	
	public static void main(String[]args) {
		WriteXML xml = new WriteXML();
		try {
			xml.insertWorkspace("workspace","C:/Users/b21019/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertWorkspace(String name, String path) throws Exception {
		 DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        Document document = documentBuilder.parse("compiler/workspaces.xml");
	        Element root = document.getDocumentElement();
	        
	        // Root Element
	        Element rootElement = document.getDocumentElement();
	        
	        // server elements
	        Element workspace = document.createElement("workspace");
	        rootElement.appendChild(workspace);
	        
	        Element nameElement = document.createElement("name");
	        nameElement.appendChild(document.createTextNode(name));
	        workspace.appendChild(nameElement);
	        
	        Element pathElement = document.createElement("path");
	        pathElement.appendChild(document.createTextNode(path));
	        workspace.appendChild(pathElement);
	        
	        root.appendChild(workspace);
	        
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", 2);
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        
	        DOMSource source = new DOMSource(document);
	        StreamResult result =  new StreamResult("compiler/workspaces.xml");
	        transformer.transform(source, result);
	}
}