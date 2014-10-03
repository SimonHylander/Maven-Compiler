package com.shy.application.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class ModifyXMLFile {
 
	public static void main(String argv[]) {
		
		try {
			writeWorkspaceXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static void writeWorkspaceXML() throws Exception {

	        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        Document document = documentBuilder.parse("compiler/workspaces.xml");
	        Element root = document.getDocumentElement();
	        
	        
	        // Root Element
	        Element rootElement = document.getDocumentElement();
	        
	            // server elements
	            Element server = document.createElement("workspace");
	            rootElement.appendChild(server);
	            
	            Element name = document.createElement("name");
	            name.appendChild(document.createTextNode("name"));
	            server.appendChild(name);
	            
	            Element port = document.createElement("path");
	            port.appendChild(document.createTextNode("path"));
	            server.appendChild(port);
	            
	            root.appendChild(server);
	       

	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", 2);
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        DOMSource source = new DOMSource(document);
	        StreamResult result =  new StreamResult("compiler/workspaces.xml");
	        transformer.transform(source, result);
	    }
}