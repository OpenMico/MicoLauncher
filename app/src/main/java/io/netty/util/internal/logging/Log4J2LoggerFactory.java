package io.netty.util.internal.logging;

import org.apache.logging.log4j.LogManager;

/* loaded from: classes4.dex */
public final class Log4J2LoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new Log4J2LoggerFactory();

    @Override // io.netty.util.internal.logging.InternalLoggerFactory
    public InternalLogger newInstance(String str) {
        return new d(LogManager.getLogger(str));
    }
}
