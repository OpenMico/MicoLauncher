package org.seamless.xhtml;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import org.seamless.xml.DOM;
import org.w3c.dom.Document;

/* loaded from: classes4.dex */
public class XHTML extends DOM {
    public static final String NAMESPACE_URI = "http://www.w3.org/1999/xhtml";
    public static final String SCHEMA_RESOURCE = "org/seamless/schemas/xhtml1-strict.xsd";

    /* loaded from: classes4.dex */
    public enum ATTR {
        id,
        style,
        title,
        type,
        href,
        name,
        content,
        scheme,
        rel,
        rev,
        colspan,
        rowspan,
        src,
        alt,
        action,
        method;
        
        public static final String CLASS = "class";
    }

    /* loaded from: classes4.dex */
    public enum ELEMENT {
        html,
        head,
        title,
        meta,
        link,
        script,
        style,
        body,
        div,
        span,
        p,
        object,
        a,
        img,
        pre,
        h1,
        h2,
        h3,
        h4,
        h5,
        h6,
        table,
        thead,
        tfoot,
        tbody,
        tr,
        th,
        td,
        ul,
        ol,
        li,
        dl,
        dt,
        dd,
        form,
        input,
        select,
        option
    }

    @Override // org.seamless.xml.DOM
    public String getRootElementNamespace() {
        return NAMESPACE_URI;
    }

    public static Source[] createSchemaSources() {
        return new Source[]{new StreamSource(XHTML.class.getClassLoader().getResourceAsStream(SCHEMA_RESOURCE))};
    }

    public XHTML(Document document) {
        super(document);
    }

    public Root createRoot(XPath xPath, ELEMENT element) {
        super.createRoot(element.name());
        return getRoot(xPath);
    }

    @Override // org.seamless.xml.DOM
    public Root getRoot(XPath xPath) {
        return new Root(xPath, getW3CDocument().getDocumentElement());
    }

    @Override // org.seamless.xml.DOM
    public XHTML copy() {
        return new XHTML((Document) getW3CDocument().cloneNode(true));
    }
}
