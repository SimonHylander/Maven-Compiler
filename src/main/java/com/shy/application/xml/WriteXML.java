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
		PreferenceTags prefTags = new PreferenceTags();
		List<PreferenceTags> prefTagsList = new ArrayList<>();
		prefTags.setConsolewindow(false);
		
		
		WriteXML xml = new WriteXML();
		try {
			xml.savePreferences("C:/Users/b21019/desktop/preferences.xml", prefTags);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void savePreferences(String configFile, PreferenceTags prefTags) throws Exception {
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
	}
	
	
	private void createMaintag(XMLEventWriter eventWriter, XMLEventFactory eventFactory, XMLEvent end, List<PreferenceTags> prefTagslist)  {
		StartElement configStartElement = eventFactory.createStartElement("", "", "preferences");
		try {
			eventWriter.add(configStartElement);
			eventWriter.add(end);
			
			if(prefTagslist != null) {
				for(PreferenceTags tags : prefTagslist) {
					createInnerTag(eventWriter, eventFactory, end, tags.getInnertag(), tags.getName(), tags.getValue());				
				}
			}
			eventWriter.add(eventFactory.createEndElement("", "",""));
			eventWriter.add(end);
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createInnerTag(XMLEventWriter eventWriter, XMLEventFactory eventFactory, XMLEvent end, String tagname, String name, String value) throws Exception {
		StartElement configStartElement = eventFactory.createStartElement("", "", tagname);
		eventWriter.add(configStartElement);
		eventWriter.add(end);
		
		createNode(eventWriter, name, value);		
		
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
