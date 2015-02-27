/*
 * Copyright (C) 2012-2015 Radical Electronic Systems, South Africa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.radicales.sm100.apps;

import com.radicales.sm100.device.Sequence;
import com.radicales.sm100.device.Sm100;
import com.radicales.sm100.device.Sm100Program;
import com.radicales.sm100.device.Sm100Zone;
import com.radicales.sm100.device.StartTime;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Setup XML
 * Parses a setup file and converts setup into a XML file
 *
 * @author
 * Jan Zwiegers,
 * <a href="mailto:jan@radicalsystems.co.za">jan@radicalsystems.co.za</a>,
 * <a href="http://www.radicalsystems.co.za">www.radicalsystems.co.za</a>
 *
 * @version
 * <b>1.0 01/11/2014</b><br>
 * Original release.
 */
public class SetupXML {

    private static final Logger gLogger = Logger.getLogger("SmartMist");
    private static final String[] WATER_MONTHS = { "January", "February", "March", "April", "May", "June",
                                                "July", "August", "September", "October", "November", "December"};
    private String gFileName;
    private List<Sm100> gSm100List = new ArrayList();
    private SetupXMLEvent gListener = null;

    public SetupXML( String FileName, SetupXMLEvent Listener ) {
        gFileName = FileName;
        gListener = Listener;
    }

    public List<Sm100> getDevices() {
        return gSm100List;
    }

    public void addDevice( Sm100 sm100 ) {
        gSm100List.add(sm100);
        if(gListener != null) {
            gListener.eventDeviceListChanged(gSm100List);
        }
    }

    private Element createZonesNode( Document doc, List<Sm100Zone> zoneList ) {
        Element ze = doc.createElement("Zones");

        for(Sm100Zone z : zoneList) {
            Element e = doc.createElement("Zone");
            e.setAttribute("name", z.getName());
            e.setAttribute("channel", Integer.toString(z.getChannel()));
            e.setAttribute("controlword", "0x" + Long.toHexString(z.getControlWord()));
            e.setAttribute("initvalue", Boolean.toString(z.getInitState()));
            e.setAttribute("offdelay", Integer.toString(z.getOffDelay()));
            ze.appendChild(e);
        }

        return ze;
    }

    private Element createProgramWaterBudget( Document doc, int[] WaterBudget ) {
        Element wb = doc.createElement("WaterBudget");
        for(int d=0; d<12; d++) {
            Element e = doc.createElement(WATER_MONTHS[d]);
            e.setTextContent(Integer.toString(WaterBudget[d]));
            wb.appendChild(e);
        }

        return wb;
    }

    private Element createProgramStartTimes( Document doc, List<StartTime> timesList ) {
        Element ste = doc.createElement("StartTimes");

        for(StartTime st : timesList) {
            Element e = doc.createElement("StartTime");
            e.setAttribute("time", st.toString());
            ste.appendChild(e);
        }

        return ste;
    }

    private Element createProgramSequences( Document doc, List<Sequence> seqList ) {
        Element se = doc.createElement("Sequences");

        for(Sequence sq : seqList) {
            Element e = doc.createElement("Sequence");
            e.setAttribute("zone", sq.Zone.getName());
            e.setAttribute("runtime", Integer.toString(sq.RunTime));
            se.appendChild(e);
        }

        return se;
    }

    private Element createProgramsNode( Document doc, List<Sm100Program> progList ) {
        Element progs = doc.createElement("Programs");

        for(Sm100Program p : progList) {

            Element pe = doc.createElement("Program");
            pe.setAttribute("name", p.getName());
            pe.setAttribute("controlword", "0x" + Long.toHexString(p.getControlWord()));
            pe.appendChild(createProgramWaterBudget(doc, p.getWaterBudget()));
            pe.appendChild(createProgramStartTimes(doc, p.getStartTimesList()));
            pe.appendChild(createProgramSequences(doc, p.getSequenceList()));
            progs.appendChild(pe);
        }

        return progs;
    }

