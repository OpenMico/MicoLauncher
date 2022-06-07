package org.apache.commons.logging.impl;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

/* loaded from: classes5.dex */
public class LogFactoryImpl extends LogFactory {
    public static final String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
    public static final String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
    public static final String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
    public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";
    protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
    static Class b = null;
    static Class c = null;
    static Class d = null;
    static Class e = null;
    private static final int f = 32;
    private static final String[] g = {"org.apache.commons.logging.impl.Log4JLogger", "org.apache.commons.logging.impl.Jdk14Logger", "org.apache.commons.logging.impl.Jdk13LumberjackLogger", "org.apache.commons.logging.impl.SimpleLog"};
    private String i;
    private String j;
    private boolean k;
    private boolean l;
    protected Class[] logConstructorSignature;
    protected Method logMethod;
    protected Class[] logMethodSignature;
    private boolean m;
    private boolean h = true;
    protected Hashtable attributes = new Hashtable();
    protected Hashtable instances = new Hashtable();
    protected Constructor logConstructor = null;

    public static ClassLoader a() throws LogConfigurationException {
        return directGetContextClassLoader();
    }

    public LogFactoryImpl() {
        Class[] clsArr = new Class[1];
        Class cls = b;
        if (cls == null) {
            cls = c("java.lang.String");
            b = cls;
        }
        clsArr[0] = cls;
        this.logConstructorSignature = clsArr;
        this.logMethod = null;
        Class[] clsArr2 = new Class[1];
        Class cls2 = c;
        if (cls2 == null) {
            cls2 = c(LogFactory.FACTORY_PROPERTY);
            c = cls2;
        }
        clsArr2[0] = cls2;
        this.logMethodSignature = clsArr2;
        b();
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Instance created.");
        }
    }

    static Class c(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    @Override // org.apache.commons.logging.LogFactory
    public Object getAttribute(String str) {
        return this.attributes.get(str);
    }

    @Override // org.apache.commons.logging.LogFactory
    public String[] getAttributeNames() {
        return (String[]) this.attributes.keySet().toArray(new String[this.attributes.size()]);
    }

    @Override // org.apache.commons.logging.LogFactory
    public Log getInstance(Class cls) throws LogConfigurationException {
        return getInstance(cls.getName());
    }

    @Override // org.apache.commons.logging.LogFactory
    public Log getInstance(String str) throws LogConfigurationException {
        Log log = (Log) this.instances.get(str);
        if (log != null) {
            return log;
        }
        Log newInstance = newInstance(str);
        this.instances.put(str, newInstance);
        return newInstance;
    }

    @Override // org.apache.commons.logging.LogFactory
    public void release() {
        logDiagnostic("Releasing all known loggers");
        this.instances.clear();
    }

    @Override // org.apache.commons.logging.LogFactory
    public void removeAttribute(String str) {
        this.attributes.remove(str);
    }

    @Override // org.apache.commons.logging.LogFactory
    public void setAttribute(String str, Object obj) {
        if (this.logConstructor != null) {
            logDiagnostic("setAttribute: call too late; configuration already performed.");
        }
        if (obj == null) {
            this.attributes.remove(str);
        } else {
            this.attributes.put(str, obj);
        }
        if (str.equals(LogFactory.TCCL_KEY)) {
            this.h = obj != null && Boolean.valueOf(obj.toString()).booleanValue();
        }
    }

    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return LogFactory.getContextClassLoader();
    }

    protected static boolean isDiagnosticsEnabled() {
        return LogFactory.isDiagnosticsEnabled();
    }

    protected static ClassLoader getClassLoader(Class cls) {
        return LogFactory.getClassLoader(cls);
    }

    private void b() {
        String str;
        ClassLoader classLoader = getClassLoader(getClass());
        if (classLoader == null) {
            str = "BOOTLOADER";
        } else {
            try {
                str = objectId(classLoader);
            } catch (SecurityException unused) {
                str = "UNKNOWN";
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LogFactoryImpl@");
        stringBuffer.append(System.identityHashCode(this));
        stringBuffer.append(" from ");
        stringBuffer.append(str);
        stringBuffer.append("] ");
        this.i = stringBuffer.toString();
    }

    protected void logDiagnostic(String str) {
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.i);
            stringBuffer.append(str);
            logRawDiagnostic(stringBuffer.toString());
        }
    }

    protected String getLogClassName() {
        if (this.j == null) {
            e(getClass().getName());
        }
        return this.j;
    }

    protected Constructor getLogConstructor() throws LogConfigurationException {
        if (this.logConstructor == null) {
            e(getClass().getName());
        }
        return this.logConstructor;
    }

    protected boolean isJdk13LumberjackAvailable() {
        return b("Jdk13Lumberjack", "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
    }

    protected boolean isJdk14Available() {
        return b("Jdk14", "org.apache.commons.logging.impl.Jdk14Logger");
    }

    protected boolean isLog4JAvailable() {
        return b("Log4J", "org.apache.commons.logging.impl.Log4JLogger");
    }

    protected Log newInstance(String str) throws LogConfigurationException {
        Log log;
        try {
            if (this.logConstructor == null) {
                log = e(str);
            } else {
                log = (Log) this.logConstructor.newInstance(str);
            }
            if (this.logMethod != null) {
                this.logMethod.invoke(log, this);
            }
            return log;
        } catch (InvocationTargetException e2) {
            e = e2;
            Throwable targetException = e.getTargetException();
            if (targetException != null) {
                e = targetException;
            }
            throw new LogConfigurationException(e);
        } catch (LogConfigurationException e3) {
            throw e3;
        } catch (Throwable th) {
            handleThrowable(th);
            throw new LogConfigurationException(th);
        }
    }

    private static ClassLoader c() throws LogConfigurationException {
        return (ClassLoader) AccessController.doPrivileged(new a());
    }

    private static String a(String str, String str2) throws SecurityException {
        return (String) AccessController.doPrivileged(new b(str, str2));
    }

    private ClassLoader a(ClassLoader classLoader) {
        try {
            return (ClassLoader) AccessController.doPrivileged(new c(this, classLoader));
        } catch (SecurityException unused) {
            logDiagnostic("[SECURITY] Unable to obtain parent classloader");
            return null;
        }
    }

    private boolean b(String str, String str2) {
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Checking for '");
            stringBuffer.append(str);
            stringBuffer.append("'.");
            logDiagnostic(stringBuffer.toString());
        }
        try {
            if (a(str2, getClass().getName(), false) == null) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Did not find '");
                    stringBuffer2.append(str);
                    stringBuffer2.append("'.");
                    logDiagnostic(stringBuffer2.toString());
                }
                return false;
            } else if (!isDiagnosticsEnabled()) {
                return true;
            } else {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Found '");
                stringBuffer3.append(str);
                stringBuffer3.append("'.");
                logDiagnostic(stringBuffer3.toString());
                return true;
            }
        } catch (LogConfigurationException unused) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Logging system '");
                stringBuffer4.append(str);
                stringBuffer4.append("' is available but not useable.");
                logDiagnostic(stringBuffer4.toString());
            }
            return false;
        }
    }

    private String d(String str) {
        String a;
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[ENV] Trying to get configuration for item ");
            stringBuffer.append(str);
            logDiagnostic(stringBuffer.toString());
        }
        Object attribute = getAttribute(str);
        if (attribute != null) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("[ENV] Found LogFactory attribute [");
                stringBuffer2.append(attribute);
                stringBuffer2.append("] for ");
                stringBuffer2.append(str);
                logDiagnostic(stringBuffer2.toString());
            }
            return attribute.toString();
        }
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("[ENV] No LogFactory attribute found for ");
            stringBuffer3.append(str);
            logDiagnostic(stringBuffer3.toString());
        }
        try {
            a = a(str, (String) null);
        } catch (SecurityException unused) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("[ENV] Security prevented reading system property ");
                stringBuffer4.append(str);
                logDiagnostic(stringBuffer4.toString());
            }
        }
        if (a != null) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("[ENV] Found system property [");
                stringBuffer5.append(a);
                stringBuffer5.append("] for ");
                stringBuffer5.append(str);
                logDiagnostic(stringBuffer5.toString());
            }
            return a;
        }
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append("[ENV] No system property found for property ");
            stringBuffer6.append(str);
            logDiagnostic(stringBuffer6.toString());
        }
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer7 = new StringBuffer();
            stringBuffer7.append("[ENV] No configuration defined for item ");
            stringBuffer7.append(str);
            logDiagnostic(stringBuffer7.toString());
        }
        return null;
    }

    private boolean a(String str, boolean z) {
        String d2 = d(str);
        return d2 == null ? z : Boolean.valueOf(d2).booleanValue();
    }

    private void d() {
        this.k = a(ALLOW_FLAWED_CONTEXT_PROPERTY, true);
        this.l = a(ALLOW_FLAWED_DISCOVERY_PROPERTY, true);
        this.m = a(ALLOW_FLAWED_HIERARCHY_PROPERTY, true);
    }

    private Log e(String str) throws LogConfigurationException {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Discovering a Log implementation...");
        }
        d();
        Log log = null;
        String e2 = e();
        if (e2 != null) {
            if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Attempting to load user-specified log class '");
                stringBuffer.append(e2);
                stringBuffer.append("'...");
                logDiagnostic(stringBuffer.toString());
            }
            Log a = a(e2, str, true);
            if (a != null) {
                return a;
            }
            StringBuffer stringBuffer2 = new StringBuffer("User-specified log class '");
            stringBuffer2.append(e2);
            stringBuffer2.append("' cannot be found or is not useable.");
            a(stringBuffer2, e2, "org.apache.commons.logging.impl.Log4JLogger");
            a(stringBuffer2, e2, "org.apache.commons.logging.impl.Jdk14Logger");
            a(stringBuffer2, e2, "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
            a(stringBuffer2, e2, "org.apache.commons.logging.impl.SimpleLog");
            throw new LogConfigurationException(stringBuffer2.toString());
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("No user-specified Log implementation; performing discovery using the standard supported logging implementations...");
        }
        int i = 0;
        while (true) {
            String[] strArr = g;
            if (i >= strArr.length || log != null) {
                break;
            }
            log = a(strArr[i], str, true);
            i++;
        }
        if (log != null) {
            return log;
        }
        throw new LogConfigurationException("No suitable Log implementation");
    }

    private void a(StringBuffer stringBuffer, String str, String str2) {
        if (!str.equals(str2) && str.regionMatches(true, 0, str2, 0, f + 5)) {
            stringBuffer.append(" Did you mean '");
            stringBuffer.append(str2);
            stringBuffer.append("'?");
        }
    }

    private String e() {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.Log'");
        }
        String str = (String) getAttribute(LOG_PROPERTY);
        if (str == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.log'");
            }
            str = (String) getAttribute(LOG_PROPERTY_OLD);
        }
        if (str == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.Log'");
            }
            try {
                str = a(LOG_PROPERTY, (String) null);
            } catch (SecurityException e2) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No access allowed to system property 'org.apache.commons.logging.Log' - ");
                    stringBuffer.append(e2.getMessage());
                    logDiagnostic(stringBuffer.toString());
                }
            }
        }
        if (str == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.log'");
            }
            try {
                str = a(LOG_PROPERTY_OLD, (String) null);
            } catch (SecurityException e3) {
                if (isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("No access allowed to system property 'org.apache.commons.logging.log' - ");
                    stringBuffer2.append(e3.getMessage());
                    logDiagnostic(stringBuffer2.toString());
                }
            }
        }
        return str != null ? str.trim() : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x015e A[LOOP:0: B:6:0x002c->B:38:0x015e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x015c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.apache.commons.logging.Log a(java.lang.String r10, java.lang.String r11, boolean r12) throws org.apache.commons.logging.LogConfigurationException {
        /*
            Method dump skipped, instructions count: 593
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.impl.LogFactoryImpl.a(java.lang.String, java.lang.String, boolean):org.apache.commons.logging.Log");
    }

    private ClassLoader f() throws LogConfigurationException {
        Class cls = d;
        if (cls == null) {
            cls = c(LogFactory.FACTORY_DEFAULT);
            d = cls;
        }
        ClassLoader classLoader = getClassLoader(cls);
        if (!this.h) {
            return classLoader;
        }
        ClassLoader c2 = c();
        ClassLoader a = a(c2, classLoader);
        if (a != null) {
            if (a != c2) {
                if (!this.k) {
                    throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
                } else if (isDiagnosticsEnabled()) {
                    logDiagnostic("Warning: the context classloader is an ancestor of the classloader that loaded LogFactoryImpl; it should be the same or a descendant. The application using commons-logging should ensure the context classloader is used correctly.");
                }
            }
            return a;
        } else if (this.k) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[WARNING] the context classloader is not part of a parent-child relationship with the classloader that loaded LogFactoryImpl.");
            }
            return c2;
        } else {
            throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
        }
    }

    private ClassLoader a(ClassLoader classLoader, ClassLoader classLoader2) {
        if (classLoader == null) {
            return classLoader2;
        }
        if (classLoader2 == null) {
            return classLoader;
        }
        ClassLoader classLoader3 = classLoader;
        while (classLoader3 != null) {
            if (classLoader3 == classLoader2) {
                return classLoader;
            }
            classLoader3 = a(classLoader3);
        }
        ClassLoader classLoader4 = classLoader2;
        while (classLoader4 != null) {
            if (classLoader4 == classLoader) {
                return classLoader2;
            }
            classLoader4 = a(classLoader4);
        }
        return null;
    }

    private void a(String str, ClassLoader classLoader, Throwable th) {
        Throwable targetException;
        Throwable exception;
        if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not instantiate Log '");
            stringBuffer.append(str);
            stringBuffer.append("' -- ");
            stringBuffer.append(th.getClass().getName());
            stringBuffer.append(": ");
            stringBuffer.append(th.getLocalizedMessage());
            logDiagnostic(stringBuffer.toString());
            if ((th instanceof InvocationTargetException) && (targetException = ((InvocationTargetException) th).getTargetException()) != null) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("... InvocationTargetException: ");
                stringBuffer2.append(targetException.getClass().getName());
                stringBuffer2.append(": ");
                stringBuffer2.append(targetException.getLocalizedMessage());
                logDiagnostic(stringBuffer2.toString());
                if ((targetException instanceof ExceptionInInitializerError) && (exception = ((ExceptionInInitializerError) targetException).getException()) != null) {
                    StringWriter stringWriter = new StringWriter();
                    exception.printStackTrace(new PrintWriter((Writer) stringWriter, true));
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("... ExceptionInInitializerError: ");
                    stringBuffer3.append(stringWriter.toString());
                    logDiagnostic(stringBuffer3.toString());
                }
            }
        }
        if (!this.l) {
            throw new LogConfigurationException(th);
        }
    }

    private void a(ClassLoader classLoader, Class cls) throws LogConfigurationException {
        Class cls2;
        Class cls3 = e;
        if (cls3 == null) {
            cls3 = c(LOG_PROPERTY);
            e = cls3;
        }
        String name = cls3.getName();
        Class<?>[] interfaces = cls.getInterfaces();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= interfaces.length) {
                break;
            } else if (name.equals(interfaces[i].getName())) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            if (isDiagnosticsEnabled()) {
                try {
                    if (e == null) {
                        cls2 = c(LOG_PROPERTY);
                        e = cls2;
                    } else {
                        cls2 = e;
                    }
                    ClassLoader classLoader2 = getClassLoader(cls2);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Class '");
                    stringBuffer.append(cls.getName());
                    stringBuffer.append("' was found in classloader ");
                    stringBuffer.append(objectId(classLoader));
                    stringBuffer.append(". It is bound to a Log interface which is not");
                    stringBuffer.append(" the one loaded from classloader ");
                    stringBuffer.append(objectId(classLoader2));
                    logDiagnostic(stringBuffer.toString());
                } catch (Throwable th) {
                    handleThrowable(th);
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Error while trying to output diagnostics about bad class '");
                    stringBuffer2.append(cls);
                    stringBuffer2.append(LrcRow.SINGLE_QUOTE);
                    logDiagnostic(stringBuffer2.toString());
                }
            }
            if (!this.m) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Terminating logging for this context ");
                stringBuffer3.append("due to bad log hierarchy. ");
                stringBuffer3.append("You have more than one version of '");
                Class cls4 = e;
                if (cls4 == null) {
                    cls4 = c(LOG_PROPERTY);
                    e = cls4;
                }
                stringBuffer3.append(cls4.getName());
                stringBuffer3.append("' visible.");
                if (isDiagnosticsEnabled()) {
                    logDiagnostic(stringBuffer3.toString());
                }
                throw new LogConfigurationException(stringBuffer3.toString());
            } else if (isDiagnosticsEnabled()) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Warning: bad log hierarchy. ");
                stringBuffer4.append("You have more than one version of '");
                Class cls5 = e;
                if (cls5 == null) {
                    cls5 = c(LOG_PROPERTY);
                    e = cls5;
                }
                stringBuffer4.append(cls5.getName());
                stringBuffer4.append("' visible.");
                logDiagnostic(stringBuffer4.toString());
            }
        } else if (!this.l) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Terminating logging for this context. ");
            stringBuffer5.append("Log class '");
            stringBuffer5.append(cls.getName());
            stringBuffer5.append("' does not implement the Log interface.");
            if (isDiagnosticsEnabled()) {
                logDiagnostic(stringBuffer5.toString());
            }
            throw new LogConfigurationException(stringBuffer5.toString());
        } else if (isDiagnosticsEnabled()) {
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append("[WARNING] Log class '");
            stringBuffer6.append(cls.getName());
            stringBuffer6.append("' does not implement the Log interface.");
            logDiagnostic(stringBuffer6.toString());
        }
    }
}
