package com.elvishew.xlog;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.formatter.border.BorderFormatter;
import com.elvishew.xlog.formatter.message.json.JsonFormatter;
import com.elvishew.xlog.formatter.message.object.ObjectFormatter;
import com.elvishew.xlog.formatter.message.throwable.ThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.XmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.StackTraceFormatter;
import com.elvishew.xlog.formatter.thread.ThreadFormatter;
import com.elvishew.xlog.interceptor.Interceptor;
import com.elvishew.xlog.internal.DefaultsFactory;
import com.elvishew.xlog.internal.SystemCompat;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.PrinterSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/Logger.class */
public class Logger {
    private LogConfiguration logConfiguration;
    private Printer printer;

    public Logger(LogConfiguration logConfiguration, Printer printer) {
        this.logConfiguration = logConfiguration;
        this.printer = printer;
    }

    Logger(Builder builder) {
        LogConfiguration.Builder logConfigBuilder = new LogConfiguration.Builder(XLog.sLogConfiguration);
        if (builder.logLevel != 0) {
            logConfigBuilder.logLevel(builder.logLevel);
        }
        if (builder.tag != null) {
            logConfigBuilder.tag(builder.tag);
        }
        if (builder.threadSet) {
            if (builder.withThread) {
                logConfigBuilder.enableThreadInfo();
            } else {
                logConfigBuilder.disableThreadInfo();
            }
        }
        if (builder.stackTraceSet) {
            if (builder.withStackTrace) {
                logConfigBuilder.enableStackTrace(builder.stackTraceOrigin, builder.stackTraceDepth);
            } else {
                logConfigBuilder.disableStackTrace();
            }
        }
        if (builder.borderSet) {
            if (builder.withBorder) {
                logConfigBuilder.enableBorder();
            } else {
                logConfigBuilder.disableBorder();
            }
        }
        if (builder.jsonFormatter != null) {
            logConfigBuilder.jsonFormatter(builder.jsonFormatter);
        }
        if (builder.xmlFormatter != null) {
            logConfigBuilder.xmlFormatter(builder.xmlFormatter);
        }
        if (builder.throwableFormatter != null) {
            logConfigBuilder.throwableFormatter(builder.throwableFormatter);
        }
        if (builder.threadFormatter != null) {
            logConfigBuilder.threadFormatter(builder.threadFormatter);
        }
        if (builder.stackTraceFormatter != null) {
            logConfigBuilder.stackTraceFormatter(builder.stackTraceFormatter);
        }
        if (builder.borderFormatter != null) {
            logConfigBuilder.borderFormatter(builder.borderFormatter);
        }
        if (builder.objectFormatters != null) {
            logConfigBuilder.objectFormatters(builder.objectFormatters);
        }
        if (builder.interceptors != null) {
            logConfigBuilder.interceptors(builder.interceptors);
        }
        this.logConfiguration = logConfigBuilder.build();
        if (builder.printer != null) {
            this.printer = builder.printer;
        } else {
            this.printer = XLog.sPrinter;
        }
    }

    public void v(Object object) {
        println(2, (int) object);
    }

    public void v(Object[] array) {
        println(2, array);
    }

    public void v(String format, Object... args) {
        println(2, format, args);
    }

    public void v(String msg) {
        println(2, msg);
    }

    public void v(String msg, Throwable tr) {
        println(2, msg, tr);
    }

    public void d(Object object) {
        println(3, (int) object);
    }

    public void d(Object[] array) {
        println(3, array);
    }

    public void d(String format, Object... args) {
        println(3, format, args);
    }

    public void d(String msg) {
        println(3, msg);
    }

    public void d(String msg, Throwable tr) {
        println(3, msg, tr);
    }

    public void i(Object object) {
        println(4, (int) object);
    }

    public void i(Object[] array) {
        println(4, array);
    }

    public void i(String format, Object... args) {
        println(4, format, args);
    }

    public void i(String msg) {
        println(4, msg);
    }

    public void i(String msg, Throwable tr) {
        println(4, msg, tr);
    }

    public void w(Object object) {
        println(5, (int) object);
    }

    public void w(Object[] array) {
        println(5, array);
    }