    private Document assyDocument() {
        Document doc = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("SmartMist");
            rootElement.setAttribute("version", "1.0");
            doc.appendChild(rootElement);

            for(Sm100 sm : gSm100List) {
                // SM100 elements
                Element sme = doc.createElement("SM100");
                sme.setAttribute("name", sm.getName());
                sme.setAttribute("address", sm.getIpAddress());
                sme.setAttribute("port", Integer.toString(sm.getPort()));
                sme.setAttribute("start", Boolean.toString(sm.getEnabled()));
		rootElement.appendChild(sme);
                Element desc = doc.createElement("Description");
                desc.setTextContent(sm.getDescription());
                sme.appendChild(desc);
                Element loc = doc.createElement("Location");
                loc.setTextContent(sm.getLocation());
                sme.appendChild(loc);
                Element mode = doc.createElement("Mode");
                mode.setTextContent(sm.getMode());
                sme.appendChild(mode);
                sme.appendChild(createZonesNode(doc, sm.getZoneList()));
                Element progs = createProgramsNode(doc, sm.getProgramsList());
                sme.appendChild(progs);
            }

        } catch (ParserConfigurationException ex) {
            return null;
        }

        return doc;
    }

    public boolean save() {
        Document doc = assyDocument();
        if(doc == null) {
            return false;
        }

        try {
            File xmlFile = new File(gFileName);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(xmlFile);
            Source input = new DOMSource(doc);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(input, output);
        } catch (TransformerConfigurationException ex) {
            return false;
        } catch (TransformerException ex) {
            return false;
        }

        return true;
    }

    public boolean saveAs( String FileName ) {
        gFileName = FileName;
        Document doc = assyDocument();
        if(doc == null) {
            return false;
        }

        try {
            File xmlFile = new File(gFileName);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(xmlFile);
            Source input = new DOMSource(doc);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(input, output);
        } catch (TransformerConfigurationException ex) {
            return false;
        } catch (TransformerException ex) {
            return false;
        }

        return true;
    }

    public void parse() {
        File xmlFile = new File(gFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            gLogger.error(ex.getMessage());
        }

        if(doc != null) {
            doc.getDocumentElement().normalize();
            Node pn = doc.getFirstChild();
            if((pn != null) && (pn.getNodeName().matches("SmartMist")) ) {

                NodeList nList = pn.getChildNodes();

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node cNode = nList.item(temp);

                    if (cNode.getNodeType() == Node.ELEMENT_NODE) {

                       Element eElement = (Element) cNode;
                       if(eElement.getNodeName().matches("SM100")) {
                           Sm100 sm = parseSM100(cNode);
                           if(sm != null) {
                               gSm100List.add(sm);
                           }
                       }
                       else {
                           gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                       }
                    }
                }
            }
            else {
                gLogger.error("File Format Error: " + xmlFile.getName());
            }
        }
        else {
            gLogger.error("Failed to parse: " + xmlFile.getName());
        }

        if(gListener != null) {
            gListener.eventDeviceListChanged(gSm100List);
        }
    }

     private Sm100 parseSM100(Node node) {
        NodeList nList = node.getChildNodes();
        Element dElement = (Element) node;
        String name =  dElement.getAttribute("name");
        String address = dElement.getAttribute("address");
        String port = dElement.getAttribute("port");
        String start = dElement.getAttribute("start");
        int nport = Integer.parseInt(port);
        boolean bstart = Boolean.parseBoolean(start);
        Sm100 sm100 = new Sm100(name, address, nport, bstart);

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node cNode = nList.item(temp);

            if (cNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) cNode;
                if (eElement.getNodeName().matches("SetupFile")) {

                }
                else if (eElement.getNodeName().matches("Description")) {
                    sm100.setDescription(eElement.getTextContent());
                }
                else if (eElement.getNodeName().matches("Location")) {
                    sm100.setLocation(eElement.getTextContent());
                }
                else if (eElement.getNodeName().matches("Mode")) {
                    sm100.setMode(eElement.getTextContent());
                }
                else if (eElement.getNodeName().matches("Zones")) {
                    parseZones(cNode, sm100);
                }
                else if (eElement.getNodeName().matches("Programs")) {
                    parsePrograms(cNode, sm100);
                }
                else {
                    gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                }
            }
        }

        return sm100;
    }

    private void parseZones(Node node, Sm100 sm100) {
        NodeList nList = node.getChildNodes();
         for (int temp = 0; temp < nList.getLength(); temp++) {

            Node cNode = nList.item(temp);
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) cNode;
                if(eElement.getNodeName().matches("Zone")) {
                    String name =  eElement.getAttribute("name");
                    String channel = eElement.getAttribute("channel");
                    String controlword = eElement.getAttribute("controlword");
                    String initvalue = eElement.getAttribute("initvalue");
                    String offdelay = eElement.getAttribute("offdelay");
                    int ichan = Integer.parseInt(channel);
                    long icw = Long.decode(controlword);
                    int ioffdel = Integer.parseInt(offdelay);
                    boolean binit = initvalue.matches("on");
                    sm100.addZone(name, ichan, icw, binit, ioffdel, true);
                }
                else {
                    gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                }
            }
        }
    }

    private void parsePrograms(Node node, Sm100 sm100) {
        NodeList nList = node.getChildNodes();
         for (int temp = 0; temp < nList.getLength(); temp++) {

            Node cNode = nList.item(temp);
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) cNode;
                if(eElement.getNodeName().matches("Program")) {
                    String name =  eElement.getAttribute("name");
                    String controlword = eElement.getAttribute("controlword");
                    long icw = Long.decode(controlword);
                    Sm100Program p = new Sm100Program(name, icw, sm100);
                    parseProgram(cNode, p, sm100);
                    sm100.addProgramObject(p);
                }
                else {
                    gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                }
            }
        }
    }

    private void parseProgram(Node node, Sm100Program prog, Sm100 sm100) {
        NodeList nList = node.getChildNodes();
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node cNode = nList.item(temp);
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) cNode;
                if(eElement.getNodeName().matches("WaterBudget")) {
                    prog.setWaterBudget(parseWaterBudget(cNode));
                }
                else if(eElement.getNodeName().matches("StartTimes")) {
                    prog.setStartTimesList(parseStartTimes(cNode));
                }
                else if(eElement.getNodeName().matches("Sequences")) {
                    prog.setSequenceList(parseSequences(cNode, sm100));
                }
                else {
                    gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                }
            }
         }
    }

    private int[] parseWaterBudget(Node node) {
        NodeList nList = node.getChildNodes();
        int[] wb = new int[12];

        for(int i : wb) {
            i = 100;
        }

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node cNode = nList.item(temp);
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) cNode;
                boolean found = false;
                for(int i=0; i<12 && !found; i++) {
                    if(eElement.getNodeName().matches(WATER_MONTHS[i])) {
                        found = true;
                        wb[i] = Integer.parseInt(eElement.getTextContent());
                    }
                }

                if(!found) {
                    gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                }
            }
        }

        return wb;
    }

    private List<StartTime> parseStartTimes(Node node) {
        NodeList nList = node.getChildNodes();
        List<StartTime> stl = new ArrayList();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node cNode = nList.item(temp);
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) cNode;
                if(eElement.getNodeName().matches("StartTime")) {
                    String time = eElement.getAttribute("time");
                    StartTime st = new StartTime(time);
                    stl.add(st);
                }
                else {
                    gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                }
            }
        }

        return stl;
    }

    private List<Sequence> parseSequences(Node node, Sm100 sm100) {
        NodeList nList = node.getChildNodes();
        List<Sequence> seql = new ArrayList();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node cNode = nList.item(temp);
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) cNode;
                if(eElement.getNodeName().matches("Sequence")) {
                    String zone = eElement.getAttribute("zone");
                    String runtime = eElement.getAttribute("runtime");
                    int irt = Integer.parseInt(runtime);
                    Sm100Zone z = sm100.getZone(zone);
                    Sequence s = new Sequence(z, irt);
                    seql.add(s);
                }
                else {
                    gLogger.warn("Skipping uknown Node: " + eElement.getNodeName());
                }
            }
        }

        return seql;
    }


}
