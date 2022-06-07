package org.eclipse.jetty.servlet.listener;

import java.beans.Introspector;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/* loaded from: classes5.dex */
public class IntrospectorCleaner implements ServletContextListener {
    @Override // javax.servlet.ServletContextListener
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override // javax.servlet.ServletContextListener
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Introspector.flushCaches();
    }
}
