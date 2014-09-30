package com.shy.application.xml;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.shy.application.pojo.PreferenceTags;

public class WriteXML {
	
	public static void main(String[]args) {
		
		WriteXML xml = new WriteXML();
		try {
			xml.insertWorkspace("compiler/test.xml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/*public void savePreferences(String configFile, PreferenceTags prefTags) throws Exception {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(configFile));
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		
		List<PreferenceTags> prefTagsList = new ArrayList<>();
		
		if(prefTags.isConsolewindow()) {
			prefTags.setName("status");
			prefTags.setValue("true");
		}else {
			prefTags.setName("status");
			prefTags.setValue("false");
		}
		prefTags.setInnertag("consolewindow"); // loop when there's more
		prefTagsList.add(prefTags);
		
		createMaintag(eventWriter, eventFactory, end, prefTagsList);
		
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}*/
	
	public void insertWorkspace(String filepath) throws Exception {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(filepath));
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		
		List<String> nodeList = new ArrayList<>();
		
		createMaintag(eventWriter, eventFactory, end, "workspaces", "workspace", nodeList);
		
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}
	
	private void createMaintag(XMLEventWriter eventWriter, XMLEventFactory eventFactory, XMLEvent end, String startTag, String innerTag, List<String> nodeList)  {
		StartElement configStartElement = eventFactory.createStartElement("", "", startTag);
		try {
			eventWriter.add(configStartElement);
			eventWriter.add(end);
			
			
			
			createInnerTag(eventWriter, eventFactory, end, "innerTag", nodeList, "workspace");	
			
			
			
			eventWriter.add(eventFactory.createEndElement("", "",""));
			eventWriter.add(end);
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createInnerTag(XMLEventWriter eventWriter, XMLEventFactory eventFactory, XMLEvent end, String innerTag, List<String> nodeList , String value) throws Exception {
		StartElement configStartElement = eventFactory.createStartElement("", "", innerTag);
		eventWriter.add(configStartElement); 
		eventWriter.add(end);
		
		for(String str : nodeList) {
			
		}
		
//		createNode(eventWriter, name, value);					
		
		eventWriter.add(eventFactory.createEndElement("","",""));
		eventWriter.add(end);
		
	}
	
	private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		
		StartElement startElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(startElement);
		Characters chars = eventFactory.createCharacters(value);
		eventWriter.add(chars);
		
		EndElement endElement = eventFactory.createEndElement("", "",name);
		eventWriter.add(endElement);
		eventWriter.add(end);
	}
	
}
