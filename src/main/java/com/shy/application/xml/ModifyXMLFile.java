package com.shy.application.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
//		writeXML("compiler/test.xml","workspace");
		
//		writeWorkspaceXML();
	}
	
	public static void writeWorkspaceXML() {
		try {
			String filepath = "compiler/workspaces.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
	 
			Node workspaces = doc.getFirstChild();
			Node innerNode = doc.getElementsByTagName("workspace").item(0);
			 
			NamedNodeMap attr = innerNode.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("2");
			
			System.out.println(innerNode.getChildNodes());
			
			NodeList list = innerNode.getChildNodes();
			
			for (int i = 0; i < list.getLength(); i++) {
				 
	            Node node = list.item(i);
	            if("name".equals(node.getNodeName())) {
	            	node.setTextContent("workspace");
	            }
	            if("path".equals(node.getNodeName())) {
	            	node.setTextContent("C:/Users/b21019/workspaces/workspace");
	            }            
			}
			
			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeXML(String filepath, String secondNode) {
	   try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);
 
		// Get the root element
		Node mainNode = doc.getFirstChild();
 
		// Get the staff element , it may not working if tag has spaces, or
		// whatever weird characters in front...it's better to use
		// getElementsByTagName() to get it directly.
		// Node staff = company.getFirstChild();
		
		// Get the staff element by tag name directly
		Node innerNode = doc.getElementsByTagName(secondNode).item(0);
 
		// update staff attribute
		NamedNodeMap attr = innerNode.getAttributes();
		Node nodeAttr = attr.getNamedItem("id");
		nodeAttr.setTextContent("2");
 
		// append a new node to staff
		/*Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode("workspace"));
		innerNode.appendChild(name);
		
		Element path = doc.createElement("path");
		path.appendChild(doc.createTextNode("C://path"));
		innerNode.appendChild(path);*/
		
		System.out.println(innerNode.getChildNodes());
		
		NodeList list = innerNode.getChildNodes();
		
		for (int i = 0; i < list.getLength(); i++) {
			 
            Node node = list.item(i);
            if("name".equals(node.getNodeName())) {
            	node.setTextContent("name");
            }
            if("path".equals(node.getNodeName())) {
            	node.setTextContent("path");
            }            
		}
		
		/*NodeList list = innerNode.getChildNodes();
 
		for (int i = 0; i < list.getLength(); i++) {
 
                   Node node = list.item(i);
 
		   // get the salary element, and update the value
		   if ("salary".equals(node.getNodeName())) {
			node.setTextContent("2000000");
		   }
 
                   //remove firstname
		   if ("firstname".equals(node.getNodeName())) {
			innerNode.removeChild(node);
		   }
 
		}*/
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);
 
		System.out.println("Done");
 
	   } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	   } catch (TransformerException tfe) {
		tfe.printStackTrace();
	   } catch (IOException ioe) {
		ioe.printStackTrace();
	   } catch (SAXException sae) {
		sae.printStackTrace();
	   }
	}
}