package org.apache.commons.logging.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;

/* loaded from: classes5.dex */
public class SimpleLog implements Serializable, Log {
    protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_ERROR = 5;
    public static final int LOG_LEVEL_FATAL = 6;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_OFF = 7;
    public static final int LOG_LEVEL_TRACE = 1;
    public static final int LOG_LEVEL_WARN = 4;
    static Class a = null;
    static Class b = null;
    protected static DateFormat dateFormatter = null;
    protected static volatile String dateTimeFormat = null;
    private static final long serialVersionUID = 136942970684951178L;
    protected static volatile boolean showDateTime = false;
    protected static volatile boolean showLogName = false;
    protected static volatile boolean showShortName = false;
    protected static final Properties simpleLogProps = new Properties();
    protected static final String systemPrefix = "org.apache.commons.logging.simplelog.";
    protected volatile int currentLogLevel;
    protected volatile String logName;
    private volatile String shortLogName = null;

    public static ClassLoader a() {
        return b();
    }

    static {
        showLogName = false;
        showShortName = true;
        showDateTime = false;
        dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
        dateFormatter = null;
        InputStream c = c("simplelog.properties");
        if (c != null) {
            try {
                simpleLogProps.load(c);
                c.close();
            } catch (IOException unused) {
            }
        }
        showLogName = a("org.apache.commons.logging.simplelog.showlogname", showLogName);
        showShortName = a("org.apache.commons.logging.simplelog.showShortLogname", showShortName);
        showDateTime = a("org.apache.commons.logging.simplelog.showdatetime", showDateTime);
        if (showDateTime) {
            dateTimeFormat = a("org.apache.commons.logging.simplelog.dateTimeFormat", dateTimeFormat);
            try {
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            } catch (IllegalArgumentException unused2) {
                dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            }
        }
    }

    private static String b(String str) {
        String str2;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        return str2 == null ? simpleLogProps.getProperty(str) : str2;
    }

    private static String a(String str, String str2) {
        String b2 = b(str);
        return b2 == null ? str2 : b2;
    }

    private static boolean a(String str, boolean z) {
        String b2 = b(str);
        return b2 == null ? z : "true".equalsIgnoreCase(b2);
    }

    public SimpleLog(String str) {
        this.logName = null;
        this.logName = str;
        setLevel(3);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("org.apache.commons.logging.simplelog.log.");
        stringBuffer.append(this.logName);
        String b2 = b(stringBuffer.toString());
        int lastIndexOf = String.valueOf(str).lastIndexOf(".");
        while (b2 == null && lastIndexOf > -1) {
            str = str.substring(0, lastIndexOf);
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("org.apache.commons.logging.simplelog.log.");
            stringBuffer2.append(str);
            b2 = b(stringBuffer2.toString());
            lastIndexOf = String.valueOf(str).lastIndexOf(".");
        }
        b2 = b2 == null ? b("org.apache.commons.logging.simplelog.defaultlog") : b2;
        if ("all".equalsIgnoreCase(b2)) {
            setLevel(0);
        } else if ("trace".equalsIgnoreCase(b2)) {
            setLevel(1);
        } else if ("debug".equalsIgnoreCase(b2)) {
            setLevel(2);
        } else if ("info".equalsIgnoreCase(b2)) {
            setLevel(3);
        } else if ("warn".equalsIgnoreCase(b2)) {
            setLevel(4);
        } else if ("error".equalsIgnoreCase(b2)) {
            setLevel(5);
        } else if ("fatal".equalsIgnoreCase(b2)) {
            setLevel(6);
        } else if ("off".equalsIgnoreCase(b2)) {
            setLevel(7);
        }
    }

    public void setLevel(int i) {
        this.currentLogLevel = i;
    }

    public int getLevel() {
        return this.currentLogLevel;
    }

