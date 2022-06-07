package javax.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes5.dex */
public interface ServletRequest {
    AsyncContext getAsyncContext();

    Object getAttribute(String str);

    Enumeration<String> getAttributeNames();

    String getCharacterEncoding();

    int getContentLength();

    String getContentType();

    DispatcherType getDispatcherType();

    ServletInputStream getInputStream() throws IOException;

    String getLocalAddr();

    String getLocalName();

    int getLocalPort();

    Locale getLocale();

    Enumeration<Locale> getLocales();

    String getParameter(String str);

    Map<String, String[]> getParameterMap();

    Enumeration<String> getParameterNames();

    String[] getParameterValues(String str);

    String getProtocol();

    BufferedReader getReader() throws IOException;

    String getRealPath(String str);

    String getRemoteAddr();

    String getRemoteHost();

    int getRemotePort();

    RequestDispatcher getRequestDispatcher(String str);

    String getScheme();

    String getServerName();

    int getServerPort();

    ServletContext getServletContext();

    boolean isAsyncStarted();

    boolean isAsyncSupported();

    boolean isSecure();

    void removeAttribute(String str);

    void setAttribute(String str, Object obj);

    void setCharacterEncoding(String str) throws UnsupportedEncodingException;

    AsyncContext startAsync() throws IllegalStateException;

    AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException;
}
