package javax.servlet;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletRegistration;
import javax.servlet.descriptor.JspConfigDescriptor;

/* loaded from: classes5.dex */
public interface ServletContext {
    public static final String ORDERED_LIBS = "javax.servlet.context.orderedLibs";
    public static final String TEMPDIR = "javax.servlet.context.tempdir";

    FilterRegistration.Dynamic addFilter(String str, Class<? extends Filter> cls);

    FilterRegistration.Dynamic addFilter(String str, String str2);

    FilterRegistration.Dynamic addFilter(String str, Filter filter);

    void addListener(Class<? extends EventListener> cls);

    void addListener(String str);

    <T extends EventListener> void addListener(T t);

    ServletRegistration.Dynamic addServlet(String str, Class<? extends Servlet> cls);

    ServletRegistration.Dynamic addServlet(String str, String str2);

    ServletRegistration.Dynamic addServlet(String str, Servlet servlet);

    <T extends Filter> T createFilter(Class<T> cls) throws ServletException;

    <T extends EventListener> T createListener(Class<T> cls) throws ServletException;

    <T extends Servlet> T createServlet(Class<T> cls) throws ServletException;

    void declareRoles(String... strArr);

    Object getAttribute(String str);

    Enumeration<String> getAttributeNames();

    ClassLoader getClassLoader();

    ServletContext getContext(String str);

    String getContextPath();

    Set<SessionTrackingMode> getDefaultSessionTrackingModes();

    int getEffectiveMajorVersion();

    int getEffectiveMinorVersion();

    Set<SessionTrackingMode> getEffectiveSessionTrackingModes();

    FilterRegistration getFilterRegistration(String str);

    Map<String, ? extends FilterRegistration> getFilterRegistrations();

    String getInitParameter(String str);

    Enumeration<String> getInitParameterNames();

    JspConfigDescriptor getJspConfigDescriptor();

    int getMajorVersion();

    String getMimeType(String str);

    int getMinorVersion();

    RequestDispatcher getNamedDispatcher(String str);

    String getRealPath(String str);

    RequestDispatcher getRequestDispatcher(String str);

    URL getResource(String str) throws MalformedURLException;

    InputStream getResourceAsStream(String str);

    Set<String> getResourcePaths(String str);

    String getServerInfo();

    Servlet getServlet(String str) throws ServletException;

    String getServletContextName();

    Enumeration<String> getServletNames();

    ServletRegistration getServletRegistration(String str);

    Map<String, ? extends ServletRegistration> getServletRegistrations();

    Enumeration<Servlet> getServlets();

    SessionCookieConfig getSessionCookieConfig();

    void log(Exception exc, String str);

    void log(String str);

    void log(String str, Throwable th);

    void removeAttribute(String str);

    void setAttribute(String str, Object obj);

    boolean setInitParameter(String str, String str2);

    void setSessionTrackingModes(Set<SessionTrackingMode> set);
}