    protected void log(int i, Object obj, Throwable th) {
        String format;
        StringBuffer stringBuffer = new StringBuffer();
        if (showDateTime) {
            Date date = new Date();
            synchronized (dateFormatter) {
                format = dateFormatter.format(date);
            }
            stringBuffer.append(format);
            stringBuffer.append(StringUtils.SPACE);
        }
        switch (i) {
            case 1:
                stringBuffer.append("[TRACE] ");
                break;
            case 2:
                stringBuffer.append("[DEBUG] ");
                break;
            case 3:
                stringBuffer.append("[INFO] ");
                break;
            case 4:
                stringBuffer.append("[WARN] ");
                break;
            case 5:
                stringBuffer.append("[ERROR] ");
                break;
            case 6:
                stringBuffer.append("[FATAL] ");
                break;
        }
        if (showShortName) {
            if (this.shortLogName == null) {
                String substring = this.logName.substring(this.logName.lastIndexOf(".") + 1);
                this.shortLogName = substring.substring(substring.lastIndexOf("/") + 1);
            }
            stringBuffer.append(String.valueOf(this.shortLogName));
            stringBuffer.append(" - ");
        } else if (showLogName) {
            stringBuffer.append(String.valueOf(this.logName));
            stringBuffer.append(" - ");
        }
        stringBuffer.append(String.valueOf(obj));
        if (th != null) {
            stringBuffer.append(" <");
            stringBuffer.append(th.toString());
            stringBuffer.append(">");
            StringWriter stringWriter = new StringWriter(1024);
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            printWriter.close();
            stringBuffer.append(stringWriter.toString());
        }
        write(stringBuffer);
    }

    protected void write(StringBuffer stringBuffer) {
        System.err.println(stringBuffer.toString());
    }

    protected boolean isLevelEnabled(int i) {
        return i >= this.currentLogLevel;
    }

    @Override // org.apache.commons.logging.Log
    public final void debug(Object obj) {
        if (isLevelEnabled(2)) {
            log(2, obj, null);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void debug(Object obj, Throwable th) {
        if (isLevelEnabled(2)) {
            log(2, obj, th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void trace(Object obj) {
        if (isLevelEnabled(1)) {
            log(1, obj, null);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void trace(Object obj, Throwable th) {
        if (isLevelEnabled(1)) {
            log(1, obj, th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void info(Object obj) {
        if (isLevelEnabled(3)) {
            log(3, obj, null);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void info(Object obj, Throwable th) {
        if (isLevelEnabled(3)) {
            log(3, obj, th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void warn(Object obj) {
        if (isLevelEnabled(4)) {
            log(4, obj, null);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void warn(Object obj, Throwable th) {
        if (isLevelEnabled(4)) {
            log(4, obj, th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void error(Object obj) {
        if (isLevelEnabled(5)) {
            log(5, obj, null);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void error(Object obj, Throwable th) {
        if (isLevelEnabled(5)) {
            log(5, obj, th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void fatal(Object obj) {
        if (isLevelEnabled(6)) {
            log(6, obj, null);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final void fatal(Object obj, Throwable th) {
        if (isLevelEnabled(6)) {
            log(6, obj, th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public final boolean isDebugEnabled() {
        return isLevelEnabled(2);
    }

    @Override // org.apache.commons.logging.Log
    public final boolean isErrorEnabled() {
        return isLevelEnabled(5);
    }

    @Override // org.apache.commons.logging.Log
    public final boolean isFatalEnabled() {
        return isLevelEnabled(6);
    }

    @Override // org.apache.commons.logging.Log
    public final boolean isInfoEnabled() {
        return isLevelEnabled(3);
    }

    @Override // org.apache.commons.logging.Log
    public final boolean isTraceEnabled() {
        return isLevelEnabled(1);
    }

    @Override // org.apache.commons.logging.Log
    public final boolean isWarnEnabled() {
        return isLevelEnabled(4);
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private static ClassLoader b() {
        Class cls;
        ClassLoader classLoader = null;
        try {
            if (a == null) {
                cls = a("java.lang.Thread");
                a = cls;
            } else {
                cls = a;
            }
            try {
                classLoader = (ClassLoader) cls.getMethod("getContextClassLoader", null).invoke(Thread.currentThread(), null);
            } catch (InvocationTargetException e) {
                if (!(e.getTargetException() instanceof SecurityException)) {
                    throw new LogConfigurationException("Unexpected InvocationTargetException", e.getTargetException());
                }
            }
        } catch (IllegalAccessException | NoSuchMethodException unused) {
        }
        if (classLoader != null) {
            return classLoader;
        }
        Class cls2 = b;
        if (cls2 == null) {
            cls2 = a("org.apache.commons.logging.impl.SimpleLog");
            b = cls2;
        }
        return cls2.getClassLoader();
    }

    private static InputStream c(String str) {
        return (InputStream) AccessController.doPrivileged(new d(str));
    }
}
