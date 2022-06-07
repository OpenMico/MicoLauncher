package org.seamless.xml;

import java.net.URI;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/* loaded from: classes4.dex */
public abstract class DOM {
    public static final String CDATA_BEGIN = "<![CDATA[";
    public static final String CDATA_END = "]]>";
    public static final URI XML_SCHEMA_NAMESPACE = URI.create("http://www.w3.org/2001/xml.xsd");
    private Document dom;

    public abstract DOM copy();

    public abstract DOMElement getRoot(XPath xPath);

    public abstract String getRootElementNamespace();

    public DOM(Document document) {
        this.dom = document;
    }

    public Document getW3CDocument() {
        return this.dom;
    }

    public Element createRoot(String str) {
        Element createElementNS = getW3CDocument().createElementNS(getRootElementNamespace(), str);
        getW3CDocument().appendChild(createElementNS);
        return createElementNS;
    }
}
