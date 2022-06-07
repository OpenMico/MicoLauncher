package com.xiaomi.ai.b;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class e implements ThreadFactory {
    private final int a;
    private final AtomicInteger b = new AtomicInteger();
    private final String c;

    public e(String str, int i) {
        this.c = str;
        this.a = i;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "AIVS-" + this.c + '-' + this.b.getAndIncrement()) { // from class: com.xiaomi.ai.b.e.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                setPriority(e.this.a);
                super.run();
            }
        };
    }
}
