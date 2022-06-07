package com.jeremyliao.liveeventbus.logger;

import java.util.logging.Level;

/* loaded from: classes2.dex */
public interface Logger {
    void log(Level level, String str);

    void log(Level level, String str, Throwable th);
}
