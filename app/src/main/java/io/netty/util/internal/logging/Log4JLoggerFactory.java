package io.netty.util.internal.logging;

import org.apache.log4j.Logger;

/* loaded from: classes4.dex */
public class Log4JLoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new Log4JLoggerFactory();

    @Override // io.netty.util.internal.logging.InternalLoggerFactory
    public InternalLogger newInstance(String str) {
        return new e(Logger.getLogger(str));
    }
}
