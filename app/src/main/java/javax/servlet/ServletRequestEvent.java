package javax.servlet;

import java.util.EventObject;

/* loaded from: classes5.dex */
public class ServletRequestEvent extends EventObject {
    private ServletRequest request;

    public ServletRequestEvent(ServletContext servletContext, ServletRequest servletRequest) {
        super(servletContext);
        this.request = servletRequest;
    }

    public ServletRequest getServletRequest() {
        return this.request;
    }

    public ServletContext getServletContext() {
        return (ServletContext) super.getSource();
    }
}
