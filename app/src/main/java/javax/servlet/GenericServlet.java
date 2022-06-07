package javax.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;

/* loaded from: classes5.dex */
public abstract class GenericServlet implements Serializable, Servlet, ServletConfig {
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);
    private transient ServletConfig config;

    @Override // javax.servlet.Servlet
    public void destroy() {
    }

    @Override // javax.servlet.Servlet
    public String getServletInfo() {
        return "";
    }

    public void init() throws ServletException {
    }

    @Override // javax.servlet.Servlet
    public abstract void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException;

    @Override // javax.servlet.ServletConfig
    public String getInitParameter(String str) {
        ServletConfig servletConfig = getServletConfig();
        if (servletConfig != null) {
            return servletConfig.getInitParameter(str);
        }
        throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
    }

    @Override // javax.servlet.ServletConfig
    public Enumeration<String> getInitParameterNames() {
        ServletConfig servletConfig = getServletConfig();
        if (servletConfig != null) {
            return servletConfig.getInitParameterNames();
        }
        throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
    }

    @Override // javax.servlet.Servlet
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override // javax.servlet.ServletConfig
    public ServletContext getServletContext() {
        ServletConfig servletConfig = getServletConfig();
        if (servletConfig != null) {
            return servletConfig.getServletContext();
        }
        throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
    }

    @Override // javax.servlet.Servlet
    public void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
        init();
    }

    public void log(String str) {
        ServletContext servletContext = getServletContext();
        servletContext.log(getServletName() + ": " + str);
    }

    public void log(String str, Throwable th) {
        ServletContext servletContext = getServletContext();
        servletContext.log(getServletName() + ": " + str, th);
    }

    @Override // javax.servlet.ServletConfig
    public String getServletName() {
        ServletConfig servletConfig = getServletConfig();
        if (servletConfig != null) {
            return servletConfig.getServletName();
        }
        throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
    }
}
