package org.eclipse.jetty.server.handler.jmx;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Attributes;

/* loaded from: classes5.dex */
public class ContextHandlerMBean extends AbstractHandlerMBean {
    public ContextHandlerMBean(Object obj) {
        super(obj);
    }

    public Map getContextAttributes() {
        HashMap hashMap = new HashMap();
        Attributes attributes = ((ContextHandler) this._managed).getAttributes();
        Enumeration<String> attributeNames = attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            hashMap.put(nextElement, attributes.getAttribute(nextElement));
        }
        return hashMap;
    }

    public void setContextAttribute(String str, Object obj) {
        ((ContextHandler) this._managed).getAttributes().setAttribute(str, obj);
    }

    public void setContextAttribute(String str, String str2) {
        ((ContextHandler) this._managed).getAttributes().setAttribute(str, str2);
    }

    public void removeContextAttribute(String str) {
        ((ContextHandler) this._managed).getAttributes().removeAttribute(str);
    }
}
