package org.seamless.xhtml;

import javax.xml.xpath.XPath;
import org.seamless.xhtml.XHTML;
import org.w3c.dom.Element;

/* loaded from: classes4.dex */
public class Link extends XHTMLElement {
    public Link(XPath xPath, Element element) {
        super(xPath, element);
    }

    public Href getHref() {
        return Href.fromString(getAttribute(XHTML.ATTR.href));
    }

    public String getRel() {
        return getAttribute(XHTML.ATTR.rel);
    }

    public String getRev() {
        return getAttribute(XHTML.ATTR.rev);
    }
}
