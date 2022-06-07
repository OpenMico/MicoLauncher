package com.zhangyue.we.anoprocesser.xml;

import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.b;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class AttrReader {
    private File b;
    private SAXParser c;
    private HashMap<String, Func> d;
    private HashMap<String, Attr> a = new HashMap<>();
    private HashMap<String, String> e = new HashMap<>();

    public AttrReader(File file, HashMap<String, Func> hashMap) {
        this.b = a(file);
        this.d = hashMap;
        for (String str : this.d.keySet()) {
            this.e.put(str.substring(str.lastIndexOf(Constants.COLON_SEPARATOR) + 1), str);
        }
        try {
            this.c = SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File a(File file) {
        String str = File.separator;
        File[] listFiles = new File(file.getAbsolutePath() + str + "build" + str + "intermediates" + str + "incremental").listFiles(new FileFilter() { // from class: com.zhangyue.we.anoprocesser.xml.AttrReader.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                String name = file2.getName();
                return name.startsWith("merge") && name.endsWith("Resources");
            }
        });
        long j = 0;
        File file2 = null;
        for (File file3 : listFiles) {
            File file4 = new File(file3.getAbsolutePath() + str + "merged.dir" + str + "values" + str + "values.xml");
            if (file4.lastModified() > j) {
                j = file4.lastModified();
                file2 = file4;
            }
        }
        if (file2 != null) {
            return file2;
        }
        return new File(file.getAbsolutePath() + str + "src" + str + "main" + str + "res" + str + "values" + str + "attrs.xml");
    }

    public HashMap<String, Attr> parse() {
        try {
            this.c.parse(this.b, new a());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e2) {
            e2.printStackTrace();
        }
        return this.a;
    }

    /* loaded from: classes4.dex */
    private class a extends DefaultHandler {
        private Attr b;

        private a() {
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            String value = attributes.getValue("name");
            if (str3.equals("attr")) {
                if (AttrReader.this.e.containsKey(value)) {
                    this.b = new Attr();
                    this.b.name = (String) AttrReader.this.e.get(value);
                    this.b.format = attributes.getValue("format");
                    this.b.toFunc = (Func) AttrReader.this.d.get(AttrReader.this.e.get(value));
                    return;
                }
                this.b = null;
            } else if (str3.equals("enum") || str3.equals("flag")) {
                Attr attr = this.b;
                if (attr != null) {
                    attr.enums.put(value, attributes.getValue(b.p));
                }
            } else {
                this.b = null;
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            if (str3.equals("attr")) {
                if (this.b != null) {
                    if (this.b.compareTo((Attr) AttrReader.this.a.get(this.b.name)) > 0) {
                        AttrReader.this.a.put(this.b.name, this.b);
                    }
                }
                this.b = null;
            }
        }
    }
}
