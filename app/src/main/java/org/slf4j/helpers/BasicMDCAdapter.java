package org.slf4j.helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.spi.MDCAdapter;

/* loaded from: classes4.dex */
public class BasicMDCAdapter implements MDCAdapter {
    static boolean a = a();
    private InheritableThreadLocal<Map<String, String>> b = new InheritableThreadLocal<>();

    static boolean a() {
        try {
            return System.getProperty("java.version").startsWith("1.4");
        } catch (SecurityException unused) {
            return false;
        }
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void put(String str, String str2) {
        if (str != null) {
            Map<String, String> map = this.b.get();
            if (map == null) {
                map = Collections.synchronizedMap(new HashMap());
                this.b.set(map);
            }
            map.put(str, str2);
            return;
        }
        throw new IllegalArgumentException("key cannot be null");
    }

    @Override // org.slf4j.spi.MDCAdapter
    public String get(String str) {
        Map<String, String> map = this.b.get();
        if (map == null || str == null) {
            return null;
        }
        return map.get(str);
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void remove(String str) {
        Map<String, String> map = this.b.get();
        if (map != null) {
            map.remove(str);
        }
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void clear() {
        Map<String, String> map = this.b.get();
        if (map != null) {
            map.clear();
            if (a()) {
                this.b.set(null);
            } else {
                this.b.remove();
            }
        }
    }

    public Set<String> getKeys() {
        Map<String, String> map = this.b.get();
        if (map != null) {
            return map.keySet();
        }
        return null;
    }

    @Override // org.slf4j.spi.MDCAdapter
    public Map<String, String> getCopyOfContextMap() {
        Map<String, String> map = this.b.get();
        if (map == null) {
            return null;
        }
        Map<String, String> synchronizedMap = Collections.synchronizedMap(new HashMap());
        synchronized (map) {
            synchronizedMap.putAll(map);
        }
        return synchronizedMap;
    }

    @Override // org.slf4j.spi.MDCAdapter
    public void setContextMap(Map<String, String> map) {
        this.b.set(Collections.synchronizedMap(new HashMap(map)));
    }
}
