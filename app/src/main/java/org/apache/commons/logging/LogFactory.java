package org.apache.commons.logging;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/* loaded from: classes5.dex */
public abstract class LogFactory {
    public static final String DIAGNOSTICS_DEST_PROPERTY = "org.apache.commons.logging.diagnostics.dest";
    public static final String FACTORY_DEFAULT = "org.apache.commons.logging.impl.LogFactoryImpl";
    public static final String FACTORY_PROPERTIES = "commons-logging.properties";
    public static final String FACTORY_PROPERTY = "org.apache.commons.logging.LogFactory";
    public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";
    public static final String PRIORITY_KEY = "priority";
    protected static final String SERVICE_ID = "META-INF/services/org.apache.commons.logging.LogFactory";
    public static final String TCCL_KEY = "use_tccl";
    static Class a;
    private static PrintStream b;
    private static final String c;
    private static final ClassLoader d;
    protected static Hashtable factories;
    protected static volatile LogFactory nullClassLoaderFactory;

    public abstract Object getAttribute(String str);

    public abstract String[] getAttributeNames();

    public abstract Log getInstance(Class cls) throws LogConfigurationException;

    public abstract Log getInstance(String str) throws LogConfigurationException;

    public abstract void release();

    public abstract void removeAttribute(String str);

    public abstract void setAttribute(String str, Object obj);

    public static void b(String str) {
        d(str);
    }

    private static final Hashtable a() {
        String str;
        Hashtable hashtable = null;
        try {
            str = a(HASHTABLE_IMPLEMENTATION_PROPERTY, (String) null);
        } catch (SecurityException unused) {
            str = null;
        }
        if (str == null) {
            str = "org.apache.commons.logging.impl.WeakHashtable";
        }
        try {
            hashtable = (Hashtable) Class.forName(str).newInstance();
        } catch (Throwable th) {
            handleThrowable(th);
            if (!"org.apache.commons.logging.impl.WeakHashtable".equals(str)) {
                if (isDiagnosticsEnabled()) {
                    d("[ERROR] LogFactory: Load of custom hashtable failed");
                } else {
                    System.err.println("[ERROR] LogFactory: Load of custom hashtable failed");
                }
            }
        }
        return hashtable == null ? new Hashtable() : hashtable;
    }

