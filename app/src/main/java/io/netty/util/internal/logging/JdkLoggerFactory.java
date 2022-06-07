package io.netty.util.internal.logging;

import java.util.logging.Logger;

/* loaded from: classes4.dex */
public class JdkLoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new JdkLoggerFactory();

    @Override // io.netty.util.internal.logging.InternalLoggerFactory
    public InternalLogger newInstance(String str) {
        return new c(Logger.getLogger(str));
    }
}
