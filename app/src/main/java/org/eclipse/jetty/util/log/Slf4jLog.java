package org.eclipse.jetty.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/* loaded from: classes5.dex */
public class Slf4jLog extends AbstractLogger {
    private final Logger _logger;

    public Slf4jLog() throws Exception {
        this("org.eclipse.jetty.util.log");
    }

    public Slf4jLog(String str) {
        Logger logger = LoggerFactory.getLogger(str);
        if (logger instanceof LocationAwareLogger) {
            this._logger = new JettyAwareLogger((LocationAwareLogger) logger);
        } else {
            this._logger = logger;
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public String getName() {
        return this._logger.getName();
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Object... objArr) {
        this._logger.warn(str, objArr);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(Throwable th) {
        warn("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Throwable th) {
        this._logger.warn(str, th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Object... objArr) {
        this._logger.info(str, objArr);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(Throwable th) {
        info("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Throwable th) {
        this._logger.info(str, th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Object... objArr) {
        this._logger.debug(str, objArr);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(Throwable th) {
        debug("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Throwable th) {
        this._logger.debug(str, th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public boolean isDebugEnabled() {
        return this._logger.isDebugEnabled();
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void setDebugEnabled(boolean z) {
        warn("setDebugEnabled not implemented", null, null);
    }

    @Override // org.eclipse.jetty.util.log.AbstractLogger
    protected Logger newLogger(String str) {
        return new Slf4jLog(str);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void ignore(Throwable th) {
        if (Log.isIgnored()) {
            warn(Log.IGNORED, th);
        }
    }

    public String toString() {
        return this._logger.toString();
    }
}
