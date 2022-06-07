package org.apache.commons.logging.impl;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;

/* loaded from: classes5.dex */
public class Jdk13LumberjackLogger implements Serializable, Log {
    protected static final Level dummyLevel = Level.FINE;
    private static final long serialVersionUID = -8649807923527610591L;
    protected transient Logger logger;
    protected String name;
    private String sourceClassName = "unknown";
    private String sourceMethodName = "unknown";
    private boolean classAndMethodFound = false;

    public Jdk13LumberjackLogger(String str) {
        this.logger = null;
        this.name = null;
        this.name = str;
        this.logger = getLogger();
    }

    private void a(Level level, String str, Throwable th) {
        if (getLogger().isLoggable(level)) {
            LogRecord logRecord = new LogRecord(level, str);
            if (!this.classAndMethodFound) {
                a();
            }
            logRecord.setSourceClassName(this.sourceClassName);
            logRecord.setSourceMethodName(this.sourceMethodName);
            if (th != null) {
                logRecord.setThrown(th);
            }
            getLogger().log(logRecord);
        }
    }

    private void a() {
        try {
            Throwable th = new Throwable();
            th.fillInStackTrace();
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            StringTokenizer stringTokenizer = new StringTokenizer(stringWriter.getBuffer().toString(), "\n");
            stringTokenizer.nextToken();
            String nextToken = stringTokenizer.nextToken();
            while (nextToken.indexOf(getClass().getName()) == -1) {
                nextToken = stringTokenizer.nextToken();
            }
            while (nextToken.indexOf(getClass().getName()) >= 0) {
                nextToken = stringTokenizer.nextToken();
            }
            String substring = nextToken.substring(nextToken.indexOf("at ") + 3, nextToken.indexOf(40));
            int lastIndexOf = substring.lastIndexOf(46);
            this.sourceClassName = substring.substring(0, lastIndexOf);
            this.sourceMethodName = substring.substring(lastIndexOf + 1);
        } catch (Exception unused) {
        }
        this.classAndMethodFound = true;
    }

    @Override // org.apache.commons.logging.Log
    public void debug(Object obj) {
        a(Level.FINE, String.valueOf(obj), null);
    }

    @Override // org.apache.commons.logging.Log
    public void debug(Object obj, Throwable th) {
        a(Level.FINE, String.valueOf(obj), th);
    }

    @Override // org.apache.commons.logging.Log
    public void error(Object obj) {
        a(Level.SEVERE, String.valueOf(obj), null);
    }

    @Override // org.apache.commons.logging.Log
    public void error(Object obj, Throwable th) {
        a(Level.SEVERE, String.valueOf(obj), th);
    }

    @Override // org.apache.commons.logging.Log
    public void fatal(Object obj) {
        a(Level.SEVERE, String.valueOf(obj), null);
    }

    @Override // org.apache.commons.logging.Log
    public void fatal(Object obj, Throwable th) {
        a(Level.SEVERE, String.valueOf(obj), th);
    }

    public Logger getLogger() {
        if (this.logger == null) {
            this.logger = Logger.getLogger(this.name);
        }
        return this.logger;
    }

    @Override // org.apache.commons.logging.Log
    public void info(Object obj) {
        a(Level.INFO, String.valueOf(obj), null);
    }

    @Override // org.apache.commons.logging.Log
    public void info(Object obj, Throwable th) {
        a(Level.INFO, String.valueOf(obj), th);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isDebugEnabled() {
        return getLogger().isLoggable(Level.FINE);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isErrorEnabled() {
        return getLogger().isLoggable(Level.SEVERE);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isFatalEnabled() {
        return getLogger().isLoggable(Level.SEVERE);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isInfoEnabled() {
        return getLogger().isLoggable(Level.INFO);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isTraceEnabled() {
        return getLogger().isLoggable(Level.FINEST);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isWarnEnabled() {
        return getLogger().isLoggable(Level.WARNING);
    }

    @Override // org.apache.commons.logging.Log
    public void trace(Object obj) {
        a(Level.FINEST, String.valueOf(obj), null);
    }

    @Override // org.apache.commons.logging.Log
    public void trace(Object obj, Throwable th) {
        a(Level.FINEST, String.valueOf(obj), th);
    }

    @Override // org.apache.commons.logging.Log
    public void warn(Object obj) {
        a(Level.WARNING, String.valueOf(obj), null);
    }

    @Override // org.apache.commons.logging.Log
    public void warn(Object obj, Throwable th) {
        a(Level.WARNING, String.valueOf(obj), th);
    }
}
