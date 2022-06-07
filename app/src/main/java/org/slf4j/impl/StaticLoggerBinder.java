package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/* loaded from: classes4.dex */
public class StaticLoggerBinder implements LoggerFactoryBinder {
    private final ILoggerFactory c = new SimpleLoggerFactory();
    private static final StaticLoggerBinder a = new StaticLoggerBinder();
    public static String REQUESTED_API_VERSION = "1.6.99";
    private static final String b = SimpleLoggerFactory.class.getName();

    public static final StaticLoggerBinder getSingleton() {
        return a;
    }

    private StaticLoggerBinder() {
    }

    @Override // org.slf4j.spi.LoggerFactoryBinder
    public ILoggerFactory getLoggerFactory() {
        return this.c;
    }

    @Override // org.slf4j.spi.LoggerFactoryBinder
    public String getLoggerFactoryClassStr() {
        return b;
    }
}
