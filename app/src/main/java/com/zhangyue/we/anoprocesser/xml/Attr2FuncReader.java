package com.zhangyue.we.anoprocesser.xml;

import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class Attr2FuncReader {
    private File a;
    private SAXParser b;
    private HashMap<String, Func> c = new HashMap<>();

    public Attr2FuncReader(File file) {
        this.a = file;
    }

    public HashMap<String, Attr> parse() {
        try {
            this.b = SAXParserFactory.newInstance().newSAXParser();
            if (this.a.exists()) {
                this.b.parse(this.a, new a());
                return new AttrReader(this.a.getParentFile(), this.c).parse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a extends DefaultHandler {
        private String b;
        private String c;

        private a() {
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            this.b = attributes.getValue("name");
            this.c = attributes.getValue("toFunc");
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            Attr2FuncReader.this.c.put(this.b, new Func(this.c.trim()));
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endDocument() throws SAXException {
            super.endDocument();
            Attr2FuncReader.this.b.reset();
        }
    }
}
