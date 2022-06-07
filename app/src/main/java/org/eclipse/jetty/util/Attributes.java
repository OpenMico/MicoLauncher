package org.eclipse.jetty.util;

import java.util.Enumeration;

/* loaded from: classes5.dex */
public interface Attributes {
    void clearAttributes();

    Object getAttribute(String str);

    Enumeration<String> getAttributeNames();

    void removeAttribute(String str);

    void setAttribute(String str, Object obj);
}