    private static String c(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    protected static void handleThrowable(Throwable th) {
        if (th instanceof ThreadDeath) {
            throw ((ThreadDeath) th);
        } else if (th instanceof VirtualMachineError) {
            throw ((VirtualMachineError) th);
        }
    }

    public static LogFactory getFactory() throws LogConfigurationException {
        BufferedReader bufferedReader;
        String property;
        ClassLoader b2 = b();
        if (b2 == null && isDiagnosticsEnabled()) {
            d("Context classloader is null.");
        }
        LogFactory a2 = a(b2);
        if (a2 != null) {
            return a2;
        }
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[LOOKUP] LogFactory implementation requested for the first time for context classloader ");
            stringBuffer.append(objectId(b2));
            d(stringBuffer.toString());
            a("[LOOKUP] ", b2);
        }
        Properties c2 = c(b2, FACTORY_PROPERTIES);
        ClassLoader classLoader = (c2 == null || (property = c2.getProperty(TCCL_KEY)) == null || Boolean.valueOf(property).booleanValue()) ? b2 : d;
        if (isDiagnosticsEnabled()) {
            d("[LOOKUP] Looking for system property [org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
        }
        try {
            String a3 = a(FACTORY_PROPERTY, (String) null);
            if (a3 != null) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("[LOOKUP] Creating an instance of LogFactory class '");
                    stringBuffer2.append(a3);
                    stringBuffer2.append("' as specified by system property ");
                    stringBuffer2.append(FACTORY_PROPERTY);
                    d(stringBuffer2.toString());
                }
                a2 = newFactory(a3, classLoader, b2);
            } else if (isDiagnosticsEnabled()) {
                d("[LOOKUP] No system property [org.apache.commons.logging.LogFactory] defined.");
            }
        } catch (SecurityException e) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [");
                stringBuffer3.append(c(e.getMessage()));
                stringBuffer3.append("]. Trying alternative implementations...");
                d(stringBuffer3.toString());
            }
        } catch (RuntimeException e2) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[LOOKUP] An exception occurred while trying to create an instance of the custom factory class: [");
                stringBuffer4.append(c(e2.getMessage()));
                stringBuffer4.append("] as specified by a system property.");
                d(stringBuffer4.toString());
            }
            throw e2;
        }
        if (a2 == null) {
            if (isDiagnosticsEnabled()) {
                d("[LOOKUP] Looking for a resource file of name [META-INF/services/org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
            }
            try {
                InputStream a4 = a(b2, SERVICE_ID);
                if (a4 != null) {
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(a4, "UTF-8"));
                    } catch (UnsupportedEncodingException unused) {
                        bufferedReader = new BufferedReader(new InputStreamReader(a4));
                    }
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    if (readLine != null && !"".equals(readLine)) {
                        if (isDiagnosticsEnabled()) {
                            StringBuffer stringBuffer5 = new StringBuffer();
                            stringBuffer5.append("[LOOKUP]  Creating an instance of LogFactory class ");
                            stringBuffer5.append(readLine);
                            stringBuffer5.append(" as specified by file '");
                            stringBuffer5.append(SERVICE_ID);
                            stringBuffer5.append("' which was present in the path of the context classloader.");
                            d(stringBuffer5.toString());
                        }
                        a2 = newFactory(readLine, classLoader, b2);
                    }
                } else if (isDiagnosticsEnabled()) {
                    d("[LOOKUP] No resource file with name 'META-INF/services/org.apache.commons.logging.LogFactory' found.");
                }
            } catch (Exception e3) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer6 = new StringBuffer();
                    stringBuffer6.append("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [");
                    stringBuffer6.append(c(e3.getMessage()));
                    stringBuffer6.append("]. Trying alternative implementations...");
                    d(stringBuffer6.toString());
                }
            }
        }
        if (a2 == null) {
            if (c2 != null) {
                if (isDiagnosticsEnabled()) {
                    d("[LOOKUP] Looking in properties file for entry with key 'org.apache.commons.logging.LogFactory' to define the LogFactory subclass to use...");
                }
                String property2 = c2.getProperty(FACTORY_PROPERTY);
                if (property2 != null) {
                    if (isDiagnosticsEnabled()) {
                        StringBuffer stringBuffer7 = new StringBuffer();
                        stringBuffer7.append("[LOOKUP] Properties file specifies LogFactory subclass '");
                        stringBuffer7.append(property2);
                        stringBuffer7.append(LrcRow.SINGLE_QUOTE);
                        d(stringBuffer7.toString());
                    }
                    a2 = newFactory(property2, classLoader, b2);
                } else if (isDiagnosticsEnabled()) {
                    d("[LOOKUP] Properties file has no entry specifying LogFactory subclass.");
                }
            } else if (isDiagnosticsEnabled()) {
                d("[LOOKUP] No properties file available to determine LogFactory subclass from..");
            }
        }
        if (a2 == null) {
            if (isDiagnosticsEnabled()) {
                d("[LOOKUP] Loading the default LogFactory implementation 'org.apache.commons.logging.impl.LogFactoryImpl' via the same classloader that loaded this LogFactory class (ie not looking in the context classloader).");
            }
            a2 = newFactory(FACTORY_DEFAULT, d, b2);
        }
        if (a2 != null) {
            a(b2, a2);
            if (c2 != null) {
                Enumeration<?> propertyNames = c2.propertyNames();
                while (propertyNames.hasMoreElements()) {
                    String str = (String) propertyNames.nextElement();
                    a2.setAttribute(str, c2.getProperty(str));
                }
            }
        }
        return a2;
    }

    public static Log getLog(Class cls) throws LogConfigurationException {
        return getFactory().getInstance(cls);
    }

    public static Log getLog(String str) throws LogConfigurationException {
        return getFactory().getInstance(str);
    }

    public static void release(ClassLoader classLoader) {
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Releasing factory for classloader ");
            stringBuffer.append(objectId(classLoader));
            d(stringBuffer.toString());
        }
        Hashtable hashtable = factories;
        synchronized (hashtable) {
            if (classLoader != null) {
                LogFactory logFactory = (LogFactory) hashtable.get(classLoader);
                if (logFactory != null) {
                    logFactory.release();
                    hashtable.remove(classLoader);
                }
            } else if (nullClassLoaderFactory != null) {
                nullClassLoaderFactory.release();
                nullClassLoaderFactory = null;
            }
        }
    }

    public static void releaseAll() {
        if (isDiagnosticsEnabled()) {
            d("Releasing factory for all classloaders.");
        }
        Hashtable hashtable = factories;
        synchronized (hashtable) {
            Enumeration elements = hashtable.elements();
            while (elements.hasMoreElements()) {
                ((LogFactory) elements.nextElement()).release();
            }
            hashtable.clear();
            if (nullClassLoaderFactory != null) {
                nullClassLoaderFactory.release();
                nullClassLoaderFactory = null;
            }
        }
    }

    public static ClassLoader getClassLoader(Class cls) {
        try {
            return cls.getClassLoader();
        } catch (SecurityException e) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unable to get classloader for class '");
                stringBuffer.append(cls);
                stringBuffer.append("' due to security restrictions - ");
                stringBuffer.append(e.getMessage());
                d(stringBuffer.toString());
            }
            throw e;
        }
    }

    public static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return directGetContextClassLoader();
    }

    private static ClassLoader b() throws LogConfigurationException {
        return (ClassLoader) AccessController.doPrivileged(new a());
    }

    public static ClassLoader directGetContextClassLoader() throws LogConfigurationException {
        try {
            return Thread.currentThread().getContextClassLoader();
        } catch (SecurityException unused) {
            return null;
        }
    }

    private static LogFactory a(ClassLoader classLoader) {
        if (classLoader == null) {
            return nullClassLoaderFactory;
        }
        return (LogFactory) factories.get(classLoader);
    }

    private static void a(ClassLoader classLoader, LogFactory logFactory) {
        if (logFactory == null) {
            return;
        }
        if (classLoader == null) {
            nullClassLoaderFactory = logFactory;
        } else {
            factories.put(classLoader, logFactory);
        }
    }

    protected static LogFactory newFactory(String str, ClassLoader classLoader, ClassLoader classLoader2) throws LogConfigurationException {
        Object doPrivileged = AccessController.doPrivileged(new b(str, classLoader));
        if (doPrivileged instanceof LogConfigurationException) {
            LogConfigurationException logConfigurationException = (LogConfigurationException) doPrivileged;
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("An error occurred while loading the factory class:");
                stringBuffer.append(logConfigurationException.getMessage());
                d(stringBuffer.toString());
            }
            throw logConfigurationException;
        }
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Created object ");
            stringBuffer2.append(objectId(doPrivileged));
            stringBuffer2.append(" to manage classloader ");
            stringBuffer2.append(objectId(classLoader2));
            d(stringBuffer2.toString());
        }
        return (LogFactory) doPrivileged;
    }

    protected static LogFactory newFactory(String str, ClassLoader classLoader) {
        return newFactory(str, classLoader, null);
    }

    public static Object createFactory(String str, ClassLoader classLoader) {
        Class cls;
        Class cls2;
        Class cls3;
        Class<?> cls4 = null;
        try {
            if (classLoader != null) {
                try {
                    try {
                        cls4 = classLoader.loadClass(str);
                        if (a == null) {
                            cls2 = a(FACTORY_PROPERTY);
                            a = cls2;
                        } else {
                            cls2 = a;
                        }
                        if (cls2.isAssignableFrom(cls4)) {
                            if (isDiagnosticsEnabled()) {
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append("Loaded class ");
                                stringBuffer.append(cls4.getName());
                                stringBuffer.append(" from classloader ");
                                stringBuffer.append(objectId(classLoader));
                                d(stringBuffer.toString());
                            }
                        } else if (isDiagnosticsEnabled()) {
                            StringBuffer stringBuffer2 = new StringBuffer();
                            stringBuffer2.append("Factory class ");
                            stringBuffer2.append(cls4.getName());
                            stringBuffer2.append(" loaded from classloader ");
                            stringBuffer2.append(objectId(cls4.getClassLoader()));
                            stringBuffer2.append(" does not extend '");
                            if (a == null) {
                                cls3 = a(FACTORY_PROPERTY);
                                a = cls3;
                            } else {
                                cls3 = a;
                            }
                            stringBuffer2.append(cls3.getName());
                            stringBuffer2.append("' as loaded by this classloader.");
                            d(stringBuffer2.toString());
                            a("[BAD CL TREE] ", classLoader);
                        }
                        return (LogFactory) cls4.newInstance();
                    } catch (ClassCastException unused) {
                        if (classLoader == d) {
                            boolean a2 = a(cls4);
                            StringBuffer stringBuffer3 = new StringBuffer();
                            stringBuffer3.append("The application has specified that a custom LogFactory implementation ");
                            stringBuffer3.append("should be used but Class '");
                            stringBuffer3.append(str);
                            stringBuffer3.append("' cannot be converted to '");
                            if (a == null) {
                                cls = a(FACTORY_PROPERTY);
                                a = cls;
                            } else {
                                cls = a;
                            }
                            stringBuffer3.append(cls.getName());
                            stringBuffer3.append("'. ");
                            if (a2) {
                                stringBuffer3.append("The conflict is caused by the presence of multiple LogFactory classes ");
                                stringBuffer3.append("in incompatible classloaders. ");
                                stringBuffer3.append("Background can be found in http://commons.apache.org/logging/tech.html. ");
                                stringBuffer3.append("If you have not explicitly specified a custom LogFactory then it is likely ");
                                stringBuffer3.append("that the container has set one without your knowledge. ");
                                stringBuffer3.append("In this case, consider using the commons-logging-adapters.jar file or ");
                                stringBuffer3.append("specifying the standard LogFactory from the command line. ");
                            } else {
                                stringBuffer3.append("Please check the custom implementation. ");
                            }
                            stringBuffer3.append("Help can be found @http://commons.apache.org/logging/troubleshooting.html.");
                            if (isDiagnosticsEnabled()) {
                                d(stringBuffer3.toString());
                            }
                            throw new ClassCastException(stringBuffer3.toString());
                        }
                    }
                } catch (ClassNotFoundException e) {
                    if (classLoader == d) {
                        if (isDiagnosticsEnabled()) {
                            StringBuffer stringBuffer4 = new StringBuffer();
                            stringBuffer4.append("Unable to locate any class called '");
                            stringBuffer4.append(str);
                            stringBuffer4.append("' via classloader ");
                            stringBuffer4.append(objectId(classLoader));
                            d(stringBuffer4.toString());
                        }
                        throw e;
                    }
                } catch (NoClassDefFoundError e2) {
                    if (classLoader == d) {
                        if (isDiagnosticsEnabled()) {
                            StringBuffer stringBuffer5 = new StringBuffer();
                            stringBuffer5.append("Class '");
                            stringBuffer5.append(str);
                            stringBuffer5.append("' cannot be loaded");
                            stringBuffer5.append(" via classloader ");
                            stringBuffer5.append(objectId(classLoader));
                            stringBuffer5.append(" - it depends on some other class that cannot be found.");
                            d(stringBuffer5.toString());
                        }
                        throw e2;
                    }
                }
            }
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer6 = new StringBuffer();
                stringBuffer6.append("Unable to load factory class via classloader ");
                stringBuffer6.append(objectId(classLoader));
                stringBuffer6.append(" - trying the classloader associated with this LogFactory.");
                d(stringBuffer6.toString());
            }
            return (LogFactory) Class.forName(str).newInstance();
        } catch (Exception e3) {
            if (isDiagnosticsEnabled()) {
                d("Unable to create LogFactory instance.");
            }
            if (cls4 != null) {
                Class cls5 = a;
                if (cls5 == null) {
                    cls5 = a(FACTORY_PROPERTY);
                    a = cls5;
                }
                if (!cls5.isAssignableFrom(cls4)) {
                    return new LogConfigurationException("The chosen LogFactory implementation does not extend LogFactory. Please check your configuration.", e3);
                }
            }
            return new LogConfigurationException(e3);
        }
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private static boolean a(Class cls) {
        boolean z = false;
        if (cls != null) {
            try {
                ClassLoader classLoader = cls.getClassLoader();
                if (classLoader == null) {
                    d("[CUSTOM LOG FACTORY] was loaded by the boot classloader");
                } else {
                    a("[CUSTOM LOG FACTORY] ", classLoader);
                    z = Class.forName(FACTORY_PROPERTY, false, classLoader).isAssignableFrom(cls);
                    if (z) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("[CUSTOM LOG FACTORY] ");
                        stringBuffer.append(cls.getName());
                        stringBuffer.append(" implements LogFactory but was loaded by an incompatible classloader.");
                        d(stringBuffer.toString());
                    } else {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("[CUSTOM LOG FACTORY] ");
                        stringBuffer2.append(cls.getName());
                        stringBuffer2.append(" does not implement LogFactory.");
                        d(stringBuffer2.toString());
                    }
                }
            } catch (ClassNotFoundException unused) {
                d("[CUSTOM LOG FACTORY] LogFactory class cannot be loaded by classloader which loaded the custom LogFactory implementation. Is the custom factory in the right classloader?");
            } catch (LinkageError e) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[CUSTOM LOG FACTORY] LinkageError thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: ");
                stringBuffer3.append(e.getMessage());
                d(stringBuffer3.toString());
            } catch (SecurityException e2) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[CUSTOM LOG FACTORY] SecurityException thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: ");
                stringBuffer4.append(e2.getMessage());
                d(stringBuffer4.toString());
            }
        }
        return z;
    }

    private static InputStream a(ClassLoader classLoader, String str) {
        return (InputStream) AccessController.doPrivileged(new c(classLoader, str));
    }

    private static Enumeration b(ClassLoader classLoader, String str) {
        return (Enumeration) AccessController.doPrivileged(new d(classLoader, str));
    }

    private static Properties a(URL url) {
        return (Properties) AccessController.doPrivileged(new e(url));
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final java.util.Properties c(java.lang.ClassLoader r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.LogFactory.c(java.lang.ClassLoader, java.lang.String):java.util.Properties");
    }

    private static String a(String str, String str2) throws SecurityException {
        return (String) AccessController.doPrivileged(new f(str, str2));
    }

    private static PrintStream c() {
        try {
            String a2 = a(DIAGNOSTICS_DEST_PROPERTY, (String) null);
            if (a2 == null) {
                return null;
            }
            if (a2.equals("STDOUT")) {
                return System.out;
            }
            if (a2.equals("STDERR")) {
                return System.err;
            }
            try {
                return new PrintStream(new FileOutputStream(a2, true));
            } catch (IOException unused) {
                return null;
            }
        } catch (SecurityException unused2) {
            return null;
        }
    }

    public static boolean isDiagnosticsEnabled() {
        return b != null;
    }

    private static final void d(String str) {
        PrintStream printStream = b;
        if (printStream != null) {
            printStream.print(c);
            b.println(str);
            b.flush();
        }
    }

    protected static final void logRawDiagnostic(String str) {
        PrintStream printStream = b;
        if (printStream != null) {
            printStream.println(str);
            b.flush();
        }
    }

    private static void b(Class cls) {
        if (isDiagnosticsEnabled()) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("[ENV] Extension directories (java.ext.dir): ");
                stringBuffer.append(System.getProperty("java.ext.dir"));
                d(stringBuffer.toString());
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("[ENV] Application classpath (java.class.path): ");
                stringBuffer2.append(System.getProperty("java.class.path"));
                d(stringBuffer2.toString());
            } catch (SecurityException unused) {
                d("[ENV] Security setting prevent interrogation of system classpaths.");
            }
            String name = cls.getName();
            try {
                ClassLoader classLoader = getClassLoader(cls);
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("[ENV] Class ");
                stringBuffer3.append(name);
                stringBuffer3.append(" was loaded via classloader ");
                stringBuffer3.append(objectId(classLoader));
                d(stringBuffer3.toString());
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[ENV] Ancestry of classloader which loaded ");
                stringBuffer4.append(name);
                stringBuffer4.append(" is ");
                a(stringBuffer4.toString(), classLoader);
            } catch (SecurityException unused2) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("[ENV] Security forbids determining the classloader for ");
                stringBuffer5.append(name);
                d(stringBuffer5.toString());
            }
        }
    }

    private static void a(String str, ClassLoader classLoader) {
        if (isDiagnosticsEnabled()) {
            if (classLoader != null) {
                String obj = classLoader.toString();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str);
                stringBuffer.append(objectId(classLoader));
                stringBuffer.append(" == '");
                stringBuffer.append(obj);
                stringBuffer.append(LrcRow.SINGLE_QUOTE);
                d(stringBuffer.toString());
            }
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (classLoader != null) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(str);
                    stringBuffer2.append("ClassLoader tree:");
                    StringBuffer stringBuffer3 = new StringBuffer(stringBuffer2.toString());
                    do {
                        stringBuffer3.append(objectId(classLoader));
                        if (classLoader == systemClassLoader) {
                            stringBuffer3.append(" (SYSTEM) ");
                        }
                        try {
                            classLoader = classLoader.getParent();
                            stringBuffer3.append(" --> ");
                        } catch (SecurityException unused) {
                            stringBuffer3.append(" --> SECRET");
                        }
                    } while (classLoader != null);
                    stringBuffer3.append("BOOT");
                    d(stringBuffer3.toString());
                }
            } catch (SecurityException unused2) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append(str);
                stringBuffer4.append("Security forbids determining the system classloader.");
                d(stringBuffer4.toString());
            }
        }
    }

    public static String objectId(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(obj.getClass().getName());
        stringBuffer.append("@");
        stringBuffer.append(System.identityHashCode(obj));
        return stringBuffer.toString();
    }

    static {
        String str;
        Class cls = a;
        if (cls == null) {
            cls = a(FACTORY_PROPERTY);
            a = cls;
        }
        d = getClassLoader(cls);
        try {
            str = d == null ? "BOOTLOADER" : objectId(d);
        } catch (SecurityException unused) {
            str = "UNKNOWN";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LogFactory from ");
        stringBuffer.append(str);
        stringBuffer.append("] ");
        c = stringBuffer.toString();
        b = c();
        Class cls2 = a;
        if (cls2 == null) {
            cls2 = a(FACTORY_PROPERTY);
            a = cls2;
        }
        b(cls2);
        factories = a();
        if (isDiagnosticsEnabled()) {
            d("BOOTSTRAP COMPLETED");
        }
    }
}
