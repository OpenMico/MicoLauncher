package com.elvishew.xlog;

import com.elvishew.xlog.formatter.border.BorderFormatter;
import com.elvishew.xlog.formatter.message.json.JsonFormatter;
import com.elvishew.xlog.formatter.message.object.ObjectFormatter;
import com.elvishew.xlog.formatter.message.throwable.ThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.XmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.StackTraceFormatter;
import com.elvishew.xlog.formatter.thread.ThreadFormatter;
import com.elvishew.xlog.interceptor.Interceptor;
import com.elvishew.xlog.internal.DefaultsFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/LogConfiguration.class */
public class LogConfiguration {
    public final int logLevel;
    public final String tag;
    public final boolean withThread;
    public final boolean withStackTrace;
    public final String stackTraceOrigin;
    public final int stackTraceDepth;
    public final boolean withBorder;
    public final JsonFormatter jsonFormatter;
    public final XmlFormatter xmlFormatter;
    public final ThrowableFormatter throwableFormatter;
    public final ThreadFormatter threadFormatter;
    public final StackTraceFormatter stackTraceFormatter;
    public final BorderFormatter borderFormatter;
    private final Map<Class<?>, ObjectFormatter<?>> objectFormatters;
    public final List<Interceptor> interceptors;

    LogConfiguration(Builder builder) {
        this.logLevel = builder.logLevel;
        this.tag = builder.tag;
        this.withThread = builder.withThread;
        this.withStackTrace = builder.withStackTrace;
        this.stackTraceOrigin = builder.stackTraceOrigin;
        this.stackTraceDepth = builder.stackTraceDepth;
        this.withBorder = builder.withBorder;
        this.jsonFormatter = builder.jsonFormatter;
        this.xmlFormatter = builder.xmlFormatter;
        this.throwableFormatter = builder.throwableFormatter;
        this.threadFormatter = builder.threadFormatter;
        this.stackTraceFormatter = builder.stackTraceFormatter;
        this.borderFormatter = builder.borderFormatter;
        this.objectFormatters = builder.objectFormatters;
        this.interceptors = builder.interceptors;
    }

    public <T> ObjectFormatter<? super T> getObjectFormatter(T object) {
        ObjectFormatter<? super T> formatter;
        if (this.objectFormatters == null) {
            return null;
        }
        Class<? super Object> cls = object.getClass();
        do {
            formatter = (ObjectFormatter<? super T>) this.objectFormatters.get(cls);
            cls = cls.getSuperclass();
            if (formatter != null) {
                break;
            }
        } while (cls != null);
        return formatter;
    }

    public boolean isLoggable(int level) {
        return level >= this.logLevel;
    }

    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/LogConfiguration$Builder.class */
    public static class Builder {
        private static final int DEFAULT_LOG_LEVEL = Integer.MIN_VALUE;
        private static final String DEFAULT_TAG = "X-LOG";
        private int logLevel;
        private String tag;
        private boolean withThread;
        private boolean withStackTrace;
        private String stackTraceOrigin;
        private int stackTraceDepth;
        private boolean withBorder;
        private JsonFormatter jsonFormatter;
        private XmlFormatter xmlFormatter;
        private ThrowableFormatter throwableFormatter;
        private ThreadFormatter threadFormatter;
        private StackTraceFormatter stackTraceFormatter;
        private BorderFormatter borderFormatter;
        private Map<Class<?>, ObjectFormatter<?>> objectFormatters;
        private List<Interceptor> interceptors;

        public Builder() {
            this.logLevel = Integer.MIN_VALUE;
            this.tag = DEFAULT_TAG;
        }

        public Builder(LogConfiguration logConfiguration) {
            this.logLevel = Integer.MIN_VALUE;
            this.tag = DEFAULT_TAG;
            this.logLevel = logConfiguration.logLevel;
            this.tag = logConfiguration.tag;
            this.withThread = logConfiguration.withThread;
            this.withStackTrace = logConfiguration.withStackTrace;
            this.stackTraceOrigin = logConfiguration.stackTraceOrigin;
            this.stackTraceDepth = logConfiguration.stackTraceDepth;
            this.withBorder = logConfiguration.withBorder;
            this.jsonFormatter = logConfiguration.jsonFormatter;
            this.xmlFormatter = logConfiguration.xmlFormatter;
            this.throwableFormatter = logConfiguration.throwableFormatter;
            this.threadFormatter = logConfiguration.threadFormatter;
            this.stackTraceFormatter = logConfiguration.stackTraceFormatter;
            this.borderFormatter = logConfiguration.borderFormatter;
            if (logConfiguration.objectFormatters != null) {
                this.objectFormatters = new HashMap(logConfiguration.objectFormatters);
            }
            if (logConfiguration.interceptors != null) {
                this.interceptors = new ArrayList(logConfiguration.interceptors);
            }
        }

