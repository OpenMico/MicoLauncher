package org.eclipse.jetty.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes5.dex */
public class Loader {
    public static URL getResource(Class<?> cls, String str, boolean z) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        URL url = null;
        while (url == null && contextClassLoader != null) {
            url = contextClassLoader.getResource(str);
            contextClassLoader = (url != null || !z) ? null : contextClassLoader.getParent();
        }
        ClassLoader classLoader = cls == null ? null : cls.getClassLoader();
        while (url == null && classLoader != null) {
            url = classLoader.getResource(str);
            classLoader = (url != null || !z) ? null : classLoader.getParent();
        }
        return url == null ? ClassLoader.getSystemResource(str) : url;
    }

    public static Class loadClass(Class cls, String str) throws ClassNotFoundException {
        return loadClass(cls, str, false);
    }

    public static Class loadClass(Class cls, String str, boolean z) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class<?> cls2 = null;
        ClassNotFoundException e = null;
        while (cls2 == null && contextClassLoader != null) {
            try {
                cls2 = contextClassLoader.loadClass(str);
            } catch (ClassNotFoundException e2) {
                e = e2;
                if (e == null) {
                }
            }
            contextClassLoader = (cls2 != null || !z) ? null : contextClassLoader.getParent();
        }
        ClassLoader classLoader = cls == null ? null : cls.getClassLoader();
        while (cls2 == null && classLoader != null) {
            try {
                cls2 = classLoader.loadClass(str);
            } catch (ClassNotFoundException e3) {
                e = e3;
                if (e == null) {
                }
            }
            classLoader = (cls2 != null || !z) ? null : classLoader.getParent();
        }
        if (cls2 == null) {
            try {
                cls2 = Class.forName(str);
            } catch (ClassNotFoundException e4) {
                e = e4;
                if (e == null) {
                }
            }
        }
        if (cls2 != null) {
            return cls2;
        }
        throw e;
    }

    public static ResourceBundle getResourceBundle(Class<?> cls, String str, boolean z, Locale locale) throws MissingResourceException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        ResourceBundle resourceBundle = null;
        MissingResourceException e = null;
        while (resourceBundle == null && contextClassLoader != null) {
            try {
                resourceBundle = ResourceBundle.getBundle(str, locale, contextClassLoader);
            } catch (MissingResourceException e2) {
                e = e2;
                if (e == null) {
                }
            }
            contextClassLoader = (resourceBundle != null || !z) ? null : contextClassLoader.getParent();
        }
        ClassLoader classLoader = cls == null ? null : cls.getClassLoader();
        while (resourceBundle == null && classLoader != null) {
            try {
                resourceBundle = ResourceBundle.getBundle(str, locale, classLoader);
            } catch (MissingResourceException e3) {
                e = e3;
                if (e == null) {
                }
            }
            classLoader = (resourceBundle != null || !z) ? null : classLoader.getParent();
        }
        if (resourceBundle == null) {
            try {
                resourceBundle = ResourceBundle.getBundle(str, locale);
            } catch (MissingResourceException e4) {
                e = e4;
                if (e == null) {
                }
            }
        }
        if (resourceBundle != null) {
            return resourceBundle;
        }
        throw e;
    }

    public static String getClassPath(ClassLoader classLoader) throws Exception {
        StringBuilder sb = new StringBuilder();
        while (classLoader != null && (classLoader instanceof URLClassLoader)) {
            URL[] uRLs = ((URLClassLoader) classLoader).getURLs();
            if (uRLs != null) {
                for (URL url : uRLs) {
                    File file = Resource.newResource(url).getFile();
                    if (file != null && file.exists()) {
                        if (sb.length() > 0) {
                            sb.append(File.pathSeparatorChar);
                        }
                        sb.append(file.getAbsolutePath());
                    }
                }
            }
            classLoader = classLoader.getParent();
        }
        return sb.toString();
    }
}
