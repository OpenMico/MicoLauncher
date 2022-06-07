package javax.servlet;

import java.util.EventListener;

/* loaded from: classes5.dex */
public interface ServletContextListener extends EventListener {
    void contextDestroyed(ServletContextEvent servletContextEvent);

    void contextInitialized(ServletContextEvent servletContextEvent);
}
