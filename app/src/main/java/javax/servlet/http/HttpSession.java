package javax.servlet.http;

import java.util.Enumeration;
import javax.servlet.ServletContext;

/* loaded from: classes5.dex */
public interface HttpSession {
    Object getAttribute(String str);

    Enumeration<String> getAttributeNames();

    long getCreationTime();

    String getId();

    long getLastAccessedTime();

    int getMaxInactiveInterval();

    ServletContext getServletContext();

    HttpSessionContext getSessionContext();

    Object getValue(String str);

    String[] getValueNames();

    void invalidate();

    boolean isNew();

    void putValue(String str, Object obj);

    void removeAttribute(String str);

    void removeValue(String str);

    void setAttribute(String str, Object obj);

    void setMaxInactiveInterval(int i);
}
