package org.fourthline.cling.support.messagebox.parser;

import javax.xml.xpath.XPath;
import org.seamless.xml.DOMElement;
import org.w3c.dom.Element;

/* loaded from: classes5.dex */
public class MessageElement extends DOMElement<MessageElement, MessageElement> {
    public static final String XPATH_PREFIX = "m";

    public MessageElement(XPath xPath, Element element) {
        super(xPath, element);
    }

    @Override // org.seamless.xml.DOMElement
    protected String prefix(String str) {
        return "m:" + str;
    }

    @Override // org.seamless.xml.DOMElement
    protected DOMElement<MessageElement, MessageElement>.Builder createParentBuilder(DOMElement dOMElement) {
        return new DOMElement<MessageElement, MessageElement>.Builder(dOMElement) { // from class: org.fourthline.cling.support.messagebox.parser.MessageElement.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.seamless.xml.DOMElement.Builder
            public MessageElement build(Element element) {
                return new MessageElement(MessageElement.this.getXpath(), element);
            }
        };
    }

    @Override // org.seamless.xml.DOMElement
    protected DOMElement<MessageElement, MessageElement>.ArrayBuilder createChildBuilder(DOMElement dOMElement) {
        return new DOMElement<MessageElement, MessageElement>.ArrayBuilder(dOMElement) { // from class: org.fourthline.cling.support.messagebox.parser.MessageElement.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.seamless.xml.DOMElement.ArrayBuilder
            public MessageElement[] newChildrenArray(int i) {
                return new MessageElement[i];
            }

            @Override // org.seamless.xml.DOMElement.Builder
            public MessageElement build(Element element) {
                return new MessageElement(MessageElement.this.getXpath(), element);
            }
        };
    }
}