    public void w(String format, Object... args) {
        println(5, format, args);
    }

    public void w(String msg) {
        println(5, msg);
    }

    public void w(String msg, Throwable tr) {
        println(5, msg, tr);
    }

    public void e(Object object) {
        println(6, (int) object);
    }

    public void e(Object[] array) {
        println(6, array);
    }

    public void e(String format, Object... args) {
        println(6, format, args);
    }

    public void e(String msg) {
        println(6, msg);
    }

    public void e(String msg, Throwable tr) {
        println(6, msg, tr);
    }

    public void log(int logLevel, Object object) {
        println(logLevel, (int) object);
    }

    public void log(int logLevel, Object[] array) {
        println(logLevel, array);
    }

    public void log(int logLevel, String format, Object... args) {
        println(logLevel, format, args);
    }

    public void log(int logLevel, String msg) {
        println(logLevel, msg);
    }

    public void log(int logLevel, String msg, Throwable tr) {
        println(logLevel, msg, tr);
    }

    public void json(String json) {
        if (3 >= this.logConfiguration.logLevel) {
            printlnInternal(3, this.logConfiguration.jsonFormatter.format(json));
        }
    }

    public void xml(String xml) {
        if (3 >= this.logConfiguration.logLevel) {
            printlnInternal(3, this.logConfiguration.xmlFormatter.format(xml));
        }
    }

    private <T> void println(int logLevel, T object) {
        String objectString;
        if (logLevel >= this.logConfiguration.logLevel) {
            if (object != null) {
                ObjectFormatter<? super T> objectFormatter = this.logConfiguration.getObjectFormatter(object);
                if (objectFormatter != null) {
                    objectString = objectFormatter.format(object);
                } else {
                    objectString = object.toString();
                }
            } else {
                objectString = "null";
            }
            printlnInternal(logLevel, objectString);
        }
    }

    private void println(int logLevel, Object[] array) {
        if (logLevel >= this.logConfiguration.logLevel) {
            printlnInternal(logLevel, Arrays.deepToString(array));
        }
    }

    private void println(int logLevel, String format, Object... args) {
        if (logLevel >= this.logConfiguration.logLevel) {
            printlnInternal(logLevel, formatArgs(format, args));
        }
    }

    public void println(int logLevel, String msg) {
        if (logLevel >= this.logConfiguration.logLevel) {
            printlnInternal(logLevel, msg != null ? msg : "");
        }
    }

