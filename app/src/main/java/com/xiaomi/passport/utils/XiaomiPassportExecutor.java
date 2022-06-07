package com.xiaomi.passport.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class XiaomiPassportExecutor {
    private static final ExecutorService sExecutorService = Executors.newCachedThreadPool();

    public static ExecutorService getSingleton() {
        return sExecutorService;
    }
}
