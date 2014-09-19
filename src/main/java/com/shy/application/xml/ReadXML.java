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




import com.shy.application.pojo.ConsoleWindow;
import com.shy.application.pojo.Preferences;


public class ReadXML {

	public static void main(String[] args) {
		ReadXML readXml = new ReadXML();
//		readXml.readXMLPreferences("C:/Users/b21019/desktop/spar.xml");
		
		for(ConsoleWindow cWindow : readXml.readXMLPreferences("C:/Users/b21019/desktop/preferences.xml")) {
			System.out.println(cWindow.getStatus());
		}
	}
	
	public List<ConsoleWindow> readXMLPreferences(String configFile) {
		List<ConsoleWindow> consoleWindow = new ArrayList<ConsoleWindow>();
		ConsoleWindow nameAddress = null;
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			
			while(eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				
				if(event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					String startElementName = startElement.getName().getLocalPart();  
					
					if(startElementName.equals("consolewindow")) {
						nameAddress = new ConsoleWindow();
					}
					
					if(event.isStartElement()) {
						if(event.asStartElement().getName().getLocalPart().equals("status")) {
							event = eventReader.nextEvent();
							nameAddress.setStatus(event.asCharacters().getData());
							continue;
						}
						
					}
				}
				if(event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					String endElementName = endElement.asEndElement().getName().getLocalPart(); 
					
					if(endElementName.equals("consolewindow")) {
						consoleWindow.add(nameAddress);
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
		return consoleWindow;
	}
	
}
