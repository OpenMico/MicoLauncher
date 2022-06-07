package org.slf4j.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.eclipse.jetty.http.HttpMethods;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.Util;

/* loaded from: classes4.dex */
public class SimpleLogger extends MarkerIgnoringBase {
    public static final String DATE_TIME_FORMAT_KEY = "org.slf4j.simpleLogger.dateTimeFormat";
    public static final String DEFAULT_LOG_LEVEL_KEY = "org.slf4j.simpleLogger.defaultLogLevel";
    public static final String LEVEL_IN_BRACKETS_KEY = "org.slf4j.simpleLogger.levelInBrackets";
    public static final String LOG_FILE_KEY = "org.slf4j.simpleLogger.logFile";
    public static final String LOG_KEY_PREFIX = "org.slf4j.simpleLogger.log.";
    public static final String SHOW_DATE_TIME_KEY = "org.slf4j.simpleLogger.showDateTime";
    public static final String SHOW_LOG_NAME_KEY = "org.slf4j.simpleLogger.showLogName";
    public static final String SHOW_SHORT_LOG_NAME_KEY = "org.slf4j.simpleLogger.showShortLogName";
    public static final String SHOW_THREAD_NAME_KEY = "org.slf4j.simpleLogger.showThreadName";
    public static final String SYSTEM_PREFIX = "org.slf4j.simpleLogger.";
    public static final String WARN_LEVEL_STRING_KEY = "org.slf4j.simpleLogger.warnLevelString";
    private static long a = System.currentTimeMillis();
    private static final Properties b = new Properties();
    private static boolean c = false;
    private static int d = 20;
    private static boolean e = false;
    private static String f = null;
    private static DateFormat g = null;
    private static boolean h = true;
    private static boolean i = true;
    private static boolean j = false;
    private static String k = "System.err";
    private static PrintStream l = null;
    private static boolean m = false;
    private static String n = "WARN";
    private static final long serialVersionUID = -632788891211436180L;
    protected int currentLogLevel;
    private transient String o = null;

    private static String a(String str) {
        String str2;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        return str2 == null ? b.getProperty(str) : str2;
    }

    private static String a(String str, String str2) {
        String a2 = a(str);
        return a2 == null ? str2 : a2;
    }

    private static boolean a(String str, boolean z) {
        String a2 = a(str);
        return a2 == null ? z : "true".equalsIgnoreCase(a2);
    }

    static void a() {
        c = true;
        c();
        String a2 = a(DEFAULT_LOG_LEVEL_KEY, (String) null);
        if (a2 != null) {
            d = c(a2);
        }
        i = a(SHOW_LOG_NAME_KEY, i);
        j = a(SHOW_SHORT_LOG_NAME_KEY, j);
        e = a(SHOW_DATE_TIME_KEY, e);
        h = a(SHOW_THREAD_NAME_KEY, h);
        f = a(DATE_TIME_FORMAT_KEY, f);
        m = a(LEVEL_IN_BRACKETS_KEY, m);
        n = a(WARN_LEVEL_STRING_KEY, n);
        k = a(LOG_FILE_KEY, k);
        l = b(k);
        String str = f;
        if (str != null) {
            try {
                g = new SimpleDateFormat(str);
            } catch (IllegalArgumentException e2) {
                Util.report("Bad date format in simplelogger.properties; will output relative time", e2);
            }
        }
    }

    private static PrintStream b(String str) {
        if ("System.err".equalsIgnoreCase(str)) {
            return System.err;
        }
        if ("System.out".equalsIgnoreCase(str)) {
            return System.out;
        }
        try {
            return new PrintStream(new FileOutputStream(str));
        } catch (FileNotFoundException e2) {
            Util.report("Could not open [" + str + "]. Defaulting to System.err", e2);
            return System.err;
        }
    }

