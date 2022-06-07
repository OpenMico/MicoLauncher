package com.zhangyue.we.anoprocesser.xml;

import com.zhangyue.we.anoprocesser.Util;
import com.zhangyue.we.view.View;
import java.io.File;
import java.util.Stack;
import javax.annotation.processing.Filer;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class LayoutReader {
    private Filer a;
    private SAXParser b;
    private String c;
    private String d;
    private String e;
    private String f;
    private File g;

    public LayoutReader(File file, String str, Filer filer, String str2, int i) {
        this.g = file;
        this.a = filer;
        this.f = str2;
        this.e = str;
        this.c = a(i, str);
    }

    public String parse() {
        try {
            this.b = SAXParserFactory.newInstance().newSAXParser();
            this.b.parse(this.g, new a());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a extends DefaultHandler {
        private Stack<View> b;
        private View c;
        private boolean d;

        private a() {
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startDocument() throws SAXException {
            super.startDocument();
            this.b = new Stack<>();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            View a = a(str3, attributes);
            if (a != null) {
                if (this.b.size() > 0) {
                    Stack<View> stack = this.b;
                    a.setParent(stack.get(stack.size() - 1));
                } else {
                    a.setParent(null);
                }
                this.b.push(a);
            }
            super.startElement(str, str2, str3, attributes);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            if (this.b.size() > 0) {
                View pop = this.b.pop();
                if (this.b.size() == 0) {
                    this.c = pop;
                    StringBuilder sb = new StringBuilder();
                    this.c.translate(sb);
                    sb.append("return ");
                    sb.append(this.c.getObjName());
                    LayoutWriter layoutWriter = new LayoutWriter(sb.toString(), LayoutReader.this.a, LayoutReader.this.c, LayoutReader.this.f, Util.getLayoutCategory(LayoutReader.this.g), LayoutReader.this.e, this.c.getImports());
                    LayoutReader.this.d = layoutWriter.write();
                }
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        private View a(String str, Attributes attributes) {
            if (str.equals("layout") || str.equals("data") || str.equals("variable") || str.equals("import")) {
                this.d = true;
                return null;
            }
            View view = new View(LayoutReader.this.f, str, attributes);
            if (this.b.size() == 0) {
                view.setDirName(Util.getDirName(LayoutReader.this.g));
                view.setIsDataBinding(this.d);
            }
            view.setLayoutName(LayoutReader.this.e);
            return view;
        }
    }

    private String a(int i, String str) {
        String[] split = (i + "_" + str).split("_");
        StringBuilder sb = new StringBuilder("X2C");
        for (int i2 = 0; i2 < split.length; i2++) {
            sb.append(split[i2].substring(0, 1).toUpperCase());
            sb.append(split[i2].substring(1));
            if (i2 < split.length - 1) {
                sb.append("_");
            }
        }
        return sb.toString();
    }
}
