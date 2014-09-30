package com.shy.application.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.shy.application.xml.pojo.Workspaces;


public class XMLParser {
	public static void main(String[]rgs) {
		XMLParser xml = new XMLParser();
		
		for(Workspaces w : xml.parseXML("compiler/workspaces.xml")) {
			System.out.println(w.getName());
			System.out.println(w.getPath());
		}
	}
	
	public List <Workspaces> parseXML(String configFile) {
		List<Workspaces> nameAddressList = new ArrayList<>();
		Workspaces workspace = null;
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			
			while(eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				
				if(event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					String startElementName = startElement.getName().getLocalPart();  
					
					if(startElementName.equals("workspaces")) {
						workspace = new Workspaces();
					}
					
					if(event.isStartElement()) {
						if(event.asStartElement().getName().getLocalPart().equals("name")) {
							event = eventReader.nextEvent();
							workspace.setName(event.asCharacters().getData());
							continue;
						}
						if(event.asStartElement().getName().getLocalPart().equals("path")) {
							event = eventReader.nextEvent();
							workspace.setPath(event.asCharacters().getData());
							continue;
						}

					}
				}
				if(event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					String endElementName = endElement.asEndElement().getName().getLocalPart(); 
					
					if(endElementName.equals("workspaces")) {
						nameAddressList.add(workspace);
						
					}
				}
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(XMLStreamException e) {
			e.printStackTrace();
		}
		return nameAddressList;
	}
	
	
}