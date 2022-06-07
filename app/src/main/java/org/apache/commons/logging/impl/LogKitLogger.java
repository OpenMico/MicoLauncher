package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.log.Hierarchy;
import org.apache.log.Logger;

/* loaded from: classes5.dex */
public class LogKitLogger implements Serializable, Log {
    private static final long serialVersionUID = 3768538055836059519L;
    protected volatile transient Logger logger;
    protected String name;

    public LogKitLogger(String str) {
        this.logger = null;
        this.name = null;
        this.name = str;
        this.logger = getLogger();
    }

    public Logger getLogger() {
        Logger logger = this.logger;
        if (logger == null) {
            synchronized (this) {
                logger = this.logger;
                if (logger == null) {
                    logger = Hierarchy.getDefaultHierarchy().getLoggerFor(this.name);
                    this.logger = logger;
                }
            }
        }
        return logger;
    }

    @Override // org.apache.commons.logging.Log
    public void trace(Object obj) {
        debug(obj);
    }

    @Override // org.apache.commons.logging.Log
    public void trace(Object obj, Throwable th) {
        debug(obj, th);
    }

    @Override // org.apache.commons.logging.Log
    public void debug(Object obj) {
        if (obj != null) {
            getLogger().debug(String.valueOf(obj));
        }
    }

    @Override // org.apache.commons.logging.Log
    public void debug(Object obj, Throwable th) {
        if (obj != null) {
            getLogger().debug(String.valueOf(obj), th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public void info(Object obj) {
        if (obj != null) {
            getLogger().info(String.valueOf(obj));
        }
    }

    @Override // org.apache.commons.logging.Log
    public void info(Object obj, Throwable th) {
        if (obj != null) {
            getLogger().info(String.valueOf(obj), th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public void warn(Object obj) {
        if (obj != null) {
            getLogger().warn(String.valueOf(obj));
        }
    }

    @Override // org.apache.commons.logging.Log
    public void warn(Object obj, Throwable th) {
        if (obj != null) {
            getLogger().warn(String.valueOf(obj), th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public void error(Object obj) {
        if (obj != null) {
            getLogger().error(String.valueOf(obj));
        }
    }

    @Override // org.apache.commons.logging.Log
    public void error(Object obj, Throwable th) {
        if (obj != null) {
            getLogger().error(String.valueOf(obj), th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public void fatal(Object obj) {
        if (obj != null) {
            getLogger().fatalError(String.valueOf(obj));
        }
    }

    @Override // org.apache.commons.logging.Log
    public void fatal(Object obj, Throwable th) {
        if (obj != null) {
            getLogger().fatalError(String.valueOf(obj), th);
        }
    }

    @Override // org.apache.commons.logging.Log
    public boolean isDebugEnabled() {
        return getLogger().isDebugEnabled();
    }

    @Override // org.apache.commons.logging.Log
    public boolean isErrorEnabled() {
        return getLogger().isErrorEnabled();
    }

    @Override // org.apache.commons.logging.Log
    public boolean isFatalEnabled() {
        return getLogger().isFatalErrorEnabled();
    }

    @Override // org.apache.commons.logging.Log
    public boolean isInfoEnabled() {
        return getLogger().isInfoEnabled();
    }

    @Override // org.apache.commons.logging.Log
    public boolean isTraceEnabled() {
        return getLogger().isDebugEnabled();
    }

    @Override // org.apache.commons.logging.Log
    public boolean isWarnEnabled() {
        return getLogger().isWarnEnabled();
    }
}
