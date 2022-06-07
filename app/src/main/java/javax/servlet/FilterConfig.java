package javax.servlet;

import java.util.Enumeration;

/* loaded from: classes5.dex */
public interface FilterConfig {
    String getFilterName();

    String getInitParameter(String str);

    Enumeration<String> getInitParameterNames();

    ServletContext getServletContext();
}
