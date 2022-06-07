package org.fourthline.cling.model;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes5.dex */
public class XMLUtil {
    public static String documentToString(Document document) throws Exception {
        return documentToString(document, true);
    }

    public static String documentToString(Document document, boolean z) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"");
        sb.append(z ? "yes" : "no");
        sb.append("\"?>");
        String sb2 = sb.toString();
        return sb2 + nodeToString(document.getDocumentElement(), new HashSet(), document.getDocumentElement().getNamespaceURI());
    }

    public static String documentToFragmentString(Document document) throws Exception {
        return nodeToString(document.getDocumentElement(), new HashSet(), document.getDocumentElement().getNamespaceURI());
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected static String nodeToString(Node node, Set<String> set, String str) throws Exception {
        boolean z;
        StringBuilder sb = new StringBuilder();
        if (node == null) {
            return "";
        }
        if (node instanceof Element) {
            Element element = (Element) node;
            sb.append("<");
            sb.append(element.getNodeName());
            HashMap hashMap = new HashMap();
            if (element.getPrefix() != null && !set.contains(element.getPrefix())) {
                hashMap.put(element.getPrefix(), element.getNamespaceURI());
            }
            if (element.hasAttributes()) {
                NamedNodeMap attributes = element.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node item = attributes.item(i);
                    if (!item.getNodeName().startsWith("xmlns")) {
                        if (item.getPrefix() != null && !set.contains(item.getPrefix())) {
                            hashMap.put(item.getPrefix(), element.getNamespaceURI());
                        }
                        sb.append(StringUtils.SPACE);
                        sb.append(item.getNodeName());
                        sb.append("=\"");
                        sb.append(item.getNodeValue());
                        sb.append("\"");
                    }
                }
            }
            if (str != null && !hashMap.containsValue(str) && !str.equals(element.getParentNode().getNamespaceURI())) {
                sb.append(" xmlns=\"");
                sb.append(str);
                sb.append("\"");
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                sb.append(" xmlns:");
                sb.append((String) entry.getKey());
                sb.append("=\"");
                sb.append((String) entry.getValue());
                sb.append("\"");
                set.add(entry.getKey());
            }
            NodeList childNodes = element.getChildNodes();
            int i2 = 0;
            while (true) {
                if (i2 >= childNodes.getLength()) {
                    z = true;
                    break;
                } else if (childNodes.item(i2).getNodeType() != 2) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (!z) {
                sb.append(">");
                for (int i3 = 0; i3 < childNodes.getLength(); i3++) {
                    sb.append(nodeToString(childNodes.item(i3), set, childNodes.item(i3).getNamespaceURI()));
                }
                sb.append("</");
                sb.append(element.getNodeName());
                sb.append(">");
            } else {
                sb.append("/>");
            }
            for (String str2 : hashMap.keySet()) {
                set.remove(str2);
            }
        } else if (node.getNodeValue() != null) {
            sb.append(encodeText(node.getNodeValue(), node instanceof Attr));
        }
        return sb.toString();
    }

    public static String encodeText(String str) {
        return encodeText(str, true);
    }

    public static String encodeText(String str, boolean z) {
        String replaceAll = str.replaceAll(MusicGroupListActivity.SPECIAL_SYMBOL, "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        return z ? replaceAll.replaceAll(LrcRow.SINGLE_QUOTE, LrcRow.APOS).replaceAll("\"", "&quot;") : replaceAll;
    }

    public static Element appendNewElement(Document document, Element element, Enum r2) {
        return appendNewElement(document, element, r2.toString());
    }

    public static Element appendNewElement(Document document, Element element, String str) {
        Element createElement = document.createElement(str);
        element.appendChild(createElement);
        return createElement;
    }

    public static Element appendNewElementIfNotNull(Document document, Element element, Enum r3, Object obj) {
        return appendNewElementIfNotNull(document, element, r3, obj, (String) null);
    }

    public static Element appendNewElementIfNotNull(Document document, Element element, Enum r2, Object obj, String str) {
        return appendNewElementIfNotNull(document, element, r2.toString(), obj, str);
    }

    public static Element appendNewElementIfNotNull(Document document, Element element, String str, Object obj) {
        return appendNewElementIfNotNull(document, element, str, obj, (String) null);
    }

    public static Element appendNewElementIfNotNull(Document document, Element element, String str, Object obj, String str2) {
        return obj == null ? element : appendNewElement(document, element, str, obj, str2);
    }

    public static Element appendNewElement(Document document, Element element, String str, Object obj) {
        return appendNewElement(document, element, str, obj, null);
    }

    public static Element appendNewElement(Document document, Element element, String str, Object obj, String str2) {
        Element element2;
        if (str2 != null) {
            element2 = document.createElementNS(str2, str);
        } else {
            element2 = document.createElement(str);
        }
        if (obj != null) {
            element2.appendChild(document.createTextNode(obj.toString()));
        }
        element.appendChild(element2);
        return element2;
    }

    public static String getTextContent(Node node) {
        StringBuffer stringBuffer = new StringBuffer();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 3) {
                stringBuffer.append(item.getNodeValue());
            }
        }
        return stringBuffer.toString();
    }
}
