package com.xiaomi.push;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class m implements ThreadFactory {
    private final ThreadGroup a;
    private final AtomicInteger b = new AtomicInteger(1);
    private final String c;

    public m(String str) {
        SecurityManager securityManager = System.getSecurityManager();
        this.a = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.c = "mipush-" + str + "-thread-";
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        ThreadGroup threadGroup = this.a;
        Thread thread = new Thread(threadGroup, runnable, this.c + this.b.getAndIncrement(), 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        return thread;
    }
}
