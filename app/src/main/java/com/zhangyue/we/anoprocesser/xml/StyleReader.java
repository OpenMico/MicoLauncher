package com.zhangyue.we.anoprocesser.xml;

import com.xiaomi.idm.service.iot.LightService;
import com.zhangyue.we.anoprocesser.FileFilter;
import com.zhangyue.we.anoprocesser.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class StyleReader {
    private HashMap<String, Style> a;
    private File b;
    private SAXParser c;

    public StyleReader(File file, HashMap<String, Style> hashMap) {
        this.b = file;
        this.a = hashMap;
        try {
            this.c = SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse() {
        HashMap<String, ArrayList<File>> a2 = a(this.b);
        Log.w("parse " + this.b.getAbsolutePath());
        for (ArrayList<File> arrayList : a2.values()) {
            Iterator<File> it = arrayList.iterator();
            while (it.hasNext()) {
                File next = it.next();
                try {
                    Log.w("style " + next.getAbsolutePath());
                    this.c.parse(next, new a());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private HashMap<String, ArrayList<File>> a(File file) {
        return new FileFilter(file).include("values").fileStart("style").exclude("layout").exclude("build").exclude("java").exclude("libs").exclude("mipmap").exclude("drawable").exclude("anim").exclude(LightService.LightPropertyCommand.COLOR).exclude("menu").exclude("raw").exclude("xml").filter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a extends DefaultHandler {
        private Style b;
        private String c;
        private String d;

        private a() {
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            this.c = attributes.getValue("name");
            if (str3.equals("style")) {
                this.b = new Style();
                Style style = this.b;
                style.name = this.c;
                style.parent = attributes.getValue("parent");
                if (this.b.parent == null && this.c.contains(".")) {
                    Style style2 = this.b;
                    String str4 = this.c;
                    style2.parent = str4.substring(0, str4.lastIndexOf("."));
                }
            } else if (str3.equals("item")) {
                this.b.attribute.put(this.c, str2);
            }
            this.d = str3;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void characters(char[] cArr, int i, int i2) throws SAXException {
            super.characters(cArr, i, i2);
            if (this.d != null) {
                String str = "";
                try {
                    str = new String(cArr, i, i2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Style style = this.b;
                if (style != null) {
                    style.attribute.put(this.c, str);
                }
            }
            this.d = null;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            if (str3.equals("style")) {
                StyleReader.this.a.put(this.b.name, this.b);
                this.b = null;
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }
}
