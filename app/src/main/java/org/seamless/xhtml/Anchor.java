package org.seamless.xhtml;

import javax.xml.xpath.XPath;
import org.seamless.xhtml.XHTML;
import org.w3c.dom.Element;

/* loaded from: classes4.dex */
public class Anchor extends XHTMLElement {
    public Anchor(XPath xPath, Element element) {
        super(xPath, element);
    }

    public String getType() {
        return getAttribute(XHTML.ATTR.type);
    }

    public Anchor setType(String str) {
        setAttribute(XHTML.ATTR.type, str);
        return this;
    }

    public Href getHref() {
        return Href.fromString(getAttribute(XHTML.ATTR.href));
    }

    public Anchor setHref(String str) {
        setAttribute(XHTML.ATTR.href, str);
        return this;
    }

    @Override // org.seamless.xml.DOMElement
    public String toString() {
        return "(Anchor) " + getAttribute(XHTML.ATTR.href);
    }
}
