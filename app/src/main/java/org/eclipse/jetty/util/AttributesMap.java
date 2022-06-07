package org.eclipse.jetty.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class AttributesMap implements Attributes {
    protected final Map<String, Object> _map;

    public AttributesMap() {
        this._map = new HashMap();
    }

    public AttributesMap(Map<String, Object> map) {
        this._map = map;
    }

    public AttributesMap(AttributesMap attributesMap) {
        this._map = new HashMap(attributesMap._map);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void removeAttribute(String str) {
        this._map.remove(str);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void setAttribute(String str, Object obj) {
        if (obj == null) {
            this._map.remove(str);
        } else {
            this._map.put(str, obj);
        }
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Object getAttribute(String str) {
        return this._map.get(str);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(this._map.keySet());
    }

    public Set<String> getAttributeNameSet() {
        return this._map.keySet();
    }

    public Set<Map.Entry<String, Object>> getAttributeEntrySet() {
        return this._map.entrySet();
    }

    public static Enumeration<String> getAttributeNamesCopy(Attributes attributes) {
        if (attributes instanceof AttributesMap) {
            return Collections.enumeration(((AttributesMap) attributes)._map.keySet());
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Collections.list(attributes.getAttributeNames()));
        return Collections.enumeration(arrayList);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void clearAttributes() {
        this._map.clear();
    }

    public int size() {
        return this._map.size();
    }

    public String toString() {
        return this._map.toString();
    }

    public Set<String> keySet() {
        return this._map.keySet();
    }

    public void addAll(Attributes attributes) {
        Enumeration<String> attributeNames = attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            setAttribute(nextElement, attributes.getAttribute(nextElement));
        }
    }
}
