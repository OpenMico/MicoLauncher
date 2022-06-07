package org.apache.commons.logging.impl;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.LogFactory;

/* loaded from: classes5.dex */
public class ServletContextCleaner implements ServletContextListener {
    static Class a;
    private static final Class[] b;

    @Override // javax.servlet.ServletContextListener
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    static {
        Class[] clsArr = new Class[1];
        Class cls = a;
        if (cls == null) {
            cls = a("java.lang.ClassLoader");
            a = cls;
        }
        clsArr[0] = cls;
        b = clsArr;
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // javax.servlet.ServletContextListener
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Object[] objArr = {contextClassLoader};
        ClassLoader classLoader = contextClassLoader;
        while (classLoader != null) {
            try {
                Class<?> loadClass = classLoader.loadClass(LogFactory.FACTORY_PROPERTY);
                loadClass.getMethod("release", b).invoke(null, objArr);
                classLoader = loadClass.getClassLoader().getParent();
            } catch (ClassNotFoundException unused) {
                classLoader = null;
            } catch (IllegalAccessException unused2) {
                System.err.println("LogFactory instance found which is not accessable!");
                classLoader = null;
            } catch (NoSuchMethodException unused3) {
                System.err.println("LogFactory instance found which does not support release method!");
                classLoader = null;
            } catch (InvocationTargetException unused4) {
                System.err.println("LogFactory instance release method failed!");
                classLoader = null;
            }
        }
        LogFactory.release(contextClassLoader);
    }
}
