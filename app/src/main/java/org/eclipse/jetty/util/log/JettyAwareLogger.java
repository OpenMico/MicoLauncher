package org.eclipse.jetty.util.log;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/* loaded from: classes5.dex */
public class JettyAwareLogger implements Logger {
    private static final int DEBUG = 10;
    private static final int ERROR = 40;
    private static final String FQCN = Slf4jLog.class.getName();
    private static final int INFO = 20;
    private static final int TRACE = 0;
    private static final int WARN = 30;
    private final LocationAwareLogger _logger;

    public JettyAwareLogger(LocationAwareLogger locationAwareLogger) {
        this._logger = locationAwareLogger;
    }

    @Override // org.slf4j.Logger
    public String getName() {
        return this._logger.getName();
    }

    @Override // org.slf4j.Logger
    public boolean isTraceEnabled() {
        return this._logger.isTraceEnabled();
    }

    @Override // org.slf4j.Logger
    public void trace(String str) {
        log(null, 0, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj) {
        log(null, 0, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj, Object obj2) {
        log(null, 0, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object[] objArr) {
        log(null, 0, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Throwable th) {
        log(null, 0, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isTraceEnabled(Marker marker) {
        return this._logger.isTraceEnabled(marker);
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str) {
        log(marker, 0, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object obj) {
        log(marker, 0, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object obj, Object obj2) {
        log(marker, 0, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Object[] objArr) {
        log(marker, 0, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void trace(Marker marker, String str, Throwable th) {
        log(marker, 0, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isDebugEnabled() {
        return this._logger.isDebugEnabled();
    }

    @Override // org.slf4j.Logger
    public void debug(String str) {
        log(null, 10, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj) {
        log(null, 10, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj, Object obj2) {
        log(null, 10, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object[] objArr) {
        log(null, 10, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Throwable th) {
        log(null, 10, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isDebugEnabled(Marker marker) {
        return this._logger.isDebugEnabled(marker);
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str) {
        log(marker, 10, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object obj) {
        log(marker, 10, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object obj, Object obj2) {
        log(marker, 10, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Object[] objArr) {
        log(marker, 10, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void debug(Marker marker, String str, Throwable th) {
        log(marker, 10, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isInfoEnabled() {
        return this._logger.isInfoEnabled();
    }

    @Override // org.slf4j.Logger
    public void info(String str) {
        log(null, 20, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj) {
        log(null, 20, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj, Object obj2) {
        log(null, 20, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object[] objArr) {
        log(null, 20, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Throwable th) {
        log(null, 20, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isInfoEnabled(Marker marker) {
        return this._logger.isInfoEnabled(marker);
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str) {
        log(marker, 20, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object obj) {
        log(marker, 20, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object obj, Object obj2) {
        log(marker, 20, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Object[] objArr) {
        log(marker, 20, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void info(Marker marker, String str, Throwable th) {
        log(marker, 20, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isWarnEnabled() {
        return this._logger.isWarnEnabled();
    }

    @Override // org.slf4j.Logger
    public void warn(String str) {
        log(null, 30, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj) {
        log(null, 30, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object[] objArr) {
        log(null, 30, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj, Object obj2) {
        log(null, 30, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Throwable th) {
        log(null, 30, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isWarnEnabled(Marker marker) {
        return this._logger.isWarnEnabled(marker);
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str) {
        log(marker, 30, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object obj) {
        log(marker, 30, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object obj, Object obj2) {
        log(marker, 30, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Object[] objArr) {
        log(marker, 30, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void warn(Marker marker, String str, Throwable th) {
        log(marker, 30, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isErrorEnabled() {
        return this._logger.isErrorEnabled();
    }

    @Override // org.slf4j.Logger
    public void error(String str) {
        log(null, 40, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj) {
        log(null, 40, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object obj, Object obj2) {
        log(null, 40, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Object[] objArr) {
        log(null, 40, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Throwable th) {
        log(null, 40, str, null, th);
    }

    @Override // org.slf4j.Logger
    public boolean isErrorEnabled(Marker marker) {
        return this._logger.isErrorEnabled(marker);
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str) {
        log(marker, 40, str, null, null);
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object obj) {
        log(marker, 40, str, new Object[]{obj}, null);
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object obj, Object obj2) {
        log(marker, 40, str, new Object[]{obj, obj2}, null);
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Object[] objArr) {
        log(marker, 40, str, objArr, null);
    }

    @Override // org.slf4j.Logger
    public void error(Marker marker, String str, Throwable th) {
        log(marker, 40, str, null, th);
    }

    public String toString() {
        return this._logger.toString();
    }

    private void log(Marker marker, int i, String str, Object[] objArr, Throwable th) {
        if (objArr == null) {
            this._logger.log(marker, FQCN, i, str, null, th);
            return;
        }
        if ((this._logger.isTraceEnabled() ? 0 : this._logger.isDebugEnabled() ? 10 : this._logger.isInfoEnabled() ? 20 : this._logger.isWarnEnabled() ? 30 : 40) <= i) {
            this._logger.log(marker, FQCN, i, MessageFormatter.arrayFormat(str, objArr).getMessage(), null, th);
        }
    }
}
