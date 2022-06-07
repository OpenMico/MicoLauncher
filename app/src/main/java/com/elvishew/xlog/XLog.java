package com.elvishew.xlog;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.formatter.border.BorderFormatter;
import com.elvishew.xlog.formatter.message.json.JsonFormatter;
import com.elvishew.xlog.formatter.message.object.ObjectFormatter;
import com.elvishew.xlog.formatter.message.throwable.ThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.XmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.StackTraceFormatter;
import com.elvishew.xlog.formatter.thread.ThreadFormatter;
import com.elvishew.xlog.interceptor.Interceptor;
import com.elvishew.xlog.internal.DefaultsFactory;
import com.elvishew.xlog.internal.Platform;
import com.elvishew.xlog.internal.util.StackTraceUtil;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.PrinterSet;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import io.netty.handler.codec.compression.ZlibWrapper;

/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/XLog.class */
public class XLog {
    private static Logger sLogger;
    static LogConfiguration sLogConfiguration;
    static Printer sPrinter;
    static boolean sIsInitialized;

    private XLog() {
    }

    public static void init() {
        init(new LogConfiguration.Builder().build(), DefaultsFactory.createPrinter());
    }

    public static void init(int logLevel) {
        init(new LogConfiguration.Builder().logLevel(logLevel).build(), DefaultsFactory.createPrinter());
    }

    @Deprecated
    public static void init(int logLevel, LogConfiguration logConfiguration) {
        init(new LogConfiguration.Builder(logConfiguration).logLevel(logLevel).build());
    }

    public static void init(LogConfiguration logConfiguration) {
        init(logConfiguration, DefaultsFactory.createPrinter());
    }

    public static void init(Printer... printers) {
        init(new LogConfiguration.Builder().build(), printers);
    }

    public static void init(int logLevel, Printer... printers) {
        init(new LogConfiguration.Builder().logLevel(logLevel).build(), printers);
    }

    @Deprecated
    public static void init(int logLevel, LogConfiguration logConfiguration, Printer... printers) {
        init(new LogConfiguration.Builder(logConfiguration).logLevel(logLevel).build(), printers);
    }

    public static void init(LogConfiguration logConfiguration, Printer... printers) {
        if (sIsInitialized) {
            Platform.get().warn("XLog is already initialized, do not initialize again");
        }
        sIsInitialized = true;
        if (logConfiguration == null) {
            throw new IllegalArgumentException("Please specify a LogConfiguration");
        }
        sLogConfiguration = logConfiguration;
        sPrinter = new PrinterSet(printers);
        sLogger = new Logger(sLogConfiguration, sPrinter);
    }

    public static void assertInitialization() {
        if (!sIsInitialized) {
            throw new IllegalStateException("Do you forget to initialize XLog?");
        }
    }

    public static Logger.Builder logLevel(int logLevel) {
        return new Logger.Builder().logLevel(logLevel);
    }

    public static Logger.Builder tag(String tag) {
        return new Logger.Builder().tag(tag);
    }

    @Deprecated
    public static Logger.Builder t() {
        return enableThreadInfo();
    }

    public static Logger.Builder enableThreadInfo() {
        return new Logger.Builder().enableThreadInfo();
    }

    @Deprecated
    public static Logger.Builder nt() {
        return disableThreadInfo();
    }

    public static Logger.Builder disableThreadInfo() {
        return new Logger.Builder().disableThreadInfo();
    }

    @Deprecated
    public static Logger.Builder st(int depth) {
        return enableStackTrace(depth);
    }

    public static Logger.Builder enableStackTrace(int depth) {
        return new Logger.Builder().enableStackTrace(depth);
    }

    @Deprecated
    public static Logger.Builder st(String stackTraceOrigin, int depth) {
        return enableStackTrace(stackTraceOrigin, depth);
    }

    public static Logger.Builder enableStackTrace(String stackTraceOrigin, int depth) {
        return new Logger.Builder().enableStackTrace(stackTraceOrigin, depth);
    }

    @Deprecated
    public static Logger.Builder nst() {
        return disableStackTrace();
    }

    public static Logger.Builder disableStackTrace() {
        return new Logger.Builder().disableStackTrace();
    }

    @Deprecated
    public static Logger.Builder b() {
        return enableBorder();
    }

    public static Logger.Builder enableBorder() {
        return new Logger.Builder().enableBorder();
    }

    @Deprecated
    public static Logger.Builder nb() {
        return disableBorder();
    }

    public static Logger.Builder disableBorder() {
        return new Logger.Builder().disableBorder();
    }

    public static Logger.Builder jsonFormatter(JsonFormatter jsonFormatter) {
        return new Logger.Builder().jsonFormatter(jsonFormatter);
    }

    public static Logger.Builder xmlFormatter(XmlFormatter xmlFormatter) {
        return new Logger.Builder().xmlFormatter(xmlFormatter);
    }

