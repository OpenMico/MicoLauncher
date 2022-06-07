package org.seamless.xml;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class SAXParser {
    private final XMLReader xr;
    private static final Logger log = Logger.getLogger(SAXParser.class.getName());
    public static final URI XML_SCHEMA_NAMESPACE = URI.create("http://www.w3.org/2001/xml.xsd");
    public static final URL XML_SCHEMA_RESOURCE = Thread.currentThread().getContextClassLoader().getResource("org/seamless/schemas/xml.xsd");

    protected Source[] getSchemaSources() {
        return null;
    }

    public SAXParser() {
        this(null);
    }

    public SAXParser(DefaultHandler defaultHandler) {
        this.xr = create();
        if (defaultHandler != null) {
            this.xr.setContentHandler(defaultHandler);
        }
    }

    public void setContentHandler(ContentHandler contentHandler) {
        this.xr.setContentHandler(contentHandler);
    }

    protected XMLReader create() {
        try {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setFeature("http://xml.org/sax/features/external-general-entities", false);
            newInstance.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            newInstance.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            newInstance.setXIncludeAware(false);
            newInstance.setNamespaceAware(true);
            if (getSchemaSources() != null) {
                newInstance.setSchema(createSchema(getSchemaSources()));
            }
            XMLReader xMLReader = newInstance.newSAXParser().getXMLReader();
            xMLReader.setErrorHandler(getErrorHandler());
            return xMLReader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Schema createSchema(Source[] sourceArr) {
        try {
            SchemaFactory newInstance = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            newInstance.setResourceResolver(new CatalogResourceResolver(new HashMap<URI, URL>() { // from class: org.seamless.xml.SAXParser.1
                {
                    put(SAXParser.XML_SCHEMA_NAMESPACE, SAXParser.XML_SCHEMA_RESOURCE);
                }
            }));
            return newInstance.newSchema(sourceArr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ErrorHandler getErrorHandler() {
        return new SimpleErrorHandler();
    }

    public void parse(InputSource inputSource) throws ParserException {
        try {
            this.xr.parse(inputSource);
        } catch (Exception e) {
            throw new ParserException(e);
        }
    }

    /* loaded from: classes4.dex */
    public class SimpleErrorHandler implements ErrorHandler {
        public SimpleErrorHandler() {
        }

        @Override // org.xml.sax.ErrorHandler
        public void warning(SAXParseException sAXParseException) throws SAXException {
            throw new SAXException(sAXParseException);
        }

        @Override // org.xml.sax.ErrorHandler
        public void error(SAXParseException sAXParseException) throws SAXException {
            throw new SAXException(sAXParseException);
        }

        @Override // org.xml.sax.ErrorHandler
        public void fatalError(SAXParseException sAXParseException) throws SAXException {
            throw new SAXException(sAXParseException);
        }
    }

    /* loaded from: classes4.dex */
    public static class Handler<I> extends DefaultHandler {
        protected Attributes attributes;
        protected StringBuilder characters;
        protected I instance;
        protected Handler parent;
        protected SAXParser parser;

        protected boolean isLastElement(String str, String str2, String str3) {
            return false;
        }

        public Handler(I i) {
            this(i, null, null);
        }

        public Handler(I i, SAXParser sAXParser) {
            this(i, sAXParser, null);
        }

        public Handler(I i, Handler handler) {
            this(i, handler.getParser(), handler);
        }

        public Handler(I i, SAXParser sAXParser, Handler handler) {
            this.characters = new StringBuilder();
            this.instance = i;
            this.parser = sAXParser;
            this.parent = handler;
            if (sAXParser != null) {
                sAXParser.setContentHandler(this);
            }
        }

        public I getInstance() {
            return this.instance;
        }

        public SAXParser getParser() {
            return this.parser;
        }

        public Handler getParent() {
            return this.parent;
        }

        protected void switchToParent() {
            Handler handler;
            SAXParser sAXParser = this.parser;
            if (sAXParser != null && (handler = this.parent) != null) {
                sAXParser.setContentHandler(handler);
                this.attributes = null;
            }
        }

        public String getCharacters() {
            return this.characters.toString();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            this.characters = new StringBuilder();
            this.attributes = new AttributesImpl(attributes);
            Logger logger = SAXParser.log;
            logger.finer(getClass().getSimpleName() + " starting: " + str2);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void characters(char[] cArr, int i, int i2) throws SAXException {
            this.characters.append(cArr, i, i2);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            if (isLastElement(str, str2, str3)) {
                Logger logger = SAXParser.log;
                logger.finer(getClass().getSimpleName() + ": last element, switching to parent: " + str2);
                switchToParent();
                return;
            }
            Logger logger2 = SAXParser.log;
            logger2.finer(getClass().getSimpleName() + " ending: " + str2);
        }

        protected Attributes getAttributes() {
            return this.attributes;
        }
    }
}
