package javax.servlet.http;

import java.util.Enumeration;

/* loaded from: classes5.dex */
public interface HttpSessionContext {
    Enumeration<String> getIds();

    HttpSession getSession(String str);
}
