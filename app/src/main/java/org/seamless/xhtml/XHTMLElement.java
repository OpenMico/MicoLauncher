package org.seamless.xhtml;

import java.util.ArrayList;
import javax.xml.xpath.XPath;
import org.apache.commons.lang3.StringUtils;
import org.seamless.xhtml.XHTML;
import org.seamless.xml.DOMElement;
import org.w3c.dom.Element;

/* loaded from: classes4.dex */
public class XHTMLElement extends DOMElement<XHTMLElement, XHTMLElement> {
    public static final String XPATH_PREFIX = "h";

    public XHTMLElement(XPath xPath, Element element) {
        super(xPath, element);
    }

    @Override // org.seamless.xml.DOMElement
    protected DOMElement<XHTMLElement, XHTMLElement>.Builder createParentBuilder(DOMElement dOMElement) {
        return new DOMElement<XHTMLElement, XHTMLElement>.Builder(dOMElement) { // from class: org.seamless.xhtml.XHTMLElement.1
            @Override // org.seamless.xml.DOMElement.Builder
            public XHTMLElement build(Element element) {
                return new XHTMLElement(XHTMLElement.this.getXpath(), element);
            }
        };
    }

    @Override // org.seamless.xml.DOMElement
    protected DOMElement<XHTMLElement, XHTMLElement>.ArrayBuilder createChildBuilder(DOMElement dOMElement) {
        return new DOMElement<XHTMLElement, XHTMLElement>.ArrayBuilder(dOMElement) { // from class: org.seamless.xhtml.XHTMLElement.2
            @Override // org.seamless.xml.DOMElement.ArrayBuilder
            public XHTMLElement[] newChildrenArray(int i) {
                return new XHTMLElement[i];
            }

            @Override // org.seamless.xml.DOMElement.Builder
            public XHTMLElement build(Element element) {
                return new XHTMLElement(XHTMLElement.this.getXpath(), element);
            }
        };
    }

    @Override // org.seamless.xml.DOMElement
    protected String prefix(String str) {
        return "h:" + str;
    }

    public XHTML.ELEMENT getConstant() {
        return XHTML.ELEMENT.valueOf(getElementName());
    }

    public XHTMLElement[] getChildren(XHTML.ELEMENT element) {
        return (XHTMLElement[]) super.getChildren(element.name());
    }

    public XHTMLElement getFirstChild(XHTML.ELEMENT element) {
        return (XHTMLElement) super.getFirstChild(element.name());
    }

    public XHTMLElement[] findChildren(XHTML.ELEMENT element) {
        return (XHTMLElement[]) super.findChildren(element.name());
    }

    public XHTMLElement createChild(XHTML.ELEMENT element) {
        return (XHTMLElement) super.createChild(element.name(), XHTML.NAMESPACE_URI);
    }

    public String getAttribute(XHTML.ATTR attr) {
        return getAttribute(attr.name());
    }

    public XHTMLElement setAttribute(XHTML.ATTR attr, String str) {
        super.setAttribute(attr.name(), str);
        return this;
    }

    public String getId() {
        return getAttribute(XHTML.ATTR.id);
    }

    public XHTMLElement setId(String str) {
        setAttribute(XHTML.ATTR.id, str);
        return this;
    }

    public String getTitle() {
        return getAttribute(XHTML.ATTR.title);
    }

    public XHTMLElement setTitle(String str) {
        setAttribute(XHTML.ATTR.title, str);
        return this;
    }

    public XHTMLElement setClasses(String str) {
        setAttribute("class", str);
        return this;
    }

    public XHTMLElement setClasses(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            sb.append(strArr[i]);
            if (i != strArr.length - 1) {
                sb.append(StringUtils.SPACE);
            }
        }
        setAttribute("class", sb.toString());
        return this;
    }

    public String[] getClasses() {
        String attribute = getAttribute("class");
        if (attribute == null) {
            return new String[0];
        }
        return attribute.split(StringUtils.SPACE);
    }

    public Option[] getOptions() {
        return Option.fromString(getAttribute(XHTML.ATTR.style));
    }

    public Option getOption(String str) {
        Option[] options = getOptions();
        for (Option option : options) {
            if (option.getKey().equals(str)) {
                return option;
            }
        }
        return null;
    }

    public Anchor[] findAllAnchors() {
        return findAllAnchors(null, null);
    }

    public Anchor[] findAllAnchors(String str) {
        return findAllAnchors(str, null);
    }

    public Anchor[] findAllAnchors(String str, String str2) {
        XHTMLElement[] findChildrenWithClass = findChildrenWithClass(XHTML.ELEMENT.a, str2);
        ArrayList arrayList = new ArrayList(findChildrenWithClass.length);
        for (XHTMLElement xHTMLElement : findChildrenWithClass) {
            String attribute = xHTMLElement.getAttribute(XHTML.ATTR.href);
            if (str == null || (attribute != null && attribute.startsWith(str))) {
                arrayList.add(new Anchor(getXpath(), xHTMLElement.getW3CElement()));
            }
        }
        return (Anchor[]) arrayList.toArray(new Anchor[arrayList.size()]);
    }

    public XHTMLElement[] findChildrenWithClass(XHTML.ELEMENT element, String str) {
        ArrayList arrayList = new ArrayList();
        XHTMLElement[] findChildren = findChildren(element);
        for (XHTMLElement xHTMLElement : findChildren) {
            if (str == null) {
                arrayList.add(xHTMLElement);
            } else {
                String[] classes = xHTMLElement.getClasses();
                int length = classes.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (classes[i].matches(str)) {
                        arrayList.add(xHTMLElement);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        return (XHTMLElement[]) arrayList.toArray(this.CHILD_BUILDER.newChildrenArray(arrayList.size()));
    }

    @Override // org.seamless.xml.DOMElement
    /* renamed from: setContent */
    public DOMElement<XHTMLElement, XHTMLElement> setContent2(String str) {
        super.setContent(str);
        return this;
    }

    @Override // org.seamless.xml.DOMElement
    public XHTMLElement setAttribute(String str, String str2) {
        super.setAttribute(str, str2);
        return this;
    }
}
