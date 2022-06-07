package org.fourthline.cling.support.lastchange;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import org.fourthline.cling.model.XMLUtil;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.shared.AbstractMap;
import org.seamless.util.Exceptions;
import org.seamless.util.io.IO;
import org.seamless.xml.DOMParser;
import org.seamless.xml.SAXParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: classes5.dex */
public abstract class LastChangeParser extends SAXParser {
    private static final Logger log = Logger.getLogger(LastChangeParser.class.getName());

    protected abstract String getNamespace();

    /* loaded from: classes5.dex */
    public enum CONSTANTS {
        Event,
        InstanceID,
        val;

        public boolean equals(String str) {
            return name().equals(str);
        }
    }

    protected Set<Class<? extends EventedValue>> getEventedVariables() {
        return Collections.EMPTY_SET;
    }

    protected EventedValue createValue(String str, Map.Entry<String, String>[] entryArr) throws Exception {
        for (Class<? extends EventedValue> cls : getEventedVariables()) {
            if (cls.getSimpleName().equals(str)) {
                return (EventedValue) cls.getConstructor(Map.Entry[].class).newInstance(entryArr);
            }
        }
        return null;
    }

    public Event parseResource(String str) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
            return parse(IO.readLines(inputStream));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public Event parse(String str) throws Exception {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("Null or empty XML");
        }
        Event event = new Event();
        new RootHandler(event, this);
        if (log.isLoggable(Level.FINE)) {
            log.fine("Parsing 'LastChange' event XML content");
            log.fine("===================================== 'LastChange' BEGIN ============================================");
            log.fine(str);
            log.fine("====================================== 'LastChange' END  ============================================");
        }
        parse(new InputSource(new StringReader(str)));
        Logger logger = log;
        logger.fine("Parsed event with instances IDs: " + event.getInstanceIDs().size());
        if (log.isLoggable(Level.FINEST)) {
            for (InstanceID instanceID : event.getInstanceIDs()) {
                Logger logger2 = log;
                logger2.finest("InstanceID '" + instanceID.getId() + "' has values: " + instanceID.getValues().size());
                for (EventedValue eventedValue : instanceID.getValues()) {
                    Logger logger3 = log;
                    logger3.finest(eventedValue.getName() + " => " + eventedValue.getValue());
                }
            }
        }
        return event;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public class RootHandler extends SAXParser.Handler<Event> {
        RootHandler(Event event, SAXParser sAXParser) {
            super(event, sAXParser);
        }

        RootHandler(Event event) {
            super(event);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            String value;
            super.startElement(str, str2, str3, attributes);
            if (CONSTANTS.InstanceID.equals(str2) && (value = attributes.getValue(CONSTANTS.val.name())) != null) {
                InstanceID instanceID = new InstanceID(new UnsignedIntegerFourBytes(value));
                getInstance().getInstanceIDs().add(instanceID);
                new InstanceIDHandler(instanceID, this);
            }
        }
    }

    /* loaded from: classes5.dex */
    class InstanceIDHandler extends SAXParser.Handler<InstanceID> {
        InstanceIDHandler(InstanceID instanceID, SAXParser.Handler handler) {
            super(instanceID, handler);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            Map.Entry<String, String>[] entryArr = new Map.Entry[attributes.getLength()];
            for (int i = 0; i < entryArr.length; i++) {
                entryArr[i] = new AbstractMap.SimpleEntry(attributes.getLocalName(i), attributes.getValue(i));
            }
            try {
                EventedValue createValue = LastChangeParser.this.createValue(str2, entryArr);
                if (createValue != null) {
                    getInstance().getValues().add(createValue);
                }
            } catch (Exception e) {
                Logger logger = LastChangeParser.log;
                logger.warning("Error reading event XML, ignoring value: " + Exceptions.unwrap(e));
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            return CONSTANTS.InstanceID.equals(str2);
        }
    }

    public String generate(Event event) throws Exception {
        return XMLUtil.documentToFragmentString(buildDOM(event));
    }

    protected Document buildDOM(Event event) throws Exception {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        newInstance.setNamespaceAware(true);
        Document newDocument = newInstance.newDocumentBuilder().newDocument();
        generateRoot(event, newDocument);
        return newDocument;
    }

    protected void generateRoot(Event event, Document document) {
        Element createElementNS = document.createElementNS(getNamespace(), CONSTANTS.Event.name());
        document.appendChild(createElementNS);
        generateInstanceIDs(event, document, createElementNS);
    }

    protected void generateInstanceIDs(Event event, Document document, Element element) {
        for (InstanceID instanceID : event.getInstanceIDs()) {
            if (instanceID.getId() != null) {
                Element appendNewElement = XMLUtil.appendNewElement(document, element, CONSTANTS.InstanceID.name());
                appendNewElement.setAttribute(CONSTANTS.val.name(), instanceID.getId().toString());
                for (EventedValue eventedValue : instanceID.getValues()) {
                    generateEventedValue(eventedValue, document, appendNewElement);
                }
            }
        }
    }

    protected void generateEventedValue(EventedValue eventedValue, Document document, Element element) {
        String name = eventedValue.getName();
        Map.Entry<String, String>[] attributes = eventedValue.getAttributes();
        if (attributes != null && attributes.length > 0) {
            Element appendNewElement = XMLUtil.appendNewElement(document, element, name);
            for (Map.Entry<String, String> entry : attributes) {
                appendNewElement.setAttribute(entry.getKey(), DOMParser.escape(entry.getValue()));
            }
        }
    }
}