        public Builder logLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder t() {
            return enableThreadInfo();
        }

        public Builder enableThreadInfo() {
            this.withThread = true;
            return this;
        }

        public Builder nt() {
            return disableThreadInfo();
        }

        public Builder disableThreadInfo() {
            this.withThread = false;
            return this;
        }

        public Builder st(int depth) {
            enableStackTrace(depth);
            return this;
        }

        public Builder enableStackTrace(int depth) {
            enableStackTrace(null, depth);
            return this;
        }

        public Builder st(String stackTraceOrigin, int depth) {
            return enableStackTrace(stackTraceOrigin, depth);
        }

        public Builder enableStackTrace(String stackTraceOrigin, int depth) {
            this.withStackTrace = true;
            this.stackTraceOrigin = stackTraceOrigin;
            this.stackTraceDepth = depth;
            return this;
        }

        public Builder nst() {
            return disableStackTrace();
        }

        public Builder disableStackTrace() {
            this.withStackTrace = false;
            this.stackTraceOrigin = null;
            this.stackTraceDepth = 0;
            return this;
        }

        public Builder b() {
            return enableBorder();
        }

        public Builder enableBorder() {
            this.withBorder = true;
            return this;
        }

        public Builder nb() {
            return disableBorder();
        }

        public Builder disableBorder() {
            this.withBorder = false;
            return this;
        }

        public Builder jsonFormatter(JsonFormatter jsonFormatter) {
            this.jsonFormatter = jsonFormatter;
            return this;
        }

        public Builder xmlFormatter(XmlFormatter xmlFormatter) {
            this.xmlFormatter = xmlFormatter;
            return this;
        }

        public Builder throwableFormatter(ThrowableFormatter throwableFormatter) {
            this.throwableFormatter = throwableFormatter;
            return this;
        }

        public Builder threadFormatter(ThreadFormatter threadFormatter) {
            this.threadFormatter = threadFormatter;
            return this;
        }

        public Builder stackTraceFormatter(StackTraceFormatter stackTraceFormatter) {
            this.stackTraceFormatter = stackTraceFormatter;
            return this;
        }

        public Builder borderFormatter(BorderFormatter borderFormatter) {
            this.borderFormatter = borderFormatter;
            return this;
        }

        public <T> Builder addObjectFormatter(Class<T> objectClass, ObjectFormatter<? super T> objectFormatter) {
            if (this.objectFormatters == null) {
                this.objectFormatters = new HashMap(DefaultsFactory.builtinObjectFormatters());
            }
            this.objectFormatters.put(objectClass, objectFormatter);
            return this;
        }

        public Builder objectFormatters(Map<Class<?>, ObjectFormatter<?>> objectFormatters) {
            this.objectFormatters = objectFormatters;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (this.interceptors == null) {
                this.interceptors = new ArrayList();
            }
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder interceptors(List<Interceptor> interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public LogConfiguration build() {
            initEmptyFieldsWithDefaultValues();
            return new LogConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if (this.jsonFormatter == null) {
                this.jsonFormatter = DefaultsFactory.createJsonFormatter();
            }
            if (this.xmlFormatter == null) {
                this.xmlFormatter = DefaultsFactory.createXmlFormatter();
            }
            if (this.throwableFormatter == null) {
                this.throwableFormatter = DefaultsFactory.createThrowableFormatter();
            }
            if (this.threadFormatter == null) {
                this.threadFormatter = DefaultsFactory.createThreadFormatter();
            }
            if (this.stackTraceFormatter == null) {
                this.stackTraceFormatter = DefaultsFactory.createStackTraceFormatter();
            }
            if (this.borderFormatter == null) {
                this.borderFormatter = DefaultsFactory.createBorderFormatter();
            }
            if (this.objectFormatters == null) {
                this.objectFormatters = new HashMap(DefaultsFactory.builtinObjectFormatters());
            }
        }
    }
}