    public static Logger.Builder throwableFormatter(ThrowableFormatter throwableFormatter) {
        return new Logger.Builder().throwableFormatter(throwableFormatter);
    }

    public static Logger.Builder threadFormatter(ThreadFormatter threadFormatter) {
        return new Logger.Builder().threadFormatter(threadFormatter);
    }

    public static Logger.Builder stackTraceFormatter(StackTraceFormatter stackTraceFormatter) {
        return new Logger.Builder().stackTraceFormatter(stackTraceFormatter);
    }

    public static Logger.Builder borderFormatter(BorderFormatter borderFormatter) {
        return new Logger.Builder().borderFormatter(borderFormatter);
    }

    public static <T> Logger.Builder addObjectFormatter(Class<T> objectClass, ObjectFormatter<? super T> objectFormatter) {
        return new Logger.Builder().addObjectFormatter(objectClass, objectFormatter);
    }

    public static Logger.Builder addInterceptor(Interceptor interceptor) {
        return new Logger.Builder().addInterceptor(interceptor);
    }

    public static Logger.Builder printers(Printer... printers) {
        return new Logger.Builder().printers(printers);
    }

    public static void v(Object object) {
        assertInitialization();
        sLogger.v(object);
    }

    public static void v(Object[] array) {
        assertInitialization();
        sLogger.v(array);
    }

    public static void v(String format, Object... args) {
        assertInitialization();
        sLogger.v(format, args);
    }

    public static void v(String msg) {
        assertInitialization();
        sLogger.v(msg);
    }

    public static void v(String msg, Throwable tr) {
        assertInitialization();
        sLogger.v(msg, tr);
    }

    public static void d(Object object) {
        assertInitialization();
        sLogger.d(object);
    }

    public static void d(Object[] array) {
        assertInitialization();
        sLogger.d(array);
    }

    public static void d(String format, Object... args) {
        assertInitialization();
        sLogger.d(format, args);
    }

    public static void d(String msg) {
        assertInitialization();
        sLogger.d(msg);
    }

    public static void d(String msg, Throwable tr) {
        assertInitialization();
        sLogger.d(msg, tr);
    }

    public static void i(Object object) {
        assertInitialization();
        sLogger.i(object);
    }

    public static void i(Object[] array) {
        assertInitialization();
        sLogger.i(array);
    }

    public static void i(String format, Object... args) {
        assertInitialization();
        sLogger.i(format, args);
    }

    public static void i(String msg) {
        assertInitialization();
        sLogger.i(msg);
    }

    public static void i(String msg, Throwable tr) {
        assertInitialization();
        sLogger.i(msg, tr);
    }

    public static void w(Object object) {
        assertInitialization();
        sLogger.w(object);
    }

    public static void w(Object[] array) {
        assertInitialization();
        sLogger.w(array);
    }

    public static void w(String format, Object... args) {
        assertInitialization();
        sLogger.w(format, args);
    }

    public static void w(String msg) {
        assertInitialization();
        sLogger.w(msg);
    }

    public static void w(String msg, Throwable tr) {
        assertInitialization();
        sLogger.w(msg, tr);
    }

    public static void e(Object object) {
        assertInitialization();
        sLogger.e(object);
    }

    public static void e(Object[] array) {
        assertInitialization();
        sLogger.e(array);
    }

    public static void e(String format, Object... args) {
        assertInitialization();
        sLogger.e(format, args);
    }

    public static void e(String msg) {
        assertInitialization();
        sLogger.e(msg);
    }

    public static void e(String msg, Throwable tr) {
        assertInitialization();
        sLogger.e(msg, tr);
    }

    public static void log(int logLevel, Object object) {
        assertInitialization();
        sLogger.log(logLevel, object);
    }

    public static void log(int logLevel, Object[] array) {
        assertInitialization();
        sLogger.log(logLevel, array);
    }

    public static void log(int logLevel, String format, Object... args) {
        assertInitialization();
        sLogger.log(logLevel, format, args);
    }

    public static void log(int logLevel, String msg) {
        assertInitialization();
        sLogger.log(logLevel, msg);
    }

    public static void log(int logLevel, String msg, Throwable tr) {
        assertInitialization();
        sLogger.log(logLevel, msg, tr);
    }

    public static void json(String json) {
        assertInitialization();
        sLogger.json(json);
    }

    public static void xml(String xml) {
        assertInitialization();
        sLogger.xml(xml);
    }

    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/XLog$Log.class */
    public static class Log {
        public Log() {
        }

