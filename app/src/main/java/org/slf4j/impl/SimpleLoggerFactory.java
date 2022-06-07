package org.slf4j.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/* loaded from: classes4.dex */
public class SimpleLoggerFactory implements ILoggerFactory {
    ConcurrentMap<String, Logger> a = new ConcurrentHashMap();

    @Override // org.slf4j.ILoggerFactory
    public Logger getLogger(String str) {
        Logger logger = this.a.get(str);
        if (logger != null) {
            return logger;
        }
        SimpleLogger simpleLogger = new SimpleLogger(str);
        Logger putIfAbsent = this.a.putIfAbsent(str, simpleLogger);
        return putIfAbsent == null ? simpleLogger : putIfAbsent;
    }
}
