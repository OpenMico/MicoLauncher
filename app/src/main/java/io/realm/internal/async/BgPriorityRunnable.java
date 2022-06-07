package io.realm.internal.async;

import android.os.Process;

/* loaded from: classes5.dex */
public class BgPriorityRunnable implements Runnable {
    private final Runnable a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BgPriorityRunnable(Runnable runnable) {
        this.a = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        this.a.run();
    }
}