        /*  JADX ERROR: ArrayIndexOutOfBoundsException in pass: SSATransform
            java.lang.ArrayIndexOutOfBoundsException
            */
        /* renamed from: <init> */
        public static void m16init(
        /*  JADX ERROR: ArrayIndexOutOfBoundsException in pass: SSATransform
            java.lang.ArrayIndexOutOfBoundsException
            */
        /*  JADX ERROR: Method generation error
            jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r3v0 ??
            	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
            	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            */

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0007: MOVE  (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('msg' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        /* renamed from: a */
        public static void operationComplete(
        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0007: MOVE  (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('msg' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            */
        /*  JADX ERROR: Method generation error
            jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v0 ??
            	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
            	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            */

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 4, insn: 0x0007: MOVE  (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('msg' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        public static void operationComplete(
        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 4, insn: 0x0007: MOVE  (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('msg' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            */
        /*  JADX ERROR: Method generation error
            jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r3v0 ??
            	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
            	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            */

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: <init> */
        public static void m17init(JdkZlibEncoder jdkZlibEncoder, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            XLog.tag(jdkZlibEncoder).build().d((String) channelHandlerContext, (Throwable) channelPromise);
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 3, insn: 0x0000: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r3 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tag' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        public static void run() {
            /*
                r0 = r3
                com.elvishew.xlog.Logger$Builder r0 = com.elvishew.xlog.XLog.tag(r0)
                com.elvishew.xlog.Logger r0 = r0.build()
                r1 = r4
                r0.i(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.XLog.Log.run():void");
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 4, insn: 0x0000: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tag' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        static {
            /*
                r0 = r4
                com.elvishew.xlog.Logger$Builder r0 = com.elvishew.xlog.XLog.tag(r0)
                com.elvishew.xlog.Logger r0 = r0.build()
                r1 = r5
                r2 = r6
                r0.i(r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.XLog.Log.m18clinit():void");
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 3, insn: 0x0000: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r3 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tag' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        static {
            /*
                r0 = r3
                com.elvishew.xlog.Logger$Builder r0 = com.elvishew.xlog.XLog.tag(r0)
                com.elvishew.xlog.Logger r0 = r0.build()
                r1 = r4
                r0.w(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.XLog.Log.m19clinit():void");
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 4, insn: 0x0000: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tag' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        /* renamed from: <init> */
        public static void m20init() {
            /*
                r0 = r4
                com.elvishew.xlog.Logger$Builder r0 = com.elvishew.xlog.XLog.tag(r0)
                com.elvishew.xlog.Logger r0 = r0.build()
                r1 = r5
                r2 = r6
                r0.w(r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.XLog.Log.m20init():void");
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0009: MOVE  (r2 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tr' java.lang.Throwable)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        /* renamed from: <init> */
        public static void m21init(
        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0009: MOVE  (r2 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tr' java.lang.Throwable)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            */
        /*  JADX ERROR: Method generation error
            jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v0 ??
            	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
            	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            */

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: <init> */
        public static void m22init(int i, byte[] bArr) {
            XLog.tag(i).build().e((String) bArr);
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0007: MOVE  (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('msg' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        /* renamed from: <init> */
        public static void m23init(
        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0007: MOVE  (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('msg' java.lang.String)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            */
        /*  JADX ERROR: Method generation error
            jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v0 ??
            	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
            	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            */

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: <init> */
        public static void m24init(ZlibWrapper zlibWrapper, int i) {
            e(zlibWrapper, i);
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0003: MOVE  (r2 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tr' java.lang.Throwable)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        /* renamed from: <init> */
        public static void m25init(
        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 5, insn: 0x0003: MOVE  (r2 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tr' java.lang.Throwable)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            */
        /*  JADX ERROR: Method generation error
            jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v0 ??
            	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
            	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            */

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 6, insn: 0x0002: MOVE  (r2 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r6 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tr' java.lang.Throwable)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        public static io.netty.channel.ChannelFuture a(
        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 6, insn: 0x0002: MOVE  (r2 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r6 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('tr' java.lang.Throwable)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            */
        /*  JADX ERROR: Method generation error
            jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v0 ??
            	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
            	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            */

        /* JADX WARN: Multi-variable type inference failed */
        public static ChannelFuture a(JdkZlibEncoder jdkZlibEncoder, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            XLog.tag(channelHandlerContext).build().println((int) jdkZlibEncoder, (String) channelPromise);
            return;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 4, insn: 0x0003: MOVE  (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('level' int)]), block:B:2:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            */
        public static io.netty.channel.ChannelHandlerContext a() {
            /*
                com.elvishew.xlog.LogConfiguration r0 = com.elvishew.xlog.XLog.sLogConfiguration
                r1 = r4
                boolean r0 = r0.isLoggable(r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.XLog.Log.a():io.netty.channel.ChannelHandlerContext");
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [io.netty.channel.ChannelHandlerContext, java.lang.String] */
        public static ChannelHandlerContext a(JdkZlibEncoder jdkZlibEncoder) {
            return StackTraceUtil.getStackTraceString(jdkZlibEncoder);
        }
    }
}
