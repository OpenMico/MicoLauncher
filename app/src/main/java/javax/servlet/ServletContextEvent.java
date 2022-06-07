package javax.servlet;

import java.util.EventObject;

/* loaded from: classes5.dex */
public class ServletContextEvent extends EventObject {
    public ServletContextEvent(ServletContext servletContext) {
        super(servletContext);
    }

    public ServletContext getServletContext() {
        return (ServletContext) super.getSource();
    }
}