    private void println(int logLevel, String msg, Throwable tr) {
        if (logLevel >= this.logConfiguration.logLevel) {
            printlnInternal(logLevel, ((msg == null || msg.length() == 0) ? "" : msg + SystemCompat.lineSeparator) + this.logConfiguration.throwableFormatter.format(tr));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0090  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void printlnInternal(int r10, java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 393
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.Logger.printlnInternal(int, java.lang.String):void");
    }

    private String formatArgs(String format, Object... args) {
        if (format != null) {
            return String.format(format, args);
        }
        StringBuilder sb = new StringBuilder();
        int N = args.length;
        for (int i = 0; i < N; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(args[i]);
        }
        return sb.toString();
    }

    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/Logger$Builder.class */
    public static class Builder {
        private int logLevel;
        private String tag;
        private boolean withThread;
        private boolean threadSet;
        private boolean withStackTrace;
        private String stackTraceOrigin;
        private int stackTraceDepth;
        private boolean stackTraceSet;
        private boolean withBorder;
        private boolean borderSet;
        private JsonFormatter jsonFormatter;
        private XmlFormatter xmlFormatter;
        private ThrowableFormatter throwableFormatter;
        private ThreadFormatter threadFormatter;
        private StackTraceFormatter stackTraceFormatter;
        private BorderFormatter borderFormatter;
        private Map<Class<?>, ObjectFormatter<?>> objectFormatters;
        private List<Interceptor> interceptors;
        private Printer printer;

        public Builder() {
            XLog.assertInitialization();
        }

        public Builder logLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        @Deprecated
        public Builder t() {
            return enableThreadInfo();
        }

        public Builder enableThreadInfo() {
            this.withThread = true;
            this.threadSet = true;
            return this;
        }

        @Deprecated
        public Builder nt() {
            return disableThreadInfo();
        }

        public Builder disableThreadInfo() {
            this.withThread = false;
            this.threadSet = true;
            return this;
        }

        @Deprecated
        public Builder st(int depth) {
            return enableStackTrace(depth);
        }

        public Builder enableStackTrace(int depth) {
            this.withStackTrace = true;
            this.stackTraceDepth = depth;
            this.stackTraceSet = true;
            return this;
        }

        @Deprecated
        public Builder st(String stackTraceOrigin, int depth) {
            return enableStackTrace(stackTraceOrigin, depth);
        }

        public Builder enableStackTrace(String stackTraceOrigin, int depth) {
            this.withStackTrace = true;
            this.stackTraceOrigin = stackTraceOrigin;
            this.stackTraceDepth = depth;
            this.stackTraceSet = true;
            return this;
        }

        @Deprecated
        public Builder nst() {
            return disableStackTrace();
        }

        public Builder disableStackTrace() {
            this.withStackTrace = false;
            this.stackTraceOrigin = null;
            this.stackTraceDepth = 0;
            this.stackTraceSet = true;
            return this;
        }

        @Deprecated
        public Builder b() {
            return enableBorder();
        }

        public Builder enableBorder() {
            this.withBorder = true;
            this.borderSet = true;
            return this;
        }

        @Deprecated
        public Builder nb() {
            return disableBorder();
        }

        public Builder disableBorder() {
            this.withBorder = false;
            this.borderSet = true;
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

        public Builder addInterceptor(Interceptor interceptor) {
            if (this.interceptors == null) {
                this.interceptors = new ArrayList();
            }
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder printers(Printer... printers) {
            if (printers.length == 0) {
                this.printer = null;
            } else if (printers.length == 1) {
                this.printer = printers[0];
            } else {
                this.printer = new PrinterSet(printers);
            }
            return this;
        }

        public void v(Object object) {
            build().v(object);
        }

        public void v(Object[] array) {
            build().v(array);
        }

        public void v(String format, Object... args) {
            build().v(format, args);
        }

        public void v(String msg) {
            build().v(msg);
        }

        public void v(String msg, Throwable tr) {
            build().v(msg, tr);
        }

        public void d(Object object) {
            build().d(object);
        }

        public void d(Object[] array) {
            build().d(array);
        }

        public void d(String format, Object... args) {
            build().d(format, args);
        }

        public void d(String msg) {
            build().d(msg);
        }

        public void d(String msg, Throwable tr) {
            build().d(msg, tr);
        }

        public void i(Object object) {
            build().i(object);
        }

        public void i(Object[] array) {
            build().i(array);
        }

        public void i(String format, Object... args) {
            build().i(format, args);
        }

        public void i(String msg) {
            build().i(msg);
        }

        public void i(String msg, Throwable tr) {
            build().i(msg, tr);
        }

        public void w(Object object) {
            build().w(object);
        }

        public void w(Object[] array) {
            build().w(array);
        }

        public void w(String format, Object... args) {
            build().w(format, args);
        }

        public void w(String msg) {
            build().w(msg);
        }

        public void w(String msg, Throwable tr) {
            build().w(msg, tr);
        }

        public void e(Object object) {
            build().e(object);
        }

        public void e(Object[] array) {
            build().e(array);
        }

        public void e(String format, Object... args) {
            build().e(format, args);
        }

        public void e(String msg) {
            build().e(msg);
        }

        public void e(String msg, Throwable tr) {
            build().e(msg, tr);
        }

        public void log(int logLevel, Object object) {
            build().log(logLevel, object);
        }

        public void log(int logLevel, Object[] array) {
            build().log(logLevel, array);
        }

        public void log(int logLevel, String format, Object... args) {
            build().log(logLevel, format, args);
        }

        public void log(int logLevel, String msg) {
            build().log(logLevel, msg);
        }

        public void log(int logLevel, String msg, Throwable tr) {
            build().log(logLevel, msg, tr);
        }

        public void json(String json) {
            build().json(json);
        }

        public void xml(String xml) {
            build().xml(xml);
        }

        public Logger build() {
            return new Logger(this);
        }
    }
}
