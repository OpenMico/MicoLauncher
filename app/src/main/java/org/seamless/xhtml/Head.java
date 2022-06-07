package org.seamless.xhtml;

import javax.xml.xpath.XPath;
import org.seamless.xhtml.XHTML;
import org.seamless.xml.DOMElement;
import org.w3c.dom.Element;

/* loaded from: classes4.dex */
public class Head extends XHTMLElement {
    public Head(XPath xPath, Element element) {
        super(xPath, element);
    }

    public XHTMLElement getHeadTitle() {
        return (XHTMLElement) this.CHILD_BUILDER.firstChildOrNull(XHTML.ELEMENT.title.name());
    }

    public Link[] getLinks() {
        return new DOMElement<XHTMLElement, XHTMLElement>.ArrayBuilder(this) { // from class: org.seamless.xhtml.Head.1
            @Override // org.seamless.xml.DOMElement.Builder
            public Link build(Element element) {
                return new Link(Head.this.getXpath(), element);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.seamless.xml.DOMElement.ArrayBuilder
            public Link[] newChildrenArray(int i) {
                return new Link[i];
            }
        }.getChildElements(XHTML.ELEMENT.link.name());
    }

    public Meta[] getMetas() {
        return new DOMElement<XHTMLElement, XHTMLElement>.ArrayBuilder(this) { // from class: org.seamless.xhtml.Head.2
            @Override // org.seamless.xml.DOMElement.Builder
            public Meta build(Element element) {
                return new Meta(Head.this.getXpath(), element);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.seamless.xml.DOMElement.ArrayBuilder
            public Meta[] newChildrenArray(int i) {
                return new Meta[i];
            }
        }.getChildElements(XHTML.ELEMENT.meta.name());
    }

    public XHTMLElement[] getDocumentStyles() {
        return (XHTMLElement[]) this.CHILD_BUILDER.getChildElements(XHTML.ELEMENT.style.name());
    }

    public XHTMLElement[] getScripts() {
        return (XHTMLElement[]) this.CHILD_BUILDER.getChildElements(XHTML.ELEMENT.script.name());
    }
}