    private static void c() {
        InputStream inputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.slf4j.impl.SimpleLogger.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                if (contextClassLoader != null) {
                    return contextClassLoader.getResourceAsStream("simplelogger.properties");
                }
                return ClassLoader.getSystemResourceAsStream("simplelogger.properties");
            }
        });
        if (inputStream != null) {
            try {
                b.load(inputStream);
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SimpleLogger(String str) {
        this.currentLogLevel = 20;
        if (!c) {
            a();
        }
        this.name = str;
        String b2 = b();
        if (b2 != null) {
            this.currentLogLevel = c(b2);
        } else {
            this.currentLogLevel = d;
        }
    }

    String b() {
        String str = this.name;
        int length = str.length();
        String str2 = str;
        String str3 = null;
        while (str3 == null && length > -1) {
            str2 = str2.substring(0, length);
            str3 = a(LOG_KEY_PREFIX + str2, (String) null);
            length = String.valueOf(str2).lastIndexOf(".");
        }
        return str3;
    }

    private static int c(String str) {
        if ("trace".equalsIgnoreCase(str)) {
            return 0;
        }
        if ("debug".equalsIgnoreCase(str)) {
            return 10;
        }
        if ("info".equalsIgnoreCase(str)) {
            return 20;
        }
        if ("warn".equalsIgnoreCase(str)) {
            return 30;
        }
        return "error".equalsIgnoreCase(str) ? 40 : 20;
    }

    private void a(int i2, String str, Throwable th) {
        if (isLevelEnabled(i2)) {
            StringBuffer stringBuffer = new StringBuffer(32);
            if (e) {
                if (g != null) {
                    stringBuffer.append(d());
                    stringBuffer.append(' ');
                } else {
                    stringBuffer.append(System.currentTimeMillis() - a);
                    stringBuffer.append(' ');
                }
            }
            if (h) {
                stringBuffer.append('[');
                stringBuffer.append(Thread.currentThread().getName());
                stringBuffer.append("] ");
            }
            if (m) {
                stringBuffer.append('[');
            }
            if (i2 == 0) {
                stringBuffer.append(HttpMethods.TRACE);
            } else if (i2 == 10) {
                stringBuffer.append("DEBUG");
            } else if (i2 == 20) {
                stringBuffer.append("INFO");
            } else if (i2 == 30) {
                stringBuffer.append(n);
            } else if (i2 == 40) {
                stringBuffer.append("ERROR");
            }
            if (m) {
                stringBuffer.append(']');
            }
            stringBuffer.append(' ');
            if (j) {
                if (this.o == null) {
                    this.o = e();
                }
                stringBuffer.append(String.valueOf(this.o));
                stringBuffer.append(" - ");
            } else if (i) {
                stringBuffer.append(String.valueOf(this.name));
                stringBuffer.append(" - ");
            }
            stringBuffer.append(str);
            a(stringBuffer, th);
        }
    }

    void a(StringBuffer stringBuffer, Throwable th) {
        l.println(stringBuffer.toString());
        if (th != null) {
            th.printStackTrace(l);
        }
        l.flush();
    }

    private String d() {
        String format;
        Date date = new Date();
        synchronized (g) {
            format = g.format(date);
        }
        return format;
    }

    private String e() {
        return this.name.substring(this.name.lastIndexOf(".") + 1);
    }

    private void a(int i2, String str, Object obj, Object obj2) {
        if (isLevelEnabled(i2)) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            a(i2, format.getMessage(), format.getThrowable());
        }
    }

    private void a(int i2, String str, Object... objArr) {
        if (isLevelEnabled(i2)) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            a(i2, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    protected boolean isLevelEnabled(int i2) {
        return i2 >= this.currentLogLevel;
    }

    @Override // org.slf4j.Logger
    public boolean isTraceEnabled() {
        return isLevelEnabled(0);
    }

    @Override // org.slf4j.Logger
    public void trace(String str) {
        a(0, str, (Throwable) null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj) {
        a(0, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj, Object obj2) {
        a(0, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object... objArr) {
        a(0, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Throwable th) {
        a(0, str, th);
    }

    @Override // org.slf4j.Logger
    public boolean isDebugEnabled() {
        return isLevelEnabled(10);
    }

    @Override // org.slf4j.Logger
    public void debug(String str) {
        a(10, str, (Throwable) null);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj) {
        a(10, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj, Object obj2) {
        a(10, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object... objArr) {
        a(10, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Throwable th) {
        a(10, str, th);
    }

    @Override // org.slf4j.Logger
    public boolean isInfoEnabled() {
        return isLevelEnabled(20);
    }

    @Override // org.slf4j.Logger
    public void info(String str) {
        a(20, str, (Throwable) null);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj) {
        a(20, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj, Object obj2) {
        a(20, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object... objArr) {
        a(20, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Throwable th) {
        a(20, str, th);
    }

    @Override // org.slf4j.Logger
    public boolean isWarnEnabled() {
        return isLevelEnabled(30);
    }

    @Override // org.slf4j.Logger
    public void warn(String str) {
        a(30, str, (Throwable) null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj) {
        a(30, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj, Object obj2) {
        a(30, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object... objArr) {
        a(30, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Throwable th) {
        a(30, str, th);
    }

    @Override // org.slf4j.Logger
    public boolean isErrorEnabled() {
        return isLevelEnabled(40);
    }

    @Override // org.slf4j.Logger
    public void error(String str) {
        a(40, str, (Throwable) null);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj) {
        a(40, str, obj, null);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj, Object obj2) {
        a(40, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object... objArr) {
        a(40, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Throwable th) {
        a(40, str, th);
    }
}
